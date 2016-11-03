package reflection;

import javax.xml.ws.soap.MTOM;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        List<Method> getterMethods = appropriateMethods(from, "^get.*");
        List<Method> setterMethods = appropriateMethods(to, "^set.*");

        for (Method getterMethod : getterMethods) {
            if (getterMethod.getParameterCount() != 0) {
                continue;
            }
            Type getterType = getterMethod.getReturnType();
            for (Method setterMethod : setterMethods) {
                if (setterMethod.getParameterCount() != 1) {
                    continue;
                }
                Type setterType = setterMethod.getParameterTypes()[0];

                if (checkMethodNames(setterMethod, getterMethod) && checkTypes(setterType, getterType)) {
                    System.out.println("try to invoke method " + setterMethod.getName() + " with result of method " + getterMethod.getName());
                    try {
                        setterMethod.invoke(to, getterMethod.invoke(from));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static List<Method> appropriateMethods(Object o, String pattern) {
        List<Method> methods = Arrays.asList(o.getClass().getMethods());
        List<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.getName().matches(pattern)) {
                result.add(method);
            }
        }
        return result;
    }

    private static boolean checkTypes(Type setterType, Type getterType) {
        try {
            Class<?> setterClazz = Class.forName(setterType.getTypeName());
            Class<?> getterClazz = Class.forName(getterType.getTypeName());
            return setterClazz.isAssignableFrom(getterClazz);
        } catch (ClassNotFoundException e) {
            return setterType.getTypeName() == getterType.getTypeName();
        }
    }

    private static boolean checkMethodNames(Method setterMethod, Method getterMethod) {
        String getterMethodName = getterMethod.getName().substring(3);
        String setterMethodName = setterMethod.getName().substring(3);
        return setterMethodName.equals(getterMethodName);
    }
}
