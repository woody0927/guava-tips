package idv.woody.bookstore.service;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ListMultimap;
import idv.woody.bookstore.dao.BookDao;
import idv.woody.bookstore.model.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by chun-chiao on 2016/8/29.
 */
public class BookServiceGuavaTest {

    private BookServiceGuava unit;
    private BookDao bookDao;

    private Book dockerInPractice = new Book("Docker in Practice", "Manning", 1418);
    private Book javaPerformance = new Book("Java Performance", "Addison-Wesley Professional", 544);
    private Book headFirstJava = new Book("Head First Java, 2nd Edition", "O'Reilly Media", 1456);
    private Book javaConcurrency = new Book("Java Concurrency in Practice", "Addison-Wesley Professional", 1819);
    private Book effectiveJava = new Book("Effective Java (2nd Edition)", "Pearson", 1795);

    @Before
    public void init() {
        bookDao = new BookDao();
        unit = new BookServiceGuava(bookDao);
        bookDao.persist(dockerInPractice);
        bookDao.persist(javaPerformance);
        bookDao.persist(headFirstJava);
        bookDao.persist(javaConcurrency);
        bookDao.persist(effectiveJava);
    }

    @Test
    public void testFindNull() throws Exception {
        Optional<Book> bookNull = unit.findNull();
        if (bookNull.isPresent()) {
            System.out.println("Title: " + bookNull.get().getTitle());
        }
    }

    @Test
    public void testPostFilterFirstMatch() throws Exception {
        Predicate<Book> titleLike = new Predicate<Book>() {
            @Override
            public boolean apply(Book book) {
                return book.getTitle().contains("Performance");
            }
        };
        Predicate<Book> priceUnder = new Predicate<Book>() {
            @Override
            public boolean apply(Book book) {
                return book.getPrice() < 1500;
            }
        };
        Optional<Book> found = unit.postFilterFirstMatch(bookDao.findAll(), titleLike, priceUnder);
        assertThat("At least one book should be found", found.isPresent());
        assertThat(found.get().getTitle(), is(javaPerformance.getTitle()));
    }

    @Test
    public void testListTitles() throws Exception {
        Predicate<Book> priceUnder = new Predicate<Book>() {
            @Override
            public boolean apply(Book book) {
                return book.getPrice() < 1500;
            }
        };
        List<String> qualifiedBookTitles = unit.listTitles(bookDao.findAll(), priceUnder);
        assertThat(qualifiedBookTitles, containsInAnyOrder(javaPerformance.getTitle(), headFirstJava.getTitle(), dockerInPractice.getTitle()));
    }

    @Test
    public void testListByPublisher() throws Exception {
        ListMultimap<String, Book> publisherListMap = unit.listByPublisher();
        assertThat(publisherListMap.get("Manning"), containsInAnyOrder(dockerInPractice));
        assertThat(publisherListMap.get("Addison-Wesley Professional"), containsInAnyOrder(javaPerformance, javaConcurrency));
        assertThat(publisherListMap.get("O'Reilly Media"), containsInAnyOrder(headFirstJava));
        assertThat(publisherListMap.get("Pearson"), containsInAnyOrder(effectiveJava));
    }

    @Test
    public void testSort_comparable() throws Exception {
        List<Book> books = bookDao.findAll();
        Collections.sort(books);
        assertEquals(Arrays.asList(javaPerformance, javaConcurrency, dockerInPractice, headFirstJava, effectiveJava), books);
    }

    @Test
    public void testSort_compartor() throws Exception {
        List<Book> books = bookDao.findAll();
        unit.sort(books);
        assertEquals(Arrays.asList(javaPerformance, javaConcurrency, dockerInPractice, headFirstJava, effectiveJava), books);
    }

}
