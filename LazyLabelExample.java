import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.rpsg.lazyFont.LazyFont;
import com.rpsg.lazyFont.LazyLabel;

public class Test implements ApplicationListener {
	public static void main(String[] args) {
		new LwjglApplication(new Test());
	}

	Stage stage;
	LabelStyle style;
	public void create() {
		stage=new Stage();
		style=new LabelStyle();
		style.font=new LazyFont(30, Gdx.files.internal("msyh.ttf"));
		style.fontColor=Color.RED;
		LazyLabel l=new LazyLabel("abcde\ndeded", style);
		l.setPosition(300, 200);
		stage.addActor(l);
		
		stage.setDebugAll(true);
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
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
