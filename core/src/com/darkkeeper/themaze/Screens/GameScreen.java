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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Timer;
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
public class GameScreen implements Screen, GestureDetector.GestureListener {
    private Viewport viewPort;

    private Player player;
    private Flag flag;
    private Key key;

    private float epsilon;
    private boolean isPlayerWithKey = false;

    private static float START_X = 0;
    private static float START_Y = 1080;

    private boolean isSwipeEnabled;


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
    private Stage stageHUD;
    private OrthographicCamera guiCam; // camera for the GUI. It's the stage default camera.


    public static final Vector2 GRAVITY = new Vector2(0, 0);
    private RayHandler rayHandler;
    private PointLight pointLightDoor;
    private PointLight pointLightPlayer;
    private float randomForLight;

    private boolean isZoomed = false;
    private float totalTime;
    private Label timerText;
    private Label stopWatchesAmountText;
    private boolean timerIsOn;

    private Table uiTable;
    private float uiTableElementWidth;
    private float uiTableElementHeight;

    Vector3 last_touch_down = new Vector3();

    public GameScreen ( MazeGenerator mazeGenerator ) {
        viewPort = new ExtendViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage = new Stage(viewPort);
        stageHUD = new Stage( new ExtendViewport( Constants.APP_WIDTH, Constants.APP_HEIGHT ) );


        //BETTA_OPTIONS
        Settings.isLvlWithKey = true;
        world = new World( GRAVITY, true );

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
                    dispose();
                    TheMaze.interestialAddInterface.show();
                    TheMaze.game.setScreen( new GameOverScreen( false ) );
                 }
                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer( new GestureDetector( 0.0f, 0.0f, 0.0f, 5f, this ), stage, stageHUD, backProcessor );
        Gdx.input.setInputProcessor(multiplexer);

        Gdx.input.setCatchBackKey(true);


        this.maze = mazeGenerator.getMaze( world );

        totalTime = 3 * maze.length;
        START_Y = 1080;
        START_Y -= maze[0][0].height;
        displayMaze();

        displayUI();


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

            key = new Key(  maze [ i ][ j ].getX(),  maze[ i ][ j ].getY(), 1.2f * Cell.width, 1.2f * Cell.height );
            stage.addActor( key );
        }   else {
            key = null;
        }

        epsilon = 0.005f * Cell.width;

        System.out.println (" ------------------- key = " + key );

       // isNight = true;

        if ( Settings.isNigthLevelsAvailable ){
            System.out.println( "isCustomMaze = " + Settings.isCustomMaze );
            if ( Settings.isCustomMaze ){
                System.out.println( "isMazeCustomWithNight = " + Settings.isMazeCustomWithNight );
                if ( Settings.isMazeCustomWithNight ){
                    isNight = true;
                }

            }   else {
                if ( Settings.currentLevel%3 == 0){
                    isNight = true;
                }
            }
        }

        if ( isNight ) {
            rayHandler = new RayHandler( world );
            rayHandler.setCombinedMatrix(stage.getCamera().combined);
            pointLightDoor = new PointLight( rayHandler, 4, new Color ( 1f,1f,0.5f,1), 200 , flag.getX() + flag.getWidth()/2, flag.getY()+ flag.getHeight()/2 );
            pointLightPlayer = new PointLight(rayHandler, 6, new Color(1, 1, 0.5f, 1), 300, Constants.APP_WIDTH / 2, Constants.APP_HEIGHT / 2);
           // pointLight.setSoftnessLength(0);
        }
    }



    private void displayUI () {
        uiTable = new Table();
        uiTable.setSize( Constants.APP_WIDTH, Constants.BOTTOM_BAR_HEIGHT );
        uiTable.setPosition( 0,0 );
 //       uiTable.setBackground( Assets.sliderBackground );


        int backgroundPartsAmount = 7;


        for ( int i = 0; i < backgroundPartsAmount; i ++ ){
            Image bottomBarBackground  = new Image( Assets.bottomBarBackground );
            bottomBarBackground.setSize( Constants.APP_WIDTH/backgroundPartsAmount, Constants.BOTTOM_BAR_HEIGHT + Cell.height/4 );
            bottomBarBackground.setPosition( i * bottomBarBackground.getWidth() - i, 0 );
            stageHUD.addActor( bottomBarBackground );
        }



        timerText = new Label( "0123456789:", Assets.skin );
        timerText.setSize( 100, uiTable.getHeight() );
        timerText.setPosition( uiTable.getWidth()/2, uiTable.getHeight()/2 - timerText.getHeight()/2 );

        uiTable.addActor( timerText );


        uiTableElementWidth =  uiTable.getHeight();
        uiTableElementHeight = uiTable.getHeight();

        Image exitImage = new Image( Assets.bottomBarExitButton );
        exitImage.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                TheMaze.interestialAddInterface.show();
                TheMaze.game.setScreen( new GameOverScreen( false ) );

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Image zoomImage = new Image ( Assets.bottomBarZoomButton );
        zoomImage.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                if ( isZoomed ){
                    isZoomed = false;
                    if ( isNight ){
                        pointLightDoor.setPosition( flag.getX() + flag.getWidth() / 2, flag.getY()  + flag.getHeight() / 2 );
                        pointLightPlayer.setPosition( player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);

                    }
                    ((OrthographicCamera)stage.getCamera()).zoom += 0.5f;
                    ((OrthographicCamera)stage.getCamera()).position.set( stage.getWidth()/2, stage.getHeight()/2, 0 );




                }   else    {
                    isZoomed = true;
                    if ( isNight ){
                        pointLightDoor.setPosition( ( (flag.getX() + flag.getWidth() / 2 ) -  ( player.getX() + player.getWidth()/2 ) ) * 2 + stage.getWidth()/2, ( (flag.getY()  + flag.getHeight() / 2 ) - ( player.getY() + player.getHeight() / 2 ) ) * 2 + stage.getHeight()/2 );
                        pointLightPlayer.setPosition( stage.getWidth()/2, stage.getHeight()/2 );

                    }
                    ((OrthographicCamera)stage.getCamera()).zoom -= 0.5f;
                }

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stopWatchesAmountText = new Label( "0123456789", Assets.skin );
        stopWatchesAmountText.setSize( 100, uiTable.getHeight() );
        stopWatchesAmountText.setPosition( uiTable.getWidth()/2 - 10 * uiTableElementWidth / 5 - 40, uiTable.getHeight()/2 - stopWatchesAmountText.getHeight()/2 );

        uiTable.addActor( stopWatchesAmountText );

        Settings.loadStopWatchesAmount();
  //      Settings.stopWatchesAmount = 10;
        stopWatchesAmountText.setText( Settings.stopWatchesAmount+"" );

        Image stopWatchImage = new Image ( Assets.bottomBarStopWatchButton );
        stopWatchImage.addListener( new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(Settings.stopWatchesAmount);
                if ( Settings.stopWatchesAmount > 0 ){
                    Settings.stopWatchesAmount --;
                    stopWatchesAmountText.setText( Settings.stopWatchesAmount + "" );
                    totalTime+=15;
                    Settings.saveStopWatchesAmount();
                }

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stopWatchImage.setSize( uiTableElementWidth, uiTableElementHeight );
        stopWatchImage.setPosition( uiTable.getWidth()/2 - 10 * uiTableElementWidth / 5, uiTable.getHeight()/2 - uiTableElementHeight/2 + 2 );
        uiTable.addActor ( stopWatchImage );
       // addUIButton( stopWatchImage, uiTable.getWidth()/2 - 10 * uiTableElementWidth / 5, uiTableElementWidth*1.2f, uiTableElementHeight*1.2f, uiTable );

        addUIButton( exitImage, uiTable.getWidth() - 10 * uiTableElementWidth / 5, uiTableElementWidth, uiTableElementHeight, uiTable );
        addUIButton( zoomImage, uiTable.getWidth() - 20 * uiTableElementWidth / 5, uiTableElementWidth, uiTableElementHeight, uiTable );



        stageHUD.addActor( uiTable );

    }

    private void addUIButton ( Image image, float xPos, float width, float height, Table table ){

        image.setSize( width * 0.85f, height * 0.85f );
        image.setPosition( xPos, table.getHeight()/2 - height/2 - 1 );

        table.addActor(image);
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
        keyTipButton.setPosition( Constants.APP_WIDTH/2 - keyTipButton.getWidth()/2, Constants.APP_HEIGHT/2 - keyTipButton.getHeight()/2 + Constants.BOTTOM_BAR_HEIGHT );
        stageHUD.addActor( keyTipButton );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

        stage.act(delta);
        stage.draw();



        calculateTime();

        checkIfNight ();
        checkPlayerOnKey();
        checkGameOver();
        checkControlls();

        stageHUD.act( delta );
        stageHUD.draw();

    }

    private void calculateTime () {
        float deltaTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        totalTime -= deltaTime; //if counting down

        float minutes = ((int)totalTime) / 60;
        float seconds = ((int)totalTime) % 60;

        timerText.setText(String.format("%.00fm:%.00fs", minutes, seconds));

        if ( minutes == 0f && seconds == 0f ){
            TheMaze.game.setScreen( new GameOverScreen( false ) );
        }
    }

    private void checkIfNight () {
        if ( isNight ) {
            if ( !isZoomed ){
                pointLightPlayer.setPosition(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);

                randomForLight = (float)Math.sin( (( 0.99f + Math.random()/50 )*totalTime) % 3.14 );
                pointLightPlayer.setDistance( 200 + 120 * randomForLight );
                pointLightDoor.setDistance( 650  + 70 * randomForLight );
            }   else {
                stage.getCamera().position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
                stage.getCamera().update();

                randomForLight = (float)Math.sin( (( 0.99f + Math.random()/50 )*totalTime) % 3.14 );
                pointLightPlayer.setDistance( 400 + 240 * randomForLight );
                pointLightDoor.setDistance( 1000  + 140 * randomForLight );
            }

            rayHandler.updateAndRender();
            world.step(1 / 60f, 6, 2);
        }
    }

    private void checkPlayerOnKey (){
        if ( key != null ) {
          //  System.out.println( "playerX = " + player.getX() + " playerY = " + player.getY() + " keyX = " + key.getX() + " keyY = " + key.getY() );
            if ( Math.abs ( player.getX() - key.getX() ) < epsilon && Math.abs( player.getY() - key.getY() ) < epsilon ) {
                key.remove();
                isPlayerWithKey = true;

                key = new Key( 2 * uiTableElementWidth, uiTable.getHeight()/2 - (1.2f * uiTableElementHeight)/2, 1.2f * uiTableElementWidth, 1.2f * uiTableElementHeight );
                key.setPosition( key.getX(), key.getY() );
                stageHUD.addActor( key );
            }
        }
    }

    private void checkControlls (){
        if ( !player.isMoovingToNextIntersection && !isShowingControlls){
            showControlls();
            isSwipeEnabled = true;
        }   else if ( player.isMoovingToNextIntersection && isShowingControlls )   {
            hideControlls();
            isSwipeEnabled = false;
        }
    }

    private void enableSwiping () {

    }

    private void checkGameOver () {
        if ( (int) player.getX() == (int) flag.getX() && (int) player.getY() == (int) flag.getY() ){
            if ( key != null ) {
                if ( isPlayerWithKey ) {
                    TheMaze.interestialAddInterface.show();
                    Settings.levelsDone += 1;
                    Settings.saveResults();
                    TheMaze.game.setScreen(new GameOverScreen( true ));
                }   else if ( !isShowingKeyTip && !keyTipWasShown ) {
                    player.isTouchingDoor = true;
                    showKeyTip ();
                }
            }   else {
                TheMaze.interestialAddInterface.show();
                Settings.levelsDone += 1;
                Settings.saveResults();
                TheMaze.game.setScreen(new GameOverScreen( true ));
            }
        }
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
        if (isNight) {
            rayHandler.dispose();
        }
        stage.dispose();
    }


    private void moveCamera( int touch_x, int touch_y ) {
        Vector3 new_position = getNewCameraPosition( touch_x, touch_y );

        if( !cameraOutOfLimit( new_position ) )
            stage.getCamera().translate( new_position.sub( stage.getCamera().position ) );

        last_touch_down.set( touch_x, touch_y, 0);
    }

    private Vector3 getNewCameraPosition( int x, int y ) {
        Vector3 new_position = last_touch_down;
        new_position.sub(x, y, 0);
        new_position.y = -new_position.y;
        new_position.add( stage.getCamera().position );

        return new_position;
    }

    private boolean cameraOutOfLimit( Vector3 position ) {
        float x_left_limit = Constants.APP_WIDTH / 2;
        float x_right_limit = stage.getWidth() - x_left_limit;
        float y_bottom_limit = Constants.APP_HEIGHT / 2;
        float y_top_limit = stage.getHeight() - y_bottom_limit;

        if( position.x < x_left_limit || position.x > x_right_limit )
            return true;
        else if( position.y < y_bottom_limit || position.y > y_top_limit )
            return true;
        else
            return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println( velocityX + " " + velocityY );
        if ( isSwipeEnabled && ( Math.abs(velocityX) > 600 || Math.abs(velocityY) > 600 )) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 0) {
                    player.mooveRight();
                } else if (velocityX < 0) {
                    player.mooveLeft();
                } else {
                    // Do nothing.
                }
            } else {
                if (velocityY > 0) {
                    player.mooveDown();
                } else if (velocityY < 0) {
                    player.mooveUp();
                } else {
                    // Do nothing.
                }
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}


