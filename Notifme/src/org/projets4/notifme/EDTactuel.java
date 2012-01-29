package org.projets4.notifme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EDTactuel extends Activity {

	private ProgressDialog mSpinner;
	private WebView webview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("L'emploi du temps charge, ça arrive");
        webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://gestionedt.emploisdutempssrc.net/edt/semaine/Web1/2012-01-23");
        webview.setWebViewClient(new SherifWebClient());
        setContentView(webview);
    }
    private class SherifWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSpinner.dismiss();
        }

    }
}
		
		/*this.getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.edtactuel);
		final Activity MyActivity = this;
		getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

		webview = (WebView) findViewById(R.id.WebViewActuel);
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress)   
			{
				MyActivity.setTitle("Loading...");
				MyActivity.setProgress(progress * 100);
				if(progress == 100)
					MyActivity.setTitle("Emploi du temps actuel");
			}
		});

		webview.setWebViewClient(new myWebViewClient());
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.loadUrl("http://gestionedt.emploisdutempssrc.net/edt/semaine/Web1/2012-01-23");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}*/