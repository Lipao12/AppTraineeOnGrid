package com.example.apptreineeongrid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class TelaQuestion extends AppCompatActivity {
    boolean counter_continue;

    Button b_r1 = null;
    Button b_r2 = null;
    Button b_r3 = null;
    Button b_continue = null;
    Button b_curiosity = null;

    TextView t_question_text = null;
    TextView t_score_text = null;
    TextView t_counter = null;

    ImageView i_question_background = null;
    ImageView i_happy_face = null;
    ImageView i_sad_face = null;
    ImageView i_image_view = null;

    Question question = null;

    Typeface font = null;

    Intent return_intent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        String json = getIntent().getExtras().getString("question");
        JSONObject object = null;
        try {
            object = new JSONObject(json);
            this.question = new Question(object.getString("text"), object.getString("correct"), object.getString("incorrect1"), object.getString("incorrect2"), object.getString("curiosity"), object.getString("difficulty").charAt(0));
        } catch (JSONException e) {
            this.bail(e.getMessage());
        }

        this.return_intent = new Intent();

        this.counter_continue = true;

        this.b_r1        = findViewById(R.id.Opc1);
        this.b_r2        = findViewById(R.id.Opc2);
        this.b_r3        = findViewById(R.id.Opc3);
        this.b_continue  = findViewById(R.id.continuar);
        this.b_curiosity = findViewById(R.id.vcSabia);

        this.t_question_text = findViewById(R.id.pergunta);
        this.t_score_text    = findViewById(R.id.score);
        this.t_counter       = findViewById(R.id.counter);

        this.i_question_background = findViewById(R.id.imageView);
        this.i_happy_face          = findViewById(R.id.rosto_feliz);
        this.i_sad_face            = findViewById(R.id.rosto_triste);
        this.i_image_view          = findViewById(R.id.imageView);

        font = Typeface.createFromAsset(getAssets(),"RobotoMono-Light.ttf");

        this.b_r1.setTypeface(font);
        this.b_r2.setTypeface(font);
        this.b_r3.setTypeface(font);
        this.t_question_text.setTypeface(font);

        this.setInvisibleAndDisable();

        Runnable correctOptionCallback = () -> {
            this.return_intent.putExtra("result", 0);
            this.i_happy_face.setVisibility(View.VISIBLE);
            setResult(Activity.RESULT_OK,  this.return_intent);
        };

        Runnable incorrectOptionCallback = () -> {
            this.return_intent.putExtra("result", -1);
            this.i_sad_face.setVisibility(View.VISIBLE);
            setResult(Activity.RESULT_OK, this.return_intent);
        };

        Runnable enableCuriosityAndContinue = () -> {
            this.t_question_text.setVisibility(View.INVISIBLE);
            this.i_image_view.setVisibility(View.INVISIBLE);
            this.b_curiosity.setVisibility(View.VISIBLE);
            this.b_curiosity.setEnabled(true);
            this.b_continue.setVisibility(View.VISIBLE);
            this.b_continue.setEnabled(true);
        };

        Runnable disableOptions = () -> {
            this.b_r1.setEnabled(false);
            this.b_r2.setEnabled(false);
            this.b_r3.setEnabled(false);
            this.counter_continue = false;
        };

        ArrayList<Pair<Button, Integer>> arr = new ArrayList<>();

        Runnable failFunc = () -> {
            disableOptions.run();
            enableCuriosityAndContinue.run();
            incorrectOptionCallback.run();
            this.setOptsColors(arr);
        };

        Runnable successFunc = () -> {
            disableOptions.run();
            enableCuriosityAndContinue.run();
            correctOptionCallback.run();
            this.setOptsColors(arr);
        };



        switch(new Random(System.currentTimeMillis()).nextInt(3)) {
            case 0: {
                arr.add(new Pair<Button, Integer>(this.b_r1, 0xD806B50D));
                this.b_r1.setText(this.question.getCorrect());
                this.b_r2.setText(this.question.getIncorrect1());
                this.b_r3.setText(this.question.getIncorrect2());
                this.b_r1.setOnClickListener(v -> {
                    successFunc.run();
                });
                this.b_r2.setOnClickListener(v -> {
                    arr.add(new Pair<Button, Integer>(this.b_r2, 0xFFCA1010));
                    failFunc.run();
                });
                this.b_r3.setOnClickListener(v -> {
                    arr.add(new Pair<Button, Integer>(this.b_r3, 0xFFCA1010));
                    failFunc.run();
                });
                break;
            }
            case 1: {
                arr.add(new Pair<Button, Integer>(this.b_r2, 0xD806B50D));
                this.b_r2.setText(this.question.getCorrect());
                this.b_r1.setText(this.question.getIncorrect1());
                this.b_r3.setText(this.question.getIncorrect2());
                this.b_r2.setOnClickListener(v -> {
                    successFunc.run();
                });
                this.b_r1.setOnClickListener(v -> {
                    arr.add(new Pair<Button, Integer>(this.b_r1, 0xFFCA1010));
                    failFunc.run();
                });
                this.b_r3.setOnClickListener(v -> {
                    arr.add(new Pair<Button, Integer>(this.b_r3, 0xFFCA1010));
                    failFunc.run();
                });
                break;
            }
            case 2: {
                arr.add(new Pair<Button, Integer>(this.b_r3, 0xD806B50D));
                this.b_r3.setText(this.question.getCorrect());
                this.b_r2.setText(this.question.getIncorrect1());
                this.b_r1.setText(this.question.getIncorrect2());
                this.b_r3.setOnClickListener(v -> {
                    successFunc.run();
                });
                this.b_r2.setOnClickListener(v -> {
                    arr.add(new Pair<Button, Integer>(this.b_r2, 0xFFCA1010));
                    failFunc.run();
                });
                this.b_r1.setOnClickListener(v -> {
                    arr.add(new Pair<Button, Integer>(this.b_r1, 0xFFCA1010));
                    failFunc.run();
                });
                break;
            }
        }

        System.out.println(String.format("Resposta certa: %s", this.question.getCorrect()));

        this.b_curiosity.setOnClickListener(v -> {
            Intent curiosity = new Intent(this, TelaCuriosidade.class);
            curiosity.putExtra("curiosidade", this.question.getCuriosity());

            startActivity(curiosity);
        });

        this.b_continue.setOnClickListener(v -> {
            finish();
        });

        this.t_question_text.setText(this.question.getText());
        this.t_score_text.setText(Integer.toString(getIntent().getExtras().getInt("score")));

        _counter(30, failFunc);
    }

    private void setOptsColors(ArrayList<Pair<Button, Integer>> arr) {
        for(int i = 0; i < arr.size(); i++)
            arr.get(i).first.setBackgroundColor(arr.get(i).second);
    }

    private void _counter(int i, Runnable callback) {
        this.t_counter.setText(Integer.toString(i));
        if(this.counter_continue) {
            if (i > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int j = i;
                        j--;
                        _counter(j, callback);
                    }
                }, 1000);
            } else callback.run();
        }
    }

    private void setInvisibleAndDisable() {
        this.i_happy_face.setVisibility(View.INVISIBLE);
        this.i_sad_face.setVisibility(View.INVISIBLE);
        this.b_continue.setVisibility(View.INVISIBLE);
        this.b_curiosity.setVisibility(View.INVISIBLE);

        this.i_happy_face.setEnabled(false);
        this.i_sad_face.setEnabled(false);
        this.b_continue.setEnabled(false);
        this.b_curiosity.setEnabled(false);
    }

    private Runnable endPause() {
        return () -> finish();
    }

    public void bail(String message) {
        Log.v("Erro LoadingScreen", message);
        Toast.makeText(getApplicationContext(), String.format("Application failure: %s", message), Toast.LENGTH_SHORT).show();
        Intent home = new Intent(TelaQuestion.this, MainActivity.class);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Voce ira para a tela inicial, tem certeza?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent home = new Intent(TelaQuestion.this, MainActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
