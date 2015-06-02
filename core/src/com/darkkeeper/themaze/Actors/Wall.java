package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 5/28/15.
 */
public class Wall extends Actor {

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.wall, getX(), getY(), getOriginX(),getOriginY(), getWidth(), getHeight(),getScaleX(),getScaleY(),getRotation());
    }
}
