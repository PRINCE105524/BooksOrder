package com.priem.booksorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class showdb extends AppCompatActivity {
    DatabaseHelper db;
    TextView shopname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdb);

        shopname = (TextView) findViewById(R.id.shopname);

       // String b = getIntent().getExtras().getString("matha");
       // Toast.makeText(showdb.this, b, Toast.LENGTH_SHORT).show();

        db = new DatabaseHelper(this);

        printDatabase();
    }


    void printDatabase() {
        String b = getIntent().getExtras().getString("shopname");
        shopname.setText(b);
         String query = "SELECT * FROM james WHERE bookshop='" + b + "'";

        //String query1 = "SELECT * FROM james";

        String dbstring = db.databasetostring(query); //convert the content of database to string
        //dbstring += "NEW ORDER :"+"\n";
        String[] strArray = new String[]{dbstring};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArray);

        ListView ls = (ListView) findViewById(R.id.showdb);
        ls.setAdapter(adapter);

    }

}
