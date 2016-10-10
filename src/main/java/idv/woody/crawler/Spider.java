package idv.woody.crawler;

import java.util.concurrent.Callable;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

/**
 * Created by chun-chiao on 2016/9/19.
 */
public class Spider implements Callable<String> {
    private String url;

    public Spider(String url) {
        this.url = url;
    }

    @Override
    public String call() throws Exception {
        System.out.println("About to crawl " + url);
        long tenSeconds = 10000L;
        Thread.sleep(tenSeconds);
        return randomAlphanumeric(10);
    }
}
