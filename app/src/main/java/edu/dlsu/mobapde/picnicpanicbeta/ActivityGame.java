package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Allyza on 13/11/2017.
 */

public class ActivityGame extends AppCompatActivity{

    GestureDetector gestureDetector;
    GameLayout gameLayout;
    ImageView buttonPause;
    PauseDialog pd;

    boolean hasDialog;
    SharedPreferences dsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        setContentView(R.layout.activity_game);

        gameLayout = (GameLayout) findViewById(R.id.layout_game);


        buttonPause = (ImageView) findViewById(R.id.button_pause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                PauseDialog pd = new PauseDialog();
                gameLayout.togglePause();
                pd.setCancelable(false);
                pd.show(getSupportFragmentManager(), "");

            }
        });

        gestureDetector = new GestureDetector(this, new GestureListenerHuhu());
        gameLayout.setOnTouchListener(touchListener);

        hasDialog = false;
    }

    public void saveMe(int score) {
        dsp = PreferenceManager.getDefaultSharedPreferences(this);

        int coinTotal = dsp.getInt("coinTotal", 0);
        if(coinTotal >= SaveMeDialog.COIN_NEED) {
            onPause();
            SaveMeDialog pd = new SaveMeDialog();
            Bundle b=new Bundle();
            b.putInt("coinTotal", coinTotal);
            b.putInt("score", score);
            pd.setArguments(b);
            gameLayout.togglePause();
            pd.setCancelable(false);
            pd.show(getSupportFragmentManager(), "");
        } else {
            Intent i = new Intent();
            i.setClass(this, GameOverActivity.class);
            i.putExtra("score", score);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLayout.pause();
        //gameLayout.getCanvas().save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameLayout.resume();
        if (gameLayout.getPause()) {
            gameLayout.pause();
            //gameLayout.invalidate();
        }
    }

    @Override
    public void onBackPressed() {

        Log.d("DEBUG GAME PAUSE", "if");
        onPause();
        PauseDialog pd = new PauseDialog();
        pd.setCancelable(false);
        pd.show(getSupportFragmentManager(), "");
        hasDialog = true;

    }

    public void resume(){
        gameLayout.togglePause();
        onResume();
    }

    public void saved() {
        dsp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor dspEditor = dsp.edit();
        dspEditor.putInt("coinTotal", dsp.getInt("coinTotal", 0) - SaveMeDialog.COIN_NEED);
        dspEditor.commit();
        gameLayout.togglePause();
        gameLayout.saved();
        onResume();
    }

    /*@Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gameLayout.motion_bowl(gameLayout.BOWL_RIGHT);
        return false;
    }*/

    //////////////////////// Listeners
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    };

    class GestureListenerHuhu implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
/*

            if(motionEvent1.getY() - motionEvent2.getY() > 50){
                Log.d("GESTURE", "Swipe Up");
                return true;
            }

            if(motionEvent2.getY() - motionEvent1.getY() > 50){
                Log.d("GESTURE", "Swipe Down");

                return true;
            }
*/

            if(motionEvent1.getX() - motionEvent2.getX() > 50){
                Log.d("GESTURE", "Swipe Left");
                gameLayout.getCatcher().moveCatcher(gameLayout.getCatcher().CATCHER_LEFT);

                return true;
            }

            if(motionEvent2.getX() - motionEvent1.getX() > 50) {

                Log.d("GESTURE", "Swipe Right");
                gameLayout.getCatcher().moveCatcher(gameLayout.getCatcher().CATCHER_RIGHT);

                return true;
            }
            else {

                return true ;
            }
        }
    }



/*
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if(event2.getX() > event1.getX()) {
                gameLayout.motion_bowl(gameLayout.BOWL_LEFT);
                Log.d("GESTURE", "Event2 > Event1");
            } else if(event2.getX() < event1.getX()) {
                gameLayout.motion_bowl(gameLayout.BOWL_LEFT);
                Log.d("GESTURE", "Event2 < Event1");
            }
            gameLayout.motion_bowl(gameLayout.BOWL_RIGHT);
            Log.d("GESTURE", "Event2 < Event1");

            return true;
        }

    }*/
}
