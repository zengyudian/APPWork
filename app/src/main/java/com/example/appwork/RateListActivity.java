package com.example.appwork;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.RequiresApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RateListActivity extends ListActivity {

    Handler handler;

    int year,month,day;
    LocalDate nowDate ;//当前时间
    LocalDate Date ;//上次更新数据时间
    Period period;//时间间隔



    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_rate_list);

        /*List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("item" + i);
        }
        String[] list_data = {"one", "tow", "three", "four"};

        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list1);
                //_data);
        setListAdapter(adapter);*/

        //读取上一次运行保存的时间
        SharedPreferences sp1 = getSharedPreferences("time", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);

        String year1,month1,day1;
        int p=1;
        year1 = sp1.getString("year", "");
        month1 = sp1.getString("month", "");
        day1 = sp1.getString("day", "");

        if(year1!=""&&month1!=""&&day1!=""){
            //读取保存的年月日
            year = Integer.parseInt(year1);
            month = Integer.parseInt(month1);
            day = Integer.parseInt(day1);

            nowDate = LocalDate.now();//当前时间
            Log.i(TAG, "当前时间: " + nowDate);
            Date = LocalDate.of(year,month,day);//上次时间
            Log.i(TAG, "上次更新时间: " + Date);
            period = Period.between(Date, nowDate);//间隔
            p=period.getDays();
        }


        //SharedPreferences sp = getSharedPreferences("list",Activity.MODE_PRIVATE);
        if (p == 0) {
            Log.i(TAG, "无更新");
            //不更新
            //读取上次保存的数据
            SharedPreferences sp = getSharedPreferences("ratelist", Activity.MODE_PRIVATE);
            PreferenceManager.getDefaultSharedPreferences(this);

            String str1 = "";
            str1 = sp.getString("list", "");
            String[] str1Array = str1.split(",");
            List<String> ratelist = Arrays.asList(str1Array);

            ListAdapter adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, ratelist);
            setListAdapter(adapter);
        }

        else {
            //更新数据
            Log.i(TAG, "数据更新");
            new Thread() {
                public void run() {

                    //获取网络数据
                    List<String> rate_list =new ArrayList<String>();
                    try {
                        String url1 = "http://www.usd-cny.com/bankofchina.htm";
                        Document doc = Jsoup.connect(url1).get();
                        Log.i(TAG, "run: " + doc.title());
                        //Log.i(TAG, "run: " + doc);
                        Elements tables = doc.getElementsByTag("table");
                        //Log.i(TAG, "run: " + tables);
                        Element table = tables.get(0);
                        // 获取 TD 中的数据
                        //Log.i(TAG, "run: " + table);
                        Elements tds = table.getElementsByTag("td");
                        //Log.i(TAG, "run: " + tds);


                        for (int i = 0; i < tds.size(); i += 6) {
                            Element td1 = tds.get(i);
                            Element td2 = tds.get(i + 5);
                            String str1 = td1.text();
                            String val = td2.text();
                            //Log.i(TAG, "run: " + str1 + "==>" + val);
                            rate_list.add(str1 + "==>" + val);

                            // 获取数据并返回 ……
                        }
                        Log.i(TAG, "run: " + rate_list);


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //保存汇率数据
                    SharedPreferences sp=getSharedPreferences("ratelist", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();

                    String str="";
                    for(String s:rate_list){
                        str+=s+",";
                    }

                    editor.putString("list", str);
                    Log.i(TAG,"saveOK");
                    editor.apply();


                    //保存时间数据
                    SharedPreferences sp1 = getSharedPreferences("time", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sp1.edit();
                    String time = LocalDate.now().toString();
                    String y=time.split("-")[0];
                    String m=time.split("-")[1];
                    String d=time.split("-")[2];

                    editor1.putString("year",y);
                    editor1.putString("month",m);
                    editor1.putString("day", d);
                    editor1.apply();

                    //Message msg=handler.obtainMessage(5);
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = rate_list;
                    handler.sendMessage(msg);

                }
            }.start();

        }


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(
                            RateListActivity.this,
                            android.R.layout.simple_list_item_1,
                            list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };


    }

}
