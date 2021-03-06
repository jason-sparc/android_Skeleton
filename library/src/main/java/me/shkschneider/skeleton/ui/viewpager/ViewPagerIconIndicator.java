package me.shkschneider.skeleton.ui.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import me.shkschneider.skeleton.R;

public class ViewPagerIconIndicator extends ViewPagerIndicator {

    public ViewPagerIconIndicator(final Context context) {
        this(context, null);
    }

    public ViewPagerIconIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIconIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        final int viewPagerIndicatorHeight = getResources().getInteger(R.integer.sk_viewPager_indicatorHeight);
        init(context, R.color.contentBackgroundColor, R.color.primaryColor, viewPagerIndicatorHeight);
    }

    private View createDefaultStrip(final Context context) {
        final ImageView imageView = new ImageView(context);
        final int padding = (int) ((getResources().getInteger(R.integer.sk_viewPager_padding) / 2) * getResources().getDisplayMetrics().density);
        imageView.setPadding(padding, padding, padding, padding);
        return imageView;
    }

    @Override
    protected void populateStrip() {
        final ViewPagerIndicatorAdapter viewPagerIndicatorAdapter = (ViewPagerIndicatorAdapter) mViewPager.getAdapter();
        final OnClickListener onClickListener = new TabClickListener();
        for (int i = 0; i < viewPagerIndicatorAdapter.getCount(); i++) {
            final View tabView = createDefaultStrip(getContext());
            ((ImageView) tabView).setImageDrawable(viewPagerIndicatorAdapter.getPageIcon(i));
            if (mFillViewPort) {
                tabView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
            }
            tabView.setOnClickListener(onClickListener);
            mTabStripCell.addView(tabView);
        }
    }

}
