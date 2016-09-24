package idv.woody.bookstore.service;

import idv.woody.bookstore.dao.BookDao;
import idv.woody.bookstore.model.Book;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by chun-chiao on 2016/9/17.
 */
public class BookServiceJava8 {

    private BookDao bookDao;

    public BookServiceJava8(final BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Optional<Book> findNull() {
        return Optional.ofNullable(bookDao.findNull());
    }

    public Optional<Book> postFilterFirstMatch(final List<Book> books, Predicate<Book> postFilter) {
        Optional<Book> firstMatch = Optional.empty();
        for (Book book : books) {
            if (postFilter.test(book)) {
                firstMatch = Optional.of(book);
                break;
            }
        }
        return firstMatch;
    }

    public List<String> listTitles(final List<Book> books, final Predicate<Book> predicate) {
        return books.stream().filter(predicate).map(book -> book.getTitle()).collect(Collectors.toList());
    }

    public Map<String, List<Book>> listByPublisher() {
        List<Book> books = bookDao.findAll();
        return books.stream().collect(Collectors.groupingBy(b -> b.getPublisher()));
    }

    public void sort(List<Book> books) {
        sort(books, Comparator.comparing(Book::getPublisher).thenComparing(Book::getPrice));
    }

    public void sort(List<Book> books, Comparator<Book> comparator) {
        Collections.sort(books, comparator);
    }
}
