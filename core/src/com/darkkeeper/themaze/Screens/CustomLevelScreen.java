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
    private Button primMethodButton;

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
        rootTable.background( Assets.mainMenuBackgroundTextureRegion );
        rootTable.setFillParent( true );
        stage.addActor( rootTable );

        //stage.setDebugAll( true );

        addMethodButtons();

        Image widthTextImage = new Image( Assets.widthBtnTextureRegion );
        widthTextImage.setPosition( 170, 615 );

        stage.addActor( widthTextImage );

        widthSlider = new MySlider( 14f, 54f, 2f, false, Assets.skin, "default" );
        initializeSlider( widthSlider, 960, 634 );

        Image nightModeImage = new Image( Assets.nightBtnTextureRegion );
        nightModeImage.setPosition( 170, 415 );
        stage.addActor( nightModeImage );

        final Button nightModeButton = new Button( Assets.skin, "checkBox" );
        nightModeButton.addListener( new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if ( nightModeButton.isChecked() )
                {
                    Settings.isMazeCustomWithNight = true;
                }   else    {
                    Settings.isMazeCustomWithNight = false;
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        nightModeButton.setPosition( 960, 415 );
        stage.addActor( nightModeButton );


        addPlayButton();

    }


    private void changeSliderText ( MySlider slider ){
        
        int value = Math.round( slider.getValue() ) + 1;
        System.out.println("value = " + value + " float = " + slider.getValue() );

        if ( slider.currentImage!=null ){
            slider.currentImage.remove();
        }
        if ( slider.currentImage2!=null ){
            slider.currentImage2.remove();
        }
        if ( value > 0 && value < 10 ) {
            Assets.loadDigit( (int)value );
            slider.currentImage = new Image( Assets.digitTextureRegionDrawable );
            slider.currentImage.setSize(40, 60);
            slider.currentImage.setPosition( slider.getX() + slider.getWidth()/2, slider.getY() + 90 );
            stage.addActor(slider.currentImage);
        }   else if ( value > 9 && value < 100 ){
            Assets.loadDigit( (int)value / 10);
            slider.currentImage = new Image( Assets.digitTextureRegionDrawable );
            slider.currentImage.setSize(40, 60);
            slider.currentImage.setPosition( slider.getX() + slider.getWidth()/2 - 20, slider.getY() + 90 );
            stage.addActor(slider.currentImage);

            Assets.loadDigit( (int)value % 10 );
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
        slider.setHeight(100);
        slider.setValue(14f);
     //   System.out.println(" float = " + slider.getValue() );
        slider.setPosition( xPos, yPos );
        stage.addActor( slider );
        changeSliderText( slider );
    }




    private void displayMaze () {

        Settings.mazeCustomWidth = (int)(Math.round(widthSlider.getValue() + 1) / 2) ;
        Settings.isCustomMaze = true;

        if (recbackMethodButton.isChecked()) {
            Settings.mazeCustomMethod = "recback";
        } else if (primMethodButton.isChecked()) {
            Settings.mazeCustomMethod = "prim";
        } else {
            Settings.mazeCustomMethod = "hunt";
        }

        TheMaze.game.setScreen( new GameScreen( MazeGenerator.generateNewCustomMaze() ) );

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

        recbackMethodButton     = new Button( Assets.skin, "checkBox" );
        primMethodButton       = new Button( Assets.skin, "checkBox" );
        huntAndKillMethodButton = new Button( Assets.skin, "checkBox" );

        initializeCheckerButton( recbackMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( primMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );
        initializeCheckerButton( huntAndKillMethodButton, METHOD_BUTTON_WIDTH, METHOD_BUTTON_HEIGHT );

        Image recbackMethodTextImage       = new Image( Assets.reckBackTextureRegion );
        Image primMethodTextImage           = new Image( Assets.primMethodTextureRegion );
        Image huntAndKillMethodTextImage    = new Image( Assets.huntAndKillkMethodTextureRegion );

        stage.addActor( recbackMethodTextImage );
        stage.addActor( primMethodTextImage );
        stage.addActor( huntAndKillMethodTextImage );

        recbackMethodTextImage.setPosition( 910, 967 );
        primMethodTextImage.setPosition( 1303, 967 );
        huntAndKillMethodTextImage.setPosition( 1690, 967 );

        recbackMethodButton.setPosition( 953, 860 );
        primMethodButton.setPosition( 1337, 860 );
        huntAndKillMethodButton.setPosition( 1721, 860 );

        buttonGroup.add(recbackMethodButton);
        buttonGroup.add(primMethodButton);
        buttonGroup.add(huntAndKillMethodButton);

        buttonGroup.setMaxCheckCount( 1 );
        recbackMethodButton.setChecked( true );

    }

    private void addPlayButton () {
        int buttonWidth = 500;
        int buttonHeight = 190;

        Image playButton = new Image( Assets.playBtnTextureRegion );
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
