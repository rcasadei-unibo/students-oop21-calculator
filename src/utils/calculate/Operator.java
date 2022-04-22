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
//took inspiration from 
//https://github.com/fasseg/exp4j/tree/master/src/main/java/net/objecthunter/exp4j/operator
package utils.calculate;

import java.util.Set;

/**
 * The Operator is used in reference for all valid operations binary and unary
 * and their precedence.
 *
 */
public class Operator {

    private enum Precedence {
        SUM(1), SUB(1), MUL(2), DIV(2), UNARYMINUS(3), UNARYPLUS(3), POW(4);

        private int value;

        Precedence(final int v) {
            this.value = v;
        }

        private int getPrecedence() {
            return this.value;
        }
    }

    /**
     * 
     */
    public static final Set<String> AVAILABLECHARS = Set.of("+", "-", "*", "/", "^", "÷", "\u00D7");

    private int numOperands;
    private boolean leftAssociative;
    private String symbol;
    private int precedence;

    /**
     * @param symbol
     * @param numberOfOperands
     * @param leftAssociative
     */
    public Operator(final String symbol, final int numberOfOperands, final boolean leftAssociative) {
        if (!isAllowedOperator(symbol)) {
            throw new IllegalArgumentException();
        }
        this.numOperands = numberOfOperands;
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = Operator.getPrecedences(symbol, numberOfOperands);
    }

    /**
     * @param s
     * @return true if the operator exists
     */
    public static boolean isAllowedOperator(final String s) {
        return AVAILABLECHARS.contains(s);
    }

    /**
     * if the class is left associative.
     * 
     * @return true if it is left associative , false otherwise
     */
    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    /**
     * @return the precedence
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * @return the symbol of he operator
     */
    public String getSymbol() {
        return symbol;
    }

    /** 
     * @return number of operands.
     */
    public int getNumOperands() {
        return numOperands;
    }

    /**
     * @param symbol
     * @param numArguments
     * @return given the symbol and the numOfArguments you get the precedence back
     */
    public static Integer getPrecedences(final String symbol, final int numArguments) {
        switch (symbol) {
        case "+":
            if (numArguments > 1) {
                return Precedence.SUM.getPrecedence();
            }
            return Precedence.UNARYPLUS.getPrecedence();

        case "-":
            if (numArguments > 1) {
                return Precedence.SUB.getPrecedence();
            }
            return Precedence.UNARYMINUS.getPrecedence();
        case "\u00D7":
        case "*":
            return Precedence.MUL.getPrecedence();
        case "÷":
        case "/":
            return Precedence.DIV.getPrecedence();
        case "^":
            return Precedence.POW.getPrecedence();
        default:
            return null;
        }
    }

    /**
     * @param symbol
     * @param numArguments
     * @return an operator in the base of their symbol and numArguments
     */
    public static Operator getOperatorBySymbolAndArgs(final String symbol, final int numArguments) {
        switch (symbol) {
        case "+":
            if (numArguments > 1) {
                return new Operator(symbol, numArguments, true);
            }
            return new Operator(symbol, numArguments, false);

        case "-":
            if (numArguments > 1) {
                return new Operator(symbol, numArguments, true);
            }
            return new Operator(symbol, numArguments, false);
        case "*":
        case "\u00D7":
            return new Operator("\u00D7", numArguments, true);
        case "÷":
        case "/":
            return new Operator("÷", numArguments, true);
        case "^":
            return new Operator(symbol, numArguments, false);
        default:
            return null;
        }
    }

}
