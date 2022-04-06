package utils.calculate;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import utils.tokens.SpecialToken;
import utils.tokens.Token;
import utils.tokens.TokenType;


/**
 * @author pesic
 *
 */
public class ShuntingYardAlgorithm {

    private Stack<Token> operators;
    private List<Token> output;
    private Tokenizer tok;

    @SuppressWarnings("unchecked")
    private void pushOperator(SpecialToken<Operator> op) {
        if (operators.isEmpty()) {
            operators.push(op);
            return;
        }
        while (operators.size() > 0
                && (operators.peek().getTypeToken().equals(TokenType.OPERATOR) && ((SpecialToken<Operator>) operators.peek())
                        .getObjectToken().getPrecedence() >= op.getObjectToken().getPrecedence())) {
            output.add(operators.pop());
        }
        operators.push(op);
    }

    private void closeParanthesisPop() {
        if (operators.isEmpty()) {
            throw new IllegalStateException("No open Paranthesis found");
        }
        boolean openParFound = false;
        while (operators.size() > 0 && !openParFound) {
            if (operators.peek().getTypeToken().equals(TokenType.OPENPAR)) {
                openParFound = true;
                operators.pop();
            } else {
                output.add(operators.pop());
            }
        }
        if (!openParFound) {
            throw new IllegalStateException("No open Paranthesis found");
        }
        if (!operators.isEmpty() && operators.peek().getTypeToken().equals(TokenType.FUNCTION)) {
            output.add(operators.pop());
        }
    }

    @SuppressWarnings("unchecked")
    private void popUnaryOperation(SpecialToken<Operator> op) {
        while (operators.size() > 0 && operators.peek().getTypeToken().equals(TokenType.OPERATOR)
                && ((SpecialToken<Operator>) operators.peek()).getObjectToken().getNumOperands() == 1
                && !((SpecialToken<Operator>) operators.peek()).getObjectToken().isLeftAssociative()
                && ((SpecialToken<Operator>) operators.peek()).getObjectToken().getPrecedence() >= op.getObjectToken()
                        .getPrecedence()) {
            output.add(operators.pop());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Token> getReversedPolishedNotation(String expression) {
        tok = new Tokenizer(expression);
        operators = new Stack<>();
        output = new LinkedList<>();

        while (tok.hasNextToken()) {
            final Token t = tok.getNextToken();
            System.out.println(t.getTypeToken());
            if (t.getTypeToken().equals(TokenType.NUMBER) || t.getTypeToken().equals(TokenType.VARIABLE)
                    || t.getTypeToken().equals(TokenType.CONSTANT)) {
                output.add(t);
            } else if (t.getTypeToken().equals(TokenType.OPERATOR)) {
                final SpecialToken<Operator> opT = (SpecialToken<Operator>) t;
                if (!opT.getObjectToken().isLeftAssociative() && opT.getObjectToken().getNumOperands() == 1) {
                    popUnaryOperation(opT);
                    operators.push(opT);
                } else {
                    pushOperator(opT);
                }
            } else if (t.getTypeToken().equals(TokenType.OPENPAR) || t.getTypeToken().equals(TokenType.FUNCTION)) {
                operators.push(t);
            } else if (t.getTypeToken().equals(TokenType.CLOSEPAR)) {
                closeParanthesisPop();
            }
        }
        if (operators.size() > 0) {
            while (!operators.isEmpty()) {
                output.add(operators.pop());
            }
        }

        return output;
    }

    public static void main(String[] args) {
        var alg = new ShuntingYardAlgorithm();
        var li = alg.getReversedPolishedNotation("xpi");
        li.stream().map(t -> t.getSymbol()).forEach(tt -> System.out.println("token: " + tt));
    }

}
