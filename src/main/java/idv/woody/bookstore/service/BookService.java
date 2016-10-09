package idv.woody.bookstore.service;

import idv.woody.bookstore.dao.BookDao;
import idv.woody.bookstore.model.Book;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by chun-chiao on 2016/9/17.
 */
public class BookService {

    private BookDao bookDao;

    public BookService(final BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book findNull() {
        return bookDao.findNull();
    }

    public Book postFilterFirstMatch(final List<Book> books, final String titleLike, final Integer priceUnder) {
        for (Book book : books) {
            boolean matchedTitle = StringUtils.isNotBlank(titleLike) ? book.getTitle().contains(titleLike) : true;
            boolean matchedPrice = priceUnder != null ? book.getPrice() < priceUnder : true;
            if (matchedTitle && matchedPrice) {
                return book;
            }
        }
        return null;
    }

    public List<String> listTitles(final List<Book> books, final String titleLike, final Integer priceUnder) {
        List<String> titles = new ArrayList<>();
        for (Book book : books) {
            boolean matchedTitle = StringUtils.isNotBlank(titleLike) ? book.getTitle().contains(titleLike) : true;
            boolean matchedPrice = priceUnder != null ? book.getPrice() < priceUnder : true;
            if (matchedTitle && matchedPrice) {
                titles.add(book.getTitle());
            }
        }
        return titles;
    }

    public Map<String, List<Book>> listByPublisher() {
        List<Book> books = bookDao.findAll();
        Map<String, List<Book>> publisherMap = new HashMap<String, List<Book>>();
        for (Book book : books) {
            List<Book> bookList = publisherMap.get(book.getPublisher());
            if (bookList == null) {
                bookList = new ArrayList<Book>();
                publisherMap.put(book.getPublisher(), bookList);
            }
            bookList.add(book);
        }
        return publisherMap;
    }

    public void sort(List<Book> books) {
        Comparator<Book> comparator = new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                int result = b1.getPublisher().compareTo(b2.getPublisher());
                if (result == 0) {
                    result = b1.getPrice() < b2.getPrice() ? -1 : (b1.getPrice() > b2.getPrice()) ? 1 : 0;
                }
                return result;
            }
        };
        Collections.sort(books, comparator);
    }
}
