package com.darkkeeper.themaze.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

/**
 * Created by andreipiatosin on 8/4/15.
 */
public class MySlider extends Slider {
    public Image currentImage;
    public Image currentImage2;

    public MySlider(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        super(min, max, stepSize, vertical, skin, styleName);
    }
}
