package idv.woody.bookstore.model;

import com.google.common.base.Function;

/**
 * Created by chun-chiao on 2016/9/18.
 */
public interface BookFunctionsGuava {
    Function<Book, String> GET_PUBLISHER = new Function<Book, String>() {
        @Override
        public String apply(Book book) {
            return book.getPublisher();
        }
    };
    Function<Book, Integer> GET_PRICE = new Function<Book, Integer>() {
        @Override
        public Integer apply(Book book) {
            return book.getPrice();
        }
    };
    Function<Book, String> GET_TITLE = new Function<Book, String>() {
        @Override
        public String apply(Book book) {
            return book.getTitle();
        }
    };
}
