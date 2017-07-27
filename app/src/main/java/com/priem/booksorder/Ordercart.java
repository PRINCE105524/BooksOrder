package com.priem.booksorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

public class Ordercart extends Activity {

    private static final String TAG = Ordercart.class.getSimpleName();

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    dbhandler db;
    Button submitorder, B2;
    private TextView t1;
    Product s;
    private DatabaseHelper db1;
//	public   final String  ss =getIntent().getExtras().getString("psd");

    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String URL_SAVE_NAME = "http://10.0.2.2/booksorder/save.php";
    public String b;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "com.priem.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    protected void onCreate(Bundle savedInstanceState) {


        b = getIntent().getExtras().getString("psd");
        Toast.makeText(Ordercart.this, b, Toast.LENGTH_SHORT).show();

        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        db1 = new DatabaseHelper(this);
        B2 = (Button) findViewById(R.id.btn);
        submitorder = (Button) findViewById(R.id.submitorder);
        mCartList = ShoppingCartHelper.getCartList();
//		 s=ShoppingCartHelper.getCartList().get(0);
        t1 = (TextView) findViewById(R.id.views);

        db = new dbhandler(this, null, null, 1);
        // Make sure to clear the selections
        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = true;
        }
        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetailsActivity.class);

                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });
        submitorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mCartList.size(); i++) {
                    //db.addProduct(mCartList.get(i));}


                 // Log.d(TAG,mProductAdapter.getItem(i).toString());
                  //  if (!mCartList.contains(mCartList.get(i).getTitle())) {

                        saveNameToServer(mCartList.get(i).getTitle() + "\n");

                   // }

                }
                Toast.makeText(Ordercart.this, "successfully submitted", Toast.LENGTH_SHORT).show();


                for (int i = 0; i < mCartList.size(); i++) {


                    ShoppingCartHelper.removeProduct(mCartList.get(i));

                }



             // mCartList.removeAll(mCartList);

                //Intent intent = new Intent(Ordercart.this, showdb.class);
                //startActivity(intent);

            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ordercart.this, showdb.class);
                intent.putExtra("shopname", b);

                startActivity(intent);
            }
        });
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

    }

    private void saveNameToServer(String name1) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name...");
        progressDialog.show();
        final String name = name1;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(name, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name, NAME_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //on error storing the name to sqlite with status unsynced
                        saveNameToLocalStorage(name, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void saveNameToLocalStorage(String name, int status) {
        //editTextName.setText("");
        db1.addName(name, status, b);
        Toast.makeText(Ordercart.this, "successfully saved", Toast.LENGTH_SHORT).show();
        //Name n = new Name(name, status);
        //names.add(n);
        //refreshList();
    }


    protected void onResume() {
        super.onResume();

        // Refresh the data
        if (mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }
    }


}
