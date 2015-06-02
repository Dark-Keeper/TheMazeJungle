package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.Basics.StageSettings;
import com.darkkeeper.themaze.TheMaze;


/**
 * Created by andreipiatosin on 5/15/15.
 */
public class MainMenuScreen implements Screen {

    public static float WIDTH = 1920;
    public static float HEIGHT = 1080;

    private Camera camera;
    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;

    //Touch areas
    private Rectangle playGame;




    public MainMenuScreen () {

        viewPort = new ExtendViewport(1920,1080);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        System.out.println("mainMenuScreen");

        TextButton playButton = new TextButton( "Play", Assets.textButtonStyle );

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

        rootTable.add( playButton ).expand().center();

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
