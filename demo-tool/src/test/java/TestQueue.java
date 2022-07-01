import java.util.concurrent.LinkedBlockingQueue;

public class TestQueue {
    /**
     * jdk 8  LinkedBlockingQueue使用stream遍历的一个bug
     * 在jdk 9中被修复
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(1000);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    queue.offer(new Object());
                    queue.remove();
                }
            }).start();
        }
        while (true) {
            System.out.println("begin scan, i still alive");
            queue.stream()
                    .filter(o -> o == null)
                    .findFirst()
                    .isPresent();
            Thread.sleep(100);
            System.out.println("finish scan, i still alive");
        }
    }
}