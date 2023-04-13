package graphmaster.grapher.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * The TokenString class represents a list of tokens.
 */
// Definition of class TokenString
public class TokenString {
    // A private field to hold the list of tokens
    private final List<Token> tokens;

    /**
     * Constructor to initialize the list of tokens.
     */
    public TokenString() {
        tokens = new ArrayList<>();
    }

    // Private constructor to create a new TokenString with an existing list of tokens.
    private TokenString(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Method to add a token to the list.
     *
     * @param token The token to add to the list.
     */
    public void addToken(Token token) {
        tokens.add(token);
    }

    /**
     * Method to get the token at a specific index in the list.
     *
     * @param i The index of the token to get.
     * @return The token at the specified index.
     */
    // Method to get the token at a specific index in the list.
    public Token tokenAt(int i) {
        return tokens.get(i);
    }

    /**
     * Method to get the length of the list.
     *
     * @return The length of the list.
     */
    // Method to get the length of the list
    public int getLength() {
        return tokens.size();
    }

    /**
     * Method to split the list into a new TokenString between two indexes.
     *
     * @param start The index to start the sub-list at.
     * @param stop  The index to end the sub-list at.
     * @return The new TokenString containing the sub-list of tokens.
     */
    // Method to split the list into a new TokenString between two indexes.
    public TokenString split(int start, int stop) {
        // Ensure the start index isn't less than 0
        int max = Math.max(0, start);
        // Ensure stop index isn't greater than the size of the list.
        int min = Math.min(tokens.size(), stop);

        // Create a new list to hold the sub-list of tokens
        List<Token> subList = new ArrayList<>();
        // Iterate through the tokens between the start and stop indexes and add them to the sub-list.
        for (int i = max; i < min; i++) {
            subList.add(tokens.get(i));
        }
        // Create a new TokenString with the sub-list of tokens and return it.
        return new TokenString(subList);
    }

    /**
     * Method to insert a token at a specific index in the list.
     *
     * @param i     The index to insert the token at.
     * @param token The token to insert.
     */
    // Method to insert a token at a specific index in the list.
    public void insert(int i, Token token) {
        tokens.add(i, token);
    }

    /**
     * Method to remove a token from a specific index in the list.
     *
     * @param i The index of the token to remove.
     */
    // Method to remove a token from a specific index in the list.
    public void remove(int i) {
        tokens.remove(i);
    }

    // Method to convert the TokenString to a string for printing
    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        // Iterate through the list of tokens and append their string representation to the line.
        for (Token t : tokens) {
            line.append(t.toString());
            // If the token has data, append it in angle brackets
            if (!t.data.isEmpty()) line.append("<").append(t.data).append(">");
            line.append(" ");
        }
        // Return the final line
        return line.toString();
    }
}