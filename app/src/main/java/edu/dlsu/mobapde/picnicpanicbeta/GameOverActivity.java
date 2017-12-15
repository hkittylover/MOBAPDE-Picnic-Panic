package edu.dlsu.mobapde.picnicpanicbeta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    Button buttonNewGame;
    Button buttonHome;
    TextView tvScore, tvHighscore, tvGameOver, tvHighscoreText, tvScoreText, tvCoinTotal, tvCoin, tvCoinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        int score = getIntent().getExtras().getInt("score");
        int coin = score / 10;
        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int highscore = dsp.getInt("highscore", -1);
        int coinTotal = dsp.getInt("coinTotal", 0);
        if (score > highscore || highscore == -1) {
            highscore = score;
            SharedPreferences.Editor dspEditor = dsp.edit();
            dspEditor.putInt("highscore", highscore);
            dspEditor.commit();
        }

        coinTotal += coin;
        SharedPreferences.Editor dspEditor = dsp.edit();
        dspEditor.putInt("coinTotal", coinTotal);
        dspEditor.commit();

        tvHighscore = (TextView) findViewById(R.id.tv_highscore);
        tvHighscore.setText("" + highscore);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvScore.setText("" + score);
        tvCoinTotal = (TextView) findViewById(R.id.tv_coin_total);
        tvCoinTotal.setText("" + coinTotal);
        tvCoin = (TextView) findViewById(R.id.tv_coin);
        tvCoin.setText("" + coin);
        tvHighscoreText = (TextView) findViewById(R.id.tv_highscore_text);
        tvScoreText = (TextView) findViewById(R.id.tv_score_text);
        tvGameOver = (TextView) findViewById(R.id.tv_game_over);
        tvCoinText = (TextView) findViewById(R.id.tv_coin_text);

        Typeface title = Typeface.createFromAsset(getAssets(),
                "fonts/dancing_script.ttf");
        tvGameOver.setTypeface(title);

        buttonNewGame = (Button) findViewById(R.id.button_new_game);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO go to game
                Intent i = new Intent();
                i.setClass(getBaseContext(), ActivityGame.class);

                startActivity(i);
                finish();
            }
        });
        buttonHome = (Button) findViewById(R.id.button_home);

        buttonHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO go to game
                Intent i = new Intent();
                i.setClass(getBaseContext(), MainActivity.class);

                startActivity(i);
                finish();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/unica_one.ttf");
        buttonHome.setTypeface(typeface);
        buttonNewGame.setTypeface(typeface);
        tvScore.setTypeface(typeface);
        tvHighscore.setTypeface(typeface);
        tvScoreText.setTypeface(typeface);
        tvHighscoreText.setTypeface(typeface);
        tvCoin.setTypeface(typeface);
        tvCoinText.setTypeface(typeface);
        tvCoinTotal.setTypeface(typeface);
    }

    @Override
    public void onBackPressed() {
        // go home
        Intent i = new Intent();
        i.setClass(getBaseContext(), MainActivity.class);

        startActivity(i);
        finish();
    }
}
