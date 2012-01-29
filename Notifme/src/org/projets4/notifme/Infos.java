package org.projets4.notifme;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Infos extends Activity {

	public static final int ID_NOTIFICATION = 2011;
	private static final int CODE_RETOUR = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.infos);

		/********Code pour les notifications**********/
		Button boutonCreateNotif = (Button) findViewById(R.id.CreateNotif);
		Button boutonClearNotif = (Button) findViewById(R.id.ClearNotif);  

		boutonCreateNotif.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createNotify();
			}
		});

		boutonClearNotif.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cancelNotify();
			}
		}); 
	}


	/********Méthode qui créée la notification**********/
	private void createNotify(){
		//On créé un "gestionnaire de notification"
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);        

		//On créé la notification avec une icône et du texte défilant (optionnel, si pas défilant : argument à null)
		Notification notification = new Notification(R.drawable.plus_24, "Toc toc, c'est une notification !", System.currentTimeMillis());  

		//Le PendingIntent permet d'atteindre l'activity Notification
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, Notification.class), 0);
		String titreNotification = "C'est moi la notification !";
		String texteNotification = "Je suis une belle notification...";         

		//On configure notre notification avec tous les paramètres que l'on vient de créer
		notification.setLatestEventInfo(this, titreNotification, texteNotification, pendingIntent);

		//Style de vibration à la notif avec 0sec de pause, 0.2sec de vibration, 0.1sec de pause, 0.2sec de vibration, 0.1sec de pause, 0.2sec de vibration
		//notification.vibrate = new long[] {0,200,100,200,100,200};
		//Ou sinon utiliser les fonctions de bases de l'utilisation :
		notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
		
		//Enfin on ajoute notre notification et son ID à notre gestionnaire de notification
		notificationManager.notify(ID_NOTIFICATION, notification);
	}

	/********Methode pour supprimer de la liste de notification la notif que l'on vient de créer**********/ 
	private void cancelNotify(){
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(ID_NOTIFICATION);
	}


	private void getPreferences() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

		((TextView)findViewById(R.id.prefGroupe1)).setText("Le lien vers votre groupe TP : " + preferences.getString("linkgroupe", ""));
		((TextView)findViewById(R.id.prefGroupe2)).setText("Vous êtes en : " + preferences.getString("namegroupe", ""));
		((TextView)findViewById(R.id.prefSounds)).setText("Sons activés : " + preferences.getBoolean("sounds", true));
		((TextView)findViewById(R.id.prefVibrate)).setText("Sonnerie : " + preferences.getBoolean("vibrate", true));
		((TextView)findViewById(R.id.prefNotifications)).setText("Vibreur : " + preferences.getBoolean("notifications", true));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CODE_RETOUR) {
			Toast.makeText(this, "Modifications terminées", Toast.LENGTH_SHORT).show();
			getPreferences();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}