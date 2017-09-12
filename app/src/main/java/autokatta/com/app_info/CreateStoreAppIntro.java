package autokatta.com.app_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;

import autokatta.com.R;

/**
 * Created by ak-001 on 12/9/17.
 */

public class CreateStoreAppIntro extends AppIntro {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_store_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_store_two));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_store_three));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_store_five));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_store_six));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_store_seven));

        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(false);

        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        //setVibrate(true);
        //setVibrateIntensity(30);

        //Add animation to the intro slider
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
