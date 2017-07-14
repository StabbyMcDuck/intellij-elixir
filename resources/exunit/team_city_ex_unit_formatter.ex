# Originally based on https://github.com/lixhq/teamcity-exunit-formatter, but it did not work for parallel tests: IDEA
# does not honor flowId, so needed to use the nodeId/parentNodeIde system
#
# nodeId and parentNodeId system is documented in
# https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000389550/comments/115000330464
defmodule TeamCityExUnitFormatter do
  @moduledoc false

  use GenEvent

  @root_parent_node_id 0

  # Functions

  @doc false
  def formatter(_color, msg), do: msg

  ## GenEvent Callbacks

  def handle_event({:case_finished, test_case = %ExUnit.TestCase{}}, config) do
    put_formatted :test_suite_finished, attributes(test_case)

    {:ok, config}
  end

  def handle_event({:case_started, test_case = %ExUnit.TestCase{}}, config) do
    put_formatted :test_suite_started, attributes(test_case)

    {:ok, config}
  end

  def handle_event(
        {:test_finished, test = %ExUnit.Test{state: {:failed, {_, reason, _} = failed},}, time: time},
        config
      ) do
    formatted = ExUnit.Formatter.format_test_failure(
      test,
      failed,
      config.failures_counter + 1,
      config.width,
      &formatter/2
    )
    attributes = attributes(test)

    put_formatted :test_failed,
                  Keyword.merge(
                    attributes,
                    details: formatted,
                    message: inspect(reason)
                  )
    put_formatted :test_finished,
                  Keyword.merge(
                    attributes,
                    duration: div(time, 1000)
                  )

    {
      :ok,
      %{
        config |
        tests_counter: config.tests_counter + 1,
        failures_counter: config.failures_counter + 1
      }
    }
  end

  def handle_event({:test_finished, test = %ExUnit.Test{state: {:failed, failed}}, time: time}, config)
      when is_list(failed) do
    formatted = ExUnit.Formatter.format_test_failure(
      test,
      failed,
      config.failures_counter + 1,
      config.width,
      &formatter/2
    )
    message = Enum.map_join(failed, "", fn {_kind, reason, _stack} -> inspect(reason) end)
    attributes = attributes(test)

    put_formatted :test_failed,
                  Keyword.merge(
                    attributes,
                    details: formatted,
                    message: message
                  )
    put_formatted :test_finished,
                  Keyword.merge(
                    attributes,
                    duration: div(time, 1000)
                  )

    {
      :ok,
      %{
        config |
        tests_counter: config.tests_counter + 1,
        failures_counter: config.failures_counter + 1
      }
    }
  end

  def handle_event({:test_finished, test = %ExUnit.Test{state: {:skip, _}}}, config) do
    attributes = attributes(test)

    put_formatted :test_ignored, attributes
    put_formatted :test_finished, attributes

    {
      :ok,
      %{
        config |
        tests_counter: config.tests_counter + 1,
        skipped_counter: config.skipped_counter + 1
      }
    }
  end

  def handle_event({:test_finished, test = %ExUnit.Test{time: time}}, config) do
    put_formatted :test_finished,
                  test
                  |> attributes()
                  |> Keyword.merge(
                       duration: div(time, 1000)
                     )

    {:ok, config}
  end

  def handle_event({:test_started, test = %ExUnit.Test{tags: tags}}, config) do
    put_formatted :test_started,
                  test
                  |> attributes()
                  |> Keyword.merge(
                    locationHint: "file://#{tags[:file]}:#{tags[:line]}"
                  )

    {:ok, config}
  end

  def handle_event(_, config) do
    {:ok, config}
  end

  def init(opts) do
    {
      :ok,
      %{
        failures_counter: 0,
        invalids_counter: 0,
        seed: opts[:seed],
        skipped_counter: 0,
        tests_counter: 0,
        trace: opts[:trace],
        width: 80
      }
    }
  end

  ## Private Functions

  defp attributes(test_or_test_case) do
    [
      nodeId: nodeId(test_or_test_case),
      name: name(test_or_test_case),
      parentNodeId: parentNodeId(test_or_test_case)
    ]
  end

  defp camelize(s) do
    [head | tail] = String.split s, "_"
    "#{head}#{Enum.map tail, &String.capitalize/1}"
  end

  # Must escape certain characters
  # see: https://confluence.jetbrains.com/display/TCD9/Build+Script+Interaction+with+TeamCity
  defp escape_output(s) when not is_binary(s), do: escape_output("#{s}")
  defp escape_output(s) do
    s
    |> String.replace("|", "||")
    |> String.replace("'", "|'")
    |> String.replace("\n", "|n")
    |> String.replace("\r", "|r")
    |> String.replace("[", "|[")
    |> String.replace("]", "|]")
  end

  defp format(type, attributes) do
    messageName = type
                  |> Atom.to_string()
                  |> camelize()
    attrs = attributes
            |> Enum.map(&format_attribute/1)
            |> Enum.join(" ")
    "##teamcity[#{messageName} #{attrs}]"
  end

  defp format_attribute({k, v}) do
    "#{Atom.to_string k}='#{escape_output v}'"
  end

  defp format_case_name(case_name) do
    case_name
    |> to_string()
    |> String.replace(~r/\bElixir\./, "")
  end

  defp name(test = %ExUnit.Test{name: name}) do
    named_captures = Regex.named_captures(
      ~r|test doc at (?<module>.+)\.(?<function>\w+)/(?<arity>\d+) \((?<count>\d+)\)|,
      to_string(name)
    )
    name(test, named_captures)
  end
  defp name(%ExUnit.TestCase{name: name}), do: format_case_name(name)

  defp name(%ExUnit.Test{name: name}, nil), do: to_string(name)
  defp name(
         %ExUnit.Test{case: case_name},
         %{"arity" => arity, "count" => count, "function" => function, "module" => module}
       ) do
    name = "#{function}/#{arity} doc (#{count})"

    if module <> "Test" == format_case_name(case_name) do
      name
    else
      "#{module}.#{name}"
    end
  end

  defp nodeId(%ExUnit.Test{case: case_name, name: name}), do: "#{case_name}.#{name}"
  defp nodeId(%ExUnit.TestCase{name: name}), do: name

  defp parentNodeId(%ExUnit.Test{case: case_name}), do: case_name
  defp parentNodeId(%ExUnit.TestCase{}), do: @root_parent_node_id

  # DO NOT use `flowId` as an attribute.  IDEA ignores flowId and so it can't be used to interleave async test output
  defp put_formatted(type, attributes) do
    type
    |> format(attributes)
    |> IO.puts()
  end
end
