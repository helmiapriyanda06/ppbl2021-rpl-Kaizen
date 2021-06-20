package com.ppbl2021_rpl_kaizen.kopiku.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ppbl2021_rpl_kaizen.kopiku.R

/**
 * Implementation of App Widget functionality.
 */
class kopikuwidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int) {
    val views = RemoteViews(context.packageName, R.layout.kopikuwidget)
    val ref = FirebaseDatabase.getInstance()
    val kopi = ref.getReference("Coffee")



    val postListener = object : ValueEventListener {
        override fun onDataChange(it: DataSnapshot) {
            views.setTextViewText(R.id.dataCoffe, it.childrenCount.toString() )
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    kopi.addValueEventListener(postListener)
}