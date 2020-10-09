package com.example.appwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out=findViewById(R.id.tv1);
        //out.setText("123");


        editText =(EditText)findViewById(R.id.editTextTextPersonName);
        //String str=editText.getText().toString();
        Button btn=(Button)findViewById(R.id.btn);
        //add something...


    }

    public void abc(View v){

        Log.i("main","onClick msg");
        //EditText editText =(EditText)findViewById(R.id.editTextTextPersonName);
        String str=editText.getText().toString();
        float i=Float.parseFloat(str);
        float j=i*9/5+32;
        String s=""+j;
        out.setText(s);

    }
}