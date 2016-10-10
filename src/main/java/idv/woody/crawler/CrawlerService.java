package idv.woody.crawler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by chun-chiao on 2016/9/19.
 */
public class CrawlerService {

    public static void main(String[] args) throws Exception {
        String url = "helloworld.com";
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            Spider spider = new Spider(url);
            Future<String> future = executorService.submit(spider);
            while (!future.isDone()) {
                System.out.println(String.format("crawling %s ...", url));
                Thread.sleep(2000);
            }
            try {
                System.out.println(String.format("Content of %s is %s", url, future.get()));
            } catch (InterruptedException e) {
                System.out.println("Interrupted" + e.getMessage());
            } catch (ExecutionException e) {
                System.out.println("Exception in task" + e.getMessage());
            }
        } finally {
            executorService.shutdown();
        }
    }
}
