package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 8/5/15.
 */
public class Key extends Actor {
    private float x;
    private float y;

    private static int FRAME_COLS = 11;
    private static int FRAME_ROWS = 1;
    private TextureRegion[] spin;
    private Animation spinAnimation;
    private float stateTime;
    private TextureRegion currentFrame;
    private Animation currentAnimation;

    public Key ( float x, float y, float width, float height ) {
        this.x = x;
        this.y = y;
        setWidth( width );
        setHeight( height );
        setPosition( x, y );


        Texture keyTexture = new Texture(Gdx.files.internal("game/key.png"));
        TextureRegion[][] tmp = TextureRegion.split(keyTexture, keyTexture.getWidth()/FRAME_COLS, keyTexture.getHeight()/FRAME_ROWS);              // #10
        spin = new TextureRegion[FRAME_COLS];

        for (int i = 0; i < FRAME_COLS; i++) {
            spin[i] = tmp[0][i];
        }
        spinAnimation = new Animation(0.14f, spin);

        currentAnimation = spinAnimation;
        stateTime = 0f;
        
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       // batch.draw(Assets.key,getX(),getY(),getOriginX(),getOriginY(),Cell.width,Cell.height,getScaleX(),getScaleY(), 0);
        stateTime += Gdx.graphics.getDeltaTime();


        currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, getX(), getY(), Cell.width, Cell.height);
    }

}