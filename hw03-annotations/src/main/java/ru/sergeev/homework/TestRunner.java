package ru.sergeev.homework;

import ru.sergeev.homework.annotations.After;
import ru.sergeev.homework.annotations.Before;
import ru.sergeev.homework.annotations.Test;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static ru.sergeev.homework.utills.ReflectionHelper.*;

public class TestRunner {

    public static void INSTANCE(String className) throws ClassNotFoundException {
        new TestRunner().test(className);
    }

    private void test(String className) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(className);
        List<Method> beforeList = getAnnotationMethods(aClass, Before.class);
        List<Method> afterList = getAnnotationMethods(aClass, After.class);
        List<Method> testList = getAnnotationMethods(aClass, Test.class);
        int passed = 0;
        int failed = 0;

        for (Method testMethod : Collections.unmodifiableList(testList)) {
            Object instantiateObj = instantiate(aClass);
            try {
                beforeList.forEach(beforeMethod -> callMethod(instantiateObj, beforeMethod));
                callMethod(instantiateObj, testMethod);
                passed++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                failed++;
            } finally {
                try {
                    afterList.forEach(afterMethod -> callMethod(instantiateObj, afterMethod));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    failed++;
                }
            }
        }
        present(testList.size(), passed, failed);
    }

    private void present(int countTests, int passed, int failed) {
        System.out.println("Количество тестов: " + countTests);
        System.out.println("Успешное количество выполненных тестов: " + passed);
        System.out.println("Упавшее количество выполненных тестов: " + failed);
    }
}