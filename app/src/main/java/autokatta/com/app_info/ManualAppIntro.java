package autokatta.com.app_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;

import autokatta.com.R;

/**
 * Created by ak-001 on 5/7/17.
 */

public class ManualAppIntro extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_manual_enquiry_one));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_manual_enquiry_two));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_manual_enquiry_three));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_manual_enquiry_four));
        addSlide(AppIntroSlider.newInstance(R.layout.app_intro_manual_enquiry_five));

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
} /*AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Inquiries",
                "Get inquiries for your products, service or vehicles as it gets searched.");
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Add manual enquiries against the inventory and" +
                " keep a track of the sales cycle of each customer.", "");
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Get inquiry from the product/ service or vehicle inventory page. Customer send enquiry directly as a business" +
                " communication", "");
        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("Customers send enquiry from the store page through the inquiry module.",
                "");
        AhoyOnboarderCard ahoyOnboarderCard5 = new AhoyOnboarderCard("Manage inquires and update status of the inquiries, follow take mail of the inquiry list received against each inventory.",
                "");

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
