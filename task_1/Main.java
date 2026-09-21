package T1.homework.task_1;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<TestResult, List<Test>> results = TestRunner.runTests(Task_1.class);

        for (Map.Entry<TestResult, List<Test>> entry : results.entrySet()) {
            System.out.println("\n=== " + entry.getKey() + " ===");
            for (Test test : entry.getValue()) {
                System.out.println("  - " + test.getTestName());
                if (test.getException() != null) {
                    System.out.println("    X " + test.getException().getMessage());
                }
            }
        }
    }
}
