package idv.woody.bookstore.service;

import idv.woody.bookstore.dao.BookDao;
import idv.woody.bookstore.model.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

/**
 * Created by chun-chiao on 2016/9/17.
 */
public class BookServiceJava8Test {

    private BookServiceJava8 unit;
    private BookDao bookDao;

    private Book dockerInPractice = new Book("Docker in Practice", "Manning", 1418);
    private Book javaPerformance = new Book("Java Performance", "Addison-Wesley Professional", 544);
    private Book headFirstJava = new Book("Head First Java, 2nd Edition", "O'Reilly Media", 1456);
    private Book javaConcurrency = new Book("Java Concurrency in Practice", "Addison-Wesley Professional", 1819);
    private Book effectiveJava = new Book("Effective Java (2nd Edition)", "Pearson", 1795);

    @Before
    public void init() {
        bookDao = new BookDao();
        unit = new BookServiceJava8(bookDao);
        bookDao.persist(dockerInPractice);
        bookDao.persist(javaPerformance);
        bookDao.persist(headFirstJava);
        bookDao.persist(javaConcurrency);
        bookDao.persist(effectiveJava);
    }

    @Test
    public void testFindNull() throws Exception {

    }

    @Test
    public void testPostFilterFirstMatch() throws Exception {
        java.util.function.Predicate<Book> pricePredicate = b -> b.getPrice() > 1500;
        java.util.function.Predicate<Book> titlePredicate = b -> b.getTitle().contains("Concurrency");
        java.util.function.Predicate<Book> aggregatedPredicate = pricePredicate.and(titlePredicate);
        Optional<Book> found = unit.postFilterFirstMatch(bookDao.findAll(), aggregatedPredicate);
        System.out.println(found.isPresent());
        System.out.println(found.get());
    }

    @Test
    public void testListTitles() throws Exception {
        java.util.function.Predicate<Book> pricePredicate = b -> b.getPrice() < 1500;
        List<String> qualifiedBookTitles = unit.listTitles(bookDao.findAll(), pricePredicate);
        assertThat(qualifiedBookTitles, containsInAnyOrder("Java Performance", "Head First Java, 2nd Edition", "Docker in Practice"));
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