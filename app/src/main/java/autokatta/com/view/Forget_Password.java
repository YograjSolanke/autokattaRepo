package autokatta.com.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class Forget_Password extends AppCompatActivity implements  View.OnClickListener,RequestNotifier {

    EditText edtContact;
    String contact;
    Button submit, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        final Random myRandom = new Random();

        edtContact = (EditText) findViewById(R.id.edit_contact);
        clear = (Button) findViewById(R.id.clear);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        clear.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                forgetPassword();
                break;
            case R.id.clear:

                edtContact.setText("");
        }
    }

    private void  forgetPassword()
    {
       contact=edtContact.getText().toString().trim();
        if (TextUtils.isEmpty(edtContact.getText().toString().trim())) {
            edtContact.requestFocus();
            edtContact.setFocusable(true);
            edtContact.setError(getString(R.string.err_mobile));
        }else {
            ApiCall mApiCall = new ApiCall(Forget_Password.this, this);
            mApiCall.forgetPassword(contact);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                Toast.makeText(getApplicationContext(), "S"+response.body(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void notifyError(Throwable error) {
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
    public void notifyString(String str) {
        if (str !=null){
            if(str.equals("Success")){
                Log.i("String","->"+str);
                CustomToast.customToast(getApplicationContext(),"You are a valid user");
                Intent i=new Intent(Forget_Password.this,OTP.class);
                System.out.println("contact================================================"+contact);
                i.putExtra("contact",contact);
                i.putExtra("call","forgot");
                startActivity(i);
            }
            else
            {
                CustomToast.customToast(getApplicationContext(),"You are not a valid user");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                // alertDialog.setTitle("Upload Vehicle");
                alertDialog.setMessage("You are not registered user. Do you want to register now?");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Forget_Password.this, RegistrationActivity.class);
                        startActivity(i);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Forget_Password.this, LoginActivity.class);
                        startActivity(i);
                        dialog.cancel();

                    }
                });
                alertDialog.show();

            }

        }else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }



   /* ////////////////     volley      //////////////////////////////////
    private void checkUsercontact()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://autokatta.com/mobile/getContactForgotPass.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {

                            if (result.equalsIgnoreCase("Success"))
                            {
                                Intent i=new Intent(ForgetPassword.this,OTPClass.class);
                                System.out.println("contact="+contact);
                                i.putExtra("contact",contact);
                                i.putExtra("call","forgot");
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(ForgetPassword.this, "You are not a valid user", Toast.LENGTH_SHORT).show();

                                new AlertDialog.Builder(ForgetPassword.this)
                                        //.setTitle("confirm")
                                        .setMessage("You are not registered user. Do you want to register now?")

                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent i = new Intent(ForgetPassword.this, RegistrationActivity.class);
                                                startActivity(i);

                                            }
                                        })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();

                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            // tv.setText("error2");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onError:forgetpassword:" + error.toString());


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("contact",contact);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }*/



}
