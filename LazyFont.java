package com.rpsg.lazyFont;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.FloatArray;

public class LazyFont extends BitmapFont {

	private int fontSize;
	private String currentText = "";
	private BitmapFont currentFont;
	private FreeTypeFontGenerator generator;
	private boolean changed = false, ownGenerator = true;
	private boolean dirty = false;
	List<LazyFont> disposes=new ArrayList<LazyFont>();

	public LazyFont(int fontSize, FileHandle ttf) {
		setFontSize(fontSize);
		setGenerator(ttf);
		generate(currentText);
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public LazyFont(LazyFont lazyFont){
		this(lazyFont.getFontSize(),lazyFont.getGenerator());
	}

	public LazyFont(int fontSize, FreeTypeFontGenerator gen) {
		setFontSize(fontSize);
		setGenerator(gen);
		generate(currentText);
	}

	private void setGenerator(FileHandle ttf2) {
		if (generator != null)
			generator.dispose();
		generator = new FreeTypeFontGenerator(ttf2);
	}
	
	FreeTypeFontGenerator getGenerator(){
		return generator;
	}

	private void setGenerator(FreeTypeFontGenerator gen) {
		if (generator != null)
			generator.dispose();
		generator = gen;
		ownGenerator = false;
	}

	public TextBounds draw(Batch batch, CharSequence str, float x, float y) {
		generate(str.toString());
		return currentFont.draw(batch, str, x, y);
	}

	@SuppressWarnings("deprecation")
	private void generate(String str) {
		if(str == null)
			str="";
		setCurrentText(str);
		if (currentText != null) {
			if (changed) {
				dirty=true;
				if (currentFont != null)
					currentFont.dispose();
				if(generator != null){
					currentFont = generator.generateFont(fontSize, dereplication(currentText), false);
				}
				
			}
		}
	}

	private String dereplication(String str) {
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (!data.contains(s))
				data.add(s);
		}
		String result = "";
		for (String s : data)
			result += s;
		return result;
	}

	public void dispose() {
		currentFont.dispose();
		if (ownGenerator)
			generator.dispose();
		for(LazyFont font:disposes)
			font.dispose();
	}

	public String getCurrentText() {
		return currentText;
	}

	public void setCurrentText(String text) {
		if (this.currentText == null || this.currentText.equals("") || !this.currentText.equals(text))
			changed = true;
		else
			changed = false;
		this.currentText = text;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		if (this.fontSize != fontSize)
			changed = true;
		this.fontSize = fontSize;
	}

	@Override
	public TextBounds draw(Batch batch, CharSequence str, float x, float y, int start, int end) {
		generate(str.toString());
		return currentFont.draw(batch, str, x, y, start, end);
	}

	@Override
	public TextBounds drawMultiLine(Batch batch, CharSequence str, float x, float y) {
		generate(str.toString());
		return currentFont.drawMultiLine(batch, str, x,y );
	}
	
	/**
	 * 是否开启英文字母智能自动换行 auto linefeed
	 * 测试版本，如果有bug请提交！！
	 */
	public boolean autoLinefeed = false;
	
	public TextBounds drawMultiLine(Batch batch, CharSequence str, float x, float y,float width) {
		generate(str.toString());

		if(autoLinefeed){
			String[] words=currentText.split(" ");
			int currX=0;
			int t_width=0;
			for(int i=0;i<words.length;i++){
				String s=words[i];
				t_width=0;
				char[] chars=s.toCharArray();
				boolean nextFlag=false;
				int tmpX=currX;
				for(char c:chars){
					tmpX+=getGlyphWidth(c);
					t_width+=getGlyphWidth(c);
					if(tmpX>width)
						nextFlag=true;
				}
				if(nextFlag){
					words[i]="\n"+s;
					currX=0;
				}else
					currX=t_width;
			}
			StringBuilder sb=new StringBuilder();
			for(String s:words)
				sb.append(s+" ");
			String str_f=sb.toString();
			BitmapFontCache cache=currentFont.getCache();
			cache.clear();
			TextBounds bounds = cache.addMultiLineText(str_f, x, y, 0, HAlignment.LEFT);
			
			cache.draw(batch);
			
			return bounds;
		}else{
			return currentFont.drawMultiLine(batch, str, x, y);
		}
		
	}
	
	private int getGlyphWidth(char c){
		return getData().getGlyph(c).width;
	}

	@Override
	public TextBounds drawMultiLine(Batch batch, CharSequence str, float x, float y, float alignmentWidth, HAlignment alignment) {
		generate(str.toString());
		return currentFont.drawMultiLine(batch, str, x, y, alignmentWidth, alignment);
	}

	@Override
	public TextBounds drawWrapped(Batch batch, CharSequence str, float x, float y, float wrapWidth) {
		generate(str.toString());
		return currentFont.drawWrapped(batch, str, x, y, wrapWidth);
	}

	@Override
	public TextBounds drawWrapped(Batch batch, CharSequence str, float x, float y, float wrapWidth, HAlignment alignment) {
		generate(str.toString());
		return currentFont.drawWrapped(batch, str, x, y, wrapWidth, alignment);
	}

	@Override
	public TextBounds getBounds(CharSequence str) {
		generate(str.toString());
		return currentFont.getBounds(str);
	}

	@Override
	public TextBounds getBounds(CharSequence str, TextBounds textBounds) {
		generate(str.toString());
		return currentFont.getBounds(str, textBounds);
	}

	@Override
	public TextBounds getBounds(CharSequence str, int start, int end) {
		generate(str.toString());
		return currentFont.getBounds(str, start, end);
	}

	@Override
	public TextBounds getBounds(CharSequence str, int start, int end, TextBounds textBounds) {
		generate(str.toString());
		return currentFont.getBounds(str, start, end, textBounds);
	}

	@Override
	public TextBounds getMultiLineBounds(CharSequence str) {
		generate(str.toString());
		return currentFont.getMultiLineBounds(str);
	}

	@Override
	public TextBounds getMultiLineBounds(CharSequence str, TextBounds textBounds) {
		generate(str.toString());
		return currentFont.getMultiLineBounds(str, textBounds);
	}

	@Override
	public TextBounds getWrappedBounds(CharSequence str, float wrapWidth) {
		generate(str.toString());
		return currentFont.getWrappedBounds(str, wrapWidth);
	}

	@Override
	public TextBounds getWrappedBounds(CharSequence str, float wrapWidth, TextBounds textBounds) {
		generate(str.toString());
		return currentFont.getWrappedBounds(str, wrapWidth, textBounds);
	}

	@Override
	public void computeGlyphAdvancesAndPositions(CharSequence str, FloatArray glyphAdvances, FloatArray glyphPositions) {
		generate(str.toString());
		currentFont.computeGlyphAdvancesAndPositions(str, glyphAdvances, glyphPositions);
	}

	@Override
	public int computeVisibleGlyphs(CharSequence str, int start, int end, float availableWidth) {
		generate(str.toString());
		return currentFont.computeVisibleGlyphs(str, start, end, availableWidth);
	}

	@Override
	public void setColor(float color) {
		currentFont.setColor(color);
	}

	@Override
	public void setColor(Color color) {
		currentFont.setColor(color);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		currentFont.setColor(r, g, b, a);
	}

	@Override
	public Color getColor() {
		return currentFont.getColor();
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		currentFont.setScale(scaleX, scaleY);
	}

	@Override
	public void setScale(float scaleXY) {
		currentFont.setScale(scaleXY);
	}

	@Override
	public void scale(float amount) {
		currentFont.scale(amount);
	}

	@Override
	public float getScaleX() {
		return currentFont.getScaleX();
	}

	@Override
	public float getScaleY() {
		return currentFont.getScaleY();
	}

	@Override
	public TextureRegion getRegion() {
		return currentFont.getRegion();
	}

	@Override
	public TextureRegion[] getRegions() {
		return currentFont.getRegions();
	}

	@Override
	public TextureRegion getRegion(int index) {
		return currentFont.getRegion(index);
	}

	@Override
	public float getLineHeight() {
		return currentFont.getLineHeight();
	}

	@Override
	public float getSpaceWidth() {
		return currentFont.getSpaceWidth();
	}

	@Override
	public float getXHeight() {
		return currentFont.getXHeight();
	}

	@Override
	public float getCapHeight() {
		return currentFont.getCapHeight();
	}

	@Override
	public float getAscent() {
		return currentFont.getAscent();
	}

	@Override
	public float getDescent() {
		return currentFont.getDescent();
	}

	@Override
	public boolean isFlipped() {
		return currentFont.isFlipped();
	}

	@Override
	public boolean isMarkupEnabled() {
		return currentFont.isMarkupEnabled();
	}

	@Override
	public void setMarkupEnabled(boolean markupEnabled) {
		currentFont.setMarkupEnabled(markupEnabled);
	}

	@Override
	public void setFixedWidthGlyphs(CharSequence glyphs) {
		currentFont.setFixedWidthGlyphs(glyphs);
	}

	@Override
	public boolean containsCharacter(char character) {
		return currentFont.containsCharacter(character);
	}

	@Override
	public void setUseIntegerPositions(boolean integer) {
		currentFont.setUseIntegerPositions(integer);
	}

	@Override
	public boolean usesIntegerPositions() {
		if(currentFont!=null)
			return currentFont.usesIntegerPositions();
		else
			 return true;
	}

	@Override
	public BitmapFontCache getCache() {
		return currentFont.getCache();
	}
	
	@Override
	public BitmapFontData getData() {
		return currentFont.getData();
	}

	@Override
	public boolean ownsTexture() {
		return currentFont.ownsTexture();
	}

	@Override
	public void setOwnsTexture(boolean ownsTexture) {
		currentFont.setOwnsTexture(ownsTexture);
	}

	@Override
	public String toString() {
		return currentFont.toString();
	}
}
