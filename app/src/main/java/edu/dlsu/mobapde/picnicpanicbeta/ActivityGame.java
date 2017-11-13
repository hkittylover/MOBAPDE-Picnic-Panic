package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Allyza on 13/11/2017.
 */

public class ActivityGame extends Activity {

    GestureDetector gestureDetector;
    GameLayout gameLayout;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        gameLayout = new GameLayout(this);
        setContentView(gameLayout);

        gestureDetector = new GestureDetector(this, new GestureListenerHuhu());
        gameLayout.setOnTouchListener(touchListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLayout.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameLayout.resume();
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
