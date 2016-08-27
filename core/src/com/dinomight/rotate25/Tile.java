package com.dinomight.rotate25;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Jacqueline on 2016-08-26.
 */
public class Tile {

    private Texture pic;
    private int xCoord;
    private int yCoord;
    private int gdxYCoord;
    private int height;
    private int width;
    private int value;
    boolean clicked;

    public void setxCoord(int x){
        this.xCoord = x;
    }

    public void setyCoord(int y){
        this.yCoord = y;
    }

    public void setGdxYCoord(int y){
        this.gdxYCoord = y;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setPic(Texture pic){
        this.pic = pic;
    }

    public int getxCoord(){
        return this.xCoord;
    }

    public int getyCoord(){
        return this.yCoord;
    }

    public int getGdxYCoord(){
        return this.gdxYCoord;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getValue(){
        return this.value;
    }

    public Texture getPic(){
        return this.pic;
    }

    public void swap(Tile target){
        int tempX,tempY;
        tempX = this.xCoord;
        tempY = this.yCoord;
        this.xCoord = target.xCoord;
        this.yCoord = target.yCoord;
        target.xCoord = tempX;
        target.yCoord = tempY;
    }

    public int isClicked(float x, float y){
        if(x > this.xCoord & x < this.xCoord + this.width & y > this.yCoord & y < this.yCoord + this.height)
        {
            clicked = true;
            return this.value;
        }
        else
        {
            clicked = false;
            return -1;
        }
    }

}
