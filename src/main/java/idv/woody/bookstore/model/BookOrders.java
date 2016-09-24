package idv.woody.bookstore.model;

import com.google.common.collect.Ordering;

/**
 * Created by chun-chiao on 2016/9/18.
 */
public interface BookOrders {
    Ordering<Book> PUBLISHER_ASC = Ordering.natural().nullsLast().onResultOf(BookFunctionsGuava.GET_PUBLISHER);
    Ordering<Book> PRICE_ASC = Ordering.natural().nullsLast().onResultOf(BookFunctionsGuava.GET_PRICE);
}
