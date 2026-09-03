package T1.homework.task_1;

import java.lang.reflect.Method;

public class Test {
    private TestResult result;
    private String testName;
    private Throwable exception;

    public Test(TestResult result, String testName, Throwable exception) {
        this.result = result;
        this.testName = testName;
        this.exception = exception;
    }

    public TestResult getResult() {
        return result;
    }

    public String getTestName() {
        return testName;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "Test{" +
                "result=" + result +
                ", testName='" + testName + '\'' +
                ", exception=" + exception +
                '}';
    }
}
