package topjava.grad;

import org.assertj.core.api.Assertions;

public class TestMatcher<T> {
    private final String[] ignoredFields;


    public TestMatcher(String...ignoredFields) {
        this.ignoredFields = ignoredFields;
    }

    public void assertMatch(T actual, T expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, ignoredFields);
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields(ignoredFields).isEqualTo(expected);
    }
}
