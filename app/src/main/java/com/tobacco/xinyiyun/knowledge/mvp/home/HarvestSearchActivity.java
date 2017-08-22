package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.home.base.BaseSearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.fragment.base.BaseKdgeFragment;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.HarvestSearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

import butterknife.Bind;


@LayoutInject(R.layout.activity_harvest_search)
@ToolbarInject(value = "查询", showBack = true, menuId = R.menu.menu_search_filter)
public class HarvestSearchActivity extends BaseSearchActivity<HarvestSearchPresenter> {

    @Bind(R.id.ll_filter)
    LinearLayout mLlFilter;
    @Bind(R.id.chk_xby)
    CheckBox mChkXby;
    @Bind(R.id.chk_zby)
    CheckBox mChkZby;
    @Bind(R.id.chk_sby)
    CheckBox mChkSby;
    @Bind(R.id.chk_yy87)
    CheckBox mChkYy87;
    @Bind(R.id.chk_k326)
    CheckBox mChkK326;
    @Bind(R.id.chk_hhdjy)
    CheckBox mChkHhdjy;

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

        ContentEntity mEntity = mListData.get(position);
        mEntity.arg = getIntent().getStringExtra(BaseKdgeFragment.ARG1);
        BakeDetailActivity.start(this, BakeDetailActivity.class, mEntity);
    }

    @Override
    public void onSearch(String text) {

        CheckBox[] mChksBw = new CheckBox[]{mChkXby, mChkZby, mChkSby};
        CheckBox[] mChksPz = new CheckBox[]{mChkYy87, mChkK326, mChkHhdjy};
        String mBw = getFilter(mChksBw);
        String mPz = getFilter(mChksPz);
        String mSearch = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearch) && TextUtils.isEmpty(mBw) && TextUtils.isEmpty(mPz)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        hideFilter();
        getP().getBakeList(getIntent().getStringExtra(BaseKdgeFragment.ARG1), mPz, mBw, mSearch);
    }

}
