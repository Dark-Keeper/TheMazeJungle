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

    private float charX;
    private float  charY;

    public Cell currentCell;
    private Cell prevCell;
    private Cell[][] cells;

    public Player ( float x, float y , Cell[][] cells ) {
        this.cells = cells;
        this.charX = x;
        this.charY = y;
        setWidth( Cell.width );
        setHeight(Cell.height);
        setPosition( x, y );
        unlocked = true;
        currentCell = cells[1][1];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.character, getX(), getY(), Cell.width, Cell.height);
    }

    @Override
    public void act ( float delta ) {
        super.act(delta);
        //checkCell();
        updateState();
    }

    private void checkCell (){

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
/*                System.out.println( this.getX() + " == " + cells[i][j].getX() );
                System.out.println( this.getY() + " == " + cells[i][j].getY() );*/
                    if ( cells[i][j] != null ) {
                        if (this.getX() == cells[i][j].getX() && this.getY() == cells[i][j].getY()) {
                            System.out.println( this.getX() + " == " + cells[i][j].getX() );
                            System.out.println( this.getY() + " == " + cells[i][j].getY() );
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
        if ( ( isNoWallUp() && unlocked) || isMoovingToNextIntersection ){
            System.out.println("UP");
            this.addAction( Actions.sequence( lock() , Actions.moveTo(getX(), getY() + Cell.height, 0.1f), setCell( currentCell.i - 1, currentCell.j ), mooveToNextIntersection( currentCell.i - 1, currentCell.j ) ) );
        }
    }

    public void mooveDown() {
        if ( (isNoWallDown() && unlocked) || isMoovingToNextIntersection ) {
            System.out.println("DOWN");
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX(), getY() - Cell.height, 0.1f), setCell( currentCell.i + 1, currentCell.j ), mooveToNextIntersection( currentCell.i + 1, currentCell.j ) ) );
        }
    }

    public void mooveLeft() {
        if ( ( isNoWallLeft() && unlocked) || isMoovingToNextIntersection ) {
            System.out.println("LEFT");
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX() - Cell.width, getY(), 0.1f), setCell( currentCell.i, currentCell.j - 1), mooveToNextIntersection( currentCell.i, currentCell.j - 1 ) ) );
        }
    }

    public void mooveRight() {
        if ( ( isNoWallRight() && unlocked) || isMoovingToNextIntersection ) {
            System.out.println("RIGHT");
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX() + Cell.width, getY(), 0.1f), setCell( currentCell.i, currentCell.j + 1 ), mooveToNextIntersection( currentCell.i, currentCell.j + 1 ) ) );
        }
    }
    private Action setCell ( final int newI, final int newJ ) {

        return Actions.run(new Runnable() {
            public void run() {
                prevCell = currentCell;
                currentCell = cells[newI][newJ];
            }
        });

    }

    private int getOpenDirections (){
        int directions = 4;
        if ( !isNoWallUp() ){
            directions--;
        }
        if ( !isNoWallDown() ){
            directions--;
        }
        if ( !isNoWallLeft() ){
            directions--;
        }
        if ( !isNoWallRight() ){
            directions--;
        }
        return directions;
    }

    private Action mooveToNextIntersection ( int newI, int newJ){

        return Actions.run(new Runnable() {
            public void run() {
                isMoovingToNextIntersection = true;


                if ( getOpenDirections() == 1 ){
                    prevCell = cells[0][0];
                }

                if ( getOpenDirections() == 2 ) {
                    if ( isNoWallUp() && currentCell.i - 1 != prevCell.i ){
                        mooveUp();
                    }
                    if ( isNoWallRight() && currentCell.j + 1 != prevCell.j ){
                        mooveRight();
                    }
                    if ( isNoWallDown() && currentCell.i + 1 != prevCell.i ){
                        mooveDown();
                    }
                    if ( isNoWallLeft() && currentCell.j - 1 != prevCell.j ){
                        mooveLeft();
                    }
                }   else {
                    unlocked = true;
                    isMoovingToNextIntersection = false;
                }
            }
        });

    }

    public boolean isNoWallUp (){
        if ( cells[currentCell.i - 1][currentCell.j].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
    public boolean isNoWallDown (){
        if ( cells[currentCell.i + 1][currentCell.j].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
    public boolean isNoWallLeft (){
        if ( cells[currentCell.i][currentCell.j - 1 ].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
    public boolean isNoWallRight (){
        if ( cells[currentCell.i][currentCell.j + 1].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
}
