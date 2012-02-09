package org.projets4.notifme;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.projets4.notifme.R;

public class Infos extends Activity {

	public static final int ID_NOTIFICATION = 2011;
	private static final int CODE_RETOUR = 1;
	
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.infos);
		
		/*****R�cup�ration de la date courante*****/
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		String s = f.format(d);
		Toast.makeText(Infos.this, "la date de d�bu de l'analyse :" + s, Toast.LENGTH_LONG).show();

		/********Code pour les notifications**********/
		final Button boutonCreateNotif = (Button) findViewById(R.id.CreateNotif);
		Button boutonClearNotif = (Button) findViewById(R.id.ClearNotif);  

		boutonCreateNotif.setOnClickListener(new Button.OnClickListener()  {
			public String datatext;
			private BufferedReader in0;
			private BufferedReader in1;
			protected String ligne;
			protected String ligne1;
			protected String[] elements0 = new String[4];//1:dateD�but	2:dateFin	3:Type	4:Salle	5:cours
			protected String[] elements1 = new String[4];
			private BufferedReader inCherche;
			private String cline;
			private String LOG_TAG;
			private String data;
			private Notification notif;

			public void onClick(View v) {

					if(v==boutonCreateNotif){
					//httpget
					try {
						data= getPage("http://gestionedt.emploisdutempssrc.net/edt/ical/SRC/Web1/basic.ics");
						//datatext.append(data);
						Toast.makeText(getApplicationContext(), data,Toast.LENGTH_SHORT).show();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "basic not downloaded",Toast.LENGTH_SHORT).show(); 

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
					//ercrire dans le fichier basic1
					WriteData(getApplicationContext(), data,"basic1.ics");

					LireText(getApplicationContext());
					
					//cr�ation de la notification
					//createNotify(datatext);
					Toast.makeText(Infos.this, datatext, Toast.LENGTH_LONG).show();

					}
					
				
						
					
					
				}

				private void LireText(Context context) {
					// Lis le fichier ICS et effectue la comparaison de blocs
					try {
						//initialisation des flux pour la lecture des fichiers
						in0 = new BufferedReader(new InputStreamReader(context.getAssets().open("basic0.ics")));//context.openFileInput("basic0.ics")));//activity.getAssets().open("basic0.ics")));
						in1 = new BufferedReader(new InputStreamReader(context.openFileInput("basic1.ics")));//activity.getAssets().open("basic0.ics")));
						//fIn = context.openFileInput("settings.dat");
						//Lecture de la premi�re ligne
						ligne=in0.readLine();
						ligne1=in1.readLine();

						//D�but de la boucle de tests
						while(/*!ligne1.equals("END:VCALENDAR")*/ ligne != null && ligne1 != null){//lecture tant que la fin du fichier n'est pas atteinte

							//enregistrement des blocs
							elements0=saveBloc(ligne,in0);
							elements1=saveBloc(ligne1,in1);
							


							if(ligne==null){
								//le bloc a disparru
								datatext += "des nouveau cours ont �t� ajout�s";
							}
							//comparaison  des blocs Upload� et Originaux
							if(!elements0[0].equals(elements1[0])/* && elements1[0]!= null*/ ){
								String[] elTest = new String [4];
								
								//la date a chang�
								elTest=chercheBloc(elements0[0], context);


								inCherche.close();

								

								if(ligne==null){
									//le bloc a disparru
									datatext += "des nouveau cours ont �t� ajout�s";
								}
								if(elTest[0]==null){
									//le bloc a disparru
									datatext += "ce cours a �t� supprim�"+elements0[0];
								}
								else if((!elements0[1].equals(elTest[1]))){
									//le type� chang�
									datatext += "le cours du "+elTest[0]+" est maintenant un "+elTest[1];

								}
								else if((!elements0[2].equals(elTest[2]))){
									//le cours � chang�

									datatext += " le cours du "+elTest[0]+" de "+elements0[2]+"a �t� remplac� par le "+elTest[1]+" de "+elTest[2];
								}
								else if((!elements0[3].equals(elTest[3]))){
									//la salle � chang�
									datatext += " la salle cours du "+elTest[0]+"est maintenant: "+elTest[3];
								}
								datatext += "\n";
							}
							ligne=in0.readLine();
							ligne1=in1.readLine();
						}

					



						//Fermeture des Flux de Fichier
						in0.close();
						in1.close();


					} catch (IOException e) {
						// Erreur d'entr�e sortie
						e.printStackTrace();
						/*
						//in = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(0x7f040000)));//activity.getAssets().open("basic0.ics")));
						try {
							String data0 =new String();
							data0="";
							in.readLine();
							while(in.readLine() != null){
								data0=data0+in.readLine()+"\n";//possibilit� de beug avec le "\n" !!! 
								in.readLine();
							}
							in.close();
							WriteData(getApplicationContext(),data0,"baisc0.ics");
							LireText(getApplicationContext());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/


					}

				}


				private String[] chercheBloc(String date,Context context) {
					// cette m�thode cherche le bloc de la version upload� correspondant � sa date en parcourant le fichier avec un nouveau flux
					//retourne le bloc  contenant correspondant sous forme de tableau se String
					try {
						inCherche = new BufferedReader(new InputStreamReader(context.openFileInput("basic1.ics")));
						cline=inCherche.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					Boolean dateTrouve=false;

					while((cline != null  && !cline.equals("END:VCALENDAR") ) && (dateTrouve==false)){
						try {
							cline=inCherche.readLine();

						} catch (IOException e) {
							e.printStackTrace();
						}
						if( cline != null && cline.length()>35){
							if((cline.substring(26, 34).concat(cline.substring(35))).equals(date)){
								dateTrouve=true;
							}
						}
					}
					String[] el1= new String[4];
					if(dateTrouve){				
						el1=saveBloc(cline, inCherche);
					}
					return el1;
				}

				protected String[] saveBloc(String line, BufferedReader in) {
					// m�thode d'enregistrement de des blocs fonctionnant ligne par ligne (ce n'est pas cette methode qui fait avancer la lecture des lignes
					String [] el = new String [4];

					while (!line.equals("END:VEVENT") &&( !line.equals("END:VCALENDAR") && line != null )) {//lecture d'un bloc (jusq'� la ligne VEVENT)


						// r�cup�ration de l'identifiant de la ligne
						String idLigne=line.substring(0, 5);

						if (idLigne.equals("DTSTA")) {//test date de commencement

							//r�cup�ration de la date de d�but du bloc
							el[0]=(line.substring(26, 34).concat(line.substring(35)));
						}
						else if (idLigne.equals("SUMMA")) {

							//r�cup�ration du Type 
							el[1]=(line.substring(10, 12));

							int a= nextSpace(line,13);
							//int b= nextSpace(line,a);
							//r�cup�ration du Cours
							el[2]=(line.substring(13, a));

							//r�cup�ration de la Salle
							el[3]=(line.substring(a+1));

						}
						try {
							line=in.readLine();
							//datatext.append(line+"\n");
						} catch (IOException e) {
							// rapporte une erreur de lecture
							e.printStackTrace();
						}
					}

					return el;
				}

				private int nextSpace(String line, int i) {
					// cette m�thode parcours la ligne � partir de a pour connaitre le prochain espace
					while(line.charAt(i)!=' ' && i<(line.length()-1) ){
						if((line.charAt(i)=='.') && ((!(line.substring(line.length()-8)).equals("AmphiSRC") && !(line.substring(line.length()-4)).equals("100F"))) && ((line.length()-i)>5) && (i<line.length())){
							i++;
						}
						i++;
					}
					return i;
				}


				public String getPage(String url) throws ClientProtocolException, IOException{
					StringBuffer stringBuffer = new StringBuffer("");
					BufferedReader bufferedReader = null;

					try{

						//Cr�ation d'un DefaultHttpClient et un HttpGet permettant d'effectuer une requ�te HTTP de type GET
						HttpClient httpClient = new DefaultHttpClient();
						HttpGet httpGet = new HttpGet();

						//Cr�ation de l'URI et on l'affecte au HttpGet
						URI uri = new URI(url);
						httpGet.setURI(uri);

						//Execution du client HTTP avec le HttpGet
						HttpResponse httpResponse = httpClient.execute(httpGet);

						//On r�cup�re la r�ponse dans un InputStream
						InputStream inputStream = httpResponse.getEntity().getContent();

						//On cr�e un bufferedReader pour pouvoir stocker le r�sultat dans un string
						bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

						//On lit ligne � ligne le bufferedReader pour le stocker dans le stringBuffer
						String ligneCodeHTML = bufferedReader.readLine();
						while (!ligneCodeHTML.equals("END:VCALENDAR") && ligneCodeHTML != null){
							stringBuffer.append(ligneCodeHTML);
							stringBuffer.append("\n");
							ligneCodeHTML = bufferedReader.readLine();


							//this.datatext.append(ligneCodeHTML+"\n");
						}      

					}catch (Exception e){

						Log.e(LOG_TAG, e.getMessage());
					}finally{
						//Dans tous les cas on ferme le bufferedReader s'il n'est pas null
						if (bufferedReader != null){
							try{
								bufferedReader.close();
							}catch(IOException e){
								Log.e(LOG_TAG, e.getMessage());        
							}
						}
					}

					//On retourne le stringBuffer
					return stringBuffer.toString();
				}


				public void WriteData(Context context, String data, String fichier){ 
					FileOutputStream fOut = null; 
					OutputStreamWriter osw = null; 

					try{ 
						fOut = context.openFileOutput(fichier,MODE_WORLD_WRITEABLE);//MODE_APPEND);       
						osw = new OutputStreamWriter(fOut); 
						osw.write(data);  
						osw.flush(); 
						//popup surgissant pour le r�sultat
						//Toast.makeText(context, "Settings saved",Toast.LENGTH_SHORT).show(); 
					} 
					catch (Exception e) {       
						Toast.makeText(context, "Settings not saved",Toast.LENGTH_SHORT).show(); 
					} 
					finally { 
						try { 
							osw.close(); 
							fOut.close(); 
						} catch (IOException e) { 
							Toast.makeText(context, "Settings not saved",Toast.LENGTH_SHORT).show(); 
						} 
					} 
				

			
				
				
				
				
				
				
				
			
			}
		});

		boutonClearNotif.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cancelNotify();
			}
		}); 
	}


	/********M�thode qui cr��e la notification
	 * @param datatext **********/
	private void createNotify(String datatext){
		//On cr�� un "gestionnaire de notification"
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);        

		
		//On cr�� la notification avec une ic�ne et du texte d�filant (optionnel, si pas d�filant : argument � null)
		Notification notification = new Notification(R.drawable.plus_24, "Des changements d'emplois du temps sont survenus!", System.currentTimeMillis());  

		//Le PendingIntent permet d'atteindre l'activity Notification
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, Notification.class), 0);
		String titreNotification = "Des changements d'emplois du temps sont survenus!";
		String texteNotification = datatext;         

		//On configure notre notification avec tous les param�tres que l'on vient de cr�er
		notification.setLatestEventInfo(this, titreNotification, texteNotification, pendingIntent);

		//Style de vibration � la notif avec 0sec de pause, 0.2sec de vibration, 0.1sec de pause, 0.2sec de vibration, 0.1sec de pause, 0.2sec de vibration
		//notification.vibrate = new long[] {0,200,100,200,100,200};
		//Ou sinon utiliser les fonctions de bases de l'utilisation :
		notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
		//notification.flags |= Notification.;
		
		
		//Enfin on ajoute notre notification et son ID � notre gestionnaire de notification
		notificationManager.notify(ID_NOTIFICATION, notification);

	}

	/********Methode pour supprimer de la liste de notification la notif que l'on vient de cr�er**********/ 
	private void cancelNotify(){
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(ID_NOTIFICATION);
	}


	private void getPreferences() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

		((TextView)findViewById(R.id.prefGroupe1)).setText("Le lien vers votre groupe TP : " + preferences.getString("linkgroupe", ""));
		((TextView)findViewById(R.id.prefGroupe2)).setText("Vous �tes en : " + preferences.getString("namegroupe", ""));
		((TextView)findViewById(R.id.prefSounds)).setText("Sons activ�s : " + preferences.getBoolean("sounds", true));
		((TextView)findViewById(R.id.prefVibrate)).setText("Sonnerie : " + preferences.getBoolean("vibrate", true));
		((TextView)findViewById(R.id.prefNotifications)).setText("Vibreur : " + preferences.getBoolean("notifications", true));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CODE_RETOUR) {
			Toast.makeText(this, "Modifications termin�es", Toast.LENGTH_SHORT).show();
			getPreferences();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}