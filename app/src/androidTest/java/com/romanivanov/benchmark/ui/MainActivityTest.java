package com.romanivanov.benchmark.ui;

import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.romanivanov.benchmark.CustomMatcher.result;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.tabs.TabLayout;
import com.romanivanov.benchmark.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.rxjava3.annotations.NonNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void tabsAreClickable() {
        Espresso.onView(withId(R.id.tabLayout)).check(matches(hasDescendant(withText(R.string.lists))));
        Espresso.onView(withId(R.id.tabLayout)).check(matches(hasDescendant(withText(R.string.maps))));
        Espresso.onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(0));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(isDisplayed()));
        Espresso.onView(result(withId(R.id.rv), 1)).check(matches(not(isDisplayed())));

        Espresso.onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(1));
        waitAfterChangeTab();

        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(not(isDisplayed())));
        Espresso.onView(result(withId(R.id.rv), 1)).check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(0));
    }

    @Test
    public void tabsAreSwipeable() {
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(isDisplayed()));
        Espresso.onView(result(withId(R.id.rv), 1)).check(matches(not(isDisplayed())));

        Espresso.onView(withId(R.id.viewPager)).perform(swipeLeft());
        waitAfterChangeTab();

        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(not(isDisplayed())));
        Espresso.onView(result(withId(R.id.rv), 1)).check(matches(isDisplayed()));
    }

    private void waitAfterChangeTab() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    @NonNull
    private ViewAction selectTabAtPosition(final int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return "with tab at index" + String.valueOf(position);
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof TabLayout) {
                    TabLayout tabLayout = (TabLayout) view;
                    TabLayout.Tab tab = tabLayout.getTabAt(position);

                    if (tab != null) {
                        tab.select();
                    }
                }
            }
        };
    }

    public static void swipeToLeft() {
        Espresso.onView(withId(R.id.viewPager)).perform(swipeLeft());
    }

    public static void swipeToRight() {
        Espresso.onView(withId(R.id.viewPager)).perform(swipeRight());
    }
}
