package org.projets4.notifme;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class Notification extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//On créé un TextView en Java
		TextView txt=new TextView(this);
		txt.setText("Voici l'Activity qui apparait lorsque l'on clique sur la notification !");

		//On ajoute notre TextView a la vue
		setContentView(txt);

		//On supprime la notification de la liste de notification comme dans la m�thode cancelNotify de l'Activity principale
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NotifMeActivity.ID_NOTIFICATION);
	}
}