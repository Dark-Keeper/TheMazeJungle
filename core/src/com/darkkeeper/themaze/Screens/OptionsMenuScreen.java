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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.Utils.Constants;

/**
 * Created by andreipiatosin on 8/4/15.
 */
public class OptionsMenuScreen implements Screen {
    private Stage stage;
    private Viewport viewport;

    private Table rootTable;

    public OptionsMenuScreen () {
        viewport = new ExtendViewport( Constants.APP_WIDTH, Constants.APP_HEIGHT );
        stage = new Stage( viewport );

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
                    dispose();
                    TheMaze.game.setScreen( new MainMenuScreen() );
                }

                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer( stage, backProcessor );
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);

        Settings.loadSettings();
        System.out.println( "currentLvl = " + Settings.currentLevel + " levelsDone = " + Settings.levelsDone );

        rootTable = new Table();
        rootTable.background( Assets.mainMenuBackgroundTextureRegion );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );
        
        addOptions ();

    }
    
    private void addOptions() {
        
        Image nightMode = new Image( Assets.nightLevelsBtnTextureRegion );
        nightMode.setPosition( 350, 650 );
        stage.addActor( nightMode );        
        final Button nightModeBtn = new Button( Assets.skin, "checkBox" );
        nightModeBtn.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                if ( !nightModeBtn.isChecked() ) {
                    Settings.isNigthLevelsAvailable = true;
                }   else {
                    Settings.isNigthLevelsAvailable = false;                    
                }
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        nightModeBtn.setPosition(1400, 650);
        nightModeBtn.setChecked( Settings.isNigthLevelsAvailable );
        stage.addActor( nightModeBtn );

/*
        final Image soundMode = new Image( Assets.widthBtnTextureRegion );
        soundMode.setPosition( 450, 600 );
        stage.addActor( soundMode );
        final Button soundModeBtn = new Button( Assets.skin, "checkBox" );
        soundModeBtn.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                
                if ( soundModeBtn.isChecked() ){
                    Settings.isSoundEnabled = true;                    
                }   else    {
                    Settings.isSoundEnabled = false;                    
                }


                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        soundModeBtn.setPosition( 1300, 600 );
        soundModeBtn.setChecked( Settings.isSoundEnabled );
        stage.addActor( soundModeBtn );*/


        final Image musicMode = new Image( Assets.musicBtnTextureRegion );
        musicMode.setPosition( 350, 500 );
        stage.addActor( musicMode );
        final Button musicModeBtn = new Button( Assets.skin, "checkBox" );
        musicModeBtn.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                if ( !musicModeBtn.isChecked() ){
                    Settings.isMusicEnabled = true;
                    Assets.menuMusic.setLooping( true );
                    Assets.menuMusic.play();
                }   else    {
                    Settings.isMusicEnabled = false;
                    Assets.menuMusic.stop();
                }

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        musicModeBtn.setPosition(1400, 500);
        musicModeBtn.setChecked( Settings.isMusicEnabled );
        stage.addActor( musicModeBtn );



        final Image vibrationMode = new Image( Assets.vibrationsBtnTextureRegion );
        vibrationMode.setPosition( 350, 350 );
        stage.addActor( vibrationMode );
        final Button vibrationModeBtn = new Button( Assets.skin, "checkBox" );
        vibrationModeBtn.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                if ( !vibrationModeBtn.isChecked() ){
                    Settings.isVibrationEnabled = true;
                }   else    {
                    Settings.isVibrationEnabled = false;
                }

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        vibrationModeBtn.setPosition(1400, 350);
        vibrationModeBtn.setChecked( Settings.isVibrationEnabled );
        stage.addActor( vibrationModeBtn );




        final Image okBtn = new Image( Assets.okBtnTextureRegion );
        okBtn.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                Settings.saveSettings();
                dispose();
                TheMaze.game.setScreen( new MainMenuScreen() );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        okBtn.setPosition( Constants.APP_WIDTH/2 - okBtn.getWidth()/2, 50 );
        stage.addActor( okBtn );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
        Settings.saveSettings();
        stage.dispose();
    }
}
