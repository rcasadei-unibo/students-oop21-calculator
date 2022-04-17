package utils.calculate;

import utils.ast.Operation;
import utils.ast.OperationsFactory;
import utils.tokens.Token;
import utils.tokens.SpecialToken;


/**
 * Evaluates every node of the AST using a post oder traversal of the tree.
 *https://mariusbancila.ro/blog/2009/02/06/evaluate-expressions-%e2%80%93-part-4-evaluate-the-abstract-syntax-tree/
 */
public class EvaluatorAST {

    /**it is called recursively until you reach the root of the tree.
     * @param node
     * @return given a node it returns all the calculation done with its children nodes
     */
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
            throw new IllegalArgumentException("Invalid Token Expression");
        }
    }

    /**
     * Evaluates a constant node.
     * @param node
     * @return a constant node
     */
    private Operation evaluateConstant(final AbstractSyntaxNode node) {
        final Token token = node.getToken();
        switch (token.getSymbol()) {
        case "pi":
            return OperationsFactory.constant(String.valueOf(Math.PI));
        case "e":
            return OperationsFactory.constant(String.valueOf(Math.E));
        default:
            throw new IllegalArgumentException("The constant doesn't exist");
        }

    }

    /**
     * Evaluates function given a node.
     * @param node
     * @return a result of the function
     */
    @SuppressWarnings("unchecked")
    private Operation evaluateFunction(final AbstractSyntaxNode node) {
        if (node.getRight().isEmpty()) {
            throw new IllegalArgumentException("Function needs arguments");
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
        case "√":
        case "sqrt":
            return OperationsFactory.sqrt(right);
        case "tan":
            return OperationsFactory.tan(right);
        case "exp":
            return OperationsFactory.exp(right);
        case "abs":
            return OperationsFactory.abs(right);
        case "csc":
            return OperationsFactory.csc(right);
        case "cot":
            return OperationsFactory.cot(right);
        case "sec":
            return OperationsFactory.sec(right);
        default:
            throw new IllegalArgumentException("Function error");
        }
    }

    /**
     * evaluates all operators.
     * @param node
     * @return the result of an operation
     */
    private Operation evaluateOperator(final AbstractSyntaxNode node) {

        if (node.getLeft().isPresent() && node.getRight().isPresent()) {
            return evaluateBinaryOperator(node);
        } else if (node.getRight().isPresent() && node.getLeft().isEmpty()) {
            return evaluateUnaryOperator(node);
        }

        throw new IllegalStateException("Error with operator: " + node.getToken().getSymbol() + " and node.left: "
                + node.getLeft().isPresent() + " and node.right: " + node.getRight().isPresent());
    }

    /**Evaluates unary operators(e.g. unaryminus , unaryplus).
     * @param node
     * @return a result of an operation
     */
    @SuppressWarnings("unchecked")
    private Operation evaluateUnaryOperator(final AbstractSyntaxNode node) {
        final Operation right = evaluateSubTree(node.getRight().get());
        final SpecialToken<Operator> token = (SpecialToken<Operator>) node.getToken();

        if ("-".equals(token.getSymbol())) {
            return OperationsFactory.negate(right);
        }

        throw new IllegalArgumentException("Unary Operator doesn't work");
    }

    /**
     * Evaluates only binary operators (e.g. + - ).
     * @param node
     * @return a result of an operation
     */
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
        case "\u00D7":
        case "*":
            return OperationsFactory.product(left, right);
        case "÷":
        case "/":
            return OperationsFactory.division(left, right);
        case "^":
            return OperationsFactory.pow(left, right);
        default:
            throw new IllegalArgumentException("Unary Operator doesn't work");
        }
    }

    /**
     * Starts the evaluation process.
     * @param root
     * @return the result of the entire expression
     */
    public Operation evaluate(final AbstractSyntaxNode root) {
        if (root == null) {
            throw new IllegalArgumentException();
        }
        return evaluateSubTree(root);
    }

}
