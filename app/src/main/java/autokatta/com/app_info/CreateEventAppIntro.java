package autokatta.com.app_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;

import autokatta.com.R;

/**
 * Created by ak-001 on 12/9/17.
 */

public class CreateEventAppIntro extends AppIntro {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_event_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_event_two));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_event_three));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_event_pay_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_create_event_pay_four));

        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(true);

        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        //setVibrate(true);
        //setVibrateIntensity(30);

        //Add animation to the intro slider
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        finish();
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