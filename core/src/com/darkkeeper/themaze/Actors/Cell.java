package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.TheMaze;

/**
 * Created by andreipiatosin on 5/29/15.
 */
public class Cell extends Actor {
    public static float width;
    public static float height;
    public boolean isWall;
    public int i;
    public int j;

    public Cell ( float width, float height, boolean isWall, int i, int j) {
        this.width = width;
        this.height = height;
        this.isWall = isWall;
        this.i = i;
        this.j = j;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if ( isWall ) {
            batch.draw(Assets.currentStyleTexture, getX(),getY(), width,height, width/TheMaze.WIDTH*j, ( height/TheMaze.HEIGHT*i )/2, width/TheMaze.WIDTH*(j+1), ( height/TheMaze.HEIGHT*(i+1) )/2 );
        }   else    {
            batch.draw(Assets.currentStyleTexture, getX(),getY(), width,height, width/TheMaze.WIDTH*j, ( height/TheMaze.HEIGHT*i )/2 + 0.5f, width/TheMaze.WIDTH*(j+1), ( height/TheMaze.HEIGHT*(i+1) )/2 + 0.5f );
        }
    }
}
