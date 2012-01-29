//package org.projets4.notifme;
//
//import java.util.ArrayList;
//
//import android.content.ContentResolver;
//import android.content.Context;
//import android.database.Cursor;
//import android.net.Uri;
//import android.util.Log;
//
//public class CalendarActivity {
//	static public void loadCalendar(Context context){
//	    ContentResolver contentResolver = context.getContentResolver();
//
//	    // Fetch a list of all calendars synced with the device, their display names and whether the
//	    // user has them selected for display.
//	    final Cursor cursor = contentResolver.query(Uri.parse("content://calendar/calendars"),
//	            (new String[] { "_id", "displayName", "selected","color" }), null, null, null);
//	    // For a full list of available columns see http://tinyurl.com/yfbg76w
//
//	   // ArrayList<PersonnalCalendar> calendarList = new ArrayList<PersonnalCalendar>();
//	        
//	    while (cursor.moveToNext()) {
//
//	        final String _id = cursor.getString(0);
//	        final String displayName = cursor.getString(1);
//	        final Boolean selected = !cursor.getString(2).equals("0");
//	        final Integer color = cursor.getInt(3);
//	            
//	        Log.e("LoadCalendar", "Id: " + _id + " Display Name: " + displayName + " Selected: " + selected);
//	    }    
//	}
//}