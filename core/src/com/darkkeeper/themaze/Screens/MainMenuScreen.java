package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
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
        Gdx.input.setInputProcessor(stage);

        Image playCampaignButton = new Image( Assets.playBtnTextureRegion );
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


        Image rateButton = new Image( Assets.rateBtnTextureRegion );
        rateButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

           //     TheMaze.exitAddInterface.show();
                //Gdx.app.exit();

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        rootTable = new Table();
        rootTable.background( Assets.mainMenuBackgroundTextureRegion );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        addImage( playCampaignButton, 1150, 760 );
        addImage( playCustomLevelButton, 1150, 585 );
        addImage( optionsButton, 1150, 410 );
        addImage( rateButton, 1150, 235 );
    }

    private void addImage ( Image image, float x, float y ){
        image.setOrigin( image.getWidth()/2, image.getHeight()/2 );
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
