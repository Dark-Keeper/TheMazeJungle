package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.MazeGenerator;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.TheMaze;

/**
 * Created by andreipiatosin on 5/19/15.
 */

public class LevelChooseScreen implements Screen {

    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;



    public LevelChooseScreen () {
        viewPort = new ExtendViewport(1920,1080);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        System.out.println("levelChooseScreen");

        rootTable = new Table();
        rootTable.background( Assets.menuBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        stage.setDebugAll( true );

        for ( int i = 0; i < 5; i++ ){
            TextButton playButton = new TextButton( "Play", Assets.textButtonStyle );

            playButton.addListener( new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    MazeGenerator maze = new MazeGenerator(31,17,0);
                    maze.display();
                    TheMaze.game.setScreen( new GameScreen( maze.getMaze() ) );
                }
            });

            rootTable.add( playButton ).expand().center();
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.act( delta );
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update( width, height );
    }

    @Override
    public void pause() {
        Settings.save();
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
