package org.elixir_lang.elixir_flex_lexer;

import org.elixir_lang.ElixirFlexLexer;
import org.elixir_lang.TokenTypeState;
import org.elixir_lang.psi.ElixirTypes;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by luke.imhoff on 12/7/14.
 */
@RunWith(Parameterized.class)
public class DotRelativeIdentifierParenthesesCallTest extends Test {
    /*
     * Fields
     */

    private CharSequence identifierCharSequence;
    private List<TokenTypeState> tokenTypeStates;

    /*
     * Constructors
     */

    public DotRelativeIdentifierParenthesesCallTest(CharSequence identifierCharSequence, List<TokenTypeState> tokenTypeStates) {
        this.identifierCharSequence = identifierCharSequence;
        this.tokenTypeStates = tokenTypeStates;
    }

    /*
     * Static Methods
     */

    @Parameterized.Parameters(
            name = "\"{0}\" parses"
    )
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
                        {
                                "&&",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.AND_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "&&&",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.AND_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<<<",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<<~",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<|>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<~>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                ">>>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "~>>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<~",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "|>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "~>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ARROW_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        /* NOTE: not parsed as ASSOCIATION_OPERATOR because => is not captured as an operator in the
                           call AST, but instead the key and value are put into a pair tuple directly. */
                        {
                                "=>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.MATCH_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "identifier",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE)
                                )
                        },
                        {
                                "@",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.AT_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                // BIT_STRING_OPERATOR is not a valid relative identifer because it's a special form
                                "<<>>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.TWO_OPERATOR, ElixirFlexLexer.KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "&",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.CAPTURE_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "after",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.AFTER, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "afterwards",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "and",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.AND_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "androids",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "catch",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.CATCH, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "catchall",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "do",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.DO, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "done",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "else",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.ELSE, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "elsewhere",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "end",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.END, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "ending",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "in",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IN_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "inner",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "not",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.UNARY_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "notifiers",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "or",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.OR_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "order",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "rescue",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.RESCUE, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "rescuer",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IDENTIFIER, ElixirFlexLexer.CALL_OR_KEYWORD_PAIR_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "!==",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.COMPARISON_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "===",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.COMPARISON_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "!=",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.COMPARISON_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "+",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.DUAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "-",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.DUAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "^^^",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.HAT_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "||",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.OR_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "|||",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.OR_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "~~~",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.UNARY_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "!",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.UNARY_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "^",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.UNARY_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<-",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IN_MATCH_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "\\\\",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.IN_MATCH_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                /* %{} is a special form and only appears in AST or a literal %{}.  It can't be a
                                   relative identifier. */
                                "%{}",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.STRUCT_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.OPENING_CURLY, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.CLOSING_CURLY, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "=",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.MATCH_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "*",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.MULTIPLICATION_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "/",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.MULTIPLICATION_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "|",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.PIPE_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<=",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                ">=",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "<",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                ">",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.RELATIONAL_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "->",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.STAB_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "%",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.STRUCT_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                /* {} is only used to construct tuples (it's not even a special form), so it's not a
                                   valid relative identifier */
                                "{}",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.OPENING_CURLY, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.CLOSING_CURLY, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "++",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.TWO_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        {
                                "--",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.TWO_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        },
                        /* "." would actually be parsed as ".." with the leading "." in reset */
                        {
                                "<>",
                                Arrays.asList(
                                        new TokenTypeState(ElixirTypes.TWO_OPERATOR, ElixirFlexLexer.CALL_MAYBE),
                                        new TokenTypeState(ElixirTypes.CALL, INITIAL_STATE),
                                        new TokenTypeState(ElixirTypes.OPENING_PARENTHESIS, INITIAL_STATE)
                                )
                        }
                }
        );
    }

    /*
     * Instance Methods
     */

    @org.junit.Test
    public void identifierCall() throws IOException {
        reset(identifierCharSequence);

        int lastState = ElixirFlexLexer.DOT_OPERATION;

        assertEquals(ElixirTypes.DOT_OPERATOR, flexLexer.advance());
        assertEquals(lastState, flexLexer.yystate());

        for (TokenTypeState tokenTypeState: tokenTypeStates) {
            assertEquals(tokenTypeState.tokenType, flexLexer.advance());
            assertEquals(tokenTypeState.state, flexLexer.yystate());
            lastState = tokenTypeState.state;
        }

        assertEquals(lastState, INITIAL_STATE);
    }

    @Override
    protected void reset(CharSequence charSequence) throws IOException {
        // append "." to trigger DOT_OPERATION
        // charSequence for relative identifier
        // append "(" to trigger CALL_OR_KEYWORD_PAIR_MAYBE
        CharSequence fullCharSequence = "." + charSequence + "(";
        super.reset(fullCharSequence);
    }
}
