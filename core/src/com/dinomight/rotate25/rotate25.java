package com.dinomight.rotate25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import javax.swing.JOptionPane;

public class rotate25 extends ApplicationAdapter implements ApplicationListener, InputProcessor{

	private Texture[] squares = new Texture[25];
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public static final int SQUAREDIM = 96;
	private Tile[][] tiles = new Tile[5][5];

	@Override
	public void create () {

		for(int i = 0; i < 25; i++)
			squares[i] = new Texture(Gdx.files.internal(i+1+".png"));

		int k = 0;
		for(int j = 0; j < 5; j++) {
			for(int i = 0; i < 5; i++){
				tiles[i][j] = new Tile();
				tiles[i][j].setxCoord(160 + i*SQUAREDIM);
				tiles[i][j].setGdxYCoord(Math.abs(j-4)*SQUAREDIM);
				tiles[i][j].setyCoord(j*SQUAREDIM);
				tiles[i][j].setWidth(SQUAREDIM);
				tiles[i][j].setHeight(SQUAREDIM);
				tiles[i][j].setValue(k);
				tiles[i][j].setPic(squares[k]);
				tiles[i][j].setxGrid(i);
				tiles[i][j].setyGrid(j);
				k++;
			}

		}

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

	/*
		for(int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				System.out.println("(" + tiles[i][j].getxGrid()+ ","+tiles[i][j].getyGrid()+")");
			}
		}*/
	}

	@Override
	public void render () throws OutOfFieldException{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); //blue bg
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				batch.draw(tiles[i][j].getPic(), tiles[i][j].getxCoord(), tiles[i][j].getGdxYCoord());

			}
		}
		batch.end();


	}
	
	@Override
	public void dispose () {
		for (int i = 0; i < 25; i++) {
			squares[i].dispose();

		}
	}

	public int convertX(float x){
		int iValue;
		iValue = (((int)x-160)/SQUAREDIM);
		return iValue;
	}

	public int convertY(float y){
		int jValue;
		jValue = Math.abs((int)(y/SQUAREDIM)-4);
		return jValue;
	}

	public void rotateLeft(Tile center){
		int centerX, centerY;
		centerX = center.getxGrid();
		centerY = center.getyGrid();
		Tile tempTile = new Tile();
		Tile OGTile = new Tile();
		OGTile.equals(tiles[centerX-1][centerY-1]);
		tempTile.equals(tiles[centerX][centerY-1]);

		tiles[centerX-1][centerY-1].equals(tiles[centerX][centerY-1]); //topleft taking topcenter
		tiles[centerX-1][centerY-1].takeCoords(OGTile);

		tiles[centerX][centerY-1].equals(tiles[centerX+1][centerY-1]); //topcenter taking topright
		tiles[centerX][centerY-1].takeCoords(tempTile);
		tempTile = tiles[centerX+1][centerY-1];

		tiles[centerX+1][centerY-1].equals(tiles[centerX+1][centerY]); //topright taking centerright
		tiles[centerX+1][centerY-1].takeCoords(tempTile);
		tempTile = tiles[centerX+1][centerY];

		tiles[centerX+1][centerY].equals(tiles[centerX+1][centerY+1]); //centerright taking bottomright
		tiles[centerX+1][centerY].takeCoords(tempTile);
		tempTile = tiles[centerX+1][centerY+1];

		tiles[centerX+1][centerY+1].equals(tiles[centerX][centerY+1]); //bottomright taking bottomcenter
		tiles[centerX+1][centerY+1].takeCoords(tempTile);
		tempTile = tiles[centerX][centerY+1];

		tiles[centerX][centerY+1].equals(tiles[centerX-1][centerY+1]); //bottomcenter taking bottomleft
		tiles[centerX][centerY+1].takeCoords(tempTile);
		tempTile = tiles[centerX-1][centerY+1];

		tiles[centerX-1][centerY+1].equals(tiles[centerX-1][centerY]); //bottomleft taking centerleft
		tiles[centerX-1][centerY+1].takeCoords(tempTile);
		tempTile = tiles[centerX-1][centerY];

		tiles[centerX-1][centerY].equals(OGTile); //centerleft taking topleft
		tiles[centerX-1][centerY].takeCoords(tempTile);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPos = new Vector3(); //new 3d vector
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos); //convert vector into camera coordinates

		if(touchPos.x < 160 || touchPos.x > 640){ //if out of game field
			System.out.println("Oh hi dere");
		}
		else{
			int tempx, tempy;
			tempx = convertX(touchPos.x);
			tempy = convertY(touchPos.y);
			if(tempx == 0 || tempx == 4 || tempy == 0 || tempy == 4){ //if click border squares
				System.out.println("Oh hi dere");
			}
			else{

				rotateLeft(tiles[tempx][tempy]);

				for(int j = 0; j < 5; j++) {
					for (int i = 0; i < 5; i++) {
						System.out.println("(" + tiles[i][j].getxGrid()+ ","+tiles[i][j].getyGrid()+")" + tiles[i][j].getValue());
						//System.out.println(tiles[i][j].getPic());
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
