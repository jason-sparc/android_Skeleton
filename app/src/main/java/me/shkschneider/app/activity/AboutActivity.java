package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.shkschneider.app.fragment.AboutFragment;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragmentActivity;

public class AboutActivity extends SkeletonFragmentActivity implements SkeletonActivity.NavigationCallback {

    public static Intent getInstance(final Activity activity) {
        return new Intent(activity, AboutActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home(true);
        setFragment(new AboutFragment());
    }

    @Override
    public void onHomeAsUpPressed() {
        startActivity(MainActivity.getInstance(AboutActivity.this));
    }

}
