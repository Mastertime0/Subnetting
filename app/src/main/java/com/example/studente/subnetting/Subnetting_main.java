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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnetting_main);

        EditText ip1= findViewById( R.id.editText);
        EditText ip2=findViewById(R.id.editText2);
        EditText ip3=findViewById(R.id.editText3);
        EditText ip4=findViewById(R.id.editText4);

        System.out.println(ip1.getText().toString());

        this.InitializeTextchangeFirst(ip1);
        this.InitializeTextchange(ip2);
        this.InitializeTextchange(ip3);
        this.InitializeTextchange(ip4);

        //if (savedInstanceState != null){
        //    this.initializeSpinner(ip1);
        //    int x=savedInstanceState.getInt("n");
        //    Spinner s=findViewById(R.id.spinner);
        //    s.setSelection(x);
        //}


        Button b=findViewById(R.id.buttonSubnet);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ip1= findViewById( R.id.editText);
                EditText ip2=findViewById(R.id.editText2);
                EditText ip3=findViewById(R.id.editText3);
                EditText ip4=findViewById(R.id.editText4);
                Spinner spinner=findViewById(R.id.spinner);

                Intent i=new Intent(Subnetting_main.this, Second.class);

                IpAddress indirizzo=new IpAddress(ip1.getText().toString(), ip2.getText().toString(), ip3.getText().toString(), ip4.getText().toString(), spinner.getSelectedItem().toString());

                i.putExtra("address", indirizzo.toStringArray());
                startActivity(i);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        Spinner spinner=findViewById(R.id.spinner);

        outState.putInt("n", spinner.getSelectedItemPosition());

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

                if (text != null && text.length() > 2)
                {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();
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

    private void initializeSpinner(EditText ip)
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Subnetting_main.this, android.R.layout.simple_spinner_item);

        adapter.addAll(createPossibleMask(IpAddress.getNetClass(Integer.parseInt(ip.getText().toString()))));

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

                if (text != null && text.length() > 2)
                {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();

                    initializeSpinner(ip);

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
