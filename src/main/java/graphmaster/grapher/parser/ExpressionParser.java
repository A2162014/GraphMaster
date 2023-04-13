package graphmaster.grapher.parser;

import graphmaster.grapher.expressions.Number;
import graphmaster.grapher.expressions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpressionParser class is used to parse and evaluate mathematical expressions
 */
// Define the ExpressionParser class
public class ExpressionParser {

    // Declare instance variables
    private final Error error;
    private final Variable x;
    private final Variable y;
    private final Variable z;

    /**
     * Constructor to initialize the instance variables
     */
    // Define constructor
    public ExpressionParser() {
        error = new Error();
        x = new Variable();
        y = new Variable();
        z = new Variable();
    }

    /**
     * Checks if a given token is a boolean.
     *
     * @param prev the token to select
     * @return true if the token is boolean, false otherwise
     */
    private static boolean isaBoolean(Token prev) {
        return null == prev || !(TokenType.NUMBER == prev.type || TokenType.X == prev.type || TokenType.CLOSE_PARENTHESES == prev.type);
    }

    /**
     * Parses the input expression and returns a Function object
     *
     * @param expr the expression to parse
     * @return a Function object
     */
    // Define the parse method
    public final Function parse(String expr) {
        // Tokenize the expression
        TokenString tokens = tokenize(expr);

        // Check if tokens aren't null
        if (null != tokens) {
            // Check parentheses
            checkParentheses(tokens);
            // Substitute unary minus
            substituteUnaryMinus(tokens);
            // Perform order of operations
            Quantity root = doOrderOfOperations(tokens);
            // Check if root isn't null
            if (null == root) {
                error.makeError("Parsing of the function \"" + expr + "\" failed.");
                return null;
            }
            // Return a new Function object
            return new Function(root, x, y, z);
        }
        // Return null if tokenization fails
        error.makeError("Parsing of the function \"" + expr + "\" failed.");
        return null;
    }

    /**
     * Tokenizes the given mathematical expression.
     *
     * @param expr the mathematical expression to tokenize
     * @return a TokenString object containing the tokens that make up the expression.
     */
    private TokenString tokenize(String expr) {
        TokenString tkString = new TokenString();

        String name = "";
        String number = "";
        int numDecimals = 0;

        for (int i = 0; i < expr.length(); i++) {
            char currentChar = expr.charAt(i);
            boolean special = false;
            // select if the current character is alphabetical
            if (Character.isAlphabetic(currentChar)) {
                // if it is a variable name, add a token for the appropriate variable.
                if ('x' == currentChar) {
                    tkString.addToken(new Token(TokenType.X));
                } else if ('y' == currentChar) {
                    tkString.addToken(new Token(TokenType.Y));
                } else if ('z' == currentChar) {
                    tkString.addToken(new Token(TokenType.Z));
                } else {
                    // if it isn't a variable, add it to the function name.
                    name += currentChar;
                }
                special = true;
            } else if (!name.isEmpty()) {
                // if the current character isn't alphabetical and there is a function name, get the TokenType.
                TokenType type = getTokenTypeByName(name);
                if (null == type) {
                    // if the TokenType is null, return an error
                    error.makeError("The function name " + name + " isn't valid!");
                    return null;
                }
                // add the function TokenType to the TokenString
                tkString.addToken(new Token(type));
                name = "";
            }

            // select if the current character is a digit or decimal point.
            if (Character.isDigit(currentChar) || '.' == currentChar) {
                // if it is a decimal point, add it to the number if there hasn't been one already.
                if ('.' == currentChar) {
                    if (0 == numDecimals) number += '.';
                    numDecimals++;
                } else {
                    // if it is a digit, add it to the number
                    number += currentChar;
                }
                special = true;
            } else if (!number.isEmpty()) {
                // if the current character isn't a digit and there is a number, add it to the TokenString.
                tkString.addToken(new Token(TokenType.NUMBER, number));
                number = "";
                numDecimals = 0;
            }

            // select if the current character is a special character
            if (!(special || ' ' == currentChar)) {
                if ('(' == currentChar) tkString.addToken(new Token(TokenType.OPEN_PARENTHESES));
                else if (')' == currentChar) tkString.addToken(new Token(TokenType.CLOSE_PARENTHESES));
                else if (',' == currentChar) tkString.addToken(new Token(TokenType.COMMA));
                else if ('+' == currentChar) tkString.addToken(new Token(TokenType.PLUS));
                else if ('-' == currentChar) tkString.addToken(new Token(TokenType.MINUS));
                else if ('*' == currentChar) tkString.addToken(new Token(TokenType.TIMES));
                else if ('/' == currentChar) tkString.addToken(new Token(TokenType.DIVIDED_BY));
                else if ('^' == currentChar) tkString.addToken(new Token(TokenType.RAISED_TO));
                else if ('%' == currentChar) tkString.addToken(new Token(TokenType.MODULO));
                else {
                    // if the character isn't a valid special character, return an error.
                    error.makeError("The character '" + currentChar + "' it isn't allowed.");

                    return null;
                }
            }
        }
        if (!name.isEmpty()) {
            TokenType type = getTokenTypeByName(name); // Get the TokenType for the current function name
            if (null == type) {
                error.makeError("The function name '" + name + "' isn't valid!"); // Report an error if the function name is invalid
                return null; // Return null to show an error
            }
            tkString.addToken(new Token(type)); // Add a new token with the TokenType for the current function name.
            name = ""; // Reset the variable for storing function names
        }
        if (!number.isEmpty()) {
            tkString.addToken(new Token(TokenType.NUMBER, number));
            number = "";
            numDecimals = 0;
        }

        return tkString;
    }

    /**
     * Performs order of operations on a given TokenString.
     * <p>
     * Order of operations in reverse:
     * <p>
     * Addition, Subtraction, Division, Multiplication, Modulo, Exponentiation, Function, Parentheses, Variables, Numbers
     * <p>
     * All from right to left.
     *
     * @param tokens The TokenString to perform order of operations on.
     * @return The Quantity resulting from performing order of operations on the given TokenString.
     */
    private Quantity doOrderOfOperations(TokenString tokens) {
        int location = 0; // Location of some operator
        Quantity ret = new Number(0.0);

        // Perform addition operation
        location = scanFromRight(tokens, TokenType.PLUS);
        if (-1 == location) {
            // Perform subtraction operation
            location = scanFromRight(tokens, TokenType.MINUS);
            if (-1 == location) {
                // Perform division operation
                location = scanFromRight(tokens, TokenType.DIVIDED_BY);
                if (-1 == location) {
                    // Perform multiplication operation
                    location = scanFromRight(tokens, TokenType.TIMES);
                    if (-1 == location) {
                        // Perform modulo operation
                        location = scanFromRight(tokens, TokenType.MODULO);
                        if (-1 == location) {
                            // Perform exponentiation operation
                            location = scanFromRight(tokens, TokenType.RAISED_TO);
                            if (-1 == location) {
                                // Perform function operation
                                location = scanFromRight(tokens);
                                if (-1 != location) {
                                    int endParams = getFunctionParamsEnd(tokens, location + 2);
                                    if (-1 != endParams) {
                                        TokenString paramString = tokens.split(location + 2, endParams);
                                        ret = parseFunctionParams(paramString, tokens.tokenAt(location).type);
                                    }
                                } else if (2 <= tokens.getLength() && TokenType.CLOSE_PARENTHESES == tokens.tokenAt(tokens.getLength() - 1).type
                                        && TokenType.OPEN_PARENTHESES == tokens.tokenAt(0).type) {
                                    // Perform parentheses operation
                                    TokenString inParentheses = tokens.split(1, tokens.getLength() - 1);
                                    ret = doOrderOfOperations(inParentheses);
                                } else {
                                    location = scanFromRight(tokens, TokenType.X);
                                    if (-1 == location) {
                                        location = scanFromRight(tokens, TokenType.Y);
                                        if (-1 == location) {
                                            location = scanFromRight(tokens, TokenType.Z);
                                            if (-1 == location) {
                                                location = scanFromRight(tokens, TokenType.NUMBER);
                                                if (-1 != location) {
                                                    ret = new Number(Double.parseDouble(tokens.tokenAt(location).data));
                                                }
                                            } else {
                                                ret = z;
                                            }
                                        } else {
                                            ret = y;
                                        }
                                    } else {
                                        ret = x;
                                    }
                                }
                            } else {
                                TokenString left = tokens.split(0, location);
                                TokenString right = tokens.split(location + 1, tokens.getLength());
                                ret = new Power(doOrderOfOperations(left), doOrderOfOperations(right));
                            }
                        } else {
                            TokenString left = tokens.split(0, location);
                            TokenString right = tokens.split(location + 1, tokens.getLength());
                            ret = new Modulo(doOrderOfOperations(left), doOrderOfOperations(right));
                        }
                    } else {
                        TokenString left = tokens.split(0, location);
                        TokenString right = tokens.split(location + 1, tokens.getLength());
                        ret = new Product(doOrderOfOperations(left), doOrderOfOperations(right));
                    }
                } else {
                    TokenString left = tokens.split(0, location);
                    TokenString right = tokens.split(location + 1, tokens.getLength());
                    ret = new Quotient(doOrderOfOperations(left), doOrderOfOperations(right));
                }
            } else {
                TokenString left = tokens.split(0, location);
                TokenString right = tokens.split(location + 1, tokens.getLength());
                ret = new Difference(doOrderOfOperations(left), doOrderOfOperations(right));
            }
        } else {
            TokenString left = tokens.split(0, location);
            TokenString right = tokens.split(location + 1, tokens.getLength());
            ret = new Sum(doOrderOfOperations(left), doOrderOfOperations(right));
        }

        return ret;
    }

    /**
     * Parses the function parameters of a specified type from a token string.
     *
     * @param paramString The token string containing the parameters to parse.
     * @param type        The type of function to parse.
     * @return The parsed Quantity object.
     */
    private Quantity parseFunctionParams(TokenString paramString, TokenType type) {
        List<TokenString> params = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < paramString.getLength(); i++) {
            Token t = paramString.tokenAt(i);
            if (TokenType.COMMA == t.type) {
                params.add(paramString.split(start, i));
                start = i + 1;
            }
        }
        params.add(paramString.split(start, paramString.getLength()));

        if (params.isEmpty()) {
            return null;
        }

        if (1 == params.size()) {
            Quantity param1 = doOrderOfOperations(params.get(0));
            switch (type) {
                case ABSOLUTE_VALUE:
                    return new AbsoluteValue(param1);
                case CEILING:
                    return new Ceiling(param1);
                case FLOOR:
                    return new Floor(param1);
                case SINE:
                    return new Sine(param1);
                case COSINE:
                    return new Cosine(param1);
                case TANGENT:
                    return new Tangent(param1);
                case COTANGENT:
                    return new Cotangent(param1);
                case SECANT:
                    return new Secant(param1);
                case COSECANT:
                    return new Cosecant(param1);
                case SQUARE_ROOT:
                    return new SquareRoot(param1);
                default:
                    return null;
            }
        } else if (2 == params.size()) {
            Quantity param1 = doOrderOfOperations(params.get(0));
            Quantity param2 = doOrderOfOperations(params.get(1));
            switch (type) {
                case NTHROOT:
                    return new NthRoot(param1, param2);
                case LOG:
                    return new Log(param1, param2);
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * This method finds the index of the closing parentheses of the function parameters
     * given the starting index of the opening parentheses.
     *
     * @param tokens   The token string containing the function parameters.
     * @param location The index of the opening parentheses of the function parameters.
     * @return The index of the closing parentheses of the function parameters, or –1 if not found.
     */
    private int getFunctionParamsEnd(TokenString tokens, int location) {
        int openParentheses = 0;
        for (int i = location; i < tokens.getLength(); i++) {
            Token t = tokens.tokenAt(i);
            if (TokenType.OPEN_PARENTHESES == t.type) {
                openParentheses++;
            } else if (TokenType.CLOSE_PARENTHESES == t.type) {
                if (0 == openParentheses) {
                    return i;
                }
                openParentheses--;
            }
        }
        return -1;
    }

    /**
     * This method scans the token string from right to left and finds the index
     * of the given token type, taking into account any nested parentheses.
     *
     * @param tokens The token string to be scanned.
     * @param type   The token type to search for.
     * @return The index of the last occurrence of the given token type, or –1 if not found.
     */
    private int scanFromRight(TokenString tokens, TokenType type) {
        int openParentheses = 0;
        for (int i = tokens.getLength() - 1; 0 <= i; i--) {
            Token t = tokens.tokenAt(i);
            if (TokenType.CLOSE_PARENTHESES == t.type) {
                openParentheses++;
            } else if (TokenType.OPEN_PARENTHESES == t.type) {
                openParentheses--;
            } else if (t.type == type && 0 == openParentheses) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method scans the token string from right to left and finds the index
     * of the first occurrence of any function token type, taking into account any nested parentheses.
     *
     * @param tokens The token string to be scanned.
     * @return The index of the last occurrence of the given token type, or –1 if not found.
     */
    private int scanFromRight(TokenString tokens) {
        int openParentheses = 0;
        for (int i = tokens.getLength() - 1; 0 <= i; i--) {
            Token t = tokens.tokenAt(i);
            if (TokenType.CLOSE_PARENTHESES == t.type) {
                openParentheses++;
            } else if (TokenType.OPEN_PARENTHESES == t.type) {
                openParentheses--;
            } else {
                if (0 == openParentheses) {
                    for (TokenType type : TokenType.FUNCTIONS) {
                        if (t.type == type) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Replaces unary minus symbols with equal expressions.
     *
     * @param tokens the token string to change
     */
    private void substituteUnaryMinus(TokenString tokens) {
        Token prev = null;
        for (int i = 0; i < tokens.getLength(); i++) {
            Token t = tokens.tokenAt(i);
            if (TokenType.MINUS == t.type) {
                if (isaBoolean(prev)) {
                    // Replace -x with (0–1)*x
                    tokens.remove(i);
                    tokens.insert(i, new Token(TokenType.TIMES));
                    tokens.insert(i, new Token(TokenType.CLOSE_PARENTHESES));
                    tokens.insert(i, new Token(TokenType.NUMBER, "1"));
                    tokens.insert(i, new Token(TokenType.MINUS));
                    tokens.insert(i, new Token(TokenType.NUMBER, "0"));
                    tokens.insert(i, new Token(TokenType.OPEN_PARENTHESES));
                    i += 6;
                }
            }
            prev = t;
        }
    }

    /**
     * Checks if a token string has the correct number of parentheses.
     *
     * @param tokens the token string to select
     */
    private void checkParentheses(TokenString tokens) {
        // Test for correct number of parentheses
        int openParentheses = 0;
        for (int i = 0; i < tokens.getLength(); i++) {
            Token t = tokens.tokenAt(i);
            if (TokenType.OPEN_PARENTHESES == t.type) {
                openParentheses++;
            } else if (TokenType.CLOSE_PARENTHESES == t.type) {
                openParentheses--;
            }
            if (0 > openParentheses) {
                error.makeError("You closed too many parentheses.");
            }
        }
        if (0 < openParentheses) {
            error.makeError("You didn't close enough parentheses.");
        }
    }

    /**
     * Gets the TokenType associated with a given name.
     *
     * @param name the name of the TokenType to retrieve
     * @return the TokenType associated with the given name, or null if no match is found.
     */
    private TokenType getTokenTypeByName(String name) {
        TokenType[] values = TokenType.FUNCTIONS;
        for (TokenType v : values) {
            if (v.name.equals(name)) return v;
        }
        return null;
    }
}
