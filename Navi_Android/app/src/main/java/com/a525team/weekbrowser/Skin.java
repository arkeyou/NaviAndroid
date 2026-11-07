package com.a525team.weekbrowser;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import java.util.Random;

public class Skin {
	
	private static volatile Skin instance;
	private final ExtendedDataHolder dataHolder;
	private Context appContext; // Кеш контексту для доступу до XML-ресурсів
	
	// Кешовані змінні
	public int[] color1, rcolor1, bg;
	public int grad, rgrad, bgrad, roundness, rroundness, sroundness, rsroundness;
	public int stripec, rstripec, bstripec, tm, rtm, btm, resol, stripeCount;
	public float gradientRadius, gradientCenterX, gradientCenterY;
	public float rgradientRadius, rgradientCenterX, rgradientCenterY;
	public float bgradientRadius, bgradientCenterX, bgradientCenterY;
	public int[] colors;
	
	private Skin() {
		dataHolder = ExtendedDataHolder.getInstance();
		reload();
	}
	
	public static Skin getInstance() {
		if (instance == null) {
			synchronized (Skin.class) {
				if (instance == null) {
					instance = new Skin();
				}
			}
		}
		return instance;
	}
	
	public void setStaticBG(android.app.Activity activity, int r, boolean wrapInScroll) {
		if (activity != null) {
			this.appContext = activity.getApplicationContext();
		}
		
		android.view.ViewGroup rootContent = activity.findViewById(android.R.id.content);
		android.view.View existingContent = rootContent.getChildAt(0);
		
		if (existingContent == null) return;
		
		existingContent.setBackgroundResource(0);
		
		if (existingContent instanceof android.widget.ScrollView) {
			android.view.View scrollChild = ((android.widget.ScrollView) existingContent).getChildAt(0);
			if (scrollChild != null) {
				scrollChild.setBackgroundResource(0);
			}
		}
		
		android.widget.FrameLayout newRoot = new android.widget.FrameLayout(activity);
		
		android.view.View bgView = new android.view.View(activity);
		bgView.setTag("custom_static_bg");
		newRoot.addView(bgView, new android.widget.FrameLayout.LayoutParams(
		android.view.ViewGroup.LayoutParams.MATCH_PARENT, 
		android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		
		if (wrapInScroll) {
			android.widget.ScrollView scrollView = new android.widget.ScrollView(activity);
			scrollView.setFillViewport(true);
			
			rootContent.removeView(existingContent);
			
			android.widget.FrameLayout.LayoutParams scrollChildParams = new android.widget.FrameLayout.LayoutParams(
			android.view.ViewGroup.LayoutParams.MATCH_PARENT, 
			android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			
			scrollView.addView(existingContent, scrollChildParams);
			
			newRoot.addView(scrollView, new android.widget.FrameLayout.LayoutParams(
			android.view.ViewGroup.LayoutParams.MATCH_PARENT, 
			android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		} else {
			rootContent.removeView(existingContent);
			newRoot.addView(existingContent, new android.widget.FrameLayout.LayoutParams(
			android.view.ViewGroup.LayoutParams.MATCH_PARENT, 
			android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		}
		
		activity.setContentView(newRoot);
		setBG(bgView, r, false);
	}
	
	public void updateBG(android.app.Activity activity, int r) {
		if (activity != null) {
			this.appContext = activity.getApplicationContext();
		}
		android.view.View bgView = activity.findViewById(android.R.id.content).findViewWithTag("custom_static_bg");
		if (bgView != null) {
			setBG(bgView, r, false);
			bgView.invalidate();
		}
	}
	
	public void reload() {
		color1 = parseColorArray(dataHolder.getData("bbg"));
		rcolor1 = parseColorArray(dataHolder.getData("rbg"));
		bg = parseColorArray(dataHolder.getData("bg"));
		
		grad = safeParseInt(dataHolder.getData("grad"), 0);
		rgrad = safeParseInt(dataHolder.getData("rgrad"), 0);
		bgrad = safeParseInt(dataHolder.getData("bgrad"), 0);
		
		roundness = safeParseInt(dataHolder.getData("rou"), 0);
		rroundness = safeParseInt(dataHolder.getData("rrou"), 0);
		sroundness = safeParseInt(dataHolder.getData("srou"), 0);
		rsroundness = safeParseInt(dataHolder.getData("rsrou"), 0);
		
		stripec = safeParseInt(dataHolder.getData("strcou"), 0);
		rstripec = safeParseInt(dataHolder.getData("rstrcou"), 0);
		bstripec = safeParseInt(dataHolder.getData("bstrcou"), 0);
		
		gradientRadius = safeParseFloat(dataHolder.getData("rad"), 0.0f);
		gradientCenterX = safeParseFloat(dataHolder.getData("x"), 0.0f);
		gradientCenterY = safeParseFloat(dataHolder.getData("y"), 0.0f);
		
		rgradientRadius = safeParseFloat(dataHolder.getData("rrad"), 0.0f);
		rgradientCenterX = safeParseFloat(dataHolder.getData("rx"), 0.0f);
		rgradientCenterY = safeParseFloat(dataHolder.getData("ry"), 0.0f);
		
		bgradientRadius = safeParseFloat(dataHolder.getData("brad"), 0.0f);
		bgradientCenterX = safeParseFloat(dataHolder.getData("bx"), 0.0f);
		bgradientCenterY = safeParseFloat(dataHolder.getData("by"), 0.0f);
		
		tm = safeParseInt(dataHolder.getData("tm"), 0);
		rtm = safeParseInt(dataHolder.getData("rtm"), 0);
		btm = safeParseInt(dataHolder.getData("btm"), 0);
		
		resol = safeParseInt(dataHolder.getData("resol"), 500);
	}
	
	private final java.util.ArrayList<SkinObserver> observers = new java.util.ArrayList<>();
	
	public void registerObserver(SkinObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
			observer.onSkinChanged();
		}
	}
	
	public void unregisterObserver(SkinObserver observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for (SkinObserver observer : observers) {
			if (observer != null) observer.onSkinChanged();
		}
	}
	
	public int[] getColorsByKey(String key) {
		if (key == null || key.isEmpty()) return null;
		switch (key) {
			case "bg": return this.bg;
			case "bbg": return this.color1;
			case "rbg": return this.rcolor1;
			default:
			return parseColorArray(dataHolder.getData(key));
		}
	}
	
	private static String toHex(int color) {
		return String.format("%08X", color);
	}
	
	private int safeParseInt(String data, int defaultValue) {
		try { return Integer.parseInt(data); } catch (Exception e) { return defaultValue; }
	}
	
	private float safeParseFloat(String data, float defaultValue) {
		try { return Float.parseFloat(data); } catch (Exception e) { return defaultValue; }
	}
	
	private int avgcol(int[] c) {
		if (c == null || c.length == 0) {
			return 0xFF00897B;
		}
		
		int cl = c.length;
		long a = 0, r = 0, g = 0, b = 0;
		
		for (int cc : c) {
			a += (cc >>> 24) & 0xFF;
			r += (cc >> 16) & 0xFF;
			g += (cc >> 8) & 0xFF;
			b += cc & 0xFF;
		}
		
		a /= cl;
		r /= cl;
		g /= cl;
		b /= cl;
		
		return ((int) a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
	}
	
	public int StatusCol() {
		try { return (int) Long.parseLong(dataHolder.getData("bg").substring(0, 8), 16); } 
		catch (Exception e) { return Color.BLACK; }
	}
	
	public int NavCol() {
		try { 
			String bbg = dataHolder.getData("bg");
			return (int) Long.parseLong(bbg.substring(bbg.length() - 8), 16); 
		} 
		catch (Exception e) { return Color.BLACK; }
	}
	
	public int avgbg() { return avgcol(bg); }
	public int avgb() { return avgcol(color1); }
	public int avgrb() { return avgcol(rcolor1); }
	
	private void applyColors(String bg, String bbg, String rbg, String t, String tf, String add, String h, String cb, String tpc, String tc, String bt, String rbt) {
		dataHolder.setData("bg", bg); dataHolder.setData("bbg", bbg); dataHolder.setData("rbg", rbg);
		dataHolder.setData("t", t); dataHolder.setData("tf", tf); dataHolder.setData("add", add);
		dataHolder.setData("h", h); dataHolder.setData("cb", cb); dataHolder.setData("tpc", tpc);
		dataHolder.setData("tc", tc); dataHolder.setData("bt", bt); dataHolder.setData("rbt", rbt);
	}
	
	private void applyGeometry(String rou, String rrou, String srou, String rsrou, String grad, String rad, String x, String y, String strcou) {
		dataHolder.setData("rou", rou); dataHolder.setData("rrou", rrou);
		dataHolder.setData("srou", srou); dataHolder.setData("rsrou", rsrou);
		dataHolder.setData("grad", grad); dataHolder.setData("rgrad", grad); dataHolder.setData("bgrad", grad);
		dataHolder.setData("rad", rad); dataHolder.setData("rrad", rad); dataHolder.setData("brad", rad);
		dataHolder.setData("x", x); dataHolder.setData("rx", x); dataHolder.setData("bx", x);
		dataHolder.setData("y", y); dataHolder.setData("ry", y); dataHolder.setData("by", y);
		dataHolder.setData("strcou", strcou); dataHolder.setData("rstrcou", strcou); dataHolder.setData("bstrcou", strcou);
		dataHolder.setData("tm", "1"); dataHolder.setData("rtm", "1"); dataHolder.setData("btm", "1");
	}
	
	public void colorToRam(Context context) {
		if (context != null) {
			this.appContext = context.getApplicationContext();
		}
		BitmapHolder.clearCache();
		JSutil.clearCache();
		
		SharedPreferences sf = context.getSharedPreferences("sf", Context.MODE_PRIVATE);
		String themePref = sf.getString("theme", "0");
		
		boolean applyDefaultGeometry = false;
		
		switch (themePref) {
			case "0": // Світла (Динамічна Monet)
			applyColors(
			processColor("m__a1010", true, context), 
			processColor("m__a1600", true, context), 
			"ffD32F2F",
			processColor("m__n2700", false, context), 
			processColor("m__n2700", false, context),
			processColor("m__a3600", false, context), 
			processColor("m__a2600", false, context),
			processColor("m__a1600", false, context), 
			processColor("m__a1600", false, context),
			processColor("m__a1600", false, context), 
			processColor("m__a1050", false, context),
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			break;
			
			case "8": // Світла (Статична — жорстко прописані HEX кольори)
			applyColors(
			"ffE9F6FB", // Замість m__a1010
			"ff00897B", // Замість m__a1600
			"ffD32F2F",
			"ff455A64", // Замість m__n2700
			"ff455A64", // Замість m__n2700
			"ff48617B", // Замість m__a3600
			"ff4A6360", // Замість m__a2600
			"ff00897B", // Замість m__a1600
			"ff00897B", // Замість m__a1600
			"ff00897B", // Замість m__a1600
			"ffE0F2F1", // Замість m__a1050
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			break;
			
			
			case "1": // Темна (Динамічна)
			applyColors(
			processColor("m__n2900", true, context), 
			processColor("m__a1700", true, context), 
			"ffC62828",
			processColor("m__n2200", false, context), 
			processColor("m__n2200", false, context),
			processColor("m__a3200", false, context), 
			processColor("m__a2200", false, context),
			processColor("m__a1200", false, context), 
			processColor("m__a1200", false, context),
			processColor("m__a1200", false, context), 
			processColor("m__a1050", false, context),
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			break;
			
			case "9": // Темна (Статична — прямі HEX кольори)
			applyColors(
			"ff263238", // m__n2900 (#263238)
			"ff00796B", // m__a1700 (#00796B)
			"ffC62828",
			"ffB0BEC5", // m__n2200 (#B0BEC5)
			"ffB0BEC5", // m__n2200 (#B0BEC5)
			"ffAFC9E7", // m__a3200 (#AFC9E7)
			"ffB0CCC8", // m__a2200 (#B0CCC8)
			"ff80CBC4", // m__a1200 (#80CBC4)
			"ff80CBC4", // m__a1200 (#80CBC4)
			"ff80CBC4", // m__a1200 (#80CBC4)
			"ffE0F2F1", // m__a1050 (#E0F2F1)
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			break;
			
			case "4": // Amoled White
			applyColors(
			"ffffffff", 
			processColor("m__a1600", true, context), 
			"ffD32F2F",
			processColor("m__n2700", false, context), 
			processColor("m__n2700", false, context),
			processColor("m__a3600", false, context), 
			processColor("m__a2600", false, context),
			processColor("m__a1600", false, context), 
			processColor("m__a1600", false, context),
			processColor("m__a1600", false, context), 
			processColor("m__a1050", false, context), 
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			dataHolder.setData("tmy", "2"); dataHolder.setData("rtmy", "2"); dataHolder.setData("btmy", "2");
			break;
			
			case "5": // Amoled Black (Динамічна)
			applyColors(
			"ff000000", 
			processColor("m__a1700", true, context), 
			"ffD32F2F",
			processColor("m__n2200", false, context), 
			processColor("m__n2200", false, context),
			processColor("m__a3200", false, context), 
			processColor("m__a2200", false, context),
			processColor("m__a1200", false, context), 
			processColor("m__a1200", false, context),
			processColor("m__a1200", false, context), 
			processColor("m__a1050", false, context), 
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			break;
			
			case "10": // Amoled Black (Статична — прямі HEX кольори)
			applyColors(
			"ff000000", // Чистий чорний
			"ff00796B", // m__a1700 (#00796B)
			"ffD32F2F",
			"ffB0BEC5", // m__n2200 (#B0BEC5)
			"ffB0BEC5", // m__n2200 (#B0BEC5)
			"ffAFC9E7", // m__a3200 (#AFC9E7)
			"ffB0CCC8", // m__a2200 (#B0CCC8)
			"ff80CBC4", // m__a1200 (#80CBC4)
			"ff80CBC4", // m__a1200 (#80CBC4)
			"ff80CBC4", // m__a1200 (#80CBC4)
			"ffE0F2F1", // m__a1050 (#E0F2F1)
			"ffFFCDD2"
			);
			applyDefaultGeometry = true;
			break;
			
			case "2":
			applyColors("ff000000", "ff00ffff", "ffff0000", "ffffffff", "ff000000", "ff00ffff", "ffffff00", "ffffff00", "ff00ff00", "ff00ffff", "ff000000", "ffffffff");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "6":
			applyColors("ffffffff", "ffd6d6d6", "ffd6d6d6", "ff000000", "ff000000", "ff35b5e5", "ff757575", "ff33b5e5", "ff33b5e5", "ff33b5e5", "ff000000", "ffff0000");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "7":
			applyColors("ff000000ff282d33", "ff525252", "ff525252", "ffffffff", "ffffffff", "ff35b5e5", "ffbdbdbd", "ff33b5e5", "ff33b5e5", "ff33b5e5", "ffffffff", "ffff8080");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "11":
			applyColors("ffffffff", "ffeaeaeaffb8b8b8", "ffeaeaeaffb8b8b8", "ff000000", "ff000000", "ff68B200", "ff888888", "ff68B200", "ffffbb00", "ff9b9b9b", "ff000000", "ffff0000");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "12":
			applyColors("ff000000", "ffeaeaeaffb8b8b8", "ffeaeaeaffb8b8b8", "ffffffff", "ff000000", "ff90ff22", "ff888888", "ff90ff22", "ffffbb00", "ff9b9b9b", "ff000000", "ffff0000");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "13":
			applyColors("ff000000", "ffffff00", "ffff0000", "ffffffff", "ff000000", "ff00ffff", "ff00ff00", "ffffff00", "ffffff00", "ffffff00", "ff000000", "ffffffff");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "14":
			applyColors("ff000000", "ff00ff00", "ffff0000", "ff00ff00", "ff00ffff", "ffffff00", "ffff00ff", "ff00ffff", "ff00ffff", "ff00ff00", "ff000000", "ffffff00");
			applyGeometry("6", "0", "4", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "15":
			applyColors("ffffffff", "ff000000", "ff0000ff", "ff000000", "ff000000", "ff0000ff", "ff880088", "ff000000", "ff000000", "ff0000ff", "ffffffff", "ff00ffff");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "16":
			applyColors("ff000000ff330000", "ff770000ff000000", "ffff0000ffff7777", "ffffdddd", "ffffbbbb", "ffff7777", "ffff0000", "ffff0000", "ffff0000", "ffff0000", "ffff7777", "ff550000");
			applyGeometry("15", "8", "8", "4", "0", "1", "0.5", "0.5", "2");
			dataHolder.setData("bgrad", "5"); dataHolder.setData("bstrcou", "5");
			break;
			
			case "17":
			applyColors("ffffbbbbffffdddd", "ff770000ffbb0000", "ff000000ffff7777", "ff330000", "ff770000", "ffff0000", "ffff5555", "ff770000", "ff770000", "ff770000", "ffffbbbb", "ff550000");
			applyGeometry("15", "8", "8", "4", "0", "1", "0.5", "0.5", "2");
			dataHolder.setData("bgrad", "5"); dataHolder.setData("bstrcou", "5");
			break;
			
			case "18":
			applyColors("ff000000ff331c00", "ffff8700ffce5d00", "ffbb0000ffff0000", "ffffdead", "ffffbf7f", "ffff8400", "ff885500", "ffff7a00", "ffff7b00", "ffffb100", "ffffffff", "ffffffff");
			applyGeometry("10", "5", "5", "2", "2", "1", "0.5", "0.5", "2");
			dataHolder.setData("rgrad", "1"); dataHolder.setData("bgrad", "0"); 
			dataHolder.setData("tm", "2");
			break;
			
			case "19":
			applyColors("ff000000ff181400", "ff00ffffff00bbbb", "ffa10000ff770000", "ffffffff", "ffffffbb", "ffffff00", "ff999900", "ffff7a00", "ffff7b00", "ffffb100", "ff003333", "ffffffff");
			applyGeometry("0", "0", "0", "0", "8", "1", "3.0", "0.5", "2");
			dataHolder.setData("bgrad", "6");
			dataHolder.setData("bx", "1.0"); dataHolder.setData("by", "-2.0");
			break;
			
			case "20":
			applyColors("ff322500", "ffffff00", "ffff7700", "ffffffff", "ffffffff", "ffff8000", "ffbd5e00", "ffff7a00", "ffff7b00", "ffffb100", "ff000000", "ffffffff");
			applyGeometry("20", "20", "10", "10", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "21":
			applyColors("ff003300", "ff000000", "ff330000", "ff00ff00", "ff00ff00", "ff00ffff", "ff00bb00", "ff00ff00", "ff00ffff", "ff00ff00", "ff00ff00", "ffff0000");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "22":
			applyColors("ffffffbb", "ffffff00", "ffb8ff77", "ff000000", "ff000000", "ff777700", "ffbbbb00", "ff777700", "ff777700", "ff777700", "ff555500", "ff2e5500");
			applyGeometry("20", "20", "10", "10", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "23":
			applyColors("ff003300ff000000", "ff005300ff00ff00", "ff000000ffff7300", "ffddffdd", "ffbbff99", "ff00ff00", "ff009900", "ff00ff00", "ff00ff00", "ff00ff00", "ff000000", "ffffff00");
			applyGeometry("0", "0", "0", "0", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "24":
			applyColors("ffffffff", "ff000000", "ff000000", "ff000000", "ff000000", "ff000000", "ff000000", "ff000000", "ff000000", "ff000000", "ffffffff", "ffffffff");
			applyGeometry("15", "5", "10", "3", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "25":
			applyColors("ff000000", "ffffffff", "ffffffff", "ffffffff", "ffffffff", "ffffffff", "ffffffff", "ffffffff", "ffffffff", "ffffffff", "ff000000", "ff000000");
			applyGeometry("15", "5", "10", "3", "0", "1", "0.5", "0.5", "2");
			break;
			
			case "3":
			loadCustomThemeColors(context, sf);
			break;
		}
		
		if (applyDefaultGeometry) {
			applyGeometry("12", "4", "6", "2", "0", "1", "0.5", "0.5", "2");
		}
		
		loadExtraPreferences(sf, context);
		reload();
	}
	
	private void loadCustomThemeColors(Context context, SharedPreferences sf) {
		String[] bgKeys = {"bg", "bbg", "rbg"};
		for (String key : bgKeys) {
			dataHolder.setData(key, processColor(sf.getString(key, ""), true, context));
		}
		
		String[] textKeys = {"t", "tf", "add", "h", "bt", "rbt", "cb", "tpc", "tc"};
		for (String key : textKeys) {
			dataHolder.setData(key, processColor(sf.getString(key, ""), false, context));
		}
		
		String[] directKeys = {
			"rou", "rrou", "srou", "rsrou", "grad", "rgrad", "bgrad",
			"rad", "rrad", "brad", "x", "rx", "bx", "y", "ry", "by",
			"strcou", "rstrcou", "bstrcou", "tm", "rtm", "btm"
		};
		for (String key : directKeys) {
			dataHolder.setData(key, sf.getString(key, ""));
		}
	}
	
	private void loadExtraPreferences(SharedPreferences sf, Context context) {
		String[] extraKeys = {
			"listanimp", "listanimi", "bpanimp", "bpanimi", "spanimp", "spanimi",
			"don", "dblurx", "dblury", "drred", "drgreen", "drblue", 
			"dsatur", "dvred", "dvgreen", "dvblue", "dvalp"
		};
		for (String key : extraKeys) {
			dataHolder.setData(key, sf.getString(key, ""));
		}
		
		dataHolder.setData("resol", sf.getString("resol", "500"));
		
		boolean saver = BatterySaverChecker.isBatterySaverOn(context);
		dataHolder.setData("batsav", saver ? "1" : sf.getString("batsav", "0"));
	}
	
	void stripe(int[] colorArray, int r) {
		int sharp = (r == 2 ? btm : r == 1 ? rtm : tm);
		int stri = r == 2 ? bstripec : r == 1 ? rstripec : stripec;
		stripeCount = Math.max(stri, 2);
		
		if (sharp != 0) {
			colors = new int[stripeCount * sharp];
			for (int i = 0; i < stripeCount * sharp; i++) {
				colors[i] = colorArray[(i / sharp) % colorArray.length];
			}
		} else {
			colors = new int[stri];
			Random rnd = new Random();
			for (int i = 0; i < stri; i++) {
				colors[i] = colorArray[rnd.nextInt(colorArray.length)];
			}
		}
	}
	
	private boolean setCachedBG(View v, int r) {
		String cacheKey = (r == 2) ? "bg" : (r == 1 ? "rbg" : "bbg");
		BitmapDrawable bit = BitmapHolder.getBitmapDrawable(cacheKey);
		if (bit == null) return false;
		bit.setTargetDensity(1);
		v.setBackground(bit);
		v.invalidate();
		return true;
	}
	
	private BitmapDrawable create1x1Drawable(int color, Context context) {
		Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
		bitmap.setPixel(0, 0, color);
		return new BitmapDrawable(context.getResources(), bitmap);
	}
	
	private int blendWithBackground(int buttonAvgColor, int bgAvgColor) {
		int pk = (buttonAvgColor >>> 24) & 0xFF;
		int kR = (buttonAvgColor >> 16) & 0xFF;
		int kG = (buttonAvgColor >> 8) & 0xFF;
		int kB = buttonAvgColor & 0xFF;
		
		int fR = (bgAvgColor >> 16) & 0xFF;
		int fG = (bgAvgColor >> 8) & 0xFF;
		int fB = bgAvgColor & 0xFF;
		
		int finalR = (kR * pk + fR * (255 - pk)) / 255;
		int finalG = (kG * pk + fG * (255 - pk)) / 255;
		int finalB = (kB * pk + fB * (255 - pk)) / 255;
		
		return 0xFF000000 | (finalR << 16) | (finalG << 8) | finalB;
	}
	
	public void setBG(View v, int r, boolean s) {
		if (v != null && v.getContext() != null) {
			this.appContext = v.getContext().getApplicationContext();
		}
		
		int[] colorArray = r == 2 ? bg : (r == 1 || r == 3) ? rcolor1 : color1;
		
		if (dataHolder.getData("batsav") != null && dataHolder.getData("batsav").equals("0")) {
			if (setCachedBG(v, r)) return;
			try {
				int g = r == 2 ? bgrad : r == 1 ? rgrad : grad;
				float gradientX = r == 2 ? bgradientCenterX : r == 1 ? rgradientCenterX : gradientCenterX;
				float gradientY = r == 2 ? bgradientCenterY : r == 1 ? rgradientCenterY : gradientCenterY;
				float gradientR = r == 2 ? bgradientRadius : r == 1 ? rgradientRadius : gradientRadius;
				
				stripe(colorArray, r);
				Drawable drawable = null;
				
				double re = resol / 500.0;
				if (r != 2) {
					re /= 2.0;
					if (s) re /= 5.0;
				}
				re = Math.max(0.3, Math.min(re, 4.0));
				
				switch (g) {
					case 0: case 1: case 2: case 3:
					GradientDrawable.Orientation[] orientations = {
						GradientDrawable.Orientation.TOP_BOTTOM, GradientDrawable.Orientation.LEFT_RIGHT,
						GradientDrawable.Orientation.TL_BR, GradientDrawable.Orientation.TR_BL
					};
					drawable = new GradientDrawable(orientations[g], colors);
					break;
					case 4:
					GradientDrawable radialDrawable = new GradientDrawable();
					radialDrawable.setShape(GradientDrawable.RECTANGLE);
					radialDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
					radialDrawable.setColors(colors);
					radialDrawable.setGradientCenter(gradientX, gradientY);
					radialDrawable.setGradientRadius(Math.max(400 * gradientR, gradientR * Resources.getSystem().getDisplayMetrics().density * 100));
					drawable = radialDrawable;
					break;
					case 5:
					GradientDrawable sweepDrawable = new GradientDrawable();
					sweepDrawable.setShape(GradientDrawable.RECTANGLE);
					sweepDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
					sweepDrawable.setColors(colors);
					sweepDrawable.setGradientCenter(gradientX, gradientY);
					drawable = sweepDrawable;
					break;
					case 6: case 7:
					drawable = drawCheckerboard(v, re, gradientX, gradientY, r, g == 6);
					break;
					case 8:
					drawable = drawIsoscelesTriangles(v, re, gradientX, r);
					break;
					case 9:
					drawable = drawRightTriangles(v, re, gradientX, r);
					break;
					case 10:
					drawable = drawHexagons(v, re, gradientX, r);
					break;
					case 11:
					drawable = drawDragonScales(v, re, gradientX, r);
					break;
					case 12:
					drawable = drawRandomShapes(v, re, gradientX, gradientY, gradientR, r);
					break;
					default:
					drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colorArray);
					break;
				}
				
				if (drawable instanceof GradientDrawable) {
					float cornerRadius = (r == 2 ? 0 : (r == 1 ? (s ? rsroundness : rroundness) : (s ? sroundness : roundness))) * Resources.getSystem().getDisplayMetrics().density;
					((GradientDrawable) drawable).setCornerRadius(cornerRadius);
					v.setBackground(drawable);
				} else if (drawable != null) {
					BitmapDrawable ddrawable = (BitmapDrawable) drawable;
					ddrawable.setTargetDensity(1);
					v.setBackground(ddrawable);
					
					String cacheKey = (r == 2) ? "bg" : ((r == 1 || r == 3) ? "rbg" : "bbg");
					if (BitmapHolder.getBitmapDrawable(cacheKey) == null) {
						BitmapHolder.putBitmapDrawable(cacheKey, ddrawable);
					}
					v.invalidate();
				}
			} catch (Exception ignored) {}
		} else {
			int avgColor = avgcol(colorArray);
			
			if (r != 2) {
				int mainBgColor = avgcol(bg);
				avgColor = blendWithBackground(avgColor, mainBgColor);
			} else {
				avgColor = 0xFF000000 | (avgColor & 0xFFFFFF);
			}
			
			BitmapDrawable onePixelDrawable = create1x1Drawable(avgColor, v.getContext());
			String cacheKey = (r == 2) ? "bg" : ((r == 1 || r == 3) ? "rbg" : "bbg");
			
			if (BitmapHolder.getBitmapDrawable(cacheKey) == null) {
				BitmapHolder.putBitmapDrawable(cacheKey, onePixelDrawable);
			}
			
			v.setBackgroundColor(avgColor);
		}
	}
	
	private void setupCanvasBg(Canvas canvas, int r) {
		if (r == 2) {
			canvas.drawColor(ThemeManager.getLight() ? Color.WHITE : Color.BLACK);
		}
	}
	
	private Paint createPatternPaint() {
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);
		p.setFlags(0);
		return p;
	}
	
	private Drawable drawCheckerboard(View v, double re, float gradientX, float gradientY, int r, boolean isDiagonal) {
		int size = Math.max((int) ((gradientX + 2.2) * 20d * re), 3);
		int width = (int) (500 * re), height = r == 2 ? (int) (500 * re) : (int) (200 * re);
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setupCanvasBg(canvas, r);
		
		Paint paint = createPatternPaint();
		float cornerRadius = (float) ((gradientY + 2f) * 10f * Resources.getSystem().getDisplayMetrics().density * re);
		RectF rect = new RectF();
		int colorIndex = 0;
		
		for (int y = 0; y < height; y += size) {
			for (int x = 0; x < width; x += size) {
				colorIndex = isDiagonal ? ((x / size) + (y / size)) : colorIndex + 1;
				paint.setColor(colors[colorIndex % colors.length]);
				rect.set(x, y, x + size, y + size);
				canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
			}
		}
		return new BitmapDrawable(v.getResources(), bitmap);
	}
	
	private Drawable drawIsoscelesTriangles(View v, double re, float gradientX, int r) {
		int size = Math.max((int) ((gradientX + 2.2) * 20d * re), 3);
		int width = (int) (500 * re), height = r == 2 ? (int) (500 * re) : (int) (200 * re);
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setupCanvasBg(canvas, r);
		Paint paint = createPatternPaint();
		
		int colorIndex = 0;
		for (int y = 0; y < height; y += size) {
			for (int x = -size / 2; x < width; x += size / 2) {
				paint.setColor(colors[++colorIndex % colors.length]);
				Path path = new Path();
				if (colorIndex % 2 == 0) {
					path.moveTo(x, y); path.lineTo(x + size, y); path.lineTo(x + size / 2f, y + size);
				} else {
					path.moveTo(x, y + size); path.lineTo(x + size, y + size); path.lineTo(x + size / 2f, y);
				}
				path.close();
				canvas.drawPath(path, paint);
			}
		}
		return new BitmapDrawable(v.getResources(), bitmap);
	}
	
	private Drawable drawRightTriangles(View v, double re, float gradientX, int r) {
		int size = Math.max((int) ((gradientX + 2.2) * 20d * re), 3);
		int width = (int) (500 * re), height = r == 2 ? (int) (500 * re) : (int) (200 * re);
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setupCanvasBg(canvas, r);
		Paint paint = createPatternPaint();
		
		int colorIndex = 0;
		for (int y = 0; y < height; y += size) {
			for (int x = -size / 2; x < width; x += size / 2) {
				paint.setColor(colors[++colorIndex % colors.length]);
				int triangleX = (x / (size / 2)) % 2 == 0 ? x : x - size / 2;
				Path path = new Path();
				if ((x / (size / 2) + y / size) % 2 == 0) {
					path.moveTo(triangleX, y); path.lineTo(triangleX + size, y); path.lineTo(triangleX + size, y + size);
				} else {
					path.moveTo(triangleX, y + size); path.lineTo(triangleX + size, y + size); path.lineTo(triangleX, y);
				}
				path.close();
				canvas.drawPath(path, paint);
			}
		}
		return new BitmapDrawable(v.getResources(), bitmap);
	}
	
	private Drawable drawHexagons(View v, double re, float gradientX, int r) {
		int size = Math.max((int) ((gradientX + 2.2) * 20d * re), 3);
		int width = (int) (500 * re), height = r == 2 ? (int) (500 * re) : (int) (200 * re);
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setupCanvasBg(canvas, r);
		Paint paint = createPatternPaint();
		
		int colorIndex = 0;
		for (int y = -size / 2; y < height; y += size / 2) {
			boolean isEvenRow = ((y + size / 2) / (size / 2)) % 2 == 0;
			for (int x = isEvenRow ? (int) (-size * 0.75) : 0; x < width; x += (int) (size * 1.5)) {
				paint.setColor(colors[++colorIndex % colors.length]);
				Path path = new Path();
				path.moveTo(x, y + size / 2f);
				path.lineTo(x + size / 4f, y + size);
				path.lineTo(x + size * 0.75f, y + size);
				path.lineTo(x + size, y + size / 2f);
				path.lineTo(x + size * 0.75f, y);
				path.lineTo(x + size / 4f, y);
				path.close();
				canvas.drawPath(path, paint);
			}
		}
		return new BitmapDrawable(v.getResources(), bitmap);
	}
	
	private Drawable drawDragonScales(View v, double re, float gradientX, int r) {
		int baseSize = (int) ((gradientX + 2.2) * 20d);
		int size = Math.max(baseSize, 3);
		int width = (int) (500 * re), height = r == 2 ? (int) (500 * re) : (int) (200 * re);
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setupCanvasBg(canvas, r);
		Paint paint = createPatternPaint();
		
		int colorIndex = 0;
		float re2 = (float) re;
		for (int baseY = 0; baseY * re < height; baseY += size) {
			for (int baseX = -size / 2; baseX * re < width; baseX += size) {
				paint.setColor(colors[++colorIndex % colors.length]);
				Path path = new Path();
				path.moveTo(baseX * re2, (baseY / 2f * size) * re2);
				path.lineTo((baseX / 4f * size + size) * re2, (baseY + size) * re2);
				path.lineTo((baseX / 4f * 3) * re2, (baseY + size) * re2);
				path.lineTo(baseX * re2, (baseY + size / 2f) * re2);
				path.lineTo((baseX / 4f * 3) * re2, baseY * re2);
				path.close();
				canvas.drawPath(path, paint);
			}
		}
		return new BitmapDrawable(v.getResources(), bitmap);
	}
	
	private Drawable drawRandomShapes(View v, double re, float gradientX, float gradientY, float gradientR, int r) {
		int size = Math.max((int) ((gradientX + 2.5) * 100d * re), 3);
		int den = (int) (((gradientY + 2.5) / 4.0) * size);
		if (den <= 0) den = 1;
		int width = (int) (500 * re), height = r == 2 ? (int) (500 * re) : (int) (200 * re);
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setupCanvasBg(canvas, r);
		Paint paint = createPatternPaint();
		Random rnd = new Random();
		
		int colorIndex = 0;
		int w = (int) (gradientR * 10.0);
		for (int y = 0; y < height; y += den) {
			for (int x = -size / 2; x < width; x += den) {
				paint.setColor(colors[++colorIndex % colors.length]);
				Path path = new Path();
				int rr = 3 + (w > 0 ? rnd.nextInt(w) : 0);
				path.moveTo(x + rnd.nextInt(size), y + rnd.nextInt(size));
				for (int i = 0; i < rr; i++) {
					path.lineTo(x + rnd.nextInt(size), y + rnd.nextInt(size));
				}
				path.close();
				canvas.drawPath(path, paint);
			}
		}
		return new BitmapDrawable(v.getResources(), bitmap);
	}
	
	// -----------------------------------------------------
	// ЄДИНИЙ МЕТОД ДЛЯ ОБРОБКИ 8-СИМВОЛЬНОГО ТОКЕНА КОЛЬОРУ
	// -----------------------------------------------------
	
	public int parseColorTokenToInt(Context context, String colorString) {
		if (colorString == null || colorString.isEmpty()) return Color.BLACK;
		
		// Якщо це Monet-маркер (наприклад: m_n1010, m_n1050, m_n1100, m_a1050)
		if (colorString.startsWith("m_")) {
			
			// 1. Нормалізуємо назву: видаляємо зайві 'm_' та перетворюємо m_n1050 -> m_n1_50
			String cleanName = colorString.replaceFirst("^m_+", "m_");
			
			// Якщо назва не містить підкреслення перед відтінком (наприклад, m_n1050 чи m_a1100)
			if (!cleanName.matches("^m_[a-z]\\d+_\\d+$")) {
				// Регулярка розбиває m_n1050 на: group(1)="m_n1", group(2)="050" або "010"
				cleanName = cleanName.replaceAll("^m_([a-z]\\d+)(\\d{3})$", "m_$1_$2");
				
				// Для двозначних відтінків (наприклад 010 -> 10, 050 -> 50)
				cleanName = cleanName.replace("_0", "_"); 
			}
			
			// 2. Шукаємо ID ресурсу в R.color (знайде m_n1_10, m_n1_50, m_n1_100 і т.д.)
			int resId = context.getResources().getIdentifier(cleanName, "color", context.getPackageName());
			
			if (resId != 0) {
				try {
					return getColorFromRes(context, resId);
				} catch (Exception e) {
					// fallback
				}
			}
		}
		
		// Якщо це звичайний HEX (#FF0000)
		try {
			String hex = colorString.startsWith("#") ? colorString : "#" + colorString;
			return Color.parseColor(hex);
		} catch (Exception e) {
			return Color.BLACK;
		}
	}
	
	public String parseColorTokenToHex(Context context, String token8) {
		int colorInt = parseColorTokenToInt(context, token8);
		return String.format("%08X", colorInt);
	}
	
	public String processColor(String colorString, boolean isBg, Context context) {
		if (colorString == null || colorString.isEmpty()) return "ff000000";
		
		// Якщо це Monet-маркер (наприклад: m__a1010, m_n1050, m_a1100)
		if (colorString.startsWith("m_")) {
			try {
				// 1. Отримуємо типом категорії (n1, n2, a1, a2, a3)
				// Беремо 2 символи після останньої буквенної категорії (наприклад "a1")
				String clean = colorString.replaceFirst("^m_+", ""); // "a1010" або "n1050"
				String type = clean.substring(0, 2); // "a1", "n1" і т.д.
				
				// 2. Беремо останні цифри і математично перетворюємо в int (050 -> 50, 010 -> 10, 100 -> 100)
				int shade = Integer.parseInt(clean.substring(2)); 
				
				// 3. Формуємо назву XML-ресурсу: "m_a1_10", "m_n1_50", "m_a1_100"
				String resName = "m_" + type + "_" + shade;
				
				int resId = context.getResources().getIdentifier(resName, "color", context.getPackageName());
				if (resId != 0) {
					int colorInt = getColorFromRes(context, resId);
					return String.format("%08x", colorInt);
				}
			} catch (Exception e) {
                return "ffff0000";
				// Якщо щось пішло не так при парсингу — переходимо до повернення вихідного рядка
			}
		}
		
		// Якщо це звичайний HEX
		return colorString;
	}
	
	
	// Розбиваємо градієнти (наприклад: "FF00897Bm__a1200") на масив int[]
	private int[] parseColorArray(String colorString) {
		if (colorString == null || colorString.trim().isEmpty()) {
			return new int[]{0xFF00897B};
		}
		
		String cleaned = colorString.trim();
		int length = cleaned.length();
		
		// Якщо рядок не кратний 8, обробляємо його як 1 колір
		if (length % 8 != 0) {
			return new int[]{ parseColorTokenToInt(appContext, cleaned) };
		}
		
		int count = length / 8;
		int[] c = new int[count];
		for (int i = 0; i < count; i++) {
			String token8 = cleaned.substring(i * 8, (i + 1) * 8);
			c[i] = parseColorTokenToInt(appContext, token8);
		}
		return c;
	}
	
	// Сумісність для статичного виклику
	public static int parseMonetToken(Context context, String token) {
		return getInstance().parseColorTokenToInt(context, token);
	}
	
	private static int getColorFromRes(Context context, int resId) {
		try {
			if (WTF.SDK_INT() >= 23) {
				Resources.Theme theme = (context != null) ? context.getTheme() : null;
				return context.getResources().getColor(resId, theme);
			} else {
				return context.getResources().getColor(resId);
			}
		} catch (Exception e) {
			return 0xffff0000;
		}
	}
}
