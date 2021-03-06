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

public class rotate25 extends Game{

	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render (){
		super.render();
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
	}
}
