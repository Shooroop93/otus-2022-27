package ru.sergeev.logger;

import ru.sergeev.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

import static java.text.MessageFormat.*;
import static java.util.Arrays.*;

public class Ioc<T> {

    private static List<String> methodList;
    private final T objecT;

    public Ioc(T object) {
        this.objecT = object;
        methodList = new ArrayList<>();

        stream(objecT.getClass().getMethods())
                .forEach(method -> {
                    if (method.isAnnotationPresent(Log.class)) {
                        methodList.add(nameHandler(method));
                    }
                });
    }

    public static <T> T getInstance(Object object) {
        return (T) new Ioc(object).createMyClass();
    }

    public T createMyClass() {
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), objecT.getClass().getInterfaces(), new DemoInvocationHandler(objecT));
    }

    class DemoInvocationHandler<T> implements InvocationHandler {
        private final T myClass;

        DemoInvocationHandler(T myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodList.contains(nameHandler(method))) printLogger(args);
            return method.invoke(myClass, args);
        }
    }

    private String nameHandler(Method method) {
        return format("{0}=={1}", method.getName(), Arrays.stream(method.getParameterTypes()).map(Class::getName).toList());
    }

    private void printLogger(Object[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Logger --------> ");
        for (Object arg : args) {
            stringBuilder.append(arg + " ");
        }
        stringBuilder.append("<--------");
        System.out.println(stringBuilder);
    }
}