package idv.woody.bookstore.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by chun-chiao on 2016/8/29.
 */
public class Book implements Comparable<Book> {
    private Long id;
    private String title;
    private String publisher;
    private int price;

    public Book(String title, String publisher, int price) {
        this.title = title;
        this.publisher = publisher;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public int compareTo(Book book) {
        return ComparisonChain.start().
                compare(this.getPublisher(), book.getPublisher(), Ordering.natural().nullsLast()).
                compare(this.getPrice(), book.getPrice()).result();
    }

//    @Override
//    public int compareTo(Book book) {
//        int i = this.getPublisher() == null ?
//                (book.getPublisher() == null ? 0 : Integer.MIN_VALUE) :
//                (book.getPublisher() == null ? Integer.MAX_VALUE : this.getPublisher().compareTo(book.getPublisher()));
//        if (i != 0) {
//            return i;
//        }
//
//        return Integer.compare(this.getPrice(), book.getPrice());
//    }
}
