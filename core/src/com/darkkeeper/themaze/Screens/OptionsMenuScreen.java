package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
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

        Gdx.input.setInputProcessor(stage);

        Settings.loadSettings();
        System.out.println( "currentLvl = " + Settings.currentLevel + " levelsDone = " + Settings.levelsDone );

        rootTable = new Table();
        rootTable.background( Assets.mainMenuBackgroundTextureRegion );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
