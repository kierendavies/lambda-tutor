package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class Scope {
    private HashMap<String, LinkedList<LambdaVariable>> map;

    public Scope() {
        map = new HashMap<String, LinkedList<LambdaVariable>>();
    }

    public Scope(Scope other) {
        this();
//        map.putAll(other.map);
        for (String name : other.map.keySet()) {
            map.put(name, new LinkedList<LambdaVariable>(other.map.get(name)));
        }
    }

    public void add(LambdaVariable variable) {
        String name = variable.getName();
        if (!map.containsKey(variable.getName())) {
            map.put(name, new LinkedList<LambdaVariable>());
        } else if (map.get(name).contains(variable)) {
            return;  // don't add it twice
        }
        map.get(name).push(variable);
    }

    public void addAll(Scope scope) {
        for (String name : scope.map.keySet()) {
            add(scope.get(name));
        }
    }

    public void remove(LambdaVariable variable) {
        if (contains(variable.getName())) {
            LinkedList<LambdaVariable> list = map.get(variable.getName());
            if (!list.isEmpty() && list.peek() == variable) {
                list.pop();
            }
        }
    }

    public LambdaVariable get(String variableName) {
        return map.get(variableName).peek();
    }

    public boolean contains(String variableName) {
        return map.containsKey(variableName) && !map.get(variableName).isEmpty();
    }

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
