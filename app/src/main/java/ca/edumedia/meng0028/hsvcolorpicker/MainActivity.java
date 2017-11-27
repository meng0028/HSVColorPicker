package ca.edumedia.meng0028.hsvcolorpicker;


import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import model.HSVModel;

/**
 * MAD9132 Midterm Assignment - HSV color picker App
 * This app is designed for picking color by changing hue, saturation and brightness.
 *
 * @author Yanming Meng (meng0028@algonquinlive.com)
 */

public class MainActivity extends Activity implements Observer, SeekBar.OnSeekBarChangeListener {
    // CLASS VARIABLES
    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    // INSTANCE VARIABLES
    // Pro-tip: different naming style; the 'm' means 'member'
    //private AboutDialogFragment mAboutDialog;
    private TextView mColorSwatch;
    private HSVModel mModel;
    private TextView mHueTV;
    private SeekBar mHueSB;
    private TextView mSaturationTV;
    private SeekBar mSaturationSB;
    private TextView mBrightnessTV;
    private SeekBar mBrightnessSB;
//About Dialog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//The event handler for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create new model
        mModel = new HSVModel();
        //set initial color to black
        mModel.setHSV(HSVModel.MIN_HUE, HSVModel.MIN_SAT, HSVModel.MIN_BRT);
        // The Model is observing this Controller (class MainActivity implements Observer)
        mModel.addObserver(this);
        // reference each View
        mColorSwatch = (TextView) findViewById(R.id.colorSwatch);
        mHueTV = (TextView) findViewById(R.id.hueTV);
        mHueSB = (SeekBar) findViewById(R.id.hueSB);
        mSaturationTV = (TextView) findViewById(R.id.saturationTV);
        mSaturationSB = (SeekBar) findViewById(R.id.saturationSB);
        mBrightnessTV = (TextView) findViewById(R.id.brightnessTV);
        mBrightnessSB = (SeekBar) findViewById(R.id.brightnessSB);
        // set the domain (i.e. max) for each component
        mHueSB.setMax(HSVModel.MAX_HUE);
        mSaturationSB.setMax(100);
        mBrightnessSB.setMax(100);
        // register the event handler for each <SeekBar>
        mHueSB.setOnSeekBarChangeListener(this);
        mSaturationSB.setOnSeekBarChangeListener(this);
        mBrightnessSB.setOnSeekBarChangeListener(this);
        //Show current HSV values when click on color swatch
        mColorSwatch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int hue = mModel.getHue();
                float saturation = mModel.getSaturation();
                //converted to percentage
                saturation = saturation * 100;
                float brightness = mModel.getBrightness();
                brightness = brightness * 100;
                String string = "H: " + hue + "\u00B0 S: " + saturation + "% V: " + brightness + "%";
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // initialize the View to the values of the Model
        this.updateView();
    }

    public void colorBtn(View view) {
        switch (view.getId()) {
            case R.id.redBtn:
                mModel.asRed();
                break;
            case R.id.orangeBtn:
                mModel.asOrange();
                break;
            case R.id.yellowBtn:
                mModel.asYellow();
                break;
            case R.id.greenBtn:
                mModel.asGreen();
                break;
            case R.id.blueBtn:
                mModel.asBlue();
                break;
            case R.id.indigoBtn:
                mModel.asIndigo();
                break;
            case R.id.violetBtn:
                mModel.asViolet();
                break;
        }
        int hue = mModel.getHue();
        float saturation = mModel.getSaturation();
        saturation = saturation * 100;
        float brightness = mModel.getBrightness();
        brightness = brightness * 100;
        String string = "H: " + hue + "\u00B0 S: " + saturation + "% V: " + brightness + "%";
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * Event handler for the <SeekBar>s: hue, saturation and brightness
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // Did the user cause this event?
        // YES > continue
        // NO  > leave this method
        if (fromUser == false) {
            return;
        }
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mModel.setHue(mHueSB.getProgress());
                mHueTV.setText(getResources().getString(R.string.hueProgress, progress).toUpperCase());
                break;
            case R.id.saturationSB:
                float saturation = mSaturationSB.getProgress();
                saturation = saturation / 100;
                mModel.setSaturation(saturation);
                mSaturationTV.setText(getResources().getString(R.string.saturationProgress, progress).toUpperCase() + "%");
                break;
            case R.id.brightnessSB:
                float brightness = mBrightnessSB.getProgress();
                brightness = brightness / 100;
                mModel.setBrightness(brightness);
                mBrightnessTV.setText(getResources().getString(R.string.brightnessProgress, progress).toUpperCase() + "%");
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // No-Operation
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mHueTV.setText(getResources().getString(R.string.hue));
                break;
            case R.id.saturationSB:
                mSaturationTV.setText(getResources().getString(R.string.saturation));
                break;
            case R.id.brightnessSB:
                mBrightnessTV.setText(getResources().getString(R.string.brightness));
                break;
        }
    }

    // The Model has changed state!
    // Refresh the View to display the current values of the Model.
    @Override
    public void update(Observable observable, Object data) {
        this.updateView();
    }

    private void updateColorSwatch() {
        float[] hsv = {mModel.getHue(), mModel.getSaturation(), mModel.getBrightness()};
        mColorSwatch.setBackgroundColor(Color.HSVToColor(hsv));
    }

    private void updateHueSB() {
        mHueSB.setProgress(mModel.getHue());
    }

    private void updateSaturationSB() {
        float saturation = mModel.getSaturation();
        saturation = saturation * 100;
        mSaturationSB.setProgress((int) saturation);
    }

    private void updateBrightnessSB() {
        float brightness = mModel.getBrightness();
        brightness = brightness * 100;
        mBrightnessSB.setProgress((int) brightness);
    }

    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateSaturationSB();
        this.updateBrightnessSB();
    }
}


