package com.example.studente.subnetting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Locale;

public class Subnetting_main extends AppCompatActivity {

    EditText ip1=null;
    EditText ip2=null;
    EditText ip3=null;
    EditText ip4=null;
    Spinner spinner=null;
    Button b=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnetting_main);

        ip1= findViewById( R.id.editText);
        ip2=findViewById(R.id.editText2);
        ip3=findViewById(R.id.editText3);
        ip4=findViewById(R.id.editText4);

        spinner = (Spinner) findViewById(R.id.spinner);

        this.InitializeTextchangeFirst(ip1);
        this.InitializeTextchange(ip2);
        this.InitializeTextchange(ip3);
        this.InitializeTextchange(ip4);




        b=findViewById(R.id.buttonSubnet);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(!ip1.getText().toString().equals("") && !ip2.getText().toString().equals("") && !ip3.getText().toString().equals("") && !ip4.getText().toString().equals("")) {
                Intent i = new Intent(Subnetting_main.this, Second.class);

                IpAddress indirizzo = new IpAddress(ip1.getText().toString(), ip2.getText().toString(), ip3.getText().toString(), ip4.getText().toString(), spinner.getSelectedItem().toString());

                i.putExtra("address", indirizzo.toStringArray());
                startActivity(i);
            }
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

            int x = savedInstanceState.getInt("n");
            String saveip = savedInstanceState.getString("ip");

            if(saveip!=null) {
                initializeSpinner(saveip);
                spinner.setSelection(x);
            }


        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if(spinner!=null && !ip1.getText().toString().equals("")) {
            outState.putInt("n", spinner.getSelectedItemPosition());
            outState.putString("ip", ip1.getText().toString());
        }

        super.onSaveInstanceState(outState);
    }

    private void InitializeTextchange(EditText ip)
    {
        ip.addTextChangedListener(new TextWatcher() {

            // onTextChanged
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                TextView text = (TextView)getCurrentFocus();
                if(text!=null && text.length() > 1) {
                    if (Integer.parseInt(text.getText().toString()) > 255) {
                        text.setText("255");
                    }

                    if (text.length() > 2) {
                        View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                        if (next != null)
                            next.requestFocus();
                    }
                }


            }

            // afterTextChanged
            @Override
            public void afterTextChanged(Editable s) {}

            // beforeTextChanged
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });
    }

    private void initializeSpinner(String ip)
    {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Subnetting_main.this, android.R.layout.simple_spinner_item);

        adapter.addAll(createPossibleMask(IpAddress.getNetClass(Integer.parseInt(ip))));

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void InitializeTextchangeFirst(final EditText ip)
    {
        ip.addTextChangedListener(new TextWatcher() {

            // onTextChanged
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                TextView text = (TextView)getCurrentFocus();
                if(ip!=null && !ip.getText().toString().equals("") && ip.length()>0) {

                    if (Integer.parseInt(ip.getText().toString()) > 223) {
                        ip.setText("223");
                    }

                    if (ip.length() > 2) {
                        View next = ip.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                        if (next!=null)
                            next.requestFocus();
                    }

                    initializeSpinner(ip.getText().toString());
                }
            }

            // afterTextChanged
            @Override
            public void afterTextChanged(Editable s) {}

            // beforeTextChanged
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
        });
    }

    private String[] createPossibleMask(char x)
    {
        String[] vet=null;

        switch(x)
        {
            case 'A':
                vet=new String[23];
                break;
            case 'B':
                vet=new String[15];
                break;
            case 'C':
                vet=new String[7];
                break;
            default:
                vet=new String[0];
                break;
        }

        for(int i=0;i<vet.length;i++)
        {
            vet[i]=""+(i+(31-vet.length));
        }
        return vet;
    }

    @SuppressWarnings("deprecation")
    private void setLocale(Locale locale) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplicationContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration, displayMetrics);
        }

    }
}
