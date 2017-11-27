package model;

import java.util.Observable;

/**
 * HSVModel class - used to control color swatch when working with MainActivity Class
 * Yanming Meng (meng0028)
 */

public class HSVModel extends Observable {
    // CLASS VARIABLES
    public static final Integer MAX_HUE = 359;
    public static final Integer MIN_HUE = 0;
    public static final float MAX_SAT = 1;
    public static final float MIN_SAT = 0;
    public static final float MAX_BRT = 1;
    public static final float MIN_BRT = 0;


    // INSTANCE VARIABLES
    private Integer hue;
    private float saturation;
    private float brightness;

    /**
     * No argument constructor.
     * <p>
     * Instantiate a new instance of this class, and
     * initialize hue, saturation and brightness to max values.
     */
    public HSVModel() {
        this(MAX_HUE, MAX_SAT, MAX_BRT);
    }

    /**
     * Convenience constructor.
     *
     * @param hue-        starting hue value
     * @param saturation- starting saturation- value
     * @param brightness- starting brightness value
     */
    public HSVModel(Integer hue, float saturation, float brightness) {
        super();
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    //Setting HSV values for each button
    public void asRed() {
        this.setHSV(0, 1, 1);
    }

    public void asOrange() {
        this.setHSV(30, 1, 1);
    }

    public void asYellow() {
        this.setHSV(60, 1, 1);
    }

    public void asGreen() {
        this.setHSV(120, 1, 1);
    }

    public void asBlue() {
        this.setHSV(240, 1, 1);
    }

    public void asIndigo() {
        this.setHSV(275, 1, 1);
    }

    public void asViolet() {
        this.setHSV(282, 1, 1);
    }

    //GETTERS
    public Integer getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    //SETTERS
    public void setHue(Integer hue) {
        this.hue = hue;
        this.updateObservers();
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
        this.updateObservers();
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
        this.updateObservers();
    }

    //CONVENIENT SETTER
    public void setHSV(Integer hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.updateObservers();
    }

    // the model has changed!
    // broadcast the update method to all registered observers
    private void updateObservers() {
        this.setChanged();
        this.notifyObservers();
    }
}


