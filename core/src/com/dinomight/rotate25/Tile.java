package com.dinomight.rotate25;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Jacqueline on 2016-08-26.
 */
public class Tile {

    private Texture pic;
    private int xCoord;
    private int yCoord;
    private int gdxYCoord; //because gdx (0,0) is bottom left while android(0,0) is top left
    private int height;
    private int width;
    private int value;
    private int xGrid;
    private int yGrid;


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

    public void setxGrid(int x){
        this.xGrid = x;
    }

    public void setyGrid(int y){
        this.yGrid = y;
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

    public int getxGrid(){
        return this.xGrid;
    }

    public int getyGrid(){
        return this.yGrid;
    }

    public void takeCoords(Tile target){
        this.xCoord = target.xCoord;
        this.gdxYCoord = target.gdxYCoord;
        this.yCoord = target.yCoord;
        this.xGrid = target.xGrid;
        this.yGrid = target.yGrid;
    }

    public void copy(Tile target){
        this.pic = target.pic;
        this.value = target.value;
    }


    /*public int convertX(float x){
        int iValue;
        iValue = (((int)x-160)/rotate25.SQUAREDIM);
        return iValue;
    }

    public int convertY(float y){
        int jValue;
        jValue = Math.abs((int)(y/rotate25.SQUAREDIM)-4);
        return jValue;
    }*/

}