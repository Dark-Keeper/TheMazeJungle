package com.darkkeeper.themaze.Basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by andreipiatosin on 5/20/15.
 */
public class StageSettings {
    public static void render ( Stage stage ){
        Gdx.gl.glClearColor( 1, 1, 1, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.act();
        stage.draw();
    }
}
