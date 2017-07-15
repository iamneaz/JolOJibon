package com.example.fuhad.jolojibon;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fuhad.jonojog.CitiesFragment;

import java.util.HashMap;
import java.util.Map;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4 ;// age 5 silo
    private static int[] tabIcons = {
            R.drawable.newsfeed,
            R.drawable.map,
            R.drawable.pi,
            //R.drawable.community,
            R.drawable.game

    };
    private static Context myContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        myContext = getActivity().getApplicationContext();

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                //tabLayout.Tab
            }
        });

        return x;

    }

    public static class MyAdapter extends FragmentPagerAdapter {

         private static Map<Integer, String> mFrag;
         private static FragmentManager manager;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            manager = fm;
            mFrag = new HashMap<Integer, String>();
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new NewsFragment();
                case 1 : return new MapsFragment();
                case 2 : return new CitiesFragment();
                //case 3 : return new CommunityFragment();
                case 3 : return new GameFragment();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            /*switch (position){
                case 0 :
                    return "News Feeds";
                case 1 :
                    return "Pollution Map";
                case 2 :
                    return "Pollution Statistics";

            }*/

            Drawable image = ContextCompat.getDrawable(myContext,tabIcons[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
            //return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if(obj instanceof Fragment){
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFrag.put(position,tag);
            }
            return  obj;
        }

        public static Fragment getFragment(int position){
            String tag = mFrag.get(position);
            if(tag == null)
            {
                return null;
            }
            return manager.findFragmentByTag(tag);
        }
    }

}