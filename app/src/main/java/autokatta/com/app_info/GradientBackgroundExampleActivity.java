package autokatta.com.app_info;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;

public class GradientBackgroundExampleActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Create your profile in autokatta", "a place where service providers and customers, engage and manage business..", R.drawable.hdlogo);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("", "Tell more about yourself..", R.drawable.hdlogo);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Tell us your interests", "This will help us to connect you to the right customers and service providers.", R.drawable.hdlogo);
        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("My vehicle", "upload the vehicles you own with correct information and we will remind you expiring services in time.", R.drawable.hdlogo);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard4.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);
        pages.add(ahoyOnboarderCard4);

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "font/Roboto-Light.ttf");
        setFont(face);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        Toast.makeText(this, "Finish Pressed", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getApplicationContext(), SolidBackgroundExampleActivity.class));
    }
}
