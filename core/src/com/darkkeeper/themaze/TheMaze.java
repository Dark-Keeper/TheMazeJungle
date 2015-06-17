package com.darkkeeper.themaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.Screens.LogoScreen;
import com.darkkeeper.themaze.Screens.MainMenuScreen;

public class TheMaze extends Game implements ApplicationListener, InputProcessor {
    FPSLogger fps;
    public static Game game;
    public static float WIDTH = 1920;
    public static float HEIGHT = 1080;

	@Override
	public void create () {
        Gdx.input.setInputProcessor ( this );
        Gdx.input.setCatchBackKey(true);
        game = this;
        setScreen( new LogoScreen() );

        fps = new FPSLogger();
	}

	@Override
	public void render () {
/*		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/
        super.render();
        fps.log();
	}

    @Override
    public void dispose () {
        super.dispose();

        getScreen().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
