package topjava.grad;

import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static topjava.grad.TestUtil.readListFromJsonMvcResult;

public class TestMatcher<T> {
    private final String[] ignoredFields;
    private final Class<T> clazz;

    public TestMatcher(Class<T> clazz, String...ignoredFields) {
        this.ignoredFields = ignoredFields;
        this.clazz = clazz;
    }

    public void assertMatch(T actual, T expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, ignoredFields);
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields(ignoredFields).isEqualTo(expected);
    }

    public ResultMatcher jsonListMatcher(Iterable<T> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, clazz), expected);
    }

    public ResultMatcher jsonMatcher(T... expected) {
        return jsonListMatcher(List.of(expected));
    }


}
