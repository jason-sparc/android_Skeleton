package me.shkschneider.skeleton.helper;

import android.os.Vibrator;

public class VibratorHelper {

    public static boolean hasVibrator() {
        final Vibrator vibrator = SystemServices.vibrator();
        if (vibrator == null) {
            LogHelper.warning("Vibrator was NULL");
            return false;
        }
        return vibrator.hasVibrator();
    }

    public static boolean vibrate(final long[] durations, final boolean repeat) {
        final Vibrator vibrator = SystemServices.vibrator();
        if (vibrator == null) {
            LogHelper.warning("Vibrator was NULL");
            return false;
        }
        else if (! hasVibrator()) {
            LogHelper.warning("No vibrator");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    public static boolean vibrate(final long[] durations) {
        return vibrate(durations, false);
    }

    public static boolean vibrate(final long duration) {
        return vibrate(new long[] { duration }, false);
    }

}
