package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;

/**
 * Base Application you should use!
 *
 * - Context CONTEXT
 *
 * @see android.app.Application
 */
public class SkeletonApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();
    }

}
