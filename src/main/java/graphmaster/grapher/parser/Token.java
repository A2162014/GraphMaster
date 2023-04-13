package graphmaster.grapher.parser;

/**
 * The Token class represents a token with its type and data.
 */
// class definition
public class Token {
    /**
     * The data associated with the token.
     */
    // public final field of String data
    public final String data;
    // a final field of TokenType type
    final TokenType type;

    // constructor with TokenType and String parameters
    Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    // constructor with only TokenType parameter, which calls the earlier constructor
    Token(TokenType type) {
        this(type, "");
    }

    // toString method override to return TokenType as string
    @Override
    public String toString() {
        return type.toString();
    }
}