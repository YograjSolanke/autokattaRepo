package autokatta.com.broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.AutokattaApplication;
import autokatta.com.R;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetAutokattaContactResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ak-001 on 30/3/17.
 */

public class BackgroundService extends Service {

    List<String> names = new ArrayList<>();
    List<String> numbers = new ArrayList<>();
    String numberstring = "", namestring = "";
    DbOperation operation;

    IBinder mIBinder = new LocalBinder();
    AutokattaApplication autokattaApplication = new AutokattaApplication();

    private class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    /*
    On Create Method...
     */

    @Override
    public void onCreate() {
        super.onCreate();
        try {

            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor people = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);

            int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            people.moveToFirst();
            do {
                String name = people.getString(indexName);
                String number = people.getString(indexNumber);

                number = number.replaceAll("-", "");
                number = number.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("[\\D]", "");

                if (number.length() > 10)
                    number = number.substring(number.length() - 10);

                if (!number.equalsIgnoreCase(getApplicationContext().getSharedPreferences(getString(R.string.my_preference)
                        , MODE_PRIVATE).getString("loginContact", ""))) {
                    names.add(name);
                    numbers.add(number);
                }
            } while (people.moveToNext());
            autokattaApplication.setName(names);
            autokattaApplication.setNumber(numbers);
            Log.i("Back Name", "->" + names);
            Log.i("Back Name", "->" + names);
            Log.i("Back Name", "->" + names);
            Log.i("Back Name", "->" + names);
            System.out.println("rutu-------------------- names -arrylist--" + names);
            System.out.println("rutu-------------------- numbers arrylist--  -" + numbers);
            System.out.println("rutu-------------------- numbers arrylist 1--  -" + autokattaApplication.getName());
            System.out.println("rutu-------------------- numbers arrylist 1--  -" + autokattaApplication.getNumber());
            if (!(numbers.size() == 0)) {
                for (int i = 0; i < numbers.size(); i++) {
                    if (i == 0 && numberstring.equalsIgnoreCase("") && namestring.equalsIgnoreCase("")) {
                        numberstring = numbers.get(i);
                        namestring = names.get(i);
                    } else {
                        numberstring = numberstring + "," + numbers.get(i);
                        namestring = namestring + "," + names.get(i);
                    }
                }
            }
            getAutokattaContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Autokatta Contacts...
     */
    private void getAutokattaContacts() {
        try {
            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(getApplicationContext().getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();
            ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
            Call<GetAutokattaContactResponse> mAutokattaContact = mServiceApi
                    .getAutokattaContact(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""), numberstring, namestring);
            mAutokattaContact.enqueue(new Callback<GetAutokattaContactResponse>() {
                @Override
                public void onResponse(Call<GetAutokattaContactResponse> call, Response<GetAutokattaContactResponse> response) {
                    if (response.isSuccessful()) {
                        long result = 0;
                        GetAutokattaContactResponse mContactResponse = response.body();
                        for (GetAutokattaContactResponse.Success success : mContactResponse.getSuccess()) {
                            success.setUserName(success.getUserName());
                            success.setProfilePic(success.getProfilePic());
                            success.setContact(success.getContact());
                            success.setFollowStatus(success.getFollowStatus());
                            success.setMystatus(success.getMystatus());

                            operation = new DbOperation(getApplicationContext());
                            operation.OPEN();
                            result = operation.addMyAutokattaContact(success.getUserName(), success.getProfilePic(),
                                    String.valueOf(success.getContact()), success.getFollowStatus(), success.getMystatus());
                        }
                        if (result > 0) {
                            Log.i("TAG", "Record Inserted Successfully");
                        }
                        operation.CLOSE();
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string._404));
                    }
                }

                @Override
                public void onFailure(Call<GetAutokattaContactResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
