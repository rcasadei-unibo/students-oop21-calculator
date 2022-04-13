package utils.ast;

/**
 * @author pesic
 *
 */
public class OperationsFactory {

    public static Operation sin(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation cos(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
                return Math.cos(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0.0"),product(sin(op), op.getDerivative()));
            }

            @Override
            public String toString() {
                return "cos(" + op.toString() + ")";
            }

        };
    }
    
    public static Operation cot(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
                return Math.cos(op.getNumericResult(val))/Math.sin(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return product(subtraction(constant("0"), division(constant("1"), pow(sin(op), constant("2")))), op.getDerivative());
            }
            
            public String toString() {
                return "cot(" + op.toString() + ")";
            }
            
        };
    }
    
    public static Operation csc(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
                return 1.0/Math.sin(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0"),product(product(cot(op),csc(op)), op.getDerivative()));
            }
            
            public String toString() {
                return "csc(" + op.toString() + ")";
            }
        };

    }
    
    public static Operation sec(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
                return 1.0/ Math.cos(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return product(product(tan(op), sec(op)),op.getDerivative());
            }

            public String toString() {
                return "sec(" + op.toString() + ")";
            }
        };
    }

    public static Operation negate(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation addition(Operation left, Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation subtraction(Operation left, Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation product(Operation left, Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation division(Operation left, Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation constant(String c) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation pow(Operation left, Operation right) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation log(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation abs(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation acos(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(final Double val) {
                return Math.acos(op.getNumericResult(val));
            }

            @Override
            public Operation getDerivative() {
                return subtraction(constant("0.0"),division(op.getDerivative(), sqrt(subtraction(constant("1"), pow(op, constant("2"))))));
            }

            public String toString() {
                return "acos(" + op.toString() + ")";
            }
        };
    }

    public static Operation asin(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation atan(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation exp(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation simpleVar() {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
                if (val == null)
                    throw new NullPointerException("Variable set to null!");
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

    public static Operation sqrt(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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

    public static Operation tan(Operation op) {
        return new Operation() {

            @Override
            public Double getNumericResult(Double val) {
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
