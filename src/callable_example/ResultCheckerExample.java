package callable_example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;

public class ResultCheckerExample implements Runnable {

    private final HashMap<RunnableFuture<Integer>, Boolean> runnableFutureList;

    public ResultCheckerExample(HashMap<RunnableFuture<Integer>, Boolean> runnableFutureList) {
        this.runnableFutureList = runnableFutureList;
    }

    public void run() {

        int completedTask = 0;

        while (completedTask != 3) {
            for (Map.Entry<RunnableFuture<Integer>, Boolean> future : runnableFutureList.entrySet()) {

                if (future.getKey().isDone() && !future.getValue()) {
                    completedTask++;
                    future.setValue(true);
                    try {
                        System.out.println("Задача выполнилась за: " + future.getKey().get() + " миллисекунд");
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (completedTask == 2) {
                    completedTask++;
                    future.getKey().cancel(true);
                    System.out.println("Задача остановлена, результат её работы не требуется");
                }
            }
        }

    }

}
