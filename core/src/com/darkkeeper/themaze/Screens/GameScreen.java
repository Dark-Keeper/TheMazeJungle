package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Actors.Cell;
import com.darkkeeper.themaze.Actors.Controlls;
import com.darkkeeper.themaze.Actors.Flag;
import com.darkkeeper.themaze.Actors.Player;
import com.darkkeeper.themaze.TheMaze;

/**
 * Created by andreipiatosin on 5/15/15.
 */
public class GameScreen implements Screen {
    private Viewport viewPort;
    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch myBatch;
    private ShapeRenderer shapeRenderer;
    private boolean mazeIsNotDisplayed = true;

    Player player;
    Flag flag;

    private static float START_X = 0;
    private static float START_Y = 1080;


    private Controlls controllLeft;
    private Controlls controllUp;
    private Controlls controllDown;
    private Controlls controllRight;

    private boolean isShowingControlls = false;




    private Table mazeTable;
    private Cell[][] maze;


    public GameScreen (Cell[][] maze) {
        viewPort = new ExtendViewport(TheMaze.WIDTH,TheMaze.HEIGHT);
        stage = new Stage(viewPort);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
                    dispose();
                    TheMaze.interestialAddInterface.show();
                    TheMaze.game.setScreen(new LevelChooseScreen());
                 }

                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer( stage, backProcessor );
        Gdx.input.setInputProcessor(multiplexer);

        Gdx.input.setCatchBackKey(true);

        this.maze = maze;

        START_Y = 1080;
        START_Y -= maze[0][0].height;
        displayMaze();


        player = new Player( maze[1][1].getX(), maze[1][1].getY(), maze );
        stage.addActor( player );

        flag = new Flag(  maze [ maze.length - 2 ][ maze[ 0 ].length - 2 ].getX(),  maze[ maze.length-2 ] [ maze[0].length-2 ].getY() );
        stage.addActor( flag );
    }

    private void displayMaze () {
        for ( int i = 0; i < maze.length; i ++ ){
            for ( int j = 0; j < maze[0].length; j ++ ){
                maze[i][j].setPosition( START_X + Cell.width * j, START_Y - Cell.height * i );
                stage.addActor( maze[i][j] );
            }
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act( delta );
        stage.draw();

        if ( (int) player.getX() == (int) flag.getX() && (int) player.getY() == (int) flag.getY() ){
            TheMaze.interestialAddInterface.show();
            TheMaze.game.setScreen( new LevelChooseScreen() );
        }

        if ( !player.isMoovingToNextIntersection && !isShowingControlls){
            showControlls();
        }   else if ( player.isMoovingToNextIntersection && isShowingControlls )   {
            hideControlls();
        }

    }

    public void showControlls () {
        isShowingControlls = true;

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
