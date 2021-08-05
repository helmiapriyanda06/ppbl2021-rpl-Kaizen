package com.kaizen_team.kopiku

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class LandingScreentest{

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<LandingScreenActivity>(
        LandingScreenActivity::class.java
    )
    @Test
    @Throws(Exception::class)
    fun clickLoginButton_opensLoginUi() {
        onView(withId(R.id.admin))
    }
}