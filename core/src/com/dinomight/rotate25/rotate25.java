package com.dinomight.rotate25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import javax.swing.JOptionPane;

public class rotate25 extends ApplicationAdapter {

	private Texture[] squares = new Texture[25];
	private OrthographicCamera camera;
	private SpriteBatch batch;
	//private Rectangle[][] tiles = new Rectangle[5][5];
	private final int SQUAREDIM = 96;
	private Tile[][] tiles = new Tile[5][5];

	@Override
	public void create () {

		for(int i = 0; i < 25; i++)
			squares[i] = new Texture(Gdx.files.internal(i+1+".png"));

		int k = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++){
				tiles[j][i] = new Tile();
				tiles[j][i].setxCoord(160 + i*SQUAREDIM);
				tiles[j][i].setGdxYCoord(Math.abs(j-4)*SQUAREDIM);
				tiles[j][i].setyCoord(j*SQUAREDIM);
				tiles[j][i].setWidth(SQUAREDIM);
				tiles[j][i].setHeight(SQUAREDIM);
				tiles[j][i].setValue(k);
				tiles[j][i].setPic(squares[k]);
				k++;
			}

		}

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

	}

	@Override
	public void render () throws OutOfFieldException{
		int k = 0;
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); //blue bg
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				batch.draw(tiles[i][j].getPic(), tiles[j][i].getxCoord(), tiles[j][i].getGdxYCoord());

			}
		}
		batch.end();

		if(Gdx.input.isTouched()) { //if clicked or touched
			Vector3 touchPos = new Vector3(); //new 3d vector
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); //convert vector into camera coordinates

			//JOptionPane.showMessageDialog(null,touchPos.y,"Coordinates",JOptionPane.PLAIN_MESSAGE);
			if(touchPos.x < 160 || touchPos.x > 640){
				System.out.println("Oh hi dere");
			}
			else{
				int tempx, tempy;
				tempx = convertX(touchPos.x);
				tempy = convertY(touchPos.y);
				if(tempx == 0 || tempx == 4 || tempy == 0 || tempy == 4){
					System.out.println("Oh hi dere");
				}
			}
			//JOptionPane.showMessageDialog(null,tiles[convertX(touchPos.x)][convertY(touchPos.y)].getValue(),"Coordinates",JOptionPane.PLAIN_MESSAGE);

		}

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
}
