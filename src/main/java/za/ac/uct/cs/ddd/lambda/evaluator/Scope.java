package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;

class Scope {
    private HashMap<String, LambdaVariable> map;

    public Scope() {
        map = new HashMap<String, LambdaVariable>();
    }

    public Scope(Scope other) {
        this();
        map.putAll(other.map);
    }

    public void add(LambdaVariable variable) {
        map.put(variable.getName(), variable);
    }

    public void remove(String variableName) {
        map.remove(variableName);
    }

    public LambdaVariable get(String variableName) {
        return map.get(variableName);
    }

    public boolean contains(String variableName) {
        return map.containsKey(variableName);
    }
}
