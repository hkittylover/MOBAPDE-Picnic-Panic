package edu.dlsu.mobapde.picnicpanicbeta;

import android.graphics.Bitmap;

/**
 * Created by Allyza on 13/11/2017.
 */

public class Catcher {

    Bitmap img;
    int xPos, yPos;
    int xPosCurr, yPosCurr;
    int distance;
    int speed;
    int minPos, maxPos;

    public Catcher(Bitmap img, int xPos, int yPos, int distance, int speed) {
        this.img = img;
        this.xPos = xPos;
        this.xPosCurr = xPos;
        this.yPos = yPos;
        this.yPosCurr = yPos;
        this.distance = distance;
        this.speed = speed;
        this.minPos = xPos - distance;
        this.maxPos = xPos + distance;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMinPos() {
        return minPos;
    }

    public void setMinPos(int minPos) {
        this.minPos = minPos;
    }

    public int getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(int maxPos) {
        this.maxPos = maxPos;
    }

    public static final int CATCHER_RIGHT = 0;
    public static final int CATCHER_LEFT = 1;

    
    /**
     * Sets the end x coordinate of the bowl.
     * @param direction
     */
    public void moveCatcher(int direction) {
        if(direction == CATCHER_LEFT) {
            xPos = xPos == minPos ? minPos : xPos - distance;
        }
        if(direction == CATCHER_RIGHT) {
            xPos = xPos == maxPos ? maxPos : xPos + distance;
        }
    }

    /**
     * Animates the bowl.
     */
    public void motionCatcher() {
        if(xPos > xPosCurr) {
            xPosCurr += speed;
        } else if(xPos < xPosCurr) {
            xPosCurr -= speed;
        }
    }

}
