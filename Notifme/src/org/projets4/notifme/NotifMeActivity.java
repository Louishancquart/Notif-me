package org.projets4.notifme;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class NotifMeActivity extends TabActivity implements OnClickListener {

	@SuppressWarnings("unused")
	private static final CharSequence s = null;
	//private ListView maListViewPerso;
	public static final int ID_NOTIFICATION = 2011;
	private static final int CODE_RETOUR = 1;
	EditText edit;
	Button button;
//	String url = "http://gestionedt.emploisdutempssrc.net/edt/ical/SRC/SRC2A1/basic.ics";
//	String filename = basic.ics;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Service pour tâche de fond
		//startService(new Intent(NotifMeActivity.this, NotifMeService.class));

		/*****Récupération de la date courante*****/
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		String s = f.format(d);
		Toast.makeText(NotifMeActivity.this, s, Toast.LENGTH_LONG).show();


		/******Code pour la tabHost******/
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		Intent intent;  // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, Infos.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("Infos").setIndicator("Infos", res.getDrawable(R.drawable.ic_infos_tab)).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, EDTactuel.class);
		spec = tabHost.newTabSpec("Edt actuel").setIndicator("Edt actuel", res.getDrawable(R.drawable.ic_edtactuel_tab)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, EDTsuivant.class);
		spec = tabHost.newTabSpec("Edt prochain").setIndicator("Edt prochain", res.getDrawable(R.drawable.ic_edtsuivant_tab)).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
	/******Fin du OnCreate******/


	/********Création du menu**********/
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.layout.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	/********Ecouteur des boutons du menu**********/
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
		case R.id.preference:
			try {
				startActivityForResult(new Intent(NotifMeActivity.this, Preference.class), CODE_RETOUR);
				return true;
			}
			catch(Exception e)
			{
				Log.e("Mon erreur : ", e.getMessage());
			}
		case R.id.upload:
			Intent intentGet = new Intent(Intent.ACTION_VIEW, Uri.parse("http://gestionedt.emploisdutempssrc.net/edt/ical/SRC/SRC2A1/basic.ics"));
			startActivity(intentGet);
			return true;
		case R.id.quitter:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//	//http://gestionedt.emploisdutempssrc.net/edt/ical/SRC/SRC2A1/basic.ics
	//	public static void downloadFile(String url, String filename)
	//			throws IOException {
	//
	//		final URLConnection conn = new URL(url).openConnection();
	//		conn.connect();
	//
	//		final InputStream input = new BufferedInputStream(conn.getInputStream());
	//		final OutputStream output = new FileOutputStream(filename);
	//
	//		final byte data[] = new byte[1024];
	//
	//		int count;
	//		while ((count = input.read(data)) != -1)
	//			output.write(data, 0, count);
	//
	//		output.flush();
	//		output.close();
	//		input.close();
	//	}


	public void onClick(View v) {

	}
}

////La listview
//			maListViewPerso = (ListView) findViewById(R.id.listviewperso);
//		    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
//		    HashMap<String, String> map;
//			setContentView(R.layout.listpref);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 1A1");
//		    map.put("description", "Client de courrier ï¿½lectronique");
//		    map.put("img", String.valueOf(R.drawable.word));
//		    listItem.add(map);
//
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 1A2");
//		    map.put("img", String.valueOf(R.drawable.excel));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 1B1");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 1B2");
//		    map.put("img", String.valueOf(R.drawable.outlook));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 1C1");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 1C2");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 2A1");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 2A2");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 2B1");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 2B2");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    map = new HashMap<String, String>();
//		    map.put("titre", "SRC 2C1");
//		    map.put("img", String.valueOf(R.drawable.powerpoint));
//		    listItem.add(map);
//
//		    SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageitem,
//		    		new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
//
//		    maListViewPerso.setAdapter(mSchedule);
//
//		    maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
//		    	@SuppressWarnings("unchecked")
//		    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//		    		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
//		    		AlertDialog.Builder adb = new AlertDialog.Builder(NotifMeActivity.this);
//		    		adb.setTitle("Sélection Item");
//		    		adb.setMessage("Votre choix : "+map.get("titre"));
//		    		adb.setPositiveButton("Ok", null);
//		    		adb.show();
//		    	}
//		    });