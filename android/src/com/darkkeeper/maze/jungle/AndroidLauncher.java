package com.darkkeeper.maze.jungle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import com.appodeal.ads.Appodeal;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.darkkeeper.themaze.Interfaces.ShareInterface;
import com.darkkeeper.themaze.Interfaces.ExitAddInterface;
import com.darkkeeper.themaze.Interfaces.InterestialAddInterface;
import com.darkkeeper.themaze.Interfaces.RateInterface;
import com.darkkeeper.themaze.TheMaze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;


public class AndroidLauncher extends AndroidApplication {

    private static final String TAG = "LOGS";
    private static final String COMMERCIAL_COUNTER = "002";
    private static final String IS_RATED           = "003";
  //  private Main main;
    private String str;
    private Activity activity;
    private Handler exitHandler;
    private Handler interestialHandler;
    private Handler rateHandler;
    private Handler helpHandler;

    private SharedPreferences prefs;
/*    @Override
    protected void onResume (){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onResume();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new TheMaze(new ExitAddAndroid(), new InterestialAddAndroid(), new ShareAndroid(), new RateAndroid()), config);

/*        AdConfig.setAppId(286218);  //setting appid.
        AdConfig.setApiKey("1384289212166369778"); //setting apikey
        AdConfig.setEulaListener(this); //setting EULA listener.
        AdConfig.setAdListener(this);  //setting global Ad listener.
        AdConfig.setCachingEnabled(true); //Enabling SmartWall ad caching.
        AdConfig.setPlacementId(0); //pass the placement id.
        AdConfig.setEulaLanguage(AdConfig.EulaLanguage.ENGLISH); //Set the eula langauge

        AdConfig.setCachingEnabled( true );*/


      //  new RetrieveAirpush().execute();


        String appKey = "e8c3bf2214f41efa8577173b7ef963ce68cb93d36ec98784";
        Appodeal.initialize(this, appKey);
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
                if ( message.what == 0 ) {
                    Appodeal.show(AndroidLauncher.this, Appodeal.INTERSTITIAL);
                } else if ( message.what == 1 ){
                    Appodeal.hide(activity, Appodeal.BANNER_BOTTOM);
                }   else if ( message.what == 2 ){
                    Appodeal.show(activity, Appodeal.BANNER_BOTTOM);
                }
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

        prefs = getPreferences( MODE_PRIVATE );

        final boolean[] isRated = {prefs.getBoolean(IS_RATED, false)};

        if ( !isRated[0]){
            AlertDialog.Builder rate = new AlertDialog.Builder(AndroidLauncher.this);
            rate.setMessage("You like the game? Rate it on the Store!" + "\n" +
                    "Thank you")
                    .setTitle("Rate it!")
                    .setCancelable(false)
                    .setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent( Intent.ACTION_VIEW );
                                    i.setData( Uri.parse( "https://play.google.com/store/apps/details?id=" + activity.getPackageName() ) );
                                    startActivity(i);

                                    isRated[0] = true;
                                    SharedPreferences.Editor ed = prefs.edit();
                                    ed.putBoolean( IS_RATED, isRated[0] );
                                    ed.commit();

                                    return;
                                }
                            }
                    )
                    .setNeutralButton("Later",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    isRated[0] = false;
                                    SharedPreferences.Editor ed = prefs.edit();
                                    ed.putBoolean( IS_RATED, isRated[0] );
                                    ed.commit();

                                    return;
                                }
                            }
                    )
                    .setPositiveButton("Never",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    isRated[0] = true;
                                    SharedPreferences.Editor ed = prefs.edit();
                                    ed.putBoolean( IS_RATED, isRated[0] );
                                    ed.commit();

                                    return;
                                }
                            }
                    );
            AlertDialog alert = rate.create();
            alert.show();
        }

    }

    public void showHelpDialog () {

        AlertDialog.Builder rate = new AlertDialog.Builder(AndroidLauncher.this);


        String helpMessage = "Thank you for downloading Maze!" + "\n" + "\n" +
                "You should find the key and use it to open the door before the time runs out. " +
                "Use SWIPE or arrows on the screen to move your character. " + "\n" + "\n" +
                "Login every day to collect your time bonuses. Use them to add additional 15 seconds to the current level." + "\n" + "\n" +
                "You can toggle zoom by clicking the bottom right loop button. If you dont like night mode, you can disable it in options. " + "\n" + "\n" +
                "Report any bug to apiatosin@gmail.com and leave five stars on the store! " + "\n" + "\n" +
                "Have fun!" + "\n" + "\n" +
                "Developer: Andrei Piatosin"
                ;

        rate.setMessage( helpMessage )
                .setTitle("Help")
                .setCancelable(false)
                .setNegativeButton("Rate me!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent( Intent.ACTION_VIEW );
                                i.setData( Uri.parse( "https://play.google.com/store/apps/details?id=" + activity.getPackageName() ) );
                                startActivity(i);
                            }
                        }
                )
                .setNeutralButton("Report",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sendEmail();
                                return;
                            }
                        }
                )
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick (DialogInterface dialog, int id){
                                return;
                            }
                        }
                );

        AlertDialog alert = rate.create();
        alert.show();

    }

    private void sendEmail(){

        Intent myIntent1 = new Intent(android.content.Intent.ACTION_SEND);
        myIntent1.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"apiatosin@gmail.com"});
        final String my1 = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        final String my2 = android.os.Build.DEVICE;
        final String my3 = android.os.Build.MANUFACTURER;
        final String my4 = android.os.Build.MODEL;
        final String my5 = android.os.Build.VERSION.RELEASE;
        final int my6 = android.os.Build.VERSION.SDK_INT;
        final String my7 = android.os.Build.BRAND;
        final String my8 = android.os.Build.VERSION.INCREMENTAL;
        final String my9 = android.os.Build.PRODUCT;
        myIntent1.putExtra(android.content.Intent.EXTRA_SUBJECT, "Support Request: " + my1 + " Device: " + my2 + " Manufacturer: " + my3 + " Model: " + my4 + " Version: " + my5 + " SDK: " + my6 + " Brand: " + my7 + " Incremental: " + my8 + " Product: " + my9);
        myIntent1.setType("text/plain");
//IN CASE EMAIL APP FAILS, THEN DEFINE THE OPTION TO LAUNCH SUPPORT WEBSITE
        String url2 = "";
        Intent myIntent2 = new Intent(Intent.ACTION_VIEW);
        myIntent2.setData(Uri.parse(url2));
//IF USER CLICKS THE OK BUTTON, THEN DO THIS
        try {
// TRY TO LAUNCH TO EMAIL APP
            activity.startActivity(Intent.createChooser(myIntent1, "Send email to Developer"));
//                                                          startActivity(myIntent1);
        } catch (ActivityNotFoundException ex) {
// ELSE LAUNCH TO WEB BROWSER
         //   activity.startActivity(myIntent2);
        }

    }

    public class ExitAddAndroid implements ExitAddInterface {
        @Override
        public void show() {

            Message message = exitHandler.obtainMessage(0, null);
            message.sendToTarget();

        }
    }

    public class InterestialAddAndroid implements InterestialAddInterface {

        @Override
        public void show() {

            prefs = getPreferences(MODE_PRIVATE);
            int counter = prefs.getInt( COMMERCIAL_COUNTER, 0 );
            counter++;
            if ( counter%3 == 1 ) {
                Message message = interestialHandler.obtainMessage( 0 );
                message.sendToTarget();
            }
            SharedPreferences.Editor ed = prefs.edit();
            ed.putInt( COMMERCIAL_COUNTER, counter );
            ed.commit();

        }

        @Override
        public void hideBanner() {
            Message message = interestialHandler.obtainMessage( 1 );
            message.sendToTarget();
        }

        @Override
        public void showBanner() {
            Message message = interestialHandler.obtainMessage( 2 );
            message.sendToTarget();
        }
    }

    public class RateAndroid implements RateInterface {

        @Override
        public void showRateNotification() {
        //    final boolean[] isRated = {false};

            prefs = getPreferences(MODE_PRIVATE);
            int counter = prefs.getInt( COMMERCIAL_COUNTER, 0 );
            if ( counter%3 == 0 ) {

                Message message = rateHandler.obtainMessage( 0, null );
                message.sendToTarget();

            }
/*            SharedPreferences.Editor ed = prefs.edit();
            ed.putInt( COMMERCIAL_COUNTER, counter );
            ed.commit();*/

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

/*
    class RetrieveAirpush extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected String doInBackground(Void... params) {
            try {
                // Create a URL for the desired page
                URL url = new URL("https://www.dropbox.com/s/jmzf24yqg8vg4co/checker.txt?dl=1");

                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                str = in.readLine();
*//*            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)
            }*//*
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

    }*/
}
