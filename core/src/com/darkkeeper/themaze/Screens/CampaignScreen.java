package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.Methods.HuntAndKillMazeGenerator;
import com.darkkeeper.themaze.Methods.MazeGenerator;
import com.darkkeeper.themaze.Methods.PrimMazeGenerator;
import com.darkkeeper.themaze.Methods.RecursiveBacktrackerMazeGenerator;
import com.darkkeeper.themaze.TheMaze;

import java.util.Set;

import javax.swing.ButtonGroup;

/**
 * Created by andreipiatosin on 7/23/15.
 */
public class CampaignScreen implements Screen {

    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;

    private static int arrowWidth = 170;
    private static int arrowHeight = 170;

    private static int playButtonWidth = 200;
    private static int playButtonHeight = 120;

    private Image currenLvlImage;
    private Image currenLvlImage2;
    private Button nextLvlButton;
    private Button prevLvlButton;

    private Mesh mesh;
    private ShaderProgram shader;
    private Texture texture;

    public CampaignScreen() {
        viewPort = new ExtendViewport(TheMaze.WIDTH,TheMaze.HEIGHT);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        Settings.loadResults();
        System.out.println( "currentLvl = " + Settings.currentLevel + " levelsDone = " + Settings.levelsDone );

        rootTable = new Table();
        rootTable.background( Assets.levelChooseBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        addLevelText();
        addLevelChooserButtons();
        addPlayButton();
    }


    private void addLevelChooserButtons () {
        nextLvlButton = new Button ( Assets.skin, "arrow" );
        if ( Settings.currentLevel == Settings.levelsDone + 1 ){
            nextLvlButton.setChecked( true );
        }
        nextLvlButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                changeLevel( Settings.currentLevel + 1 );
            }
        });


        prevLvlButton = new Button ( Assets.skin, "arrow" );
        if ( Settings.currentLevel == 1 ) {
            prevLvlButton.setChecked(true);
        }
        prevLvlButton.setTransform(true);
        prevLvlButton.setOrigin(arrowWidth / 2, arrowHeight / 2);
        prevLvlButton.setRotation(180);
        prevLvlButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                changeLevel(Settings.currentLevel - 1);
            }
        });

        addButton(nextLvlButton, arrowWidth, arrowHeight, 1500, 490);
        addButton( prevLvlButton, arrowWidth, arrowHeight, 1300, 490 );
    }

    private void changeLevel ( int level ) {
        if ( level <= Settings.levelsDone + 1 && level >= 1 ){
            Settings.currentLevel = level;
            Settings.currentLevel = Settings.currentLevel;
            Settings.saveResults();
            addLevelText();

            if ( Settings.currentLevel == Settings.levelsDone + 1 ){
                nextLvlButton.setChecked( true );
                nextLvlButton.setDisabled( true );
            }   else {
                nextLvlButton.setChecked( false );
                nextLvlButton.setDisabled( false );
            }


            if ( Settings.currentLevel == 1 ) {
                prevLvlButton.setChecked( true );
                prevLvlButton.setDisabled( true );
            }   else {
                prevLvlButton.setChecked( false );
                prevLvlButton.setDisabled( false );
            }
        }
    }

    private void addLevelText (){
        if ( currenLvlImage!=null ){
            currenLvlImage.remove();
        }
        if ( currenLvlImage2!=null ){
            currenLvlImage2.remove();
        }
        if ( Settings.currentLevel > 0 && Settings.currentLevel < 10 ) {
            Assets.loadDigit(Settings.currentLevel);
            currenLvlImage = new Image( Assets.digitTextureRegionDrawable );
            currenLvlImage.setSize(80, 130);
            currenLvlImage.setPosition(1730, 680);
            stage.addActor(currenLvlImage);
        }   else if ( Settings.currentLevel > 9 && Settings.currentLevel < 100 ){
            Assets.loadDigit(Settings.currentLevel / 10);
            currenLvlImage = new Image( Assets.digitTextureRegionDrawable );
            currenLvlImage.setSize(80, 130);
            currenLvlImage.setPosition(1690, 680);
            stage.addActor(currenLvlImage);

            Assets.loadDigit( Settings.currentLevel%10 );
            currenLvlImage2 = new Image( Assets.digitTextureRegionDrawable );
            currenLvlImage2.setSize(80, 130);
            currenLvlImage2.setPosition(1770, 680);
            stage.addActor(currenLvlImage2);
        }
    }

    private void addPlayButton () {
        Button playButton = new Button( Assets.skin, "default" );
        playButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();

                TheMaze.game.setScreen( new GameScreen( MazeGenerator.generateNewMaze() ) );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        addButton( playButton, playButtonWidth, playButtonHeight, 1300, 340 );
    }

    private void addButton ( Button button, float width, float height, float x, float y ){
        button.setOrigin( width/2, height/2 );
        button.setPosition( x, y );
        button.setSize( width, height );
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
