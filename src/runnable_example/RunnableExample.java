package runnable_example;

public class RunnableExample implements Runnable{

    private final Integer timeToCompute;

    public RunnableExample(Integer timeToCompute) {
        this.timeToCompute = timeToCompute;
    }

    @Override
    public void run() {
        System.out.println("Работа займет: " + timeToCompute + " миллисекунд");
        try {
            Thread.sleep(timeToCompute);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
