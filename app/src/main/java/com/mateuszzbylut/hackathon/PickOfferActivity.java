package com.mateuszzbylut.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PickOfferActivity extends AppCompatActivity {

    public JSONObject user;
    public JSONObject offer;
    public RequestQueue queue;
    private int id;
    int userID = 1;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_offer);
        queue = Volley.newRequestQueue(this);
        session = new Session(this);
        userID = Integer.parseInt(session.getusename());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Log.d("Helpim", String.valueOf(id));
        this.id = id;
        getOffer(id);
    }

    public void getOffer(int id) {
        String url = "http://mateuszzbylut.com/service.php?getOffer=" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                offer = response;
//                TextView textView = (TextView) findViewById(R.id.textTest);
//                textView.setText(response.toString());
                try {
                    getUser(Integer.parseInt(offer.getString("id_user")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Helpim", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Helpim", "Error: getOffer request");
                Log.d("Helpim", error.toString());
            }
        });

        queue.add(request);
    }

    public void getUser(int id) {
        String url = "http://mateuszzbylut.com/service.php?userInfo=" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                user = response;
                TextView poText = (TextView) findViewById(R.id.poText);
                TextView poName = (TextView) findViewById(R.id.poName);
                TextView poAddress = (TextView) findViewById(R.id.poAddress);
                try {
                    poText.setText(offer.getString("about"));
                    poName.setText(user.getString("name") + " " + user.getString("lastname"));
                    poAddress.setText(user.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public void goToRTO(View view) {
        Intent intent = new Intent(this, ResponseToOfferActivity.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }
}
