package runnable_example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class Main {
    public static void main(String[] args) {
        RunnableFuture<String> future1 = new FutureTask<>(new RunnableExample(12000), "Задача 1");
        RunnableFuture<String> future2 = new FutureTask<>(new RunnableExample(3000), "Задача 2");
        RunnableFuture<String> future3 = new FutureTask<>(new RunnableExample(20000), "Задача 3");

        List<RunnableFuture<String>> taskList = new ArrayList<>();
        taskList.add(future1);
        taskList.add(future2);
        taskList.add(future3);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        taskList.forEach(executor::execute);

        ResultCheckerExample resultCheckerExample = new ResultCheckerExample(taskList);
        executor.execute(resultCheckerExample);

        executor.shutdown();

        System.out.println("Выполнение основной программы не прерывается");
    }
}