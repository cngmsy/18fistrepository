package demo.example.com.demo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 丁军明 on 2018/1/13.
 * 会直接将view的LayoutParams上设置的width，height直接进行百分比处理
 */

public class AutoUtils {
    public static int displayWidth;
    public static int displayHeight;

    private static int designWidth;
    private static int designHeight;

    private static double textPixelsRate;

    //获取activity的宽 高
    public static void setSize(Activity act, boolean hasStatusBar, int designWidth, int designHeight) {

        if (act == null || designWidth < 1 || designHeight < 1)
            return;
        // 获取屏幕密度
        Display display = act.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        if (hasStatusBar) {
            height -= getStatusBarHeight(act);
        }

        AutoUtils.displayWidth = width;
        AutoUtils.displayHeight = height;

        // 根据实际屏幕尺寸动态调整设计基准
        // 如果是横屏显示器(宽>高)，调整设计尺寸以匹配横屏比例
        if (width > height) {
            // 横屏模式：保持设计比例但调整为横屏
            double designRatio = (double) designWidth / designHeight;
            double displayRatio = (double) width / height;
            
            if (displayRatio > designRatio) {
                // 显示器更宽，以高度为基准
                AutoUtils.designWidth = (int)(designHeight * displayRatio);
                AutoUtils.designHeight = designHeight;
            } else {
                // 显示器相对更高，以宽度为基准  
                AutoUtils.designWidth = designWidth;
                AutoUtils.designHeight = (int)(designWidth / displayRatio);
            }
        } else {
            // 竖屏模式：使用原始设计尺寸
            AutoUtils.designWidth = designWidth;
            AutoUtils.designHeight = designHeight;
        }

        // sqrt 一个非负实数的平方根     pow 算x的y次幂
        double displayDiagonal = Math.sqrt(Math.pow(AutoUtils.displayWidth, 2) + Math.pow(AutoUtils.displayHeight, 2));
        double designDiagonal = Math.sqrt(Math.pow(AutoUtils.designWidth, 2) + Math.pow(AutoUtils.designHeight, 2));

        AutoUtils.textPixelsRate = displayDiagonal / designDiagonal;

    }


    public static int getStatusBarHeight(Context context) {

        int result = 0;
        try {

            //获取资源Id
            int resourceId = context.getResources().getIdentifier(
                    "status_bar_height", "dimen", "android");

            if (resourceId > 0) {
                //获取某个dimen的值
                result = context.getResources().getDimensionPixelSize(resourceId);
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return result;

    }

    //给某个activity进行屏幕适配
    public static void auto(Activity act) {

        if (act == null || displayWidth < 1 || displayHeight < 1)
            return;

        View view = act.getWindow().getDecorView();
        auto(view);
    }

    //给里面的View屏幕适配
    public static void auto(View view) {

        if (view == null || displayWidth < 1 || displayHeight < 1)
            return;

        AutoUtils.autoTextSize(view);
        AutoUtils.autoSize(view);
        AutoUtils.autoPadding(view);
        AutoUtils.autoMargin(view);


        if (view instanceof ViewGroup) {
            auto((ViewGroup) view);
        }


    }

    // 给childView计算出建议的宽和高和测量模式
    private static void auto(ViewGroup viewGroup) {

        int count = viewGroup.getChildCount();

        for (int i = 0; i < count; i++) {
            View child = viewGroup.getChildAt(i);

            if (child != null) {
                auto(child);
            }
        }

    }

    //对外边距适配
    public static void autoMargin(View view) {

        if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            return;


        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp == null)
            return;


        lp.leftMargin = getDisplayWidthValue(lp.leftMargin);
        lp.topMargin = getDisplayHeightValue(lp.topMargin);
        lp.rightMargin = getDisplayWidthValue(lp.rightMargin);
        lp.bottomMargin = getDisplayHeightValue(lp.bottomMargin);

    }

    //对内边距适配
    public static void autoPadding(View view) {

        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();


        l = getDisplayWidthValue(l);
        t = getDisplayHeightValue(t);
        r = getDisplayWidthValue(r);
        b = getDisplayHeightValue(b);

        view.setPadding(l, t, r, b);
    }

    //对View的大小适配
    public static void autoSize(View view) {

        ViewGroup.LayoutParams lp = view.getLayoutParams();

        if (lp == null)
            return;

        boolean isSquare = false;
        if (lp.width == lp.height) {
            isSquare = true;
        }

        if (lp.width > 0) {
            lp.width = getDisplayWidthValue(lp.width);
        }

        if (lp.height > 0) {
            lp.height = getDisplayHeightValue(lp.height);
        }

        if (isSquare) {
            if (lp.width > lp.height) {
                lp.width = lp.height;
            } else {
                lp.height = lp.width;
            }
        }

    }


    public static void autoTextSize(View view) {

        if (view instanceof TextView) {

            double designPixels = ((TextView) view).getTextSize();
            double displayPixels = textPixelsRate * designPixels;

            ((TextView) view).setIncludeFontPadding(false);
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) displayPixels);

        }

    }


    public static int getDisplayWidthValue(int designWidthValue) {

        if (Math.abs(designWidthValue) < 2) {
            return designWidthValue;
        }

        return designWidthValue * displayWidth / designWidth;

    }


    public static int getDisplayHeightValue(int designHeightValue) {

        if (Math.abs(designHeightValue) < 2) {
            return designHeightValue;
        }

        return designHeightValue * displayHeight / designHeight;

    }

    public static float getDisplayTextSize(float designTextSize) {
        return (float) (AutoUtils.textPixelsRate * designTextSize);
    }

    /**
     * 获取屏幕密度比例，用于更精确的适配
     */
    public static float getScreenDensityRatio(Activity activity) {
        float density = activity.getResources().getDisplayMetrics().density;
        return density;
    }

    /**
     * 智能适配方法，根据屏幕密度和尺寸自动选择最佳适配策略
     */
    public static void smartSetSize(Activity act, boolean hasStatusBar, int designWidth, int designHeight) {
        if (act == null || designWidth < 1 || designHeight < 1)
            return;

        Display display = act.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        
        // 获取屏幕密度
        float density = act.getResources().getDisplayMetrics().density;
        
        if (hasStatusBar) {
            height -= getStatusBarHeight(act);
        }

        AutoUtils.displayWidth = width;
        AutoUtils.displayHeight = height;

        // 根据屏幕密度和尺寸智能调整设计基准
        if (density > 2.0f) { // 高密度屏幕
            // 对于高密度屏幕，适当放大设计基准
            designWidth = (int)(designWidth * 1.2f);
            designHeight = (int)(designHeight * 1.2f);
        }

        // 处理横屏和竖屏
        if (width > height) {
            // 横屏模式
            double aspectRatio = (double) width / height;
            AutoUtils.designWidth = (int)(designHeight * aspectRatio);
            AutoUtils.designHeight = designHeight;
        } else {
            // 竖屏模式
            AutoUtils.designWidth = designWidth;
            AutoUtils.designHeight = designHeight;
        }

        // 计算文字缩放比例
        double displayDiagonal = Math.sqrt(Math.pow(AutoUtils.displayWidth, 2) + Math.pow(AutoUtils.displayHeight, 2));
        double designDiagonal = Math.sqrt(Math.pow(AutoUtils.designWidth, 2) + Math.pow(AutoUtils.designHeight, 2));

        AutoUtils.textPixelsRate = displayDiagonal / designDiagonal;
    }
}
