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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.Methods.HuntAndKillMazeGenerator;
import com.darkkeeper.themaze.Methods.MazeGenerator;
import com.darkkeeper.themaze.Methods.PrimMazeGenerator;
import com.darkkeeper.themaze.Methods.RecursiveBacktrackerMazeGenerator;
import com.darkkeeper.themaze.TheMaze;

/**
 * Created by andreipiatosin on 5/19/15.
 */

public class LevelChooseScreen implements Screen {

    private static int DIFFICULTY_BUTTON_WIDTH   =   200;
    private static int DIFFICULTY_BUTTON_HEIGHT  =   70;
    private static int METHOD_BUTTON_WIDTH       =   200;
    private static int METHOD_BUTTON_HEIGHT      =   70;

    private static int MAZE_SMALL_SIZE_WIDTH    =   13;
    private static int MAZE_SMALL_SIZE_HEIGHT   =   7;
    private static int MAZE_MEDIUM_SIZE_WIDTH   =   22;
    private static int MAZE_MEDIUM_SIZE_HEIGHT  =   12;
    private static int MAZE_LARGE_SIZE_WIDTH    =   36;
    private static int MAZE_LARGE_SIZE_HEIGHT   =   20;


    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;


    private Button recbackMethodButton;
    private Button huntAndKillMethodButton;
    private Button primsMethodButton;

    private Button smallMazeSizeButton;
    private Button medMazeSizeButton;
    private Button largeMazeSizeButton;

    private Button classicSkinSelectButton;
    private Button glanceSkinSelectButton;
    private Button hellSkinSelectButton;

    private int mazeWidth;
    private int mazeHeight;
    
/*    private static int skin;
    private static int CLASSIC_MAZE_SKIN    = 0;
    private static int GLANCE_MAZE_SKIN     = 1;
    private static int HELL_MAZE_SKIN       = 2;*/

    public LevelChooseScreen () {
        viewPort = new ExtendViewport( TheMaze.WIDTH, TheMaze.HEIGHT );
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        rootTable = new Table();
        rootTable.background( Assets.menuBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        stage.setDebugAll( true );

        addMethodButtons();
        addMazeSizeButtons();
        addSkinSelectButtons();

        addPlayButton ();

    }

    private void addSkinSelectButtons () {
        ButtonGroup buttonGroup = new ButtonGroup();

        classicSkinSelectButton = new Button( Assets.skin, "play" );
        glanceSkinSelectButton = new Button( Assets.skin, "play" );
        hellSkinSelectButton = new Button( Assets.skin, "play" );
        initializeCheckerButton( classicSkinSelectButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( glanceSkinSelectButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( hellSkinSelectButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );

        classicSkinSelectButton.setPosition( 200, 600 );
        glanceSkinSelectButton.setPosition( 500, 600 );
        hellSkinSelectButton.setPosition( 800, 600 );

        buttonGroup.add(classicSkinSelectButton);
        buttonGroup.add(glanceSkinSelectButton);
        buttonGroup.add(hellSkinSelectButton);

        buttonGroup.setMaxCheckCount( 1 );
        classicSkinSelectButton.setChecked( true );
    }

    private void addMazeSizeButtons () {
        ButtonGroup buttonGroup = new ButtonGroup();

        smallMazeSizeButton = new Button( Assets.skin, "play" );
        medMazeSizeButton = new Button( Assets.skin, "play" );
        largeMazeSizeButton = new Button( Assets.skin, "play" );
        initializeCheckerButton( smallMazeSizeButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( medMazeSizeButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( largeMazeSizeButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );

        smallMazeSizeButton.setPosition( 200, 800 );
        medMazeSizeButton.setPosition( 500, 800 );
        largeMazeSizeButton.setPosition( 800, 800 );

        buttonGroup.add(smallMazeSizeButton);
        buttonGroup.add(medMazeSizeButton);
        buttonGroup.add(largeMazeSizeButton);

        buttonGroup.setMaxCheckCount( 1 );
        medMazeSizeButton.setChecked( true );
    }

    private void initializeCheckerButton (final Button checkButton, int width, int height) {
        checkButton.setSize( width, height );
        checkButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                checkButton.setChecked(true);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        stage.addActor( checkButton );
    }


    private void addMethodButtons (){
        ButtonGroup buttonGroup = new ButtonGroup();

        recbackMethodButton = new Button( Assets.skin, "play" );
        primsMethodButton = new Button( Assets.skin, "play" );
        huntAndKillMethodButton = new Button( Assets.skin, "play" );
        initializeCheckerButton( recbackMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( primsMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( huntAndKillMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );

        recbackMethodButton.setPosition( 200, 900 );
        primsMethodButton.setPosition( 500, 900 );
        huntAndKillMethodButton.setPosition( 800, 900 );

        buttonGroup.add(recbackMethodButton);
        buttonGroup.add(primsMethodButton);
        buttonGroup.add(huntAndKillMethodButton);

        buttonGroup.setMaxCheckCount( 1 );
        recbackMethodButton.setChecked( true );

    }

    private void addPlayButton () {
        int buttonWidth = 300;
        int buttonHeight = 100;

        Button playButton = new Button( Assets.skin, "play" );
        playButton.setOrigin( buttonWidth/2, buttonHeight/2 );
        playButton.setPosition( TheMaze.WIDTH/2 - buttonWidth/2, TheMaze.HEIGHT/10 );
        playButton.setSize( buttonWidth, buttonHeight );
        playButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                MazeGenerator maze;


                if ( smallMazeSizeButton.isChecked() ){
                    mazeWidth = MAZE_SMALL_SIZE_WIDTH;
                    mazeHeight = MAZE_SMALL_SIZE_HEIGHT;
                }   else    if ( medMazeSizeButton.isChecked() ){
                    mazeWidth = MAZE_MEDIUM_SIZE_WIDTH;
                    mazeHeight = MAZE_MEDIUM_SIZE_HEIGHT;
                }   else    {
                    mazeWidth = MAZE_LARGE_SIZE_WIDTH;
                    mazeHeight = MAZE_LARGE_SIZE_HEIGHT;
                }

                if ( classicSkinSelectButton.isChecked() ){
                    Assets.currentStyleTexture = Assets.classicStyleTexture;
                }   else    if ( glanceSkinSelectButton.isChecked() ){
                    Assets.currentStyleTexture = Assets.winterStyleTexture;
                }   else    {
                    Assets.currentStyleTexture = Assets.hellStyleTexture;
                }


                if ( recbackMethodButton.isChecked() ){
                    maze = new RecursiveBacktrackerMazeGenerator( mazeWidth, mazeHeight, 0, 1 );
                }   else    if ( primsMethodButton.isChecked() ){
                    maze = new PrimMazeGenerator( mazeWidth, mazeHeight, 0, 1 );
                }   else    {
                    maze = new HuntAndKillMazeGenerator( mazeWidth, mazeHeight, 0, 1 );
                }
                maze.generate();
                maze.print(System.out);
                TheMaze.game.setScreen( new GameScreen( maze.getMaze() ) );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stage.addActor( playButton );
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
