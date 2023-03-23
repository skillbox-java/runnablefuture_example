package runnable_example;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;

public class ResultCheckerExample implements Runnable {

    private final List<RunnableFuture<String>> runnableFutureList;

    public ResultCheckerExample(List<RunnableFuture<String>> runnableFutureList) {
        this.runnableFutureList = runnableFutureList;
    }

    public void run() {

        int completedTask = 0;

        while (completedTask != 3) {
            for (Iterator<RunnableFuture<String>> futureIterator = runnableFutureList.iterator(); futureIterator.hasNext(); ) {

                RunnableFuture<String> future = futureIterator.next();

                if (future.isDone()) {
                    completedTask++;
                    try {
                        System.out.println("Выполнена " + future.get());
                        futureIterator.remove();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
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
