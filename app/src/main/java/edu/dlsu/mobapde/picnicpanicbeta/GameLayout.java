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
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Paint paint;

    // values
    int screenWidth, screenHeight;
    int[] colPositions;
    int[] imgIds;
    int score = 0;
    int multiplier = 1;
    int imgWidth;
    int imgHeight;
    int numCol = 3;

    public GameLayout(Context context, int height, int width) {
        super(context);
        surfaceHolder = getHolder();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.trees);

        // the image width and height will be 20% of the screen width
        imgWidth = screenWidth * 20 / 100;
        imgHeight = imgWidth;

        Bitmap catcherBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.soup), imgWidth, imgHeight, false);
        catcher = new Catcher(catcherBitmap, (width / numCol) + (width / numCol - imgWidth) / 2, height - imgHeight - screenHeight * 5 / 100, width / numCol, (width / numCol) / 3);

        colPositions = new int[]{catcher.getMinPos(), catcher.getxPos(), catcher.getMaxPos()};
        imgIds = new int[]{
                R.drawable.bamboo,
                R.drawable.biwa,
                R.drawable.cherry_blossom,
                R.drawable.daruma,
                R.drawable.dohyo,
                R.drawable.ema,
                R.drawable.fude,
                R.drawable.fuji_mountain,
                R.drawable.geisha,
                R.drawable.geta,
                R.drawable.hamaya,
                R.drawable.hannya,
                R.drawable.haori,
                R.drawable.japan,
                R.drawable.kamon,
                R.drawable.kasa,
                R.drawable.katana,
                R.drawable.katana_1,
                R.drawable.kimono,
                R.drawable.kogai_and_kushi,
                R.drawable.koinobori,
                R.drawable.ko_omote,
                R.drawable.lotus,
                R.drawable.maneki_neko,
                R.drawable.ninja,
                R.drawable.omamori,
                R.drawable.origami,
                R.drawable.pagoda,
                R.drawable.paper_lantern,
                R.drawable.sake,
                R.drawable.shamisen,
                R.drawable.shamisen,
                R.drawable.shinto,
                R.drawable.sun,
                R.drawable.sushi,
                R.drawable.sushi_1,
                R.drawable.suzuri,
                R.drawable.taiko,
                R.drawable.tatami,
                R.drawable.tea,
                R.drawable.temple,
                R.drawable.torii,
                R.drawable.uchiwa,
                R.drawable.wagasa,
                R.drawable.washi,
                R.drawable.wind_bell,
                R.drawable.zen_garden,
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
                        if (score % 20 == 0)
                            speed++;
                        iterator.remove();
                        // TODO implement check if bomb or not
                        // if bomb, notify user
                    }
                    // if the falling object did not touch the catcher, it will just fall to the end of the screen
                    else if (f.getY_pos_curr() >= screenHeight) {
                        iterator.remove();
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
            canvas.drawText(Integer.toString(score), canvas.getWidth() / 2 - 20, 100, paint);

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
