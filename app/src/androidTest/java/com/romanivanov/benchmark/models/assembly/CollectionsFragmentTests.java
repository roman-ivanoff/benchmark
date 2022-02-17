package com.romanivanov.benchmark.models.assembly;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.romanivanov.benchmark.CustomMatcher.result;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.romanivanov.benchmark.App;
import com.romanivanov.benchmark.R;
import com.romanivanov.benchmark.di.DaggerTestAppComponent;
import com.romanivanov.benchmark.di.TestAppModule;
import com.romanivanov.benchmark.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CollectionsFragmentTests {

    @Before
    public void setup() {
        App.getInstance().setAppComponent(
                DaggerTestAppComponent.builder().testAppModule(new TestAppModule()).build()
        );
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void listIsCorrect() {
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.arraylist))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.linkedlist))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.copylist))));

        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.search_by_value))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.adding_in_the_beginning))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.adding_in_the_middle))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.adding_in_the_end))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.removing_in_the_beginning))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.removing_in_the_middle))));
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText(R.string.removing_in_the_end))));
    }

    @Test
    public void invalidInputTest() {
        Espresso.onView(result(withId(R.id.etSizeCollectionFragment), 0))
                .perform(replaceText("..."));
        Espresso.onView(result(withId(R.id.btnCalculateCollectionFragment), 0)).perform(click());
        Espresso.onView(result(withId(R.id.etSizeCollectionFragment), 0))
                .check(matches(withText(R.string.enter_correct_size)));
    }

    @Test
    public void measurementsCompleted() {
        Espresso.onView(result(withId(R.id.etSizeCollectionFragment), 0))
                .perform(typeText("222"));

        Espresso.onView(result(withId(R.id.btnCalculateCollectionFragment), 0)).perform(click());
        Espresso.closeSoftKeyboard();
        waitAfterChangeTab();
        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(hasDescendant(withText("3"))));
    }

    @Test
    public void measurementsHasBeenInterrupted() {
        Espresso.onView(result(withId(R.id.etSizeCollectionFragment), 0))
                .perform(typeText("5555555"));
        Espresso.closeSoftKeyboard();

        Espresso.onView(result(withId(R.id.btnCalculateCollectionFragment), 0)).perform(click());
        Espresso.onView(result(withId(R.id.btnClear), 0)).perform(click());
        waitAfterChangeTab();

        Espresso.onView(result(withId(R.id.rv), 0)).check(matches(not(isDisplayed())));
    }

    private void waitAfterChangeTab() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}
