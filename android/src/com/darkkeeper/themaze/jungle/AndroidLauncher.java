package com.darkkeeper.themaze.jungle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;

import com.appodeal.ads.Appodeal;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.darkkeeper.themaze.Interfaces.ShareInterface;
import com.darkkeeper.themaze.Interfaces.ExitAddInterface;
import com.darkkeeper.themaze.Interfaces.InterestialAddInterface;
import com.darkkeeper.themaze.Interfaces.RateInterface;
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
import java.util.ArrayList;
import java.util.List;


public class AndroidLauncher extends AndroidApplication  implements AdListener, EulaListener {

    private Main main;
    private String str;
    private Activity activity;
    private Handler exitHandler;
    private Handler interestialHandler;
    private Handler rateHandler;
    private Handler helpHandler;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new TheMaze( new ExitAddAndroid(), new InterestialAddAndroid(), new ShareAndroid(), new RateAndroid() ), config);

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

        activity = this;



        rateHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                showRateDialog();
            }
        };

        helpHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                showHelpDialog();
            }
        };

        exitHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                showExitDialog();
            }
        };

        interestialHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Appodeal.show(AndroidLauncher.this, Appodeal.INTERSTITIAL);
            }
        };



	}

    public void showExitDialog () {

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

    public void showRateDialog () {

        AlertDialog.Builder rate = new AlertDialog.Builder(AndroidLauncher.this);
        rate.setMessage("You like the game? Rate it on the Store!" + "\n" +
                "Thank you")
                .setTitle("Rate it!")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent( Intent.ACTION_VIEW );
                                i.setData( Uri.parse( "https://play.google.com/store/apps/details?id=" + activity.getPackageName() ) );
                                startActivity(i);
                                //   isRated[0] = true;
                                return;
                            }
                        }
                )
                .setNeutralButton("Later",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //    isRated[0] = false;
                                return;
                            }
                        }
                )
                .setNegativeButton("Never",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //    isRated[0] = true;
                                return;
                            }
                        }
                );
        AlertDialog alert = rate.create();
        alert.show();
    }

    public void showHelpDialog () {

        AlertDialog.Builder rate = new AlertDialog.Builder(AndroidLauncher.this);


        String helpMessage = "Thank you for downloading Maze!" + "\n" + "\n" +
                "Use arrows to move your character. If you dont like night mode in Campaign you can disable it in options. "
                ;

        rate.setMessage( helpMessage )
                .setTitle("Help")
                .setCancelable(false)
                .setPositiveButton("Rate me!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent( Intent.ACTION_VIEW );
                                i.setData( Uri.parse( "https://play.google.com/store/apps/details?id=" + activity.getPackageName() ) );
                                startActivity(i);
                            }
                        }
                )
                .setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        }
                );
        AlertDialog alert = rate.create();
        alert.show();

    }

    public class ExitAddAndroid implements ExitAddInterface {
        @Override
        public void show() {

            Message message = exitHandler.obtainMessage( 0, null );
            message.sendToTarget();

        }
    }

    public class InterestialAddAndroid implements InterestialAddInterface {

        @Override
        public void show() {

            Message message = interestialHandler.obtainMessage( 0, null );
            message.sendToTarget();

        }
    }

    public class RateAndroid implements RateInterface {

        @Override
        public void showRateNotification() {
        //    final boolean[] isRated = {false};

            Message message = rateHandler.obtainMessage( 0, null );
            message.sendToTarget();
         //   return isRated[0];
        }

        @Override
        public void help() {

            Message message = helpHandler.obtainMessage( 0, null );
            message.sendToTarget();

        }
    }

    public class ShareAndroid implements ShareInterface {

        @Override
        public void share( int currentLvl ){

            PackageManager pm = activity.getPackageManager();

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);

            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShare.setType("text/plain");
                    if (info.activityInfo.packageName.toLowerCase().contains("facebook") || info.activityInfo.name.toLowerCase().contains("facebook")||info.activityInfo.packageName.toLowerCase().contains("twitter") || info.activityInfo.name.toLowerCase().contains("twitter")|| info.activityInfo.packageName.toLowerCase().contains("vk") || info.activityInfo.name.toLowerCase().contains("vk")) {
                        targetedShare.putExtra(Intent.EXTRA_TEXT,"Hey, I have completed "+currentLvl+" level on \""+activity.getResources().getString(R.string.app_name)+"\". Can you beat it? "+ "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                activity.startActivity(chooserIntent);
            }
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
