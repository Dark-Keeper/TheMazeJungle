package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

        Button playCampaignButton = new Button( Assets.skin, "default" );
        playCampaignButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new CampaignScreen () );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Button playCustomLevelButton = new Button( Assets.skin, "default" );
        playCustomLevelButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new CustomLevelScreen() );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Button optionsButton = new Button( Assets.skin, "default" );
        optionsButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new OptionsMenuScreen() );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });


        Button exitButton = new Button( Assets.skin, "default" );
        exitButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                TheMaze.exitAddInterface.show();
                //Gdx.app.exit();

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });



        rootTable = new Table();
        rootTable.background( Assets.menuBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        addButton( playCampaignButton, 1150, 760 );
        addButton( playCustomLevelButton, 1150, 585 );
        addButton( optionsButton, 1150, 410 );
        addButton( exitButton, 1150, 235 );
    }

    private void addButton ( Button button, float x, float y ){
        button.setOrigin( buttonWidth/2, buttonHeight/2 );
        button.setPosition( x, y );
        button.setSize( buttonWidth, buttonHeight );
        stage.addActor( button );
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
