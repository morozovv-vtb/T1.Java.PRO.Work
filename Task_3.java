package T1.homework.task_3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Task_3 {
    public static void main(String[] args) throws InterruptedException {
        SimThPl pool = new SimThPl(5);

        // Добавляем 10 задач
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("Задача " + taskId + " выполняется " + Thread.currentThread().getName());
                try {
                    Thread.sleep(100); // Имитация работы
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Завершаем пул
        pool.shutdown();

        // Ждём завершения всех задач
        pool.awaitTermination();

        System.out.println("Все задачи выполнены. Пул завершил работу.");
    }

    public static class SimThPl {

        private final int poolSize;
        private final Queue<Runnable> taskQueue = new LinkedList<>();
        private final Thread[] workers;
        private final AtomicBoolean isShutdown = new AtomicBoolean(false);
        private final AtomicBoolean isTerminated = new AtomicBoolean(false);

        public SimThPl(int poolSize) {
            if (poolSize <= 0) {
                throw new IllegalArgumentException("Размер пула должен быть больше 0.");
            }
            this.poolSize = poolSize;
            this.workers = new Thread[poolSize];

            // Инициализируем и запускаем рабочие потоки
            IntStream.range(0, poolSize)
                    .forEach(i -> {
                        workers[i] = new Thread(() -> {
                            while (true) {
                                Runnable task;
                                synchronized (taskQueue) {
                                    // Если пул выключен и очередь пуста — выходим
                                    if (isShutdown.get() && taskQueue.isEmpty()) {
                                        isTerminated.set(true);
                                        taskQueue.notifyAll(); // Разбудить awaitTermination
                                        return;
                                    }

                                    // Ждём, пока не появится задача
                                    while (taskQueue.isEmpty() && !isShutdown.get()) {
                                        try {
                                            taskQueue.wait(); // Освобождает монитор и ждёт
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            return;
                                        }
                                    }

                                    // Если пришли из-за shutdown и очередь пуста — выходим
                                    if (isShutdown.get() && taskQueue.isEmpty()) {
                                        isTerminated.set(true);
                                        taskQueue.notifyAll();
                                        return;
                                    }

                                    // Извлекаем задачу
                                    task = taskQueue.poll();
                                }

                                // Выполняем задачу вне синхронизированного блока
                                if (task != null) {
                                    try {
                                        task.run();
                                    } catch (Exception e) {
                                        System.err.println("Задача завершилась с ошибкой: " + e.getMessage());
                                    }
                                }
                            }
                        },
                                "Поток-"+i);
                        workers[i].start();
                    });
        }

        public int getPoolSize() {
            return poolSize;
        }

        public void execute(Runnable command) {
            if (command == null) {
                throw new NullPointerException("Задача не может быть равна null");
            }

            synchronized (taskQueue) {
                if (isShutdown.get()) {
                    throw new IllegalStateException("Пул потоков остановлен, новые задачи не принимаются");
                }
                taskQueue.offer(command);
                taskQueue.notify(); // Разбудить один ожидающий поток
            }
        }

        public void shutdown() {
            isShutdown.set(true);
            synchronized (taskQueue) {
                taskQueue.notifyAll(); // Разбудить все ожидающие потоки
            }
        }

        public void awaitTermination() {
            synchronized (taskQueue) {
                while (!isTerminated.get()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        public boolean isTerminated() {
            return isTerminated.get();
        }

        public boolean isShutdown() {
            return isShutdown.get();
        }
    }
}
