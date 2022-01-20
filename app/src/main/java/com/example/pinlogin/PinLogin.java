package com.example.pinlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PinLogin extends AppCompatActivity {
    EditText input1, input2, input3, input4;
    TextView tv_text1, tv_text2, tv_text3, btn_forget;
    Button btn_submit;
    private static final String PREF_INPUT_KEY = "pref_input_key";
    private static final String PREF_SAVEPIN = "savePin";
    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        tv_text1 = findViewById(R.id.tv_text1);
        tv_text2 = findViewById(R.id.tv_text2);
        tv_text3 = findViewById(R.id.tv_text3);
        btn_forget = findViewById(R.id.btn_forget);
        btn_submit = findViewById(R.id.btn_submit_pin);

        //submit 4 digit pin by user
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip1 = input1.getText().toString();
                String ip2 = input2.getText().toString();
                String ip3 = input3.getText().toString();
                String ip4 = input4.getText().toString();

                SharedPreferences sp = getSharedPreferences(PREF_SAVEPIN, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(PREF_INPUT_KEY, ip1 + ip2 + ip3 + ip4);
                editor.apply();

                Log.i(TAG, "-----------ip1 :" + ip1);
                Log.i(TAG, "-----------ip2 :" + ip2);
                Log.i(TAG, "-----------ip3 :" + ip3);
                Log.i(TAG, "-----------ip4 :" + ip4);

                //validate or Check if all pin is filled
                if (!ip1.isEmpty() && !ip2.isEmpty() && !ip3.isEmpty() && !ip4.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PinLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PinLogin.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //forget button
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences(PREF_SAVEPIN, MODE_PRIVATE);
                sp.edit().clear().apply();
                Log.i(TAG, "-------forget pin: " + PREF_SAVEPIN);
                Toast.makeText(getApplicationContext(), "Enter new pin", Toast.LENGTH_SHORT).show();
                btn_submit.setVisibility(View.VISIBLE);
            }
        });

        //Remove Button Visibilty in second time
        SharedPreferences sp = getSharedPreferences(PREF_SAVEPIN, MODE_PRIVATE);
        String rButton = sp.getString(PREF_INPUT_KEY, "");
        if (!rButton.isEmpty() && rButton.length() > 0) {
            tv_text1.setVisibility(View.GONE);
            tv_text3.setVisibility(View.GONE);
            tv_text2.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);
        } else {
            btn_submit.setVisibility(View.VISIBLE);
        }

        autoNext();
    }

    private void autoNext() {
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String in = input1.getText().toString() + input2.getText().toString() + input3.getText().toString() + input4.getText().toString();
                Log.i(TAG, "----in: " + in);

                //get data from shared prefrence
                SharedPreferences sp = getSharedPreferences(PREF_SAVEPIN, MODE_PRIVATE);
                String inputAllData = sp.getString(PREF_INPUT_KEY, "");
                Log.i(TAG, "--------inputAllData: " + inputAllData);

                //matching user data and existing data
                if (!in.equals(null)) {
                    if (inputAllData.equals(in)) {
                        Toast.makeText(PinLogin.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PinLogin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (inputAllData.length() > 3) {
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}