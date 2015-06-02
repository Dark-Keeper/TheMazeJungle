package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 6/1/15.
 */
public class Controlls extends Actor {
    private int WIDTH = 120;
    private int HEIGHT = 55;

    private Player player;
    private String direction;

    public Controlls ( Player player, String direction ) {
        this.player = player;
        this.direction = direction;


        if ( direction.equals("north") ){
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - 30,player.getY() - 47 + 5/3*WIDTH );
        }
        if ( direction.equals("east") ) {
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - 30 + 5/3*WIDTH,player.getY() - 47 );
        }
        if ( direction.equals("south") ){
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - 30,player.getY() - 47 - 5/3*WIDTH);
        }
        if ( direction.equals("west") ){
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - 30 - 5/3*WIDTH,player.getY() - 47 );
        }


    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {

        if ( direction.equals("north") ){
            Sprite sprite = new Sprite(Assets.arrow);
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),WIDTH,HEIGHT,getScaleX(),getScaleY(), 90);
        }
        if ( direction.equals("east") ) {
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),WIDTH,HEIGHT,getScaleX(),getScaleY(), 0);
        }
        if ( direction.equals("south") ){
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),WIDTH,HEIGHT,getScaleX(),getScaleY(), 270);
        }
        if ( direction.equals("west") ){
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),WIDTH,HEIGHT,getScaleX(),getScaleY(), 180);
        }
    }
}
