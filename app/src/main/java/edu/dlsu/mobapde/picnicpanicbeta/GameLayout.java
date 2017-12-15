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
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
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
    boolean pause = false;
    Bitmap background;
    Rect rectOverlay;
    Paint paintOverlay;
    Bitmap life;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Paint paint;
    Context context;

    // values
    int screenWidth, screenHeight;
    int[] colPositions;
    ArrayList<FallingObject> foods;
    ArrayList<Bomb> bombs;
    int score = 0;
    int scoreMargin = 0;
    int lives = 5;
    int multiplier = 1;
    int imgWidth;
    int imgHeight;
    int basketWidth;
    int basketHeight;
    int numCol = 3;

    public GameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        surfaceHolder = getHolder();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_test_darker);
        background = new BackgroundScale(screenWidth, true).transform(background);

        background = Bitmap.createBitmap(background, 0, background.getHeight() - screenHeight, screenWidth, screenHeight);

        // the image width and height will be 20% of the screen width
        imgWidth = screenWidth * 17 / 100;
        imgHeight = imgWidth;
        basketWidth = screenWidth * 20 / 100;
        basketHeight = basketWidth;

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_catcher_basket3);
        Canvas temp = new Canvas();
        Bitmap basket = Bitmap.createBitmap(basketWidth, basketHeight, Bitmap.Config.ARGB_8888);
        temp.setBitmap(basket);
        drawable.setBounds(0, 0, basketWidth, basketHeight);
        drawable.draw(temp);

        catcher = new Catcher(basket, (screenWidth / numCol) + (screenWidth / numCol - basketWidth) / 2, screenHeight - basketHeight - screenHeight * 5 / 100, screenWidth / numCol, (screenWidth / numCol) / 2);

        colPositions = new int[numCol];
        for (int i = 0; i < numCol; i++) {
            colPositions[i] = (screenWidth / numCol) * i + (screenWidth / numCol - imgWidth) / 2;
        }

        life = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.heart), screenWidth * 8 / 100, screenWidth * 8 / 100, false);

        // get all food resources
        foods = new ArrayList<>();
        final Field[] fields = R.drawable.class.getDeclaredFields();
        final R.drawable drawableResources = new R.drawable();
        for (int i = 0; i < fields.length; i++) {
            try {
                if (fields[i].getName().contains("ic_0")) {

                    Drawable tmpD = ContextCompat.getDrawable(context, fields[i].getInt(drawableResources));
                    Canvas tmpC = new Canvas();
                    Bitmap fall = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                    tmpC.setBitmap(fall);
                    tmpD.setBounds(0, 0, imgWidth, imgHeight);
                    tmpD.draw(tmpC);

                    FallingObject f = new FallingObject(fall, colPositions, -imgHeight);
                    foods.add(f);
                }
            } catch (Exception e) {
                continue;
            }
        }

        bombs = new ArrayList<>();
        for(int i = 0; i < screenHeight / imgHeight; i++) {
            Drawable tmpD = ContextCompat.getDrawable(context, R.drawable.bomb);
            Canvas tmpC = new Canvas();
            Bitmap fall = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
            tmpC.setBitmap(fall);
            tmpD.setBounds(0, 0, imgWidth, imgHeight);
            tmpD.draw(tmpC);

            Bomb f = new Bomb(fall, colPositions, -imgHeight);
            bombs.add(f);
        }

        // Initialize sounds
        sfx_collected = MediaPlayer.create(context, R.raw.sfx_coin);
        fallingObjects = new ArrayList<FallingObject>();
    }

    @Override
    public void run() {
        int minY = screenHeight;
        int speed = 15;
        boolean gameover = false;
        AssetManager assetManager = getContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/unica_one.ttf");
        float textSize = screenWidth * 10 / 100;
        float outbound = screenHeight - basketHeight / 2 - screenHeight * 5 / 100;
        float scorePos = screenHeight * 625 / 10000;
        float heartXPos = screenWidth * 27 / 1000;
        float heartYPos = screenHeight * 16 / 1000;
        float lifeXPos = screenWidth * 12 / 100;
        float lifeYPos = screenHeight * 600 / 10000;
        int speedMultiplier = screenHeight * 8 / 10000;

        while (canDraw) {
            if (lives <= 0 || gameover) {
                MediaPlayer.create(getContext(), R.raw.lose).start();
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent();
                i.setClass(getContext(), GameOverActivity.class);
                i.putExtra("score", score);
                getContext().startActivity(i);
                ((Activity) getContext()).finish();
                break;
            }
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            canvas = surfaceHolder.lockCanvas();

            Random r = new Random();
            if (minY >= imgHeight / 3) {
                if (r.nextInt() % 20 == 1) {
                    int num = r.nextInt();

                    int obj = Math.abs(r.nextInt()) % foods.size();

                    int index = Math.abs(num % 3);
                    FallingObject f = foods.remove(obj).start(index);
                    f.move_object(screenHeight + 1);

                    fallingObjects.add(f);
                } else if (r.nextInt() % 200 == 0) {
                    int num = r.nextInt();

                    int index = Math.abs(num % 3);
                    FallingObject f = bombs.remove(0).start(index);
                    f.move_object(screenHeight + 1);

                    fallingObjects.add(f);
                } else if(r.nextInt() % 1000 == 0) {
                    int index = Math.abs(r.nextInt() % 3);
                    Drawable tmpD = ContextCompat.getDrawable(context, R.drawable.heart);
                    Canvas tmpC = new Canvas();
                    Bitmap fall = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                    tmpC.setBitmap(fall);
                    tmpD.setBounds(0, 0, imgWidth, imgHeight);
                    tmpD.draw(tmpC);

                    Heart f = new Heart(fall, colPositions, -imgHeight);
                    f.start(index);
                    f.move_object(screenHeight + 1);
                    fallingObjects.add(f);
                }
            }
            minY = screenHeight;

            // draw background
            canvas.drawBitmap(background, 0, 0, null);

            // TODO draw column dividers

            // move falling objects
            Iterator<FallingObject> iterator = fallingObjects.iterator();
            while (iterator.hasNext()) {
                FallingObject f = iterator.next();

                f.motion_object(speed);
                canvas.drawBitmap(f.getImage(), f.getxPosCurr(), f.getyPosCurr(), null);
                minY = Math.min(minY, f.getyPosCurr());

                // TODO keep track of time when powerup catched then update
                if (f.getyPosCurr() >= catcher.getyPos() - imgHeight) {
                    if (f.getCurrIndex() == catcher.getCurrIndex() && f.getyPosCurr() < outbound) {
                        if (f instanceof Bomb) {
                            gameover = true;
                        } else if(f instanceof Heart) {
                            lives++;
                        } else {
                            sfx_collected.start();
                            score += multiplier * 1;
                            //scoreMargin = (Integer.toString(score).length() - 1) * 45;
                            if (score % 20 == 0)
                                speed++;

                            if(score % 100 == 0)
                                multiplier++;

                            foods.add(f);
                        }
                        iterator.remove();
                    }
                    // if the falling object did not touch the catcher, it will just fall to the end of the screen
                    else if (f.getyPosCurr() >= screenHeight) {
                        iterator.remove();
                        if (!(f instanceof Bomb)) {
                            lives--;
                            foods.add(f);
                        } else {
                            bombs.add((Bomb) f);
                        }
                    }
                }
            }

            // move bowl
            catcher.motionCatcher();
            canvas.drawBitmap(catcher.getImg(), catcher.getxPosCurr(), catcher.getyPosCurr(), null);

            // update score;
            paint = new Paint();

            paint.setColor(Color.WHITE);
            paint.setTextSize(textSize);
            paint.setTypeface(typeface);
            canvas.drawText(Integer.toString(score), (canvas.getWidth() / 2) + ((paint.ascent() + paint.descent()) / 2), scorePos, paint);

            // draw hearts
            canvas.drawBitmap(life, heartXPos, heartYPos, null);

            paint.setTextSize(textSize);
            canvas.drawText("X" + Integer.toString(lives), lifeXPos, lifeYPos, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {

        canDraw = false;

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
