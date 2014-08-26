package za.ac.uct.cs.ddd.lambda.evaluator;

public class Token {
    private TokenType type;
    private int line, column;
    private String content;

    public Token() {
    }

    public Token(TokenType type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public Token(TokenType type, int line, int column, String content) {
        this(type, line, column);
        this.content = content;
    }

    public TokenType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getContent() {
        return content;
    }

    public boolean isEOF() {
        return type == TokenType.END_OF_FILE;
    }

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
