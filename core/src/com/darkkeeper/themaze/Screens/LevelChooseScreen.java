package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.darkkeeper.themaze.Actors.Cell;
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

    private static int DIFFICULTY_BUTTON_WIDTH   =   400;
    private static int DIFFICULTY_BUTTON_HEIGHT  =   144;
    private static int METHOD_BUTTON_WIDTH       =   390;
    private static int METHOD_BUTTON_HEIGHT      =   144;

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

    private Cell[][] cells;

    private static float START_X = 1273;
    private static float START_Y = 1040;

    public LevelChooseScreen () {
        viewPort = new ExtendViewport( TheMaze.WIDTH, TheMaze.HEIGHT );
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        rootTable = new Table();
        rootTable.background( Assets.levelChooseBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        //stage.setDebugAll( true );

        addMethodButtons();
        addMazeSizeButtons();
        addSkinSelectButtons();

        addPlayButton ();

        displayMaze( false );

    }




    private void displayMaze ( boolean isSettingGameScreen ) {

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


        MazeGenerator maze;
        if ( !isSettingGameScreen ) {
            if (recbackMethodButton.isChecked()) {
                maze = new RecursiveBacktrackerMazeGenerator(mazeHeight, mazeWidth, 0, 1);
            } else if (primsMethodButton.isChecked()) {
                maze = new PrimMazeGenerator(mazeHeight, mazeWidth, 0, 1);
            } else {
                maze = new HuntAndKillMazeGenerator(mazeHeight, mazeWidth, 0, 1);
            }
        }   else    {
            if (recbackMethodButton.isChecked()) {
                maze = new RecursiveBacktrackerMazeGenerator(mazeWidth, mazeHeight, 0, 1);
            } else if (primsMethodButton.isChecked()) {
                maze = new PrimMazeGenerator(mazeWidth, mazeHeight, 0, 1);
            } else {
                maze = new HuntAndKillMazeGenerator(mazeWidth, mazeHeight, 0, 1);
            }
        }
        maze.generate();
       // maze.print(System.out);

        if ( isSettingGameScreen ){
            cells = new Cell[mazeWidth][mazeHeight];

            cells = maze.getMaze( false );
            dispose();
            TheMaze.game.setScreen( new GameScreen( cells ) );
        }   else    {
            cells = new Cell[mazeHeight][mazeWidth];

            cells = maze.getMaze( true );
         //   START_X = 1273;
            START_Y = 1062;
            START_Y -= cells[0][0].height;
        //    START_X += cells[0][0].width;

            for ( int i = 0; i < cells.length; i ++ ){
                for ( int j = 0; j < cells[0].length; j ++ ){
                    cells[i][j].setOrigin( Cell.width/2, Cell.height/2 );
                    cells[i][j].setPosition( START_X + Cell.width * j, START_Y - Cell.height * i );
                    stage.addActor( cells[i][j] );
                }
            }
        }
    }

    private void initializeCheckerButton (final Button checkButton, int width, int height) {
        checkButton.setSize( width, height );
        checkButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                checkButton.setChecked(true);

                for ( int i = 0; i < cells.length; i ++ ){
                    for ( int j = 0; j < cells[0].length; j ++ ){
                        cells[i][j].remove();
                    }
                }

                displayMaze( false );

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        stage.addActor( checkButton );
    }


    private void addMethodButtons (){
        ButtonGroup buttonGroup = new ButtonGroup();

        recbackMethodButton = new Button( Assets.skin, "default" );
        primsMethodButton = new Button( Assets.skin, "default" );
        huntAndKillMethodButton = new Button( Assets.skin, "default" );
        initializeCheckerButton( recbackMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( primsMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( huntAndKillMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );

        recbackMethodButton.setPosition( 47, 771 );
        primsMethodButton.setPosition( 421, 771 );
        huntAndKillMethodButton.setPosition( 801, 771 );

        buttonGroup.add(recbackMethodButton);
        buttonGroup.add(primsMethodButton);
        buttonGroup.add(huntAndKillMethodButton);

        buttonGroup.setMaxCheckCount( 1 );
        recbackMethodButton.setChecked( true );

    }

    private void addMazeSizeButtons () {
        ButtonGroup buttonGroup = new ButtonGroup();

        smallMazeSizeButton = new Button( Assets.skin, "default" );
        medMazeSizeButton = new Button( Assets.skin, "default" );
        largeMazeSizeButton = new Button( Assets.skin, "default" );
        initializeCheckerButton( smallMazeSizeButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( medMazeSizeButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( largeMazeSizeButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );

        smallMazeSizeButton.setPosition( 47, 545 );
        medMazeSizeButton.setPosition( 421, 545 );
        largeMazeSizeButton.setPosition( 801, 545 );

        buttonGroup.add(smallMazeSizeButton);
        buttonGroup.add(medMazeSizeButton);
        buttonGroup.add(largeMazeSizeButton);

        buttonGroup.setMaxCheckCount( 1 );
        smallMazeSizeButton.setChecked( true );
    }

    private void addSkinSelectButtons () {
        ButtonGroup buttonGroup = new ButtonGroup();

        classicSkinSelectButton = new Button( Assets.skin, "default" );
        glanceSkinSelectButton = new Button( Assets.skin, "default" );
        hellSkinSelectButton = new Button( Assets.skin, "default" );
        initializeCheckerButton( classicSkinSelectButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( glanceSkinSelectButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );
        initializeCheckerButton( hellSkinSelectButton, DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT );

        classicSkinSelectButton.setPosition( 47, 280 );
        glanceSkinSelectButton.setPosition( 421, 280 );
        hellSkinSelectButton.setPosition( 801, 280 );

        buttonGroup.add(classicSkinSelectButton);
        buttonGroup.add(glanceSkinSelectButton);
        buttonGroup.add(hellSkinSelectButton);

        buttonGroup.setMaxCheckCount( 1 );
        classicSkinSelectButton.setChecked( true );
    }


    private void addPlayButton () {
        int buttonWidth = 500;
        int buttonHeight = 190;

        Button playButton = new Button( Assets.skin, "default" );
        playButton.setOrigin( buttonWidth/2, buttonHeight/2 );
        playButton.setPosition( 365, 8 );
        playButton.setSize( buttonWidth, buttonHeight );
        playButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                displayMaze( true );

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
        Gdx.gl.glClearColor( 0, 0, 0, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.act( delta );
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            this.dispose();
            TheMaze.game.setScreen( new MainMenuScreen() );
        }
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
