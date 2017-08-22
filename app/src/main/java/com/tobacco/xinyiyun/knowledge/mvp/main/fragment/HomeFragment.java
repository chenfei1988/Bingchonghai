package com.tobacco.xinyiyun.knowledge.mvp.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.ConsultActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.TabActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.SearchActivity;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.VerticalDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by YangQiang on 2017/8/15.
 */

@LayoutInject(R.layout.fragment_home)
@ToolbarInject(value = "首页", menuId = R.menu.menu_home_top)
public class HomeFragment extends BaseFragment {

    @Bind(R.id.rpv_gallery)
    RollPagerView mRpvGallery;
    @Bind(R.id.rcl_menu)
    RecyclerView mRclMenu;

    GralleryAdapter mGralleryAdapter;
    List<HomeMenu> mListMenu;
    MenuAdapter mMenuAdapter;

    @Override
    public void init(View view) {
        mRpvGallery.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, getResources().getColor(R.color.c_50ffffff)));
        mGralleryAdapter = new GralleryAdapter(mRpvGallery);
        mRpvGallery.setAdapter(mGralleryAdapter);


        parseMenuConfig();

        mMenuAdapter = new MenuAdapter(mListMenu);
        mRclMenu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRclMenu.addItemDecoration(new VerticalDivider.Builder(getActivity())
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclMenu.addItemDecoration(new HorizontalDivider.Builder(getActivity())
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mRclMenu.setAdapter(mMenuAdapter);
        mRclMenu.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position != mListMenu.size() - 1) {
                    TabActivity.start(getActivity(), mListMenu.get(position).arrayId, mListMenu.get(position).title);
                } else {
                    startActivity(new Intent(getActivity(), ConsultActivity.class));
                }
            }
        });
    }


    /**
     * 解析菜单配置数据
     */
    private void parseMenuConfig() {
        mListMenu = new ArrayList<>();
        String[] mTitles = getResources().getStringArray(R.array.home_menus);
        for (int i = 0; i < mTitles.length; i++) {
            String[] mStrs = mTitles[i].split("#");
            mListMenu.add(new HomeMenu(mStrs[0], mStrs[1], ViewUtils.getMipmap(getActivity(), mStrs[2]), ViewUtils.getArray(getActivity(), mStrs[3])));
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        }
        return super.onMenuItemClick(item);
    }

    private class GralleryAdapter extends LoopPagerAdapter {

        public GralleryAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {

            ImageView mImage = new ImageView(container.getContext());
            mImage.setImageResource(R.mipmap.img_test);
            mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return mImage;
        }

        @Override
        public int getRealCount() {
            return 3;
        }
    }

    private class MenuAdapter extends BaseQuickAdapter<HomeMenu, BaseViewHolder> {


        public MenuAdapter(@Nullable List<HomeMenu> data) {
            super(R.layout.view_home_menu_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeMenu item) {
            helper.setText(R.id.tx_title, item.title);
            helper.setText(R.id.tx_sub_title, item.subTitle);
            helper.setImageResource(R.id.img_icon, item.iconId);
        }
    }

    private class HomeMenu {

        public HomeMenu(String title, String subTitle, int iconId, int arrayId) {
            this.title = title;
            this.subTitle = subTitle;
            this.iconId = iconId;
            this.arrayId = arrayId;
        }

        public String title;
        public String subTitle;
        public int iconId;
        public int arrayId;
    }
}
