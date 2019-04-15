package com.mateuszzbylut.hackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ResponseToOfferActivity extends AppCompatActivity {

    private int id;
    private int userID = 1;
    CronetEngine cronetEngine;
    Context context;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_to_offer);
        context = this;
        Intent intent = getIntent();
        this.id = intent.getIntExtra("id", 0);
        session = new Session(this);
        userID = Integer.parseInt(session.getusename());
        CronetEngine.Builder myBuilder = new CronetEngine.Builder(this);
        cronetEngine = myBuilder.build();
    }

    public void sendResponse(View view) {
        EditText editText = (EditText) findViewById(R.id.rtoText);
        String value = editText.getText().toString();
        value.trim();
        Log.d("Helpim", value);
        Executor executor = Executors.newSingleThreadExecutor();
        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                "http://mateuszzbylut.com/service.php?makeProposal=t&offer=" + id + "&user=" + userID + "&text=" + value, new UrlRequestCallback(), executor);
        UrlRequest request = requestBuilder.build();
        request.start();
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
            Intent intent = new Intent(context, ResponseAddedActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
            Log.d("Helpim", error.getMessage());
        }
    }
}
