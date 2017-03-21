package autokatta.com.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class OTP extends AppCompatActivity implements RequestNotifier,View.OnClickListener {

    TextView OtpText, emailOtpText;
    EditText OtpEdit, otpemail;
    String otpstr2, msg;
    String username, contact, email, city, profession, sub_profession, password, dob, gender, industry,
            pincode, call;
    String reg_id;
    Button mSubmit;

    Editor editor;
    SharedPreferences prefs;
    public static final String MyProfilePREFERENCES = "profilePrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        prefs = this.getSharedPreferences(MyProfilePREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();

        OtpEdit = (EditText) findViewById(R.id.edtotp1);
        otpemail = (EditText) findViewById(R.id.otpemail);

        OtpText = (TextView) findViewById(R.id.otp2);
        emailOtpText = (TextView) findViewById(R.id.emailOtpText);
        mSubmit = (Button) findViewById(R.id.submit_otp);
        mSubmit.setOnClickListener(this);


        Intent i = getIntent();
        username = i.getStringExtra("username");
        contact = i.getStringExtra("contact");
        email = i.getStringExtra("email");
        dob = i.getStringExtra("dob");
        gender = i.getStringExtra("gender");

        pincode = i.getStringExtra("pincode");
        city = i.getStringExtra("city");

        profession = i.getStringExtra("profession");
        sub_profession = i.getStringExtra("sub_profession");
        industry = i.getStringExtra("industry");
        password = i.getStringExtra("password");
        call = i.getStringExtra("call");

        System.out.println("text contact=" + contact + call + email);

        ApiCall mApiCall = new ApiCall(OTP.this, this);
        mApiCall.getOTP(contact);

    }

        @Override
        public void notifySuccess (Response < ? > response){

        }

        @Override
        public void notifyError (Throwable error){
            if (error instanceof SocketTimeoutException) {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            } else if (error instanceof NullPointerException) {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            } else if (error instanceof ClassCastException) {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            } else {
                Log.i("Check Class-", "Forget Password Activity");
            }
        }

        @Override
        public void notifyString (String str){
            if (str != null) {
                    Log.i("String", "->" + str);
                    {
                        OtpText.setText(str);
                        otpstr2 = "" + str;
                    }

            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }



        }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.submit_otp:
                String otpstr1 = OtpEdit.getText().toString();
                otpstr2 = OtpText.getText().toString().trim();
                String otpemailvalue = otpemail.getText().toString();
                String otpemailpass = emailOtpText.getText().toString();
                if (otpstr1.equals(otpstr2)) {
                    if (call.equalsIgnoreCase("forgot")) {

                        Intent back = new Intent(OTP.this, NewPassword.class);
                        back.putExtra("contact", contact);
                        startActivity(back);
                    } else if (call.equalsIgnoreCase("register")) {
                        if (otpemailvalue.equalsIgnoreCase(otpemailpass)) {
                            ApiCall mApiCall = new ApiCall(OTP.this, this);
                            mApiCall.registrationAfterOtp(contact,username,email,dob,gender,pincode,city,profession,password,sub_profession,industry);
                            //  Registration();
                            /// new Registrationtask().execute();
                        } else {
                            otpemail.setError("Please enter valid OTP");
                        }

                    }
                } else {
                    OtpEdit.setError("Please enter valid OTP");

                }

                break;

        }
    }

        @Override
        public void onBackPressed ()
        {
            Intent i = new Intent(OTP.this, RegistrationActivity.class);
            i.putExtra("username", username);
            i.putExtra("contact", contact);
            i.putExtra("email", email);
            i.putExtra("pincode", pincode);
            i.putExtra("city", city);
            i.putExtra("password", password);
            i.putExtra("dob", dob);
            i.putExtra("gender", gender);

            startActivity(i);
            OTP.this.finish();
        }
    }

        /*    OtpEdit.requestFocus();

            if (call.equalsIgnoreCase("register"))
            {
                otpemail.setVisibility(View.VISIBLE);
                emailOtpText.setVisibility(View.VISIBLE);
                otpemail.clearFocus();

                OTPeMail();
                //  new OTPEmailTask().execute();
            }
*/

/*
        }

    //////////////////                 volley         /////////////////////////////////////
    private  void Registration()

    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://autokatta.com/mobile/registration1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {

                        if (sub_profession.equalsIgnoreCase("Select Category"))
                            sub_profession="";
                        if (industry.equalsIgnoreCase("Select Industry"))
                            industry="";


                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "");
                            }
                            is.close();
                            result1 = sb.toString();
                        } catch (Exception e) {
                            Log.e("log_tag", "Error converting result " + e.toString());
                        }
                        try {

                            String response = sResponse.trim();
                            System.out.println(sResponse);

                            reg_id = sResponse
                                    .replaceAll("[^0-9]", "");
                            System.out.println("Registration id is :" + reg_id
                            );
                            editor.putString("reg_id", reg_id);
                            editor.commit();

                            if (response.startsWith("Success"))
                            {
                                Toast.makeText(getApplicationContext(), "Registration Sucessfull",
                                        Toast.LENGTH_LONG).show();

                                Intent i = new Intent(OTPClass.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Please try later",
                                        Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onError:Registration:" + error.toString());


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("username", username);
                params.put("contact", contact);
                params.put("email", email);
                params.put("dob", dob);
                params.put("gender", gender);
                params.put("pincode", pincode);
                params.put("city", city);
                params.put("profession", profession);
                params.put("password", password);
                params.put("sub_profession", sub_profession);
                params.put("industry", industry);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    /////////////////       volley for OTPmail   /////////////////////////////////

    private  void OTPeMail()
    {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://autokatta.com/mobile/otp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {

                        try {
                            String response=sResponse.trim();
                            System.out.println(sResponse);
                            if(isNumeric(response))
                            {
                                emailOtpText.setText(response);
                                msg="OTP for email validation "+response;
                                System.out.println("rutu================"+sResponse);

                                SendMail sm = new SendMail(OTPClass.this, "amitkamble@autokatta.com", "Don not reply to this mail ",msg);
                                sm.execute();
                            }
                            else
                            {
                                Toast.makeText(OTPClass.this, "Resend OTP",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(OTPClass.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onError:OTPeMail:" + error.toString());


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("number",contact);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
*/
