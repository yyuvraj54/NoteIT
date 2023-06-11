package com.example.noteit



import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.example.noteit.Database.NoteDatabase
import com.example.noteit.Database.NotesRepository



class NoteWidget : AppWidgetProvider() {


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        val repository = NotesRepository(NoteDatabase.getDatabase(context).getNoteDao())



        for (appWidgetId in appWidgetIds) {
            repository.allNotes.observeForever { notes ->
                if (notes.isNotEmpty()) {
                    // Process the retrieved notes and update the widget views
                    val latestNote = notes[notes.size-1]
                    val title = latestNote.title
                    val note = latestNote.note
                    val date = latestNote.date
                    // Update the widget views with the retrieved data
                    updateAppWidget(context, appWidgetManager, appWidgetId, title!!, note!!, date!!)
                }
            }
        }
    }
}



internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    title: String,
    note: String,
    date: String
) {

    Log.d(TAG, "updateAppWidget_: "+title+note+date)

    val views = RemoteViews(context.packageName, R.layout.note_widget)
    views.setTextViewText(R.id.title_txt, title)
    views.setTextViewText(R.id.note_txt, note)
    views.setTextViewText(R.id.date_txt, date)

    appWidgetManager.updateAppWidget(appWidgetId, views)


}
