package autokatta.com.broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.request.AutokattaContactRequest;
import autokatta.com.request.ManualEnquiryRequestCount;
import autokatta.com.response.GetAutokattaContactResponse;
import autokatta.com.response.ManualEnquiryResponse;
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
    List<ManualEnquiryRequestCount> mMyGroupsList = new ArrayList<>();
    String numberstring = "", namestring = "";
    DbOperation operation;

    IBinder mIBinder = new LocalBinder();
    private Timer timer;

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

            if (people != null) {
                int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                people.moveToFirst();
                do {
                    Log.i("cursor", "countBackground- " + people.getCount());
                    if (people.getCount() != 0) {
                        String name = people.getString(indexName);
                        String number = people.getString(indexNumber);

                        //number = number.replaceAll("-", "");
                        number = number.replace("-", "").replace("(", "").replace(")", "").replaceAll(" ", "").
                                replaceAll("[\\D]", "");

                        if (number.length() > 10)
                            number = number.substring(number.length() - 10);

                        if (!number.equalsIgnoreCase(getApplicationContext().getSharedPreferences(getString(R.string.my_preference)
                                , MODE_PRIVATE).getString("loginContact", ""))) {
                            names.add(name);
                            numbers.add(number);
                            /*json.put(name,number);
                            Cont cont = new Cont();
                            List<Cont> contact = new ArrayList<>();
                            Contacts contacts = new Contacts();
                            cont.setName(name);
                            cont.setName(number);
                            contact.add(cont);
                            contacts.setContacts(contact);*/

                        }
                        /*jsonArray.put(json);
                        Log.i("Name","Number->"+jsonArray);*/
                    }
                } while (people.moveToNext());
                people.close();
            }

            if (!(numbers.size() == 0)) {
                for (int i = 0; i < numbers.size(); i++) {
                    if (i == 0 && numberstring.equalsIgnoreCase("") && namestring.equalsIgnoreCase("")) {
                        numberstring = numbers.get(i);
                        namestring = names.get(i);
                    } else {
                        numberstring = numberstring + "❖" + numbers.get(i);
                        namestring = namestring + "❖" + names.get(i);
                    }
                }

                Log.i("numberString=", "->" + numberstring);
                Log.i("numberString=", "->" + namestring);

            }
            getAutokattaContacts();
            getEnquiryCount();
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getAutokattaContacts();
                            getEnquiryCount();
                            Log.i("Background", "call webservice");
                        } catch (Exception e) {
                            Log.e("background", e.getMessage());

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);

    }

    public void onDestroy() {
//mNM.cancel(R.string.local_service_started);
        try {
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopSelf();
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

            AutokattaContactRequest autokattaContactRequest = new AutokattaContactRequest(numberstring, namestring, getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                    .getString("loginContact", ""));
            Call<GetAutokattaContactResponse> mAutokattaContact = mServiceApi.getAutokattaContact(autokattaContactRequest);
            mAutokattaContact.enqueue(new Callback<GetAutokattaContactResponse>() {
                @Override
                public void onResponse(Call<GetAutokattaContactResponse> call, Response<GetAutokattaContactResponse> response) {
                    if (response.isSuccessful()) {
                        Log.i("entered", "->");
                        long result = 0;
                        operation = new DbOperation(getApplicationContext());
                        operation.OPEN();
                        operation.deleteAutokattaContacts();
                        operation.createAutokattaContactTable();
                        GetAutokattaContactResponse mContactResponse = response.body();

                        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER};

                        Cursor people = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
                        int indexName = 0, indexNumber = 0;
                        if (people != null) {
                            indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                            indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                            if (mContactResponse.getSuccess() != null) {
                                for (GetAutokattaContactResponse.Success success : mContactResponse.getSuccess()) {
                            /*success.setUserName(success.getUserName());
                            success.setProfilePic(success.getProfilePic());
                            success.setContact(success.getContact());
                            success.setFollowStatus(success.getFollowStatus());
                            success.setMystatus(success.getMystatus());*/
                                    Log.i("asdfd", "asdf" + success.getUserName());

                                    String sContact = success.getContact();
                                    String sUserName = success.getUserName();
                                    String sProfilePic = success.getProfilePic();
                                    String sFollowStatus = success.getFollowStatus();
                                    String sMyStatus = success.getMystatus();

                                    people.moveToFirst();
                                    do {
                                        String name = people.getString(indexName);
                                        String number = people.getString(indexNumber);

                                        number = number.replace("-", "").replace("(", "").replace(")", "").replaceAll(" ", "").
                                                replaceAll("[\\D]", "");

                                        if (number.length() > 10)
                                            number = number.substring(number.length() - 10);

                                        if (number.equalsIgnoreCase(sContact)) {
                                            success.setUserName(name);
                                            success.setProfilePic(success.getProfilePic());
                                            success.setContact(success.getContact());
                                            success.setFollowStatus(success.getFollowStatus());
                                            success.setMystatus(success.getMystatus());
                                            success.setGroupIds(success.getGroupIds());
                                            success.setGroupNames(success.getGroupNames());
                                        }
                                    } while (people.moveToNext());


                                    result = operation.addMyAutokattaContact(success.getUserName(), success.getProfilePic(),
                                            success.getContact(), success.getFollowStatus(), success.getMystatus(),
                                            success.getGroupIds(), success.getGroupNames());
                                }
                                people.close();
                            }
                        }

                        if (result > 0) {
                            Log.i("TAG", "Record Inserted Successfully");
                        }
                        operation.CLOSE();

                    } else {
                        //CustomToast.customToast(getApplicationContext(), getString(R.string._404));
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

    /*
    Get Autokatta Contacts...
     */
    private void getEnquiryCount() {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getApplicationContext().getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();
            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
            Call<ManualEnquiryResponse> mServiceMelaResponse = serviceApi.getManualEnquiry(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                    .getString("loginContact", ""), "null", "null", "null", "null", "null", "null", "null", "null", "null");
            mServiceMelaResponse.enqueue(new Callback<ManualEnquiryResponse>() {
                @Override
                public void onResponse(Call<ManualEnquiryResponse> call, Response<ManualEnquiryResponse> response) {
                    if (response.isSuccessful()) {
                        long result = 0;
                        operation = new DbOperation(getApplicationContext());
                        operation.OPEN();
                        operation.deleteEnquiryCount();
                        operation.createEnquiryCount();
                        mMyGroupsList.clear();
                        ManualEnquiryResponse manualEnquiry = (ManualEnquiryResponse) response.body();
                        if (manualEnquiry.getSuccess() != null) {
                        /*Used Vehicle*/
                            for (ManualEnquiryResponse.Success.UsedVehicle success : manualEnquiry.getSuccess().getUsedVehicle()) {
                                ManualEnquiryRequestCount request = new ManualEnquiryRequestCount();
                                request.setVehicleId(success.getVehicleId());
                                mMyGroupsList.add(request);
                            }

                        /*Products*/
                            for (ManualEnquiryResponse.Success.Product success : manualEnquiry.getSuccess().getProducts()) {
                                ManualEnquiryRequestCount request = new ManualEnquiryRequestCount();
                                request.setProductId(success.getProductId());
                                mMyGroupsList.add(request);
                            }

                        /*Services*/
                            for (ManualEnquiryResponse.Success.Service service : manualEnquiry.getSuccess().getServices()) {
                                ManualEnquiryRequestCount request = new ManualEnquiryRequestCount();
                                request.setServiceId(service.getId());
                                mMyGroupsList.add(request);
                            }
                            result = operation.updateEnquiryCount(mMyGroupsList.size());
                            Cursor cursor = operation.getEnquiryCount();
                            cursor.moveToLast();
                            Log.i("dsafdsfads", "->" + cursor.getString(cursor.getColumnIndex(DbConstants.enq_val)));
                            operation.CLOSE();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ManualEnquiryResponse> call, Throwable t) {
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
