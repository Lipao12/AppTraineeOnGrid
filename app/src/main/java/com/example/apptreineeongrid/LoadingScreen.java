package com.example.apptreineeongrid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class LoadingScreen extends AppCompatActivity {

    ArrayList<JSONObject> json_questions = null;
    RequestQueue queue = null;

    int score;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        this.json_questions = new ArrayList<>();
        this.queue = Volley.newRequestQueue(this);
        this.score = 0;

        char difficulty = getIntent().getExtras().getChar("dificuldade");

        ImageView imageView = (ImageView) findViewById(R.id.LoadingView);
        Glide.with(this).asGif().load(R.raw.loading).into(imageView);

        requestQuestions(difficulty);
    }

    public void requestQuestions(char difficulty) {
        String url = String.format("https://appongridtraineer.garcias.dev:8443/question/list/%c", difficulty);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray questions = null;
                        try {
                            questions = response.getJSONArray("success");
                            for(int i = 0; i < questions.length(); i++) {
                                JSONObject object = null;
                                object = questions.getJSONObject(i);
                                LoadingScreen.this.json_questions.add(object);
                            }
                        } catch (JSONException e) {
                            LoadingScreen.this.bail(e.getMessage());
                        }
                        Toast.makeText(getApplicationContext(),"Conteudo carregado.", Toast.LENGTH_SHORT).show();
                        LoadingScreen.this.start();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // TODO: Handle error
                        LoadingScreen.this.bail(e.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void start() {
        if(this.json_questions.size() > 0) {
            int i = new Random(System.currentTimeMillis()).nextInt(this.json_questions.size());
            JSONObject question = this.json_questions.get(i);
            Intent game = new Intent(this, TelaQuestion.class);

            this.json_questions.remove(i);
            game.putExtra("score", this.score);
            game.putExtra("question", question.toString());

            startActivityForResult(game, 555);
        } else this.endGame();
    }

    public void bail(String message) {
        Log.v("Erro LoadingScreen", message);
        Toast.makeText(getApplicationContext(), String.format("Application failure: %s", message), Toast.LENGTH_SHORT).show();
        Intent home = new Intent(LoadingScreen.this, MainActivity.class);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
    }

    public void endGame() {
        Intent end = new Intent(this, TelaFinal.class);
        end.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        end.putExtra("final_score", this.score);
        startActivity(end);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 555 && data != null) {
            if(resultCode == Activity.RESULT_OK){
                int result = data.getExtras().getInt("result");
                switch (result) {
                    case -1:
                        LoadingScreen.this.endGame();
                        break;
                    case 0:
                        LoadingScreen.this.score++;
                        LoadingScreen.this.start();
                        break;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
