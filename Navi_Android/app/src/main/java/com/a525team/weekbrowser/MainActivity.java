package com.a525team.weekbrowser;

import com.a525team.weekbrowser.navi.ui.NaviSheetView;
import com.a525team.weekbrowser.navi.ui.NaviSettingsDialog;
import com.a525team.weekbrowser.navi.core.NaviDataStore;
import com.a525team.weekbrowser.navi.core.NaviBillingManager;

import android.animation.*;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.a525team.weekbrowser.SkinAutoCompleteTextView;
import com.a525team.weekbrowser.SkinButton;
import com.a525team.weekbrowser.SkinCheckBox;
import com.a525team.weekbrowser.SkinSeekBar;
import com.a525team.weekbrowser.SkinTextView;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import android.net.Uri;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.IOException;
import android.webkit.URLUtil;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.ValueCallback;
//import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.content.pm.ActivityInfo;
import android.view.inputmethod.EditorInfo;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import android.view.inputmethod.InputMethodManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.concurrent.atomic.AtomicBoolean;
import android.widget.FrameLayout;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import android.os.Debug;
import android.app.ActivityManager.MemoryInfo;
import android.content.ClipData;
import java.util.LinkedHashSet;
//import com.a525team.weekbrowser.MyWebViewClient;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

//import android.content.ComponentName;
//import android.content.Context;
//import android.content.pm.PackageManager;
import android.webkit.WebViewDatabase;
import android.webkit.WebView.HitTestResult;
import android.app.ActivityManager.MemoryInfo;
import android.net.http.SslCertificate;
import android.util.Log;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private String URL = "";
	private String info = "";
	private boolean cache = false;
	private boolean Internet = false;
	private double curnum = 0;
	private double allnum = 0;
	private boolean focused = false;
	private boolean selected = false;
	private String url = "";
	private boolean is1window = false;
	private boolean is1touch = false;
	private boolean issu = false;
	private String tempurl = "";
	private boolean isshowpan = false;
	private boolean ismovedpanels = false;
	private boolean isshowed = false;
	private boolean swipetorefresh = false;
	private boolean local1 = false;
	private boolean local2 = false;
	private boolean ALLOWSWIPE = false;
	private boolean isnotchfullscreen = false;
	private String designbmlcache = "";
	private double adsblockedd = 0;
	private String themebml = "";
	int Tt;
	int ATt;
	private Timer times;
	private TimerTask timerTask;
	private double load = 0;
	int usedMemoryMB;
	private boolean holdsearchtext = false;
	private boolean recentlyreleased = false;
	ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
	private boolean Foreground = false;
	private String prevCharSeq = "";
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);;
	private boolean posshow = false;
	private JavaScriptInterface jsInterface;
	private boolean nf = false;
	private boolean notFirst = false;
	private double wwpos = 0;
	private Runnable debounceRunnable;
	private final Handler debounceHandler = new Handler(Looper.getMainLooper());
	BarMan bm;
	double totalMem;
	byte pSslBtn = (byte)0xff;
	int winholdsize = 30;
	HashMap<String, Long> urlHistory = new HashMap<>();
	int buttoncolors;
	long time = 0L;
	long ticks;
	
	private ArrayList<HashMap<String, Object>> history = new ArrayList<>();
	private ArrayList<String> skin = new ArrayList<>();
	private ArrayList<String> srcsug = new ArrayList<>();
	private ArrayList<String> languag = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> tab = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> sitepermis = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> windowpos = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> srhbuf = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> rpl = new ArrayList<>();
	
	private LinearLayout linear2;
	private SkinTextView textview1;
	private LinearLayout windowsmanager;
	private TextView ramuse;
	private RelativeLayout tabscontainer;
	private GridView srhhis;
	private LinearLayout linear14;
	private ScrollView settingspanel;
	private HorizontalScrollView nav;
	private LinearLayout searchpan;
	private LinearLayout linear1;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear9;
	private SkinButton widthm1;
	private SkinButton widthp1;
	private SkinButton heightm1;
	private SkinButton heightp1;
	private GridView tabscont;
	private LinearLayout linear15;
	private SkinButton add;
	private SkinButton closeall;
	private LinearLayout linear3;
	private LinearLayout linear12;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private SkinCheckBox iframe;
	private SkinCheckBox swipetore;
	private SkinTextView textview5;
	private SkinSeekBar dp;
	private SkinTextView textview3;
	private SkinSeekBar font;
	private SkinTextView textview2;
	private SkinSeekBar seekbar1;
	private SkinTextView title;
	private SkinButton copyallurl;
	private SkinButton multilink;
	private SkinButton otherapp;
	private SkinCheckBox themepage;
	private SkinCheckBox js;
	private SkinCheckBox pc;
	private SkinCheckBox incognito;
	private SkinCheckBox image;
	private SkinCheckBox blockad;
	private LinearLayout linear5;
	private SkinAutoCompleteTextView search;
	private SkinButton sup;
	private SkinTextView num;
	private SkinButton sdown;
	private SkinButton ssl;
	private SkinAutoCompleteTextView autocomplete1;
	private SkinButton hide;
	private SkinButton refresh;
	private SkinButton button1;
	private SkinButton openpanel;
	private SkinButton button3;
	private LinearLayout linear16;
	private SkinButton closeeditor;
	private SkinButton copyurl;
	private SkinButton pasteurl;
	private SkinButton pastegourl;
	private SkinButton selall;
	private SkinButton cururl;
	private SkinButton forcesearch;
	private SkinButton forceurl;
	private SkinButton share;
	private SkinButton pos1;
	private SkinButton pos2;
	private SkinButton pos3;
	
	private TimerTask timer;
	private AlertDialog.Builder dlg;
	private Intent intent = new Intent();
	private Intent intent2 = new Intent();
	private Intent thema = new Intent();
	private ObjectAnimator anim = new ObjectAnimator();
	private ObjectAnimator position = new ObjectAnimator();
	private ObjectAnimator tabanim = new ObjectAnimator();
	private AlertDialog.Builder dial;
	private ObjectAnimator anim2 = new ObjectAnimator();
	private ObjectAnimator srhanim = new ObjectAnimator();
	private AlertDialog.Builder shareurl;
	private Intent shareurll = new Intent();
	private AlertDialog.Builder iframecatcher;
	private AlertDialog.Builder blockads;
	private AlertDialog.Builder this_is_not_a_toast_im_stupid_sorry;
	private SharedPreferences sf;
	private ObjectAnimator searchpan2 = new ObjectAnimator();
	private SharedPreferences sf2;
	private AlertDialog.Builder exit;
	private AlertDialog.Builder copyurl2;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		NaviDataStore naviDataStore = new NaviDataStore(this);
		naviDataStore.clearLog();
		naviDataStore.clearProcessed();
		ThemeManager.applyTheme(this);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		totalMem = _totalMem();
		if (totalMem >= 900)
		warmupWebView(); //Прогрів, якщо ≥1 ГБ оперативки (запрошую трохи менше, оскільки не весь 1 ГБ буде доступним)
		
		SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		_ifex();
		if (!sf.contains("lang")) {
			intent.setClass(getApplicationContext(), GrantActivity.class);
			finishAffinity();
			startActivity(intent);
		} else {
			
		}
		linear2 = findViewById(R.id.linear2);
		textview1 = findViewById(R.id.textview1);
		windowsmanager = findViewById(R.id.windowsmanager);
		ramuse = findViewById(R.id.ramuse);
		tabscontainer = findViewById(R.id.tabscontainer);
		srhhis = findViewById(R.id.srhhis);
		linear14 = findViewById(R.id.linear14);
		settingspanel = findViewById(R.id.settingspanel);
		nav = findViewById(R.id.nav);
		searchpan = findViewById(R.id.searchpan);
		linear1 = findViewById(R.id.linear1);
		hscroll1 = findViewById(R.id.hscroll1);
		linear9 = findViewById(R.id.linear9);
		widthm1 = findViewById(R.id.widthm1);
		widthp1 = findViewById(R.id.widthp1);
		heightm1 = findViewById(R.id.heightm1);
		heightp1 = findViewById(R.id.heightp1);
		tabscont = findViewById(R.id.tabscont);
		linear15 = findViewById(R.id.linear15);
		add = findViewById(R.id.add);
		closeall = findViewById(R.id.closeall);
		linear3 = findViewById(R.id.linear3);
		linear12 = findViewById(R.id.linear12);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		iframe = findViewById(R.id.iframe);
		swipetore = findViewById(R.id.swipetore);
		textview5 = findViewById(R.id.textview5);
		dp = findViewById(R.id.dp);
		textview3 = findViewById(R.id.textview3);
		font = findViewById(R.id.font);
		textview2 = findViewById(R.id.textview2);
		seekbar1 = findViewById(R.id.seekbar1);
		title = findViewById(R.id.title);
		copyallurl = findViewById(R.id.copyallurl);
		multilink = findViewById(R.id.multilink);
		otherapp = findViewById(R.id.otherapp);
		themepage = findViewById(R.id.themepage);
		js = findViewById(R.id.js);
		pc = findViewById(R.id.pc);
		incognito = findViewById(R.id.incognito);
		image = findViewById(R.id.image);
		blockad = findViewById(R.id.blockad);
		linear5 = findViewById(R.id.linear5);
		search = findViewById(R.id.search);
		sup = findViewById(R.id.sup);
		num = findViewById(R.id.num);
		sdown = findViewById(R.id.sdown);
		ssl = findViewById(R.id.ssl);
		autocomplete1 = findViewById(R.id.autocomplete1);
		ImageButton addressClearButton = findViewById(R.id.addressClearButton);
		if (addressClearButton != null) {
			addressClearButton.setOnClickListener(v -> {
				autocomplete1.setText("");
				autocomplete1.requestFocus();
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputMethodManager != null) inputMethodManager.showSoftInput(autocomplete1, InputMethodManager.SHOW_IMPLICIT);
			});
		}
		hide = findViewById(R.id.hide);
		refresh = findViewById(R.id.refresh);
		button1 = findViewById(R.id.button1);
		openpanel = findViewById(R.id.openpanel);
		button3 = findViewById(R.id.button3);
		android.widget.FrameLayout naviSheetContainer = findViewById(R.id.naviSheetContainer);
		NaviSheetView naviSheetView = new NaviSheetView(this);
		naviSheetContainer.addView(naviSheetView, new android.widget.FrameLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT
		));

		SkinButton buttonNaviPanel = findViewById(R.id.buttonNaviPanel);
		naviSheetView.setAutomationStateListener(running -> {
			if (buttonNaviPanel == null) return;
			android.graphics.drawable.Drawable[] icons = buttonNaviPanel.getCompoundDrawablesRelative();
			if (icons.length == 0 || icons[0] == null) return;
			android.graphics.drawable.Drawable icon = icons[0].mutate();
			icon.setTint(android.graphics.Color.parseColor(running ? "#4CAF50" : "#F44336"));
			buttonNaviPanel.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
		});
		Runnable ensureSessionBound = () -> {
			String tabTag = getCurrentTabTag();
			WebView activeWv = tabTag != null ? ResourceManager.getInstance().getWebViewForTab(tabTag) : null;
			if (activeWv != null) {
				naviSheetView.setBrowserSession(new com.a525team.weekbrowser.navi.browser.WeekBrowserWebViewAdapter(tabTag, activeWv));
			}
		};
		naviSheetView.setBrowserSessionResolver(new NaviSheetView.BrowserSessionResolver() {
			@Override
			public String getActiveTabId() {
				return getCurrentTabTag();
			}

			@Override
			public com.a525team.weekbrowser.navi.browser.NaviBrowserSession resolve(String tabTag) {
				WebView activeWv = ResourceManager.getInstance().getWebViewForTab(tabTag);
				return activeWv != null
						? new com.a525team.weekbrowser.navi.browser.WeekBrowserWebViewAdapter(tabTag, activeWv)
						: null;
			}
		});
		ensureSessionBound.run();

		View.OnClickListener toggleSheetListener = view -> {
			ensureSessionBound.run();
			if (naviSheetContainer.getVisibility() == View.VISIBLE) {
				naviSheetContainer.setVisibility(View.GONE);
			} else {
				naviSheetContainer.setVisibility(View.VISIBLE);
				naviSheetContainer.bringToFront();
			}
		};

		if (buttonNaviPanel != null) {
			buttonNaviPanel.setText("");
			buttonNaviPanel.setContentDescription("Painel de automação");
			buttonNaviPanel.setOnClickListener(toggleSheetListener);
		}
		SkinButton naviPanelButton = findViewById(R.id.naviPanel);
		if (naviPanelButton != null) {
			naviPanelButton.setOnClickListener(toggleSheetListener);
		}

		SkinButton buttonNavBack = findViewById(R.id.buttonNavBack);
		if (buttonNavBack != null) {
			buttonNavBack.setContentDescription("Voltar");
			buttonNavBack.setOnClickListener(v -> {
				String tabTag = getCurrentTabTag();
				WebView activeWv = tabTag != null ? ResourceManager.getInstance().getWebViewForTab(tabTag) : null;
				if (activeWv != null && activeWv.canGoBack()) {
					activeWv.goBack();
				}
			});
		}

		SkinButton buttonBookmarks = findViewById(R.id.buttonBookmarks);
		if (buttonBookmarks != null) {
			buttonBookmarks.setContentDescription(getString(R.string.bookmarks));
			buttonBookmarks.setOnClickListener(v -> _webViewActions(-1, ""));
		}
		SkinButton buttonTabs = findViewById(R.id.buttonTabs);
		if (buttonTabs != null) {
			buttonTabs.setContentDescription(getString(R.string.tabs));
			buttonTabs.setOnClickListener(v -> _webViewActions(-5, ""));
		}

		if (openpanel != null) openpanel.setVisibility(View.GONE);
		if (ssl != null) ssl.setVisibility(View.GONE);
		if (hide != null) hide.setVisibility(View.GONE);
		if (refresh != null) refresh.setVisibility(View.GONE);
		if (button1 != null) button1.setVisibility(View.GONE);
		if (button3 != null) {
			button3.setVisibility(View.VISIBLE);
			button3.setText("");
			button3.setContentDescription("Configurações da automação");
		}
		linear16 = findViewById(R.id.linear16);
		closeeditor = findViewById(R.id.closeeditor);
		copyurl = findViewById(R.id.copyurl);
		pasteurl = findViewById(R.id.pasteurl);
		pastegourl = findViewById(R.id.pastegourl);
		selall = findViewById(R.id.selall);
		cururl = findViewById(R.id.cururl);
		forcesearch = findViewById(R.id.forcesearch);
		forceurl = findViewById(R.id.forceurl);
		share = findViewById(R.id.share);
		pos1 = findViewById(R.id.pos1);
		pos2 = findViewById(R.id.pos2);
		pos3 = findViewById(R.id.pos3);
		dlg = new AlertDialog.Builder(this);
		dial = new AlertDialog.Builder(this);
		shareurl = new AlertDialog.Builder(this);
		iframecatcher = new AlertDialog.Builder(this);
		blockads = new AlertDialog.Builder(this);
		this_is_not_a_toast_im_stupid_sorry = new AlertDialog.Builder(this);
		sf = getSharedPreferences("sf", Activity.MODE_PRIVATE);
		sf2 = getSharedPreferences("code", Activity.MODE_PRIVATE);
		exit = new AlertDialog.Builder(this);
		copyurl2 = new AlertDialog.Builder(this);
		
		srhhis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("searchengine", "").equals("0")) {
					_webViewActions(10, "https://www.google.com/search?q=".concat(srhbuf.get((int)_position).get("s").toString()));
				}
				if (sf.getString("searchengine", "").equals("1")) {
					_webViewActions(10, "https://www.bing.com/search?q=".concat(srhbuf.get((int)_position).get("s").toString()));
				}
				if (sf.getString("searchengine", "").equals("2")) {
					_webViewActions(10, "https://search.yahoo.com/search?p=".concat(srhbuf.get((int)_position).get("s").toString()));
				}
				if (sf.getString("searchengine", "").equals("3")) {
					_webViewActions(10, "https://duckduckgo.com/?t=h_&q=".concat(srhbuf.get((int)_position).get("s").toString()));
				}
			}
		});
		
		srhhis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				srcsug.remove((int)((srhbuf.size() - 1) - _position));
				FileUtil.writeFile(MainActivity.this.getFilesDir().getPath() + "/srhhis.json", JsonUtils.toJson(srcsug));
				_srhupd();
				return true;
			}
		});
		
		widthm1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				try{
					if (Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("w").toString().replace("s", "")) > 1) {
						tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("w", "s".concat(String.valueOf((long)(Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("w").toString().replace("s", "")) - 1))));
					}
				}catch(Exception e){
					tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("w", "s1");
				}
				sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
				_webViewActions(103, "");
			}
		});
		
		widthp1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				try{
					if (Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("w").toString().replace("s", "")) < 4) {
						tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("w", "s".concat(String.valueOf((long)(Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("w").toString().replace("s", "")) - -1))));
					}
				}catch(Exception e){
					tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("w", "s3");
				}
				sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
				_webViewActions(103, "");
			}
		});
		
		heightm1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				try{
					if (Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("h").toString().replace("s", "")) > 1) {
						tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("h", "s".concat(String.valueOf((long)(Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("h").toString().replace("s", "")) - 1))));
					}
				}catch(Exception e){
					tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("h", "s1");
				}
				sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
				_webViewActions(103, "");
			}
		});
		
		heightp1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				try{
					if (Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("h").toString().replace("s", "")) < 4) {
						tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("h", "s".concat(String.valueOf((long)(Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("h").toString().replace("s", "")) - -1))));
					}
				}catch(Exception e){
					tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).put("h", "s3");
				}
				sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
				_webViewActions(103, "");
			}
		});
		
		//OnTouch
		add.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				int ev = event.getAction();
				switch (ev) {
					case MotionEvent.ACTION_DOWN:
					
					final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
					notFirst = true;
					_newTab(sf.getString("firstpage", ""), true);
					
					break;
					case MotionEvent.ACTION_UP:
					
					
					
					break;
				} return true;
			}
		});
		
		//OnTouch
		closeall.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				int ev = event.getAction();
				switch (ev) {
					case MotionEvent.ACTION_DOWN:
					
					_webViewActions(-29, "");
					
					break;
					case MotionEvent.ACTION_UP:
					
					
					
					break;
				} return true;
			}
		});
		
		iframe.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				dlg.setTitle(_geturl());
				dlg.setMessage(getString(R.string.iframeexcept));
				dlg.setPositiveButton(getString(R.string.turnon), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("if", "1");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("if", "1");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				dlg.setNegativeButton(getString(R.string.turnoff), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("if", "0");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("if", "0");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				dlg.setNeutralButton(getString(R.string.exdefault), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("if", "2");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("if", "2");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				AlertDialog dddddd = dlg.create();
				RenderUtils.setupDialogDim(MainActivity.this, dddddd);
				dddddd.show();
				
				return true;
			}
		});
		
		iframe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("iframeblock", "").equals("0")) {
					iframe.setChecked(true);
					sf.edit().putString("iframeblock", "1").commit();
				} else {
					iframe.setChecked(false);
					sf.edit().putString("iframeblock", "0").commit();
				}
			}
		});
		
		swipetore.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				dlg.setTitle(_geturl());
				dlg.setMessage(getString(R.string.swipeexcept));
				dlg.setPositiveButton(getString(R.string.turnon), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("sr", "1");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("sr", "1");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
						_Reopentab();
					}
				});
				dlg.setNegativeButton(getString(R.string.turnoff), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("sr", "0");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("sr", "0");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
						_Reopentab();
					}
				});
				dlg.setNeutralButton(getString(R.string.exdefault), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("sr", "2");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("sr", "2");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
						_Reopentab();
					}
				});
				AlertDialog dddddd = dlg.create();
				RenderUtils.setupDialogDim(MainActivity.this, dddddd);
				dddddd.show();
				
				return true;
			}
		});
		
		swipetore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("swipetore", "").equals("0")) {
					swipetore.setChecked(true);
					sf.edit().putString("swipetore", "1").commit();
				} else {
					swipetore.setChecked(false);
					sf.edit().putString("swipetore", "0").commit();
				}
				_Reopentab();
			}
		});
		
		dp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				textview5.setText(getString(R.string.qs_uisize).concat(" (").concat(String.valueOf((long)(dp.getProgress() * 10)).concat(")")));
				sf.edit().putString("dp", String.valueOf((long)(_progressValue))).commit();
				if (is1window) {
					_webViewActions(-27, String.valueOf((long)(_progressValue)));
				} else {
					_webViewActions(20, String.valueOf((long)(_progressValue)));
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		font.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				textview3.setText(getString(R.string.qs_fontsize).concat(" (").concat(String.valueOf((long)(_progressValue * 10)).concat("%)")));
				sf.edit().putString("fontsize", String.valueOf((long)(_progressValue))).commit();
				if (is1window) {
					// Знаходимо головний LinearLayout з id "tabscontainer"
					LinearLayout tabscontainer = findViewById(R.id.tabscontainer);
					
					// Ініціалізуємо стек для обходу
					java.util.Stack<ViewGroup> stack = new java.util.Stack<>();
					stack.push(tabscontainer);
					
					while (!stack.isEmpty()) {
						ViewGroup parent = stack.pop();
						
						// Проходимо по всіх дочірніх елементах parent
						for (int i = 0; i < parent.getChildCount(); i++) {
							View child = parent.getChildAt(i);
							
							if (child instanceof WebView) {
								// Якщо дочірній елемент є WebView, виконуємо необхідну команду
								WebView webView = (WebView) child;
								
								webView.getSettings().setTextZoom(_progressValue*10);
								
							} else if (child instanceof ViewGroup) {
								// Якщо дочірній елемент є ViewGroup (наприклад, LinearLayout), додаємо його до стеку для подальшого оброблення
								stack.push((ViewGroup) child);
							}
						}
					}
					
				} else {
					_webViewActions(21, String.valueOf((long)(_progressValue)));
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				textview2.setText(getString(R.string.qs_minfont).concat(" (").concat(String.valueOf((long)(_progressValue)).concat(")")));
				sf.edit().putString("minfont", String.valueOf((long)(_progressValue))).commit();
				if (is1window) {
					// Знаходимо головний LinearLayout з id "tabscontainer"
					LinearLayout tabscontainer = findViewById(R.id.tabscontainer);
					
					// Ініціалізуємо стек для обходу
					java.util.Stack<ViewGroup> stack = new java.util.Stack<>();
					stack.push(tabscontainer);
					
					while (!stack.isEmpty()) {
						ViewGroup parent = stack.pop();
						
						// Проходимо по всіх дочірніх елементах parent
						for (int i = 0; i < parent.getChildCount(); i++) {
							View child = parent.getChildAt(i);
							
							if (child instanceof WebView) {
								// Якщо дочірній елемент є WebView, виконуємо необхідну команду
								WebView webView = (WebView) child;
								
								webView.getSettings().setMinimumFontSize(_progressValue);
								
							} else if (child instanceof ViewGroup) {
								// Якщо дочірній елемент є ViewGroup (наприклад, LinearLayout), додаємо його до стеку для подальшого оброблення
								stack.push((ViewGroup) child);
							}
						}
					}
					
				} else {
					_webViewActions(22, String.valueOf((long)(_progressValue)));
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", title.getText().toString()));
				SketchwareUtil.showMessage(getApplicationContext(), "Скопійовано!");
			}
		});
		
		copyallurl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				copyurl2.setTitle("?");
				copyurl2.setMessage(getString(R.string.copyurldial));
				copyurl2.setPositiveButton(getString(R.string.name_url), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						StringBuilder content = new StringBuilder();
						for (int i = 0; i < (int)(tab.size()); i++) {
							content.append(tab.get(i).get("name") + "\n────────\n" + tab.get(i).get("url") + "\n━━━━━━━━\n");
						}
						((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", content.toString()));
						SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.copied));
					}
				});
				copyurl2.setNegativeButton(getString(R.string.urlonly), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						StringBuilder content = new StringBuilder();
						for (int i = 0; i < (int)(tab.size()); i++) {
							content.append(tab.get(i).get("url") + "\n━━━━━━━━\n");
						}
						((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", content.toString()));
						SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.copied));
					}
				});
				copyurl2.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				AlertDialog dddddd = copyurl2.create();
				RenderUtils.setupDialogDim(MainActivity.this, dddddd);
				dddddd.show();
				
			}
		});
		
		multilink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", dataHolder.getData("ml")));
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.copied));
			}
		});
		
		otherapp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				openInOtherApps(autocomplete1.getText().toString());
			}
		});
		
		themepage.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				dlg.setTitle(_geturl());
				dlg.setMessage(getString(R.string.colexcept));
				dlg.setPositiveButton(getString(R.string.change), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("th", "1");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("th", "1");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						
						_webViewActions(1999, "");
					}
				});
				dlg.setNegativeButton(getString(R.string.nochange), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("th", "0");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("th", "0");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						
						_webViewActions(1999, "");
					}
				});
				dlg.setNeutralButton(getString(R.string.exdefault), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("th", "2");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("th", "2");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						
						_webViewActions(1999, "");
					}
				});
				AlertDialog dddddd = dlg.create();
				RenderUtils.setupDialogDim(MainActivity.this, dddddd);
				dddddd.show();
				
				return true;
			}
		});
		
		themepage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("themepage", "").equals("0")) {
					themepage.setChecked(true);
					sf.edit().putString("themepage", "1").commit();
				} else {
					sf.edit().putString("themepage", "0").commit();
				}
			}
		});
		
		js.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				dlg.setTitle(_geturl());
				dlg.setMessage(getString(R.string.jsexcept));
				dlg.setPositiveButton(getString(R.string.turnon), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("js", "1");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("js", "1");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				dlg.setNegativeButton(getString(R.string.turnoff), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("js", "0");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("js", "0");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				dlg.setNeutralButton(getString(R.string.exdefault), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("js", "2");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("js", "2");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				AlertDialog dddddd = dlg.create();
				RenderUtils.setupDialogDim(MainActivity.this, dddddd);
				dddddd.show();
				
				return true;
			}
		});
		
		js.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("js", "").equals("0")) {
					js.setChecked(true);
					sf.edit().putString("js", "1").commit();
				} else {
					js.setChecked(false);
					sf.edit().putString("js", "0").commit();
				}
			}
		});
		
		pc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("pc", "").equals("0")) {
					pc.setChecked(true);
					sf.edit().putString("pc", "1").commit();
				} else {
					pc.setChecked(false);
					sf.edit().putString("pc", "0").commit();
				}
			}
		});
		
		incognito.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
				
				if (sf.getString("incognito", "").equals("0")) {
					incognito.setChecked(true);
					sf.edit().putString("incognito", "1").commit();
					openpanel.setText("");
					openpanel.setSkinKey("rbg", true);
					if (sf.getString("noscreen", "").equals("1")) {
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
					}
				} else {
					incognito.setChecked(false);
					sf.edit().putString("incognito", "0").commit();
					openpanel.setText("");
					getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
					openpanel.setSkinKey("bbg", true);
				}
			}
		});
		
		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("loadimages", "").equals("0")) {
					image.setChecked(true);
					sf.edit().putString("loadimages", "1").commit();
					_webViewActions(1998, "");
				} else {
					image.setChecked(false);
					sf.edit().putString("loadimages", "0").commit();
				}
			}
		});
		
		blockad.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				dlg.setTitle(_geturl());
				dlg.setMessage(getString(R.string.bladexcept).concat(" ").concat(String.valueOf((long)(adsblockedd)).concat(" ".concat(getString(R.string.ads).concat("\n"+getString(R.string.totalads)+" ".concat(sf.getString("totalads", "")))))));
				dlg.setPositiveButton(getString(R.string.turnon), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("ad", "1");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("ad", "1");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				dlg.setNegativeButton(getString(R.string.turnoff), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("ad", "0");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("ad", "0");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				dlg.setNeutralButton(getString(R.string.exdefault), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						for (int i = 0; i < (int)(sitepermis.size()); i++) {
							if (sitepermis.get((int)i).get("url").toString().equals(_geturl())) {
								sitepermis.get((int)i).put("ad", "2");
								issu = true;
								break;
							}
						}
						if (!issu) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("url", _geturl());
								sitepermis.add(_item);
							}
							sitepermis.get((int)sitepermis.size() - 1).put("ad", "2");
						}
						issu = false;
						FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", JsonUtils.toJson(sitepermis));
						_webViewActions(1999, "");
					}
				});
				AlertDialog dddddd = dlg.create();
				RenderUtils.setupDialogDim(MainActivity.this, dddddd);
				dddddd.show();
				
				return true;
			}
		});
		
		blockad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (sf.getString("blockad", "").equals("0")) {
					blockad.setChecked(true);
					sf.edit().putString("blockad", "1").commit();
				} else {
					blockad.setChecked(false);
					sf.edit().putString("blockad", "0").commit();
				}
			}
		});
		
		search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				_webViewActions(30, _charSeq);
				/*
webview1.findAll(_charSeq);
curnum = 1;
*/
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		//OnTouch
		sup.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				int ev = event.getAction();
				switch (ev) {
					case MotionEvent.ACTION_DOWN:
					
					_webViewActions(31, "");
					
					break;
					case MotionEvent.ACTION_UP:
					
					
					
					break;
				} return true;
			}
		});
		
		//OnTouch
		sdown.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				int ev = event.getAction();
				switch (ev) {
					case MotionEvent.ACTION_DOWN:
					
					_webViewActions(32, "");
					
					break;
					case MotionEvent.ACTION_UP:
					
					
					
					break;
				} return true;
			}
		});
		
		ssl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_webViewActions(6, "");
			}
		});
		
		autocomplete1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				// 🔹 Усередині onTextChanged
				prevCharSeq = _charSeq;
				
				// Скасовуємо попередню задачу
				debounceHandler.removeCallbacksAndMessages(null);
				
				// Стандартне значення (пальцем в небо)
				int delay = 500; 
				
				// Перевіряємо на null БЕЗПЕЧНО і міняємо затримку
				if (dataHolder.getData("batsav") != null) {
					delay = dataHolder.getData("batsav").equals("1") ? 1000 : 300;
				}
				
				// Фіксуємо фінальне значення для використання всередині лямбди (вимога Java)
				final int finalDelay = delay;
				
				// Плануємо виклик loadSuggestions()
				debounceHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						loadSuggestions(prevCharSeq.toString());
					}
				}, finalDelay);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		hide.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				try{
					String buf = autocomplete1.getText().toString();
					for (int i = 0; i < (int)(rpl.size()); i++) {
						int u = Integer.parseInt(rpl.get(i).get("r").toString());
						if (rpl.get((int)i).get("e").toString().equals("1")) {
							if (u > 2) {
								buf = buf.replaceAll(u-2==0?rpl.get(i).get("f").toString():rpl.get(i).get("f").toString().toLowerCase(), u-2==0?rpl.get(i).get("t").toString():rpl.get(i).get("t").toString().toLowerCase());
							} else {
								buf = buf.replace(u==0?rpl.get(i).get("f").toString():rpl.get(i).get("f").toString().toLowerCase(), u==0?rpl.get(i).get("t").toString():rpl.get(i).get("t").toString().toLowerCase());
							}
						}
					}
					if (buf.startsWith("javascript:")) {
						_webViewActions(10, JSutil.replaceInstruction(buf));
					} else {
						_webViewActions(10, buf);
					}
				}catch(Exception e){
					
				}
				return true;
			}
		});
		
		hide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear1.setVisibility(View.GONE);
				linear5.setVisibility(View.GONE);
				windowsmanager.setVisibility(View.GONE);
				winholdsize = 0;
				SafeMarginUtils.setFullscreenMode(MainActivity.this, 2);
				AlertDialog.Builder warning = new AlertDialog.Builder(MainActivity.this);
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (!sf.contains("fullscreenwarn")) {
					warning.setTitle("?");
					warning.setMessage(getString(R.string.hide_panel_hint));
					warning.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							sf.edit().putString("fullscreenwarn", "").commit();
						}
					});
					warning.setCancelable(false);
					AlertDialog dddddd = warning.create();
					RenderUtils.setupDialogDim(MainActivity.this, dddddd);
					dddddd.show();
					
				}
			}
		});
		
		refresh.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				_webViewActions(-9, "");
				return true;
			}
		});
		
		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_webViewActions(5, "");
			}
		});
		
		button1.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				try{
					String buf = autocomplete1.getText().toString();
					for (int i = 0; i < (int)(rpl.size()); i++) {
						int u = Integer.parseInt(rpl.get(i).get("r").toString());
						if (rpl.get((int)i).get("e").toString().equals("1")) {
							if (u > 2) {
								buf = buf.replaceAll(u-2==0?rpl.get(i).get("f").toString():rpl.get(i).get("f").toString().toLowerCase(), u-2==0?rpl.get(i).get("t").toString():rpl.get(i).get("t").toString().toLowerCase());
							} else {
								buf = buf.replace(u==0?rpl.get(i).get("f").toString():rpl.get(i).get("f").toString().toLowerCase(), u==0?rpl.get(i).get("t").toString():rpl.get(i).get("t").toString().toLowerCase());
							}
						}
					}
					if (buf.startsWith("javascript:")) {
						_webViewActions(10, JSutil.replaceInstruction(buf));
					} else {
						_webViewActions(10, buf);
					}
				}catch(Exception e){
					
				}
				return true;
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				URL = autocomplete1.getText().toString();
				if (URL.startsWith("https://extensions.weekbrowser.com")||URL.startsWith("https://skins.weekbrowser.com/general")||URL.startsWith("https://skins.weekbrowser.com/bookmarks")||URL.startsWith("https://multilink2.weekbrowser.com")) {
					intent.setClass(getApplicationContext(), IntentActivity.class);
					intent.setData(Uri.parse(URL));
					startActivity(intent);
					closeeditor.performClick();
					prevCharSeq = "";
					finish();
				} else {
					if ((autocomplete1.getText().toString().contains(".")&&!autocomplete1.getText().toString().contains(" "))||autocomplete1.getText().toString().contains("javascript:")) {
						if (autocomplete1.getText().toString().startsWith("javascript:")) {
							_webViewActions(10, JSutil.replaceInstruction(URL));
							closeeditor.performClick();
							prevCharSeq = "";
						} else {
							_webViewActions(10, URL);
							closeeditor.performClick();
							prevCharSeq = "";
						}
					} else {
						_search2();
					}
				}
			}
		});
		
		openpanel.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.quicksettingsbtn));
				return true;
			}
		});
		
		openpanel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
				if (settingspanel.getVisibility() == View.VISIBLE) {
					settingspanel.setVisibility(View.GONE);
					if (Integer.parseInt(sf.getString("navpan", "")) == 0) {
						linear5.setVisibility(View.GONE);
					}
				} else {
					settingspanel.setVisibility(View.VISIBLE);
					linear5.setVisibility(View.VISIBLE);
					Animator2.anim(getApplicationContext(), settingspanel, 1, 300);
					if (Integer.parseInt(sf.getString("navpan", "")) == 0) {
						Animator2.anim(getApplicationContext(), linear5, 2, 300);
					}
				}
			}
		});
		
		button3.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Configurações da automação");
				return true;
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				NaviSettingsDialog.show(MainActivity.this, new NaviDataStore(MainActivity.this), null);
			}
		});
		
		closeeditor.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.closebtn));
				return true;
			}
		});
		
		closeeditor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (SafeMarginUtils.isKeyboardVisible()) {
					SketchwareUtil.hideKeyboard(getApplicationContext());
				}
				holdsearchtext = false;
				prevCharSeq = "";
				tabscontainer.requestFocus();
			}
		});
		
		copyurl.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.copybtn));
				return true;
			}
		});
		
		copyurl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", autocomplete1.getText().toString()));
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.copied));
			}
		});
		
		pasteurl.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.pastebtn));
				return true;
			}
		});
		
		pasteurl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				
				// Перевіряємо, чи є в буфері дані
				if (clipboard.hasPrimaryClip()) {
					ClipData clip = clipboard.getPrimaryClip();
					if (clip != null && clip.getItemCount() > 0) {
						// Отримуємо текст з буфера
						CharSequence textToPaste = clip.getItemAt(0).getText();
						// Встановлюємо текст в AutoCompleteTextView
						autocomplete1.setText(textToPaste);
					}
				}
			}
		});
		
		pastegourl.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.pastesearchbtn));
				return true;
			}
		});
		
		pastegourl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pasteurl.performClick();
				button1.performClick();
			}
		});
		
		selall.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.selallbtn));
				return true;
			}
		});
		
		selall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				autocomplete1.requestFocus();
				((EditText)autocomplete1).selectAll();
			}
		});
		
		cururl.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.geturlbtn));
				return true;
			}
		});
		
		cururl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_webViewActions(0, "");
			}
		});
		
		forcesearch.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.forcesearch));
				return true;
			}
		});
		
		forcesearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_search2();
			}
		});
		
		forceurl.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.forceurl));
				return true;
			}
		});
		
		forceurl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				URL = autocomplete1.getText().toString();
				_webViewActions(10, URL);
				closeeditor.performClick();
				prevCharSeq = "";
			}
		});
		
		share.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.share2));
				return true;
			}
		});
		
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, autocomplete1.getText().toString());
				startActivity(Intent.createChooser(intent, getString(R.string.sharewith)));
			}
		});
		
		pos1.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				_webViewActions(301, "");
				return true;
			}
		});
		
		pos1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_webViewActions(401, "");
			}
		});
		
		pos2.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				_webViewActions(302, "");
				return true;
			}
		});
		
		pos2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_webViewActions(402, "");
			}
		});
		
		pos3.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				_webViewActions(303, "");
				return true;
			}
		});
		
		pos3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_webViewActions(403, "");
			}
		});
		
		anim2.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator _param1) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator _param1) {
				
			}
			
			@Override
			public void onAnimationCancel(Animator _param1) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator _param1) {
				
			}
		});
		
		searchpan2.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator _param1) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator _param1) {
				
			}
			
			@Override
			public void onAnimationCancel(Animator _param1) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		_clearonexit(true);
		_fullRepairTab();
		destroyWebViews("", false);
		Skin s = Skin.getInstance();
		s.colorToRam(this);
		s.setStaticBG(this, 2, false);
		
		ExtendedDataHolder dh = ExtendedDataHolder.getInstance();
		dh.setData("ols", "0");
		
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		// Em cada inicialização do aplicativo, recomeça com uma única aba vazia.
		// A homepage configurada do WeekBrowser continua valendo para abas abertas pelo usuário.
		tab.clear();
		HashMap<String, Object> startupTab = new HashMap<>();
		startupTab.put("name", "Navi");
		startupTab.put("url", "about:blank");
		tab.add(startupTab);
		dh.removeData("w");
		sf.edit().putString("tabs", JsonUtils.toJson(tab)).putString("activetab", "0").apply();
		
		tabscontainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom,
			int oldLeft, int oldTop, int oldRight, int oldBottom) {
				// Якщо розміри змінилися — оновлюємо сітку
				if (right - left != oldRight - oldLeft || bottom - top != oldBottom - oldTop) {
					_webViewActions(-28, ""); 
				}
			}
		});
		
		autocomplete1.setThreshold(1);
		
		time = sf.getLong("time", 0L);
		//autocomplete1.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		
		autocomplete1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH ||
				actionId == EditorInfo.IME_ACTION_DONE ||
				actionId == EditorInfo.IME_ACTION_NEXT ||
				actionId == EditorInfo.IME_NULL) { // IME_NULL може бути використаний для Enter на деяких клавіатурах
					button1.performClick();
					return true; // Сигналізуємо, що подія оброблена
				}
				return false;
			}
		});
		
		autocomplete1.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//SketchwareUtil.showMessage(getApplicationContext(), String.valueOf(keyCode));
				if (event.getAction() == KeyEvent.ACTION_DOWN && 
				(keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
					if (!event.isShiftPressed()) {
						button1.performClick();
						return true; // Сигналізуємо, що подія оброблена
					}
				}
				return false;
			}
		});
		
		_fonts();
		searchpan.setVisibility(View.GONE);
		linear9.setVisibility(View.GONE);
		linear14.setVisibility(View.GONE);
		windowsmanager.setVisibility(View.GONE);
		button1.setVisibility(View.GONE);
		linear16.setVisibility(View.GONE);
		srhhis.setVisibility(View.GONE);
		if (sf.getString("incognito", "").equals("1")) {
			sf.edit().putString("tabs", "[{\"name\":\" — \", \"url\"=\"".concat(sf.getString("firstpage", "").concat("\"}]"))).commit();
			sf.edit().putString("activetab", "0").commit();
			sf.edit().putString("incognito", "0").commit();
		}
		for (HashMap<String, Object> map : tab) {
			map.remove("tab");
			//map.remove("time");
			map.remove("load");
			//map.remove("fav");
			//map.remove("name");
		}
		if (Integer.parseInt(sf.getString("activetab", "")) > (tab.size() - 1)) {
			sf.edit().putString("activetab", String.valueOf((long)(tab.size() - 1))).commit();
		}
		tabscont.setAdapter(new TabscontAdapter(tab));
		_swipe();
		autocomplete1.setOnTouchListener(new View.OnTouchListener() {
			private float startX;
			private float startY;
			private boolean isSwiping = false;
			private boolean swiped = false;
			private final float touchSlop = ViewConfiguration.get(autocomplete1.getContext()).getScaledTouchSlop();
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Перевіряємо, чи режим редактора увімкнено (linear16 видимий)
				if (linear16.getVisibility() == View.VISIBLE) {
					// У режимі редактора свайпи не обробляються
					return false; // Повертаємо false, щоб дозволити стандартну обробку подій
				}
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					startX = event.getRawX();
					startY = event.getRawY();
					isSwiping = false;
					swiped = false;
					break;
					
					case MotionEvent.ACTION_MOVE:
					float currentX = event.getRawX();
					float currentY = event.getRawY();
					float deltaX = Math.abs(currentX - startX);
					float deltaY = Math.abs(currentY - startY);
					
					if (deltaX > touchSlop || deltaY > touchSlop) {
						if (deltaX > deltaY) {
							isSwiping = true;
							if (currentX > startX) {
								// Свайп вправо
								if (!swiped) {
									_webViewActions(200, "");
									tabscontainer.requestFocus();
									swiped = true;
								}
							} else {
								// Свайп вліво
								if (!swiped) {
									_webViewActions(201, "");
									tabscontainer.requestFocus();
									swiped = true;
								}
							}
						}
					}
					break;
					
					case MotionEvent.ACTION_UP:
					if (!isSwiping) {
						// Якщо свайпу не було, виконуємо стандартну дію для натискання
						autocomplete1.performClick();
						autocomplete1.requestFocus(); // Надаємо фокус EditText
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(autocomplete1, InputMethodManager.SHOW_IMPLICIT); // Відкриваємо клавіатуру
					}else{
						tabscontainer.requestFocus();
					}
					swiped = false;
					break;
				}
				return true; // Повертаємо true, щоб показати, що подія оброблена
			}
		});
		
		font.setProgress((int)Integer.parseInt(sf.getString("fontsize", "")));
		seekbar1.setProgress((int)Integer.parseInt(sf.getString("minfont", "")));
		dp.setProgress((int)Integer.parseInt(sf.getString("dp", "")));
		textview5.setText(getString(R.string.qs_uisize).concat(" (").concat(String.valueOf((long)(dp.getProgress() * 10)).concat(")")));
		textview3.setText(getString(R.string.qs_fontsize).concat(" (").concat(String.valueOf((long)(font.getProgress() * 10)).concat("%)")));
		textview2.setText(getString(R.string.qs_minfont).concat(" (").concat(String.valueOf((long)(seekbar1.getProgress())).concat(")")));
		settingspanel.setVisibility(View.GONE);
		((EditText)autocomplete1).setMaxLines((int)1);
		((EditText)search).setMaxLines((int)3);
		if (sf.getString("blockad", "").equals("1")) {
			blockad.setChecked(true);
		} else {
			blockad.setChecked(false);
		}
		if (sf.getString("iframeblock", "").equals("1")) {
			iframe.setChecked(true);
		} else {
			iframe.setChecked(false);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case 101:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
			}
			break;
			
			case REQUEST_SELECT_FILE:
			if (WTF.SDK_INT() >= 21) {
				if (uploadMessage == null) return; uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(_resultCode, _data)); uploadMessage = null; }
			break;
			
			case FILECHOOSER_RESULTCODE:
			if (null == mUploadMessage){
				return; }
			Uri result = _data == null || _resultCode != RESULT_OK ? null : _data.getData(); mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
			
			if (true){
			}
			else {
				
			}
			break;
			default:
			break;
		}
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		_webViewActions(100001, "");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		NaviBillingManager.get(this).connect(null);
		_init();
		SafeMarginUtils.init(MainActivity.this, linear2);
		
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		final SharedPreferences sf2 = getSharedPreferences("code", Context.MODE_PRIVATE);
		/*Skin skin = new Skin();
if(nf)skin.colorToRam(MainActivity.this);
*/
		Skin.getInstance().updateBG(this, 2); 
		
		AdBlocker ab = AdBlocker.getInstance(getApplicationContext());
		ab.loadBlockedHosts();
		
		// 1. Оновлюємо конфіги з диска 1 раз в пам'ять
		ExtensionUtil eu = ExtensionUtil.getInstance(this);
		eu.loadExtensions(this);
		winholdsize = (int)(Float.parseFloat(sf.getString("button", "40")));
		
		
		//colorify.applyColorifyToAll(getWindow().getDecorView().getRootView());
		
		/*ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
multilink.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
            

         pc.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
			
			title.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
otherapp.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
selall.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
closeall.setTextColor((int)Long.parseLong(dataHolder.getData("rbt"),16));
closeeditor.setTextColor((int)Long.parseLong(dataHolder.getData("rbt"),16));
blockad.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
num.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
iframe.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
forcesearch.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
forceurl.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
share.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));

image.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
			js.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
		incognito.setTextColor((int)Long.parseLong(dataHolder.getData("t"),16));
        add.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
			hide.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
copyurl.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
pasteurl.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
pastegourl.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
copyallurl.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
sup.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
sdown.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
refresh.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
autocomplete1.setTextColor((int)Long.parseLong(dataHolder.getData("tf"),16));
search.setTextColor((int)Long.parseLong(dataHolder.getData("tf"),16));
textview2.setTextColor((int)Long.parseLong(dataHolder.getData("add"),16));
			textview5.setTextColor((int)Long.parseLong(dataHolder.getData("add"),16));
			textview3.setTextColor((int)Long.parseLong(dataHolder.getData("add"),16));
autocomplete1.setHintTextColor((int)Long.parseLong(dataHolder.getData("h"),16));
search.setHintTextColor((int)Long.parseLong(dataHolder.getData("h"),16));
			refresh.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
			button1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
			    if(sf.getString("incognito","").equals("1")){openpanel.setTextColor((int)Long.parseLong(dataHolder.getData("rbt"),16));}else{openpanel.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
                        }
			button3.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
			textview1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
pos1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
pos2.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
pos3.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));

widthp1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));

widthm1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));

heightp1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));

heightm1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));

cururl.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));
*/
		// Приклад використання класу Skin для встановлення фонів
		/*
skin.setBG(heightm1, 0, true);
skin.setBG(selall, 0, true);
skin.setBG(heightp1, 0, true);
skin.setBG(widthm1, 0, true);
skin.setBG(widthp1, 0, true);
skin.setBG(pos1, 0, false);
skin.setBG(pos2, 0, false);
skin.setBG(pos3, 0, false);
skin.setBG(hide, 0, true);
skin.setBG(button1, 0, true);

if(sf.getString("incognito","").equals("1")) {
    skin.setBG(openpanel, 1, true);
} else {
    skin.setBG(openpanel, 0, true);
}

skin.setBG(button3, 0, true);
skin.setBG(refresh, 0, true);
skin.setBG(textview1, 0, false);
skin.setBG(copyallurl, 0, false);
skin.setBG(sup, 0, true);
skin.setBG(sdown, 0, true);
skin.setBG(multilink, 0, false);
skin.setBG(otherapp, 0, false);
skin.setBG(add, 0, false);
skin.setBG(closeall, 1, false);
skin.setBG(closeeditor, 1, true);
skin.setBG(copyurl, 0, true);
skin.setBG(pasteurl, 0, true);
skin.setBG(pastegourl, 0, true);
skin.setBG(cururl, 0, true);
skin.setBG(forcesearch, 0, true);
skin.setBG(forceurl, 0, true);
skin.setBG(share, 0, true);
*/
		
		/*          skin.setBG(tabscontainer, 2, true);
            skin.setBG(tabscont, 2, true);
            skin.setBG(windowsmanager, 2, true);
            skin.setBG(settingspanel, 2, true);
            skin.setBG(srhhis, 2, true);
            skin.setBG(nav, 2, true);
            skin.setBG(searchpan, 2, true);
            skin.setBG(linear1, 2, true);
            
            
skin.setBG(linear2, 2, false);
*/
		if (sf.getString("monoforta", "").equals("1")) {
			autocomplete1.setTypeface(Typeface.MONOSPACE);
			search.setTypeface(Typeface.MONOSPACE);
		} else {
			autocomplete1.setTypeface(Typeface.DEFAULT);
			search.setTypeface(Typeface.DEFAULT);
		}
		try{
			rpl = JsonUtils.SO(FileUtil.readFile(getFilesDir().getPath()+"/replace.json"));
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "URL replacement list is cracked");
		}
		try{
			sitepermis = JsonUtils.SO(FileUtil.readFile(getFilesDir().getPath()+"/sitepermis.json"));
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "Webpage permissions list is cracked");
		}
		try{
			history = JsonUtils.SO(FileUtil.readFile(getFilesDir().getPath()+"/history.json"));
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "History is cracked");
		}
		if (sf.getString("js", "").equals("1")) {
			js.setChecked(true);
		}
		if (sf.getString("usedram", "").equals("0")) {
			ramuse.setVisibility(View.GONE);
		} else {
			ramuse.setVisibility(View.VISIBLE);
		}
		if (!(sf.getString("incognito", "").equals("1") && sf.getString("noscreen", "").equals("1"))) {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
		}
		if (dataHolder.getData("idtxt")!=null) {
			_webViewActions(10,
			"javascript:(function(){" +
			"function getElementSelector(id) {" +
			"  if (!id) return null;" +
			"  if (document.getElementById(id)) return '#' + id;" +
			"  if (document.querySelector('[name=\"' + id + '\"]')) return '[name=\"' + id + '\"]';" +
			"  if (document.querySelector('.' + id)) return '.' + id;" +
			"  return null;" +
			"}" +
			
			"function triggerInput(el, value) {" +
			"  try {"
			+ "    el.focus();" +
			"    el.value = value;" +
			"    el.dispatchEvent(new Event('input', { bubbles: true }));" +
			"    el.dispatchEvent(new Event('change', { bubbles: true }));" +
			"    el.blur();" +
			"  } catch(e) { console.warn('Trigger error', e); }" +
			"}" +
			
			"function tryInsert(doc, id, value) {" +
			"  var selector = getElementSelector.call(doc, id);" +
			"  if (!selector) return false;" +
			"  var el = doc.querySelector(selector);" +
			"  if (el) { triggerInput(el, value); return true; }" +
			"  return false;" +
			"}" +
			
			"(function main() {" +
			"  var id = '" + dataHolder.getData("idtxt") + "';" +
			"  var txt = \"" + dataHolder.getData("txt").replace("\"", "\\\"") + "\";" +
			
			"  if (tryInsert(document, id, txt)) return;" + // головна сторінка
			
			// перевіряємо всі iframe
			"  var frames = document.getElementsByTagName('iframe');" +
			"  for (var i = 0; i < frames.length; i++) {" +
			"    try {" +
			"      var doc = frames[i].contentDocument || frames[i].contentWindow.document;" +
			"      if (tryInsert(doc, id, txt)) return;" +
			"    } catch(e) { console.warn('Iframe access blocked', e); }" +
			"  }" +
			"})();" +
			"})()"
			);
			
			dataHolder.setData("idtxt", null);
			dataHolder.setData("txt", null);
		}
		if (dataHolder.getData("blob")!=null) {
			_webViewActions(1003, "");
			dataHolder.setData("blob", null);
		}
		if ((dataHolder.getData("safe")==null || !dataHolder.getData("safe").equals("1")) && (sf2.contains("en") && (sf2.getString("en", "").equals("2") || (sf2.getString("en", "").equals("1") && sf.getString("incognito", "").equals("1"))))) {
			intent.setClass(getApplicationContext(), LockActivity.class);
			startActivity(intent);
		} else {
			dataHolder.setData("safe", "0");
		}
		if (dataHolder.getData("savemht")!=null && !dataHolder.getData("savemht").equals("")) {
			_webViewActions(2000, "");
		} else {
			
		}
		Foreground = true;
		synchronized(tab) {
			if (dataHolder.getData("url")!=null) {
				if (dataHolder.getData("url").startsWith("javascript:")) {
					_webViewActions(10, dataHolder.getData("url"));
				} else {
					if (dataHolder.getData("bgtab")==null && dataHolder.getData("sametab")==null) {
						_newTab(dataHolder.getData("url"), true);
					}
					if (dataHolder.getData("bgtab")==null && dataHolder.getData("sametab")!=null) {
						_webViewActions(10, dataHolder.getData("url"));
					}
					if (dataHolder.getData("bgtab")!=null && dataHolder.getData("sametab")==null) {
						_newTab(dataHolder.getData("url"), false);
					}
				}
				sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
				dataHolder.setData("bgtab", null);
				dataHolder.setData("sametab", null);
				dataHolder.setData("url", null);
				tabscont.setAdapter(new TabscontAdapter(tab));
				((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
				_Reopentab();
			}
		}
		themebml = JSutil.themePage();;
		ismovedpanels = false;
		nf = true;
		_buttonsizeset();
		linear1.setVisibility(View.VISIBLE);
		if (sf.getString("infotext", "").equals("1")) {
			textview1.setGravity(Gravity.CENTER_HORIZONTAL);
		} else {
			textview1.setGravity(Gravity.LEFT);
		}
		if (sf.getString("pc", "").equals("1")) {
			pc.setChecked(true);
		} else {
			pc.setChecked(false);
		}
		if (sf.getString("loadimages", "").equals("1")) {
			image.setChecked(true);
		} else {
			image.setChecked(false);
		}
		if (sf.getString("themepage", "").equals("1")) {
			themepage.setChecked(true);
		} else {
			themepage.setChecked(false);
		}
		if (sf.getString("swipetore", "").equals("1")) {
			swipetore.setChecked(true);
		} else {
			swipetore.setChecked(false);
		}
		if (sf.getString("navpan", "").equals("1")) {
			linear5.setVisibility(View.VISIBLE);
		} else {
			linear5.setVisibility(View.GONE);
		}
		if (sf.getString("incognito", "").equals("1")) {
			openpanel.setText("");
			incognito.setChecked(true);
		} else {
			openpanel.setText("");
			incognito.setChecked(false);
		}
		_timer = new Timer();
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_loading();
						if (searchpan.getVisibility() == View.VISIBLE) {
							num.setText(String.valueOf((long)(curnum)).concat("/".concat(String.valueOf((long)(allnum)))));
						}
						if (ticks%60 == 0) {
							/*        // Знаходимо головний LinearLayout з id "tabscontainer"
        //LinearLayout
        LinearLayout tabscontainer = findViewById(R.id.tabscontainer);

        // Ініціалізуємо стек для обходу
        java.util.Stack<ViewGroup> stack = new java.util.Stack<>();
        stack.push(tabscontainer);

        while (!stack.isEmpty()) {
            ViewGroup parent = stack.pop();

            // Проходимо по всіх дочірніх елементах parent
            for (int h = 0; h < parent.getChildCount(); h++) {
                View child = parent.getChildAt(h);

                if (child instanceof WebView) {
                    // Якщо дочірній елемент є WebView, виконуємо необхідну команду
                    WebView webView = (WebView) child;
    
            _setAll(webView);
     
                } else if (child instanceof ViewGroup) {
                    // Якщо дочірній елемент є ViewGroup (наприклад, LinearLayout), додаємо його до стеку для подальшого оброблення
                    stack.push((ViewGroup) child);
                }
            }
        }
    */
							new Thread(new Runnable() {
								@Override
								public void run() {
									// Виконання фонової роботи
									
									try {
										int timeoutMs = 3000;
										Socket sock = new Socket();
										SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
										
										sock.connect(sockaddr, timeoutMs);
										sock.close();
										
										Internet=false;
									} catch (IOException e) {Internet=true;}
									
									
								}
							}).start();
							_webViewActions(1999, "");
							new Thread(new Runnable() {
								@Override
								public void run() {
									// Виконання фонової роботи
									
									Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
									Debug.getMemoryInfo(memoryInfo);
									
									// Використовувана пам'ять в KB
									int usedMemory = memoryInfo.getTotalPss();
									
									// Перетворення в MB
									usedMemoryMB = usedMemory >> 10;
									
									if (sf.getString("usedram", "").equals("1")) {
										StringBuilder sb = new StringBuilder();
										sb.append("UsedRAM: ").
										append(String.valueOf(usedMemoryMB))
										.append("MiB\nTt = ")
										.append(String.valueOf(Tt))
										.append("; time = ")
										.append(String.valueOf(time));
										runOnUiThread(() -> {
											ramuse.setText(sb.toString());
											_ramoptimizer();
										});
									}
									
									
									
								}
							}).start();
						}
						if (!focused) {
							if (!ismovedpanels) {
								if (sf.getString("navpanpos", "").equals("1")) {
									linear2.removeView(linear14);
									linear2.addView(linear14, 0);
									
									linear2.removeView(searchpan);
									linear2.addView(searchpan, 0);
									
									linear2.removeView(linear9);
									linear2.addView(linear9, 0);
									
									linear2.removeView(nav);
									linear2.addView(nav, 0);
								} else {
									linear2.removeView(linear14);
									linear2.addView(linear14);
									
									linear2.removeView(linear9);
									linear2.addView(linear9);
									
									linear2.removeView(nav);
									linear2.addView(nav);
									
									linear2.removeView(searchpan);
									linear2.addView(searchpan);
								}
								if (sf.getString("searchpanelpos", "").equals("1")) {
									linear2.removeView(srhhis);
									linear2.addView(srhhis, 0);
									
									linear2.removeView(settingspanel);
									linear2.addView(settingspanel, 0);
									
									linear2.removeView(linear1);
									linear2.addView(linear1, 0);
									
									linear2.removeView(hscroll1);
									linear2.addView(hscroll1, 0);
								} else {
									linear2.removeView(srhhis);
									linear2.addView(srhhis);
									
									linear2.removeView(settingspanel);
									linear2.addView(settingspanel);
									
									linear2.removeView(linear1);
									linear2.addView(linear1);
									
									linear2.removeView(hscroll1);
									linear2.addView(hscroll1);
								}
							}
							ismovedpanels = true;
						}
						_kbdsrh();
						_webViewActions(701, "");
						ticks++;
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(timer, (int)(49), (int)(((dataHolder.getData("batsav").equals("1")) ? ((int)200) : ((int)50))));
		_tabupd();
		if (sf.getString("toolbardot", "").equals("1")) {
			hide.setText("•");
			refresh.setText("•");
			button1.setText("•");
		} else {
			hide.setText("");
			refresh.setText("");
			button1.setText("");
		}
		if (is1window) {
			windowsmanager.setVisibility(View.VISIBLE);
			SafeMarginUtils.setFullscreenMode(this, 0);
		}
		_webViewActions(1001, "");
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		SafeMarginUtils.cleanup();
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		if (_timer != null) {
			_timer.cancel();
			_timer = null;
		}
		if (sf.getString("nopreview", "").equals("1") && sf.getString("incognito", "").equals("1")) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
		}
		Foreground = false;
		_Reopentab();
		if (dataHolder.getData("ols").equals("0") && sf.getString("pauseweb", "1").equals("1")) {
			_webViewActions(1000, "");
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		if (sf.getString("incognito", "").equals("1")) {
			sf.edit().putString("activetab", "0").commit();
			sf.edit().putString("tabs", "[{\"name\":\"home\", \"url\"=\"".concat(sf.getString("firstpage", "").concat("\"}]"))).commit();
		}
		_clearonexit(false);
	}
	
	public void onLowMemory() {
		super.onLowMemory();
		SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.warninglowram));
		_webViewActions(-25, _clearram());
	}
	
	@Override 
	public void onConfigurationChanged(Configuration newConfig) 
	{ 
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == 1) {
			tabscont.setNumColumns((int)2);
			srhhis.setNumColumns((int)3);
			final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			
			tabscont.getLayoutParams().height = (int)(123 * getResources().getDisplayMetrics().density);
			tabscont.requestLayout();
			
			srhhis.getLayoutParams().height = (int)(123 * getResources().getDisplayMetrics().density);
			srhhis.requestLayout();
			
		} else {
			tabscont.setNumColumns((int)4);
			srhhis.setNumColumns((int)6);
			final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			
			tabscont.getLayoutParams().height = (int)(82 * getResources().getDisplayMetrics().density);
			tabscont.requestLayout();
			
			srhhis.getLayoutParams().height = (int)(82 * getResources().getDisplayMetrics().density);
			srhhis.requestLayout();
			
		}
		Skin s = Skin.getInstance();
		//s.colorToRam(this);
		s.setBG(getWindow().getDecorView(), 2, false);
		s.notifyObservers();
	}
	
	@Override
	protected void onPostCreate(Bundle _savedInstanceState) {
		super.onPostCreate(_savedInstanceState);
		ExtendedDataHolder dh = ExtendedDataHolder.getInstance();
		
		SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		if (dataHolder.containsKey("w") && dataHolder.getData("w").equals("1")) {
			int firstActiveTabIndex = -1;
			
			for (int i = 0; i < tab.size(); i++) {
				if (tab.get(i).get("x") != null) {
					if (firstActiveTabIndex == -1) {
						firstActiveTabIndex = i;
					}
					
					createWebViewTab(
					tab.get(i).get("url").toString(), 
					tab.get(i), 
					false, 
					false
					);
				}
			}
			
			if (firstActiveTabIndex != -1) {
				sf.edit().putString("activetab", String.valueOf(firstActiveTabIndex)).commit();
			}
			
			dataHolder.setData("window", "0");
			new Thread(() -> {
				dataHolder.setData("ml", _getMultilink());
			}).start();
			
			is1window = true;
			
			tabscontainer.post(new Runnable() {
				@Override
				public void run() {
					_webViewActions(-28, ""); 
				}
			});
			
		} else {
			int _position = Integer.parseInt(sf.getString("activetab", "0"));
			String initialUrl = tab.get(_position).get("url").toString();
			WebView initialWebView = createWebViewTab(initialUrl, tab.get(_position), false, false);
			if ("about:blank".equals(initialUrl)) showStartupLogo(initialWebView);
			_Reopentab();
		}

		android.widget.FrameLayout naviSheetContainer = findViewById(R.id.naviSheetContainer);
		if (naviSheetContainer != null) {
			naviSheetContainer.setVisibility(View.VISIBLE);
			naviSheetContainer.bringToFront();
			if (naviSheetContainer.getChildCount() > 0
					&& naviSheetContainer.getChildAt(0) instanceof NaviSheetView) {
				String activeTabTag = getCurrentTabTag();
				WebView activeWebView = activeTabTag != null
						? ResourceManager.getInstance().getWebViewForTab(activeTabTag)
						: null;
				if (activeWebView != null) {
					((NaviSheetView) naviSheetContainer.getChildAt(0)).setBrowserSession(
							new com.a525team.weekbrowser.navi.browser.WeekBrowserWebViewAdapter(
									activeTabTag, activeWebView));
				}
			}
		}
	}

	private void showStartupLogo(WebView webView) {
		if (webView == null) return;
		try (InputStream input = getResources().openRawResource(R.drawable.navi_startup_logo);
			 ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[4096];
			int count;
			while ((count = input.read(buffer)) != -1) bytes.write(buffer, 0, count);
			String icon = android.util.Base64.encodeToString(bytes.toByteArray(), android.util.Base64.NO_WRAP);
			String html = "<!doctype html><html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"></head>"
				+ "<body style=\"margin:0;background:#808080;min-height:100vh;display:flex;align-items:center;justify-content:center\">"
					+ "<img alt=\"Navi\" style=\"width:120px;height:120px;object-fit:contain\" src=\"data:image/png;base64," + icon + "\"></body></html>";
			webView.setBackgroundColor(android.graphics.Color.rgb(128, 128, 128));
			webView.loadDataWithBaseURL("about:blank", html, "text/html", "UTF-8", null);
		} catch (IOException e) {
			android.util.Log.e("Navi", "Could not load startup logo", e);
		}
	}
	public void _ifex() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		if (sf == null) {
			
			SketchwareUtil.showMessage(getApplicationContext(), "SharedPreferences Error");
			
		} else {
			String x = "g"; //унікальна для кожної версії змінна, призначена для швидшого запуску. Змінювати ЛИШЕ ЦЮ змінну!
			if (!sf.contains("a") || !sf.getString("a", "").equals(x)) {
				sf.edit().putString("a", x).apply();
				//і нижче можна вже
				
				
				
				String legacyAdHosts = sf.getString("adhosts", "");
				
				// Якщо в SharedPreferences є старі дані та вони не порожні
				if (legacyAdHosts != null && !legacyAdHosts.isEmpty()) {
					File userBlackFile = new File(getFilesDir(), "user_ad_list.txt");
					
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(userBlackFile, true))) {
						// Розбиваємо старі дані на рядки
						String[] lines = legacyAdHosts.split("\n");
						for (String line : lines) {
							String trimmed = line.trim();
							if (!trimmed.isEmpty()) {
								bw.write(trimmed);
								bw.newLine();
							}
						}
						bw.flush();
						
						// Після успішного запису у файл — видаляємо ключ із SharedPreferences
						sf.edit().remove("adhosts").apply();
						
					} catch (IOException e) {
						e.printStackTrace();
						// Якщо сталася помилка запису, ключ в SharedPreferences не видаляється,
						// щоб дані не втратилися і додаток спробував знову при наступному запуску
					}
				}
				
				
				
				if (FileUtil.isExistFile(getFilesDir().getPath().concat("/bookmark.html"))) {
					FileUtil.deleteFile(getFilesDir().getPath().concat("/bookmark.html"));
				}
				
				if (!FileUtil.isExistFile(getFilesDir().getPath()+"/history.json")) {
					FileUtil.writeFile(getFilesDir().getPath()+"/history.json", "[]");
				}
				
				if (!FileUtil.isExistFile(getFilesDir().getPath()+"/replace.json")) {
					FileUtil.writeFile(getFilesDir().getPath()+"/replace.json", "[{\"r\":\"0\",\"t\":\"ssyoutube.com\",\"e\":\"1\",\"f\":\"youtube.com\"}]");
				}
				
				if (!FileUtil.isExistFile(getFilesDir().getPath()+"/sitepermis.json")) {
					FileUtil.writeFile(getFilesDir().getPath()+"/sitepermis.json", "[]");
				}
				
				if (sf.contains("search") && sf.getString("search", "").length()>2) {
					FileUtil.writeFile(getFilesDir().getPath().concat("/srhhis.json"), sf.getString("search", "[]"));
					sf.edit().putString("search", "").commit();
				}
				
				if (!FileUtil.isExistFile(getFilesDir().getPath()+"/srhhis.json")) {
					FileUtil.writeFile(getFilesDir().getPath()+"/srhhis.json", "[]");
				}
				
				
				if (FileUtil.readFile(getFilesDir().getPath().concat("/bookmark.json")).length() < 5) {
					FileUtil.writeFile(getFilesDir().getPath().concat("/bookmark.json"), "[{\"type\"=\"link\",\"name\"=\"Ajuda - Navi\",\"url\"=\"https://github.com/arkeyou/NaviBrowser/tree/main#navi--navega\u00e7\u00e3o-inteligente\",\"color\"=\"#00FF00\"}]");
				}
				if (!sf.contains("time")) {
					sf.edit().putLong("time", 0L).commit();
				}
				if (!sf.contains("usedram")) {
					sf.edit().putString("usedram", "0").commit();
				}
				if (!sf.contains("dispagevis")) {
					sf.edit().putString("dispagevis", "1").commit();
				}
				if (!sf.contains("forcezoom")) {
					sf.edit().putString("forcezoom", "0").commit();
				}
				if (!sf.contains("resol")) {
					sf.edit().putString("resol", String.valueOf(Math.max(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()), SketchwareUtil.getDisplayWidthPixels(getApplicationContext())))).commit();
				}
				if (!sf.contains("hardware")) {
					sf.edit().putString("hardware", "1").commit();
				}
				if (!sf.contains("hardware2")) {
					sf.edit().putString("hardware2", "1").commit();
				}
				if (!sf.contains("maxhistory")) {
					sf.edit().putString("maxhistory", "1000").commit();
				}
				if (!sf.contains("maxhistoryt")) {
					sf.edit().putString("maxhistoryt", "0").commit();
				}
				if (!sf.contains("Sizesbutton")) {
					sf.edit().putString("Sizesbutton", "0").commit();
				}
				if (!sf.contains("pauseweb")) {
					sf.edit().putString("pauseweb", "1").commit();
				}
				if (!sf.contains("maxram")) {
					sf.edit().putString("maxram", String.valueOf((long)((totalMem * 3L) / 4L))).commit();
				}
				if (!sf.contains("firstpage")) {
					sf.edit().putString("firstpage", "file://" + getFilesDir().getPath().concat("/bookmark.json")).commit();
				}
				if (sf.getString("firstpage", "").equals("file://" + getFilesDir().getPath().concat("/bookmark.html"))) {
					sf.edit().putString("firstpage", "file://" + getFilesDir().getPath().concat("/bookmark.json")).commit();
				}
				if (!sf.contains("autofirstpage")) {
					sf.edit().putString("autofirstpage", "0").commit();
				}
				if (!sf.contains("autocleansearch")) {
					sf.edit().putString("autocleansearch", "0").commit();
				}
				if (!sf.contains("autocleancache")) {
					sf.edit().putString("autocleancache", "0").commit();
				}
				if (!sf.contains("autocleancookie")) {
					sf.edit().putString("autocleancookie", "0").commit();
				}
				if (!sf.contains("autocleanform")) {
					sf.edit().putString("autocleanform", "0").commit();
				}
				if (!sf.contains("positions")) {
					sf.edit().putString("positions", "[]").commit();
				}
				if (!sf.contains("fontsize")) {
					sf.edit().putString("fontsize", "10").commit();
				}
				if (!sf.contains("searchpanelpos")) {
					sf.edit().putString("searchpanelpos", "1").commit();
				}
				if (!sf.contains("loadimages")) {
					sf.edit().putString("loadimages", "1").commit();
				}
				if (!sf.contains("minfont")) {
					sf.edit().putString("minfont", "0").commit();
				}
				if (!sf.contains("blockad")) {
					sf.edit().putString("blockad", "1").commit();
				}
				if (!sf.contains("iframeblock")) {
					sf.edit().putString("iframeblock", "0").commit();
				}
				if (!sf.contains("hidepanels")) {
					sf.edit().putString("hidepanels", "0").commit();
				}
				if (!sf.contains("searchengine")) {
					sf.edit().putString("searchengine", "0").commit();
				}
				if (!sf.contains("nointernet")) {
					sf.edit().putString("nointernet", "1").commit();
				}
				if (!sf.contains("iframer")) {
					sf.edit().putString("iframer", "1").commit();
				}
				if (!sf.contains("infotext")) {
					sf.edit().putString("infotext", "0").commit();
				}
				if (!sf.contains("bg")) {
					sf.edit().putString("bg", "FF000000FF000080").commit();
				}
				if (!sf.contains("bbg")) {
					sf.edit().putString("bbg", "FF00FF00FF00FFFF").commit();
				}
				if (!sf.contains("rbg")) {
					sf.edit().putString("rbg", "FFFF0000FFFF00FF").commit();
				}
				if (!sf.contains("t")) {
					sf.edit().putString("t", "FF00FF00").commit();
				}
				if (!sf.contains("tf")) {
					sf.edit().putString("tf", "FF00FFFF").commit();
				}
				if (!sf.contains("add")) {
					sf.edit().putString("add", "FFFF0070").commit();
				}
				if (!sf.contains("h")) {
					sf.edit().putString("h", "FF7000FF").commit();
				}
				if (!sf.contains("bt")) {
					sf.edit().putString("bt", "FF000000").commit();
				}
				if (!sf.contains("rbt")) {
					sf.edit().putString("rbt", "FF000000").commit();
				}
				if (!sf.contains("style")) {
					sf.edit().putString("style", "5").commit();
				}
				if (!sf.contains("savehistory")) {
					sf.edit().putString("savehistory", "1000").commit();
				}
				if (!sf.contains("uapc")) {
					sf.edit().putString("uapc", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").commit();
				}
				if (!sf.contains("ua")) {
					sf.edit().putString("ua", "").commit();
				}
				if (!sf.contains("incognito")) {
					sf.edit().putString("incognito", "0").commit();
				}
				if (!sf.contains("theme")) {
					sf.edit().putString("theme", "1").commit();
				}
				if (!sf.contains("js")) {
					sf.edit().putString("js", "1").commit();
				}
				if (!sf.contains("dp")) {
					sf.edit().putString("dp", "10").commit();
				}
				if (!sf.contains("loadingstyle")) {
					sf.edit().putString("loadingstyle", "1").commit();
				}
				if (!sf.contains("cache")) {
					sf.edit().putString("cache", "0").commit();
				}
				if (!sf.contains("pc")) {
					sf.edit().putString("pc", "0").commit();
				}
				if (!sf.contains("cache")) {
					sf.edit().putString("cache", "0").commit();
				}
				if (!sf.contains("button")) {
					sf.edit().putString("button", "40").commit();
				}
				if (!sf.contains("activetab")) {
					sf.edit().putString("activetab", "0").commit();
				}
				if (!sf.contains("noscreen")) {
					sf.edit().putString("noscreen", "0").commit();
				}
				if (!sf.contains("nopreview")) {
					sf.edit().putString("nopreview", "1").commit();
				}
				if (!sf.contains("monoforta")) {
					sf.edit().putString("monoforta", "0").commit();
				}
				if (!sf.contains("except")) {
					sf.edit().putString("except", "[{\"url\":\"file://\",\"im\"=\"1\",\"js\"=\"1\"}]").commit();
				}
				if (!sf.contains("themepage")) {
					sf.edit().putString("themepage", "0").commit();
				}
				if (!sf.contains("rou")) {
					sf.edit().putString("rou", "0").commit();
				}
				if (!sf.contains("zoombutton")) {
					sf.edit().putString("zoombutton", "0").commit();
				}
				if (!sf.contains("activetab")) {
					sf.edit().putString("activetab", "0").commit();
				}
				if (!sf.contains("noscreen")) {
					sf.edit().putString("noscreen", "0").commit();
				}
				if (!sf.contains("nopreview")) {
					sf.edit().putString("nopreview", "1").commit();
				}
				if (!sf.contains("blockredir")) {
					sf.edit().putString("blockredir", "1").commit();
				}
				if (!sf.contains("windows")) {
					sf.edit().putString("windows", "[{\"e\":\"0\",\"px\":\"0\",\"py\":\"0\",\"sx\":\"2\",\"sy\":\"2\"},{\"e\":\"0\",\"px\":\"2\",\"py\":\"0\",\"sx\":\"2\",\"sy\":\"2\"},\n{\"e\":\"0\",\"px\":\"0\",\"py\":\"2\",\"sx\":\"2\",\"sy\":\"1\"},{\"e\":\"0\",\"px\":\"2\",\"py\":\"2\",\"sx\":\"2\",\"sy\":\"1\"},{\"e\":\"0\",\"px\":\"0\",\"py\":\"3\",\"sx\":\"2\",\"sy\":\"1\"},{\"e\":\"0\",\"px\":\"2\",\"py\":\"3\",\"sx\":\"2\",\"sy\":\"1\"},{\"w\":\"0\"}]").commit();
				}
				if (!sf.contains("navpanpos")) {
					sf.edit().putString("navpanpos", "0").commit();
				}
				if (!sf.contains("swipetore")) {
					sf.edit().putString("swipetore", "0").commit();
				}
				if (!sf.contains("searchpan")) {
					sf.edit().putString("searchpan", "1").commit();
				}
				if (!sf.contains("navpan")) {
					sf.edit().putString("navpan", "0").commit();
				}
				if (!sf.contains("adhosts")) {
					sf.edit().putString("adhosts", "").commit();
				}
				if (!sf.contains("pos1x")) {
					sf.edit().putString("pos1x", "0").commit();
				}
				if (!sf.contains("pos2x")) {
					sf.edit().putString("pos2x", "0").commit();
				}
				if (!sf.contains("pos3x")) {
					sf.edit().putString("pos3x", "0").commit();
				}
				if (!sf.contains("pos1y")) {
					sf.edit().putString("pos1y", "0").commit();
				}
				if (!sf.contains("pos2y")) {
					sf.edit().putString("pos2y", "0").commit();
				}
				if (!sf.contains("pos3y")) {
					sf.edit().putString("pos3y", "0").commit();
				}
				if (!sf.contains("videopatterns")) {
					sf.edit().putString("videopatterns", "Пропустити через|Реклама закінчиться|Реклама [0-9]|Відео незабаром почне відтворюватися|Видео скоро начнется|Вы cможете перейти\\nк видео через|[0-9] з [0-9]|Перейти на сайт рекламодавця|Перейти На Сайт Рекламодавця|This ad will end in|На Сайт Рекламодателя").commit();
				}
				if (!sf.contains("listanimp")) {
					sf.edit().putString("listanimp", "0").commit();
				}
				if (!sf.contains("listanimi")) {
					sf.edit().putString("listanimi", "2").commit();
				}
				if (!sf.contains("bpanimp")) {
					sf.edit().putString("bpanimp", "2").commit();
				}
				if (!sf.contains("bpanimi")) {
					sf.edit().putString("bpanimi", "2").commit();
				}
				if (!sf.contains("spanimp")) {
					sf.edit().putString("spanimp", "0").commit();
				}
				if (!sf.contains("spanimi")) {
					sf.edit().putString("spanimi", "2").commit();
				}
				if (!sf.contains("llm")) {
					sf.edit().putString("llm", "0").commit();
				}
				if (!sf.contains("cssdiv")) {
					sf.edit().putString("cssdiv", " ❖ ").commit();
				}
				if (!sf.contains("cssdivcolor")) {
					sf.edit().putString("cssdivcolor", "ff0000").commit();
				}
				if (!sf.contains("preventclose")) {
					sf.edit().putString("preventclose", "0").commit();
				}
				_ifex2();
				if (!sf.contains("tabs") || (sf.getString("tabs", "").length() < 5)) {
					sf.edit().putString("tabs", "[{\"name\":\"home\", \"url\"=\"".concat(sf.getString("firstpage", "").concat("\"}]"))).commit();
				}
				
				if (!sf.contains("dblurx")) {
					sf.edit().putString("dblurx", "30").commit();
				}
				if (!sf.contains("dblury")) {
					sf.edit().putString("dblury", "30").commit();
				}
				if (!sf.contains("drred")) {
					sf.edit().putString("drred", "0").commit();
				}
				if (!sf.contains("drgreen")) {
					sf.edit().putString("drgreen", "0").commit();
				}
				if (!sf.contains("drblue")) {
					sf.edit().putString("drblue", "0").commit();
				}
				if (!sf.contains("dsatur")) {
					sf.edit().putString("dsatur", "0.7").commit();
				}
				if (!sf.contains("dvred")) {
					sf.edit().putString("dvred", "0.8").commit();
				}
				if (!sf.contains("dvgreen")) {
					sf.edit().putString("dvgreen", "0.8").commit();
				}
				if (!sf.contains("dvblue")) {
					sf.edit().putString("dvblue", "0.8").commit();
				}
				if (!sf.contains("dvalp")) {
					sf.edit().putString("dvalp", "0.5").commit();
				}
				if (!sf.contains("don")) {
					sf.edit().putString("don", WTF.SDK_INT() >= Build.VERSION_CODES.S ? "1" : "0").commit();
				}
				if (!sf.contains("autocleanfav")) {
					sf.edit().putString("autocleanfav", "0").commit();
				}
				if (!sf.contains("srhbar")) {
					sf.edit().putString("srhbar", "0").commit();
				}
				if (!sf.contains("toolbar")) {
					sf.edit().putString("toolbar",
					"1&-990&&&%2&-990&&&%3&-7&&&%4&-3&&&%-1&-3&&&%13&10&&&file:///data/user/0/com.a525team.weekbrowser/files/bookmark.json%-4&-990&&&%-5&-10&[?tabs?]&&%-6&-8&&&%14&-990&&&%-2&-990&A&&%-7&-990&&&").commit();
				}
				if (!sf.contains("toolbardot")) {
					sf.edit().putString("toolbardot", "1").commit();
				}
				if (!sf.contains("usefinger")) {
					sf.edit().putString("usefinger", "1").commit();
				}
				if (!sf.contains("sm")) {
					sf.edit().putString("sm", "1").commit();
					
					if (!sf.contains("detailed_logs")) {
						sf.edit().putString("detailed_logs", "0").commit();
					}
				}
			}
		}
	}
	
	
	public void _language() {
	}
	
	@Override
	protected void attachBaseContext(Context newBase) {
		if (newBase != null) {
			SharedPreferences sf = newBase.getSharedPreferences("sf", Context.MODE_PRIVATE);
			String languageCode = sf.getString("lang", "en");
			Context context = LocaleHelper.setLocale(newBase, languageCode);
			super.attachBaseContext(context);
		} else {
			super.attachBaseContext(newBase);
		}
	}
	
	{
	}
	
	
	public void _Reopentab() {
		//try {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		sf.edit().putLong("time", time).apply();
		
		// 1. Отримуємо індекс активної вкладки
		int activeTabIndex = 0;
		try { 
			activeTabIndex = Integer.parseInt(sf.getString("activetab", "0")); 
		} catch (Exception e) {}
		
		if (activeTabIndex < 0 || activeTabIndex >= tab.size()) return;
		String activeTabTag = "ll" + getTabValue(activeTabIndex);
		ResourceManager rm = ResourceManager.getInstance();
		
		// 2. Оновлюємо відображення та SkinKey без перебору дерева
		for (int i = 0; i < tab.size(); i++) {
			String tabValue = getTabValue(i);
			if (tabValue.isEmpty()) continue;
			
			String currentTag = "ll" + tabValue;
			LinearLayout childLayout = rm.getContainerForTab(currentTag);
			if (childLayout == null) continue;
			
			boolean isActive = currentTag.equals(activeTabTag);
			
			// Логіка видимості (як у вашому коді: !is1window означає режим ОДНОГО вікна)
			if (isActive) {
				childLayout.setVisibility(View.VISIBLE);
			} else if (!is1window) {
				childLayout.setVisibility(View.GONE);
			}
			
			// Логіка скінів
			if (childLayout instanceof SkinLinearLayout) {
				SkinLinearLayout skinLayout = (SkinLinearLayout) childLayout;
				if (is1window) { // Режим сітки вікон
					skinLayout.setSkinKey(isActive ? "rbg" : "bbg", false);
				} else { // Режим одного вікна (на весь екран)
					skinLayout.setSkinKey("t", false);
				}
			}
		}
		
		// 3. Логіка свайпу (ALLOWSWIPE) тільки для WebView активної вкладки
		WebView activeWebView = rm.getWebViewForTab(activeTabTag);
		if (activeWebView != null) {
			boolean ruleFound = false;
			String url = activeWebView.getUrl();
			
			if (url != null) {
				for (int i = 0; i < sitepermis.size(); i++) {
					HashMap<String, Object> perm = sitepermis.get(i);
					if (perm != null && perm.containsKey("url") && url.contains(perm.get("url").toString())) {
						ruleFound = true;
						try{
							String sr = perm.get("sr").toString();
							if ("1".equals(sr)) ALLOWSWIPE = true;
							else if ("2".equals(sr)) ALLOWSWIPE = "1".equals(sf.getString("swipetore", ""));
							else if ("0".equals(sr)) ALLOWSWIPE = false;
						}catch(Exception e){
							ALLOWSWIPE = "1".equals(sf.getString("swipetore", ""));
						}
						break;
					}
				}
			}
			// Дефолтна дія, якщо немає правила
			if (!ruleFound) {
				ALLOWSWIPE = "1".equals(sf.getString("swipetore", ""));
			}
		} else {
			ALLOWSWIPE = "1".equals(sf.getString("swipetore", ""));
		}
		
		// 4. Додаткові дії
		_webViewActions(100, "");
		_webViewActions(30, (searchpan.getVisibility() == View.VISIBLE) ? search.getText().toString() : "");
		if (image.isChecked()) {
			_webViewActions(1998, "");
		}
		_webViewActions(102, "");
		
		// 5. ОНОВЛЕННЯ СПИСКУ (щоб вивантажені вкладки одразу перемалювали колір)
		if (tabscont != null && tabscont.getAdapter() != null) {
			((android.widget.BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
		}
		
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		if(is1window)updateWindowsSkin();
	}
	
	
	public void _loading() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		try{
			load = Integer.parseInt(tab.get((int)Integer.parseInt(sf.getString("activetab", ""))).get("load").toString().replace("l", ""));
		}catch(Exception e){
			load = -1;
		}
		if (SafeMarginUtils.getFullscreenMode() == 2) {
			textview1.setPadding(0, SafeMarginUtils.getCutoutHeight(0), 0, 0);
		} else {
			textview1.setPadding(0, 0, 0, 0);
			
		}
		if (isshowed && !swipetorefresh) {
			info = info.concat("[          ]\n");
		}
		if (swipetorefresh) {
			info = info.concat("[          ]\n");
		}
		if (load < 100) {
			if (Integer.parseInt(sf.getString("loadingstyle", "")) < 2) {
				info = getString(R.string.loading) + " ";
			}
			if ((Integer.parseInt(sf.getString("loadingstyle", "")) == 0) || (Integer.parseInt(sf.getString("loadingstyle", "")) == 2)) {
				for(int _repeat32 = 0; _repeat32 < (int)(load); _repeat32++) {
					info = info.concat("/");
				}
			} else {
				if (Integer.parseInt(sf.getString("loadingstyle", "")) == 4) {
					for(int _repeat41 = 0; _repeat41 < (int)((load / 2)); _repeat41++) {
						info = info.concat("/");
					}
					info = info.concat(String.valueOf((long)(load)).concat("%"));
					for(int _repeat53 = 0; _repeat53 < (int)((load / 2)); _repeat53++) {
						info = info.concat("/");
					}
				} else {
					if (Integer.parseInt(sf.getString("loadingstyle", "")) == 5) {
						for(int _repeat546 = 0; _repeat546 < (int)(load); _repeat546++) {
							info = info.concat("/");
						}
						info = info.concat(String.valueOf((long)(load)).concat("%"));
					} else {
						info = info.concat(String.valueOf((long)(load)).concat("%"));
					}
				}
			}
			if (sf.getString("toolbardot", "").equals("1")) {
				refresh.setText("•");
			} else {
				refresh.setText("");
			}
		} else {
			if (sf.getString("toolbardot", "").equals("1")) {
				refresh.setText("•");
			} else {
				refresh.setText("");
			}
		}
		if (Internet) {
			info = info.concat(getString(R.string.nointernet));
		}
		if (info.trim().equals("")) {
			textview1.setVisibility(View.GONE);
		} else {
			textview1.setVisibility(View.VISIBLE);
		}
		textview1.setText(info);
		info = "";
	}
	
	
	public void _titl() {
		
	}
	
	
	public void _setTopPadding(final View _view, final double _topPadding) {
		int topPaddingInPx = (int) ((int)_topPadding * getResources().getDisplayMetrics().density + 0.5f);
		
		if(topPaddingInPx>0){_view.setPadding(1, (int)topPaddingInPx, 1, 1);}else
		{_view.setPadding(0,0,0,0);}
	}
	
	
	public String _geturl() {
		String baseUrl = "";
		_webViewActions(100, "");
		String fullUrl = URL;
		try {
			URL url = new URL(fullUrl);
			baseUrl = url.getProtocol() + "://" + url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return (baseUrl);
	}
	
	
	public void _buttonsizeset() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		float buttonBaseSize = Float.parseFloat(sf.getString("button", "40")); // 40 як запасне значення
		
		View[] buttons = {
			//back, pgup, forward, pgdn,
			//bookmarks, histori, home, tabs,
			//dual, skipvideo, pagesearch, coordinates,
			hide, openpanel, refresh, button1, button3,
			widthp1, widthm1, heightp1, heightm1,
			closeeditor, copyurl, pasteurl, pastegourl,
			selall, cururl, ssl, forcesearch, forceurl,
			share, sup, sdown
		};
		
		for (View b : buttons) {
			adjustButton(b, buttonBaseSize, true);
		}
		
		View[] buttonsh = {
			themepage, js, pc, incognito,
			image, blockad, iframe, swipetore,
			linear12, linear6, linear7
		};
		
		for (View b : buttonsh) {
			adjustButton(b, buttonBaseSize, false);
		}
		
	}
	private void adjustButton(View button, float baseSizeDp, boolean widthq) {
		float density = getResources().getDisplayMetrics().density;
		ViewGroup.LayoutParams lp = button.getLayoutParams();
		if(widthq)
		lp.width  = (int) (baseSizeDp * density * 1.27f);
		lp.height = (int) (baseSizeDp * density);
		button.setLayoutParams(lp); // щоб застосувати нові розміри
		if(button instanceof TextView)
		((TextView)button).setTextSize(baseSizeDp / 2.5f);
	}
	{
	}
	
	
	public void _swipe() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		autocomplete1.setOnFocusChangeListener(new OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) { 
					
					focused = true;
				} 
				else { 
					focused = false;
				} } });
	}
	
	
	public String _clearram() {
		return ("javascript:(function() {\n    var elements = document.querySelectorAll('*');\n    for (var i = 0; i < elements.length; i++) {\n        elements[i].style.border = 'none';\n        elements[i].style.boxShadow = 'none';\n        elements[i].style.transition = 'none';\n        elements[i].style.animation = 'none';\n        elements[i].style.backgroundImage = 'none';\n        elements[i].style.textShadow = 'none';\n    }\n})();\n");
	}
	
	
	public void _onscroll(final WebView _w) {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		final Activity thiss = this;
		
		_w.setOnTouchListener(new View.OnTouchListener() {
			private float startY;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (SafeMarginUtils.isKeyboardVisible()) return false;
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					startY = event.getRawY();
					
					ExtendedDataHolder.getInstance().setData("touch_x", String.valueOf((int) event.getX()));
					ExtendedDataHolder.getInstance().setData("touch_y", String.valueOf((int) event.getY()));
					
					if (jsInterface != null) {
						_w.post(() -> jsInterface.removeButton(true));
					}
					
					if (is1window) {
						// 1. Шукаємо прямий нащадок tabscontainer (тобто сам SkinLinearLayout вікна)
						View windowView = v;
						while (windowView.getParent() != null && windowView.getParent() != tabscontainer) {
							if (windowView.getParent() instanceof View) {
								windowView = (View) windowView.getParent();
							} else {
								break;
							}
						}
						
						// Перевіряємо, чи ми дійсно знайшли вікно всередині tabscontainer
						if (windowView.getParent() == tabscontainer) {
							
							// 2. Виводимо НА ПЕРЕДНІЙ ПЛАН ТІЛЬКИ ЦЕ ВІКНО всередині tabscontainer
							// (Ні в якому разі не викликаємо bringToFront() для самого tabscontainer!)
							windowView.bringToFront();
							
							if (windowView instanceof LinearLayout) {
								updateWindowsLayout((LinearLayout) windowView);
							}
							
							// Перемальовуємо Z-порядок елементів усередині tabscontainer
							tabscontainer.invalidate();
							
							// 3. Перевизначаємо activetab для обраної вкладки
							Object rawTag = windowView.getTag() != null ? windowView.getTag() : v.getTag();
							if (rawTag != null) {
								String currentTouchTag = String.valueOf(rawTag).replace("ll", "").trim();
								
								for (int i = 0; i < tab.size(); i++) {
									String tabId = String.valueOf(tab.get(i).get("tab")).replace("ll", "").trim();
									
									if (currentTouchTag.equals(tabId)) {
										// Якщо це не була активна вкладка або потрібно просто оновити стан
										time++;
										tab.get(i).put("time", String.valueOf(time));
										sf.edit().putString("activetab", String.valueOf(i)).apply();
										
										openOrRestoreTab(i);
										
										if (tabscont != null && tabscont.getAdapter() != null) {
											((BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
										}
										break;
									}
								}
							}
						}
					}
					break;
					
					case MotionEvent.ACTION_MOVE:
					float currentY = event.getRawY();
					float deltaY = currentY - startY;
					int threshold50 = (int)SketchwareUtil.getDip(getApplicationContext(), 50);
					int threshold250 = (int)SketchwareUtil.getDip(getApplicationContext(), 250);
					
					// Скрол ДОНИЗ (показ панелей + підготовка до оновлення)
					if (deltaY > threshold50) {
						// 1. Панелі ховаються/показуються ЛИШЕ коли НЕ віконний режим (!is1window)
						if ("1".equals(sf.getString("hidepanels", "")) && !is1window) {
							linear1.setVisibility(View.VISIBLE);
							if ("1".equals(sf.getString("navpan", ""))) {
								linear5.setVisibility(View.VISIBLE);
							}
						}
						
						if (!is1window) {
							SafeMarginUtils.setFullscreenMode(thiss, 0);
						}
						
						// 2. Перша фаза свайпу: фіксуємо, що жест почався від верху сторінки
						if (!local2 && ALLOWSWIPE && !is1window) {
							if (_w.getScrollY() == 0) {
								isshowed = true;
								local1 = true;
								// Тут ваш infotext може показувати підказку про потягування
							}
						}
						local2 = true;
						
						// 3. Друга фаза: перетинання межі 250dp дозволяє оновлення при відпусканні пальця
						if (deltaY > threshold250 && local1 && ALLOWSWIPE) {
							swipetorefresh = true;
							// Якщо палець затягнуто достатньо далеко, можна оновити текст в infotext на підтвердження
						} else {
							swipetorefresh = false; // Якщо відтягнули назад вище 250dp — скасовуємо готове оновлення
						}
						
						// Скрол ВГОРУ (приховування панелей)
					} else if (deltaY < -threshold50) {
						if ("1".equals(sf.getString("hidepanels", "")) && !is1window) {
							linear1.setVisibility(View.GONE);
							linear5.setVisibility(View.GONE);
							
							int orient = getResources().getConfiguration().orientation;
							if (orient == 1) {
								SafeMarginUtils.setFullscreenMode(thiss, 2);
							} else {
								SafeMarginUtils.setFullscreenMode(thiss, 1);
							}
						}
					}
					break;
					
					case MotionEvent.ACTION_UP:
					float endY = event.getRawY();
					float touchSlop = ViewConfiguration.get(_w.getContext()).getScaledTouchSlop();
					
					// Оновлюємо сторінку ТІЛЬКИ якщо прапор підтверджено і палець пройшовTouch Slop
					if (swipetorefresh) {
						_refreshh();
					} else if (Math.abs(startY - endY) < touchSlop && is1window) {
						// Клік по контейнеру вікна у віконному режимі
						if (v.getParent() instanceof View) {
							((View) v.getParent()).callOnClick();
						}
					}
					
					// Скидання всіх станів свайпу
					resetSwipeStates();
					break;
					
					case MotionEvent.ACTION_CANCEL:
					// При скасуванні жесту системою НІЧОГО не оновлюємо
					resetSwipeStates();
					break;
				}
				return false;
			}
			
			private void resetSwipeStates() {
				isshowed = false;
				local1 = false;
				local2 = false;
				swipetorefresh = false;
			}
		});
		
	}
	
	
	public void _extrc() {
	}
	// Перевантаження для сумісності з 4 параметрами
	// 1. Перевантажений метод із 4 параметрами (для сумісності зі старими викликами)
	private WebView createWebViewTab(String url, HashMap<String, Object> tabData, boolean isNewTab, boolean _move) {
		return createWebViewTab(url, tabData, isNewTab, _move, true);
	}
	
	// 2. Основний метод із 5 параметрами
	private WebView createWebViewTab(String url, HashMap<String, Object> tabData, boolean isNewTab, boolean _move, boolean shouldLoadUrl) {
		Tt++; 
		time++;
		
		if (tabData == null) {
			tabData = new HashMap<>();
		}
		
		String tabValue = String.valueOf((long) Tt);
		String tabTag = "ll" + Tt;
		
		// 1. Оновлюємо Map вкладки
		tabData.put("tab", tabValue);
		tabData.put("time", String.valueOf(time));
		
		if (!tabData.containsKey("title") || tabData.get("title") == null || String.valueOf(tabData.get("title")).isEmpty()) {
			tabData.put("title", (url != null && !url.isEmpty() && !url.equals("about:blank")) ? url : "Нова вкладка");
		}
		
		// 2. Логіка для нових вкладок
		if (isNewTab) {
			if (_move) {
				getSharedPreferences("sf", Context.MODE_PRIVATE).edit()
				.putString("activetab", String.valueOf(tab.size())).commit();
			}
			tabData.put("url", url != null ? url : "about:blank");
			tab.add(tabData);
		}
		
		// 3. Створення контейнера
		SkinLinearLayout newWebViewContainer = new SkinLinearLayout(MainActivity.this);
		newWebViewContainer.setTag(tabTag);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT
		);
		layoutParams.weight = 1;
		newWebViewContainer.setLayoutParams(layoutParams);
		
		// 4. Створення WebView та реєстрація в ResourceManager
		WebView newWebView = new WebView(MainActivity.this);
		newWebView.setTag(tabTag);
		
		ResourceManager.getInstance().setWebViewForTab(tabTag, newWebView);
		ResourceManager.getInstance().setContainerForTab(tabTag, newWebViewContainer);
		ResourceManager.getInstance().getOrCreateResourcesForTab(tabTag);
		
		// Налаштування двіжка
		setupWebView(newWebView, MainActivity.this);
		newWebView.getSettings().setJavaScriptEnabled(true);
		
		// 5. Додавання на екран
		newWebViewContainer.addView(newWebView, new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT
		));
		newWebView.setAlpha(0f);
		newWebViewContainer.setAlpha(0f);
		tabscontainer.addView(newWebViewContainer);
		
		// 6. Завантаження сторінки
		if (shouldLoadUrl && url != null && !url.isEmpty()) {
			newWebView.loadUrl(url);
		}
		
		_tabupd();
		
		newWebView.animate().alpha(1f).setDuration(200).start();
		newWebViewContainer.animate().alpha(1f).setDuration(200).start();
		
		initWindowsConfigurations(newWebViewContainer);
		return newWebView;
	}
	
	public void setupWebView(final WebView webView, final Activity activity) {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		// Ваші внутрішні налаштування (вони можуть скидати WebSettings)
		_setAll(webView);
		_onscroll(webView);
		_download(webView);
		
		jsInterface = new JavaScriptInterface(this, webView);
		webView.addJavascriptInterface(jsInterface, "Vidro");
		webView.addJavascriptInterface(new Fl(), "Android");
		webView.addJavascriptInterface(new JsBridge(this, webView), "AndroidColorPicker");
		
		// ВАЖЛИВО: Примусово задаємо права ТІЛЬКИ ПІСЛЯ ВСІХ ІНШИХ НАЛАШТУВАНЬ
		webView.getSettings().setSupportMultipleWindows(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.setWebViewClient(new MyWebViewClient(activity));
		
		// Додання WebChromeClient для підтримки JavaScript та інших можливостей
		webView.setWebChromeClient(new CustomWebClient(activity){		
			// For Lollipop 5.0+ Devices
			public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
				if (uploadMessage != null) {
					uploadMessage.onReceiveValue(null);
					uploadMessage = null;
				}
				uploadMessage = filePathCallback;
				Intent intent = fileChooserParams.createIntent();
				try {
					startActivityForResult(intent, REQUEST_SELECT_FILE);
				} catch (ActivityNotFoundException e) {
					uploadMessage = null;
					Toast.makeText(getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
					return false;
				}
				return true;
			}
			
			protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
			}
			
			//онкреатевіндов
			@Override
			public boolean onCreateWindow(final WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
				try {
					String targetUrl = null;
					try {
						WebView.HitTestResult result = view.getHitTestResult();
						if (result != null) {
							targetUrl = result.getExtra();
						}
					} catch (Exception e) {}
					
					int adN = AdBlocker.getInstance(MainActivity.this).isBlocked(url);
					if (targetUrl != null && adN != 0) {
						SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.adblocked) + " (" + adN + ")");
						return false;
					}
					
					// Потік до створення контейнера (як у вашому старому коді)
					new Thread(() -> dataHolder.setData("ml", _getMultilink())).start();
					
					// Створюємо вкладку: передаємо targetUrl, але shouldLoadUrl = FALSE
					WebView newWebView = createWebViewTab(targetUrl, new HashMap<String, Object>(), true, true, false);
					
					// Передаємо створений WebView системі
					WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
					transport.setWebView(newWebView);
					resultMsg.sendToTarget();
					
					_Reopentab();
					
					return true;
					
				} catch (Exception e) {
					e.printStackTrace();
					SketchwareUtil.showMessage(getApplicationContext(), "Помилка відкриття вікна: " + e.getMessage());
					if (!tab.isEmpty()) {
						_closetab(tab.size() - 1);
					}
					return false;
				}
			}
			
		});
	}
	
	private String getCurrentTabTag() {
		try {
			// Зчитуємо активний індекс з SharedPreferences
			String activeTabContent = getSharedPreferences("sf", Context.MODE_PRIVATE).getString("activetab", "0");
			int activeTabIndex = Integer.parseInt(activeTabContent);
			
			if (activeTabIndex >= 0 && activeTabIndex < tab.size()) {
				Map<String, Object> tabData = tab.get(activeTabIndex);
				if (tabData != null && tabData.containsKey("tab")) {
					return "ll" + tabData.get("tab").toString();
				}
			}
		} catch (Exception ignored) {}
		return null;
	}
	
	private String getFileExtensionFromMimeType(String mimeType) {
		String[] parts = mimeType.split("/");
		if (parts.length == 2) {
			return parts[1];
		}
		return null;
	}
	
	/*private ValueCallback<Uri> mUploadMessage;
public ValueCallback<Uri[]> uploadMessage;
public static final int REQUEST_SELECT_FILE = 100;

private final static int FILECHOOSER_RESULTCODE = 1;*/
	
	public void openInOtherApps(String url) {
		Intent intent;
		try {
			if (url.startsWith("intent:")) {
				intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
				
				// Якщо інтент містить пряме посилання на сайт у полі data
				// нам треба переконатися, що категорія BROWSABLE дозволена
				if (intent.getData() != null && 
				(intent.getData().getScheme().equals("http") || intent.getData().getScheme().equals("https"))) {
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
				}
			} else {
				intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			}
		} catch (Exception e) {
			return;
		}
		
		PackageManager packageManager = getPackageManager();
		// Використовуємо MATCH_DEFAULT_ONLY для кращої сумісності
		List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		
		// СПЕЦІАЛЬНА ПЕРЕВІРКА ДЛЯ НАЛАШТУВАНЬ ТА СИСТЕМНИХ ДІЙ
		// Якщо список порожній, але інтент має Action (як android.settings.SETTINGS)
		if (resolveInfoList.isEmpty() && intent.getAction() != null) {
			try {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return;
			} catch (Exception e) {
				// Якщо навіть так не вийшло — тоді вже показуємо помилку
				SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.noapps));
				return;
			}
		}
		
		List<Intent> targetIntents = new ArrayList<>();
		for (ResolveInfo resolveInfo : resolveInfoList) {
			String packageName = resolveInfo.activityInfo.packageName;
			if (!packageName.equals(getPackageName())) {
				Intent targetIntent = new Intent(intent);
				targetIntent.setPackage(packageName);
				// Додаємо ComponentName, щоб точно потрапити в потрібну Activity
				targetIntent.setClassName(packageName, resolveInfo.activityInfo.name);
				targetIntents.add(targetIntent);
			}
		}
		
		if (!targetIntents.isEmpty()) {
			Intent chooserIntent = Intent.createChooser(targetIntents.remove(0), getString(R.string.openwith));
			if (!targetIntents.isEmpty()) {
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Intent[0]));
			}
			// Важливо для виклику з не-Activity контексту (якщо таке буде)
			chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(chooserIntent);
		} else {
			SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.noapps));
		}
	}
	
	private ValueCallback<Uri> mUploadMessage;
	public ValueCallback<Uri[]> uploadMessage;
	public static final int REQUEST_SELECT_FILE = 100;
	
	private final static int FILECHOOSER_RESULTCODE = 1;
	
	
	/*

private void startSetAllTimer(WebView webView) {
final WebView finalWebView = webView;
final Handler handler = new Handler();
final Runnable runnable = new Runnable() {
@Override
public void run() {
_setAll(finalWebView);
handler.postDelayed(this, 150); // Використання інтервалу 150 мс
}
};
handler.post(runnable);
}
*/
	
	//private boolean checkIfUrlBlocked(String url) {
	//	AdBlocker adBlocker = new AdBlocker(this);
	//	return adBlocker.isBlocked(url);
	//}
	
	
	
	public static String extractColor(String inputText, String prefix) {
		String regex = prefix + "0x" + "([a-fA-F0-9]+)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputText);
		
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			regex = prefix + "#" + "([a-fA-F0-9]+)";
			
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(inputText);
			
			if (matcher.find()) {
				return matcher.group(1);
			} else {return null;} 
		}
	}
	
	public static String extractColo(String inputText, String prefix) {
		String regex = prefix + "([a-fA-F0-9]+)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputText);
		
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
	
	private void loadSuggestions(String text) {
		ArrayList<String> suggestions = new ArrayList<>();
		if (text == null || text.trim().isEmpty()) {
			ThemeManager.setAutocomp(this, suggestions, autocomplete1);
			return;
		}
		
		// Локальні пропозиції
		for (String local : srcsug) {
			if (local.toLowerCase().startsWith(text.toLowerCase())) {
				suggestions.add(local);
			}
		}
		
		// Онлайн-пропозиції (асинхронно)
		new Thread(() -> {
			try {
				URL url = new URL("https://suggestqueries.google.com/complete/search?client=firefox&q="
				+ URLEncoder.encode(text, "UTF-8"));
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0");
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) response.append(line);
				reader.close();
				
				JSONArray arr = new JSONArray(response.toString());
				JSONArray online = arr.getJSONArray(1);
				
				for (int i = 0; i < online.length(); i++) {
					String suggestion = online.getString(i);
					if (!suggestions.contains(suggestion)) {
						suggestions.add(suggestion);
					}
				}
				
				runOnUiThread(() ->
				ThemeManager.setAutocomp(MainActivity.this, suggestions, autocomplete1)
				);
				
			} catch (Exception e) {
				runOnUiThread(() ->
				ThemeManager.setAutocomp(MainActivity.this, suggestions, autocomplete1)
				);
				e.printStackTrace();
			}
		}).start();
	}
	
	// Рекомендується викликати при створенні нового WebView або зміні режиму
	public void initWindowsConfigurations(SkinLinearLayout activeLayout) {
		activeLayout.post(new Runnable() {
			@Override
			public void run() {
				java.util.Stack<ViewGroup> stack = new java.util.Stack<>();
				stack.push(tabscontainer);
				
				int cellWidth = tabscontainer.getWidth() / 4;
				int cellHeight = tabscontainer.getHeight() / 4;
				
				// Якщо контейнер ще не встиг відмалюватися (наприклад, при старті), 
				// беремо базові розрахунки, щоб не було ділення на 0
				if (cellWidth == 0) cellWidth = 100;
				if (cellHeight == 0) cellHeight = 100;
				
				while (!stack.isEmpty()) {
					ViewGroup parent = stack.pop();
					
					for (int g = 0; g < parent.getChildCount(); g++) {
						View child = parent.getChildAt(g);
						
						if (child instanceof LinearLayout && child.getTag() != null && child.getTag().toString().startsWith("ll")) {
							final LinearLayout linearLayout = (LinearLayout) child;
							String currentTag = child.getTag().toString();
							
							// Використовуємо правильні параметри для RelativeLayout
							RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
							
							// Шукаємо дані цієї вкладки в масиві 'tab'
							int tag = 0;
							String windowId = currentTag.replace("ll", "");
							for (int i = 0; i < tab.size(); i++) {
								if (tab.get(i).get("tab") != null && tab.get(i).get("tab").toString().equals(windowId)) {
									tag = i;
									break;
								}
							}
							
							int ti = tab.get(tag).get("x") != null ? Integer.parseInt(tab.get(tag).get("x").toString().replace("s", "")) : 0;
							int th = tab.get(tag).get("y") != null ? Integer.parseInt(tab.get(tag).get("y").toString().replace("s", "")) : 0;
							int w  = tab.get(tag).get("w") != null ? Integer.parseInt(tab.get(tag).get("w").toString().replace("s", "")) : 2;
							int h  = tab.get(tag).get("h") != null ? Integer.parseInt(tab.get(tag).get("h").toString().replace("s", "")) : 2;
							
							if (is1window) { // БАГАТОВІКОННИЙ РЕЖИМ УВІМКНЕНО
								final int finalCellWidth = cellWidth;
								final int finalCellHeight = cellHeight;
								final int tabIndex = tag;
								
								linearLayout.setOnTouchListener(new OnTouchListener() {
									PointF DownPT = new PointF(); 
									int startLeftMargin = 0;
									int startTopMargin = 0;
									
									// ДОДАНО: Метод для безпечного пошуку актуального індексу
									private int getCurrentTabIndex() {
										String winId = linearLayout.getTag().toString().replace("ll", "");
										for (int i = 0; i < tab.size(); i++) {
											if (tab.get(i).get("tab") != null && tab.get(i).get("tab").toString().equals(winId)) {
												return i;
											}
										}
										return -1;
									}
									
									@Override
									public boolean onTouch(View v, MotionEvent event) {
										int currentTabIndex = getCurrentTabIndex();
										if (currentTabIndex == -1) return true; // Якщо вкладки вже немає в пам'яті
										
										RelativeLayout.LayoutParams touchLp = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
										int eid = event.getAction();
										
										switch (eid) {
											case MotionEvent.ACTION_DOWN:
											DownPT.x = event.getRawX();
											DownPT.y = event.getRawY();
											startLeftMargin = touchLp.leftMargin;
											startTopMargin = touchLp.topMargin;
											is1touch = true;
											
											final SharedPreferences sfDown = getSharedPreferences("sf", Context.MODE_PRIVATE);
											sfDown.edit().putString("activetab", String.valueOf(currentTabIndex)).commit(); // Використовуємо currentTabIndex!
											linearLayout.bringToFront();
											break;
											
											case MotionEvent.ACTION_MOVE:
											int deltaX = (int) (event.getRawX() - DownPT.x);
											int deltaY = (int) (event.getRawY() - DownPT.y);
											
											int maxLeft = tabscontainer.getWidth() - touchLp.width;
											int maxTop = tabscontainer.getHeight() - touchLp.height;
											
											touchLp.leftMargin = Math.max(0, Math.min(startLeftMargin + deltaX, maxLeft));
											touchLp.topMargin = Math.max(0, Math.min(startTopMargin + deltaY, maxTop));
											
											linearLayout.setLayoutParams(touchLp);
											break;
											
											case MotionEvent.ACTION_UP:
											is1touch = false;
											
											int gridX = (int) (((double) touchLp.leftMargin / finalCellWidth) + 0.5);
											int gridY = (int) (((double) touchLp.topMargin / finalCellHeight) + 0.5);
											
											tab.get(currentTabIndex).put("x", "s" + gridX); // Використовуємо currentTabIndex!
											tab.get(currentTabIndex).put("y", "s" + gridY); // Використовуємо currentTabIndex!
											
											final SharedPreferences sfUp = getSharedPreferences("sf", Context.MODE_PRIVATE);
											sfUp.edit().putString("activetab", String.valueOf(currentTabIndex)).commit(); // Використовуємо currentTabIndex!
											
											_webViewActions(-28, "");
											_Reopentab();
											((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
											break;
										}
										return true;
									}
								});
								////////
								
								_setTopPadding(linearLayout, winholdsize);
								lp.width = w * cellWidth;
								lp.height = h * cellHeight;
								lp.leftMargin = ti * cellWidth;
								lp.topMargin = th * cellHeight;
								
								linearLayout.setTranslationX(0);
								linearLayout.setTranslationY(0);
								linearLayout.setVisibility(View.VISIBLE);
								
							} else { // РЕЖИМ ОДНОГО ВІКНА (Багатовіконність ВИМКНЕНО)
								linearLayout.setOnTouchListener(null); // Вимикаємо тачі, бо вікно на весь екран
								
								_setTopPadding(linearLayout, 0);
								lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
								lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
								lp.leftMargin = 0;
								lp.topMargin = 0;
								
								linearLayout.setTranslationX(0);
								linearLayout.setTranslationY(0);
								
								if (linearLayout == activeLayout) {
									linearLayout.setVisibility(View.VISIBLE);
								} else {
									linearLayout.setVisibility(View.GONE);
								}
								windowsmanager.setVisibility(View.GONE);
							}
							
							linearLayout.setLayoutParams(lp);
							linearLayout.requestLayout();
						} else if (child instanceof ViewGroup) {
							stack.push((ViewGroup) child);
						}
					}
				}
			}
		});
	}
	
	// Метод для оновлення даних конкретної вкладки
	public void updateTabInfo(WebView webView, String customTitle, Bitmap customFavicon) {
		if (webView == null) return;
		
		Object webViewTag = webView.getTag(); 
		if (webViewTag == null) return; 
		
		// Очищаємо префікс "ll", щоб отримати чистий ID (наприклад, "ll1" -> "1")
		final String targetTabId = String.valueOf(webViewTag).replace("ll", "").trim();
		
		final String url = webView.getUrl();
		final String currentWebViewTitle = webView.getTitle();
		
		runOnUiThread(() -> {
			try {
				synchronized(tab) {
					for (int i = 0; i < tab.size(); i++) {
						Object tabValue = tab.get(i).get("tab");
						if (tabValue != null) {
							String cleanTabValue = String.valueOf(tabValue).replace("ll", "").trim();
							
							if (cleanTabValue.equals(targetTabId)) {
								// 1. Оновлення URL
								if (url != null && !url.isEmpty()) {
									tab.get(i).put("url", url);
								}
								
								// 2. Оновлення Назви (Title)
								String finalTitle = customTitle;
								if (finalTitle == null || finalTitle.isEmpty()) {
									finalTitle = currentWebViewTitle;
								}
								if (finalTitle != null && !finalTitle.isEmpty()) {
									tab.get(i).put("name", finalTitle);
									tab.get(i).put("title", finalTitle); // Про всяк випадок для сумісності
								}
								
								// 3. Оновлення Фавікона
								if (customFavicon != null) {
									String favEncoded = BitmapEncoderDecoder.encodeToBase64(customFavicon);
									if (favEncoded != null && !favEncoded.isEmpty()) {
										tab.get(i).put("fav", favEncoded);
									}
								}
								break;
							}
						}
					}
				}
				
				// Оновлюємо список вкладок у UI
				if (tabscont != null && tabscont.getAdapter() != null) {
					((BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void updateWindowsSkin() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE); 
		int activeIndex = -1;
		String targetTabId = "";
		
		try {
			String activeTabStr = sf.getString("activetab", "-1");
			activeIndex = Integer.parseInt(activeTabStr.trim());
			
			// Отримуємо ID активної вкладки
			if (activeIndex >= 0 && activeIndex < tab.size()) {
				Object tabObj = tab.get(activeIndex).get("tab");
				if (tabObj != null) {
					targetTabId = String.valueOf(tabObj).replace("ll", "").trim();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Проходимо по всіх вікнах у tabscontainer
		for (int i = 0; i < tabscontainer.getChildCount(); i++) {
			View child = tabscontainer.getChildAt(i);
			if (child instanceof SkinLinearLayout) {
				SkinLinearLayout window = (SkinLinearLayout) child;
				
				if (!is1window) {
					// Звичайний режим перегляду (одне вікно) — скидаємо рамку
					window.setSkinKey("bg", false);
				} else {
					// Віконний режим (!is1window) — розфарбовуємо
					Object rawTag = window.getTag();
					
					if (rawTag != null) {
						// Змінна windowId оголошується і використовується ВСЕРЕДИНІ перевірки null
						String windowId = String.valueOf(rawTag).replace("ll", "").trim();
						
						if (!targetTabId.isEmpty() && windowId.equals(targetTabId)) {
							window.setSkinKey("rbg", false); // Активне вікно
						} else {
							window.setSkinKey("bbg", false); // Неактивні вікна
						}
					} else {
						window.setSkinKey("bbg", false);
					}
				}
			}
		}
	}
	
	// Метод для збереження історії у фоновому потоці
	public void saveToHistory(final String title, final String url) {
		// Фіксуємо шлях до папки ДО того, як підемо у фоновий потік
		final String baseFolderPath = getApplicationContext().getFilesDir().getPath();
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		if ("1".equals(sf.getString("incognito", "0"))) {
			return; 
		}
		
		if (url == null || url.isEmpty() || url.equals("about:blank") || url.contains("bookmark.html")) {
			return; 
		}
		
		new Thread(() -> {
			try {
				long nowMillis = Calendar.getInstance().getTimeInMillis();
				
				synchronized (history) {
					HashMap<String, Object> _item = new HashMap<>();
					_item.put("name", (title != null && !title.isEmpty()) ? title : "UNTITLED :/");
					_item.put("url", url);
					_item.put("date", String.valueOf(nowMillis));
					history.add(_item);
					
					// ... (твій код обробки maxhistory залишається без змін) ...
					int maxhistory = 150000;
					try {
						maxhistory = Integer.parseInt(sf.getString("maxhistory", "150000"));
					} catch (NumberFormatException ignored) {}
					
					boolean maxhistoryt = "1".equals(sf.getString("maxhistoryt", "0"));
					
					if (maxhistoryt) {
						long thresholdMillis = nowMillis - (maxhistory * 1000L);
						Iterator<HashMap<String, Object>> iterator = history.iterator();
						while (iterator.hasNext()) {
							HashMap<String, Object> item = iterator.next();
							Object dateObj = item.get("date");
							if (dateObj != null) {
								try {
									if (Long.parseLong(dateObj.toString()) < thresholdMillis) {
										iterator.remove();
									}
								} catch (NumberFormatException ignored) {}
							}
						}
						if (history.size() > 150000) {
							history.subList(0, history.size() - 150000).clear();
						}
					} else {
						if (history.size() > maxhistory) {
							history.subList(0, history.size() - maxhistory).clear();
						}
					}
					
					
					// Використовуємо заздалегідь збережений шлях baseFolderPath
					FileUtil.writeFile(baseFolderPath + "/history.json", JsonUtils.toJson(history));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
	}
	
	private void repairTab(){
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		final String jsonStr = "[{\"name\":\" — \", \"url\"=\"".concat(sf.getString("firstpage", "").concat("\"}]"));
		tab.clear();
		try{
			tab = JsonUtils.SO(jsonStr);
		}catch(Exception e){
			//Навряд чи станеться помилка, але компілятор заставляє обробити її
		}
		sf.edit().putString("tabs", jsonStr)
		.putString("activetab", "0").commit();
	}
	
	class Fl { @JavascriptInterface
		public void onFolderClick(String folderIdWithAction) {} }
	
	public ArrayList<HashMap<String, Object>> getHis(){
		return history;
	}
	
	private void openOrRestoreTab(int position) {
		if (tab == null || position < 0 || position >= tab.size()) return;
		
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		// Отримуємо ДІЙСНИЙ active index із SharedPreferences
		int activeIndex = -1;
		try {
			activeIndex = Integer.parseInt(sf.getString("activetab", "-1"));
		} catch (Exception e) {}
		
		String tabValue = getTabValue(position);
		if (tabValue.isEmpty()) {
			Tt++;
			tabValue = String.valueOf(Tt);
			tab.get(position).put("tab", tabValue);
		}
		
		String tag = "ll" + tabValue;
		ResourceManager rm = ResourceManager.getInstance();
		WebView existingWebView = rm.getWebViewForTab(tag);
		
		// СТОП-КРАН: Якщо ми ВЖЕ дивимось на цю вкладку і її WebView існує в пам'яті, 
		// НЕ треба знову крутити time++ і перемальовувати контейнери!
		if (activeIndex == position && existingWebView != null) {
			updateWindowsLayout(rm.getContainerForTab(tag));
			return;
		}
		
		// Тільки при реальному переході збільшуємо лічильник часу
		time++;
		tab.get(position).put("time", String.valueOf(time));
		
		LinearLayout existingContainer = rm.getContainerForTab(tag);
		
		// 1. ОПТИМІЗОВАНЕ ВІДНОВЛЕННЯ (Без плодження порожніх клонів)
		if (existingWebView == null || existingContainer == null) {
			// СТВОРЕННЯ НОВОЇ ВКЛАДКИ
			SkinLinearLayout newWebViewContainer = new SkinLinearLayout(MainActivity.this);
			newWebViewContainer.setTag(tag);
			
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT
			);
			layoutParams.weight = 1;
			newWebViewContainer.setLayoutParams(layoutParams);
			
			final WebView newWebView = new WebView(MainActivity.this);
			newWebView.setTag(tabValue);
			newWebView.getSettings().setJavaScriptEnabled(true);
			
			rm.setWebViewForTab(tag, newWebView);
			rm.setContainerForTab(tag, newWebViewContainer);
			
			newWebView.setAlpha(0f);
			newWebViewContainer.setAlpha(0f);
			setupWebView(newWebView, MainActivity.this);
			
			String url = (tab.get(position).get("url") != null)
			? tab.get(position).get("url").toString()
			: sf.getString("firstpage", "");
			
			newWebView.loadUrl(url);
			newWebViewContainer.addView(newWebView, new LinearLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT
			));
			
			tabscontainer.addView(newWebViewContainer);
			
			newWebView.animate().alpha(1f).setDuration(200).start();
			newWebViewContainer.animate().alpha(1f).setDuration(200).start();
			
			initWindowsConfigurations(newWebViewContainer);
			
			if (tab.get(position).get("url") == null) {
				tab.get(position).put("url", url);
			}
		} else if (tabscontainer.indexOfChild(existingContainer) == -1) {
			// ВАЖЛИВО: Якщо контейнер Є в пам'яті, але випав з UI — просто повертаємо його!
			tabscontainer.addView(existingContainer);
		}
		
		// 2. ЗБЕРЕЖЕННЯ АКТИВНОЇ ВКЛАДКИ (Використовуємо apply() для швидкості UI)
		sf.edit().putString("activetab", String.valueOf(position)).apply();
		sf.edit().putString("tabs", JsonUtils.toJson(tab)).apply();
		
		// 3. ФІКС ВІЗУАЛУ ТА "ХОВАНОК" ВІКОН
		LinearLayout activeContainer = rm.getContainerForTab(tag);
		if (activeContainer != null) {
			// Піднімаємо вікно наверх ПЕРЕД оновленням макета, щоб система знала, хто тут головний
			activeContainer.bringToFront(); 
			updateWindowsLayout(activeContainer); 
		}
		
		// 4. ОНОВЛЕННЯ ДАНИХ ТА СТАНІВ
		String sz = sf.getString("dp", "");
		_tabupd();
		_Reopentab(); 
		_webViewActions(20, sz); 
	}
	private void warmupWebView() {
		// Використовуємо Handler для виконання в UI-потоці
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				try {
					// Створюємо WebView в UI-потоці
					WebView dummy = new WebView(getApplicationContext());
					// Можна навіть завантажити "порожній" рядок для повного прогріву
					dummy.loadData("<html></html>", "text/html", "utf-8");
					Log.d("Warmup", "WebView warmed up on UI thread");
				} catch (Exception e) {
					Log.e("Warmup", "Failed: " + e.getMessage());
				}
			}
		});
	}
	
	private Map<String, Object> getRules(String currentUrl){
		if (currentUrl != null) {
			for (int i = 0; i < sitepermis.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) sitepermis.get(i);
				String ruleUrl = String.valueOf(item.get("url"));
				if (!ruleUrl.isEmpty() && currentUrl.contains(ruleUrl)) {
					return item;
				}
			}
		}
		return null;
	}
	
	{
	}
	
	
	public void _refreshh() {
		_webViewActions(5, "");
	}
	
	
	public void _fonts() {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		sup.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		sdown.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		hide.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		refresh.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		button1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		openpanel.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		button3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		closeeditor.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		copyurl.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		pasteurl.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		pastegourl.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		widthm1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		widthp1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		heightm1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		heightp1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		add.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		closeall.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		selall.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		cururl.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		ssl.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		forcesearch.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		forceurl.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
		share.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/icons.ttf"), 0);
	}
	
	
	public void _ifex2() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		if (sf == null) {
			
			SketchwareUtil.showMessage(getApplicationContext(), "SharedPreferences Error");
			
		} else {
			if (!sf.contains("cssbg")) {
				sf.edit().putString("cssbg", "000000").commit();
			}
			if (!sf.contains("cssbgcol")) {
				sf.edit().putString("cssbgcol", "000000").commit();
			}
			if (!sf.contains("cssbgblend")) {
				sf.edit().putString("cssbgblend", "normal").commit();
			}
			if (!sf.contains("cssbgblur")) {
				sf.edit().putString("cssbgblur", "0.0").commit();
			}
			if (!sf.contains("cssbgbright")) {
				sf.edit().putString("cssbgbright", "1.0").commit();
			}
			if (!sf.contains("cssbgcontr")) {
				sf.edit().putString("cssbgcontr", "1.0").commit();
			}
			if (!sf.contains("cssbghue")) {
				sf.edit().putString("cssbghue", "0").commit();
			}
			if (!sf.contains("cssbginvert")) {
				sf.edit().putString("cssbginvert", "0.0").commit();
			}
			if (!sf.contains("cssbgopacity")) {
				sf.edit().putString("cssbgopacity", "0.0").commit();
			}
			if (!sf.contains("cssbgsatur")) {
				sf.edit().putString("cssbgsatur", "1.0").commit();
			}
			if (!sf.contains("cssbgsepia")) {
				sf.edit().putString("cssbgsepia", "0.0").commit();
			}
			if (!sf.contains("cssbgsize")) {
				sf.edit().putString("cssbgsize", "cover").commit();
			}
			if (!sf.contains("cssbgstrokecolor")) {
				sf.edit().putString("cssbgstrokecolor", "0000FFFF").commit();
			}
			if (!sf.contains("cssbgstrokesize")) {
				sf.edit().putString("cssbgstrokesize", "0.0").commit();
			}
			if (!sf.contains("cssbgstrokestyle")) {
				sf.edit().putString("cssbgstrokestyle", "solid").commit();
			}
			if (!sf.contains("csstextalign")) {
				sf.edit().putString("csstextalign", "left").commit();
			}
			if (!sf.contains("csstextblur")) {
				sf.edit().putString("csstextblur", "0.0").commit();
			}
			if (!sf.contains("csstextbordercolor")) {
				sf.edit().putString("csstextbordercolor", "FF0000FF").commit();
			}
			if (!sf.contains("csstextbordersize")) {
				sf.edit().putString("csstextbordersize", "0.0").commit();
			}
			if (!sf.contains("csstextborderstyle")) {
				sf.edit().putString("csstextborderstyle", "solid").commit();
			}
			if (!sf.contains("csstextbright")) {
				sf.edit().putString("csstextbright", "1.0").commit();
			}
			if (!sf.contains("csstextcontr")) {
				sf.edit().putString("csstextcontr", "1.0").commit();
			}
			if (!sf.contains("csstextfont")) {
				sf.edit().putString("csstextfont", "sans").commit();
			}
			if (!sf.contains("csstexthue")) {
				sf.edit().putString("csstexthue", "0").commit();
			}
			if (!sf.contains("csstextinvert")) {
				sf.edit().putString("csstextinvert", "0.0").commit();
			}
			if (!sf.contains("csstextline")) {
				sf.edit().putString("csstextline", "1.3").commit();
			}
			if (!sf.contains("csstextopacity")) {
				sf.edit().putString("csstextopacity", "1.0").commit();
			}
			if (!sf.contains("csstextsatur")) {
				sf.edit().putString("csstextsatur", "1.0").commit();
			}
			if (!sf.contains("csstextsepia")) {
				sf.edit().putString("csstextsepia", "0.0").commit();
			}
			if (!sf.contains("csstextshadowb")) {
				sf.edit().putString("csstextshadowb", "0.0").commit();
			}
			if (!sf.contains("csstextblend")) {
				sf.edit().putString("csstextblend", "normal").commit();
			}
			if (!sf.contains("csstextshadowc")) {
				sf.edit().putString("csstextshadowc", "FF00FFFF").commit();
			}
			if (!sf.contains("csstextshadowx")) {
				sf.edit().putString("csstextshadowx", "1.0").commit();
			}
			if (!sf.contains("csstextshadowy")) {
				sf.edit().putString("csstextshadowy", "1.0").commit();
			}
			if (!sf.contains("csstextsize")) {
				sf.edit().putString("csstextsize", "20").commit();
			}
			if (!sf.contains("csstextstrokecolor")) {
				sf.edit().putString("csstextstrokecolor", "00FFFFFF").commit();
			}
			if (!sf.contains("csstextstrokesize")) {
				sf.edit().putString("csstextstrokesize", "0.0").commit();
			}
			if (!sf.contains("csstextstyle")) {
				sf.edit().putString("csstextstyle", "initial").commit();
			}
			if (!sf.contains("csstexttrans")) {
				sf.edit().putString("csstexttrans", "initial").commit();
			}
			if (!sf.contains("csstextweight")) {
				sf.edit().putString("csstextweight", "500").commit();
			}
			if (!sf.contains("videopatterns")) {
				sf.edit().putString("videopatterns", "Пропустити через|Реклама •|Реклама закінчиться|Реклама [0-9]|Відео незабаром почне відтворюватися|Видео скоро начнется|Вы cможете перейти\\nк видео через").commit();
			}
			if (!sf.contains("skipvideoad")) {
				sf.edit().putString("skipvideoad", "1").commit();
			}
			if (!sf.contains("cb")) {
				sf.edit().putString("cb", "FF44BBFF").commit();
			}
			if (!sf.contains("tpc")) {
				sf.edit().putString("tpc", "FF44BBFF").commit();
			}
			if (!sf.contains("tc")) {
				sf.edit().putString("tc", "FF44BBFF").commit();
			}
			if (!sf.contains("jslog")) {
				sf.edit().putString("jslog", "0").commit();
			}
			if (!sf.contains("hidepass")) {
				sf.edit().putString("hidepass", "0").commit();
			}
			if (!sf.contains("hidepass2")) {
				sf.edit().putString("hidepass2", "0").commit();
			}
			if (!sf.contains("rgrad")) {
				sf.edit().putString("rgrad", "2").commit();
			}
			if (!sf.contains("grad")) {
				sf.edit().putString("grad", "2").commit();
			}
			if (!sf.contains("bgrad")) {
				sf.edit().putString("bgrad", "2").commit();
			}
			if (!sf.contains("strcou")) {
				sf.edit().putString("strcou", "2").commit();
			}
			if (!sf.contains("y")) {
				sf.edit().putString("y", "0.5").commit();
			}
			if (!sf.contains("x")) {
				sf.edit().putString("x", "0.5").commit();
			}
			if (!sf.contains("rad")) {
				sf.edit().putString("rad", "1").commit();
			}
			if (!sf.contains("rstrcou")) {
				sf.edit().putString("rstrcou", "2").commit();
			}
			if (!sf.contains("rx")) {
				sf.edit().putString("rx", "0.5").commit();
			}
			if (!sf.contains("ry")) {
				sf.edit().putString("ry", "0.5").commit();
			}
			if (!sf.contains("rrad")) {
				sf.edit().putString("rrad", "1").commit();
			}
			if (!sf.contains("bstrcou")) {
				sf.edit().putString("bstrcou", "2").commit();
			}
			if (!sf.contains("by")) {
				sf.edit().putString("by", "0.5").commit();
			}
			if (!sf.contains("bx")) {
				sf.edit().putString("bx", "0.5").commit();
			}
			if (!sf.contains("brad")) {
				sf.edit().putString("brad", "1").commit();
			}
			if (!sf.contains("rrou")) {
				sf.edit().putString("rrou", "0").commit();
			}
			if (!sf.contains("rsrou")) {
				sf.edit().putString("rsrou", "0").commit();
			}
			if (!sf.contains("srou")) {
				sf.edit().putString("srou", "0").commit();
			}
			if (!sf.contains("rrad")) {
				sf.edit().putString("rrad", "1").commit();
			}
			if (!sf.contains("bstrcou")) {
				sf.edit().putString("bstrcou", "2").commit();
			}
			if (!sf.contains("tm")) {
				sf.edit().putString("tm", "2").commit();
			}
			if (!sf.contains("rtm")) {
				sf.edit().putString("rtm", "2").commit();
			}
			if (!sf.contains("btm")) {
				sf.edit().putString("btm", "2").commit();
			}
			if (!sf.contains("totalads")) {
				sf.edit().putString("totalads", "0").commit();
			}
		}
	}
	
	
	public void _download(final WebView _w) {
		FileDownloader fileDownloader = new FileDownloader(this, _w);
	}
	
	
	public void _adsblocked() {
		SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		adsblockedd++;
		sf.edit().putString("totalads", String.valueOf((long)(Integer.parseInt(sf.getString("totalads", "")) + 1))).commit();
	}
	
	
	public void _onpagefail(final WebView _w, final String _e) {
		SketchwareUtil.showMessage(getApplicationContext(), _e);
		try{
			_Reopentab();
			((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
			final String tit = _w.getTitle();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try{
						if (tit!=null&&(!(tit.equals("bookmark.html") || tit.equals("about:blank")))) {
							if (sf.getString("incognito", "").equals("0")) {
								synchronized (history) {
									if (!history.isEmpty()) {
										HashMap<String, Object> lastItem = history.get(history.size() - 1);
										lastItem.put("name", tit);
									}
									FileUtil.writeFile(getFilesDir().getPath()+"/history.json", JsonUtils.toJson(history));
								}
							}
						}
						
					}catch(Exception e){
					}
				}
			}).start();
			
			_w.loadUrl(themebml);
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "What?! Error in error, excellent");
		}
	}
	
	
	public void _progresser(final WebView _w, final double _p) {
		try{
			final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			
			View parentView = (View) _w.getParent();
			if (parentView instanceof LinearLayout) {
				LinearLayout linearLayout = (LinearLayout) parentView;
				
				synchronized(tab) {
					if (tab.isEmpty()) return; // Вихід, якщо вкладки ще не ініціалізовані
				}
				
				List<HashMap<String, Object>> tabCopy;
				synchronized(tab) {
					tabCopy = new ArrayList<>(tab);
				}
				
				Object tag = linearLayout.getTag();
				if (tag != null) {
					String tagStr = tag.toString().replace("ll", "");
					for (int i = 0; i < tabCopy.size(); i++) {
						Object tabValue = tabCopy.get(i).get("tab");
						if (tabValue != null && tabValue.toString().equals(tagStr)) {
							if (url != null && title != null) {
								synchronized(tab) {
									for (int j = 0; j < tab.size(); j++) {
										if (tab.get(j).get("tab") != null && tab.get(j).get("tab").toString().equals(tagStr)) {
											tab.get(j).put("load", "l" + (int) _p);
											break;
										}
									}
								}
							}
							break;
						}
					}
				}
			}
			
			_webViewActions(102, "");
		}catch(Exception e){
			
		}
	}
	
	
	public void _prog11(final WebView _w) {
		try{
			final AtomicBoolean isu = new AtomicBoolean(false);
			
			ExtensionUtil extensionUtil = ExtensionUtil.getInstance(this);
			
			final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			
			Map<String, Object> r = getRules(_w.getUrl());
			
			int th = getSafeVal(r, "th");
			boolean th2 = (th == 1) || (th == 2 && sf.getString("themepage", "").equals("1"));
			if(th2)_w.loadUrl(themebml);
			if (_w.getUrl().contains(getFilesDir().getPath().concat("/bookmark.html"))) {
				_w.loadUrl(designbmlcache);
			}
			try{
				View parentView = (View) _w.getParent();
				if (parentView instanceof LinearLayout) {
					LinearLayout linearLayout = (LinearLayout) parentView;
					Object tag = linearLayout.getTag();
					if (tag != null) {
						for (int i = 0; i < (int)(tab.size()); i++) {
							if (tab.get((int)i).get("tab").toString().equals(tag.toString().replace("ll",""))) {
								tab.get((int)i).put("url", _w.getUrl());
								tab.get((int)i).put("name", _w.getTitle());
								break;
							}
						}
					}
				}
				
			}catch(Exception e){
				
			}
			((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
			sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
			extensionUtil.onPageStarted(_w, _w.getUrl());
		}catch(Exception e){
			
		}
	}
	
	
	public void _webViewActions(final double _a, final String _s) {
		SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		int activeIndex = 0;
		try {
			activeIndex = Integer.parseInt(sf.getString("activetab", "0"));
		} catch (Exception e) {}
		
		_webViewActionsForTab((int)_a, _s, activeIndex);
	}
	private void _webViewActionsForTab(int action, String _s, int targetPosition) {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		// Перевіряємо валідність targetPosition (замість зчитування sf)
		int _position = targetPosition;
		
		if (tab == null || tab.isEmpty() || _position < 0 || _position >= tab.size()) {
			android.util.Log.e("WeekBrowser_DEBUG", "ABORT: Invalid position " + _position);
			return;
		}
		
		// Отримуємо дані цільової вкладки
		HashMap<String, Object> tabData = tab.get(_position);
		String tabUrl = tabData.containsKey("url") && tabData.get("url") != null ? tabData.get("url").toString() : "";
		String tabName = tabData.containsKey("name") && tabData.get("name") != null ? tabData.get("name").toString() : tabUrl;
		
		// ==========================================
		// ВІД'ЄМНІ ДІЇ (action < 0)
		// ==========================================
		if (action < 0) {
			try {
				switch(action) {
					case -1:
					intent.setClass(getApplicationContext(), BookmarksActivity.class);
					intent.setData(Uri.parse(tabUrl));
					intent.putExtra("n", tabName);
					intent.putExtra("m", _getMultilink());
					startActivity(intent);
					break;
					
					case -2:
					if (searchpan.getVisibility() == View.GONE) {
						searchpan.setVisibility(View.VISIBLE);
						Animator2.anim(getApplicationContext(), searchpan, 2, 300);
						_webViewActionsForTab(30, search.getText().toString(), _position);
					} else {
						searchpan.setVisibility(View.GONE);
						_webViewActionsForTab(30, "", _position);
					}
					break;
					
					case -3:
					dial.setTitle("?");
					dial.setMessage(getString(R.string.want_exit));
					dial.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							finish();
						}
					});
					dial.setNegativeButton(getString(R.string.no), null);
					dial.create().show();
					break;
					
					case -4:
					intent.setClass(getApplicationContext(), HistoryActivity.class);
					startActivity(intent);
					break;
					
					case -5:
					if (linear14.getVisibility() == View.VISIBLE) {
						linear14.setVisibility(View.GONE);
					} else {
						linear14.setVisibility(View.VISIBLE);
						Animator2.anim(getApplicationContext(), linear14, 1, 300);
					}
					break;
					
					case -6:
					is1window = !is1window; 
					if (!is1window) {
						try { windowpos = JsonUtils.SO(sf.getString("windows", "")); } catch(Exception e) {}
						initAllWindowsConfigurations();
						windowsmanager.setVisibility(View.GONE);
						sf.edit().putString("windows", JsonUtils.toJson(windowpos)).commit();
					} else {
						windowsmanager.setVisibility(View.VISIBLE);
						Animator2.anim(getApplicationContext(), windowsmanager, 2, 300);
						initAllWindowsConfigurations(); 
					}
					_Reopentab();
					_webViewActionsForTab(-27, sf.getString("dp", ""), _position);
					break;
					
					case -7:
					if (linear9.getVisibility() == View.VISIBLE) {
						linear9.setVisibility(View.GONE);
					} else {
						linear9.setVisibility(View.VISIBLE);
						Animator2.anim(getApplicationContext(), linear9, 2, 300);
						if (!posshow) {
							pos1.setText("(".concat(sf.getString("pos1x", "").concat("; ".concat(sf.getString("pos1y", "").concat(")")))));
							pos2.setText("(".concat(sf.getString("pos2x", "").concat("; ".concat(sf.getString("pos2y", "").concat(")")))));
							pos3.setText("(".concat(sf.getString("pos3x", "").concat("; ".concat(sf.getString("pos3y", "").concat(")")))));
							posshow = true;
						}
					}
					break;
					
					case -8:
					_closetab(_position);
					break;
					
					case -9:
					_webViewActionsForTab(-30, "", _position);
					_webViewActionsForTab(-26, "", _position);
					break;
					
					case -10:
					notFirst = true;
					_newTab(sf.getString("firstpage", ""), true);
					break;
					
					case -11:
					notFirst = true;
					_newTab(sf.getString("firstpage", ""), false);
					break;
					
					case -13:
					closeOtherTabs(sf, _position);
					break;
					
					case -14:
					duplicateTab(_position);
					break;
					
					case -15:
					if (tabData == null || !tabData.containsKey("tab") || tabData.get("tab") == null) {
						break;
					}
					
					String tabToUnload = tabData.get("tab").toString();
					String tagToMatch = "ll" + tabToUnload;
					
					ResourceManager rm15 = ResourceManager.getInstance();
					LinearLayout container = rm15.getContainerForTab(tagToMatch);
					if (container != null) {
						destroyWebViewInLayout(container);
					}
					
					switchToBestAliveTab(tabToUnload);
					break;
					
					case -16:
					unloadTab(sf, _position, true);
					_Reopentab();
					if (tabscont != null && tabscont.getAdapter() != null) {
						((BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
					}
					break;
					
					case -17:
					createShortcut(tabName, tabUrl);
					break;
					
					case -18:
					final int finalPosition = _position;
					AdBlocker.getInstance(this).showBlockDialog(this, tabUrl, new Runnable() {
						@Override
						public void run() {
							_closetab(finalPosition);
						}
					});
					break;
					
					case -19:
					copyToClipboard(tabName, R.string.copied);
					break;
					
					case -20:
					copyToClipboard(tabUrl, R.string.copied);
					break;
					
					case -21:
					copyToClipboard(tabName + "\n────────\n" + tabUrl, R.string.copied);
					break;
					
					case -22:
					shareContent(tabName, R.string.sharewith);
					break;
					
					case -23:
					shareContent(tabUrl, R.string.sharewith);
					break;
					
					case -24:
					shareContent(tabName + "\n────────\n" + tabUrl, R.string.sharewith);
					break;
					
					case -25:
					loadAll(JSutil.replaceInstruction(_s));
					break;
					
					case -26:
					openOrRestoreTab(_position);
					break;
					
					case -27:
					final int parsedS = Integer.parseInt(_s);
					final boolean isPC = sf.getString("pc", "").equals("1");
					final float density = this.getResources().getDisplayMetrics().density;
					final int finalScale = (int) (parsedS * (isPC ? 5 : 10) * density);
					updateWebViewsScale(finalScale);
					break;
					
					case -28:
					updateWindowsLayout();
					break;
					
					case -29:
					destroyAllTabs();
					break;
					
					case -30:
					unloadTab(sf, _position, false);
					break;
					
					case -999:
					_Reopentab();
					break;
					
					default:
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} 
		// ==========================================
		// ДОДАТНІ ДІЇ (action >= 0)
		// ==========================================
		else {
			try {
				if (action == 103 && !is1window) return;
				
				if (tabData == null || !tabData.containsKey("tab") || tabData.get("tab") == null) {
					return;
				}
				
				String tabTag = "ll" + tabData.get("tab").toString();
				ResourceManager rm = ResourceManager.getInstance();
				
				// Шукаємо WebView та Контейнер саме цільової вкладки
				WebView targetWebView = rm.getWebViewForTab(tabTag);
				LinearLayout ll = rm.getContainerForTab(tabTag);
				
				// Якщо для виконання дії потрібен WebView, а вкладка вивантажена — відновлюємо її!
				if (targetWebView == null) {
					// Дії 100-102 лише оновлюють заголовки/іконки UI; якщо WebView вивантажено, скасовуємо операцію
					if (action >= 100 && action <= 102) return;
					
					openOrRestoreTab(_position);
					targetWebView = rm.getWebViewForTab(tabTag);
					ll = rm.getContainerForTab(tabTag);
					
					if (targetWebView == null) {
						android.util.Log.e("WeekBrowser", "Неможливо створити WebView для вкладки: " + tabTag);
						return; 
					}
				}
				
				if (ll != null && action == 103) {
					ll.bringToFront();
					updateWindowsLayout(ll);
				}
				
				switch(action) {
					case 0:
					int mode = Integer.parseInt(sf.getString("srhbar", "0"));
					switch(mode) {
						case 0:
						autocomplete1.setText(Formater.shrink(targetWebView.getTitle(), 200));
						break;
						case 1:
						boolean error = false;
						try {
							autocomplete1.setText(UrlHelper.decode(Formater.shrink(_extractDomain(targetWebView.getUrl()), 200)));
						} catch(Exception e) {
							try {
								autocomplete1.setText(Formater.shrink(_extractDomain(targetWebView.getUrl()), 200));
							} catch (Exception f){
								error = true;
							}
						}
						if(!error) break;
						case 2:
						try {
							autocomplete1.setText(UrlHelper.decode(Formater.shrink(targetWebView.getUrl(), 200)));
						} catch(Exception e) {
							autocomplete1.setText(Formater.shrink(targetWebView.getUrl(), 200));
						}
						break;
					}
					break;
					
					case 1:
					if (targetWebView.canGoBack()) targetWebView.goBack();
					break;
					
					case 2:
					if (targetWebView.canGoForward()) targetWebView.goForward();
					break;
					
					case 3:
					targetWebView.scrollTo(0, 0);
					break;
					
					case 4:
					targetWebView.scrollTo(0, Integer.MAX_VALUE);
					break;
					
					case 5:
					if (targetWebView.getProgress() == 100) {
						_setAll(targetWebView);
						targetWebView.reload();
					} else {
						targetWebView.stopLoading();
					}
					break;
					
					case 6:
					{
						String url = targetWebView.getUrl();
						String host = url != null ? Uri.parse(url).getHost() : null;
						String errorCode = host != null && rm != null ? rm.getSslErrorForTab(host) : null;
						SslCertificate cert = targetWebView.getCertificate();
						
						String message = SslMessageBuilder.build(MainActivity.this, url, cert, errorCode);
						
						AlertDialog.Builder ssld = new AlertDialog.Builder(MainActivity.this);
						ssld.setTitle("SSL");
						ssld.setMessage(message);
						ssld.setPositiveButton("OK", null);
						
						AlertDialog dddddd = ssld.create();
						RenderUtils.setupDialogDim(MainActivity.this, dddddd);
						dddddd.show();
					}
					break;
					
					case 7:
					showTabHistoryDialog(_position);
					break;
					
					case 10:
					_setAll(targetWebView);
					targetWebView.loadUrl(_s);
					holdsearchtext = false;
					break;
					
					case 11:
					wwpos = targetWebView.getScrollX();
					break;
					
					case 12:
					wwpos = targetWebView.getScrollY();
					break;
					
					case 13:
					_setAll(targetWebView);
					targetWebView.loadUrl(sf.getString("firstpage", ""));
					holdsearchtext = false;
					break;
					
					case 14:
					targetWebView.loadUrl("javascript:(function() {var videos = document.querySelectorAll('video, [src*=\".mp4\"], [src*=\".webm\"],[src*=\".ogv\"],[type*=\"video\"]');for (var i = 0; i < videos.length; i++) {var video = videos[i];if (typeof video.currentTime !== 'undefined') {video.currentTime = 65535;}}})();");
					holdsearchtext = false;
					break;
					
					case 20:
					float density = this.getResources().getDisplayMetrics().density;
					int baseMultiplier = sf.getString("pc","").equals("1") ? 5 : 10;
					int finalScale = (int) (Integer.parseInt(_s) * baseMultiplier * density);
					targetWebView.setInitialScale(finalScale);
					break;
					
					case 21:
					targetWebView.getSettings().setTextZoom(Integer.parseInt(_s)*10);
					break;
					
					case 22:
					targetWebView.getSettings().setMinimumFontSize(Integer.parseInt(_s));
					break;
					
					case 30:
					targetWebView.findAllAsync(_s);
					break;
					
					case 31:
					targetWebView.findNext(false);
					break;
					
					case 32:
					targetWebView.findNext(true);
					break;
					
					case 100:
					title.setText(Formater.shrink(targetWebView.getTitle(), 100));
					URL = targetWebView.getUrl();
					break;
					
					case 101:
					load = targetWebView.getProgress();
					break;
					
					case 102:
					String currentUrl = targetWebView.getUrl();
					if (currentUrl == null) return;
					String hostName = Uri.parse(currentUrl).getHost();
					String sslError = hostName != null && rm != null ? rm.getSslErrorForTab(hostName) : null;
					
					byte sslBtn = 0x00;
					if (sslError != null) {
						sslBtn = 0x01;
					} else {
						if (currentUrl.startsWith("https://")) {
							sslBtn = 0x00;
						} else if (currentUrl.startsWith("http://")) {
							sslBtn = 0x02;
						} else {
							sslBtn = 0x04;
						}
					}
					if (sslBtn != pSslBtn) {
						switch(sslBtn) {
							case 0x00:
							ssl.setSkinKey("bbg", false);
							ssl.setText("");
							break;
							case 0x01:
							case 0x02:
							ssl.setSkinKey("rbg", false);
							ssl.setText(sslBtn == 0x01 ? "" : "");
							break;
							case 0x04:
							ssl.setSkinKey("bbg", false);
							ssl.setText("( i )");
							break;
						}
						pSslBtn = sslBtn;
					}
					break;
					
					case 103:
					if (ll != null) {
						ll.bringToFront();
						updateWindowsLayout(ll);
					}
					break;
					
					case 200:
					case 201:
					{
						int at = Integer.parseInt(sf.getString("activetab", "0"));
						int tt = tab.size();
						int nt = at + ((action - 200) * 2) - 1;
						if (nt < 0) {
							nt = tt - 1;
						} else if (nt >= tt) {
							nt = 0;
						}
						sf.edit().putString("activetab", String.valueOf(nt)).commit();
						_Reopentab();
						int __position = Integer.parseInt(sf.getString("activetab", "0"));
						openOrRestoreTab(__position);
					}
					break;
					
					case 301:
					case 302:
					case 303:
					{
						int index = action - 300;
						sf.edit().putString("pos" + index + "x", String.valueOf((long)(targetWebView.getScrollX()))).commit();
						sf.edit().putString("pos" + index + "y", String.valueOf((long)(targetWebView.getScrollY()))).commit();
						if(index == 1) pos1.setText("(" + sf.getString("pos1x", "") + "; " + sf.getString("pos1y", "") + ")");
						if(index == 2) pos2.setText("(" + sf.getString("pos2x", "") + "; " + sf.getString("pos2y", "") + ")");
						if(index == 3) pos3.setText("(" + sf.getString("pos3x", "") + "; " + sf.getString("pos3y", "") + ")");
					}
					break;
					
					case 401:
					case 402:
					case 403:
					{
						int index = action - 400;
						String px = sf.getString("pos" + index + "x", "0");
						String py = sf.getString("pos" + index + "y", "0");
						targetWebView.scrollTo(Integer.parseInt(px.isEmpty() ? "0" : px), Integer.parseInt(py.isEmpty() ? "0" : py));
					}
					break;
					
					case 700:
					if (focused) {
						try {
							autocomplete1.setText(UrlHelper.decode(Formater.shrink(targetWebView.getUrl(), 10000)));
						} catch(Exception e) {
							autocomplete1.setText(Formater.shrink(targetWebView.getUrl(), 10000));
						}
					} else {
						autocomplete1.setText(targetWebView.getTitle());
					}
					break;
					
					case 701:
					if ((targetWebView.getUrl().equals("file://" + getFilesDir().getPath() + ("/bookmark.json")) ||
					targetWebView.getUrl().equals(sf.getString("firstpage", ""))) && 
					sf.getString("searchpan", "").equals("1")) {
						srhhis.setVisibility(View.VISIBLE);
					} else {
						srhhis.setVisibility(View.GONE);
					}
					break;
					
					case 998:
					targetWebView.onPause();
					break;
					
					case 999:
					targetWebView.onResume();
					break;
					
					case 1000:
					targetWebView.pauseTimers();
					loadAll("", 1);
					break;
					
					case 1001:
					targetWebView.resumeTimers();
					loadAll("", 2);
					targetWebView.clearFocus();
					final WebView finalTv = targetWebView;
					targetWebView.postDelayed(() -> {
						finalTv.requestFocus(View.FOCUS_DOWN);
						finalTv.scrollBy(0, 0); 
					}, 150);
					break;
					
					case 1998:
					targetWebView.getSettings().setBlockNetworkImage(false);
					targetWebView.getSettings().setLoadsImagesAutomatically(true);
					break;
					
					case 1999:
					tempurl = targetWebView.getUrl();
					new Thread(() -> {
						_bgexec();
						runOnUiThread(() -> _postexec());
					}).start();
					break;
					
					case 2000:
					final WebView saveWebView = targetWebView;
					new Handler(Looper.getMainLooper()).postDelayed(() -> {
						saveWebView.saveWebArchive(dataHolder.getData("savemht"), false, value -> {
							if (value != null) {
								Toast.makeText(MainActivity.this, "💾: " + value, Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(MainActivity.this, "💾❌", Toast.LENGTH_SHORT).show();
							}
						});
						dataHolder.setData("savemht", "");
					}, 1500);
					break;
					
					case 2001:
					saveAsHTML(sf, targetWebView);
					break;
					
					case 2002:
					showSaveDialog(sf, targetWebView);
					break;
					
					case 100001:
					if (targetWebView.canGoBack()) {
						targetWebView.goBack();
					} else {
						if (sf.getString("preventclose", "").equals("0")) {
							if (tab.size() > 1) {
								_closetab(_position);
							} else {
								_wantbackq();
							}
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), getString(R.string.preventedclose));
						}
					}
					break;
					
					default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Рекурсивний метод для безпечного пошуку всіх WebView
	private void updateWebViewsScale(int scale) {
		ResourceManager rm = ResourceManager.getInstance();
		for (int i = 0; i < tab.size(); i++) {
			String tabValue = getTabValue(i);
			if (tabValue.isEmpty()) continue;
			
			WebView webView = rm.getWebViewForTab("ll" + tabValue);
			if (webView != null) {
				webView.setInitialScale(scale);
				webView.invalidate();
			}
		}
	}
	
	private void closeOtherTabs(SharedPreferences sf, int position) {
		if (tab == null || position < 0 || position >= tab.size()) {
			return;
		}
		
		String activeTabValue = getTabValue(position);
		if (activeTabValue.isEmpty()) return;
		
		String tagToMatch = "ll" + activeTabValue;
		
		// 1. Знищуємо всі View з контейнера, крім target
		destroyWebViews(tagToMatch, true);
		
		// 2. Видаляємо всі інші вкладки з масиву tab
		removeOtherTabsFromData(activeTabValue);
		
		// 3. Тепер єдина вкладка, яка залишилася, гарантовано має індекс 0
		sf.edit().putString("activetab", "0").commit();
		
		// 4. Оновлюємо UI списку вкладок та SharedPreferences
		updateTabData(sf);
		
		// 5. Відновлюємо/показуємо її
		openOrRestoreTab(0);
		_Reopentab();
	}
	
	public void initAllWindowsConfigurations() {
		ResourceManager rm = ResourceManager.getInstance();
		for (int i = 0; i < tab.size(); i++) {
			String tabValue = getTabValue(i);
			if (tabValue.isEmpty()) continue;
			
			LinearLayout container = rm.getContainerForTab("ll" + tabValue);
			if (container instanceof SkinLinearLayout) {
				initWindowsConfigurations((SkinLinearLayout) container);
			}
		}
	}
	
	public void destroyAllTabs() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		try {
			time = 0;
			notFirst = true;
			
			for (int i = tab.size() - 1; i >= 0; i--) {
				String tabValue = getTabValue(i);
				if (!tabValue.isEmpty()) {
					destroyTabByTag("ll" + tabValue);
				}
			}
			
			tabscontainer.removeAllViews();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tab.clear();
		notFirst = true;
		_newTab(sf.getString("firstpage", ""), true);
	}
	
	private void duplicateTab(int position) {
		if (tab != null && position >= 0 && position < tab.size()) {
			HashMap<String, Object> tabData = tab.get(position);
			if (tabData != null && tabData.containsKey("url") && tabData.get("url") != null) {
				String tabUrl = tabData.get("url").toString();
				_newTab(tabUrl, true);
			}
		}
	}
	
	private void unloadTab(SharedPreferences sf, int targetPosition, boolean unloadOthers) {
		if (tab == null || targetPosition < 0 || targetPosition >= tab.size()) return;
		
		// Отримуємо ДІЙСНО активний індекс із SharedPreferences
		int currentActiveIndex = 0;
		try {
			currentActiveIndex = Integer.parseInt(sf.getString("activetab", "0"));
		} catch (Exception e) {}
		
		if (unloadOthers) {
			// Вивантажити всі, КРІМ targetPosition
			String targetTabValue = getTabValue(targetPosition);
			destroyWebViews("ll" + targetTabValue, true); // true = знищити всі, КРІМ targetTabValue
			
			// Якщо поточна активна вкладка була серед вивантажених, робимо targetPosition активною
			if (currentActiveIndex != targetPosition) {
				sf.edit().putString("activetab", String.valueOf(targetPosition)).commit();
				openOrRestoreTab(targetPosition);
			} else {
				_Reopentab();
			}
		} else {
			// Вивантажити ТІЛЬКИ targetPosition
			String targetTabValue = getTabValue(targetPosition);
			destroyWebViews("ll" + targetTabValue, false); // false = знищити ТІЛЬКИ targetTabValue
			
			// Перевіряємо: ми вивантажили ТУ вкладку, яку зараз дивимося?
			if (currentActiveIndex == targetPosition) {
				// Шукаємо іншу живу вкладку для перемикання
				switchToBestAliveTab(targetTabValue);
			} else {
				// Ми вивантажили ФОНОВУ вкладку: екран не міняємо, просто оновлюємо списки
				_Reopentab();
			}
		}
	}
	
	private void switchToBestAliveTab(String unloadedTabValue) {
		if (tab == null || tab.isEmpty()) return;
		if (tab.size() == 1) {
			// Якщо залишилася тільки одна вкладка, відкриваємо її індекс 0
			openOrRestoreTab(0);
			return;
		}
		
		int bestIndex = -1;
		int maxTime = -1;
		ResourceManager rm = ResourceManager.getInstance();
		
		// 1. Шукаємо ЖИВУ вкладку з найвищим time серед решти
		for (int i = 0; i < tab.size(); i++) {
			String tabValue = getTabValue(i);
			if (tabValue.isEmpty() || tabValue.equals(unloadedTabValue)) continue;
			
			// ВАЖЛИВО: Перевіряємо за єдиним форматом тегу "ll" + ID
			if (rm.getWebViewForTab("ll" + tabValue) != null) {
				int tabTime = getTabTimeSafely(i);
				if (tabTime > maxTime) {
					maxTime = tabTime;
					bestIndex = i;
				}
			}
		}
		
		// 2. Якщо ВСІ інші вкладки мертві/вивантажені — беремо будь-яку з найбільшим time
		if (bestIndex == -1) {
			maxTime = -1;
			for (int i = 0; i < tab.size(); i++) {
				String tabValue = getTabValue(i);
				if (tabValue.isEmpty() || tabValue.equals(unloadedTabValue)) continue;
				
				int tabTime = getTabTimeSafely(i);
				if (tabTime > maxTime) {
					maxTime = tabTime;
					bestIndex = i;
				}
			}
		}
		
		// 3. Захист: якщо нічого не знайшли, беремо першу ліпшу вкладку (індекс 0)
		if (bestIndex == -1) {
			bestIndex = 0;
		}
		
		// Перемикаємося тільки якщо цей індекс у межах масиву
		if (bestIndex >= 0 && bestIndex < tab.size()) {
			SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			sf.edit().putString("activetab", String.valueOf(bestIndex)).commit();
			openOrRestoreTab(bestIndex); 
		}
		_Reopentab();
	}
	
	private int getTabTimeSafely(int index) {
		try {
			HashMap<String, Object> tabData = tab.get(index);
			if (tabData != null && tabData.containsKey("time") && tabData.get("time") != null) {
				return Integer.parseInt(tabData.get("time").toString());
			}
		} catch (Exception e) {}
		return 0;
	}
	
	private void createShortcut(String name, String url) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), ShortcutActivity.class);
		intent.putExtra("name", name);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	private void copyToClipboard(String text, int messageResId) {
		try {
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			if (clipboard != null) {
				clipboard.setPrimaryClip(ClipData.newPlainText("clipboard", text));
			}
			if (messageResId != 0) {
				SketchwareUtil.showMessage(getApplicationContext(), getString(messageResId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void shareContent(String text, int chooserTitleResId) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(shareIntent, getString(chooserTitleResId)));
	}
	
	private void showTabHistoryDialog(int tabIndex) {
		if (tabIndex >= 0 && tabIndex < tab.size()) {
			HashMap<String, Object> tabData = tab.get(tabIndex);
			Toast.makeText(this, "Історія вкладки " + tabIndex, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void saveAsHTML(SharedPreferences sf, WebView webView) {
		Toast.makeText(this, "Збереження як HTML ще не реалізовано", Toast.LENGTH_SHORT).show();
	}
	
	private void showSaveDialog(SharedPreferences sf, WebView webView) {
		new AlertDialog.Builder(this)
		.setTitle("Зберегти як...")
		.setItems(new String[]{"MHT", "HTML"}, (dialog, which) -> {
			if (which == 0) {
				_webViewActions(2000, "");
			} else {
				_webViewActions(2001, "");
			}
		})
		.show();
	}
	
	// Допоміжні методи для нових функцій
	private String getTabValue(int position) {
		if (tab != null && position >= 0 && position < tab.size()) {
			HashMap<String, Object> tabData = tab.get(position);
			Object tabObj = tabData.get("tab");
			return tabObj != null ? tabObj.toString() : "";
		}
		return "";
	}
	
	private void destroyTabByTag(String tagToMatch) {
		ResourceManager rm = ResourceManager.getInstance();
		WebView webView = rm.getWebViewForTab(tagToMatch);
		
		// 1. Знаходимо фантомне вікно безпосередньо в UI-контейнері за тегом
		// Це працює миттєво і не вимагає від нас ручного циклу for
		View layoutToRemove = null;
		if (tabscontainer != null) {
			layoutToRemove = tabscontainer.findViewWithTag(tagToMatch);
		}
		
		// Fallback: якщо раптом його там ще/вже немає, беремо з ResourceManager
		if (layoutToRemove == null) {
			layoutToRemove = rm.getContainerForTab(tagToMatch);
		}
		
		// 2. Безпечно знищуємо WebView
		if (webView != null) {
			webView.stopLoading();
			webView.clearHistory();
			webView.removeAllViews();
			webView.setWebChromeClient(null);
			webView.setWebViewClient(null);
			webView.loadUrl("about:blank");
			webView.onPause();
			
			// Гарантовано відриваємо WebView від будь-якого батька
			if (webView.getParent() instanceof android.view.ViewGroup) {
				((android.view.ViewGroup) webView.getParent()).removeView(webView);
			}
			webView.destroy();
		}
		
		// 3. Фізично видаляємо вікно-пустушку з макета
		if (layoutToRemove != null && tabscontainer != null) {
			tabscontainer.removeView(layoutToRemove);
		}
		
		// 4. Очищаємо пам'ять
		rm.removeTab(tagToMatch);
	}
	
	private void destroyWebViews(String tagToMatch, boolean excludeMatch) {
		if (tabscontainer == null || tab == null) return;
		
		for (int i = tab.size() - 1; i >= 0; i--) {
			String tabValue = getTabValue(i);
			if (tabValue.isEmpty()) continue;
			
			String currentTag = "ll" + tabValue;
			boolean shouldDestroy = excludeMatch ? !tagToMatch.equals(currentTag) : tagToMatch.equals(currentTag);
			
			if (shouldDestroy) {
				destroyTabByTag(currentTag);
			}
		}
	}
	
	private void loadAll(String url) {
		loadAll(url, 0);
	}
	
	private void loadAll(String url, int action) {
		ResourceManager rm = ResourceManager.getInstance();
		for (int i = 0; i < tab.size(); i++) {
			String tabValue = getTabValue(i);
			if (tabValue.isEmpty()) continue;
			
			WebView wv = rm.getWebViewForTab("ll" + tabValue);
			if (wv != null) {
				switch (action) {
					case 0:
					wv.loadUrl(url);
					break;
					case 1:
					wv.onPause();
					break;
					case 2:
					wv.onResume();
					break;
				}
			}
		}
	}
	
	private void destroyWebViewInLayout(LinearLayout layout) {
		if (layout == null) return;
		
		for (int j = layout.getChildCount() - 1; j >= 0; j--) {
			View nestedChild = layout.getChildAt(j);
			if (nestedChild instanceof WebView) {
				WebView webView = (WebView) nestedChild;
				webView.stopLoading();
				webView.clearHistory();
				webView.removeAllViews();
				webView.setWebChromeClient(null);
				webView.setWebViewClient(null);
				webView.loadUrl("about:blank");
				webView.onPause();
				
				layout.removeView(webView);
				
				if (layout.getParent() instanceof ViewGroup) {
					((ViewGroup) layout.getParent()).removeView(layout);
				}
				
				if (layout.getTag() != null) {
					ResourceManager.getInstance().removeTab(layout.getTag().toString());
				}
				
				webView.destroy();
				break;
			}
		}
	}
	
	private void removeOtherTabsFromData(String activeTabValue) {
		if (tab == null) return;
		
		for (int i = tab.size() - 1; i >= 0; i--) {
			HashMap<String, Object> tabData = tab.get(i);
			Object tabObj = tabData.get("tab");
			String tabValue = tabObj != null ? tabObj.toString() : "";
			if (!tabValue.equals(activeTabValue)) {
				tab.remove(i);
			}
		}
	}
	
	private void updateTabData(SharedPreferences sf) {
		_tabupd();
		if (tabscont != null && tabscont.getAdapter() != null) {
			((BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
		}
		
		try {
			updateTabTimestamps(sf);
			new Thread(() -> {
				ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
				if (dataHolder != null) {
					dataHolder.setData("ml", _getMultilink());
				}
			}).start();
		} catch (Exception e) {
			notFirst = true;
			_newTab(sf.getString("firstpage", ""), true);
			new Thread(() -> {
				ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
				if (dataHolder != null) {
					dataHolder.setData("ml", _getMultilink());
				}
			}).start();
		}
	}
	
	private void updateTabTimestamps(SharedPreferences sf) {
		if (tab == null) return;
		
		int maxtime = 0;
		int maxind = 0;
		for (int i = 0; i < tab.size(); i++) {
			try {
				HashMap<String, Object> tabData = tab.get(i);
				int currentTime = Integer.parseInt(tabData.get("time").toString());
				if (maxtime < currentTime) {
					maxtime = currentTime;
					maxind = i;
				}
			} catch (Exception g) {
				// Ігноруємо помилки перетворення
			}
		}
		time++;
		if (maxind < tab.size()) {
			tab.get(maxind).put("time", String.valueOf(time));
		}
		sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
	}
	
	private void setupDialogDim(AlertDialog dialog) {
		RenderUtils.setupDialogDim(MainActivity.this, dialog);
	}
	
	// Метод findWebViewByTag залишаємо як в старому коді
	private LinearLayout findLinearByTag(String tag) {
		return ResourceManager.getInstance().getContainerForTab(tag);
	}
	
	private WebView findWebView(LinearLayout childLinearLayout) {
		if (childLinearLayout == null || childLinearLayout.getTag() == null) return null;
		return ResourceManager.getInstance().getWebViewForTab(childLinearLayout.getTag().toString());
	}
	
	// 1. Загальний метод без параметрів (для перемикання або оновлення всієї сітки)
	public void updateWindowsLayout() {
		// Передаємо null, бо в режимі сітки нам не важливо, хто саме на весь екран
		updateWindowsLayout(null);
	}
	
	// 2. Основний метод, який приймає безпосередньо активний LinearLayout
	public void updateWindowsLayout(LinearLayout activeLayout) {
		if (!Foreground) return;
		
		int containerWidth = tabscontainer.getWidth();
		int containerHeight = tabscontainer.getHeight();
		
		// 1. Захист від ділення на 0 та схлопування WebView
		if (containerWidth == 0 || containerHeight == 0) {
			tabscontainer.post(() -> updateWindowsLayout(activeLayout));
			return;
		}
		
		int cellWidth = containerWidth / 4;
		int cellHeight = containerHeight / 4;
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		ResourceManager rm = ResourceManager.getInstance();
		
		// 2. Прямий обхід вкладок замість Stack
		for (int i = 0; i < tab.size(); i++) {
			HashMap<String, Object> tabItem = tab.get(i);
			if (tabItem == null || !tabItem.containsKey("tab") || tabItem.get("tab") == null) {
				continue;
			}
			
			String windowId = tabItem.get("tab").toString();
			String tabTag = "ll" + windowId;
			
			LinearLayout windowLayout = rm.getContainerForTab(tabTag);
			if (windowLayout == null) continue;
			
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) windowLayout.getLayoutParams();
			if (lp == null) continue;
			
			if (!is1window) {
				// РЕЖИМ ОДНОГО ВІКНА (одне вікно на весь екран)
				_setTopPadding(windowLayout, 0);
				lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
				lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
				lp.leftMargin = 0;
				lp.topMargin = 0;
				
				windowLayout.setTranslationX(0);
				windowLayout.setTranslationY(0);
				
				if (activeLayout != null) {
					windowLayout.setVisibility(windowLayout == activeLayout ? View.VISIBLE : View.GONE);
				}
			} else {
				// БАГАТОВІКОННИЙ РЕЖИМ (сітка вікон)
				windowLayout.setVisibility(View.VISIBLE);
				_setTopPadding(windowLayout, winholdsize);
				
				int ti = tabItem.get("x") != null ? Integer.parseInt(tabItem.get("x").toString().replace("s", "")) : 0;
				int th = tabItem.get("y") != null ? Integer.parseInt(tabItem.get("y").toString().replace("s", "")) : 0;
				int w  = tabItem.get("w") != null ? Integer.parseInt(tabItem.get("w").toString().replace("s", "")) : 2;
				int h  = tabItem.get("h") != null ? Integer.parseInt(tabItem.get("h").toString().replace("s", "")) : 2;
				
				lp.width = w * cellWidth;
				lp.height = h * cellHeight;
				lp.leftMargin = ti * cellWidth;
				lp.topMargin = th * cellHeight;
				
				windowLayout.setTranslationX(0);
				windowLayout.setTranslationY(0);
			}
			
			windowLayout.setLayoutParams(lp);
			windowLayout.requestLayout();
			
			// Перемальовуємо WebView цієї вкладки
			WebView wv = rm.getWebViewForTab(tabTag);
			if (wv != null) {
				wv.requestLayout();
			}
		}
		
		if (is1window) {
			new Thread(() -> {
				dataHolder.setData("ml", _getMultilink());
			}).start();
			sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
		}
	}
	
	{
	}
	
	
	public void _newTab(final String _url, final boolean _move) {
		try {
			final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			
			// Викликаємо метод з 5 параметрами (передаємо true для завантаження URL)
			createWebViewTab(_url, new HashMap<String, Object>(), true, _move, true);
			
			notFirst = true;
			
			new Thread(() -> {
				if (dataHolder != null) {
					dataHolder.setData("ml", _getMultilink());
				}
			}).start();
			
			if (tabscont != null && tabscont.getAdapter() != null) {
				((BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
			}
			
			sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
			_Reopentab();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public double _totalMem() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		
		// Загальна кількість доступної пам'яті
		return memoryInfo.totalMem / 1048576L; // в MB
		
	}
	
	
	public void _ramoptimizer() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		try{
			if (usedMemoryMB > Integer.parseInt(sf.getString("maxram", ""))) {
				_forceCleanRam();
			}
		}catch(Exception e){
			
		}
	}
	
	
	public void _bgexec() {
		buttoncolors = 0;
		
		if(tempurl == null)return;
		
		// 1. Шукаємо потрібний запис у списку (лише один раз!)
		Map<String, Object> foundSettings = getRules(tempurl);
		
		// 2. Функція-помічник для безпечного отримання значень (0, 1 або 2)
		// Використовуємо статичний метод або просто логіку нижче
		// Принцип: якщо значення "1" -> 1, якщо "0" -> 0, у всіх інших випадках -> 2
		int ad = getSafeVal(foundSettings, "ad");
		int iface = getSafeVal(foundSettings, "if");
		int js = getSafeVal(foundSettings, "js");
		int th = getSafeVal(foundSettings, "th");
		int sr = getSafeVal(foundSettings, "sr");
		
		// 3. Формуємо фінальне число за розрядами
		buttoncolors += ad;          // одиниці
		buttoncolors += iface * 10;   // десятки
		buttoncolors += js * 100;     // сотні
		buttoncolors += th * 1000;    // тисячі
		buttoncolors += sr * 10000;   // десятки тисяч
		
	}
	// --- Допоміжний метод ---
	private int getSafeVal(Map<String, Object> settings, String key) {
		if (settings == null || !settings.containsKey(key)) {
			return 2; // Ключа немає або сайт не знайдено — "Не визначено"
		}
		Object val = settings.get(key);
		if (val == null) return 2;
		
		String s = val.toString();
		if (s.equals("1")) return 1;
		if (s.equals("0")) return 0;
		
		return 2; // Якщо там "2" або будь-яке інше сміття
	}
	{
	}
	
	
	public void _postexec() {
		// 1. Отримуємо значення кожного розряду математично
		// buttoncolors = s r t h j i a d
		int adVal = (buttoncolors / 1) % 10;
		int ifVal = (buttoncolors / 10) % 10;
		int jsVal = (buttoncolors / 100) % 10;
		int thVal = (buttoncolors / 1000) % 10;
		int srVal = (buttoncolors / 10000) % 10;
		
		// 2. Фарбуємо кнопки
		applyButtonStyle(blockad, adVal);
		applyButtonStyle(iframe, ifVal);
		applyButtonStyle(js, jsVal);
		applyButtonStyle(themepage, thVal);
		applyButtonStyle(swipetore, srVal);
		
	}
	
	// --- Метод, який замінює всі ваші switch-кейси ---
	private void applyButtonStyle(SkinCheckBox view, int status) {
		switch (status) {
			case 1: // Увімкнено (Примусово)
			view.setSkinKey("bbg", false);
			break;
			
			case 0: // Вимкнено (Примусово)
			view.setSkinKey("rbg", false);
			break;
			
			//case 2: // Не визначено (Дефолт)
			default:
			view.setSkinKey("t", false);
			break;
		}
	}
	{
	}
	
	
	public String _extractDomain(final String _url) {
		try {
			URI uri = new URI(_url);
			final Set<String> COMMON_PREFIXES = new HashSet<>(Arrays.asList("www", "m", "mobile"));
			String domain = uri.getHost();
			if (domain != null) {
				String[] parts = domain.split("\\.");
				if (parts.length > 2 && COMMON_PREFIXES.contains(parts[0])) {
					// Видаляє префікс, якщо він присутній у списку
					domain = String.join(".", Arrays.copyOfRange(parts, 1, parts.length));
				}
				return domain;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace(); // Додайте ваше логування тут
		}
		return null;
	}
	
	
	public void _srhupd() {
		srhbuf.clear();
		for (int i = 0; i < (int)(srcsug.size()); i++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("s", srcsug.get((int)(i)));
				srhbuf.add(_item);
			}
		}
		Collections.reverse(srhbuf);
		((BaseAdapter)srhhis.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _kbdsrh() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		
		if (SafeMarginUtils.isKeyboardVisible() && focused) {
			if (!holdsearchtext) {
				autocomplete1.setSingleLine(false);
				((EditText)autocomplete1).setMaxLines((int)5);
				hide.setVisibility(View.GONE);
				refresh.setVisibility(View.GONE);
				openpanel.setVisibility(View.GONE);
				button3.setVisibility(View.GONE);
				linear16.setVisibility(View.VISIBLE);
				button1.setVisibility(View.VISIBLE);
				ssl.setVisibility(View.GONE);
				holdsearchtext = true;
			}
		} else {
			if (!holdsearchtext) {
				autocomplete1.setSingleLine(true);
				((EditText)autocomplete1).setMaxLines((int)1);
				button3.setVisibility(View.VISIBLE);
				linear16.setVisibility(View.GONE);
				button1.setVisibility(View.GONE);
				hide.setVisibility(View.GONE);
				refresh.setVisibility(View.GONE);
				openpanel.setVisibility(View.GONE);
				ssl.setVisibility(View.GONE);
			}
		}
		if (SafeMarginUtils.isKeyboardVisible() && focused) {
			if (!selected) {
				_webViewActions(700, "");
				((EditText)autocomplete1).selectAll();
				selected = true;
			}
		} else {
			if (!holdsearchtext) {
				_webViewActions(0, "");
				selected = false;
			}
		}
		if (SafeMarginUtils.isKeyboardVisible() && !focused) {
			linear1.setVisibility(View.GONE);
			linear5.setVisibility(View.GONE);
			SafeMarginUtils.setFullscreenMode(this, 1);
			isshowpan = false;
		} else {
			if (!isshowpan) {
				linear1.setVisibility(View.VISIBLE);
				if (sf.getString("navpan", "").equals("1")) {
					linear5.setVisibility(View.VISIBLE);
				}
				SafeMarginUtils.setFullscreenMode(this, 0);
				isshowpan = true;
			}
		}
	}
	
	
	public String _getMultilink() {
		ArrayList<HashMap<String, Object>> tab2 = new ArrayList<>();
		for (HashMap<String, Object> originalMap : tab) {
			HashMap<String, Object> newMap = new HashMap<>(originalMap); // копія
			newMap.remove("tab");
			newMap.remove("time");
			newMap.remove("load");
			newMap.remove("fav");
			newMap.remove("name");
			tab2.add(newMap);
		}
		
		// Перевірка розміру і кодування JSON
		if (tab.size() <= 100) {
			try {
				String json = JsonUtils.toJson(tab2);
				return "https://multilink2.weekbrowser.com/" + (is1window?"w":"") + UrlHelper.encode(json);
			} catch (Exception e) {
				Log.e("JSON_ERROR", "Помилка генерації або парсингу JSON", e);
				return "error";
			}
		} else {
			return getString(R.string.tablimit);
		}
		
	}
	
	
	public void _openToast(final String _url) {
		try{
			this_is_not_a_toast_im_stupid_sorry.setTitle("?");
			this_is_not_a_toast_im_stupid_sorry.setMessage(getString(R.string.wantopen)+"\n\n"+_url);
			this_is_not_a_toast_im_stupid_sorry.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					openInOtherApps(_url);
				}
			});
			this_is_not_a_toast_im_stupid_sorry.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			this_is_not_a_toast_im_stupid_sorry.setNeutralButton(getString(R.string.copys), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _url));
				}
			});
			AlertDialog dddddd = this_is_not_a_toast_im_stupid_sorry.create();
			RenderUtils.setupDialogDim(MainActivity.this, dddddd);
			dddddd.show();
			
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "Error");
			((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", e.toString()));
		}
	}
	
	
	public void _ifex3() {
		
	}
	
	
	public void _ops() {
	}void _onpagestart(WebView _w, String _url, Bitmap favicon){
		boolean isOn = false;
		
		
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
		
		final AtomicBoolean isu = new AtomicBoolean(false);
		_Reopentab();
		//return isOn;
	}{
	}
	
	
	public void _onpagefinish(final WebView _w, final boolean _t) {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		//TimerTask tim;
		if(sf.getString("disvis", "1").equals("1")){
			_w.loadUrl("javascript:document.addEventListener('visibilitychange', function (e) { e.stopImmediatePropagation(); }, true);"+
			"window.addEventListener('blur', function (e) { e.stopImmediatePropagation(); }, true);" +
			"Object.defineProperty(document, 'visibilityState', { get: function () { return \"visible\"; } });"+
			"Object.defineProperty(document, 'hidden', { get: function () { return false; } });");
		}
		
		if(sf.getString("forcezoom", "0").equals("1")) {
			_w.loadUrl("javascript:document.querySelector('meta[name=viewport]').setAttribute('content', 'width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes');");
		}
		
		if(sf.getString("sm", "0").equals("1")) {
			// Отримуємо тег вкладки для фолбеку
			Object rawTag = _w.getTag();
			String tagStr = rawTag != null ? String.valueOf(rawTag) : "0";
			String safeTag = java.util.regex.Matcher.quoteReplacement(tagStr);
			
			String jsCode = "(function() {"
			+ "  if (window.__supermenu_active) return;"
			+ "  window.__supermenu_active = true;"
			
			+ "  function findBestId(el) {"
			+ "    if (!el) return '';"
			+ "    if (el.id) return el.id;"
			+ "    if (el.className && typeof el.className === 'string') return el.className.trim().split(/\\s+/)[0];"
			+ "    if (el.name) return el.name;"
			+ "    return '';"
			+ "  }"
			
			+ "  function getElementInfo(e) {"
			+ "    try {"
			+ "      var a = e.target;"
			+ "      if (a && a.tagName !== 'VIDEO' && e.clientX && e.clientY) {"
			+ "        var elementsUnderCursor = document.elementsFromPoint ? document.elementsFromPoint(e.clientX, e.clientY) : [];"
			+ "        for (var idx = 0; idx < elementsUnderCursor.length; idx++) {"
			+ "          if (elementsUnderCursor[idx].tagName === 'VIDEO') {"
			+ "            a = elementsUnderCursor[idx];"
			+ "            break;"
			+ "          }"
			+ "        }"
			+ "      }"
			+ "      if (!a) return;"
			
			+ "      var b = a.outerHTML || '';"
			+ "      var c = a.innerText || '';"
			+ "      var d = findBestId(a);"
			+ "      var f = a.src || (a.currentSrc || '');"
			+ "      var g = '';"
			+ "      var h = (a.tagName || '').toLowerCase();"
			+ "      var i = (document.body && document.body.innerText) || '';"
			+ "      var j = (document.documentElement && document.documentElement.outerHTML) || '';"
			+ "      var k = location.href;"
			+ "      var l = document.title || '';"
			
			+ "      if (a.tagName === 'A') {"
			+ "        g = a.href;"
			+ "      } else {"
			+ "        var m = a.closest ? a.closest('a') : null;"
			+ "        if (m) g = m.href;"
			+ "      }"
			
			+ "      var v = window.Vidro || (window.top && window.top.Vidro) || (window.parent && window.parent.Vidro);"
			+ "      var currentTag = (v && typeof v.getViewTag === 'function') ? v.getViewTag() : '" + safeTag + "';"
			
			+ "      var n = {"
			+ "        code: b, text: c, src: f, href: g,"
			+ "        wtext: i, wcode: j, taid: d, url: k,"
			+ "        type: h, title: l, tag: currentTag"
			+ "      };"
			
			+ "      if (v && typeof v.processElementInfo === 'function') {"
			+ "        v.processElementInfo(JSON.stringify(n));"
			+ "      }"
			+ "    } catch(o) {"
			+ "      console.error('Supermenu Error:', o);"
			+ "    }"
			+ "  }"
			
			+ "  function attach(win) {"
			+ "    try {"
			+ "      if (!win || win.__supermenu_bound) return;"
			+ "      win.__supermenu_bound = true;"
			+ "      win.addEventListener('contextmenu', getElementInfo, true);"
			+ "      if (win.document) {"
			+ "        win.document.addEventListener('contextmenu', getElementInfo, true);"
			+ "      }"
			+ "    } catch(e) {}"
			+ "  }"
			
			+ "  attach(window);"
			
			+ "  function scan() {"
			+ "    attach(window);"
			+ "    var frames = document.querySelectorAll('iframe, frame');"
			+ "    for (var i = 0; i < frames.length; i++) {"
			+ "      try {"
			+ "        if (frames[i].contentWindow) {"
			+ "          attach(frames[i].contentWindow);"
			+ "        }"
			+ "      } catch(e) {}"
			+ "    }"
			+ "  }"
			
			+ "  scan();"
			
			+ "  if (window.MutationObserver && document.documentElement) {"
			+ "    new MutationObserver(scan).observe(document.documentElement, {"
			+ "      childList: true,"
			+ "      subtree: true"
			+ "    });"
			+ "  }"
			+ "})();";
			
			_w.evaluateJavascript(jsCode, null);
		}else{
			_w.setOnLongClickListener(v -> {
				HitTestResult result = _w.getHitTestResult();
				if (result != null) {
					int x = result.getType();
					JSONObject obj = new JSONObject();
					try{
						obj.put("type", String.valueOf(x + 100));
						obj.put(x==5 ? "src" : x==9 ? "taid" : "href", result.getExtra());
						obj.put("url", _w.getUrl());
						obj.put("title", _w.getTitle());
					}catch(Exception e){}
					jsInterface.processElementInfoNative(obj.toString());
				}
				return false;
			});
		}
		
		
		/*_w.loadUrl("javascript:(function(){" +
"document.addEventListener('click',function(e){" +
"var t=e.target;" +
"while(t&&!t.href)t=t.parentElement;" +
"if(t&&t.href&&t.href.startsWith('blob:')){" +
"e.preventDefault();" +
"try{" +
"var x=new XMLHttpRequest();" +
"x.open('GET',t.href,true);" +
"x.responseType='blob';" +
"x.onload=function(){" +
"var r=new FileReader();" +
"var m=x.response.type||'application/octet-stream';" +
"var size=x.response.size;" + // Отримуємо розмір файлу
"r.onloadend=function(){" +
"Blobload.sendBlobData(r.result,m,size,t.href);" + // Виклик з 4 аргументами
"};" +
"r.readAsDataURL(x.response);" +
"};" +
"x.onerror=function(){Blobload.showToast('XHR failed');};" +
"x.send();" +
"}catch(e){" +
"Blobload.showToast('JS error: '+e.message);" +
"}" +
"}" +
"},true);" +
"})()");*/
		
		try{
			if (sf.getString("iframer", "").equals("1") && (!_w.getUrl().contains("google") && !_w.getUrl().contains("icloud"))) {
				_w.loadUrl("javascript:(function(){\n    var iframes = document.querySelectorAll('iframe');\n    if(!iframes.length) return;\n\n    iframes.forEach(function(iframe){\n        var src = iframe.src || iframe.getAttribute('data-src');\n        if(!src) return;\n\n        var link = document.createElement('a');\n        link.href = src;\n        var absoluteUrl = link.href;\n\n        var wrapper = document.createElement('div');\n        wrapper.style.position = 'relative';\n        wrapper.style.display = 'inline-block';\n        iframe.parentNode.insertBefore(wrapper, iframe);\n        wrapper.appendChild(iframe);\n\n        var button = document.createElement('a');\n        button.href = absoluteUrl;\n        button.target = '_blank';\n        button.textContent = '↗';\n\n        // Стилі кнопки\n        button.style.position = 'absolute';\n        button.style.bottom = '-25px';\n        button.style.right = '10px';\n        button.style.backgroundColor = '#00ffff';\n        button.style.color = '#fff';\n        button.style.padding = '8px 12px';\n        button.style.fontSize = '18px';\n        button.style.fontWeight = 'bold';\n        button.style.cursor = 'pointer';\n        button.style.textDecoration = 'none';\n        button.style.borderRadius = '6px';\n        button.style.boxShadow = '0 2px 4px rgba(0,0,0,0.3)';\n        button.style.zIndex = '9999999';\n        button.style.transition = 'all 0.2s ease'; // плавний ефект при ховер\n\n        // Hover-ефект\n        button.addEventListener('mouseenter', function(){\n            button.style.transform = 'scale(1.1)';\n            button.style.boxShadow = '0 4px 8px rgba(0,0,0,0.4)';\n        });\n        button.addEventListener('mouseleave', function(){\n            button.style.transform = 'scale(1)';\n            button.style.boxShadow = '0 2px 4px rgba(0,0,0,0.3)';\n        });\n\n        wrapper.appendChild(button);\n    });\n})();\n");
			}
		}catch(Exception e){
			
		}
		sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
		((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
		new Thread(() -> {
			dataHolder.setData("ml", _getMultilink());
		}).start();
		
		_webViewActions(100, "");
	}
	
	
	public void _turnonincognito() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		//ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
		
		incognito.setChecked(true);
		sf.edit().putString("incognito", "1").commit();
		openpanel.setText("");
		//Skin skin = new Skin();
		//skin.setBG(openpanel, 1, true);
		openpanel.setTextColor((int)Long.parseLong(dataHolder.getData("rbt"),16));
		if (sf.getString("noscreen", "").equals("1")) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
		}
	}
	
	
	public void _closetab(final double _n) {
		int position = (int) _n;
		if (tab == null || position < 0 || position >= tab.size()) return;
		
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		try {
			// 1. Отримуємо тег і безпечно знищуємо View + видаляємо з ResourceManager
			String tabValue = getTabValue(position);
			if (!tabValue.isEmpty()) {
				destroyTabByTag("ll" + tabValue);
			}
			
			// 2. Видаляємо вкладку зі списку даних
			tab.remove(position);
			_tabupd();
			
			if (tabscont != null && tabscont.getAdapter() != null) {
				((BaseAdapter) tabscont.getAdapter()).notifyDataSetChanged();
			}
			
			// 3. Якщо вкладок взагалі не залишилося — створюємо стартову
			if (tab.isEmpty()) {
				notFirst = true;
				_newTab(sf.getString("firstpage", ""), true);
			} else {
				// Зберігаємо оновлений список вкладок
				sf.edit().putString("tabs", JsonUtils.toJson(tab)).apply();
				
				// 4. Безпечний пошук вкладки з найвищим time
				int bestIndex = 0;
				long maxTime = -1;
				
				for (int i = 0; i < tab.size(); i++) {
					long currentTime = -1;
					Object timeObj = tab.get(i).get("time");
					
					if (timeObj != null) {
						try {
							currentTime = Long.parseLong(String.valueOf(timeObj));
						} catch (NumberFormatException ignored) {}
					}
					
					if (currentTime > maxTime) {
						maxTime = currentTime;
						bestIndex = i;
					}
				}
				
				// Фоллбек: якщо time у всіх відсутній або однаковий — вибираємо сусідню вкладку
				if (maxTime == -1) {
					bestIndex = Math.min(position, tab.size() - 1);
				}
				
				// Оновлюємо time для обраної нової активної вкладки
				time++;
				tab.get(bestIndex).put("time", String.valueOf(time));
				
				// Записуємо активну вкладку та відкриваємо її
				sf.edit().putString("activetab", String.valueOf(bestIndex)).apply();
				openOrRestoreTab(bestIndex);
			}
			
			new Thread(() -> {
				if (dataHolder != null) {
					dataHolder.setData("ml", _getMultilink());
				}
			}).start();
			
		} catch (Exception e) {
			e.printStackTrace();
			notFirst = true;
			_newTab(sf.getString("firstpage", ""), true);
		}
		
		_Reopentab();
		
	}
	
	
	public void _wantbackq() {
		exit.setTitle("?");
		exit.setMessage(getString(R.string.want_exit));
		exit.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		exit.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		exit.create().show();
	}
	
	
	public void _tabupd() {
		if(bm!=null)
		bm.updateTabs(tab.size());
	}
	
	
	public void _finish() {
		finish();
	}
	
	
	public void _search2() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		if (sf.getString("searchengine", "").equals("0")) {
			URL = "https://www.google.com/search?q=".concat(autocomplete1.getText().toString());
		}
		if (sf.getString("searchengine", "").equals("1")) {
			URL = "https://www.bing.com/search?q=".concat(autocomplete1.getText().toString());
		}
		if (sf.getString("searchengine", "").equals("2")) {
			URL = "https://search.yahoo.com/search?p=".concat(autocomplete1.getText().toString());
		}
		if (sf.getString("searchengine", "").equals("3")) {
			URL = "https://duckduckgo.com/?t=h_&q=".concat(autocomplete1.getText().toString());
		}
		if (sf.getString("incognito", "").equals("0")) {
			srcsug.add(autocomplete1.getText().toString().trim());
			Set<String> uniqueValues = new LinkedHashSet<>(srcsug);
			srcsug.clear();
			srcsug.addAll(uniqueValues);
			FileUtil.writeFile(getFilesDir().getPath()+"/srhhis.json", JsonUtils.toJson(srcsug));
			_srhupd();
		}
		_webViewActions(10, URL);
		closeeditor.performClick();
		prevCharSeq = "";
	}
	
	
	public void _prog100(final WebView _w) {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		if (sf.getString("skipvideoad", "").equals("1")) {
			_w.loadUrl(JSutil.videoskip(sf));
		}
	}
	
	
	public void _init() {
		bm = new BarMan(linear5, MainActivity.this);
	}
	
	
	public void _forceCleanRam() {
		try{
			int mintime = Integer.MAX_VALUE;
			int minind = 0;
			for (int i = 0; i < (int)(tab.size()); i++) {
				try{
					if (Integer.parseInt(tab.get((int)i).get("time").toString()) < mintime) {
						mintime = Integer.parseInt(tab.get((int)i).get("time").toString());
						minind = i;
					}
				}catch(Exception e){
					
				}
			}
			int childCount = tabscontainer.getChildCount();
			//sf.edit().putString("activetab", String.valueOf(0)).commit();
			
			try {
				// Безпечне отримання значення для tagToMatch
				Object tabValue = tab.get(minind).get("tab");
				String tagToMatch = tabValue != null ? "ll" + tabValue.toString() : "";
				
				if (!tagToMatch.isEmpty()) {
					for (int i = childCount - 1; i >= 0; i--) {
						View child = tabscontainer.getChildAt(i);
						
						// Перевірка, чи є це LinearLayout і чи має він відповідний тег
						if (child instanceof LinearLayout) {
							LinearLayout childLinearLayout = (LinearLayout) child;
							
							// Перевірка за тегом
							if (tagToMatch.equals(childLinearLayout.getTag()) && childLinearLayout.getVisibility() == View.GONE) {
								// Пошук WebView всередині LinearLayout
								for (int j = 0; j < childLinearLayout.getChildCount(); j++) {
									View nestedChild = childLinearLayout.getChildAt(j);
									if (nestedChild instanceof WebView) {
										WebView webView = (WebView) nestedChild;
										
										// Зупиняємо завантаження та очищуємо WebView
										webView.stopLoading();
										webView.clearHistory();
										webView.loadUrl("about:blank");
										webView.onPause();
										webView.removeAllViews();
										
										// Видаляємо WebView з LinearLayout перед його знищенням
										childLinearLayout.removeView(webView);
										webView.destroy();
										webView = null;
										break;
									}
								}
								
								// Видаляємо LinearLayout з контейнера
								tabscontainer.removeView(childLinearLayout);
								
								
								tab.get((int)minind).put("time", "");
								tab.get((int)minind).put("tab", "");
								((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
								sf.edit().putString("tabs", JsonUtils.toJson(tab)).commit();
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				// Логування помилки для діагностики
				e.printStackTrace();
			} finally {
				_tabupd();
			}
			
			
		}catch(Exception e){
			
		}
	}
	
	
	public void _onCreate2() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		//Skin skin = new Skin();
		try{
			srcsug = JsonUtils.S(FileUtil.readFile(getFilesDir().getPath()+"/srhhis.json"));
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), "Search history is cracked");
		}
		srhhis.setAdapter(new SrhhisAdapter(srhbuf));
		_srhupd();
	}
	
	
	public void _clearonexit(final boolean _onCr) {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		if ((sf.getString("autocleansearch", "").equals("1") || sf.getString("autocleancookie", "").equals("1")) || (sf.getString("autocleanform", "").equals("1") || (sf.getString("autocleanfav", "").equals("1") || sf.getString("autocleancache", "").equals("1")))) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							textview1.setText(getString(R.string.cleaning));
						}
					});
					if (sf.getString("autocleansearch", "").equals("1")) {
						FileUtil.deleteFile(getFilesDir().getPath().concat("/srhhis.json"));
					}
					if (sf.getString("autocleancookie", "").equals("1")) {
						CookieManager.getInstance().removeAllCookies(null);
					}
					if (sf.getString("autocleanform", "").equals("1")) {
						WebViewDatabase db = WebViewDatabase.getInstance(getApplicationContext());
						db.clearFormData();
						db.clearHttpAuthUsernamePassword();
					}
					if (sf.getString("autocleancache", "").equals("1")) {
						FileUtil.deleteFile(getCacheDir().getPath());
					}
					if (sf.getString("autocleanfav", "").equals("1")) {
						FileUtil.deleteFile(getFilesDir().getPath()+"/favicons");
					}
					if (_onCr) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_onCreate2();
							}
						});
					}
				}
			}).start();
		} else {
			if (_onCr) {
				_onCreate2();
			}
		}
	}
	
	
	public void _showToast(final String _x) {
		SketchwareUtil.showMessage(getApplicationContext(), _x);
	}
	
	
	public void _setAll(final WebView _w) {
		// 1. БАЗОВІ НАЛАШТУВАННЯ (Один раз і чисто)
		WebSettings settings = _w.getSettings();
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		String currentUrl = _w.getUrl();
		
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(false);
		settings.setLoadWithOverviewMode(true);
		settings.setSaveFormData(true);
		settings.setDomStorageEnabled(true);
		settings.setAllowFileAccess(true);
		settings.setAllowContentAccess(true);
		settings.setDatabaseEnabled(true);
		settings.setSupportMultipleWindows(true);
		settings.setGeolocationEnabled(true);
		settings.setAllowFileAccessFromFileURLs(true);
		settings.setAllowUniversalAccessFromFileURLs(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);// за замовчуванням, далі переключимо
		
		if (sf.getString("loadimages", "").equals("1")) {
			settings.setBlockNetworkImage(false);
			
			settings.setLoadsImagesAutomatically(true);
		} else {
			settings.setBlockNetworkImage(true);
			settings.setLoadsImagesAutomatically(false);
		}
		
		_w.setFindListener(new WebView.FindListener() {
			@Override
			public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
				if (isDoneCounting) {
					allnum = numberOfMatches;
					curnum = activeMatchOrdinal+1;
					// Можна додати логіку для використання allnum
				}
			}
		});
		
		// Безпечне читання чисел з SharedPreferences
		int minFontSize = safeParseInt(sf.getString("minfont", "8"), 8);
		int textZoom = safeParseInt(sf.getString("fontsize", "10"), 10) * 10;
		settings.setMinimumFontSize(minFontSize);
		settings.setTextZoom(textZoom);
		
		// Hardware acceleration
		_w.setLayerType(sf.getString("hardware2", "1").equals("1") 
		? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_SOFTWARE, null);
		
		// 2. ПОШУК НАЛАШТУВАНЬ САЙТУ (Один цикл замість трьох!)
		Map<String, Object> siteRules = getRules(currentUrl);
		
		// 3. ЗАСТОСУВАННЯ ПРАВИЛ (JS, IFRAME, AD)
		final String scriptIf = "javascript:(function(){var iframes=document.querySelectorAll('iframe');for(var i=0;i<iframes.length;i++){iframes[i].parentNode.removeChild(iframes[i]);}})();";
		
		// Логіка для IFRAME
		int ifRule = getSafeVal(siteRules, "if");
		boolean shouldBlockIframe = (ifRule == 1) || (ifRule == 2 && sf.getString("iframeblock", "").equals("1"));
		if (shouldBlockIframe) _w.loadUrl(scriptIf);
		
		// Логіка для JS
		int jsRule = getSafeVal(siteRules, "js");
		boolean jsEnabled = (jsRule == 1) || (jsRule == 2 && sf.getString("js", "").equals("1"));
		settings.setJavaScriptEnabled(jsEnabled);
		
		// Логіка для AD
		int adRule = getSafeVal(siteRules, "ad");
		boolean adBlockOn = (adRule == 1) || (adRule == 2 && sf.getString("blockad", "").equals("1"));
		ResourceManager.getInstance().setAdForTab(_w.getTag(), adBlockOn);
		
		boolean isPc = sf.getString("pc","").equals("1");
		float density = this.getResources().getDisplayMetrics().density;
		int baseMultiplier = isPc ? 5 : 10;
		int finalScale = (int) (Integer.parseInt(sf.getString("dp","")) * baseMultiplier * density);
		_w.getSettings().setUserAgentString(isPc ? sf.getString("uapc","") : sf.getString("ua",""));
		_w.setInitialScale(finalScale);
		
		// 4. КЕРУВАННЯ КЕШЕМ (Чистий Switch)
		if (sf.getString("incognito", "").equals("1")) {
			settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
			CookieManager.getInstance().setAcceptCookie(false);
			CookieManager.getInstance().setAcceptThirdPartyCookies(_w, false);
		} else {
			CookieManager.getInstance().setAcceptCookie(true);
			CookieManager.getInstance().setAcceptThirdPartyCookies(_w, true);
			int cacheType = safeParseInt(sf.getString("cache", ""), 4);
			if (Internet && sf.getString("nointernet", "").equals("1")) {
				settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			} else {
				switch (cacheType) {
					case 1: settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); break;
					case 2: settings.setCacheMode(WebSettings.LOAD_NO_CACHE); break;
					case 3: settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY); break;
					case 4: settings.setCacheMode(WebSettings.LOAD_DEFAULT); break;
					default:
					// Твоя логіка з urlHistory (свіжість сторінки)
					long lastTime = urlHistory.containsKey(currentUrl) ? urlHistory.get(currentUrl) : 0;
					settings.setCacheMode((System.currentTimeMillis() - lastTime < 60000) 
					? WebSettings.LOAD_CACHE_ELSE_NETWORK : WebSettings.LOAD_DEFAULT);
					break;
				}
			}
		}
		
		// 5. ФАВІКОН (Винесено окремо)
		saveFaviconAsync(_w, currentUrl);
		
		// Оновлення UI
		((BaseAdapter)tabscont.getAdapter()).notifyDataSetChanged();
		
	}
	
	// Безпечний парсинг чисел (щоб не падало на порожніх рядках)
	private int safeParseInt(String value, int defaultValue) {
		try {
			return (value == null || value.isEmpty()) ? defaultValue : Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	private void saveFaviconAsync(WebView _w, String currentUrl) {
		if (currentUrl == null || currentUrl.isEmpty() || currentUrl.equals("about:blank")) return;
		
		Bitmap bfav = _w.getFavicon();
		if (bfav == null) return;
		
		// Створюємо фінальну назву файлу на основі хоста
		final String host = Uri.parse(currentUrl).getHost();
		if (host == null) return;
		
		final String favfilename = getFilesDir().getPath() + "/favicons/" + host + ".webp";
		final Bitmap bitmapToProcess = bfav.copy(bfav.getConfig(), true); // Копіюємо, щоб не було помилок доступу з різних потоків
		
		// Виконуємо збереження (бажано в окремому потоці, щоб UI не "фризив")
		new Thread(() -> {
			try {
				File imageFile = new File(favfilename);
				File parentDir = imageFile.getParentFile();
				if (parentDir != null && !parentDir.exists()) {
					parentDir.mkdirs();
				}
				
				int maxSize = 32;
				int W = bitmapToProcess.getWidth();
				int H = bitmapToProcess.getHeight();
				
				// 1. Центруємо та обрізаємо до квадрата
				int side = Math.min(W, H);
				int x = (W - side) / 2;
				int y = (H - side) / 2;
				Bitmap square = Bitmap.createBitmap(bitmapToProcess, x, y, side, side);
				
				// 2. Масштабуємо
				Bitmap small;
				if (side > maxSize) {
					small = Bitmap.createScaledBitmap(square, maxSize, maxSize, true);
					if (square != bitmapToProcess) square.recycle(); 
				} else {
					small = square;
				}
				
				// 3. Записуємо у файл
				try (FileOutputStream fos = new FileOutputStream(imageFile)) {
					Bitmap.CompressFormat format = (Build.VERSION.SDK_INT >= 30) 
					? Bitmap.CompressFormat.WEBP_LOSSY 
					: Bitmap.CompressFormat.WEBP;
					
					small.compress(format, 0, fos);
					fos.flush();
				}
				
				// Очищення
				small.recycle();
				bitmapToProcess.recycle();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	{
	}
	
	
	public void _accessdenied(final int _adN) {
		SketchwareUtil.showMessage(getApplicationContext(), getApplicationContext().getString(R.string.adblocked)+" ("+_adN+")");
	}
	
	
	public void _fullRepairTab() {
		final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
		
		try{
			tab = JsonUtils.SO(sf.getString("tabs", ""));
			if(tab.size() == 0) repairTab();
		}catch(Exception e){
			repairTab();
		}
	}
	
	public class SrhhisAdapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public SrhhisAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.search, null);
			}
			
			final com.a525team.weekbrowser.SkinTextView textview1 = _view.findViewById(R.id.textview1);
			
			Animator2.anim(getApplicationContext(), textview1, 0, 100);
			textview1.setText(_data.get((int)_position).get("s").toString());
			/*ExtendedDataHolder dataHolder = ExtendedDataHolder.getInstance();
Skin skin = new Skin();

skin.setBG(textview1, 0, false);

textview1.setTextColor((int)Long.parseLong(dataHolder.getData("bt"),16));*/
			
			textview1.setSkinKey("bbg", false);
			
			return _view;
		}
	}
	
	public class TabscontAdapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public TabscontAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.tab, null);
			}
			
			final com.a525team.weekbrowser.SkinLinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView fav = _view.findViewById(R.id.fav);
			final com.a525team.weekbrowser.SkinTextView name = _view.findViewById(R.id.name);
			final com.a525team.weekbrowser.SkinTextView close = _view.findViewById(R.id.close);
			
			final SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);
			final int itemPosition = (int) _position;
			
			// 1. Форматування та безпечне встановлення назви вкладки
			if (sf.getString("usedram", "0").equals("1")) {
				Object tVal = _data.get(itemPosition).get("time");
				Object tabVal = _data.get(itemPosition).get("tab");
				name.setText("[⏳" + (tVal != null ? tVal : "0") + ", 📚" + (tabVal != null ? tabVal : "0") + "]");
			} else {
				Object nameVal = _data.get(itemPosition).get("name");
				name.setText(nameVal != null ? Formater.shrink(nameVal.toString(), 100) : "-");
			}
			
			// 2. Безпечне відновлення іконки Favicon
			try {
				Object favObj = _data.get(itemPosition).get("fav");
				if (favObj != null && !favObj.toString().isEmpty()) {
					fav.setImageBitmap(BitmapEncoderDecoder.decodeFromBase64(favObj.toString()));
				} else {
					fav.setImageResource(R.drawable.default_image);
				}
			} catch (Exception e) {
				fav.setImageResource(R.drawable.default_image);
			}
			
			// 3. Обробник кнопка закриття (Close Button)
			close.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_closetab(itemPosition);
				}
			});
			
			// 4. Клік по вкладці (ВИПРАВЛЕНО ЗАВИСАННЯ)
			name.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					// Викликаємо ТІЛЬКИ openOrRestoreTab — він сам в середині оновлює UI та активний стан!
					openOrRestoreTab(itemPosition);
				}
			});
			
			// 5. Довгий клік по кнопці закриття
			close.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_webViewActions(-18, "");
					return true;
				}
			});
			
			// 6. Довгий клік по назві вкладки (Контекстне меню)
			name.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					Object tabNameObj = _data.get(itemPosition).get("name");
					String dialogTitle = tabNameObj != null ? tabNameObj.toString() : "Tab " + itemPosition;
					
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle(dialogTitle)
					.setItems(new CharSequence[]{
						getString(R.string.add_tab),                // 0
						getString(R.string.add_bg_tab),             // 1
						getString(R.string.add_tab_incognito),      // 2
						getString(R.string.add_tab_bg_incognito),   // 3
						getString(R.string.close_tab),              // 4
						getString(R.string.close_other),            // 5
						getString(R.string.dup_tab),                // 6
						getString(R.string.unload_tab),             // 7
						getString(R.string.unload_other),           // 8
						getString(R.string.add_to_bookmark),        // 9
						getString(R.string.create_shortcut),        // 10
						getString(R.string.close_block),            // 11
						getString(R.string.copy_name),              // 12
						getString(R.string.copy_url),               // 13
						getString(R.string.copy_nameurl),           // 14
						getString(R.string.share_name),             // 15
						getString(R.string.share_url),              // 16
						getString(R.string.share_nameurl)           // 17
					},
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch(which) {
								case 0: _newTab(sf.getString("firstpage", ""), true); break;
								case 1: _newTab(sf.getString("firstpage", ""), false); break;
								case 2: _newTab(sf.getString("firstpage", ""), true); _turnonincognito(); break;
								case 3: _newTab(sf.getString("firstpage", ""), false); _turnonincognito(); break;
								case 4: _closetab(itemPosition); break;
								case 5: _webViewActionsForTab(-13, "", itemPosition); break;
								case 6: _webViewActionsForTab(-14, "", itemPosition); break;
								case 7: _webViewActionsForTab(-30, "", itemPosition); break;
								case 8: _webViewActionsForTab(-16, "", itemPosition); break;
								case 9: _webViewActionsForTab(-1, "", itemPosition); break;
								case 10: _webViewActionsForTab(-17, "", itemPosition); break;
								case 11: _webViewActionsForTab(-18, "", itemPosition); break;
								case 12: _webViewActionsForTab(-19, "", itemPosition); break;
								case 13: _webViewActionsForTab(-20, "", itemPosition); break;
								case 14: _webViewActionsForTab(-21, "", itemPosition); break;
								case 15: _webViewActionsForTab(-22, "", itemPosition); break;
								case 16: _webViewActionsForTab(-23, "", itemPosition); break;
								case 17: _webViewActionsForTab(-24, "", itemPosition); break;
							}
							
							_Reopentab();
						}
					});
					
					builder.setCancelable(true);
					AlertDialog dialog = builder.create();
					RenderUtils.setupDialogDim(MainActivity.this, dialog);
					dialog.show();
					
					return true;
				}
			});
			
			// 7. Визначення та встановлення кольору/кора вкладки
			int activeTabIndex = -1;
			try {
				activeTabIndex = Integer.parseInt(sf.getString("activetab", "0"));
			} catch (Exception e) {}
			
			String tabValue = getTabValue(itemPosition);
			String tabTag = tabValue.isEmpty() ? "" : "ll" + tabValue;
			
			boolean isAlive = !tabTag.isEmpty() && ResourceManager.getInstance().getWebViewForTab(tabTag) != null;
			
			if (activeTabIndex == itemPosition) {
				// 1. АКТИВНА ВКЛАДКА (Червоний/Активний фон)
				name.setSkinKey("rbg", false);
			} else if (isAlive) {
				// 2. ЖИВА ФОНОВА ВКЛАДКА (Синій фон)
				name.setSkinKey("bbg", false);
			} else {
				// 3. МЕРТВА / ВИВАНТАЖЕНА ВКЛАДКА (Сірий фон)
				name.setSkinKey("twb", false);
			}
			
			
			return _view;
		}
	}
}
