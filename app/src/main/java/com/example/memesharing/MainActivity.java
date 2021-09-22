package com.example.memesharing;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Barrier;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView  imageView;
    ProgressBar Bar;
    String urltoimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView_meme);
        Bar=(ProgressBar)findViewById(R.id.Bar);
        memeshare();

    }


      @RequiresApi(api = Build.VERSION_CODES.N)
      public void memeshare(){
         Bar.onVisibilityAggregated(true);
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://meme-api.herokuapp.com/gimme", null, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {   urltoimage=response.getString("url");
                            Glide.with(MainActivity.this).load(response.getString("url")).into(imageView);
                            Toast.makeText(MainActivity.this, "Next", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);


    }

    public void nextmeme(View view) {
        memeshare();
    }

    public void sharememe(View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, urltoimage);


        Intent shareIntent = Intent.createChooser(sendIntent, "share using");
        startActivity(shareIntent);
    }
}