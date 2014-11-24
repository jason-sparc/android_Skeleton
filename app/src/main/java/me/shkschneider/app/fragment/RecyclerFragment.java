package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import me.shkschneider.app.MyRecyclerAdapter;
import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.StringHelper;

public class RecyclerFragment extends SkeletonFragment {

    private MyRecyclerAdapter mRecyclerAdapter;

    public RecyclerFragment() {
        title("Recycler");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adapter

        mRecyclerAdapter = new MyRecyclerAdapter(R.layout.listview_item2);
        mRecyclerAdapter.setCallback(new MyRecyclerAdapter.Callback() {
            @Override
            public void onItemClick(final int position) {
                final MyRecyclerAdapter.MyItem myItem = mRecyclerAdapter.myItems().get(position);
                ActivityHelper.toast(String.format("%s: %s", myItem.text1, myItem.text2));
            }

            @Override
            public boolean onItemLongClick(final int position) {
                return false;
            }
        });

        // Search

        setHasOptionsMenu(true);
        skeletonActivity().searchable(getResources().getString(R.string.dots), new SkeletonActivity.SearchCallback() {
            @Override
            public void onSearchTextChange(final String q) {
                // Ignore
            }

            @Override
            public void onSearchTextSubmit(final String q) {
                refresh(q);
            }
        });
    }

    // Inflate

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    // Bind

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    // Load

    @Override
    public void onResume() {
        super.onResume();
        refresh(null);
    }

    public void refresh(final String q) {
        mRecyclerAdapter.myItems().clear();
        mRecyclerAdapter.notifyDataSetChanged();
        final Locale[] locales = Locale.getAvailableLocales();
        final List<String> countries = new ArrayList<String>();
        for (final Locale locale : locales) {
            final String country = StringHelper.withoutAccents(locale.getDisplayCountry().trim());
            if (!StringHelper.nullOrEmpty(country)
                    && (StringHelper.nullOrEmpty(q) || country.toLowerCase().contains(q.toLowerCase()))
                    && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, new Comparator<String>() {
            @Override
            public int compare(final String s1, final String s2) {
                return s1.compareTo(s2);
            }
        });
        int position = mRecyclerAdapter.myItems().size();
        for (final String country : countries) {
            final String index = (position + 1) + "/" + countries.size();
            mRecyclerAdapter.myItems().add(position, new MyRecyclerAdapter.MyItem(index, country));
            mRecyclerAdapter.notifyItemInserted(position);
            position++;
        }
    }

}