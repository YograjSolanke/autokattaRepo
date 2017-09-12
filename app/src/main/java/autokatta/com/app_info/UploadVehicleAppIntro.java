package autokatta.com.app_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;

import autokatta.com.R;

/**
 * Created by ak-001 on 5/7/17.
 */

public class UploadVehicleAppIntro extends AppIntro {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
//adding the three slides for introduction app you can ad as many you needed
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_upload_vehicle_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_upload_vehicle_two));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_upload_vehicle_pay_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_upload_vehicle_pay_two));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_upload_vehicle_pay_three));

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
/* extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Upload your used vehicle for sale.",
                "");
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Upload maximum details of the vehicles so that the " +
                "quality lead can be passed on to you.", "");

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.white);
            page.setTitleTextSize(dpToPixels(7, this));
            page.setDescriptionTextSize(dpToPixels(5, this));
            page.setIconLayoutParams(450, 450, 0, 0, 0, 0);
        }

        setFinishButtonTitle("Finish");
        showNavigationControls(true);
        setGradientBackground(R.drawable.translates);

        //set the button style you created
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "font/Roboto-Light.ttf");
        setFont(face);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        //Toast.makeText(this, "Finish Pressed", Toast.LENGTH_SHORT).show();
        finish();
        //startActivity(new Intent(getApplicationContext(), SolidBackgroundExampleActivity.class));
    }*/
}