package graphmaster.grapher.parser;

/**
 * This code defines an enumeration of TokenType,
 * which are used to represent the different types of tokens in the input expression.
 */
public enum TokenType {
    /**
     * Represents an opening parenthesis token.
     */
    OPEN_PARENTHESES("("),

    /**
     * Represents a closing parenthesis token.
     */
    CLOSE_PARENTHESES(")"),

    /**
     * Represents an addition token.
     */
    PLUS("+"),

    /**
     * Represents a subtraction token.
     */
    MINUS("-"),

    /**
     * Represents a multiplication token.
     */
    TIMES("*"),

    /**
     * Represents a division token.
     */
    DIVIDED_BY("/"),

    /**
     * Represents a power token.
     */
    RAISED_TO("^"),

    /**
     * Represents a sine function token.
     */
    SINE("sin"),

    /**
     * Represents a cosine function token.
     */
    COSINE("cos"),

    /**
     * Represents a tangent function token.
     */
    TANGENT("tan"),

    /**
     * Represents a cotangent function token.
     */
    COTANGENT("cot"),

    /**
     * Represents a secant function token.
     */
    SECANT("sec"),

    /**
     * Represents a cosecant function token.
     */
    COSECANT("csc"),

    /**
     * Represents a ceiling function token.
     */
    CEILING("ceil"),

    /**
     * Represents a floor function token.
     */
    FLOOR("floor"),

    /**
     * Represents a logarithm function token.
     */
    LOG("log"),

    /**
     * Represents a modulo operation token.
     */
    MODULO("%"),

    /**
     * Represents a nth root function token.
     */
    NTHROOT("nthroot"),

    /**
     * Represents a square root function token.
     */
    SQUARE_ROOT("sqrt"),

    /**
     * Represents an absolute value function token.
     */
    ABSOLUTE_VALUE("abs"),

    /**
     * Represents a comma token.
     */
    COMMA(","),

    /**
     * Represents a variable x token.
     */
    X("x"),

    /**
     * Represents a variable y token.
     */
    Y("x"),

    /**
     * Represents a variable z token.
     */
    Z("z"),

    /**
     * Represents a number token.
     */
    NUMBER("");

    /**
     * An array of functions defined as token types in this enumeration.
     */
    static final TokenType[] FUNCTIONS = {
            SINE, COSINE, TANGENT, COTANGENT, SECANT, COSECANT, SQUARE_ROOT,
            CEILING, FLOOR, LOG, MODULO, ABSOLUTE_VALUE, NTHROOT
    };

    /**
     * The name of each token type is stored as a string.
     */
    public final String name;

    /**
     * Initializes the name of each token type.
     *
     * @param name the name of the token type
     */
    TokenType(String name) {
        this.name = name;
    }

}