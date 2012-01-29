//package org.projets4.notifme;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.SystemClock;
//
///**
// * BroadcastReceiver qui est réveillé tous les X temps par une alarme Android,
// * et démarre le service d'update.
// * 
// * Cf {@link AlarmSetterOnBoot} pour l'enregistrement de l'alarme au redémarrage
// * du téléphone.
// */
//public class AlarmReceiver extends BroadcastReceiver {
//
//    private static final int UPDATE_EVERY_HOUR = 1000 * 60 * 60;
//    private static final int START_CHECKING_AFTER_1_MIN = 1000 * 60;
//
//    /**
//     * This should be called when the application first starts
//     */
//    public static void registerAlarm(Context context) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//        PendingIntent pendingItent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
//
//        registerAlarmAndCancelPrevious(alarmManager, pendingItent);
//    }
//
//    private static void registerAlarmAndCancelPrevious(AlarmManager alarmManager, PendingIntent pendingItent) {
//        long nextExecutionTimestamp = SystemClock.elapsedRealtime() + START_CHECKING_AFTER_1_MIN;
//
//        alarmManager.setRepeating( //
//                AlarmManager.ELAPSED_REALTIME_WAKEUP, //
//                nextExecutionTimestamp, //
//                UPDATE_EVERY_HOUR, //
//                pendingItent);
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        WakefulIntentService.acquireStaticLock(context);
//        Intent updateIntent = new Intent(context, UpdateService.class);
//        context.startService(updateIntent);
//    }
//
//}
