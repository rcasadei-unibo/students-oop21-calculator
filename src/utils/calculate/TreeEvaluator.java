package utils.calculate;

/**
 *Evaluates every node of the AST.
 * @param <O>
 */
public interface TreeEvaluator<O> {

    /**
     * Starts the evaluation process.
     * @param root
     * @return the result of the entire expression
     */
    O evaluate(AbstractSyntaxNode root);

}
