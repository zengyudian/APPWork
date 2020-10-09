package com.example.appwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    TextView tv;
    EditText text;
    float dollarRate;
    float euroRate;
    float wonRate;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.textView);
        text=findViewById(R.id.editTextTextPersonName);


        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);

        dollarRate=sharedPreferences.getFloat("dollar_rate",(float) 0.1465);
        euroRate=sharedPreferences.getFloat("euro_rate",(float) 0.1259);
        wonRate=sharedPreferences.getFloat("won_rate",(float) 0.1259);

        /*dollarRate= (float) 0.1464;
        euroRate= (float) 0.1255;
        wonRate= (float) 171.3245;*/


    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1&&resultCode==2){
            Bundle bundle=data.getExtras();

            dollarRate=bundle.getFloat("key_dollar",0.1f);
            euroRate=bundle.getFloat("key_euro",0.1f);
            wonRate=bundle.getFloat("key_won",0.1f);
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate);
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);

            SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);

            editor.apply();

        }

        super.onActivityResult(requestCode,resultCode,data);
    }


    public void dollar(View btn){


        String str=text.getText().toString();
        Float i=Float.parseFloat(str);
        Toast.makeText(this,"Hello msg",Toast.LENGTH_SHORT).show();
        Log.i(TAG,"abc:onClicked");

        if(btn.getId()==R.id.dollar){//dollar

            i=dollarRate*i;
            String j=""+i;
            tv.setText(j);

        }else if(btn.getId()==R.id.euro){//euro

            i=euroRate*i;
            String j=""+i;
            tv.setText(j);

        }else{//won

            i=wonRate*i;
            String j=""+i;
            tv.setText(j);

        }
    }

    public void open(View btn){
        Intent main2=new Intent(MainActivity.this,Main2Activity.class);

        main2.putExtra("dollar_rate_key",dollarRate);
        main2.putExtra("euro_rate_key",euroRate);
        main2.putExtra("won_rate_key",wonRate);

        Log.i(TAG,"open:dollarRate="+dollarRate);
        Log.i(TAG,"open:euroRate="+euroRate);
        Log.i(TAG,"open:wonRate="+wonRate);

        startActivityForResult(main2,1);
        //startActivity(main2);
    }



}