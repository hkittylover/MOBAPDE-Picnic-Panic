package edu.dlsu.mobapde.picnicpanicbeta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    // Important stuff
    Thread thread = null;
    boolean canDraw = false;
    Bitmap background;
    Bitmap life;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Paint paint;

    // values
    int screenWidth, screenHeight;
    int[] colPositions;
    int[] imgIds;
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

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_test_darker);

        // scales background and crops from bottom to top
        background = Bitmap.createBitmap(background, 0, background.getHeight() - height, width, height);

        // the image width and height will be 20% of the screen width
        imgWidth = screenWidth * 20 / 100;
        imgHeight = imgWidth;

        Bitmap catcherBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.catcher_basket), imgWidth, imgHeight, false);
        catcher = new Catcher(catcherBitmap, (width / numCol) + (width / numCol - imgWidth) / 2, height - imgHeight - screenHeight * 5 / 100, width / numCol, (width / numCol) / 3);

        life = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.heart), 75, 75, false);

        colPositions = new int[]{catcher.getMinPos(), catcher.getxPos(), catcher.getMaxPos()};
        imgIds = new int[]{
                R.drawable.food_apple,
                R.drawable.food_apple_1,
                R.drawable.food_aubergine,
                R.drawable.food_avocado,
                R.drawable.food_bacon,
                R.drawable.food_baguette,
                R.drawable.food_banana,
                R.drawable.food_bread,
                R.drawable.food_broccoli,
                R.drawable.food_burger,
                R.drawable.food_burrito,
                R.drawable.food_cake,
                R.drawable.food_cake_1,
                R.drawable.food_candy,
                R.drawable.food_canned_food,
                R.drawable.food_carrot,
                R.drawable.food_cheese,
                R.drawable.food_cherry,
                R.drawable.food_chestnut,
                R.drawable.food_chicken,
                R.drawable.food_chicken_leg,
                R.drawable.food_chinese_food,
                R.drawable.food_chocolate,
                R.drawable.food_coconut,
                R.drawable.food_coffee,
                R.drawable.food_cookie,
                R.drawable.food_corn,
                R.drawable.food_creme_caramel,
                R.drawable.food_croissant,
                R.drawable.food_cucumber,
                R.drawable.food_donuts,
                R.drawable.food_egg,
                R.drawable.food_food,
                R.drawable.food_food_1,
                R.drawable.food_fried_egg,
                R.drawable.food_fries,
                R.drawable.food_grapes,
                R.drawable.food_honey,
                R.drawable.food_hot_dog,
                R.drawable.food_ice_cream,
                R.drawable.food_ice_cream_1,
                R.drawable.food_ice_cream_2,
                R.drawable.food_kiwi,
                R.drawable.food_lemon,
                R.drawable.food_lollipop,
                R.drawable.food_meat,
                R.drawable.food_melon,
                R.drawable.food_milk,
                R.drawable.food_noodles,
                R.drawable.food_nori,
                R.drawable.food_nori_1,
                R.drawable.food_orange,
                R.drawable.food_pancake,
                R.drawable.food_pasta,
                R.drawable.food_pasty,
                R.drawable.food_pasty_1,
                R.drawable.food_peach,
                R.drawable.food_peanut,
                R.drawable.food_pear,
                R.drawable.food_pie,
                R.drawable.food_pineapple,
                R.drawable.food_pizza,
                R.drawable.food_popcorn,
                R.drawable.food_potato,
                R.drawable.food_pretzel,
                R.drawable.food_radish,
                R.drawable.food_rice,
                R.drawable.food_rice_1,
                R.drawable.food_salad,
                R.drawable.food_sandwich,
                R.drawable.food_shrimp,
                R.drawable.food_soup,
                R.drawable.food_soup_1,
                R.drawable.food_steak,
                R.drawable.food_strawberry,
                R.drawable.food_sushi,
                R.drawable.food_sushi_1,
                R.drawable.food_sushi_2,
                R.drawable.food_taco,
                R.drawable.food_taco_1,
                R.drawable.food_tomato,
                R.drawable.food_water,
                R.drawable.food_watermelon,
        };

        fallingObjects = new ArrayList<FallingObject>();
    }

    @Override
    public void run() {
        int minY = screenHeight;
        int speed = 15;
        while (canDraw) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            canvas = surfaceHolder.lockCanvas();

            // Create falling object (chance: 1 / 8)
            Random r = new Random();
            if (minY >= 0 && r.nextInt() % 20 == 0) {
                int num = r.nextInt();

                // Create falling object
                Bitmap fall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                        imgIds[Math.abs(r.nextInt()) % imgIds.length]), imgWidth, imgHeight, false);
                FallingObject f = new FallingObject(fall, colPositions[Math.abs(num % 3)], -imgHeight);
                f.move_object(screenHeight + 1);

                // Add falling object to
                fallingObjects.add(f);
            }
            minY = screenHeight;
            canvas.drawBitmap(background, 0, 0, null);

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
                        score += multiplier * 1;
                        scoreMargin = (Integer.toString(score).length() - 1) * 45;
                        if (score % 20 == 0)
                            speed++;
                        iterator.remove();
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
