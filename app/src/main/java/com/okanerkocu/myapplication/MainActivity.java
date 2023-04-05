package com.okanerkocu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    public ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.list);
        adapter=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);


        Button btn= findViewById(R.id.kia);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VerGet().execute();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class VerGet extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Mercedes Araç Fiyatları");
            progressDialog.setMessage("Fiyatlar yükleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String URL = "https://www.kia.com/tr/satis-merkezi/fiyat-listesi.html";
                Document doc= Jsoup.connect(URL).timeout(30*1000).get();

                Elements elements=doc.select("a");

                for (int i=0;i<elements.size();i++){

                    arrayList.add(elements.get(i).text());

                }

            } catch (IOException e) {
                System.out.println("Sayfaya ulaşılamadı.");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            lv.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}