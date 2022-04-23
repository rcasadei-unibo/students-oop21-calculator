package controller.calculators;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.calculate.Algorithm;
import utils.calculate.Expression;
import utils.calculate.Derivate;
import utils.calculate.Limit;
import utils.calculate.Tokenizer;
import utils.tokens.TokenType;
import utils.NumberFormatter;

import utils.calculate.Integrator;

/**
 * The controller for the advanced calculator.
 *
 */
public class CalculatorAdvancedController {
    private Algorithm op;
    private final Expression expr = new Expression();
    private final List<String> displayBuffer = new LinkedList<>();
    private TypeAlgorithm type;
    private List<String> params = List.of();
    private CalculatorController controller;
    private String previousOp = "";
    private List<String> previousParams = List.of();
    private TypeAlgorithm previousType = TypeAlgorithm.DERIVATE;

    /**
     * The type of Operation.
     *
     */
    public enum TypeAlgorithm {
        /**
         * Takes the Algorithm interface as input.
         */
        DERIVATE(new Derivate()),
        /**
         * 
         */
        INTEGRATE(new Integrator()),
        /**
         * 
         */
        LIMIT(new Limit());

        private Algorithm alg;

        TypeAlgorithm(final Algorithm alg) {
            this.alg = alg;
        }

        /**
         * @return the given operation
         */
        public Algorithm getAlg() {
            return alg;
        }
    }

    /**
     * @param controller
     */
    public CalculatorAdvancedController(final CalculatorController controller) {
        this.controller = controller;
        this.expr.setEngine(new CCEngine(controller));
    }

    /**
     * Sets the operation.
     * 
     * @param typeOp
     */
    public void setOperation(final TypeAlgorithm typeOp) {
        this.type = typeOp;
        this.op = typeOp.getAlg();
        op.setEngine(new CCEngine(controller));
        this.reset();
    }

    /**
     * Return the previous Operation type.
     * 
     * @return s
     */
    public TypeAlgorithm getPreviousTypeOp() {
        return this.previousType;
    }

    /**
     * Return the previous parameters.
     * 
     * @return c
     */
    public List<String> getPreviousParameters() {
        return this.previousParams;
    }

    /**
     * @param c
     */
    public void read(final String c) {
        this.displayBuffer.add(c);
        this.controller.getManager().memory().read(c);
    }

    /**
     * @param c the string to read
     */
    public void readAll(final String c) {
        final var tok = new Tokenizer(c);
        final var l = tok.getListToken();
        final var ll = Stream.iterate(0, (i) -> i < l.size(), (i) -> i + 1).map(i -> {
            if (i < l.size() - 1
                    && (l.get(i).getTypeToken().equals(TokenType.FUNCTION) && l.get(i + 1).getTypeToken().equals(TokenType.OPENPAR)
                    || "^".equals(l.get(i).getSymbol()) && l.get(i + 1).getTypeToken().equals(TokenType.OPENPAR))) {
                return l.get(i).getSymbol() + l.get(i + 1).getSymbol();
            } else if (i > 0 
                    && (l.get(i).getTypeToken().equals(TokenType.OPENPAR) && l.get(i - 1).getTypeToken().equals(TokenType.FUNCTION) 
                    || l.get(i).getTypeToken().equals(TokenType.OPENPAR) && "^".equals(l.get(i - 1).getSymbol()))) {
                return "";
            } else {
                return l.get(i).getSymbol();
            }
        }).collect(Collectors.toList());
        ll.removeIf(s -> s.isBlank());
        ll.forEach(s -> this.read(s));
    }

    /**
     * Delete the last symbol inserted.
     */
    public void deleteLast() {
        if (!displayBuffer.isEmpty()) {
            displayBuffer.remove(displayBuffer.size() - 1);
        } else {
            this.controller.getManager().memory().clear();
        }
        this.controller.getManager().memory().deleteLast();
    }

    /**
     * @return The current state in input buffer
     */
    public String getCurrentState() {
        return this.controller.getManager().memory().getCurrentState().stream().reduce("", (s1, s2) -> s1 + s2);
    }

    /**
     * @return the displayBuffer
     */
    public String getCurrentDisplay() {
        return this.displayBuffer.stream().reduce("", (s1, s2) -> s1 + s2);
    }

    /**
     * 
     */
    public void reset() {
        this.op.unsetParameters();
        this.controller.getManager().memory().clear();
        this.displayBuffer.clear();
        this.params = List.of();
    }

    /**
     * Sets the parameters of the Operation.
     * 
     * @param params
     * @throws CalcException
     */
    public void setParameters(final List<String> params) {
        this.params = new LinkedList<>(params);
    }

    /**
     * Gives you back the previous Operation.
     * 
     * @return s
     */
    public String getPreviousOp() {
        return this.previousOp;
    }

    /**
     * adds to the history the expression = result.
     * 
     * @param oldExpr
     */
    public void addToHistory(final String oldExpr) {
        this.controller.getManager().memory().addResult(oldExpr);
    }

    /**
     * @return calculates the result given the operation
     * @throws CalcException
     */
    public String calculate() throws CalcException {
        final var e = this.controller.getManager().memory().getCurrentState().stream().reduce("", (res, s) -> res + s);
        this.expr.setExpr(e);
        this.previousOp = e;
        this.previousParams = this.params;
        this.previousType = this.type;
        this.op.setParameters(params);
        String res = this.op.calculate(expr);
        if (!this.type.equals(TypeAlgorithm.DERIVATE)) {
            res = NumberFormatter.format(Double.parseDouble(res), 8, 8, 8);
        }
        reset();
        return res;
    }

}
