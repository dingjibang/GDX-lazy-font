package com.rpsg.lazyFont;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LazyLabel extends Label{

	public LazyLabel(CharSequence text, LabelStyle style) {
		super(text, style);
		if(((LazyFont)style.font).isDirty()){
			LabelStyle style2=new LabelStyle(style);
			style2.font=new LazyFont((LazyFont) style.font);
			((LazyFont)style.font).disposes.add((LazyFont) style2.font);
			setStyle(style2);
		}
	}

	public LazyLabel(CharSequence text, Skin skin, String fontName, Color color) {
		super(text, skin, fontName, color);
	}

	public LazyLabel(CharSequence text, Skin skin, String fontName, String colorName) {
		super(text, skin, fontName, colorName);
	}

	public LazyLabel(CharSequence text, Skin skin, String styleName) {
		super(text, skin, styleName);
	}

	public LazyLabel(CharSequence text, Skin skin) {
		super(text, skin);
	}
	
	public void draw (Batch batch, float parentAlpha) {
		validate();
		Color color = getColor();
		color.a *= parentAlpha;
		if (getStyle().background != null) {
			batch.setColor(color.r, color.g, color.b, color.a);
			getStyle().background.draw(batch, getX(), getY(), getWidth(), getHeight());
		}
		if (getStyle().fontColor != null) color.mul(getStyle().fontColor);
		BitmapFont font=getStyle().font;
		font.setColor(color);
		getStyle().font.getCache().setPosition(getX(), getY());
		((LazyFont)font).drawMultiLine(batch, getText(), getX(), getY(0)+getStyle().font.getCache().getBounds().height/2,getWidth());
	}
	

}
