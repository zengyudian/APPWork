package com.example.appwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.textView2);
    }

    public void one(View v){
        Log.i("main","onClick msg");
        String str=tv.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+1;
        String j=""+i;
        tv.setText(j);
    }

    public void two(View v){
        Log.i("main","onClick msg");
        String str=tv.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+2;
        String j=""+i;
        tv.setText(j);
    }

    public void three(View v){
        Log.i("main","onClick msg");
        String str=tv.getText().toString();
        Integer i=Integer.parseInt(str);
        i=i+3;
        String j=""+i;
        tv.setText(j);
    }

    public void reset(View v){
        Log.i("main","onClick msg");
        tv.setText("0");
    }

}