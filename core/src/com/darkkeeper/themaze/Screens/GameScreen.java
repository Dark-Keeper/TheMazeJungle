package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Actors.Cell;
import com.darkkeeper.themaze.Actors.Controlls;
import com.darkkeeper.themaze.Actors.Flag;
import com.darkkeeper.themaze.Actors.Player;
import com.darkkeeper.themaze.Actors.Wall;
import com.darkkeeper.themaze.Basics.Assets;
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

    private static int START_X = 50;
    private static int START_Y = 1030;

    private Cell[][] cells;

    private Controlls controllLeft;
    private Controlls controllUp;
    private Controlls controllDown;
    private Controlls controllRight;

    private boolean isShowingControlls = false;




    private Table mazeTable;
    private int[][] maze;


    public GameScreen (int[][] maze) {
        viewPort = new ExtendViewport(1920,1080);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        this.maze = maze;
        cells = new Cell[maze.length][maze[0].length];


     //   formStage();
        displayMaze();

        player = new Player( 50 , 1030 , cells );
        stage.addActor( player );

        flag = new Flag( 1850, 70 );
        flag.setHeight( 200 );
        flag.setWidth( 200 );
        stage.addActor( flag );
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act( delta );
        stage.draw();

     //   Gdx.gl20.glLineWidth(10);

/*        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        displayMaze();

        shapeRenderer.end();*/
        if ( player.getX() == flag.getX() && player.getY() == flag.getY() ){
            TheMaze.game.setScreen( new LevelChooseScreen() );
        }

        if ( !player.isMoovingToNextIntersection && !isShowingControlls){
            showControlls();
        }   else if ( player.isMoovingToNextIntersection && isShowingControlls )   {
            hideControlls();
        }




/*
        if(controllDown!=null&&controllDown.isPressed() && !player.moovingToNextIntersection ){
            player.mooveDown();
        }

        if(controllUp!=null&&controllUp.isPressed()&& !player.moovingToNextIntersection ){
            player.mooveUp();
        }

        if(controllLeft!=null&&controllLeft.isPressed()&& !player.moovingToNextIntersection ){
            player.mooveLeft();
        }

        if(controllRight!=null&&controllRight.isPressed()&& !player.moovingToNextIntersection ){
            player.mooveRight();
        }
*/

    }

    public void showControlls () {
        isShowingControlls = true;

        if ( !player.currentCell.north ){
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


        if ( !player.currentCell.east ){
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


        if ( !player.currentCell.south ){
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


        if ( !player.currentCell.west ){
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

   /* private void initControlls ( final Player player ) {
        controllDown = new Controlls( player, "south" );
        controllUp = new Controlls( player, "north" );
        controllLeft = new Controlls( player, "west" );
        controllRight = new Controlls( player, "east" );

        controllDown.addCaptureListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.mooveDown();
            }
        });

        controllLeft.addCaptureListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.mooveLeft();
            }
        });

        controllRight.addCaptureListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.mooveRight();
            }
        });

        controllUp.addCaptureListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.mooveUp();
            }
        });
        stage.addActor( controllLeft );
        stage.addActor( controllRight );
        stage.addActor( controllUp );
        stage.addActor( controllDown );
    }*/



/*    private void formStage () {
        Table rootTable = new Table();
     //   stage.setDebugAll( true );
        rootTable.setFillParent(true);
        Table controllsTable = new Table ();

        controllsTable.add( controllUp ).center().padLeft( 160 );
        controllsTable.row();
        controllsTable.add( controllLeft ).padRight( 100 );
        controllsTable.add( controllRight );
        controllsTable.row();
        controllsTable.add( controllDown ).center().padLeft( 160 );

        rootTable.add( controllsTable ).expand().bottom().right().padBottom( 30 );

        stage.addActor( rootTable );
        
*//*        mazeTable = new Table ();
        rootTable.add( mazeTable ).expand();*//*

    }*/

    private void displayMaze () {
        for ( int i = 0; i < maze.length; i ++){
            for ( int j = 0; j< maze[0].length; j ++){
                switch (maze[i][j]){
                    case 1:
                        drawCell(i,j,true,false,true,true);
                        break;
                    case 2:
                        drawCell(i,j,true,true,true,false);
                        break;
                    case 3:
                        drawCell(i,j, true,false,true,false);
                        break;
                    case 4:
                        drawCell(i,j,true,true,false,true);
                        break;
                    case 5:
                        drawCell(i,j, true, false, false, true);
                        break;
                    case 6:
                        drawCell(i,j,true,true,false,false);
                        break;
                    case 7:
                        drawCell(i,j,true,false,false,false);
                        break;
                    case 8:
                        drawCell(i,j,false,true,true,true);
                        break;
                    case 9:
                        drawCell(i,j, false, false,true,true);
                        break;
                    case 10:
                        drawCell(i,j,false,true,true,false);
                        break;
                    case 11:
                        drawCell(i,j,false,false,true,false);
                        break;
                    case 12:
                        drawCell(i,j,false,true,false,true);
                        break;
                    case 13:
                        drawCell(i,j,false, false,false,true);
                        break;
                    case 14:
                        drawCell(i,j,false,true,false,false);
                        break;
                }

            }
        }

    }

    private void drawCell( int i, int j, boolean WEST, boolean NORTH, boolean EAST, boolean SOUTH ){
        Cell cell = new Cell( WEST, NORTH, EAST, SOUTH );
        cells[i][j] = cell;
        cell.setPosition( START_X + cell.SIZE * i, START_Y - cell.SIZE * j );
        stage.addActor( cell );        
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

    }
}
