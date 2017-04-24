package autokatta.com.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.ModelFirebase;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;


/**
 * Created by SERVER on 16-Sep-16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService implements RequestNotifier {
    public static String token;
    String textContact;
    //ConnectionDetector mConnectionDetector = new ConnectionDetector(getApplicationContext());

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        token = refreshedToken;
        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("fcm_key", token).apply();
        Log.i("Refreshed token:", "->" + refreshedToken);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(final String token) {
        try {
            textContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
            //if (textContact != null) {
            ApiCall mApiCall = new ApiCall(getApplicationContext(), this);
            ModelFirebase firebase = new ModelFirebase();
            firebase.setContactNumber("7841023392");
            firebase.setTokenKey(token);
            mApiCall.firebaseToken("7841023392", token);

                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> callFirebase = serviceApi.firebaseToken(textContact, token);
                callFireba  se.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("response","firebase"+response.body());
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putBoolean("isRegistered", true).apply();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("MYTokenError:", "->" + t.getMessage());
                        t.printStackTrace();
                    }
                });*/
            /*} else {
                Toast.makeText(getApplicationContext(), "Contact is null", Toast.LENGTH_SHORT).show();
            }*/
            /*if (textContact != null) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://autokatta.com/mobile/deviceRegistration.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("MyTokenResponse:", "->" + response);
                                Toast.makeText(getApplicationContext(), "Firebase register successfully done", Toast.LENGTH_SHORT).show();
                                getSharedPreferences(getString(R.string.pref), MODE_PRIVATE).edit().putBoolean("isRegistered", true).apply();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MYTokenError:", "->" + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("contact", textContact);
                        params.put("token", token);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(getApplicationContext(), "Contact is null", Toast.LENGTH_SHORT).show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
