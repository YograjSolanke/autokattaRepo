package autokatta.com.Registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.InviteFriendsAdapter;

public class InviteFriends extends AppCompatActivity {

    private ListView lv;


    EditText inputSearch;
    Button invite,skip;

    InputStream is;
    String result;
    ArrayList<String> names = new ArrayList<String>();
    List<String> numbers = new ArrayList<String>();

    InviteFriendsAdapter adapter;
    String products[] ;

    ArrayList<String> webcontact;
    ArrayList<String> finalcontacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);


        invite=(Button)findViewById(R.id.invite);
        skip=(Button)findViewById(R.id.skip);
        lv = (ListView)findViewById(R.id.l1);
        inputSearch = (EditText)findViewById(R.id.inputSearch);

     //   getallContactRegistered();
    }
}
