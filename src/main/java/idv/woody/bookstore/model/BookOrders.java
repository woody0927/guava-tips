package idv.woody.bookstore.model;

import com.google.common.collect.Ordering;

/**
 * Created by chun-chiao on 2016/9/18.
 */
public class BookOrders {
    public static final Ordering<Book> PUBLISHER_ASC = Ordering.natural().nullsLast().onResultOf(BookFunctions.PUBLISHER);
    public static final Ordering<Book> PRICE_ASC = Ordering.natural().nullsLast().onResultOf(BookFunctions.PRICE);
}
