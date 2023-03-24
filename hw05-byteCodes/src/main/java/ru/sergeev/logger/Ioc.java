package ru.sergeev.logger;

import ru.sergeev.FatherInterfaceLogger;
import ru.sergeev.annotation.Log;
import ru.sergeev.exampleOne.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc<T> {

    private T fatherInterfaceLogger;

    public Ioc(T fatherInterfaceLogger) {
        this.fatherInterfaceLogger = fatherInterfaceLogger;
    }

    public static <T> T INSTANCE(Object testLogging) {
        return (T) new Ioc(testLogging).createMyClass();
    }

    public T createMyClass() {
        Class<?> aClass = fatherInterfaceLogger.getClass();

        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                aClass.getInterfaces(), new DemoInvocationHandler(fatherInterfaceLogger));
    }

    class DemoInvocationHandler <T> implements InvocationHandler {
        private final T myClass;

        DemoInvocationHandler(T myClass) {
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