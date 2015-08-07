package com.darkkeeper.themaze.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darkkeeper.themaze.Actors.Cell;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.Basics.Settings;
import com.darkkeeper.themaze.Methods.HuntAndKillMazeGenerator;
import com.darkkeeper.themaze.Methods.MazeGenerator;
import com.darkkeeper.themaze.Methods.PrimMazeGenerator;
import com.darkkeeper.themaze.Methods.RecursiveBacktrackerMazeGenerator;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.UI.MySlider;
import com.darkkeeper.themaze.Utils.Constants;

/**
 * Created by andreipiatosin on 5/19/15.
 */

public class CustomLevelScreen implements Screen {

    private static int METHOD_BUTTON_WIDTH       =   130;
    private static int METHOD_BUTTON_HEIGHT      =   100;

    private Viewport viewPort;
    private Stage stage;

    private Table rootTable;


    private Button recbackMethodButton;
    private Button huntAndKillMethodButton;
    private Button primsMethodButton;

    private int mazeWidth;
    private int mazeHeight;

    private MySlider widthSlider;
    private MySlider heightSlider;

    public CustomLevelScreen() {
        viewPort = new ExtendViewport( Constants.APP_WIDTH, Constants.APP_HEIGHT );
        stage = new Stage(viewPort);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)) {
                    dispose();
                    TheMaze.game.setScreen( new MainMenuScreen() );
                }

                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer( stage, backProcessor );
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);

        rootTable = new Table();
        rootTable.background( Assets.customLevelBackground );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        //stage.setDebugAll( true );

        addMethodButtons();

        widthSlider = new MySlider( 4, 50, 1, false, Assets.skin, "default" );
        heightSlider = new MySlider( 4, 50, 1, false, Assets.skin, "default" );
        initializeSlider(widthSlider, 960, 634 );
        initializeSlider(heightSlider, 960, 417 );

/*        addMazeSizeButtons();
        addSkinSelectButtons();*/

        addPlayButton ();

    }


    private void changeSliderText ( MySlider slider ){

        if ( slider.currentImage!=null ){
            slider.currentImage.remove();
        }
        if ( slider.currentImage2!=null ){
            slider.currentImage2.remove();
        }
        if ( slider.getValue() > 0 && slider.getValue() < 10 ) {
            Assets.loadDigit( (int)slider.getValue() );
            slider.currentImage = new Image( Assets.digitTextureRegionDrawable );
            slider.currentImage.setSize(40, 60);
            slider.currentImage.setPosition( slider.getX() + slider.getWidth()/2, slider.getY() + 90 );
            stage.addActor(slider.currentImage);
        }   else if ( slider.getValue() > 9 && slider.getValue() < 100 ){
            Assets.loadDigit( (int)slider.getValue() / 10);
            slider.currentImage = new Image( Assets.digitTextureRegionDrawable );
            slider.currentImage.setSize(40, 60);
            slider.currentImage.setPosition( slider.getX() + slider.getWidth()/2 - 20, slider.getY() + 90 );
            stage.addActor(slider.currentImage);

            Assets.loadDigit( (int)slider.getValue() % 10 );
            slider.currentImage2 = new Image( Assets.digitTextureRegionDrawable );
            slider.currentImage2.setSize(40, 60);
            slider.currentImage2.setPosition( slider.getX() + slider.getWidth()/2 + 20, slider.getY() + 90 );
            stage.addActor(slider.currentImage2);
        }
    }

    private void initializeSlider ( final MySlider slider, float xPos, float yPos ){
        slider.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                changeSliderText( slider );
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                changeSliderText( slider );
            }

            public void touchDragged (InputEvent event, float x, float y, int pointer) {
             //   changeSliderText( slider );
            }
        });       
        slider.setWidth( 880 );
        slider.setHeight( 100 );
        slider.setValue( 10 );
        slider.setPosition( xPos, yPos );
        stage.addActor( slider );
        changeSliderText( slider );
    }




    private void displayMaze () {

        mazeWidth = (int)widthSlider.getValue();
       // mazeHeight = (int)heightSlider.getValue();
        mazeHeight = (int)(mazeWidth/1.77);

        MazeGenerator maze;

        if (recbackMethodButton.isChecked()) {
            maze = new RecursiveBacktrackerMazeGenerator(mazeWidth, mazeHeight, 0, 1);
        } else if (primsMethodButton.isChecked()) {
            maze = new PrimMazeGenerator(mazeWidth, mazeHeight, 0, 1);
        } else {
            maze = new HuntAndKillMazeGenerator(mazeWidth, mazeHeight, 0, 1);
        }

        maze.generate();
        Assets.currentStyleTexture = Assets.gameTexture1;

        TheMaze.game.setScreen( new GameScreen( maze ) );

    }

    private void initializeCheckerButton (final Button checkButton, int width, int height) {
        checkButton.setSize( width, height );
        checkButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                checkButton.setChecked(true);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        stage.addActor( checkButton );
    }


    private void addMethodButtons (){
        ButtonGroup buttonGroup = new ButtonGroup();

        recbackMethodButton = new Button( Assets.skin, "default" );
        primsMethodButton = new Button( Assets.skin, "default" );
        huntAndKillMethodButton = new Button( Assets.skin, "default" );
        initializeCheckerButton( recbackMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( primsMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( huntAndKillMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );

        recbackMethodButton.setPosition( 953, 860 );
        primsMethodButton.setPosition( 1337, 860 );
        huntAndKillMethodButton.setPosition( 1721, 860 );

        buttonGroup.add(recbackMethodButton);
        buttonGroup.add(primsMethodButton);
        buttonGroup.add(huntAndKillMethodButton);

        buttonGroup.setMaxCheckCount( 1 );
        recbackMethodButton.setChecked( true );

    }

    private void addPlayButton () {
        int buttonWidth = 500;
        int buttonHeight = 190;

        Button playButton = new Button( Assets.skin, "default" );
        playButton.setOrigin( buttonWidth/2, buttonHeight/2 );
        playButton.setPosition( 700, 8 );
        playButton.setSize( buttonWidth, buttonHeight );
        playButton.addListener( new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                displayMaze();
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stage.addActor( playButton );
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0, 0, 0, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.act( delta );
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update( width, height );
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
