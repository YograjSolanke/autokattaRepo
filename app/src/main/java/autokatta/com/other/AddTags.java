package autokatta.com.other;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.GetAllInterestResponse;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;
import retrofit2.Response;

public class AddTags extends AppCompatActivity implements RequestNotifier {

    ChipCloud chipCloud;
    List<String> lst = new ArrayList<>();
    HashMap<String, Integer> hashMap = new HashMap<>();
    List<String> mInterestResponse = new ArrayList<>();
    List<String> lst1 = new ArrayList<>();
    String numberString = "";
    String action = "", interest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        setTitle("Interest");
        //getInterest();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Button mOk = (Button) findViewById(R.id.ok);
        FlexboxLayout flexbox = (FlexboxLayout) findViewById(R.id.flexbox);

        if (getIntent().getExtras() != null) {
            interest = getIntent().getExtras().getString("interest", "");
            action = getIntent().getExtras().getString("action", "");

            if (action != null) {
                if (action.equalsIgnoreCase("other")) {
                    mOk.setVisibility(View.GONE);
                    flexbox.setEnabled(false);
                }
            }
        }
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberString = "";
                for (int i = 0; i < lst.size(); i++) {
                    if (numberString.equalsIgnoreCase("")) {
                        numberString = lst.get(i);
                    } else {
                        numberString = numberString + "," + lst.get(i);
                    }
                }
                Log.i("numberString", "->" + numberString);
                Addintresttoprofile(numberString);
                getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit()
                        .putString("interest", numberString).apply();
                finish();
            }
        });

        ChipCloudConfig config;
        if (action.equalsIgnoreCase("other")) {
            config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.none)
                    .checkedChipColor(Color.parseColor("#ddaa00"))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#efefef"))
                    .uncheckedTextColor(Color.parseColor("#666666"));

            chipCloud = new ChipCloud(getApplicationContext(), flexbox, config);
        } else {
            config = new ChipCloudConfig()
                    .selectMode(ChipCloud.SelectMode.multi)
                    .checkedChipColor(Color.parseColor("#ddaa00"))
                    .checkedTextColor(Color.parseColor("#ffffff"))
                    .uncheckedChipColor(Color.parseColor("#efefef"))
                    .uncheckedTextColor(Color.parseColor("#666666"));

            chipCloud = new ChipCloud(getApplicationContext(), flexbox, config);

        }


        //chipCloud.addChip("HelloWorld!");


        String[] demoArray = getResources().getStringArray(R.array.demo_array);
        chipCloud.addChips(demoArray);
        chipCloud.deselectIndex(0);

        if (!interest.equalsIgnoreCase("")) {

            Log.i("inter", "->" + interest);
            String[] commaSplit = new String[0];
            if (!interest.equalsIgnoreCase("")) {
                commaSplit = interest.split(",");

                for (int i = 0; i < commaSplit.length; i++) {
                    Log.i("intesadr", "->" + Integer.parseInt(commaSplit[i]));
                    chipCloud.setChecked(Integer.parseInt(commaSplit[i]));
                    lst.add(commaSplit[i]);
                }
            }
        }

        //chipCloud.setChecked(2);

        //String label = chipCloud.getLabel(2);
        //Log.d(TAG, "Label at index 2: " + label);

        chipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if (userClick && index != 0) {
                    //Log.d(TAG, String.format("chipCheckedChange Label at index: %d checked: %s", index, checked));
                    //Log.i("asdf", "->" + chipCloud.getLabel(index));
                    //String id = String.valueOf(hashMap.get(mInterestResponse.get(index)));
                    if (checked) {
                        lst.add(String.valueOf(index));
                        Log.i("added", "->" + lst.toString());
                        /*Log.i("addedidx", "->" + id);*/

                    } else {
                        lst.remove(String.valueOf(index));
                        Log.i("removed", "->" + lst.toString());
                       /* Log.i("removedidx", "->" + index);*/
                    }
                }
            }
        });

    }

    private void getInterest() {
        ApiCall apiCall = new ApiCall(this, this);
        apiCall.getInterest();
    }

    private void Addintresttoprofile(String intrests) {
        ApiCall apiCall = new ApiCall(this, this);
        apiCall.updateProfile(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getInt("loginregistrationid", 0), "", "", "", "", "", "", "", "", "", "", "", intrests, "Interest");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetAllInterestResponse interestResponse = (GetAllInterestResponse) response.body();
                lst1.clear();
                if (!interestResponse.getSuccess().isEmpty()) {
                    for (GetAllInterestResponse.Success success : interestResponse.getSuccess()) {
                        success.setInterestID(success.getInterestID());
                        success.setTitle(success.getTitle());
                        lst1.add(success.getTitle());
                        hashMap.put(success.getTitle(), success.getInterestID());
                    }
                    mInterestResponse.addAll(lst1);
                    chipCloud.addChips(lst1);
                    try {
                        if (getIntent().getExtras() != null) {
                            List<Integer> list = new ArrayList<>();
                            String interest = getIntent().getExtras().getString("interest");
                            Log.i("inter", "->" + interest);
                            String[] commaSplit = interest.split(",");
                            /*for (int i = 1; i <= commaSplit.length; i++) {
                                Log.i("intesadr", "->" + Integer.parseInt(commaSplit[i]));
                                //list.add(Integer.parseInt(commaSplit[i]));
                                if (Integer.parseInt(commaSplit[i]) ==24){
                                    chipCloud.setChecked(5);
                                }else {
                                    chipCloud.setChecked(Integer.parseInt(commaSplit[i]));
                                }
                            }*/
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

            }
        } else {

        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            if (str.equals("success_update_Interest")) {
                CustomToast.customToast(getApplicationContext(), "Interest Added");
            }
        }
    }
}
