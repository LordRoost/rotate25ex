package com.dinomight.rotate25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

import javax.swing.JOptionPane;

public class rotate25 extends Game implements ApplicationListener, InputProcessor{

	private Texture[] squares = new Texture[25];
	private OrthographicCamera camera;
	public SpriteBatch batch;
	public BitmapFont font;
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
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
		Gdx.input.setInputProcessor(this);

		shuffle();
	}

	@Override
	public void render (){
		super.render();
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
		font.dispose();
		batch.dispose();
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
		Tile OGTile = tempTile.clone(tiles[centerX-1][centerY-1]);

		tiles[centerX-1][centerY-1].copy(tiles[centerX][centerY-1]); //topleft taking topcenter
		tiles[centerX][centerY-1].copy(tiles[centerX+1][centerY-1]); //topcenter taking topright
		tiles[centerX+1][centerY-1].copy(tiles[centerX+1][centerY]); //topright taking centerright
		tiles[centerX+1][centerY].copy(tiles[centerX+1][centerY+1]); //centerright taking bottomright
		tiles[centerX+1][centerY+1].copy(tiles[centerX][centerY+1]); //bottomright taking bottomcenter
		tiles[centerX][centerY+1].copy(tiles[centerX-1][centerY+1]); //bottomcenter taking bottomleft
		tiles[centerX-1][centerY+1].copy(tiles[centerX-1][centerY]); //bottomleft taking centerleft
		tiles[centerX-1][centerY].copy(OGTile); //centerleft taking topleft
	}

	public void rotateRight(Tile center){
		int centerX, centerY;
		centerX = center.getxGrid();
		centerY = center.getyGrid();

		Tile tempTile = new Tile();
		Tile OGTile = tempTile.clone(tiles[centerX-1][centerY-1]);

		tiles[centerX-1][centerY-1].copy(tiles[centerX-1][centerY]); //topleft taking centerleft
		tiles[centerX-1][centerY].copy(tiles[centerX-1][centerY+1]); //centerleft taking bottomleft
		tiles[centerX-1][centerY+1].copy(tiles[centerX][centerY+1]); //bottomleft taking bottomcenter
		tiles[centerX][centerY+1].copy(tiles[centerX+1][centerY+1]); //bottomcenter taking bottomright
		tiles[centerX+1][centerY+1].copy(tiles[centerX+1][centerY]); //bottomright taking centerright
		tiles[centerX+1][centerY].copy(tiles[centerX+1][centerY-1]); //centerright taking topright
		tiles[centerX+1][centerY-1].copy(tiles[centerX][centerY-1]); //topright taking topcenter
		tiles[centerX][centerY-1].copy(OGTile); //topcenter taking topleft
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
				rotateRight(tiles[tempx][tempy]);
				//errorCheck();
			}
		}
		if(checkWin())
			JOptionPane.showMessageDialog(null,"YOU WON!","YAY",JOptionPane.PLAIN_MESSAGE);
		return false;
	}

	public void shuffle(){
		for(int i = 0; i < 2; i++) {
			int random = (int)(Math.random()*9);
			switch (random){
				case 0: rotateLeft(tiles[1][1]);
					break;
				case 1: rotateLeft(tiles[2][1]);
					break;
				case 2: rotateLeft(tiles[3][1]);
					break;
				case 3: rotateLeft(tiles[2][1]);
					break;
				case 4: rotateLeft(tiles[2][2]);
					break;
				case 5: rotateLeft(tiles[2][3]);
					break;
				case 6: rotateLeft(tiles[3][1]);
					break;
				case 7: rotateLeft(tiles[3][2]);
					break;
				case 8: rotateLeft(tiles[3][3]);
					break;
			}
		}
	}

	public boolean checkWin(){
		for(int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				if(tiles[i][j].getValue() != j*5+i)
					return false;
			}
		}
		return true;
	}

	public void errorCheck(){
		for(int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				System.out.println("(" + tiles[i][j].getxGrid()+ ","+tiles[i][j].getyGrid()+")" + tiles[i][j].getValue());
				System.out.println(tiles[i][j].getPic());
			}
		}
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
