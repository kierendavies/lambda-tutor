/*
 * Lambda Tutor
 * Copyright (C) 2014  Kieren Davies, David Dunn, Matthew Dunk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package za.ac.uct.cs.ddd.lambda.evaluator;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.BRACKETED_EXPRESSION;
import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.IDENTIFIER;

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
     *
     * @param type   The token type
     * @param line   The line it occurs at
     * @param column The column it occurs at
     */
    public Token(TokenType type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    /**
     * Creates a new token.
     *
     * @param type    The token type
     * @param line    The line it occurs at
     * @param column  The column it occurs at
     * @param content The string content
     */
    public Token(TokenType type, int line, int column, String content) {
        this(type, line, column);
        this.content = content;
    }

    /**
     * Returns the type of the token.
     *
     * @return The type of the token
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the line at which the token occurred in the original input.
     *
     * @return The line number
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the column at which the token occurred in the original input.
     *
     * @return the column number
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the string contents of the token
     *
     * @return The string contents
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the length of the token.
     *
     * @return The length of the token
     */
    public int getLength() {
        if (type == IDENTIFIER) {
            return content.length();
        } else if (type == BRACKETED_EXPRESSION) {
            throw new RuntimeException("Cannot get length of bracketed expression");
        } else {
            return 1;
        }
    }

    /**
     * Returns a detailed string representation of the token.
     *
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
