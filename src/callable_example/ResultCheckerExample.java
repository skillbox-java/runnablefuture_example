package callable_example;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;

public class ResultCheckerExample implements Runnable {

    private final List<RunnableFuture<Integer>> runnableFutureList;

    public ResultCheckerExample(List<RunnableFuture<Integer>> runnableFutureList) {
        this.runnableFutureList = runnableFutureList;
    }

    public void run() {

        int completedTask = 0;

        while (completedTask != 3) {
            for (Iterator<RunnableFuture<Integer>> futureIterator = runnableFutureList.iterator(); futureIterator.hasNext(); ) {
                RunnableFuture<Integer> future = futureIterator.next();

                if (future.isDone()) {
                    completedTask++;
                    try {
                        System.out.println("Задача выполнилась за: " + future.get() + " миллисекунд");
                        futureIterator.remove();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                if (completedTask == 2) {
                    completedTask++;
                    future.cancel(true);
                    System.out.println("Задача остановлена, результат её работы не требуется");
                }
            }

        }

    }

}
