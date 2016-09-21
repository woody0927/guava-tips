package idv.woody.guava;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by chun-chiao on 2016/9/17.
 */
public class JoinerSplitterTest {
    @Test
    public void testConcatenateIdsGuava() {
        List<Long> ids = Lists.newArrayList(1L, 2L, 5L, null, 10L);
        String concatenatedIdString = Joiner.on(", ").skipNulls().join(ids);
        assertThat(concatenatedIdString, is("1, 2, 5, 10"));
    }

    @Test
    public void testConcatenateIdsJava8() {
        List<Long> ids = Lists.newArrayList(1L, 2L, 5L, null, 10L);
        Predicate<Long> notNullPredicate = id -> id != null;
        java.util.function.Function<Long, String> transformToString = id -> String.valueOf(id);
        String concatenatedIdString = String.join(", ", ids.stream().filter(notNullPredicate).map(transformToString).collect(Collectors.toList()));
        assertThat(concatenatedIdString, is("1, 2, 5, 10"));
    }

    @Test
    public void testSplitIdsGuava() {
        String idsString = "1, 2, 5, , 10";
        List<Long> ids = FluentIterable.from(Splitter.on(",").omitEmptyStrings().trimResults().split(idsString)).transform(new Function<String, Long>() {
            @Override
            public Long apply(String id) {
                return Long.valueOf(id);
            }
        }).toList();
        assertEquals(ids, Arrays.asList(1L, 2L, 5L, 10L));
    }

    @Test
    public void testSplitIdsJava8() {
        String idsString = "1, 2, 5, , 10";
        List<Long> ids = Arrays.asList(idsString.split("\\s*,\\s*")).stream().filter(StringUtils::isNotBlank).map(Long::valueOf).collect(Collectors.toList());
        assertEquals(ids, Arrays.asList(1L, 2L, 5L, 10L));
    }
}
