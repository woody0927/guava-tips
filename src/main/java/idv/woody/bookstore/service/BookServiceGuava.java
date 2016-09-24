package idv.woody.bookstore.service;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import idv.woody.bookstore.dao.BookDao;
import idv.woody.bookstore.model.Book;
import idv.woody.bookstore.model.BookFunctionsGuava;
import idv.woody.bookstore.model.BookOrders;

import java.util.Collections;
import java.util.List;

/**
 * Created by chun-chiao on 2016/9/17.
 */
public class BookServiceGuava {

    private BookDao bookDao;

    public BookServiceGuava(final BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Optional<Book> findNull() {
        return Optional.fromNullable(bookDao.findNull());
    }

    public Optional<Book> postFilterFirstMatch(final List<Book> books, final Predicate... predicates) {
        Optional<Book> firstMatch = Optional.absent();
        Predicate andPredicate = Predicates.and(predicates);
        for (Book book : books) {
            if (andPredicate.apply(book)) {
                firstMatch = Optional.of(book);
                break;
            }
        }
        return firstMatch;
    }

    public List<String> listTitles(final List<Book> books, final Predicate... predicates) {
        return FluentIterable.from(books).filter(Predicates.and(predicates)).transform(BookFunctionsGuava.GET_TITLE).toList();
    }

    public ListMultimap<String, Book> listByPublisher() {
        List<Book> books = bookDao.findAll();
        return Multimaps.index(books, BookFunctionsGuava.GET_PUBLISHER);
    }

    public List<Book> sort(List<Book> books) {
        return sort(books, BookOrders.PUBLISHER_ASC.compound(BookOrders.PRICE_ASC));
    }

    public List<Book> sort(List<Book> books, Ordering<Book> ordering) {
        Collections.sort(books, ordering);
        return books;
    }

}
