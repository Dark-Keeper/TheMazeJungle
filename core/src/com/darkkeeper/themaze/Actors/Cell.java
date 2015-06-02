package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 5/29/15.
 */
public class Cell extends Actor {
    public static int SIZE = 60;
    public boolean north;
    public boolean east;
    public boolean south;
    public boolean west;


    public Cell ( boolean WEST, boolean NORTH, boolean EAST, boolean SOUTH ) {
        this.north = NORTH;
        this.east = EAST;
        this.south = SOUTH;
        this.west = WEST;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
       // batch.draw(Assets.wall,getX(),getY(),SIZE,SIZE);
      //  batch.draw(Assets.wall, getX(), getY(), getOriginX(),getOriginY(), getWidth(), getHeight(),getScaleX(),getScaleY(),getRotation());
        batch.draw( Assets.wall, getX(),             getY(),SIZE/6, SIZE/6 ); //left top
        batch.draw( Assets.wall, getX() + 5*SIZE/6,  getY(), SIZE/6, SIZE/6 );
        batch.draw( Assets.wall, getX(),             getY() - 5*SIZE/6, SIZE/6, SIZE/6 );
        batch.draw( Assets.wall, getX() + 5*SIZE/6,  getY() - 5*SIZE/6, SIZE/6, SIZE/6 );
        if ( north ){
            batch.draw( Assets.wall, getX(), getY(), SIZE, SIZE/6 );
        }
        if ( south ){
            batch.draw( Assets.wall, getX(), getY() - 5*SIZE/6, SIZE, SIZE/6 );
        }
        if ( east ){
            batch.draw( Assets.wall, getX() + 5*SIZE/6, getY() + SIZE/6, getOriginX(),getOriginY(), SIZE, SIZE/6, getScaleX(),getScaleY(), 270  );
        }

        if ( west ){
            batch.draw( Assets.wall, getX(), getY() + SIZE/6, getOriginX(),getOriginY(), SIZE, SIZE/6, getScaleX(),getScaleY(), 270  );
        }
    }

    public int getOpenDirections (){
        int wallNumber = 4;
        if ( north ){
            wallNumber--;
        }
        if ( south ){
            wallNumber--;
        }
        if ( east ){
            wallNumber--;
        }
        if ( west ){
            wallNumber--;
        }
        return wallNumber;
    }
}
