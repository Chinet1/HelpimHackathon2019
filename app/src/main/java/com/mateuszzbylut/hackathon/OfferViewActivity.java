package com.mateuszzbylut.hackathon;

import android.content.Context;
import android.content.Intent;
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

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OfferViewActivity extends AppCompatActivity {

    public RequestQueue queue;
    public Context context;
    int userID;
    private Session session;
    CronetEngine cronetEngine;
    int offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);
        queue = Volley.newRequestQueue(this);
        context = this;
        session = new Session(this);
        userID = Integer.parseInt(session.getusename());
        CronetEngine.Builder myBuilder = new CronetEngine.Builder(this);
        cronetEngine = myBuilder.build();
        getOffer();
    }

    public void getOffer() {
        String url = "http://mateuszzbylut.com/service.php?getOfferByUser=" + userID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TextView ovText = (TextView) findViewById(R.id.ovText);
                try {
                    ovText.setText(response.getString("about"));
                    offer = response.getInt("id_offer");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Helpim", response.toString());
                getResponse();
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

    public void getResponse() {
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<Integer> expenseIds = new ArrayList<Integer>();

        list.add("Mateusz: Jestem chętny!");
        expenseIds.add(1);

        ListView listView = (ListView) findViewById(R.id.ovList);

        final ArrayAdapter adapter = new ArrayAdapter(context,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    public void deactivateOffer(View view) {
        Executor executor = Executors.newSingleThreadExecutor();
        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                "http://mateuszzbylut.com/service.php?deactivateOffer=" + offer, new UrlRequestCallback(), executor);
        UrlRequest request = requestBuilder.build();
        request.start();
        Intent intent = new Intent(context, MainActivity.class);
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
