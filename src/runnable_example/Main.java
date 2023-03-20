package runnable_example;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class Main {
    public static void main(String[] args) {
        RunnableFuture<String> future1 = new FutureTask<>(new RunnableExample(12000), "Задача 1");
        RunnableFuture<String> future2 = new FutureTask<>(new RunnableExample(3000), "Задача 2");
        RunnableFuture<String> future3 = new FutureTask<>(new RunnableExample(20000), "Задача 3");

        HashMap<RunnableFuture<String>, Boolean> taskMap = new HashMap<>();
        taskMap.put(future1, false);
        taskMap.put(future2, false);
        taskMap.put(future3, false);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        taskMap.forEach((future, status) -> executor.execute(future));

        ResultCheckerExample resultCheckerExample = new ResultCheckerExample(taskMap);
        executor.execute(resultCheckerExample);

        executor.shutdown();

        System.out.println("Выполнение основной программы не прерывается");
    }
}