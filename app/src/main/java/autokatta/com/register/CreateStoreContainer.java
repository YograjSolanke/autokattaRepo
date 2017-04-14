package autokatta.com.register;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import autokatta.com.R;
import autokatta.com.my_store.CreateStoreFragment;

public class CreateStoreContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store_container);

        String mCall = getIntent().getExtras().getString("call");
        Log.i("mCall", "->" + mCall);
        Bundle bundle = new Bundle();
        bundle.putString("call", mCall);
        CreateStoreFragment storeFragment = new CreateStoreFragment();
        storeFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = manager.beginTransaction();
        mFragmentTransaction.replace(R.id.create_store_container, storeFragment).commit();
    }
}