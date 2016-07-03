package chapter29;

import java.util.concurrent.*;
//最简单的生产者和消费者,只需存储和取用，线程同步的操作交由ArrayBlockingQueue全权处理.
//1个ArrayBlockingQueue，1个main,2个线程全为static的,属于类,
public class ConsumerProducerUsingBlockingQueue {
  private static ArrayBlockingQueue<Integer> buffer =
    new ArrayBlockingQueue<Integer>(2);//只能存2个数，

  public static void main(String[] args) {
    // Create a thread pool with two threads
    ExecutorService executor = Executors.newFixedThreadPool(2);
    executor.execute(new ProducerTask());
    executor.execute(new ConsumerTask());
    executor.shutdown();
  }

  // A task for adding an int to the buffer
  private static class ProducerTask implements Runnable {
    public void run() {
      try {
        int i = 1;
        while (true) {
          System.out.println("Producer writes " + i);
          buffer.put(i++); // Add any value to the buffer, say, 1
          Thread.sleep((int)(Math.random() * 10000));
        }
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  // A task for reading and deleting an int from the buffer
  private static class ConsumerTask implements Runnable {
    public void run() {
      try {
        while (true) {
          System.out.println("\t\t\tConsumer reads " + buffer.take());
          Thread.sleep((int)(Math.random() * 10000));
        }
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }
}
