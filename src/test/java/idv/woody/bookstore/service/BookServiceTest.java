package idv.woody.bookstore.service;

import idv.woody.bookstore.dao.BookDao;
import idv.woody.bookstore.model.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by chun-chiao on 2016/8/29.
 */
public class BookServiceTest {

    private BookService unit;
    private BookDao bookDao;

    private Book dockerInPractice = new Book("Docker in Practice", "Manning", 1418);
    private Book javaPerformance = new Book("Java Performance", "Addison-Wesley Professional", 544);
    private Book headFirstJava = new Book("Head First Java, 2nd Edition", "O'Reilly Media", 1456);
    private Book javaConcurrency = new Book("Java Concurrency in Practice", "Addison-Wesley Professional", 1819);
    private Book effectiveJava = new Book("Effective Java (2nd Edition)", "Pearson", 1795);

    @Before
    public void init() {
        bookDao = new BookDao();
        unit = new BookService(bookDao);
        bookDao.persist(dockerInPractice);
        bookDao.persist(javaPerformance);
        bookDao.persist(headFirstJava);
        bookDao.persist(javaConcurrency);
        bookDao.persist(effectiveJava);
    }

    @Test(expected = NullPointerException.class)
    public void testFindNull() throws Exception {
        Book bookNull = unit.findNull();
        System.out.println("Title: " + bookNull.getTitle());
    }

    @Test
    public void testPostFilterFirstMatch() throws Exception {
        final String titleLike = "Performance";
        final int priceUnder = 1500;
        Book book = unit.postFilterFirstMatch(bookDao.findAll(), titleLike, priceUnder);
        assertThat(book.getTitle(), is(javaPerformance.getTitle()));
    }

    @Test
    public void testListByPublisher() throws Exception {
        Map<String, List<Book>> publisherListMap = unit.listByPublisher();
        assertThat(publisherListMap.get("Manning"), containsInAnyOrder(dockerInPractice));
        assertThat(publisherListMap.get("Addison-Wesley Professional"), containsInAnyOrder(javaPerformance, javaConcurrency));
        assertThat(publisherListMap.get("O'Reilly Media"), containsInAnyOrder(headFirstJava));
        assertThat(publisherListMap.get("Pearson"), containsInAnyOrder(effectiveJava));
    }

    @Test
    public void testSort() throws Exception {
        List<Book> books = bookDao.findAll();
        unit.sort(books);
        assertEquals(Arrays.asList(javaPerformance, javaConcurrency, dockerInPractice, headFirstJava, effectiveJava), books);
    }
}
