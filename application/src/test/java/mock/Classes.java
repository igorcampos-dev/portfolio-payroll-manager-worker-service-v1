package mock;

import java.lang.reflect.Method;

public class Classes {

    public static Object callMethodByReflection(Class<?> classes, String methodName, Class<?>[] typeParam, Object[] param, Object instance) throws Exception {
        Method method = classes.getDeclaredMethod(methodName, typeParam);
        method.setAccessible(true);
        return method.invoke(instance, param);
    }
}
