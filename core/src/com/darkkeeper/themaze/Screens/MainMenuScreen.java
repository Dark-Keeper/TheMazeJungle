package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.Utils.Constants;

import java.util.Calendar;


/**
 * Created by andreipiatosin on 5/15/15.
 */
public class MainMenuScreen implements Screen {

    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;

    private static int buttonWidth = 780;
    private static int buttonHeight = 150;


    public MainMenuScreen () {

        viewPort = new ExtendViewport(Constants.APP_WIDTH,Constants.APP_HEIGHT);
        stage = new Stage(viewPort);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
                    dispose();
                    TheMaze.exitAddInterface.show();
                }

                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer( stage, backProcessor );
        Gdx.input.setInputProcessor(multiplexer);

        if ( !Settings.isRated ){
            TheMaze.rateInterface.showRateNotification();
            Settings.saveSettings();
        }

        rootTable = new Table();
        rootTable.background( Assets.mainMenuBackgroundTextureRegion );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );
        addNavigationButtons ();

        checkDaily ();

    }

    private void checkDaily ( ) {
        int currentDayOfTheYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if ( Settings.currentDayOfTheYear < currentDayOfTheYear ){
            showDailyBox( currentDayOfTheYear );
        }
    }

    private void addNavigationButtons () {

        Image playCampaignButton = new Image( Assets.campaignBtnTextureRegion );
        playCampaignButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new CampaignScreen () );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Image playCustomLevelButton = new Image( Assets.customBtnTextureRegion );
        playCustomLevelButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new CustomLevelScreen() );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Image optionsButton = new Image( Assets.optionsBtnTextureRegion );
        optionsButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new OptionsMenuScreen() );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });


        Image helpButton = new Image( Assets.helpBtnTextureRegion );
        helpButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                TheMaze.rateInterface.help();
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        addImage( playCampaignButton, 1150, 760);
        addImage( playCustomLevelButton, 1150, 585 );
        addImage( optionsButton, 1150, 410 );
        addImage( helpButton, 1150, 235 );
    }

    private void showDailyBox ( final int currentDayOfTheYear ) {

        final Image[] checkboxes = new Image[Settings.daysInARow];
        final Image dailyQuestBox = new Image ( Assets.dailyQuestsBoxTextureRegion );
        dailyQuestBox.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                for ( int i = 0; i < Settings.daysInARow; i++ ){
                    checkboxes[i].remove();
                }
                dailyQuestBox.remove();

                if ( Settings.daysInARow < 5) {
                    Settings.daysInARow = Settings.daysInARow + 1;
                }
                Settings.currentDayOfTheYear = currentDayOfTheYear;
                Settings.saveSettings();

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        addImage( dailyQuestBox, stage.getWidth()/2 - dailyQuestBox.getWidth()/2, stage.getHeight()/2 - dailyQuestBox.getHeight()/2 );

        for ( int i = 0; i < Settings.daysInARow; i++ ){
            checkboxes[i] = new Image( Assets.boxCheckerTextureRegion );
            checkboxes[i].setOrigin( Align.center );
            checkboxes[i].setPosition( 415 + i * 293, 543 );
            stage.addActor( checkboxes[i] );
        }

    }

    private void addImage ( Image image, float x, float y ){
        image.setPosition( x, y );
    //    image.setSize( buttonWidth, buttonHeight );
        stage.addActor( image );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //StageSettings.render( stage );
        Gdx.gl.glClearColor( 0, 0, 0, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update( width , height );
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
