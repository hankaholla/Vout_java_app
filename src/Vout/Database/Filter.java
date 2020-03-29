package Vout.Database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Hlavna trieda filter (navrhovy vzor Filter)
 * - abstraktna, su od nej oddedene triedy s nazvom podla toho, ake objekty chceme filtrovat.
 * Jej metody porovnavaju na zaklade principu prace HashMap - zoberu si nejaky kluc a hodnotu, a prechadzaju
 * databazu, az kym nenajdu zhodu.
 */
public abstract class Filter {

    protected Map<String, String> attributes = new HashMap<>();
    protected Map<String, Compare> compartments = new HashMap<>();

    public boolean compare(Object item) {
        Class<?> clazz = item.getClass();
        boolean match = false;

        for (String attribute : this.attributes.keySet()) {
            for (Method method : clazz.getMethods()) {
                String methodName = method.getName().toLowerCase();
                if (methodName.endsWith(attribute.toLowerCase()) && methodName.startsWith("get")) {
                    method.setAccessible(true);
                    try {
                        Compare compare = this.compartments.get(attribute);
                        match = compare.compare(method.invoke(item), this.attributes.get(attribute));
                    } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                        match = false;
                    }
                }
            }
        }
        return match;
    }

    public void addAttribute(String key, String value, String compareType) {
        this.attributes.put(key, value);

        switch (compareType) {
            case ">=":
                this.compartments.put(key, new Compare() {
                    public boolean compare(Object first, Object second) {
                        return (int) first >= (int) second;
                    }
                });
                break;
            case "<=":
                this.compartments.put(key, new Compare() {
                    public boolean compare(Object first, Object second) {
                        return (int) first <= (int) second;
                    }
                });
                break;
            case ">":
                this.compartments.put(key, new Compare() {
                    public boolean compare(Object first, Object second) {
                        return (int) first > (int) second;
                    }
                });
                break;
            case "<":
                this.compartments.put(key, new Compare() {
                    public boolean compare(Object first, Object second) {
                        return (int) first < (int) second;
                    }
                });
                break;
            default:
                this.compartments.put(key, new Compare() {
                    public boolean compare(Object first, Object second) {
                        return first.equals(second);
                    }
                });
                break;
        }
    }
}