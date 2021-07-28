package com.kaizen_team.kopiku

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
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