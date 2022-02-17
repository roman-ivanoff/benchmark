package com.romanivanov.benchmark;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatcher {

    public static <T> Matcher<T> result(final Matcher<T> matcher, final int i) {
        return new BaseMatcher<T>() {

            private int resultIndex = -1;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    resultIndex++;
                    return resultIndex == i;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
            }
        };
    }
}
