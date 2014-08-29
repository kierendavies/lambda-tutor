package za.ac.uct.cs.ddd.lambda.evaluator;

/**
 * A token which stores its type, string content, and line and column in the original input.
 */
public class Token {
    private TokenType type;
    private int line, column;
    private String content;

    /**
     * Creates a new token.
     */
    public Token() {
    }

    /**
     * Creates a new token with no associated string.
     * @param type The token type
     * @param line The line it occurs at
     * @param column The column it occurs at
     */
    public Token(TokenType type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    /**
     * Creates a new token.
     * @param type The token type
     * @param line The line it occurs at
     * @param column The column it occurs at
     * @param content The string content
     */
    public Token(TokenType type, int line, int column, String content) {
        this(type, line, column);
        this.content = content;
    }

    /**
     * Returns the type of the token.
     * @return The type of the token
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the line at which the token occurred in the original input.
     * @return The line number
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the column at which the token occurred in the original input.
     * @return the column number
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the string contents of the token
     * @return The string contents
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the length of the token.
     * @return The length of the token
     */
    public int getLength() {
        if (type == TokenType.IDENTIFIER) {
            return content.length();
        } else {
            return 1;
        }
    }

    public boolean isEOF() {
        return type == TokenType.END_OF_FILE;
    }

    /**
     * Returns a detailed string representation of the token.
     * @return The string representation
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('<');
        builder.append(type.toString());
        if (content == null) {
            builder.append('>');
        } else {
            builder.append(" \"");
            builder.append(content);
            builder.append("\">");
        }
        return builder.toString();
    }
}
