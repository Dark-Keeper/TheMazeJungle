package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.TheMaze;

/**
 * Created by andreipiatosin on 5/28/15.
 */
public class Wall extends Actor {

    public int width;
    public int height;
    public boolean isWall;
    public int i;
    public int j;

    public Wall ( int width,int height, boolean isWall, int i, int j) {
        this.width = width;
        this.height = height;
        this.isWall = isWall;
        this.i = i;
        this.j = j;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if ( !isWall ) {
/*            batch.draw( Assets.netTextureRegion,getX(),getY(),width,height);
            batch.setColor(new Color(1,1,1,0.8f));
            batch.draw(Assets.snowBackgroundTexture, getX(),getY(),width,height, width/TheMaze.WIDTH*j, height/TheMaze.HEIGHT*i, width/TheMaze.WIDTH*(j+1), height/TheMaze.HEIGHT*(i+1));
            batch.setColor(new Color(1,1,1,1));*/
        }
    }

/*    public void drawCell (Batch batch){
        if ( isWall ) {
            batch.draw(Assets.iceBackgroundTexture, getX(), getY(), width, height, width / TheMaze.WIDTH * j, height / TheMaze.HEIGHT * i, width / TheMaze.WIDTH * (j + 1), height / TheMaze.HEIGHT * (i + 1));
        }
    }*/
}
