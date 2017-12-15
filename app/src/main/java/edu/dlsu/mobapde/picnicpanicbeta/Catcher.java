package edu.dlsu.mobapde.picnicpanicbeta;

import android.graphics.Bitmap;

/**
 * Created by Allyza on 13/11/2017.
 */

public class Catcher {

    Bitmap img;
    float xPos, yPos;
    float xPosCurr, yPosCurr;
    float distance;
    float speed;
    float minPos, maxPos;
    float currIndex;

    public Catcher(Bitmap img, float xPos, float yPos, float distance, float speed) {
        this.img = img;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xPosCurr = xPos;
        this.yPosCurr = yPos;
        this.distance = distance;
        this.speed = speed;
        this.minPos = xPos - distance;
        this.maxPos = xPos + distance;
        this.currIndex = 1;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getxPosCurr() {
        return xPosCurr;
    }

    public void setxPosCurr(float xPosCurr) {
        this.xPosCurr = xPosCurr;
    }

    public float getyPosCurr() {
        return yPosCurr;
    }

    public void setyPosCurr(float yPosCurr) {
        this.yPosCurr = yPosCurr;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMinPos() {
        return minPos;
    }

    public void setMinPos(float minPos) {
        this.minPos = minPos;
    }

    public float getMaxPos() {
        return maxPos;
    }

    public void setMaxPos(float maxPos) {
        this.maxPos = maxPos;
    }

    public float getCurrIndex() {
        return currIndex;
    }

    public void setCurrIndex(float currIndex) {
        this.currIndex = currIndex;
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
            currIndex = currIndex == 0 ? 0 : currIndex - 1;
        }
        if(direction == CATCHER_RIGHT) {
            xPos = xPos == maxPos ? maxPos : xPos + distance;
            currIndex = currIndex == 2 ? 2 : currIndex + 1;
        }
    }

    /**
     * Animates the bowl.
     */
    public void motionCatcher() {
        if(xPos > xPosCurr) {
            xPosCurr += speed;
            if(xPos < xPosCurr)
                xPosCurr = xPos;
        } else if(xPos < xPosCurr) {
            xPosCurr -= speed;
            if(xPos > xPosCurr)
                xPosCurr = xPos;
        }
    }

}
