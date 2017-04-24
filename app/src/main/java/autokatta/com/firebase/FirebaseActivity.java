package autokatta.com.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import autokatta.com.R;
import autokatta.com.view.OtherProfile;

public class FirebaseActivity extends AppCompatActivity {
    String key, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                key = extras.getString("key");
                value = extras.getString("value");
                String no = extras.getString("number");
                Log.i("Contactttttttttttttt", "->" + no);

                if (key.equals("Like") && value.equalsIgnoreCase("Profile")) {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("like", key);
                    mBundle.putString("contact", value);
                    Intent intent = new Intent(getApplicationContext(), OtherProfile.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
