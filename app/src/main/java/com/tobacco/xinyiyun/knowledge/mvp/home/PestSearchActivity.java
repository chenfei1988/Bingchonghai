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
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.PestSearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;

import butterknife.Bind;

@LayoutInject(R.layout.activity_pest_search)
@ToolbarInject(value = "查询", showBack = true, menuId = R.menu.menu_search_filter)
public class PestSearchActivity extends BaseSearchActivity<PestSearchPresenter> {

    @Bind(R.id.chk_gen)
    CheckBox mChkGen;
    @Bind(R.id.chk_jing)
    CheckBox mChkJing;
    @Bind(R.id.chk_ye)
    CheckBox mChkYe;
    @Bind(R.id.chk_mq)
    CheckBox mChkMq;
    @Bind(R.id.chk_hmq)
    CheckBox mChkHmq;
    @Bind(R.id.chk_tk)
    CheckBox mChkTk;
    @Bind(R.id.chk_wz)
    CheckBox mChkWz;
    @Bind(R.id.chk_cs)
    CheckBox mChkCs;
    @Bind(R.id.ll_filter)
    LinearLayout mLlFilter;

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
        PestDetailActivity.start(this, PestDetailActivity.class, mEntity);
    }

    @Override
    public void onSearch(String text) {
        CheckBox[] mChksSzq = new CheckBox[]{mChkMq, mChkHmq, mChkTk, mChkWz, mChkCs};
        CheckBox[] mChksBw = new CheckBox[]{mChkGen, mChkJing, mChkYe};
        String mBw = getFilter(mChksBw);
        String mSzq = getFilter(mChksSzq);
        String mSearch = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearch) && TextUtils.isEmpty(mBw) && TextUtils.isEmpty(mSzq)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        hideFilter();
        getP().getPestList(getIntent().getStringExtra(BaseKdgeFragment.ARG1), mSzq, mBw, mSearch);
    }
}
