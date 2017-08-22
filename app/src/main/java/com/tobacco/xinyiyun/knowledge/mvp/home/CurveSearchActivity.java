package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.CurveSearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;


@LayoutInject(R.layout.activity_curve_search)
@ToolbarInject(value = "查询", showBack = true, menuId = R.menu.menu_search_filter)
public class CurveSearchActivity extends BaseSearchActivity<CurveSearchPresenter> {


    @Bind(R.id.rcl_shi)
    RecyclerView mRclShi;
    @Bind(R.id.rcl_sheng)
    RecyclerView mRclSheng;
    @Bind(R.id.ll_filter)
    LinearLayout mLlFilter;
    TextAdapter mFirstAdapter;
    TextAdapter mSecondAdapter;
    Map<String, String[]> mMapCity = new LinkedHashMap<>();
    List<String> mListCity = new ArrayList<>();

    @Override
    public void init() {
        super.init();
        String[] mArrays = getResources().getStringArray(R.array.citys);
        for (String str : mArrays) {
            String[] mArray = str.split("#");
            mMapCity.put(mArray[0], mArray.length == 1 ? new String[0] : mArray[1].split(","));
        }

        mFirstAdapter = new TextAdapter(new ArrayList<>(mMapCity.keySet()));
        mFirstAdapter.selectIndex = 0;
        mSecondAdapter = new TextAdapter(mListCity);

        mRclSheng.setLayoutManager(new LinearLayoutManager(this));
        mRclSheng.setAdapter(mSecondAdapter);
        mRclSheng.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSecondAdapter.notifyItemChanged(mSecondAdapter.selectIndex);
                mSecondAdapter.selectIndex = position;
                mSecondAdapter.notifyItemChanged(mSecondAdapter.selectIndex);
            }
        });

        mRclShi.setLayoutManager(new LinearLayoutManager(this));
        mRclShi.setAdapter(mFirstAdapter);
        mRclShi.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mFirstAdapter.selectIndex != position) {
                    mFirstAdapter.notifyItemChanged(mFirstAdapter.selectIndex);
                    mFirstAdapter.selectIndex = position;
                    mFirstAdapter.notifyItemChanged(mFirstAdapter.selectIndex);
                    mSecondAdapter.selectIndex = -1;
                    mListCity.clear();
                    mListCity.addAll(new ArrayList<>(Arrays.asList(mMapCity.get(adapter.getData().get(position).toString()))));
                    mSecondAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        tooggleFilter();
        return super.onMenuItemClick(item);
    }


    private void tooggleFilter() {
        mLlFilter.setVisibility(mLlFilter.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void hideFilter() {
        mLlFilter.setVisibility(View.GONE);
    }

    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        WebBrowerActivity.start(this, mListData.get(position).url, mListData.get(position).title);
    }

    @Override
    public void onSearch(String text) {

        ViewUtils.closeKeybord(mEditSearch, this);
        hideFilter();
        getP().getCurveList(mEditSearch.getText().toString(), mFirstAdapter.getSelect());

    }

    private class TextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public int selectIndex = -1;

        public TextAdapter(@Nullable List<String> data) {
            super(R.layout.view_curve_search_city_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tx_name, item);
            helper.setTextColor(R.id.tx_name, getColor(selectIndex == helper.getLayoutPosition() ? R.color.colorPrimary : R.color.gray_text));
            helper.getView(R.id.tx_line).setVisibility(helper.getLayoutPosition() == selectIndex ? View.VISIBLE : View.INVISIBLE);
        }

        public String getSelect() {
            if (selectIndex > 1) {
                return getData().get(selectIndex).substring(0, 2);
            }
            return "";
        }
    }
}
