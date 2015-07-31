package com.darkkeeper.themaze.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.darkkeeper.themaze.Basics.Assets;
import com.darkkeeper.themaze.TheMaze;
import com.darkkeeper.themaze.Utils.Constants;

/**
 * Created by andreipiatosin on 5/29/15.
 */
public class Cell extends Actor {
    public static float width;
    public static float height;
    public boolean isWall;
    public int i;
    public int j;
    public World world;

    private float isWallUp;
    private float isWallRight;
    private float isWallDown;
    private float isWallLeft;

    private ShaderProgram shader;
    private ShaderProgram shaderGround;

    public Cell ( World world, float width, float height, int i, int j) {
        this.width = width;
        this.height = height;
        this.isWall = Constants.cells[i][j];
        this.i = i;
        this.j = j;
        this.world = world;

        try {
            if (Constants.cells[i-1][j] == true) {
                isWallUp = 1.0f;
            }   else {
                isWallUp = 0.0f;
            }
        }   catch (ArrayIndexOutOfBoundsException exception){
            isWallUp = 0.0f;
        }
        try {
            if (Constants.cells[i][j+1] == true) {
                isWallRight = 1.0f;
            }   else    {
                isWallRight = 0.0f;
            }
        }   catch (ArrayIndexOutOfBoundsException exception){
            isWallRight = 0.0f;
        }
        try {
            if (Constants.cells[i+1][j] == true) {
                isWallDown = 1.0f;
            }   else    {
                isWallDown = 0.0f;
            }
    }   catch (ArrayIndexOutOfBoundsException exception){
            isWallDown = 0.0f;
    }
        try {
            if (Constants.cells[i][j-1] == true) {
                isWallLeft = 1.0f;
            }   else    {
                isWallLeft = 0.0f;
            }
}   catch (ArrayIndexOutOfBoundsException exception){
            isWallLeft = 0.0f;
        }




        String vertexShader =
                "attribute vec4 a_position;\n" +
                        "attribute vec4 a_color;\n" +
                        "attribute vec2 a_texCoord0;\n" +
                        "\n" +
                        "uniform mat4 u_projTrans;\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoord;" +
                        "varying vec4 v_position;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = a_color;\n" +
                        "    v_texCoord = a_texCoord0;\n" +
                        "    gl_Position = u_projTrans * a_position;" +
                        "    v_position = a_position;\n" +
                        "}";



        String fragmentShader =

                "#ifdef GL_ES\n" +
                        "    precision mediump float;\n" +
                        "#endif\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoord;\n" +
                        "varying vec4 v_position;\n" +
                        "\n" +
                        "uniform sampler2D u_texture;\n" +
                        "uniform vec2 u_pos;\n" +
                        "uniform vec2 u_size;\n" +
                        "uniform vec4 u_directions;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 color = v_color * texture2D(u_texture, v_texCoord);\n" +
                        "\n" +
                        "    if ( v_position.x < u_pos.x + 0.2 * u_size.x && u_directions.a != 1.0 ){\n" +
                        "        color.rgb = mix((color.rgb + 0.4),color.rgb, (v_position.x-u_pos.x)/(0.2*u_size.x) );\n" +
                        "    }   " +
                        "    if ( v_position.x > u_pos.x + 0.8 * u_size.x  && u_directions.y != 1.0 )   {\n" +
                        "        color.rgb = mix( (color.rgb - 0.4),color.rgb, (u_pos.x + u_size.x - v_position.x)/(0.2 * u_size.x));\n" +
                        "    }   " +
                        "    if ( v_position.y < u_pos.y + 0.2 * u_size.y  && u_directions.z != 1.0 )   {\n" +
                        "        color.rgb = mix((color.rgb - 0.4),color.rgb, (v_position.y-u_pos.y)/(0.2*u_size.y) );\n" +
                        "    }   " +
                        "    if ( v_position.y > u_pos.y + 0.8 * u_size.y  && u_directions.x != 1.0 )   {\n" +
                        "        color.rgb = mix((color.rgb + 0.4),color.rgb, (u_pos.y + u_size.y - v_position.y)/(0.2 * u_size.y) );\n" +
                        "    }\n" +
                        "\n" +
                        "    gl_FragColor = color;\n" +
                        "}";


        String fragmentShader2 =

                "#ifdef GL_ES\n" +
                        "    precision mediump float;\n" +
                        "#endif\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoord;\n" +
                        "varying vec4 v_position;\n" +
                        "\n" +
                        "uniform sampler2D u_texture;\n" +
                        "uniform vec2 u_pos;\n" +
                        "uniform vec2 u_size;\n" +
                        "uniform vec4 u_directions;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 color = v_color * texture2D(u_texture, v_texCoord);\n" +
                        "    float distance = texture2D(u_texture, v_texCoord).a;\n" +
                        "    float vignette = smoothstep (0.1,0.4, distance );\n" +
                        "\n" +
                        "\n" +
                        "    if ( v_position.x < u_pos.x + 0.4 * u_size.x && u_directions.a == 1.0 ){\n" +
                        "        color.rgb = mix((color.rgb - 0.3),color.rgb, (v_position.x-u_pos.x)/(0.4*u_size.x) );\n" +
                        "    }   " +
                        "    if ( v_position.y > u_pos.y + 0.6 * u_size.y  && u_directions.x == 1.0 )   {\n" +
                        "        color.rgb = mix((color.rgb - 0.3),color.rgb, (u_pos.y + u_size.y - v_position.y)/(0.4 * u_size.y) );\n" +
                        "    }\n" +
                        "\n" +
                        "    gl_FragColor = color;\n" +
                        "}";


        shader = new ShaderProgram(vertexShader, fragmentShader);
        shaderGround = new ShaderProgram( vertexShader, fragmentShader2 );

        if (!shader.isCompiled()) throw new GdxRuntimeException("Couldn't compile shader: " + shader.getLog());
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {

        Texture texture = Assets.currentStyleTexture;

        if ( isWall ) {
            shader.begin();
            shader.setUniformf( "u_pos", getX(), getY() );
            shader.setUniformf( "u_size", width, height );
            shader.setUniformf( "u_directions", isWallUp, isWallRight, isWallDown, isWallLeft );
            shader.end();

            batch.setShader( shader );
            batch.draw(texture, getX(),getY(), width,height, width/TheMaze.WIDTH*j, ( height/TheMaze.HEIGHT*i )/2, width/TheMaze.WIDTH*(j+1), ( height/TheMaze.HEIGHT*(i+1) )/2 );
        }   else    {

            shaderGround.begin();
            shaderGround.setUniformf("u_pos", getX(), getY());
            shaderGround.setUniformf( "u_size", width, height );
            shaderGround.setUniformf( "u_directions", isWallUp, isWallRight, isWallDown, isWallLeft );
            shaderGround.end();

            batch.setShader( shaderGround );
            batch.draw(texture, getX(),getY(), width,height, width/TheMaze.WIDTH*j, ( height/TheMaze.HEIGHT*i )/2 + 0.5f, width/TheMaze.WIDTH*(j+1), ( height/TheMaze.HEIGHT*(i+1) )/2 + 0.5f );
        }

        batch.setShader( null );
    }
}
