package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.darkkeeper.themaze.Actors.Cell;

/**
 * Created by andreipiatosin on 5/25/15.
 */
public class Player extends Actor {

    private static int PLAYER_STATE_DOWN = 10;
    private static int PLAYER_STATE_UP = 11;
    private static int PLAYER_STATE_LEFT = 12;
    private static int PLAYER_STATE_RIGHT = 13;
    private static int PLAYER_STATE_STANDING = 0;
    private static int PLAYER_STATE_RUNNING = 1;

    private int currentState=0;

    private boolean unlocked;
    public boolean isMoovingToNextIntersection = false;

    private float charX;
    private float  charY;

    public Cell currentCell;
    private Cell prevCell;
    private Cell[][] cells;

    private Texture playerTexture;
    private static int FRAME_COLS = 6;
    private static int FRAME_ROWS = 4;

    TextureRegion[] walkDown;
    TextureRegion[] walkLeft;
    TextureRegion[] walkRight;
    TextureRegion[] walkUp;

    Animation walkDownAnimation;
    Animation walkLeftAnimation;
    Animation walkRightAnimation;
    Animation walkUpAnimation;

    TextureRegion currentFrame;
    Animation currentAnimation;

    float stateTime;

    private static final float RADIUS = 3.0f;

    private Body body;

    public boolean isTouchingDoor = false;



    public Player ( World world, float x, float y , Cell[][] cells ) {
        this.cells = cells;
        this.charX = x;
        this.charY = y;
        setWidth( Cell.width );
        setHeight(Cell.height);
        setPosition( x, y );
        unlocked = true;
        currentCell = cells[1][1];

        playerTexture = new Texture(Gdx.files.internal("game/player2.png"));
        TextureRegion[][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth()/FRAME_COLS, playerTexture.getHeight()/FRAME_ROWS);              // #10
        walkDown = new TextureRegion[FRAME_COLS];
        walkLeft = new TextureRegion[FRAME_COLS];
        walkRight = new TextureRegion[FRAME_COLS];
        walkUp = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
                walkDown[i] = tmp[0][i];
                walkLeft[i] = tmp[1][i];
                walkRight[i] = tmp[3][i];
                walkUp[i] = tmp[2][i];
        }
        Animation startAnimation = new Animation(0.1f, walkDown[FRAME_COLS-1]);
        walkDownAnimation = new Animation(0.15f, walkDown);
        walkLeftAnimation = new Animation(0.15f, walkLeft);
        walkRightAnimation = new Animation(0.15f, walkRight);
        walkUpAnimation = new Animation(0.15f, walkUp);
        currentAnimation = startAnimation;
        stateTime = 0f;




/*
        CircleShape circle = new CircleShape();
        circle.setRadius(RADIUS);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = 2f;
        bodyDef.position.y = 2f;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;

        this.body = world.createBody(bodyDef);
        //this.body.setUserData(ElementType.BOB);

        Fixture fix = body.createFixture(circle, 50);
        fix.setDensity(1);
        fix.setFriction(1f);
        fix.setRestitution(0.8f);
        //fix.setFilterData(filter);

        circle.dispose();*/

/*        // generate bob's actor
        this.setPosition(body.getPosition().x-RADIUS, body.getPosition().y-RADIUS); // set the actor position at the box2d body position
        this.setSize(RADIUS*2, RADIUS*2); // scale actor to body's size
        this.setScaling(Scaling.stretch); // stretch the texture
        this.setAlign(Align.center);*/

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

       // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        if ( unlocked ){
            currentFrame = currentAnimation.getKeyFrame( stateTime, false );
        }   else    {
            currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, getX(), getY(), Cell.width, Cell.height);
    }

    @Override
    public void act ( float delta ) {
        super.act(delta);
        //checkCell();



        updateState();
    }

    private void updateState () {
/*        if ( unlocked ){
            currentAnimation =
        }   else    {

        }*/

    }

    private Action unlock (){
        return Actions.run(new Runnable() {
            public void run() {
                unlocked = true;
            }
        });
    }

    private Action lock (){
        return Actions.run(new Runnable() {
            public void run() {
                unlocked = false;
            }
        });
    }

    public void mooveUp (){
        if ( ( isNoWallUp() && unlocked) || isMoovingToNextIntersection ){
     //       System.out.println("UP");
            currentAnimation = walkUpAnimation;
            this.addAction( Actions.sequence( lock() , Actions.moveTo(getX(), getY() + Cell.height, 0.1f), mooveToNextIntersection( currentCell.i - 1, currentCell.j ) ) );
        }
    }

    public void mooveDown() {
        if ( (isNoWallDown() && unlocked) || isMoovingToNextIntersection ) {
      //      System.out.println("DOWN");
            currentAnimation = walkDownAnimation;
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX(), getY() - Cell.height, 0.1f), mooveToNextIntersection( currentCell.i + 1, currentCell.j ) ) );
        }
    }

    public void mooveLeft() {
        if ( ( isNoWallLeft() && unlocked) || isMoovingToNextIntersection ) {
      //      System.out.println("LEFT");
            currentAnimation = walkLeftAnimation;
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX() - Cell.width, getY(), 0.1f), mooveToNextIntersection( currentCell.i, currentCell.j - 1 ) ) );
        }
    }

    public void mooveRight() {
        if ( ( isNoWallRight() && unlocked) || isMoovingToNextIntersection ) {
        //    System.out.println("RIGHT");
            currentAnimation = walkRightAnimation;
            this.addAction( Actions.sequence( lock(), Actions.moveTo(getX() + Cell.width, getY(), 0.1f), mooveToNextIntersection( currentCell.i, currentCell.j + 1 ) ) );
        }
    }
/*    private Action setCell ( final int newI, final int newJ ) {

        return Actions.run(new Runnable() {
            public void run() {
                prevCell = currentCell;
                currentCell = cells[newI][newJ];
            }
        });

    }*/

    private int getOpenDirections (){
        int directions = 4;
        if ( !isNoWallUp() ){
            directions--;
        }
        if ( !isNoWallDown() ){
            directions--;
        }
        if ( !isNoWallLeft() ){
            directions--;
        }
        if ( !isNoWallRight() ){
            directions--;
        }
        return directions;
    }

    private Action mooveToNextIntersection ( final int newI, final int newJ){

        return Actions.run(new Runnable() {
            public void run() {
                isMoovingToNextIntersection = true;

                prevCell = currentCell;
                currentCell = cells[newI][newJ];


                if ( getOpenDirections() == 1 ){
                    prevCell = cells[0][0];
                }

                if ( getOpenDirections() == 2 && !isTouchingDoor ) {
                    if ( isNoWallUp() && currentCell.i - 1 != prevCell.i ){
                        mooveUp();
                    } else
                    if ( isNoWallRight() && currentCell.j + 1 != prevCell.j ){
                        mooveRight();
                    } else
                    if ( isNoWallDown() && currentCell.i + 1 != prevCell.i ){
                        mooveDown();
                    } else
                    if ( isNoWallLeft() && currentCell.j - 1 != prevCell.j ){
                        mooveLeft();
                    }
                }   else {
                    unlocked = true;
                    isMoovingToNextIntersection = false;
                }
            }
        });

    }

    public boolean isNoWallUp (){
        if ( cells[currentCell.i - 1][currentCell.j].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
    public boolean isNoWallDown (){
        if ( cells[currentCell.i + 1][currentCell.j].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
    public boolean isNoWallLeft (){
        if ( cells[currentCell.i][currentCell.j - 1 ].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
    public boolean isNoWallRight (){
        if ( cells[currentCell.i][currentCell.j + 1].isWall ){
            return false;
        }   else    {
            return true;
        }
    }
}
