package com.example.task4recyclerview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    public Elements title;
    public ArrayList<Pair<String, String>> imgList = new ArrayList<Pair<String, String>>();
    private RecyclerAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.recyclerView1);
        rv.setLayoutManager(new LinearLayoutManager(this));
        new NewThread().execute();
        adapter = new RecyclerAdapter(this, imgList);
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] arg) {

            Document doc;
            try {
                doc = Jsoup.connect("https://ru.freeimages.com/")
                        .get();
//                title = doc.select("li");
//                imgList.clear();
                Elements images = doc.select("img");
                System.out.println(images.size());


                for (Element img : images) {
                    String imgSrc = img.absUrl("src");
                    String name = img.attr("alt");
                    System.out.println(name);
                    if(!imgSrc.equals("")&&!name.equals("")&&!name.equals("Русский")&&!name.equals("English")) imgList.add(new Pair<String, String>(imgSrc,name));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            rv.setAdapter(adapter);
        }
    }
}
