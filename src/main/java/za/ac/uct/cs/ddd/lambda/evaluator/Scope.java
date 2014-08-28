package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.Collection;
import java.util.HashMap;

public class Scope {
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

    public void addAll(Scope scope) {
        map.putAll(scope.map);
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

    public Collection<LambdaVariable> toCollection() {
        return map.values();
    }

    @Override
    public String toString() {
        return map.values().toString();
    }
}
