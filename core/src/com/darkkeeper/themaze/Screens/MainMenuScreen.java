package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
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


/**
 * Created by andreipiatosin on 5/15/15.
 */
public class MainMenuScreen implements Screen {


    private Camera camera;
    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;

    //Touch areas
    private Rectangle playGame;




    public MainMenuScreen () {

        viewPort = new ExtendViewport(TheMaze.WIDTH,TheMaze.HEIGHT);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        System.out.println("mainMenuScreen");
        
        int buttonWidth = 780;
        int buttonHeight = 320;
        
        Button playButton = new Button( Assets.skin, "default" );
        playButton.setOrigin( buttonWidth/2, buttonHeight/2 );
        playButton.setPosition( TheMaze.WIDTH/2 - buttonWidth/2 + 25, 565 );
        playButton.setSize( buttonWidth, buttonHeight );
        playButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new LevelChooseScreen() );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Button exitButton = new Button( Assets.skin, "default" );
        exitButton.setOrigin( buttonWidth/2, buttonHeight/2 );
        exitButton.setPosition( TheMaze.WIDTH/2 - buttonWidth/2 + 25, 213 );
        exitButton.setSize( buttonWidth, buttonHeight );
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

        stage.addActor( playButton );
      //  stage.addActor( rateButton );
        stage.addActor( exitButton );



/*        TextButton playButton = new TextButton( "Play", Assets.textButtonStyle );

        playButton.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TheMaze.game.setScreen( new LevelChooseScreen() );
            }
        });

        rootTable = new Table();
        rootTable.background( Assets.menuBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );
        rootTable.setDebug( true );
        stage.setDebugAll( true );

        rootTable.add( playButton ).expand().center();*/

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
        Settings.save ();
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
