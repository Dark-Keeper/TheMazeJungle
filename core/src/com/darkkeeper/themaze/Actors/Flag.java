package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 6/1/15.
 */
public class Flag extends Actor {
    private int x;
    private int y;
    private int WIDTH = 60;
    private int HEIGHT = 60;

    public Flag ( int x, int y ) {
        this.x = x;
        this.y = y;
        setPosition( x, y );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.flag,getX(),getY() + 15,getOriginX(),getOriginY(),WIDTH,HEIGHT,getScaleX(),getScaleY(), 270);
    }

}
