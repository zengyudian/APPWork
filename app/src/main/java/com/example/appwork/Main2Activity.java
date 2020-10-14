package com.example.appwork;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    EditText dollarrate, eurorate, wonrate;
    EditText tv1;
    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        new Thread(new myrun1()).start();
        tv1=findViewById(R.id.tv1);
        //tv1.setText("123");

        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str=(String)msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg="+str);
                    tv1.setText(""+str);
                }
                super.handleMessage(msg);
            }

        };

        new Thread(){
            public void run() {

                //获取网络数据
                URL url = null;
                List<String> rate_list = null;
                try {
                    url = new URL("http://www.usd-cny.com/bankofchina.htm");

                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    InputStream in = http.getInputStream();
                    String html = inputStream2String(in);
                    Log.i(TAG, "run: html=" + html);

                    String url1 = "http://www.usd-cny.com/bankofchina.htm";
                    Document doc = Jsoup.connect(url1).get();
                    Log.i(TAG, "run: " + doc.title());
                    //Log.i(TAG, "run: " + doc);
                    Elements tables = doc.getElementsByTag("table");
                    //Log.i(TAG, "run: " + tables);
                    Element table = tables.get(0);
                    // 获取 TD 中的数据
                    Elements tds = table.getElementsByTag("td");
                    //Log.i(TAG, "run: " + tds);

                    rate_list = new ArrayList<String>();
                    for (int i = 0; i < tds.size(); i += 6) {
                        Element td1 = tds.get(i);
                        Element td2 = tds.get(i + 5);
                        String str1 = td1.text();
                        String val = td2.text();
                        //Log.i(TAG, "run: " + str1 + "==>" + val);
                        rate_list.add(str1 + "==>" + val);
                        // 获取数据并返回 ……
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Message msg=handler.obtainMessage(5);
                Message msg = new Message();
                msg.what = 5;
                msg.obj = "hello";
                handler.sendMessage(msg);

            }

            private String inputStream2String(InputStream inputStream)
                    throws IOException {
                final int bufferSize=1024;
                final char[] buffer = new char[bufferSize];
                final StringBuilder out = new StringBuilder();
                Reader in = new InputStreamReader(inputStream, "gb2312");
                while (true) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
                return out.toString();
            }

        }.start();




        //设置初始汇率
        dollarrate=findViewById(R.id.dollartext);
        eurorate=findViewById(R.id.eurotext);
        wonrate=findViewById(R.id.wontext);

        Intent intent=getIntent();
        Float dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);
        Float euro2=intent.getFloatExtra("euro_rate_key",0.0f);
        Float won2=intent.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG,"onCreate:dollar2="+dollar2);
        Log.i(TAG,"onCreate:euro2="+euro2);
        Log.i(TAG,"onCreate:won2="+won2);

        dollarrate.setText(""+dollar2);
        eurorate.setText(""+euro2);
        wonrate.setText(""+won2);

    }

    public void save(View btn){

        Intent intent=getIntent();
        String strDollar=dollarrate.getText().toString();
        String strEuro=eurorate.getText().toString();
        String strWon=wonrate.getText().toString();
        Float newDollar=Float.parseFloat(strDollar);
        Float newEuro=Float.parseFloat(strEuro);
        Float newWon=Float.parseFloat(strWon);

        Bundle bdl=new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();



    }


    public void search(View btn){
        Intent main3=new Intent(Main2Activity.this,RateListActivity.class);

        Log.i(TAG,"search12345678");
        startActivity(main3);
    }

}

class myrun1 implements Runnable{

    private final String TAG=getClass().getSimpleName();

    public void run(){

        Log.i(TAG,"run:123456789");


    }
}


