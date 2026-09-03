package T1.homework.task_1;

import java.lang.reflect.*;
import java.util.*;

public class TestRunner {

    public static Map<TestResult, List<Test>> runTests(Class<?> c) {
        Map<TestResult, List<Test>> results = new HashMap<>();
        for (TestResult result : TestResult.values()) {
            results.put(result, new ArrayList<>());
        }

        // Проверка: можно ли создать экземпляр класса
        Object testInstance;
        try {
            testInstance = c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BadTestClassError("Класс " + c.getName() + " не имеет публичного конструктора без параметров.");
        }

        // Поиск методов
        Method beforeSuite = null;
        Method afterSuite = null;
        List<Method> beforeEachMethods = new ArrayList<>();
        List<Method> afterEachMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeSuite должен быть статическим методом: " + method.getName());
                }
                beforeSuite = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterSuite должен быть статическим методом: " + method.getName());
                }
                afterSuite = method;
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeEach не может быть статическим: " + method.getName());
                }
                beforeEachMethods.add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterEach не может быть статическим: " + method.getName());
                }
                afterEachMethods.add(method);
            } else if (method.isAnnotationPresent(MyTest.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@MyTest не может быть статическим: " + method.getName());
                }
                testMethods.add(method);
            }
        }

        // Сортируем тесты по @Order (по возрастанию)
        testMethods.sort((m1, m2) -> {
            Order o1 = m1.getAnnotation(Order.class);
            Order o2 = m2.getAnnotation(Order.class);
            int order1 = o1 != null ? o1.value() : 5;
            int order2 = o2 != null ? o2.value() : 5;
            if (order1 != order2) {
                return Integer.compare(order1, order2);
            }
            return m1.getName().compareTo(m2.getName());
        });

        // Выполняем @BeforeSuite (если есть)
        if (beforeSuite != null) {
            try {
                beforeSuite.invoke(null);
            } catch (Exception e) {
                throw new BadTestClassError("Ошибка в @BeforeSuite: " + e.getMessage());
            }
        }

        // Запускаем каждый тест
        for (Method testMethod : testMethods) {
            String testName = getTestName(testMethod);
            boolean disabled = testMethod.isAnnotationPresent(Disabled.class);

            if (disabled) {
                results.get(TestResult.Skipped).add(new Test(TestResult.Skipped, testName, null));
                continue;
            }

            try {
                // Выполняем @BeforeEach
                for (Method before : beforeEachMethods) {
                    before.invoke(testInstance);
                }

                // Выполняем сам тест
                testMethod.invoke(testInstance);

                // Успех
                results.get(TestResult.Success).add(new Test(TestResult.Success, testName, null));

            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof AssertionError) {
                    results.get(TestResult.Failed).add(new Test(TestResult.Failed, testName, cause));
                } else {
                    results.get(TestResult.Error).add(new Test(TestResult.Error, testName, cause));
                }
            } catch (Exception e) {
                results.get(TestResult.Error).add(new Test(TestResult.Error, testName, e));
            } finally {
                // Выполняем @AfterEach (даже если тест упал)
                for (Method after : afterEachMethods) {
                    try {
                        after.invoke(testInstance);
                    } catch (Exception ignored) {
                        // Игнорируем ошибки в @AfterEach
                    }
                }
            }
        }

        // Выполняем @AfterSuite (если есть)
        if (afterSuite != null) {
            try {
                afterSuite.invoke(null);
            } catch (Exception e) {
                System.err.println("Ошибка в @AfterSuite: " + e.getMessage());
            }
        }

        return results;
    }

    private static String getTestName(Method method) {
        MyTest testAnnotation = method.getAnnotation(MyTest.class);
        if (testAnnotation != null && !testAnnotation.name().isEmpty()) {
            return testAnnotation.name();
        }
        return method.getName();
    }
}
