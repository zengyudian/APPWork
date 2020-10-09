package com.example.appwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=findViewById(R.id.textView3);
        tv2=findViewById(R.id.textView4);

    }

    public void one1(View v){
        Log.i("main","onClick msg");
        String str=tv1.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+1;
        String j=""+i;
        tv1.setText(j);
    }

    public void two1(View v){
        Log.i("main","onClick msg");
        String str=tv1.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+2;
        String j=""+i;
        tv1.setText(j);
    }

    public void three1(View v){
        Log.i("main","onClick msg");
        String str=tv1.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+3;
        String j=""+i;
        tv1.setText(j);
    }

    public void one2(View v){
        Log.i("main","onClick msg");
        String str=tv2.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+1;
        String j=""+i;
        tv2.setText(j);
    }

    public void two2(View v){
        Log.i("main","onClick msg");
        String str=tv2.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+2;
        String j=""+i;
        tv2.setText(j);
    }

    public void three2(View v){
        Log.i("main","onClick msg");
        String str=tv2.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+3;
        String j=""+i;
        tv2.setText(j);
    }

    public void reset(View v){
        Log.i("main","onClick msg");
        tv1.setText("0");
        tv2.setText("0");
    }
}