package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A set of variables which allows shadowing.
 */
public class Scope {
    private HashMap<String, LinkedList<LambdaVariable>> map;

    /**
     * Creates a new empty scope.
     */
    public Scope() {
        map = new HashMap<String, LinkedList<LambdaVariable>>();
    }

    /**
     * Creates a new scope from the contents of another.
     * @param other The scope to copy
     */
    public Scope(Scope other) {
        this();
        for (String name : other.map.keySet()) {
            map.put(name, new LinkedList<LambdaVariable>(other.map.get(name)));
        }
    }

    /**
     * Adds a variable to the scope, shadowing any existing variables with the same name.
     * @param variable The variable to add
     */
    public void add(LambdaVariable variable) {
        String name = variable.getName();
        if (!map.containsKey(variable.getName())) {
            map.put(name, new LinkedList<LambdaVariable>());
        } else if (map.get(name).contains(variable)) {
            return;  // don't add it twice
        }
        map.get(name).push(variable);
    }

    /**
     * Adds all the variables from another scope to this scope, shadowing any existing variables with the same name.
     * @param scope The other scope to add from
     */
    public void addAll(Scope scope) {
        for (String name : scope.map.keySet()) {
            if (!scope.map.get(name).isEmpty()) {
                add(scope.get(name));
            }
        }
    }

    /**
     * Removes a variable from the scope, and restores any shadowed variables.
     * @param variable The variable to remove
     */
    public void remove(LambdaVariable variable) {
        if (contains(variable.getName())) {
            LinkedList<LambdaVariable> list = map.get(variable.getName());
            if (!list.isEmpty() && list.peek() == variable) {
                list.pop();
            }
        }
    }

    /**
     * Gets a variable instance by its name.
     * @param variableName The name of the variable
     * @return The instance of the variable
     */
    public LambdaVariable get(String variableName) {
        return map.get(variableName).peek();
    }

    /**
     * Checks if the scope contains any variables with the given name.
     * @param variableName The name of the variable
     * @return {@code true} if it exists in this scope, {@code false} otherwise
     */
    public boolean contains(String variableName) {
        return map.containsKey(variableName) && !map.get(variableName).isEmpty();
    }

    /**
     * Checks if the scope contains a variable with the given name, then either returns it if found or otherwise adds
     * and returns a new instance of a variable with the given name.
     * @param variableName The name of the variable
     * @return The existing or new variable
     */
    public LambdaVariable getOrAddNew(String variableName) {
        if (contains(variableName)) {
            return get(variableName);
        } else {
            LambdaVariable variable = new LambdaVariable(variableName);
            add(variable);
            return variable;
        }
    }

    @Override
    public String toString() {
        return map.values().toString();
    }
}