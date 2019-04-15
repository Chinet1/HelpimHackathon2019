package com.mateuszzbylut.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    int userID = 0;
    private Session session;
    public RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        session = new Session(this);
        if (session.getusename().equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            userID = Integer.parseInt(session.getusename());
            Log.d("Helpim", String.valueOf(userID));
            userInfo();
        }
//        isActiveOffer();
    }

    public void goToOffers(View view) {
        Intent intent = new Intent(this, OffersActivity.class);
        startActivity(intent);
    }

    public void goToAddOffer(View view) {
        Intent intent = new Intent(this, AddOfferActivity.class);
        startActivity(intent);
    }

    public void goToOfferView(View view) {
        Intent intent = new Intent(this, OfferViewActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        session.setusename("0");
    }

    private void isActiveOffer() {
        String url = "http://mateuszzbylut.com/service.php?isActiveOffer=" + userID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("active")) {
                        Button button = (Button) findViewById(R.id.btnAdd);
                        button.setVisibility(View.GONE);
                    } else {
                        Button button = (Button) findViewById(R.id.btnState);
                        button.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Helpim AO", error.toString());
            }
        });

        queue.add(request);
    }

    private void userInfo() {
        String url = "http://mateuszzbylut.com/service.php?userInfo=" + userID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    TextView textView = (TextView) findViewById(R.id.userInfo);
                    textView.setText("Zalogowany jako " + response.getString("name") + " " + response.getString("lastname"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Helpim UI", error.toString());
            }
        });

        queue.add(request);
    }
}
