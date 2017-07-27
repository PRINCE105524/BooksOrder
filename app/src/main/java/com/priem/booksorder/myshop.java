package com.priem.booksorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class myshop extends AppCompatActivity {

    private static final String TAG = myshop.class.getSimpleName();
    String address = "http://10.0.2.2/booksorder/shopname.php";
    InputStream is = null;
    String line = null;
    String result = null;
    String[] data;
    String shopname;

    ListView ls;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshop);

        progressDialog = new ProgressDialog(this);

        // String[] data1 = {"Bhai Bhai book store", "Binapani Books", "BooksFair", "BooksWorld", "Gyankosh", "Book Worm", "Books Heaven", "Boipotro", "Books Universe", " Students Place"};

        ls = (ListView) findViewById(R.id.list);

        //allow network in main thread
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        progressDialog.setMessage("retrieving shop name from server...");
        progressDialog.show();

        getData();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        progressDialog.dismiss();


        ls.setAdapter(adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(myshop.this, CatalogActivity.class);
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Toast.makeText(myshop.this,selectedItem,Toast.LENGTH_SHORT).show();
                intent.putExtra("shp", selectedItem);
                startActivity(intent);

            }

        });
    }


    private void getData() {
        try {
            URL url = new URL(address);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            is = new BufferedInputStream(con.getInputStream());


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        //READ is content into a string
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");

            }

            is.close();

            result = sb.toString();


        } catch (Exception e) {

            e.printStackTrace();

        }

        //parse json data

        try {

            JSONArray js = new JSONArray(result);
            JSONObject jo;

            data = new String[js.length()];

            for (int i = 0; i < js.length(); i++) {
                jo = js.getJSONObject(i);
                data[i] = jo.getString("myshop");
                //Log.d(TAG,data[i]);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}

