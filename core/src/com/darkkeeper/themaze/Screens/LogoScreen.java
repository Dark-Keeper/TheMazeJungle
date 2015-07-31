package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.TheMaze;

/**
 * Created by andreipiatosin on 5/20/15.
 */
public class LogoScreen implements Screen {
    private Stage stage;
    private Viewport viewPort;

    public LogoScreen (){
        viewPort = new ExtendViewport(TheMaze.WIDTH,TheMaze.HEIGHT);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        Assets.loadLogo();

        Table rootTable = new Table();
        rootTable.setFillParent( true );
        rootTable.background( Assets.logoBackground );

        stage.addActor( rootTable );

    }


    private void loadGameAssets () {
        Settings.loadSettings();
        Assets.loadMenu();
        Assets.loadGame();
        Assets.loadFonts();
    }

    public void loadMenuScreen () {
        TheMaze.game.setScreen( new MainMenuScreen() );
    }

    @Override
    public void show() {
        stage.addAction( Actions.sequence (
                Actions.run(new Runnable() {
                    public void run() {
                        loadGameAssets();
                    }
                }),
                Actions.delay( 1.5f ),
                Actions.run(new Runnable() {
            public void run () {
                loadMenuScreen();
            }
        }) ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
        stage.act( delta );
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
