package idv.woody.crawler;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;

/**
 * Created by chun-chiao on 2016/9/19.
 */
public class CrawlerServiceGuava {

    public static void main(String[] args) throws Exception {
        String url = "helloworld.com";
        ListeningExecutorService executorService = null;
        try {
            executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

            Spider spider = new Spider(url);
            ListenableFuture<String> future = executorService.submit(spider);
            Futures.addCallback(future, new FutureCallback<String>() {
                @Override
                public void onSuccess(String content) {
                    System.out.println(String.format("Content of %s is %s", url, content));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println(String.format("Failed to get content of %s ...", url));
                }
            });
            System.out.println("do something else...");
            Thread.sleep(2000L);
            System.out.println("do something else 2...");
        } finally {
            executorService.shutdown();
        }
    }
}
