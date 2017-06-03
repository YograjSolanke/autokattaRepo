package autokatta.com.other;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.flexbox.FlexboxLayout;

import autokatta.com.R;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;

public class AddTags extends AppCompatActivity {

    private static final String TAG = "DemoActivity";
    ChipCloud chipCloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        Button mOk = (Button) findViewById(R.id.ok);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FlexboxLayout flexbox = (FlexboxLayout) findViewById(R.id.flexbox);

        ChipCloudConfig config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#ddaa00"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#efefef"))
                .uncheckedTextColor(Color.parseColor("#666666"));

        chipCloud = new ChipCloud(this, flexbox, config);

        chipCloud.addChip("HelloWorld!");

        String[] demoArray = getResources().getStringArray(R.array.demo_array);
        chipCloud.addChips(demoArray);

        chipCloud.setChecked(2);

        String label = chipCloud.getLabel(2);
        Log.d(TAG, "Label at index 2: " + label);

        chipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s", index, checked));
                    Log.i("asdf", "->" + chipCloud.getLabel(index));
                }
            }
        });
    }
}
