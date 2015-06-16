package com.taxi.Activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taxi.R;
import com.taxi.utils.FloatingHintEditText;

public class RegistrationActivity extends AbstractTaxiActivity {

    private FloatingHintEditText edEmail;
    private FloatingHintEditText edSetPwd;
    private FloatingHintEditText edRenterPwd;
    private FloatingHintEditText edFullName;
    private FloatingHintEditText edMobNum;
    private FloatingHintEditText edRefCode;
    private TextView tvValidation;
    private ImageView imgReferralCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edEmail.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*invalid email address");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        edSetPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edSetPwd.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Password fields shouldn't be blank");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        edRenterPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edRenterPwd.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Password fields shouldn't be blank");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        edFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edFullName.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Invalid name");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        edMobNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edMobNum.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Invalid mobile number");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        edRefCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edRefCode.getText().toString().trim().matches("")){
                        tvValidation.setVisibility(View.VISIBLE);
                        tvValidation.setText("*Invalid referral code");
                    }else{
                        tvValidation.setVisibility(View.GONE);
                    }

                }

            }
        });
        imgReferralCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(RegistrationActivity.this, "Yet to implement", Toast.LENGTH_LONG).show();
            }
        });
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
    }
}

