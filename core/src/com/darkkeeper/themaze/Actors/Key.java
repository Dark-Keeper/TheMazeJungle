package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 8/5/15.
 */
public class Key extends Actor {
    private float x;
    private float y;

    public Key ( float x, float y ) {
        this.x = x;
        this.y = y;
        setWidth( Cell.width );
        setHeight( Cell.height );
        setPosition( x, y );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.key,getX(),getY(),getOriginX(),getOriginY(),Cell.width,Cell.height,getScaleX(),getScaleY(), 0);
    }

}