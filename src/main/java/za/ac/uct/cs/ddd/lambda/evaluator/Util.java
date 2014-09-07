package za.ac.uct.cs.ddd.lambda.evaluator;

/**
 * Miscellaneous useful functions.
 */
class Util {
    /**
     * Given a variable name, increments it to produce a new variable name with similar meaning.
     * @param name The original name
     * @return The new name
     */
    static String nextVariableName(String name) {
        if (name.endsWith("\u2032")) {
            return name.substring(0, name.length()-1) + "\u2033";
        } else if (name.endsWith("\u2033")) {
            return name.substring(0, name.length()-1) + "\u2034";
        } else {
            return name + "\u2032";
        }
    }
}