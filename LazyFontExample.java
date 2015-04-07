import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.rpsg.lazyFont.LazyFont;
import com.rpsg.lazyFont.LazyLabel;

public class Test implements ApplicationListener {
	public static void main(String[] args) {
		new LwjglApplication(new Test());
	}

	SpriteBatch sb;
	LazyFont font;
	public void create() {
		sb = new SpriteBatch();
  	font = new LazyFont(22, Gdx.files.internal("msyh.ttf"));//TODO rewrite to your ttf file
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  sb.begin();
	  font.draw(sb,"test",100,100);
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}

	public void resize(int width, int height) {
	}

}
