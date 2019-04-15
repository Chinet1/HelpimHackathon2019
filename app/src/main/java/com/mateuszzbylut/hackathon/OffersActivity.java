package com.mateuszzbylut.hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class OffersActivity extends AppCompatActivity {

    public JSONObject offers;
    public List<JSONObject> users;
    public RequestQueue queue;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        queue = Volley.newRequestQueue(this);
        context = this;
        getOffers();
    }

    public void getOffers() {
        String url = "http://mateuszzbylut.com/service.php?getOffers=t";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                    offers = response;
//                try {
//                    Log.d("Helpim", offers.getString(String.valueOf(2)));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//    }
//                    for (int i = 0; i < offers.length(); i++) {
//                        try {
//                        getUser((Integer) offers.getJSONObject(i).get("id_user"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    }

//                ArrayList<String> list = new ArrayList<>();
//
//                for (int i = 0; i < offers.length(); i++)
//                    try {
//                        JSONObject obj = offers.getJSONObject(i);
//                        String name = "";
//                        for (int j = 0; j < users.size(); j++) {
//                            if (users.get(j).get("id_user") == obj.get("id_user")) {
//                                name = users.get(j).get("name") + " " + users.get(j).get("lastname");
//                            }
//                        }
//                        list.add(i + ": " + obj.get("about") + " Dodane przez " + name);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<Integer> expenseIds = new ArrayList<Integer>();

        list.add("Koszenie trawnika");
        expenseIds.add(1);


        ListView listView = (ListView) findViewById(R.id.offersList);

                final ArrayAdapter adapter = new ArrayAdapter(context,
                        android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, final long id) {
                Intent intent = new Intent(context, PickOfferActivity.class);
                intent.putExtra("id", expenseIds.get(position));
                startActivity(intent);
            }
        });

//                Log.d("Helpim", response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Helpim", "Error: getOffers request");
//                Log.d("Helpim", error.toString());
//            }
//        });

//        StringRequest request = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        Log.d("Helpim", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Helpim", "Error: getOffers request");
////                Log.d("Helpim", error.toString());
//            }
//        });
//
//        queue.add(request);

    }

    public void getUser(int id) {
        String url = "http://mateuszzbylut.com/service.php?userInfo=" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                users.add(response);
//                TextView textView = (TextView) findViewById(R.id.textTest);
//                textView.setText(response.toString());
                Log.d("Helpim", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Helpim", "Error: getUser request");
            }
        });

        queue.add(request);
    }

}
