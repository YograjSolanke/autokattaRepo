package autokatta.com.app_info;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;

/**
 * Created by ak-001 on 5/7/17.
 */

public class SearchVehicleAppIntro extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("",
                "Search the used vehicle available for sale in Autokatta.", R.drawable.hdlogo);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("", "Your search is saved and matching sellers are " +
                "notified to you.", R.drawable.hdlogo);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("", "Add maximum details so that exact match can be " +
                "notified to you.", R.drawable.hdlogo);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.grey_200);
            page.setTitleTextSize(dpToPixels(12, this));
            page.setDescriptionTextSize(dpToPixels(8, this));
            //page.setIconLayoutParams(width, height, marginTop, marginLeft, marginRight, marginBottom);
        }

        setFinishButtonTitle("Finish");
        showNavigationControls(true);
        setGradientBackground();

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
}