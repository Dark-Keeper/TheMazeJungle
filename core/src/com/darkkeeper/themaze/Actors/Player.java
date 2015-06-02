package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Screens.GameScreen;

/**
 * Created by andreipiatosin on 5/25/15.
 */
public class Player extends Actor {

    private static int PLAYER_STATE_DOWN = 10;
    private static int PLAYER_STATE_UP = 11;
    private static int PLAYER_STATE_LEFT = 12;
    private static int PLAYER_STATE_RIGHT = 13;
    private static int PLAYER_STATE_STANDING = 0;
    private static int PLAYER_STATE_RUNNING = 1;

    private boolean unlocked;
    public boolean isMoovingToNextIntersection = false;

    private int charX;
    private int charY;

    public Cell currentCell;
    private Cell prevCell;
    private Cell[][] cells;

    public Player ( int x, int y , Cell[][] cells ) {
        this.cells = cells;
        this.charX = x;
        this.charY = y;
        setPosition( x, y );
        unlocked = true;
        currentCell = new Cell( true, true, true, true );
    }

/*    @Override
    public void create() {
        quadTexture = new Texture("data/quads.png");
        batch = new SpriteBatch();
        redRegion = new TextureRegion(quadTexture, 0, 0, 32, 32);
        stage = new Stage();


        Player player = new Player();
        player.setSize(100, 50);
        player.setPosition(50, 50);

        stage.addActor( player );
    }*/

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.character, getX() + 10, getY() - 40 , 40, 40);
    }

    @Override
    public void act ( float delta ) {
        super.act(delta);
        checkCell();
        updateState();
    }

    private void checkCell (){

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
/*                System.out.println( this.getX() + " == " + cells[i][j].getX() );
                System.out.println( this.getY() + " == " + cells[i][j].getY() );*/
                    if ( cells[i][j] != null ) {
                        if (this.getX() == cells[i][j].getX() && this.getY() == cells[i][j].getY()) {
                            prevCell = currentCell;
                            currentCell = cells[i][j];
                            return;
                        }
                    }
                }
            }
    }

    private void updateState () {

    }

    private Action unlock (){
        return Actions.run(new Runnable() {
            public void run() {
                unlocked = true;
            }
        });
    }

    private Action lock (){
        return Actions.run(new Runnable() {
            public void run() {
                unlocked = false;
            }
        });
    }

    public void mooveUp (){
        if ( (!currentCell.north && unlocked) || isMoovingToNextIntersection ){
            this.addAction( Actions.sequence( lock() , Actions.moveTo(getX(), getY() + Cell.SIZE, 0.1f), mooveToNextIntersection() ) );
        }
    }

    public void mooveDown() {
        if ( (!currentCell.south && unlocked) || isMoovingToNextIntersection ) {
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX(), getY() - Cell.SIZE, 0.1f), mooveToNextIntersection() ) );
        }
    }

    public void mooveLeft() {
        if ( (!currentCell.west && unlocked) || isMoovingToNextIntersection ) {
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX() - Cell.SIZE, getY(), 0.1f), mooveToNextIntersection() ) );
        }
    }

    public void mooveRight() {
        if ( (!currentCell.east && unlocked) || isMoovingToNextIntersection ) {
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX() + Cell.SIZE, getY(), 0.1f), mooveToNextIntersection() ) );
        }
    }

    private Action mooveToNextIntersection (){


        return Actions.run(new Runnable() {
            public void run() {
                isMoovingToNextIntersection = true;


                if ( currentCell.getOpenDirections() == 1 ){
                    prevCell = new Cell( true, true, true, true );
                }

                if ( currentCell.getOpenDirections() == 2 ) {
                    if ( !currentCell.north && currentCell.getY() + Cell.SIZE != prevCell.getY() ){
                        mooveUp();
                    }
                    if ( !currentCell.east && currentCell.getX() + Cell.SIZE != prevCell.getX() ){
                        mooveRight();
                    }
                    if ( !currentCell.south && currentCell.getY() - Cell.SIZE != prevCell.getY() ){
                        mooveDown();
                    }
                    if ( !currentCell.west && currentCell.getX() - Cell.SIZE != prevCell.getX() ){
                        mooveLeft();
                    }
                }   else {
                    unlocked = true;
                    isMoovingToNextIntersection = false;
                }
            }
        });

    }
}
