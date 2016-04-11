package com.view.chip.customtabpagerstrip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;


import java.lang.reflect.Method;

//import android.os.SystemProperties;

public class DeviceUtils {

    private static final String TAG = "DeviceUtils";
    private final static int CPU_ARCH_LENGTH = 3;
    private final static String CPU_ARCH_STR = "x86";
    public static String Model = Build.MODEL.toLowerCase();
    public static String FingerPrint = Build.FINGERPRINT.toLowerCase();
    //    public static boolean ISMIUI = android.os.SystemProperties.getInt("ro.miui.ui.version.code",
    //                                                                      -1) > 0;

    public static boolean isMeizu() {
        return isManufacturerEqualTo("meizu");
    }

    public static boolean isMX1() {
        return isManufacturerEqualTo("meizu") && Build.HARDWARE.equalsIgnoreCase("mx");
    }

    public static boolean isMX3() {
        return isManufacturerEqualTo("meizu") && Build.HARDWARE.equalsIgnoreCase("mx3");
    }

    public static boolean isMX2() {
        return isManufacturerEqualTo("meizu") && Build.HARDWARE.equalsIgnoreCase("mx2");
    }

    public static boolean isSamsung() {
        return isManufacturerEqualTo("samsung");
    }

    public static boolean isHTC() {
        return isManufacturerEqualTo("htc");
    }

    //    public static boolean isMIUI() {
    //        return ISMIUI || FingerPrint.contains("miui") || isXiaoMi();
    //    }

    public static boolean isXiaoMi() {
        return (Model.startsWith("mi") && FingerPrint.contains("xiaomi")) || FingerPrint.startsWith(
                "xiaomi");
    }

    public static boolean isXiaoMi3() {
        return Model.startsWith("mi 3") && FingerPrint.contains("xiaomi");
    }

    public static boolean isXiaoMi3w() {
        return Model.equals("mi 3w") && FingerPrint.contains("xiaomi");
    }

    public static boolean isSmartisan() {
        return "SM701".equals(Build.MODEL);
    }

    public static boolean isNexus() {
        return (Model.startsWith("nexus"));
    }

    public static boolean isCoolpad8750() {
        return "Coolpad8750".equals(Build.MODEL);
    }

    public static boolean isLenovo() {
        return Model.startsWith("lenovo") && FingerPrint.startsWith("lenovo");

    }

    public static boolean isManufacturerEqualTo(String manuf) {
        if (TextUtils.isEmpty(manuf)) {
            return false;
        }

        if (TextUtils.isEmpty(Build.MANUFACTURER)) {
            return false;
        }

        return Build.MANUFACTURER.equalsIgnoreCase(manuf);
    }

    public static boolean isSDKAboveKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    //    public static boolean isX86Arch() {
    //        String cpu_abi = SystemProperties.get("ro.product.cpu.abi", "unknown")
    //                                         .substring(0, CPU_ARCH_LENGTH)
    //                                         .toLowerCase();
    //        if (checkX86Arch(cpu_abi)) {
    //            return true;
    //        }
    //
    //        String cpu_abi2 = SystemProperties.get("ro.product.cpu.abi2", "unknown")
    //                                          .substring(0, CPU_ARCH_LENGTH)
    //                                          .toLowerCase();
    //        if (checkX86Arch(cpu_abi2)) {
    //            return true;
    //        }
    //
    //        String arch = System.getProperty("os.arch").substring(0, CPU_ARCH_LENGTH).toLowerCase();
    //        if (checkX86Arch(arch)) {
    //            return true;
    //        }
    //
    //        return false;
    //    }

    private static boolean checkX86Arch(String cpuArch) {
        if (cpuArch.length() >= CPU_ARCH_LENGTH && cpuArch.equals(CPU_ARCH_STR)) {
            return true;
        }

        return false;
    }


    /**
     * 获取当前设备的真实屏幕高度（包含虚拟导航栏的高度，如果有）
     *
     * @param context
     * @return
     */
    public static int getScreenRealHeight(Context context) {
        Display display = getDefaultDisplay(context);
        return getScreenRealHeight(display);
    }

    /**
     * 获取当前设备的真实屏幕高度（包含虚拟导航栏的高度，如果有）
     *
     * @param display
     * @return
     */
    @SuppressLint("NewApi")
    public static int getScreenRealHeight(Display display) {
        if (display == null) {
            return 0;
        }

        int realHeight = -1;
        try {
            Method mt = display.getClass().getMethod("getRealSize", Point.class);
            Point size = new Point();
            mt.invoke(display, size);
            realHeight = Math.max(size.x, size.y);
        } catch (NoSuchMethodException e) {
            try {
                Method mt = display.getClass().getMethod("getRawHeight");
                realHeight = (Integer) mt.invoke(display);
            } catch (NoSuchMethodException e2) {
                try {
                    Method mt = display.getClass().getMethod("getRealHeight");
                    realHeight = (Integer) mt.invoke(display);
                } catch (Exception e3) {
                }
            } catch (Exception e2) {
            }
        } catch (Exception e) {
        }

        if (realHeight < 0) {
            realHeight = display.getHeight();
        }
        return realHeight;
    }

    /**
     * 获取虚拟导航栏的高度
     *
     * @param context
     * @return
     */
//    public static int getNavigationBarHeight(Context context) {
//        Display display = getDefaultDisplay(context);
//        if (display == null) {
//            return 0;
//        }
//
//        int screenHeight = getScreenHeight(display);
//        int realScreenHeight = getScreenRealHeight(display);
//        return Math.abs(realScreenHeight - screenHeight);
//    }
    private static Display getDefaultDisplay(Context context) {
        if (context == null) {
            return null;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display;
    }

    public static boolean isMiui() {

        String s = Build.DISPLAY;
        if (s != null) {
            if (s.toUpperCase().contains("MIUI")) {
                return true;
            }
        }

        s = Build.MODEL; // 灏忕背
        if (s != null) {
            if (s.contains("MI-ONE")) {
                return true;
            }
        }

        s = Build.DEVICE;
        if (s != null) {
            if (s.contains("mione")) {
                return true;
            }
        }

        s = Build.MANUFACTURER;
        if (s != null) {
            if (s.equalsIgnoreCase("Xiaomi")) {
                return true;
            }
        }

        s = Build.PRODUCT;
        if (s != null) {
            if (s.contains("mione")) {
                return true;
            }
        }

        return false;
    }

    public static int dip2px(Context context,
                             float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context,
                             float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isHoneycombTablet(Context context) {
        return hasHoneycomb() && isTablet(context);
    }

    private static final class VERSION_CODES {
        static int FROYO = 8;
        static int GINGERBREAD = 9;
        //		static int GINGERBREAD_MR1 = 10;
        static int HONEYCOMB = 11;
        static int HONEYCOMB_MR1 = 12;
        //		static int HONEYCOMB_MR2 = 13;
        static int ICE_CREAM_SANDWICH = 14;
        //		static int ICE_CREAM_SANDWICH_MR1 = 15;
        static int JELLY_BEAN = 16;
        static int JELLY_BEAN_MR1 = 17;
    }

    //    delete by renjie, at 2015.2.28.
    //	/**
    //	 * Set the theme of the Activity, and restart it by creating a new Activity of
    //	 * the same type.
    //	 */
    //	public static void changeToTheme(Activity activity) {
    //		activity.finish();
    //		activity.startActivity(new Intent(activity, activity.getClass()));
    //	}
    //
    //	public static TypedValue getAttrValue(Activity activity, int attrId) {
    //		TypedValue typedValue = new TypedValue();
    //		activity.getTheme().resolveAttribute(attrId, typedValue, true);
    //		return typedValue;
    //	}
    //
    //    /** 对TextView设置不同状态时其文字颜色。 */
    //	public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
    //        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
    //        int[][] states = new int[6][];
    //        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
    //        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
    //        states[2] = new int[] { android.R.attr.state_enabled };
    //        states[3] = new int[] { android.R.attr.state_focused };
    //        states[4] = new int[] { android.R.attr.state_window_focused };
    //        states[5] = new int[] {};
    //        ColorStateList colorList = new ColorStateList(states, colors);
    //        return colorList;
    //    }
    //
    //    /** 设置Selector。 */
    //    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,
    //            int idUnable) {
    //        StateListDrawable bg = new StateListDrawable();
    //        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
    //        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
    //        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
    //        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
    //        // View.PRESSED_ENABLED_STATE_SET
    //        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
    //        // View.ENABLED_FOCUSED_STATE_SET
    //        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
    //        // View.ENABLED_STATE_SET
    //        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
    //        // View.FOCUSED_STATE_SET
    //        bg.addState(new int[] { android.R.attr.state_focused }, focused);
    //        // View.WINDOW_FOCUSED_STATE_SET
    //        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
    //        // View.EMPTY_STATE_SET
    //        bg.addState(new int[] {}, normal);
    //        return bg;
    //    }
}
