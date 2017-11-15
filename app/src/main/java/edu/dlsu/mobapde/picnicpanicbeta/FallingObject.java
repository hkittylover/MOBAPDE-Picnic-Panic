package edu.dlsu.mobapde.picnicpanicbeta;

import android.graphics.Bitmap;

/**
 * Created by Allyza on 13/11/2017.
 */

public class FallingObject {

    Bitmap image;
    int x_pos, y_pos;
    int x_pos_curr, y_pos_curr;

    public FallingObject(Bitmap image, int x_pos, int y_pos) {
        this.image = image;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.x_pos_curr = x_pos;
        this.y_pos_curr = y_pos;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public int getX_pos_curr() {
        return x_pos_curr;
    }

    public void setX_pos_curr(int x_pos_curr) {
        this.x_pos_curr = x_pos_curr;
    }

    public int getY_pos_curr() {
        return y_pos_curr;
    }

    public void setY_pos_curr(int y_pos_curr) {
        this.y_pos_curr = y_pos_curr;
    }

    public void move_object(int y_pos) {
        setY_pos(y_pos);
    }

    public void motion_object(int speed) {
        if(y_pos_curr <= y_pos)
            y_pos_curr += speed;
    }
}
