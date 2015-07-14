package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Actors.Cell;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 6/1/15.
 */
public class Flag extends Actor {
    private float x;
    private float y;

    public Flag ( float x, float y ) {
        this.x = x;
        this.y = y;
        setWidth( Cell.width );
        setHeight( Cell.height );
        setPosition( x, y );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.flag,getX(),getY(),getOriginX(),getOriginY(),Cell.width,Cell.height,getScaleX(),getScaleY(), 0);
    }

}
