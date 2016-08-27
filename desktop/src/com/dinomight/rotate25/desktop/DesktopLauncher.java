package com.dinomight.rotate25.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dinomight.rotate25.rotate25;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rotate25";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new rotate25(), config);
	}
}
