package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Allyza on 13/11/2017.
 */
public class GameLayout extends SurfaceView implements Runnable {

    // Catcher
    Catcher catcher;

    // falling objects
    List<FallingObject> fallingObjects;

    // Sfx
    MediaPlayer sfx_collected;

    // Important stuff
    Thread thread = null;
    boolean canDraw = false;
    Bitmap background;
    Rect rectOverlay;
    Paint paintOverlay;
    Bitmap life;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Paint paint;

    // values
    int screenWidth, screenHeight;
    int[] colPositions;
    ArrayList<Integer> imgIds;
    int score = 0;
    int scoreMargin = 0;
    int lives = 5;
    int multiplier = 1;
    int imgWidth;
    int imgHeight;
    int numCol = 3;

    public GameLayout(Context context, int height, int width) {
        super(context);
        surfaceHolder = getHolder();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        // background = BitmapFactory.decodeResource(getResources(), R.drawable.background_test);
        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.background_test_1), screenWidth, screenHeight, false);
        rectOverlay= new Rect();
        rectOverlay.set(0, 0, screenWidth, screenHeight);
        paintOverlay = new Paint();
        paintOverlay.setColor(Color.BLACK);
        paintOverlay.setStyle(Paint.Style.FILL);
        paintOverlay.setAlpha(60);
//         background = BitmapFactory.decodeResource(getResources(), R.drawable.background_test_darker);

//         // scales background and crops from bottom to top
//         background = Bitmap.createBitmap(background, 0, background.getHeight() - height, width, height);

        // the image width and height will be 20% of the screen width
        imgWidth = screenWidth * 20 / 100;
        imgHeight = imgWidth;

        Bitmap catcherBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.catcher_basket), imgWidth, imgHeight, false);

        catcher = new Catcher(catcherBitmap, (width / numCol) + (width / numCol - imgWidth) / 2, height - imgHeight - screenHeight * 5 / 100, width / numCol, (width / numCol) / 2);
        colPositions = new int[]{catcher.getMinPos(), catcher.getxPos(), catcher.getMaxPos()};


        life = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.heart), 75, 75, false);

        // get all food resources
        imgIds = new ArrayList<Integer>();
        final Field[] fields =  R.drawable.class.getDeclaredFields();
        final R.drawable drawableResources = new R.drawable();
        for (int i = 0; i < fields.length; i++) {
            try {
                if (fields[i].getName().contains("food_")) {
                    imgIds.add(fields[i].getInt(drawableResources));
                }
            } catch (Exception e) {
                continue;
            }
        }

        // Initialize sounds
        sfx_collected = MediaPlayer.create(context, R.raw.sfx_coin);
        fallingObjects = new ArrayList<FallingObject>();
    }

    @Override
    public void run() {
        int minY = screenHeight;
        int speed = 15;
        while (canDraw) {
            if(lives <= 0) {
                MediaPlayer.create(getContext(),R.raw.lose).start();
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent();
                i.setClass(getContext(), GameOverActivity.class);
                i.putExtra("score", score);
                getContext().startActivity(i);
                ((Activity)getContext()).finish();
            }
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            canvas = surfaceHolder.lockCanvas();

            // Create falling object (chance: 1 / 8)
            Random r = new Random();
            if (minY >= 0) {
                if (r.nextInt() % 20 == 0) {
                    int num = r.nextInt();

                    // Create falling object
                    Bitmap fall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                        imgIds.get(Math.abs(r.nextInt()) % imgIds.size())), imgWidth, imgHeight, false);
                    FallingObject f = new FallingObject(fall, colPositions[Math.abs(num % 3)], -imgHeight);
                    f.move_object(screenHeight + 1);

                    // Add falling object to
                    fallingObjects.add(f);
                } else if(r.nextInt() % 200 == 0) {
                    int num = r.nextInt();

                    // Create falling object
                    Bitmap fall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                            R.drawable.bomb), imgWidth, imgHeight, false);
                    FallingObject f = new Bomb(fall, colPositions[Math.abs(num % 3)], -imgHeight);
                    f.move_object(screenHeight + 1);

                    // Add falling object to
                    fallingObjects.add(f);
                }
            }
            minY = screenHeight;

            // draw background
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawRect(rectOverlay, paintOverlay);

            // TODO draw column dividers

            // move falling objects
            Iterator<FallingObject> iterator = fallingObjects.iterator();
            while (iterator.hasNext()) {
                FallingObject f = iterator.next();

                f.motion_object(speed);
                canvas.drawBitmap(f.getImage(), f.getX_pos_curr(), f.getY_pos_curr(), null);
                minY = Math.min(minY, f.getY_pos_curr());
                // remove falling object from array
                // as long as the falling object touches the catcher, it is considered as 1 pt


                if (f.getY_pos_curr() >= catcher.getyPos() - imgHeight) {
                    if (f.getX_pos_curr() == catcher.getxPos()) {
                        if(f instanceof Bomb) {
                            MediaPlayer.create(getContext(),R.raw.lose).start();
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent i = new Intent();
                            i.setClass(getContext(), GameOverActivity.class);
                            i.putExtra("score", score);
                            getContext().startActivity(i);
                            ((Activity)getContext()).finish();
                        } else {
                            sfx_collected.start();
                            score += multiplier * 1;
                            scoreMargin = (Integer.toString(score).length() - 1) * 45;
                            if (score % 20 == 0)
                                speed++;
                        }
                        iterator.remove();

                        // play audio


                        // TODO implement check if bomb or not
                        // if bomb, notify user
                    }
                    // if the falling object did not touch the catcher, it will just fall to the end of the screen
                    else if (f.getY_pos_curr() >= screenHeight) {
                        iterator.remove();
                        lives--;

                    }
                }
            }

            // move bowl
            catcher.motionCatcher();
            canvas.drawBitmap(catcher.getImg(), catcher.getxPosCurr(), catcher.getyPosCurr(), null);

            // update score;
            paint = new Paint();

            paint.setColor(Color.WHITE);
            paint.setTextSize(75);
            AssetManager assetManager = getContext().getAssets();
            Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/unica_one.ttf");
            paint.setTypeface(typeface);
            canvas.drawText(Integer.toString(score), canvas.getWidth() - 70 - scoreMargin, 85, paint);

            // draw hearts
            for (int i = 0; i < lives; i++) {
                canvas.drawBitmap(life, 20 + i*90, 20, null);
            }


            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {

        canDraw = false;

        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread = null;

    }

    public void resume() {

        canDraw = true;
        thread = new Thread(this);
        thread.start();

    }

    public Catcher getCatcher() {
        return catcher;
    }


}
