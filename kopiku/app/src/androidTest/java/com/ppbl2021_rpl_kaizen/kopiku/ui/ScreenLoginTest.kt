package com.ppbl2021_rpl_kaizen.kopiku.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ppbl2021_rpl_kaizen.kopiku.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScreenLoginTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<ScreenLogin>(
        ScreenLogin::class.java
    )
    @Test
    @Throws(Exception::class)
    fun clickLoginButton_opensLoginUi() {
        onView(withId(R.id.login))
    }

}