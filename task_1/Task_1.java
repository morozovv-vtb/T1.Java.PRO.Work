package T1.homework.task_1;

public class Task_1 {

    @BeforeSuite
    public static void beforeAll() {
        System.out.println("<=> Запускаем тесты...");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println(" -> Начинаем тест...");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("  <- Закончили тест.");
    }

    @AfterSuite
    public static void afterAll() {
        System.out.println("(OK) Все тесты завершены.");
    }

    @Order(1)
    @MyTest(name = "Тест сложения")
    public void testAddition() {
        assert 2 + 2 == 4 : "2 + 2 должно быть 4";
    }

    @Order(2)
    @MyTest
    public void testSubtraction() {
        assert 5 - 3 == 2 : "5 - 3 должно быть 2";
    }

    @Order(1)
    @MyTest
    @Disabled
    public void testSkipped() {
        assert false : "Этот тест пропущен";
    }

    @Order(3)
    @MyTest
    public void testError() {
        throw new RuntimeException("Ой, ошибка!");
    }

    @Order(4)
    @MyTest
    public void testFailed() {
        assert 1 == 2 : "Это провалится";
    }
}
