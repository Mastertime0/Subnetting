package com.example.studente.subnetting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle b = getIntent().getExtras();
        IpAddress ip=new IpAddress(b.getStringArray("address"));

        TextView tv1=findViewById(R.id.textView7);
        TextView tv2=findViewById(R.id.textView9);
        TextView tv3=findViewById(R.id.textView11);
        TextView tv4=findViewById(R.id.textView13);
        TextView tv5=findViewById(R.id.textView15);

        tv1.setText(ip.toString());
        tv2.setText(ip.getNetAddress().toString());
        tv3.setText(ip.getBroadcastAddress().toString());
        tv4.setText(ip.getFirstHost().toString());
        tv5.setText(ip.getLastHost().toString());

        Button b1=findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Second.this.finish();
                finish();
            }
        });

    }
}
