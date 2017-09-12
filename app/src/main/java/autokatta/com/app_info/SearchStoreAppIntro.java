package autokatta.com.app_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;

import autokatta.com.R;

/**
 * Created by ak-001 on 5/7/17.
 */

public class SearchStoreAppIntro extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_search_store_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_search_store_two));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_search_store_three));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_search_store_four));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_search_store_five));

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
}/*AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Search store Based on store name.", "");
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Search store based on a keyword.", "");
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Search store based on a contact number of that store", "");
        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("Store results are displayed.", "");
        AhoyOnboarderCard ahoyOnboarderCard5 = new AhoyOnboarderCard("Keep following the stores if you are their " +
                "existing customer. This will help you to do business and get better service from them.", "");

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard4.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard5.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);
        pages.add(ahoyOnboarderCard4);
        pages.add(ahoyOnboarderCard5);

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
    }
}*/