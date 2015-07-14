package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darkkeeper.themaze.Basics.Assets;

/**
 * Created by andreipiatosin on 6/1/15.
 */
public class Controlls extends Actor {
    private int WIDTH = 150;
    private int HEIGHT = 65;

    private Player player;
    private String direction;

    public Controlls ( Player player, String direction ) {
        this.player = player;
        this.direction = direction;


        if ( direction.equals("north") ){
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - getWidth()/2 + player.getWidth()/2,player.getY()+ 5/3*WIDTH - getHeight()/2 + player.getHeight()/2);
        }
        if ( direction.equals("east") ) {
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - getWidth()/2 + player.getWidth()/2 + 5/3*WIDTH,player.getY() - getHeight()/2 + player.getHeight()/2);
        }
        if ( direction.equals("south") ){
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - getWidth()/2 + player.getWidth()/2,player.getY() - 5/3*WIDTH - getHeight()/2 + player.getHeight()/2);
        }
        if ( direction.equals("west") ){
            setWidth( WIDTH );
            setHeight( HEIGHT );
            setOrigin( getWidth()/2, getHeight()/2 );
            setPosition( player.getX() - getWidth()/2 + player.getWidth()/2- 5/3*WIDTH,player.getY() - getHeight()/2 + player.getHeight()/2);
        }


    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {

        if ( direction.equals("north") ){
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(), 90);
        }
        if ( direction.equals("east") ) {
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(), 0);
        }
        if ( direction.equals("south") ){
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(), 270);
        }
        if ( direction.equals("west") ){
            batch.draw(Assets.arrow,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(), 180);
        }
    }
}
