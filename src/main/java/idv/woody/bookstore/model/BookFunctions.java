package idv.woody.bookstore.model;

import com.google.common.base.Function;

/**
 * Created by chun-chiao on 2016/9/18.
 */
public enum BookFunctions implements Function {
    PUBLISHER {
        @Override
        public String apply(Object book) {
            return ((Book) book).getPublisher();
        }
    }, PRICE {
        @Override
        public Integer apply(Object book) {
            return ((Book) book).getPrice();
        }
    };
}
