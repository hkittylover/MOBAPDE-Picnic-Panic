
package edu.dlsu.mobapde.picnicpanicbeta;

        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.drawable.Drawable;
        import android.support.v4.content.ContextCompat;

        import java.util.ArrayList;

/**
 * Created by Krizia Lynn on 14/11/2017.
 */

public class Bomb extends FallingObject {

    // for animating
    int imgCount;
    int bombState;
    int ellapsedFrames;
    boolean reverseAnimation;

    int imgWidth, imgHeight;

    Canvas canvas;

    public Bomb(Bitmap image, int[] pos, int init, int imgCount) {
        super(image, pos, init);
        this.imgCount = imgCount;

        bombState = 0;
        ellapsedFrames = 0;
        reverseAnimation = false;
    }

    public void setImageB(Bitmap image) {
        this.image = image;

        if(!reverseAnimation) {
            bombState++;
            if(bombState >= imgCount - 1)
                reverseAnimation = !reverseAnimation;
        } else {
            bombState--;
            if(bombState < 1)
                reverseAnimation = !reverseAnimation;
        }
    }

    public int getBombState() {
        return bombState;
    }

    public int getEllapsedFrames() {
        return ellapsedFrames;
    }

    public void incEllapsedFrames() {
        ellapsedFrames = (ellapsedFrames + 1) % 3;
    }
}

