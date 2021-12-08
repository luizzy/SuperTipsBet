package com.winningbets.supertipsbet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentTabs extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager2 viewPager;
    public static int int_items = 2;
    MyAdapter myAdapter;
    View view;

    public FragmentTabs() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //this inflates out tab layout file.
        return inflater.inflate(R.layout.fragment_fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myAdapter = new MyAdapter(this);
        TabLayout tabLayout = view.findViewById(R.id.mTabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);

        // create a new adapter for our pageViewer. This adapters returns child fragments as per the position of the page Viewer.
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Home");
                    } else {
                        tab.setText("LiveScore");
                    }
                }
        ).attach();


        //return x;
    }

    private class MyAdapter extends FragmentStateAdapter {

        public MyAdapter(FragmentTabs fm) {
            super(fm);
        }

        //return the fragment with respect to page position.

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new Elite();
                case 1:
                    return new livescorefrag();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return int_items;
        }

    }
}

