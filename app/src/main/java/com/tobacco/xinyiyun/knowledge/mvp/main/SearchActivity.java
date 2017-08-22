package com.tobacco.xinyiyun.knowledge.mvp.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.entity.Search;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.BakeDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.PestDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.VarietyDetailActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.presenter.SearchPresenter;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

@LayoutInject(R.layout.activity_search)
@ToolbarInject(value = "查询", showBack = true)
@MultiStateInject(value = R.id.rcl_results, state = MultiStateView.VIEW_STATE_CONTENT)
public class SearchActivity extends BaseActivity<SearchPresenter> {

    @Bind(R.id.edit_search)
    EditText mEditSearch;
    @Bind(R.id.img_close)
    ImageView mImgClose;
    @Bind(R.id.tx_search)
    TextView mTxSearch;
    @Bind(R.id.rcl_results)
    RecyclerView mRclResults;
    SearchAdapter mSearchAdapter;
    List<Search.DataEntity> mListData = new ArrayList<>();

    @Override
    public void init() {

        mRclResults.setLayoutManager(new LinearLayoutManager(this));
        mRclResults.addItemDecoration(new HorizontalDivider.Builder(this)
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.gray_line)
                .build());
        mSearchAdapter = new SearchAdapter(mListData);
        mRclResults.setAdapter(mSearchAdapter);
        mRclResults.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                final Search.DataEntity mData = mListData.get(position);
                switch (mData.ywlx) {
                    case 2:
                    case 7:
                    case 8:
                        WebBrowerActivity.start(SearchActivity.this, mData.twdz, mData.wzbt);
                        break;
                    case 101:
                    case 102:
                        start(SearchActivity.this, PestDetailActivity.class, String.valueOf(mData.ywid));
                        break;
                    case 3:
                        //品种资源
                        start(SearchActivity.this, VarietyDetailActivity.class, String.valueOf(mData.ywid));
                        break;
                    case 4:
                        //成熟采收
                        start(SearchActivity.this, BakeDetailActivity.class, "1", String.valueOf(mData.ywid));
                        break;
                    case 5:
                        //科学烘烤
                        start(SearchActivity.this, BakeDetailActivity.class, "2", String.valueOf(mData.ywid));
                        break;
                    case 6:
                        //烤坏烟叶类型
                        start(SearchActivity.this, BakeDetailActivity.class, "3", String.valueOf(mData.ywid));
                        break;
                }
            }
        });
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
        String mSearch = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(mSearch)) {
            ToastUtils.show(this, mEditSearch.getHint());
            return;
        }
        ViewUtils.closeKeybord(mEditSearch, this);
        getP().search(mSearch);
    }

    public void setData(List<Search.DataEntity> list) {
        if (list != null) {
            mListData.clear();
            mListData.addAll(list);
        } else {
            mListData.clear();
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        mSearchAdapter.notifyDataSetChanged();
    }

    private class SearchAdapter extends BaseQuickAdapter<Search.DataEntity, BaseViewHolder> {

        public SearchAdapter(@Nullable List<Search.DataEntity> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Search.DataEntity item) {
            String mStr = "";
            switch (item.ywlx) {
                case 101:
                case 102:
                    mStr = "绿色防控";
                    break;
                case 2:
                    mStr = "栽培技术";
                    break;
                case 3:
                    mStr = "品种资源";
                    break;
                case 4:
                    mStr = "成熟采收";
                    break;
                case 5:
                    mStr = "科学烘烤";
                    break;
                case 6:
                    mStr = "烤坏烟叶类型";
                    break;
                case 7:
                    mStr = "金叶学苑";
                    break;
                case 8:
                    mStr = "烘烤曲线";
                    break;
            }
            helper.setText(android.R.id.text1, item.wzbt + "(" + mStr + ")");
        }
    }
}
