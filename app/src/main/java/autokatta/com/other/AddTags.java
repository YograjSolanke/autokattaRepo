package autokatta.com.other;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;

public class AddTags extends AppCompatActivity {

    private static final String TAG = "DemoActivity";
    ChipCloud chipCloud;
    List<String> lst = new ArrayList<>();
    List<String> lst1 = new ArrayList<>();

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

        lst1.add("1");
        lst1.add("5");
        lst1.add("7");
        lst1.add("12");
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

        for (int i = 0; i < lst1.size(); i++) {
            chipCloud.setChecked(Integer.parseInt(lst1.get(i)));
        }
        //chipCloud.setChecked(2);

        //String label = chipCloud.getLabel(2);
        //Log.d(TAG, "Label at index 2: " + label);

        chipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick) {
                    Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s", index, checked));
                    Log.i("asdf", "->" + chipCloud.getLabel(index));
                    if (checked) {
                        lst.add(chipCloud.getLabel(index));
                        Log.i("added", "->" + lst.toString());
                        Log.i("addedidx", "->" + index);
                    } else {
                        lst.remove(chipCloud.getLabel(index));
                        Log.i("removed", "->" + lst.toString());
                        Log.i("removedidx", "->" + index);
                    }
                }
            }
        });
    }
}
