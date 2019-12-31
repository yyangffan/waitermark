package com.superc.waitmarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPaperAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] mTitleLists;

    public ViewPaperAdapter(FragmentManager fm, List<Fragment> mFragments,String[] mTitleLists) {
        super(fm);
        this.mFragments=mFragments;
        this.mTitleLists=mTitleLists;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleLists[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


//    public ViewPaperAdapter(List<View>mViewList, List<String> mTitleLists){
//        this.mViewList = mViewList;
//        this.mTitleLists = mTitleLists;
//    }
//
//    @Override
//    public int getCount() {
//        return mViewList.size();      //页卡数
//    }
//
//    @Override
//    public boolean isViewFromObject(View view,  Object object) {
//        return view == object;       //官方推荐
//    }
//
//
//    @Override
//    public Fragment getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        container.addView(mViewList.get(position));           //添加页卡
//        return mViewList.get(position);
//    }
//
//    @Override
//    public void destroyItem( ViewGroup container, int position,  Object object) {
//        container.removeView(mViewList.get(position));         //删除页卡
//    }
//
//    public CharSequence getPageTitle(int position){
//        return mTitleLists.get(position);             //页卡标题
//    }
}