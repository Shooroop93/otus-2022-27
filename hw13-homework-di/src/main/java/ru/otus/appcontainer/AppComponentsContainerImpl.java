package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<C> components = new ArrayList<>();
        for (Object appComponent : appComponents) {
            if (componentClass.isAssignableFrom(appComponent.getClass())) {
                components.add((C) appComponent);
            }
        }

        if (components.isEmpty()) {
            throw new RuntimeException("Компонент не найден");
        } else if (components.size() > 1) {
            throw new RuntimeException("Обнаружены дублирующиеся компоненты");
        }

        return components.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C component = (C) appComponentsByName.get(componentName);
        if (component == null) {
            throw new RuntimeException("Компонент не найден");
        }
        return component;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private void processConfig(Class<?> configClass) {
        try {
            checkConfigClass(configClass);
            List<Method> methods = getOrderedMethods(configClass);
            Constructor<?> constructor = configClass.getDeclaredConstructor();
            Object configClazz = constructor.newInstance();

            for (Method method : methods) {
                AppComponent annotation = method.getAnnotation(AppComponent.class);
                checkComponent(annotation.name());
                Object resultInvokeMethod = invokeMethod(configClazz, method);

                if (resultInvokeMethod == null) {
                    throw new RuntimeException("Контекст не может быть настроен");
                }

                if (appComponentsByName.put(annotation.name(), resultInvokeMethod) != null) {
                    throw new RuntimeException(String.format("Компонент с именем '%s' уже существует", annotation.name()));
                }

                appComponents.add(resultInvokeMethod);
            }
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException("Контекст не может быть настроен", e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Не удалось создать экземпляр конфигурации", e);
        }
    }

    private <C> void checkComponent(C component) {
        if (component == null) {
            throw new RuntimeException("Компонент не найден");
        } else if (appComponentsByName.containsKey(component)) {
            throw new RuntimeException(String.format("Компонент с идентификатором '%s' уже существует", component));
        }
    }

    private List<Method> getOrderedMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getDeclaredMethods()).sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order())).toList();
    }

    private Object[] initParameters(Method method) throws ClassNotFoundException {
        return Arrays.stream(method.getParameterTypes())
                .map(this::getAppComponent)
                .toArray();
    }

    private Object invokeMethod(Object configObject, Method method) throws ClassNotFoundException {
        try {
            if (method.getParameterCount() == 0) {
                return method.invoke(configObject);
            } else {
                return method.invoke(configObject, initParameters(method));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Ошибка при вызове метода", e);
        }
    }
}