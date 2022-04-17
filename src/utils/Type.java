package utils;
/**
 * Enumeration for the associativity type of the operator, that determines 
 * how operators of the same precedence are grouped in the absence of parentheses.
 */
public enum Type {
    /**
     * Left-associative operator.
     */
    LEFT, 
    /**
     * Right-associative operator.
     */
    RIGHT;
}
