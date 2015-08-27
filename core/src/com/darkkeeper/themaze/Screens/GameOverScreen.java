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
import com.darkkeeper.themaze.Methods.MazeGenerator;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.Utils.Constants;

/**
 * Created by andreipiatosin on 7/23/15.
 */
public class GameOverScreen implements Screen {

    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;

    private static int buttonWidth = 780;
    private static int buttonHeight = 150;

    public GameOverScreen (){
        viewPort = new ExtendViewport( Constants.APP_WIDTH, Constants.APP_HEIGHT );
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        Settings.loadResults();


        rootTable = new Table();
        rootTable.background( Assets.gameOverBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        addButtons ();
    }



    private void addButtons() {

        Button nextButton = new Button( Assets.skin, "default" );
        nextButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                Settings.currentLevel+=1;
                TheMaze.game.setScreen( new GameScreen( MazeGenerator.generateNewMaze() ) );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });;

        Button menuButton = new Button( Assets.skin, "default" );
        menuButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new MainMenuScreen() );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });;

        Button restartButton = new Button( Assets.skin, "default" );
        restartButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.game.setScreen( new GameScreen( MazeGenerator.generateNewMaze() ) );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });;

        Button shareButton = new Button( Assets.skin, "default" );
        shareButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                TheMaze.shareInterface.share( Settings.currentLevel );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });;

        addButton( nextButton, 1150, 760 );
        addButton( menuButton, 1150, 585 );
        addButton( restartButton, 1150, 410 );
        addButton( shareButton, 1150, 235 );


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
        Gdx.gl.glClearColor( 0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
