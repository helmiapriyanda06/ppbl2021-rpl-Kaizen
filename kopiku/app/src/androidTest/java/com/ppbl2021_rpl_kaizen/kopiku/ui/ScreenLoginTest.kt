package com.ppbl2021_rpl_kaizen.kopiku.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ppbl2021_rpl_kaizen.kopiku.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches



@RunWith(AndroidJUnit4::class)
class ScreenLoginTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<ScreenLogin>(
        ScreenLogin::class.java::class.java
    )

    private val username = "helmi@gmail.com"
    private val password = "helmi123"

    @Test
    fun clickLoginButton_opensLoginUi() {
        onView(withId(R.id.inputUsrname)).perform(ViewActions.typeText(username))
        onView(withId(R.id.inputPassword)).perform(ViewActions.typeText(password))

        onView(withId(R.id.login)).perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(withId(R.id.adminlogo))
            .check(matches(withText("Success")))
    }
}