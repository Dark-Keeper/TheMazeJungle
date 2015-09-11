package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.Utils.Constants;

/**
 * Created by andreipiatosin on 5/20/15.
 */
public class LogoScreen implements Screen {
    private Stage stage;
    private Viewport viewPort;
    private ProgressBar.ProgressBarStyle barStyle;
    private ProgressBar bar;

    public LogoScreen (){
        viewPort = new ExtendViewport(Constants.APP_WIDTH,Constants.APP_HEIGHT);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

/*        barStyle = new ProgressBar.ProgressBarStyle(Assets.skin.newDrawable("white", Color.DARK_GRAY), Assets.sliderTextureRegionDrawable);
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, 10, 0.5f, false, barStyle);
        bar.setSize( 1000, 200 );
        bar.setPosition( Constants.APP_WIDTH/2 - bar.getWidth()/2, Constants.APP_HEIGHT/2 - bar.getHeight()/2 );
                stage.addActor( bar );*/

       // Assets.loadLogo();
        Texture logoTexture = new Texture( "logo/logo.jpg" );
        Image logoImage = new Image( logoTexture );
        logoImage.setSize( 512, 256 );
        logoImage.setPosition( stage.getWidth()/2 - logoImage.getWidth()/2, stage.getHeight()/2 - logoImage.getHeight()/2 );

        stage.addActor( logoImage );


/*        Table rootTable = new Table();
        rootTable.setFillParent( true );
        rootTable.background( Assets.logoBackground );


        stage.addActor( rootTable );*/

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
                Actions.delay( 1.0f ),
                Actions.run(new Runnable() {
                    public void run() {
                        loadGameAssets();
                    }
                }),
                Actions.delay( 2.0f ),
                Actions.run(new Runnable() {
            public void run () {
                loadMenuScreen();
            }
        }) ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0, 0, 0, 1);
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
