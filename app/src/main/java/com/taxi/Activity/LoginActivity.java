package com.taxi.Activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.taxi.R;
import com.taxi.utils.FloatingHintEditText;

public class LoginActivity extends AbstractTaxiActivity {

    private FloatingHintEditText edEmail;
    private FloatingHintEditText edPwd;
    private TextView tvValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edEmail.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*All fields are required to login");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        edPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edPwd.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*All fields are required to login");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
    }

    private void init(){
        edEmail = (FloatingHintEditText)findViewById(R.id.email_et);
        edPwd = (FloatingHintEditText)findViewById(R.id.pwd_et);
        tvValidation = (TextView)findViewById(R.id.validation_tv);
    }
}

