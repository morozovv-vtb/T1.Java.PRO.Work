package T1.homework.task_2;

import java.util.*;
import java.util.stream.Collectors;

public class Task_2 {

    // Вспомогательный класс Сотрудник (внутренний)
    static class Sotrudnik {
        String name;
        int age;
        String position;

        Sotrudnik(String name, int age, String position) {
            this.name = name;
            this.age = age;
            this.position = position;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public String getPosition() { return position; }
    }

    public static void main(String[] args) {
        // набор слов
        String[] words = {"коллайдер", "мультиметр", "анеморумбометр", "дихронизатор", "осцилограф", "психрометр"};

        // === Задача 1: 3-е наибольшее число ===
        int[] numbers1 = {5, 2, 10, 9, 4, 3, 10, 1, 13};
        int thirdLargest = Arrays.stream(numbers1)
                .boxed()
                .sorted(Collections.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(-1);
        System.out.println("1. 3-е наибольшее число: " + thirdLargest); // 10

        // === Задача 2: 3-е наибольшее уникальное число ===
        int thirdLargestUnique = Arrays.stream(numbers1)
                .boxed()
                .distinct()
                .sorted(Collections.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(-1);
        System.out.println("2. 3-е наибольшее уникальное число: " + thirdLargestUnique); // 9

        // === Задача 3: Имена 3 самых старших инженеров ===
        List<Sotrudnik> employees = Arrays.asList(
                new Sotrudnik("Александр", 48, "Инженер"),
                new Sotrudnik("Алексей", 35, "Инженер"),
                new Sotrudnik("Гильман", 44, "Менеджер"),
                new Sotrudnik("Айрат", 40, "Инженер"),
                new Sotrudnik("Владимир", 52, "Инженер"),
                new Sotrudnik("Ольга", 51, "Аналитик"),
                new Sotrudnik("Музафер", 59, "Аналитик"),
                new Sotrudnik("Анастасия", 43, "Тестировщик")
        );

        List<String> top3Engineers = employees.stream()
                .filter(emp -> "Инженер".equals(emp.getPosition()))
                .sorted((e1, e2) -> Integer.compare(e2.getAge(), e1.getAge()))
                .limit(3)
                .map(Sotrudnik::getName)
                .toList();
        System.out.println("3. Имена 3 самых старших инженеров: " + top3Engineers); // [Владимир, Александр, Айрат]

        // === Задача 4: Средний возраст инженеров ===
        double avgEngineerAge = employees.stream()
                .filter(emp -> "Инженер".equals(emp.getPosition()))
                .mapToInt(Sotrudnik::getAge)
                .average()
                .orElse(0.0);
        System.out.println("4. Средний возраст инженеров: " + String.format("%.1f", avgEngineerAge)); // 43,8

        // === Задача 5: Самое длинное слово ===
        String longestWord = Arrays.stream(words)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        System.out.println("5. Самое длинное слово: " + longestWord); // анеморумбометр

        // === Задача 6: Хеш-мапа слово → количество ===
        String text = "коллайдер мультиметр анеморумбометр дихронизатор осцилограф психрометр " +
                "анеморумбометр дихронизатор осцилограф психрометр мультиметр анеморумбометр дихронизатор";

        Map<String, Long> wordCount = Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy(
                        word -> word,
                        HashMap::new,
                        Collectors.counting() ));

        System.out.println("6. Частота слов: " + wordCount); // {осцилограф=2, анеморумбометр=3, психрометр=2, коллайдер=1, мультиметр=2, дихронизатор=3}

        // === Задача 7: Вывести слова по длине + алфавиту ===
        System.out.println("7. Слова по длине и алфавиту:");
        Arrays.stream(words)
                .sorted(Comparator.comparingInt(String::length).thenComparing(String::compareTo))
                .forEach(System.out::println); // коллайдер мультиметр осцилограф психрометр дихронизатор анеморумбометр

        // === Задача 8: Самое длинное слово в массиве строк ===
        String[] lines = {
                "питахайя груша личи дыня слива",
                "машина самолет пароход мотоцикл телепорт",
                "учебник перо пресс‑папье тетрадь чернила"
        };
        String longestWordInLines = Arrays.stream(lines)
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        System.out.println("8. Самое длинное слово среди всех строк: " + longestWordInLines); // пресс‑папье
    }
}