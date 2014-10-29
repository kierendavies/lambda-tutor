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

/**
 * Miscellaneous useful functions.
 */
class Util {
    /**
     * Given a variable name, increments it to produce a new variable name with similar meaning.
     *
     * @param name The original name
     * @return The new name
     */
    static String nextVariableName(String name) {
        if (name.endsWith("\u2032")) {
            return name.substring(0, name.length() - 1) + "\u2033";
        } else if (name.endsWith("\u2033")) {
            return name.substring(0, name.length() - 1) + "\u2034";
        } else {
            return name + "\u2032";
        }
    }
}
