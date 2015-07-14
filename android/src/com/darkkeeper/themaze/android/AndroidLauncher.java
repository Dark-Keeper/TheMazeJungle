package com.darkkeeper.themaze.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.darkkeeper.themaze.TheMaze;
import com.hgsavtqj.oypqhwui166369.AdConfig;
import com.hgsavtqj.oypqhwui166369.AdListener;
import com.hgsavtqj.oypqhwui166369.EulaListener;
import com.hgsavtqj.oypqhwui166369.Main;


public class AndroidLauncher extends AndroidApplication  implements AdListener, EulaListener {

    private Main main;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        AdConfig.setAppId(226047);  //setting appid.
        AdConfig.setApiKey("1384289212166369778"); //setting apikey
        AdConfig.setEulaListener(this); //setting EULA listener.
        AdConfig.setAdListener(this);  //setting global Ad listener.
        AdConfig.setCachingEnabled(true); //Enabling SmartWall ad caching.
        AdConfig.setPlacementId(0); //pass the placement id.
        AdConfig.setEulaLanguage(AdConfig.EulaLanguage.ENGLISH); //Set the eula langauge

        AdConfig.setCachingEnabled( true );

        //Initialize Airpush
        main=new Main(this);

        //for calling banner 360
        main.start360BannerAd(this);

        //for calling Smartwall ad
        main.startInterstitialAd(AdConfig.AdType.smartwall);

        //for starting Push ads
        main.startPushAd();

        //for starting Icon ads
        main.startIconAd();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new TheMaze(), config);
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
