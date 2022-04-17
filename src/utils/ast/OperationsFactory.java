package utils.ast;

/**
 * Factory of Operations .
 *
 */
//https://github.com/Ivan-Capponi/Tesi_di_Laurea/tree/master/Derivate/src/ast
public final class OperationsFactory {

    private OperationsFactory() {
    }

    /**
     * @param op
     * @return sin
     */
    public static Operation sin(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.sin(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return OperationsFactory.product(OperationsFactory.cos(op), op.getDerivative());
            }

            public String toString() {
                return "sin(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return cos
     */
    public static Operation cos(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.cos(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0.0"), product(sin(op), op.getDerivative()));
            }

            @Override
            public String toString() {
                return "cos(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return cot
     */
    public static Operation cot(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.cos(op.getNumericResult(val)) / Math.sin(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return product(subtraction(constant("0"), division(constant("1"), pow(sin(op), constant("2")))),
                        op.getDerivative());
            }

            public String toString() {
                return "cot(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return csc
     */
    public static Operation csc(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return 1.0 / Math.sin(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0"), product(product(cot(op), csc(op)), op.getDerivative()));
            }

            public String toString() {
                return "csc(" + op.toString() + ")";
            }
        };

    }

    /**
     * @param op
     * @return sec
     */
    public static Operation sec(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return 1.0 / Math.cos(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return product(product(tan(op), sec(op)), op.getDerivative());
            }

            public String toString() {
                return "sec(" + op.toString() + ")";
            }
        };
    }

    /**
     * @param op
     * @return negate
     */
    public static Operation negate(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return -op.getNumericResult(val);
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0.0"), op.getDerivative());
            }

            public String toString() {
                return "-" + op.toString();
            }

        };
    }

    /**
     * @param left
     * @param right
     * @return addition
     */
    public static Operation addition(final Operation left, final Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return left.getNumericResult(val) + right.getNumericResult(val);
            }

            @Override
            public Operation getDerivative() {
                return OperationsFactory.addition(left.getDerivative(), right.getDerivative());
            }

            @Override
            public String toString() {
                return "(" + left.toString() + ")+(" + right.toString() + ")";
            }
        };
    }

    /**
     * @param left
     * @param right
     * @return subtraction
     */
    public static Operation subtraction(final Operation left, final Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return left.getNumericResult(val) - right.getNumericResult(val);
            }

            @Override
            public Operation getDerivative() {
                return OperationsFactory.subtraction(left.getDerivative(), right.getDerivative());
            }

            @Override
            public String toString() {
                return "(" + left.toString() + ")-(" + right.toString() + ")";
            }

        };
    }

    /**
     * @param left
     * @param right
     * @return product
     */
    public static Operation product(final Operation left, final Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return left.getNumericResult(val) * right.getNumericResult(val);
            }

            @Override
            public Operation getDerivative() {
                return addition(product(left.getDerivative(), right), product(left, right.getDerivative()));
            }

            @Override
            public String toString() {
                return "(" + left.toString() + ")×(" + right.toString() + ")";
            }

        };
    }

    /**
     * @param left
     * @param right
     * @return division
     */
    public static Operation division(final Operation left, final Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return left.getNumericResult(val) / right.getNumericResult(val);
            }

            @Override
            public Operation getDerivative() {
                return division(subtraction(product(left.getDerivative(), right), product(left, right.getDerivative())),
                        pow(right, constant("2")));
            }

            @Override
            public String toString() {
                return "(" + left.toString() + ")÷(" + right.toString() + ")";
            }
        };
    }

    /**
     * @param c
     * @return constant
     */
    public static Operation constant(final String c) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Double.parseDouble(c);
            }

            @Override
            public Operation getDerivative() {
                return constant("0");
            }

            public String toString() {
                return c;
            }

        };
    }

    /**
     * @param left
     * @param right
     * @return power
     */
    public static Operation pow(final Operation left, final Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.pow(left.getNumericResult(val), right.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                final var firstTerm = pow(left, right);
                final var secondTerm = product(right.getDerivative(), log(left));
                final var thirdTerm = division(product(right, left.getDerivative()), left);
                return product(firstTerm, addition(secondTerm, thirdTerm));
            }

            @Override
            public String toString() {
                return "(" + left.toString() + ")^(" + right.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return log
     */
    public static Operation log(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.log(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return division(op.getDerivative(), op);
            }

            public String toString() {
                return "log(" + op.toString() + ")";
            }
        };
    }

    /**
     * @param op
     * @return absolute value
     */
    public static Operation abs(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.abs(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return division(product(op, op.getDerivative()), abs(op));
            }

            public String toString() {
                return "abs(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return acos
     */
    public static Operation acos(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.acos(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0.0"),
                        division(op.getDerivative(), sqrt(subtraction(constant("1"), pow(op, constant("2"))))));
            }

            public String toString() {
                return "acos(" + op.toString() + ")";
            }
        };
    }

    /**
     * @param op
     * @return asin
     */
    public static Operation asin(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.asin(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return division(op.getDerivative(), sqrt(subtraction(constant("1"), pow(op, constant("2")))));
            }

            public String toString() {
                return "asin(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return atan
     */
    public static Operation atan(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.atan(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return division(op.getDerivative(), addition(constant("1"), pow(op, constant("2"))));
            }

            public String toString() {
                return "atan(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return exp
     */
    public static Operation exp(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.exp(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return product(exp(op), op.getDerivative());
            }

            public String toString() {
                return "e^(" + op.toString() + ")";
            }

        };
    }

    /**
     * @return a variable
     */
    public static Operation simpleVar() {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return val;
            }

            @Override
            public Operation getDerivative() {
                return constant("1");
            }

            public String toString() {
                return "x";
            }

        };
    }

    /**
     * @param op
     * @return sqrt
     */
    public static Operation sqrt(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.sqrt(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return division(op.getDerivative(), product(constant("2"), sqrt(op)));
            }

            public String toString() {
                return "√(" + op.toString() + ")";
            }

        };
    }

    /**
     * @param op
     * @return tan
     */
    public static Operation tan(final Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.tan(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return division(op.getDerivative(), pow(cos(op), constant("2")));
            }

            public String toString() {
                return "tan(" + op.toString() + ")";
            }
        };
    }

}
