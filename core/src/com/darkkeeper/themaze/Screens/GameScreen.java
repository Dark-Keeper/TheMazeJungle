package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Actors.Cell;
import com.darkkeeper.themaze.Actors.Controlls;
import com.darkkeeper.themaze.Actors.Flag;
import com.darkkeeper.themaze.Actors.Key;
import com.darkkeeper.themaze.Actors.Player;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.Methods.MazeGenerator;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.Utils.Constants;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by andreipiatosin on 5/15/15.
 */
public class GameScreen implements Screen {
    private Viewport viewPort;

    private Player player;
    private Flag flag;
    private Key key;

    private boolean isPlayerWithKey = false;

    private static float START_X = 0;
    private static float START_Y = 1080;


    private Controlls controllLeft;
    private Controlls controllUp;
    private Controlls controllDown;
    private Controlls controllRight;

    private boolean isShowingControlls = false;
    private boolean isShowingKeyTip = false;
    private boolean keyTipWasShown = false;
    private Button keyTipButton;

    private boolean isNight = false;

    private Cell[][] maze;


    private World world; // contains the game world's bodies and actors.
   // private GameRenderer renderer; // our custom game renderer.
    private Stage stage; // stage that holds the GUI. Pixel-exact size.
    private OrthographicCamera guiCam; // camera for the GUI. It's the stage default camera.


    public static final Vector2 GRAVITY = new Vector2(0, 0);
    private RayHandler rayHandler;
    private PointLight pointLight;

    public GameScreen (MazeGenerator mazeGenerator ) {
        viewPort = new ExtendViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage = new Stage(viewPort);


        //BETTA_OPTIONS
        Settings.isLvlWithKey = true;



        world = new World( GRAVITY, true );


        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
                    dispose();
//                    TheMaze.interestialAddInterface.show();
                    TheMaze.game.setScreen( new MainMenuScreen() );
                 }

                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer( stage, backProcessor );
        Gdx.input.setInputProcessor(multiplexer);

        Gdx.input.setCatchBackKey(true);


        this.maze = mazeGenerator.getMaze( world );

        START_Y = 1080;
        START_Y -= maze[0][0].height;
        displayMaze();


        player = new Player( world, maze[1][1].getX(), maze[1][1].getY(), maze );
        stage.addActor( player );

        flag = new Flag(  maze [ maze.length - 2 ][ maze[ 0 ].length - 2 ].getX(),  maze[ maze.length-2 ] [ maze[0].length-2 ].getY() );
        stage.addActor( flag );

        if ( Settings.isLvlWithKey ){

            int i = 0;
            int j = 0;
            while ( maze [i][j].isWall || maze [i][j] == maze [ maze.length - 2 ][ maze[0].length - 2 ] ){
                i = (int)(Math.random() * ( maze.length - 1 ) / 2 + maze.length/2);
                j = (int)(Math.random() * ( maze[0].length - 1 ) / 2  + maze[0].length/2);
            }

            key = new Key(  maze [ i ][ j ].getX(),  maze[ i ][ j ].getY() );
            stage.addActor( key );
        }   else {
            key = null;
        }

        System.out.println (" ------------------- key = " + key );

       // isNight = true;

        if ( isNight ) {
            rayHandler = new RayHandler(world);
            rayHandler.setCombinedMatrix(stage.getCamera().combined);
            new PointLight( rayHandler, 4, new Color ( 1,1,1,1), 200 , flag.getX() + flag.getWidth()/2, flag.getY()+ flag.getHeight()/2 );
            pointLight = new PointLight(rayHandler, 6, new Color(1, 1, 1, 1), 360, Constants.APP_WIDTH / 2, Constants.APP_HEIGHT / 2);
           // pointLight.setSoftnessLength(0);
        }
    }

    private void displayMaze () {
        for ( int i = 0; i < maze.length; i ++ ){
            for ( int j = 0; j < maze[0].length; j ++ ){
                maze[i][j].setPosition( START_X + Cell.width * j, START_Y - Cell.height * i );
             //   maze[i][j].setBody( START_X + Cell.width * j, START_Y - Cell.height * i );
                stage.addActor( maze[i][j] );
            }
        }
    }

    public void setGameScreenUI () {
        int backgroundPartsAmount = 7;

        for ( int i = 0; i < backgroundPartsAmount; i ++ ){
            Image bottomBarBackground  = new Image( Assets.bottomBarBackground );
            bottomBarBackground.setSize( Constants.APP_WIDTH/backgroundPartsAmount, Constants.BOTTOM_BAR_HEIGHT + Cell.height/4 );
            bottomBarBackground.setPosition( i * bottomBarBackground.getWidth(), 0 );
            stage.addActor( bottomBarBackground );
        }

        Button exitButton = new Button( Assets.skin, "bbexitButton" );
        exitButton.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                TheMaze.game.setScreen( new MainMenuScreen() );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        } );
        exitButton.setSize( Constants.BOTTOM_BAR_ICON_SIZE, Constants.BOTTOM_BAR_ICON_SIZE );
        exitButton.setPosition( Constants.APP_WIDTH - ( Constants.BOTTOM_BAR_ICON_SIZE + Constants.BOTTOM_BAR_ICON_PADDING_LEFT ), 0 );
        stage.addActor( exitButton );

        Button zoomButton = new Button( Assets.skin, "bbzoomButton" );
        zoomButton.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                TheMaze.game.setScreen( new MainMenuScreen() );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        } );
        zoomButton.setSize( Constants.BOTTOM_BAR_ICON_SIZE, Constants.BOTTOM_BAR_ICON_SIZE );
        zoomButton.setPosition( Constants.APP_WIDTH - 2 * ( Constants.BOTTOM_BAR_ICON_SIZE + Constants.BOTTOM_BAR_ICON_PADDING_LEFT ), 0 );
        stage.addActor( zoomButton );

    }



    public void showControlls () {
        isShowingControlls = true;
        player.isTouchingDoor = false;

        if ( player.isNoWallUp() ){
            controllUp = new Controlls( player, "north" );
            controllUp.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    player.mooveUp();
                    System.out.println("clicked");
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            stage.addActor( controllUp );
        }


        if ( player.isNoWallRight() ){
            controllRight = new Controlls( player, "east" );
            controllRight.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    player.mooveRight();
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            stage.addActor( controllRight );
        }


        if ( player.isNoWallDown() ){
            controllDown = new Controlls( player, "south" );
            controllDown.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    player.mooveDown();
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            stage.addActor( controllDown );
        }


        if ( player.isNoWallLeft() ){
            controllLeft = new Controlls( player, "west" );
            controllLeft.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    player.mooveLeft();
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            stage.addActor( controllLeft );
        }
    }

    public void hideControlls () {
        if (controllDown!=null){
            controllDown.clear();
            controllDown.remove();
        }
        if (controllLeft!=null){
            controllLeft.clear();
            controllLeft.remove();
        }
        if (controllRight!=null){
            controllRight.clear();
            controllRight.remove();
        }
        if (controllUp!=null){
            controllUp.clear();
            controllUp.remove();
        }
        isShowingControlls = false;
    }

    private void showKeyTip () {
        isShowingKeyTip = true;
        keyTipButton = new Button( Assets.skin, "keyTip" );
        keyTipButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                keyTipButton.remove();
                isShowingKeyTip = false;
                keyTipWasShown = true;
            }
        } );
        keyTipButton.setSize( 600, 800 );
        keyTipButton.setPosition( Constants.APP_WIDTH/2 - keyTipButton.getWidth()/2, Constants.APP_HEIGHT/2 - keyTipButton.getHeight()/2 );
        stage.addActor( keyTipButton );
    }


    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

        stage.act(delta);
        stage.draw();

        if ( isNight ) {
            pointLight.setPosition(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
            rayHandler.updateAndRender();
            world.step(1 / 60f, 6, 2);
        }


        if ( key != null ) {
            if ((int) player.getX() == (int) key.getX() && (int) player.getY() == (int) key.getY()) {
                key.remove();
                isPlayerWithKey = true;
            }
        }


        if ( (int) player.getX() == (int) flag.getX() && (int) player.getY() == (int) flag.getY() ){
            if ( key != null ) {
                if ( isPlayerWithKey ) {
                    TheMaze.interestialAddInterface.show();
                    Settings.levelsDone += 1;
                    Settings.saveResults();
                    TheMaze.game.setScreen(new GameOverScreen());
                }   else if ( !isShowingKeyTip && !keyTipWasShown ) {
                        player.isTouchingDoor = true;
                        showKeyTip ();
                }
            }   else {
                TheMaze.interestialAddInterface.show();
                Settings.levelsDone += 1;
                Settings.saveResults();
                TheMaze.game.setScreen(new GameOverScreen());
            }
        }

        if ( !player.isMoovingToNextIntersection && !isShowingControlls){
            showControlls();
        }   else if ( player.isMoovingToNextIntersection && isShowingControlls )   {
            hideControlls();
        }

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
        if (isNight) {
            rayHandler.dispose();
        }
        stage.dispose();
    }
}


