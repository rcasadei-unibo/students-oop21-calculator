package utils.calculate;

import utils.ast.Operation;
import utils.ast.OperationsFactory;
import utils.tokens.Token;
import utils.tokens.SpecialToken;


/**
 * @author pesic
 *
 */
public class EvaluatorAST {

    @SuppressWarnings("unchecked")
    private Operation evaluateSubTree(final AbstractSyntaxNode node) {
        final Token t = node.getToken();
        switch (t.getTypeToken()) {
        case NUMBER:
            return OperationsFactory.constant(String.valueOf(((SpecialToken<Double>) t).getObjectToken()));
        case CONSTANT:
            return evaluateConstant(node);
        case VARIABLE:
            return OperationsFactory.simpleVar();
        case FUNCTION:
            return evaluateFunction(node);
        case OPERATOR:
            return evaluateOperator(node);
        default:
            throw new IllegalStateException("Invalid Token Expression");
        }
    }

    private Operation evaluateConstant(final AbstractSyntaxNode node) {
        final Token token = node.getToken();
        switch (token.getSymbol()) {
        case "pi":
            return OperationsFactory.constant(String.valueOf(Math.PI));
        case "e":
            return OperationsFactory.constant(String.valueOf(Math.E));
        default:
            throw new IllegalStateException("The constant doesn't exist");
        }

    }

    @SuppressWarnings("unchecked")
    private Operation evaluateFunction(final AbstractSyntaxNode node) {
        if (node.getRight().isEmpty()) {
            throw new IllegalStateException("Function needs arguments");
        }
        final Operation right = evaluateSubTree(node.getRight().get());
        final SpecialToken<Function> token = (SpecialToken<Function>) node.getToken();

        switch (token.getSymbol()) {
        case "acos":
            return OperationsFactory.acos(right);
        case "asin":
            return OperationsFactory.asin(right);
        case "atan":
            return OperationsFactory.atan(right);
        case "log":
            return OperationsFactory.log(right);
        case "cos":
            return OperationsFactory.cos(right);
        case "sin":
            return OperationsFactory.sin(right);
        case "sqrt":
            return OperationsFactory.sqrt(right);
        case "tan":
            return OperationsFactory.tan(right);
        case "exp":
            return OperationsFactory.exp(right);
        case "abs":
            return OperationsFactory.abs(right);
        default:
            throw new IllegalStateException("Function error");
        }
    }

    private Operation evaluateOperator(final AbstractSyntaxNode node) {

        if (node.getLeft().isPresent() && node.getRight().isPresent()) {
            return evaluateBinaryOperator(node);
        } else if (node.getRight().isPresent() && node.getLeft().isEmpty()) {
            return evaluateUnaryOperator(node);
        }

        throw new IllegalStateException("Error with operator: " + node.getToken().getSymbol() + " and node.left: "
                + node.getLeft().isPresent() + " and node.right: " + node.getRight().isPresent());
    }

    @SuppressWarnings("unchecked")
    private Operation evaluateUnaryOperator(final AbstractSyntaxNode node) {
        final Operation right = evaluateSubTree(node.getRight().get());
        final SpecialToken<Operator> token = (SpecialToken<Operator>) node.getToken();

        if (token.getSymbol().equals("-")) {
            return OperationsFactory.negate(right);
        }

        throw new IllegalStateException("Unary Operator doesn't work");
    }

    @SuppressWarnings("unchecked")
    private Operation evaluateBinaryOperator(final AbstractSyntaxNode node) {
        final Operation right = evaluateSubTree(node.getRight().get());
        final Operation left = evaluateSubTree(node.getLeft().get());
        final SpecialToken<Operator> token = (SpecialToken<Operator>) node.getToken();
        switch (token.getSymbol()) {
        case "+":
            return OperationsFactory.addition(left, right);
        case "-":
            return OperationsFactory.subtraction(left, right);
        case "*":
            return OperationsFactory.product(left, right);
        case "/":
            return OperationsFactory.division(left, right);
        case "^":
            return OperationsFactory.pow(left, right);
        default:
            throw new IllegalStateException("Unary Operator doesn't work");
        }
    }

    public Operation evaluate(AbstractSyntaxNode root) {
        if (root == null) {
            throw new IllegalStateException();
        }
        return evaluateSubTree(root);
    }

    public static void main(String[] args) {
        // (0)*((x)^(2.0))+(3.0)*(((x)^(2.0))*((0)*(log(x))+((2.0)*(1))/(x)))
        var parser = new ParserAST();
        var root = parser.parseToAST("sin(x)");
        var eval = new EvaluatorAST();
        var result = eval.evaluate(root);
        System.out.println("Result: " + result.getNumericResult(2.0));
    }

}
