package utils.tokens;

import utils.calculate.Function;
import utils.calculate.Operator;

/**
 * @author pesic
 *
 */
public class TokensFactory {
    public static Token closeParToken() {
        return new Token() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.CLOSEPAR;
            }

            @Override
            public String getSymbol() {
                return ")";
            }

        };
    }

    public static Token openParToken() {
        return new Token() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.OPENPAR;
            }

            @Override
            public String getSymbol() {
                return "(";
            }

        };
    }

    public static Token functionToken(Function func) {
        return new SpecialToken<Function>() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.FUNCTION;
            }

            @Override
            public String getSymbol() {
                return func.getName();
            }

            @Override
            public Function getObjectToken() {
                return func;
            }

        };
    }

    public static Token numberToken(double value) {
        return new SpecialToken<Double>() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.NUMBER;
            }

            @Override
            public String getSymbol() {
                return String.valueOf(value);
            }

            @Override
            public Double getObjectToken() {
                return value;
            }

        };
    }

    public static Token constantToken(String constant) {
        return new Token() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.CONSTANT;
            }

            @Override
            public String getSymbol() {
                return constant;
            }

        };
    }

    public static Token operatorToken(Operator operator) {
        return new SpecialToken<Operator>() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.OPERATOR;
            }

            @Override
            public String getSymbol() {
                return operator.getSymbol();
            }

            @Override
            public Operator getObjectToken() {
                return operator;
            }

        };
    }

    public static Token variableToken(String variable) {
        return new Token() {

            @Override
            public TokenType getTypeToken() {
                return TokenType.VARIABLE;
            }

            @Override
            public String getSymbol() {
                return variable;
            }

        };
    }
}
