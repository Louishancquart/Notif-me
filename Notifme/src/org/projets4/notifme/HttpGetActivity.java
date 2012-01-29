package org.projets4.notifme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HttpGetActivity extends NotifMeActivity {
	public static final String LOG_TAG = "HttpGet";
	public static final String URL_DATE_UPDATE = "http://gestionedt.emploisdutempssrc.net/edt/ical/SRC/SRC2A1/basic.ics";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Affichage des données dans un toast
		Toast toast = Toast.makeText(getApplicationContext(), getHttpGet(URL_DATE_UPDATE), Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * Méthode qui retourne les données désignées par l'url.
	 * @param url Adresse internet.
	 * @return Contenu des données sous la forme d’une chaîne de caractères.
	 */
	public String getHttpGet(String url) {
		StringBuffer stringBuffer = new StringBuffer("");
		BufferedReader bufferedReader = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet();
			URI uri = new URI(url);
			httpGet.setURI(uri);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream inputStream = httpResponse.getEntity().getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String readLine = bufferedReader.readLine();
			while (readLine != null) {
				stringBuffer.append(readLine);
				stringBuffer.append("\n");
				readLine = bufferedReader.readLine();
			}
		} 
		catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		} 
		finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}
		}
		return stringBuffer.toString();
	}
}