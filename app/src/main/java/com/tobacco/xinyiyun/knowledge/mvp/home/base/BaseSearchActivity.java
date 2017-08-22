package com.tobacco.xinyiyun.knowledge.mvp.home.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.common.adapter.SearchResultAdapter;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.tobacco.xinyiyun.knowledge.view.VerticalDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

@LayoutInject(R.layout.activity_home_base_search)
@ToolbarInject(value = "查询", showBack = true)
@MultiStateInject(value = R.id.rcl_results, state = MultiStateView.VIEW_STATE_CONTENT)
public abstract class BaseSearchActivity<P extends BasePresenter> extends BaseActivity<P> {
    @Bind(R.id.tx_search)
    protected TextView mTxSearch;
    @Bind(R.id.edit_search)
    protected EditText mEditSearch;
    @Bind(R.id.rcl_results)
    protected RecyclerView mRclResults;
    @Bind(R.id.img_close)
    protected ImageView mImgClose;
    protected SearchResultAdapter mSearchResultAdapter;
    protected List<ContentEntity> mListData = new ArrayList<>();

    @Override
    public void init() {
        mSearchResultAdapter = new SearchResultAdapter(mListData, this);
        if(mRclResults!=null){
            mRclResults.setLayoutManager(new LinearLayoutManager(this));
            mRclResults.setAdapter(mSearchResultAdapter);
            mRclResults.addItemDecoration(new VerticalDivider.Builder(this)
                    .sizeResId(R.dimen.dp_05)
                    .colorResId(R.color.gray_line)
                    .build());
            mRclResults.addItemDecoration(new HorizontalDivider.Builder(this)
                    .sizeResId(R.dimen.dp_05)
                    .colorResId(R.color.gray_line)
                    .build());
            mRclResults.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    BaseSearchActivity.this.onSimpleItemClick(adapter, view, position);
                }
            });
        }

    }

    @OnTextChanged(value = R.id.edit_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void OnTextChanged(Editable s) {
        mImgClose.setVisibility(mEditSearch.getText().length() == 0 ? View.GONE : View.VISIBLE);
        if (mEditSearch.getText().length() == 0) {
            setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @OnClick(R.id.img_close)
    public void clearEdit() {
        mEditSearch.setText("");
    }

    @OnClick(R.id.tx_search)
    public void search() {
        onSearch(mEditSearch.getText().toString());
    }


    public void setData(List<ContentEntity> list) {
        mListData.clear();
        if (list != null && list.size() > 0) {
            mListData.addAll(list);
        } else {
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        mSearchResultAdapter.notifyDataSetChanged();
    }

    public String getFilter(CheckBox[] checkboxs) {
        StringBuffer mSbSz = new StringBuffer();
        for (CheckBox chk : checkboxs) {
            if (chk.isChecked()) {
                mSbSz.append(chk.getText()).append(",");
            }
        }
        if (mSbSz.length() > 0) {
            return mSbSz.substring(0, mSbSz.length() - 1);
        }
        return mSbSz.toString();
    }
    public abstract void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position);

    public abstract void onSearch(String text);
}
