/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//https://github.com/fasseg/exp4j/blob/master/src/main/java/net/objecthunter/exp4j/tokenizer/Tokenizer.java 
//modified some part of the code of the tokenizer


package utils.calculate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.tokens.SpecialToken;
import utils.tokens.Token;
import utils.tokens.TokenType;
import utils.tokens.TokensFactory;

/**
 * Given a string or List<String>  tokenize every recognizable string to a token (e.g. x-> VARIABLE, sin -> FUNCTION)
 * it supports implicit multiplication (e.g. 3x -> 3*x)
 *
 */

public class Tokenizer {

    private String expr;
    private int index = 0;
    private Token lastToken = null;
    private  int lenExpr;
    private boolean isRPN = false;
    private final String variable;
    private final Map<String, Double> constants;
    private boolean implicitMultiplication = true;
    private final ExternData data = new ExternData();

    /**
     * @param expr
     */
    public Tokenizer(final String expr) {
        this.expr = expr.replaceAll(" ", "");
        this.lenExpr = expr.replaceAll(" ", "").length();
        this.variable = data.getVariable();
        this.constants = data.getConstants();
    }

    /**
     * @return true if arrived at the end of the string
     */
    public boolean hasNextToken() {
        return this.index <= this.lenExpr - 1;
    }

    /**
     * Gives you back the token associated with the char or characters in the string or lis of strings.
     * @return Token
     */
    public Token getNextToken() {
        if (!hasNextToken()) {
            return null;
        }
        final char c = this.expr.charAt(index);
        if (Character.isDigit(c)) {
            if (lastToken != null) {
                if (lastToken.getTypeToken().equals(TokenType.NUMBER) && !isRPN) {
                    throw new IllegalArgumentException("2 numbers can't stay near: " + c);
                }
                if (implicitMultiplication && lastToken.getTypeToken() != TokenType.OPENPAR && lastToken.getTypeToken() != TokenType.FUNCTION
                        && lastToken.getTypeToken() != TokenType.OPERATOR) {
                    lastToken = TokensFactory.operatorToken(new Operator("\u00D7", 2, true));

                    return lastToken;
                }
            }
            return getNumberToken();
        } else if (c == '(') {
            if (lastToken != null
                && implicitMultiplication
                && lastToken.getTypeToken() != TokenType.OPENPAR && lastToken.getTypeToken() != TokenType.OPERATOR
                && lastToken.getTypeToken() != TokenType.FUNCTION) {
                    lastToken = TokensFactory.operatorToken(new Operator("\u00D7", 2, true));

                    return lastToken;
             }
            index++;
            return TokensFactory.openParToken();
        } else if (c == ')') {
            index++;
            return TokensFactory.closeParToken();
        } else if (Operator.isAllowedOperator(String.valueOf(c))) {
            return getOperationToken();
        } else if (Character.isLetter(c) || c == '√') {
            if (lastToken != null) {
                if (implicitMultiplication && lastToken.getTypeToken() != TokenType.OPERATOR && lastToken.getTypeToken() != TokenType.FUNCTION
                        && lastToken.getTypeToken() != TokenType.OPENPAR) {
                    lastToken = TokensFactory.operatorToken(Operator.getOperatorBySymbolAndArgs("\u00D7", 2));

                    return lastToken;
                }
            }
            return getFunctionOrVariableToken();
        }
        throw new IllegalArgumentException("the character: " + c + " wasn't recognized ");
    }

    /**
     * @return a List of token of the expression set
     */
    public List<Token> getListToken() {
        final List<Token> out = new LinkedList<>();
        while (this.hasNextToken()) {
            out.add(this.getNextToken());
        }
        return out;
    }

    /**
     * @return It returns a List of stings with every string being the symbol of the associated token.
     */
    public List<String> getListSymbol() {
        final List<Token> out = this.getListToken();
        return out.stream().map(t -> t.getSymbol()).collect(Collectors.toList());
    }

    /**Sets the implicit multiplication .
     * @param flag
     */
    private void setImplicitMultiplication(final boolean flag) {
        this.implicitMultiplication = flag;
    }

    /**Given A List of symbols it convert them to a list of tokens (e.g. 3, +, x -> NUMBER, OPERATOR, VARIABLE)
     * @param expression
     * @return a List of token
     */
    public List<Token> convertToTokens(final List<String> expression) {
        final List<Token> out = new LinkedList<>();
        this.setImplicitMultiplication(false);
        expression.forEach(s -> {
            this.reset(s, true, true);
            out.addAll(this.getListToken());
        });
        this.setImplicitMultiplication(true);
        return out;
    }

    /**
     * @param expr
     * @param residualExpression
     * @param isRPN
     */
    public void reset(final String expr, final boolean residualExpression, final boolean isRPN) {
        this.index = 0;
        if (!residualExpression) {
            this.lastToken = null;
        }
        this.isRPN = isRPN;
        this.expr = expr;
        this.lenExpr = expr.length();
    }

    /**
     * Given a number in exponential format or In decimal format(with or without floating point) it converts the whole in token.
     * @return a Number Token
     */
    private Token getNumberToken() {
        double num;
        final int ind = this.index;
        while (index < this.lenExpr && Character.isDigit(this.expr.charAt(index))) {
            index++;
        }
        if (index < this.lenExpr && this.expr.charAt(index) == '.') {
            index++;
        }
        while (index < this.lenExpr && ((Character.isDigit(this.expr.charAt(index)) && this.expr.charAt(index - 1) != 'E') 
                || this.expr.charAt(index) == 'E'
                || ((this.expr.charAt(index - 1) == 'E') && (this.expr.charAt(index) == '+' || this.expr.charAt(index) == '-')))) {
            index++;
        }

        if (this.index - ind == 0) {
            num = Double.parseDouble(this.expr.substring(ind));
        } else {
            num = Double.parseDouble(this.expr.substring(ind, index));
        }

        final var number = TokensFactory.numberToken(num);
        lastToken = number;
        if (this.index == this.lenExpr - 1 && ind == this.index) {
            index++;
        }
        return number;
    }

    /**Given an Alphanumeric String verify the existence of the function or the variable.
     * @return a variable or a function token
     */
    private Token getFunctionOrVariableToken() {
        int newIndex = index;
        int previousIndex = -1;
        char c = this.expr.charAt(newIndex);
        Token newToken = null;

        while (newIndex <= this.lenExpr - 1 && (Character.isLetter(c) ||  c == '√')) {
            if (newIndex - index == 0) {
                if (String.valueOf(c).equals(this.variable)) {
                    newToken = TokensFactory.variableToken(this.variable);
                    previousIndex = newIndex + 1;
                } else if (constants.containsKey(String.valueOf(c))) {
                    newToken = TokensFactory.numberToken(constants.get(String.valueOf(c)));
                    previousIndex = newIndex + 1;
                } else if (Function.isFunction(String.valueOf(c))) {
                    newToken = TokensFactory.functionToken(Function.DICTFUNCTIONS.get(String.valueOf(c)));
                    previousIndex = newIndex + 1;
                }
            } else {
                if (this.expr.substring(index, newIndex + 1).equals(this.variable)) {
                    newToken = TokensFactory.variableToken(this.variable);
                    previousIndex = newIndex + 1;
                } else if (Function.isFunction(this.expr.substring(index, newIndex + 1))) {
                    newToken = TokensFactory.functionToken(Function.DICTFUNCTIONS.get(this.expr.substring(index, newIndex + 1)));
                    previousIndex = newIndex + 1;
                } else if (constants.containsKey(this.expr.substring(index, newIndex + 1))) {
                    newToken = TokensFactory.numberToken(constants.get(this.expr.substring(index, newIndex + 1)));
                    previousIndex = newIndex + 1;
                }
            }
            newIndex++;
            if (newIndex <= this.lenExpr - 1) {
                c = this.expr.charAt(newIndex);
            }
        }
        if (previousIndex != -1) {
            this.index = previousIndex;

        } else {
            this.index = newIndex;
        }
        if (newToken == null) {
            throw new IllegalArgumentException("The variable name or the function doesn't exist");
        }
        lastToken = newToken;
        return newToken;

    }

    /**
     * @return a operator Token
     */
    private Token getOperationToken() {
        final char c = this.expr.charAt(this.index++);
        int arguments = 2;

        if (lastToken == null) {
            arguments = 1;
        } else if (!isRPN) {
            if (lastToken.getTypeToken() == TokenType.OPENPAR) {
                arguments = 1;
            } else if (lastToken.getTypeToken() == TokenType.OPERATOR) {
                @SuppressWarnings("unchecked")
                final Operator op = ((SpecialToken<Operator>) lastToken).getObjectToken();
                if (op.getNumOperands() == 2 || op.getNumOperands() == 1 && !op.isLeftAssociative()) {
                    arguments = 1;
                }
            }
        }

        final var newOp = Operator.getOperatorBySymbolAndArgs(String.valueOf(c), arguments);
        lastToken = TokensFactory.operatorToken(newOp);
        return lastToken;
    }
}
