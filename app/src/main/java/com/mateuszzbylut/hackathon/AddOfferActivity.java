package com.mateuszzbylut.hackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddOfferActivity extends AppCompatActivity {

    public RequestQueue queue;
    CronetEngine cronetEngine;
    Context context;
    int userID = 1;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        queue = Volley.newRequestQueue(this);
        session = new Session(this);
        userID = Integer.parseInt(session.getusename());
        CronetEngine.Builder myBuilder = new CronetEngine.Builder(this);
        cronetEngine = myBuilder.build();
        context = this;
        getUser(userID);
    }

    public void getUser(int id) {
        String url = "http://mateuszzbylut.com/service.php?userInfo=" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                TextView aoName = (TextView) findViewById(R.id.aoName);
                TextView aoAddress = (TextView) findViewById(R.id.aoAddress);
                try {
                    aoName.setText(response.getString("name") + " " + response.getString("lastname"));
                    aoAddress.setText(response.getString("address"));
                    userID = response.getInt("id_user");
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

    public void addOffer(View view) {
        EditText editText = (EditText) findViewById(R.id.aoText);
        String value = editText.getText().toString();
        value.trim();
        Log.d("Helpim", value);
        Executor executor = Executors.newSingleThreadExecutor();
        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                "http://mateuszzbylut.com/service.php?createOffer=t&user=" + userID + "&text=" + value, new UrlRequestCallback(), executor);
        UrlRequest request = requestBuilder.build();
        request.start();
        Intent intent = new Intent(context, OfferAddedActivity.class);
        startActivity(intent);
    }

    class UrlRequestCallback extends UrlRequest.Callback {

        @Override
        public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) throws Exception {
            Log.d("Helpim", info.toString());
        }

        @Override
        public void onResponseStarted(UrlRequest request, UrlResponseInfo info) throws Exception {
            Log.d("Helpim", info.toString());

        }

        @Override
        public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) throws Exception {
            Log.d("Helpim", info.toString());

        }

        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
            Log.d("Helpim", info.toString());
        }

        @Override
        public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
            Log.d("Helpim", error.getMessage());
        }
    }
}
