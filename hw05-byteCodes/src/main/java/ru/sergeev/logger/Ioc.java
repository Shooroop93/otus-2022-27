package ru.sergeev.logger;

import ru.sergeev.FatherInterfaceLogger;
import ru.sergeev.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {
    }

    public static FatherInterfaceLogger createMyClass(FatherInterfaceLogger clazz) {
        InvocationHandler handler = new DemoInvocationHandler(clazz);
        return (FatherInterfaceLogger) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{FatherInterfaceLogger.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final FatherInterfaceLogger myClass;

        DemoInvocationHandler(FatherInterfaceLogger myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (myClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                System.out.println("\n");
                System.out.println("Logger -_-_-_- [ " + "class: " + myClass + "method: " + method.getName() + ", args: " + Arrays.toString(args) + " ] -_-_-_-_");
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}