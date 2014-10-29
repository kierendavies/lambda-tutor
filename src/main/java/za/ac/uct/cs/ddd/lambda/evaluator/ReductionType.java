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

public enum ReductionType {
    NONE, ALPHA, ALPHA_CA, BETA, ETA;
    // ALPHA_CA is internal only, to signal an alpha conversion to avoid variable capture

    @Override
    public String toString() {
        switch (this) {
            case ALPHA:
            case ALPHA_CA:
                return "\u03b1";
            case BETA:
                return "\u03b2";
            case ETA:
                return "\u03b7";
            default:
                return " ";
        }
    }
}
