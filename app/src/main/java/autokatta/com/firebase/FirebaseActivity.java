package autokatta.com.firebase;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.view.OtherProfile;

public class FirebaseActivity extends AppCompatActivity {
    String key, value, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                key = extras.getString("key");
                value = extras.getString("value");
                //no = extras.getString("contact");
                no = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("firebaseContact", null);
                Log.i("Contactttttttttttttt", "->" + no);
                Log.i("data", "->" + no);
                Log.i("key", "->" + key);
                Log.i("value", "->" + value);

                if (key.equals("Like") && value.equalsIgnoreCase("Likes Profile")) {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("like", key);
                    mBundle.putString("firebaseContact", no);
                    Intent intent = new Intent(getApplicationContext(), OtherProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(FirebaseActivity.this, R.anim.left_to_right, R.anim.right_to_left);
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
        }
    }
}
