package com.mateuszzbylut.hackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.chromium.net.UrlRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    public RequestQueue queue;
    private Session session;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = Volley.newRequestQueue(this);
        context = this;
        session = new Session(this);

    }

    public void login(View view) {
        EditText loginALF = (EditText) findViewById(R.id.loginALF);
        String username = loginALF.getText().toString();
        EditText passwordALF = (EditText) findViewById(R.id.passwordALF);
        String pass = passwordALF.getText().toString();
        Log.d("Helpim", username);

        sendWorkPostRequest(username, pass);
    }

    private void sendWorkPostRequest(String user, String pass) {

        String url = "http://mateuszzbylut.com/service.php?username=" + user + "&password=" + pass;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    session.setusename(response.getString("id_user"));
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Helpim", error.toString());
            }
        });

        queue.add(request);
    }
}
