package edu.dlsu.mobapde.picnicpanicbeta;

import android.graphics.Bitmap;

/**
 * Created by Allyza on 13/11/2017.
 */

public class FallingObject {

    Bitmap image;
    int init;
    int[] pos;
    int xPos, yPos;
    int xPosCurr, yPosCurr;
    int currIndex;

    public FallingObject(Bitmap image, int[] pos, int init) {
        this.image = image;
        this.pos = pos;
        this.init = init;
    }

    public FallingObject start(int currIndex) {
        this.xPos = this.xPosCurr = this.pos[currIndex];
        this.yPos = this.yPosCurr = this.init;
        this.currIndex = currIndex;
        return this;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxPosCurr() {
        return xPosCurr;
    }

    public void setxPosCurr(int xPosCurr) {
        this.xPosCurr = xPosCurr;
    }

    public int getyPosCurr() {
        return yPosCurr;
    }

    public void setyPosCurr(int yPosCurr) {
        this.yPosCurr = yPosCurr;
    }

    public void move_object(int y_pos) {
        setyPos(y_pos);
    }

    public void motion_object(int speed) {
        if (yPosCurr <= yPos)
            yPosCurr += speed;
    }

    public int getCurrIndex() {
        return currIndex;
    }
}
