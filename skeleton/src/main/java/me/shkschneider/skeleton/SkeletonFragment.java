package me.shkschneider.skeleton;

import android.app.Activity;
import android.support.v4.app.Fragment;

import me.shkschneider.skeleton.helper.ClassHelper;

/**
 * Base Fragment you should use!
 * - onCreate()
 * - onCreateView()
 * - onViewCreated()
 *
 * - boolean isAlive()
 * - String title()
 * - void title(String)
 * - SkeletonActivity skeletonActivity()
 *
 * @see android.support.v4.app.Fragment
 * @see <http://developer.android.com/guide/components/fragments.html#Creating>
 */
public class SkeletonFragment extends Fragment {

    protected SkeletonActivity mActivity;
    protected String mTitle;

    public SkeletonFragment() {
        title(ClassHelper.name(SkeletonFragment.class));
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mActivity = (SkeletonActivity) activity;
    }

    public boolean alive() {
        return isVisible();
    }

    public String title() {
        return mTitle;
    }

    protected void title(final String title) {
        mTitle = title;
    }

    public SkeletonActivity skeletonActivity() {
        if (mActivity == null) {
            return (SkeletonActivity) getActivity();
        }
        return mActivity;
    }

}