package com.taxi.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taxi.R;
import com.taxi.application.AppConfig;
import com.taxi.utils.AppUtils;
import com.taxi.utils.CustomLog;
import com.taxi.utils.FloatingHintEditText;
import com.taxi.utils.Preferences;
import com.taxi.utils.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AbstractTaxiActivity implements View.OnFocusChangeListener, View.OnClickListener {

    public final String TAG_RESPONSEINFO="responseinfo";
    public final String TAG_PHONENUMBER="phonenumber";
    public final String TAG_EMAIL="email";
    public final String TAG_USERID="userid";
    public final String TAG_PASSWORD="password";

    private FloatingHintEditText edEmail;
    private FloatingHintEditText edSetPwd;
    private FloatingHintEditText edRenterPwd;
    private FloatingHintEditText edFullName;
    private FloatingHintEditText edMobNum;
    private FloatingHintEditText edRefCode;
    private TextView tvValidation;
    private ImageView imgReferralCode;

    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        edEmail.setOnFocusChangeListener(this);
        edSetPwd.setOnFocusChangeListener(this);
        edRenterPwd.setOnFocusChangeListener(this);
        edFullName.setOnFocusChangeListener(this);
        edMobNum.setOnFocusChangeListener(this);
        edRefCode.setOnFocusChangeListener(this);
        imgReferralCode.setOnClickListener(this);
    }

    private void init(){
        edEmail = (FloatingHintEditText)findViewById(R.id.email_et);
        edSetPwd = (FloatingHintEditText)findViewById(R.id.set_pwd_et);
        edRenterPwd = (FloatingHintEditText)findViewById(R.id.re_enter_pwd_et);
        edFullName = (FloatingHintEditText)findViewById(R.id.full_name_et);
        edMobNum = (FloatingHintEditText)findViewById(R.id.enter_mob_no_et);
        edRefCode = (FloatingHintEditText)findViewById(R.id.referrel_code_et);
        tvValidation = (TextView)findViewById(R.id.validation_tv);
        imgReferralCode = (ImageView)findViewById(R.id.referral_iv);

        Button tvSignUp = (Button) findViewById(R.id.signup_btn);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.email_et:
                if(!hasFocus){
                    if(edEmail.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*invalid email address");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }
                break;
            case R.id.set_pwd_et:
                if(!hasFocus){
                    if(edSetPwd.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Password fields shouldn't be blank");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }
                break;
            case R.id.re_enter_pwd_et:
                if(!hasFocus){
                    if(edRenterPwd.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Password fields shouldn't be blank");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }
                break;
            case R.id.full_name_et:
                if(!hasFocus){
                    if(edFullName.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Invalid name");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }
                break;
            case R.id.enter_mob_no_et:
                if(!hasFocus){
                    if(edMobNum.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Invalid mobile number");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }
                break;
            case R.id.referrel_code_et:
                if(!hasFocus){
                    if(edRefCode.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Invalid referral code");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }
                    if(!hasFocus){
                        if(edFullName.getText().toString().trim().matches("")){
                            tvValidation.setVisibility(View.VISIBLE);
                            tvValidation.setText("*Invalid name");
                        }else{
                            tvValidation.setVisibility(View.GONE);
                        }

                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.validation_tv) {
            showMessage("Yet to implement..!");
        } else if (v.getId() == R.id.signup_btn) {
            registerUser();
        }
    }

    private void registerUser() {
        String emailStr = edEmail.getText().toString().trim();
        String pwdStr = edSetPwd.getText().toString().trim();
        String reenterPwd = edRenterPwd.getText().toString().trim();
        String phonStr = edMobNum.getText().toString().trim();
        String fullNameStr = edFullName.getText().toString().trim();

        if (!AppUtils.chkStatus(RegistrationActivity.this)) {
            showMessage("check internet connection");
            return;
        }
        if (emailStr.isEmpty() || !AppUtils.isValidEmail(emailStr)) {
            showMessage("Enter valid mail");
            return;
        }
        if (pwdStr.isEmpty()) {
            showMessage("Enter Password");
            return;
        }
        if (reenterPwd.isEmpty()) {
            showMessage("Re enter password");
            return;
        }
        if (!pwdStr.equalsIgnoreCase(reenterPwd)) {
            showMessage("password are not matching");
            return;
        }
        if (fullNameStr.isEmpty()) {
            showMessage("Enter your name");
            return;
        }
        if (phonStr.isEmpty()) {
            showMessage("enter mobile number");
            return;
        }

        if (phonStr.length() != 10) {
            showMessage("mobile number must have 10 digits");
            return;
        }
        serviceToRegisterUser(emailStr, pwdStr, fullNameStr,phonStr);

    }

    private void serviceToRegisterUser(String emailStr, String pwdStr, String fullNameStr,String phonStr) {
        String url = Webservices.BASE_URL + Webservices.REGISTER_URL
                +"?newemail="+emailStr+"&newfullname="+fullNameStr+"&newpassword="+pwdStr
                +"&newphonenumber="+phonStr;

        final ProgressDialog pd = new ProgressDialog(RegistrationActivity.this);
        pd.setTitle("Requesting...");
        pd.setMessage("Login to InstantTaxi..Wait");
        pd.setCancelable(false);
        pd.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        pd.dismiss();
                        registerResponse(jsonObj);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                pd.dismiss();
                CustomLog.v("", "Volley Error: " + arg0);
                showMessage("Volley error: "+ arg0);
                AppConfig.getInstance().cancelPendingRequests("taxi_register");
            }

        }) ;

        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "taxi_register");
    }

    private void registerResponse(JSONObject jsonObject) {
        CustomLog.v("TAXI_REGISTER", "register: " + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);
            String userId = jsonObject.getString(TAG_USERID);
            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("success")) {
                startScreen(LoginActivity.class);
                Preferences.setUserId(getApplicationContext(), userId);
                finish();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}

