package idv.woody.bookstore.dao;

import com.google.common.collect.Lists;
import idv.woody.bookstore.model.Book;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chun-chiao on 2016/8/29.
 */
public class BookDao {

    private List<Book> books = Lists.newArrayList();
    private AtomicLong id = new AtomicLong(0);

    public BookDao() {
    }

    public List<Book> findAll() {
        return books;
    }

    public void persist(Book book) {
        book.setId(id.getAndIncrement());
        this.books.add(book);
    }

    public Book findNull() {
        return null;
    }
}
