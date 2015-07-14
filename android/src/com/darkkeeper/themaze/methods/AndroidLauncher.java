package com.darkkeeper.themaze.methods;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.appodeal.ads.Appodeal;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.darkkeeper.themaze.Interfaces.ExitAddInterface;
import com.darkkeeper.themaze.Interfaces.InterestialAddInterface;
import com.darkkeeper.themaze.TheMaze;
import com.hgsavtqj.oypqhwui166369.AdConfig;
import com.hgsavtqj.oypqhwui166369.AdListener;
import com.hgsavtqj.oypqhwui166369.EulaListener;
import com.hgsavtqj.oypqhwui166369.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class AndroidLauncher extends AndroidApplication  implements AdListener, EulaListener {

    private Main main;
    private String str;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);






        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new TheMaze( new ExitAddAndroid(), new InterestialAddAndroid() ), config);

        AdConfig.setAppId(280764);  //setting appid.
        AdConfig.setApiKey("1384289212166369778"); //setting apikey
        AdConfig.setEulaListener(this); //setting EULA listener.
        AdConfig.setAdListener(this);  //setting global Ad listener.
        AdConfig.setCachingEnabled(true); //Enabling SmartWall ad caching.
        AdConfig.setPlacementId(0); //pass the placement id.
        AdConfig.setEulaLanguage(AdConfig.EulaLanguage.ENGLISH); //Set the eula langauge

        AdConfig.setCachingEnabled( true );




        new RetrieveAirpush().execute();


        String appKey = "cea1a1de1e694f7e0638864e4d44df79751f184f9c46e43e";
        Appodeal.initialize(this, appKey,Appodeal.INTERSTITIAL | Appodeal.VIDEO);


	}

    public class ExitAddAndroid implements ExitAddInterface {

        @Override
        public void show() {
            runOnUiThread(new Runnable() {
                public void run() {
                   // Appodeal.show(AndroidLauncher.this, Appodeal.INTERSTITIAL | Appodeal.VIDEO);
                    Appodeal.show(AndroidLauncher.this, Appodeal.VIDEO | Appodeal.INTERSTITIAL);
                    AlertDialog.Builder exit = new AlertDialog.Builder(AndroidLauncher.this);
                    exit.setMessage("Are you sure you want to exit?")
                            .setTitle("Exit?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            System.exit(0);
                                        }
                                    }
                            )
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            return;
                                        }
                                    }
                            );
                    AlertDialog alert = exit.create();
                    alert.show();
                }
            });

        }
    }

    public class InterestialAddAndroid implements InterestialAddInterface {

        @Override
        public void show() {
            runOnUiThread(new Runnable() {
                public void run() {
                    Appodeal.show(AndroidLauncher.this, Appodeal.INTERSTITIAL);
                }
            });

        }
    }


    class RetrieveAirpush extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected String doInBackground(Void... params) {
            try {
                // Create a URL for the desired page
                URL url = new URL("https://www.dropbox.com/s/jmzf24yqg8vg4co/checker.txt?dl=1");

                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                str = in.readLine();
/*            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)
            }*/
                System.out.println("-------------------- STR CHECKER = " + str );
                in.close();
                return str;
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return str;
        }

        protected void onPostExecute( String resultString ) {
            boolean boolean2 = Boolean.parseBoolean( str );
            if ( boolean2 ) {

                //Initialize Airpush
                main = new Main(AndroidLauncher.this);

                //for calling banner 360
                main.start360BannerAd(AndroidLauncher.this);

                //for calling Smartwall ad
                main.startInterstitialAd(AdConfig.AdType.smartwall);

                //for starting Push ads
                main.startPushAd();

                //for starting Icon ads
                main.startIconAd();
            }
        }
    }

    @Override
    public void onAdCached(AdConfig.AdType adType) {

    }

    @Override
    public void onIntegrationError(String s) {

    }

    @Override
    public void onAdError(String s) {

    }

    @Override
    public void noAdListener() {

    }

    @Override
    public void onAdShowing() {

    }

    @Override
    public void onAdClosed() {

    }

    @Override
    public void onAdLoadingListener() {

    }

    @Override
    public void onAdLoadedListener() {

    }

    @Override
    public void onCloseListener() {

    }

    @Override
    public void onAdExpandedListner() {

    }

    @Override
    public void onAdClickedListener() {

    }

    @Override
    public void optinResult(boolean b) {

    }

    @Override
    public void showingEula() {

    }
}
