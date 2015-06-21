package com.taxi.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class LoginActivity extends AbstractTaxiActivity implements View.OnFocusChangeListener {

    public final String TAG_RESPONSEINFO="responseinfo";
    public final String TAG_PHONE_INFO="phonenumber";
    private FloatingHintEditText edEmail;
    private FloatingHintEditText edPwd;
    private TextView tvValidation;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        edEmail.setOnFocusChangeListener(this);
        edPwd.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.email_et) {
            if(!hasFocus){
                if(edEmail.getText().toString().trim().matches("")){
                    tvValidation.setVisibility(View.VISIBLE);
                    tvValidation.setText("*All fields are required to login");
                }else{
                    tvValidation.setVisibility(View.GONE);
                }

            }
        } else if (v.getId() == R.id.pwd_et) {
            if(!hasFocus){
                if(edPwd.getText().toString().trim().matches("")){
                    tvValidation.setVisibility(View.VISIBLE);
                    tvValidation.setText("*All fields are required to login");
                }else{
                    tvValidation.setVisibility(View.GONE);
                }

            }
        }

    }

    private void init(){
        edEmail = (FloatingHintEditText)findViewById(R.id.email_et);
        edPwd = (FloatingHintEditText)findViewById(R.id.pwd_et);
        tvValidation = (TextView)findViewById(R.id.validation_tv);

    }

    private void serviceToLogin(String email, String pwd) {
        String url = Webservices.BASE_URL+Webservices.LOGIN_URL+"?email="+email+"&password="+pwd;


            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Requesting...");
            pd.setMessage("Login to InstantTaxi..Wait");
            pd.setCancelable(false);
            pd.show();



        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        pd.dismiss();
                        loginUser(jsonObj);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                pd.dismiss();
                CustomLog.v("", "Volley Error: " + arg0);
                showMessage("Volley error: "+ arg0);
                AppConfig.getInstance().cancelPendingRequests("taxi_login");
            }

        }) ;

        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "taxi_login");
    }

    private void loginUser(JSONObject jsonObject) {
        CustomLog.v("TAXI_LOGIN", "login" + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);
            String resPhnum = jsonObject.getString(TAG_PHONE_INFO);
            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("success")) {
                Preferences.setUserPhNum(getApplicationContext(), resPhnum);
                startScreen(HomeScreenActivity.class);
                finish();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void login(View v) {
        String emailStr = edEmail.getText().toString().trim();
        String pwdStr = edPwd.getText().toString().trim();
        if (!AppUtils.chkStatus(LoginActivity.this)) {
            showMessage("check your connection");
            return;
        }
        if(emailStr.isEmpty() || !AppUtils.isValidEmail(emailStr)) {
            showMessage("please enter valid email");
            return;
        }
        if (pwdStr.isEmpty()) {
            showMessage("please enter your password");
            return;
        }
        serviceToLogin(emailStr, pwdStr);
    }

    public void forgotPwd(View v) {
        showMessage("Coming soon");
    }

}

