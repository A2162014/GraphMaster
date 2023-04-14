/**
 * The parser package provides classes for parsing mathematical expressions from text input.
 * <p>The classes in this package define a tokenizer, and a parser for converting a string of mathematical expressions
 * into a tree of expression objects.
 * The tree can then be evaluated to compute a result or displayed as a graph.
 * <p>The following classes are included in this package:
 * <p>Error: Represents an error encountered during parsing.
 * <p>ExpressionParser: Provides methods for parsing an input string into a tree of expression objects.
 * <p>Token: Represents a token produced by the tokenizer.
 * <p>TokenString: Represents a sequence of tokens produced by the tokenizer.
 * <p>TokenType: Enumerates the different types of tokens that the tokenizer can produce.
 * <p>The parser uses a combination of recursive descent parsing and operator precedence parsing to handle
 * the various types of expressions that can be encountered in mathematical notation.
 *
 * @since 2023–04-14
 */
package graphmaster.grapher.parser;
