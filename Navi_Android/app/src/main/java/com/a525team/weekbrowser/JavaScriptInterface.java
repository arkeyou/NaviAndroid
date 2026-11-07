package com.a525team.weekbrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.webkit.WebView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.content.SharedPreferences;
import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class JavaScriptInterface {
	private WeakReference<Activity> activityRef;
	private final WindowManager wm;
	private SkinButton btn; // Один екземпляр кнопки
	private final SharedPreferences sf;
	private final ArrayList<HashMap<String, Object>> history;
    private final WebView webView;
	
	public JavaScriptInterface(Activity activity, WebView webView) {
		this.activityRef = new WeakReference<>(activity);
		this.wm = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
		this.sf = activity.getSharedPreferences("sf", Context.MODE_PRIVATE);
		this.history = ((MainActivity)activity).getHis();
        this.webView = webView;
	}
	
	@JavascriptInterface
	public void processElementInfo(String jsonData) {
		handleElementInfo(jsonData, true);
	}
    
    @JavascriptInterface
    public String getViewTag() {
        Object tag = webView.getTag();
        return tag != null ? tag.toString() : "";
    }
	
	public void processElementInfoNative(String jsonData) {
		handleElementInfo(jsonData, false);
	}
	
	private void handleElementInfo(final String jsonData, boolean fromJS) {
    final Activity activity = activityRef != null ? activityRef.get() : null;
    if (activity == null) return;

    ExtendedDataHolder.getInstance().setData("info", jsonData);
    
    // Безпечне отримання координат з дефолтними значеннями
    String rawX = ExtendedDataHolder.getInstance().getData("touch_x");
    String rawY = ExtendedDataHolder.getInstance().getData("touch_y");
    
    int parsedX = 100;
    int parsedY = 100;
    try {
        if (rawX != null && !rawX.isEmpty()) parsedX = (int) Float.parseFloat(rawX);
        if (rawY != null && !rawY.isEmpty()) parsedY = (int) Float.parseFloat(rawY);
    } catch (NumberFormatException e) {
        Log.e("JSInterface", "Invalid touch coordinates: x=" + rawX + ", y=" + rawY);
    }
    
    final int x = parsedX;
    final int y = parsedY;
    
    activity.runOnUiThread(() -> {
        showOrUpdateFloatingButton(activity, false, x, y);
    });
    
    new Thread(() -> {
        try {
            activity.runOnUiThread(() -> {
                if (SafeMarginUtils.isKeyboardVisible()) {
                    removeButton(true);
                    return;
                }
                showOrUpdateFloatingButton(activity, true, x, y);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}

	
	private void showOrUpdateFloatingButton(Activity activity, boolean isLoaded, int x, int y) {
		float density = activity.getResources().getDisplayMetrics().density;

		if (btn == null) {
			// Ініціалізація кнопки при першому виклику (стан завантаження)
			btn = new SkinButton(activity);
			btn.setText("///");
			btn.setSkinKey("twb", false);
			
			WindowManager.LayoutParams btnp = new WindowManager.LayoutParams(
				(int) (50 * density),
				(int) (40 * density),
				WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT
			);
			
			btnp.gravity = Gravity.TOP | Gravity.LEFT;
			btnp.x = (int) (x + 40 * density);
			btnp.y = (int) (y - 60 * density);
			
			wm.addView(btn, btnp);
		}

		// Якщо кнопка вже існує, але фоновий потік завершився -> "перетворюємо" її на робочу
		if (isLoaded) {
			btn.setText("[S]");
			btn.setSkinKey("bbg", false); 

			btn.setOnClickListener(view -> {
				removeButton(false);
				Intent intent = new Intent(activity, SsuperactivityActivity.class);
				activity.startActivity(intent);
			});
			
			btn.setOnLongClickListener(view -> {
				removeButton(true);
				return true; 
			});
		} else {
			// Гарантуємо, що поки йде завантаження, кліки не спрацюють
			btn.setOnClickListener(null);
			btn.setOnLongClickListener(null);
		}
	}
	
	public void removeButton(boolean delinfo) {
    if (delinfo) {
        ExtendedDataHolder.getInstance().setData("info", "");
    }
    if (btn != null) {
        Activity activity = activityRef != null ? activityRef.get() : null;
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (btn != null) {
                    wm.removeView(btn);
                    btn = null;
                }
            });
        }
    }
}
}