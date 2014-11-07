package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.shkschneider.app.R;
import me.shkschneider.app.fragment.ActionBarPagerFragment;
import me.shkschneider.app.fragment.IndexedListFragment;
import me.shkschneider.app.fragment.MainFragment;
import me.shkschneider.app.fragment.NetworkFragment;
import me.shkschneider.app.fragment.PagerFragment;
import me.shkschneider.skeleton.NavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.IntentHelper;

public class DashboardActivity extends NavigationDrawerActivity {

    public static final int NAVIGATION_MAIN = 0;
    public static final int NAVIGATION_PAGER = 1;
    public static final int NAVIGATION_ACTIONBARPAGER = 2;
    public static final int NAVIGATION_LISTVIEW = 3;
    public static final int NAVIGATION_NETWORK = 4;

    public static Intent getInstance(final Activity activity) {
        return new Intent(activity, DashboardActivity.class).setFlags(IntentHelper.HOME_FLAGS);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationDrawer(0);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (navigationDrawer() != NAVIGATION_LISTVIEW) {
            searchable(null, null);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            startActivity(AboutActivity.getInstance(DashboardActivity.this));
            return true;
        }
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(SettingsActivity.getInstance(DashboardActivity.this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return new ArrayAdapter<SkeletonFragment>(this, R.layout.listview_navigationdrawer_item, new ArrayList<SkeletonFragment>() {
            {
                add(NAVIGATION_MAIN, new MainFragment());
                add(NAVIGATION_PAGER, new PagerFragment());
                add(NAVIGATION_ACTIONBARPAGER, new ActionBarPagerFragment());
                add(NAVIGATION_LISTVIEW, new IndexedListFragment());
                add(NAVIGATION_NETWORK, new NetworkFragment());
            }
        }) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(DashboardActivity.this);
                    convertView = layoutInflater.inflate(R.layout.listview_navigationdrawer_item, parent, false);
                }
                final TextView textView = ((TextView) convertView.findViewById(R.id.textview));
                textView.setText(getItem(position).title());
                if (position == navigationDrawer()) {
                    textView.setTextColor(getResources().getColor(R.color.secondaryColor));
                }
                else {
                    textView.setTextColor(getResources().getColor(R.color.textPrimaryColor));
                }
                return convertView;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }
        };
    }

    @Override
    protected SkeletonFragment getFragment(final int position) {
        return (SkeletonFragment) getAdapter().getItem(position);
    }

}
