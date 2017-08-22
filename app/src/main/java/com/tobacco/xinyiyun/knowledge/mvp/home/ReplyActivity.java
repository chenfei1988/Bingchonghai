package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dd.processbutton.iml.ActionProcessButton;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Consult;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Reply;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.ReplyPresenter;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;
import com.tobacco.xinyiyun.knowledge.util.SoftHideKeyBoardUtil;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


@LayoutInject(R.layout.activity_reply)
@ToolbarInject(value = "问答详情", showBack = true, menuId = R.menu.menu_undo_reply)
@MultiStateInject(R.id.rcl_replys)
public class ReplyActivity extends BaseActivity<ReplyPresenter> {

    private static final String EXTRA_OBJ_DATA = "extra_obj_data";
    @Bind(R.id.rcl_replys)
    RecyclerView mRclReplys;
    Consult.DataEntity mDataEntity;

    ImageView mImgHead;
    TextView mTxName;
    TextView mTxConsult;
    TextView mTxTime;

    ReplyAdapter mReplyAdapter;
    List<Reply.HdataEntity> mListData = new ArrayList<>();
    @Bind(R.id.edit_reply)
    EditText mEditReply;
    @Bind(R.id.btn_reply)
    ActionProcessButton mBtnReply;

    public static void start(Context context, Consult.DataEntity entity) {
        Intent mIntent = new Intent(context, ReplyActivity.class);
        mIntent.putExtra(EXTRA_OBJ_DATA, entity);
        context.startActivity(mIntent);
    }

    @Override
    public void init() {
        SoftHideKeyBoardUtil.assistActivity(this);
        mRclReplys.setLayoutManager(new LinearLayoutManager(this));
        mReplyAdapter = new ReplyAdapter(mListData);
        mDataEntity = (Consult.DataEntity) getIntent().getSerializableExtra(EXTRA_OBJ_DATA);
        View mView = LayoutInflater.from(this).inflate(R.layout.view_reply_head, mRclReplys, false);
        mImgHead = (ImageView) mView.findViewById(R.id.img_head);
        mTxName = (TextView) mView.findViewById(R.id.tx_name);
        mTxConsult = (TextView) mView.findViewById(R.id.tx_consult);
        mTxTime = (TextView) mView.findViewById(R.id.tx_time);

        mTxName.setText(mDataEntity.qzfbr);
        mTxConsult.setText(mDataEntity.qznr);
        mTxTime.setText(mDataEntity.qzsj);

        Glide.with(ReplyActivity.this)
                .load(mDataEntity.qzfbrtx)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_faile)
                .centerCrop()
                .dontAnimate()
                .into(mImgHead);

        mReplyAdapter.addHeaderView(mView);
        mRclReplys.setAdapter(mReplyAdapter);
        getP().getReplyList(String.valueOf(mDataEntity.ywid));
    }


    private class ReplyAdapter extends BaseMultiItemQuickAdapter<Reply.HdataEntity, BaseViewHolder> {

        public ReplyAdapter(@Nullable List<Reply.HdataEntity> data) {
            super(data);
            addItemType(0, R.layout.view_reply_list_item);
            addItemType(1, R.layout.view_reply_empty_list_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Reply.HdataEntity item) {
            if (item.getItemType() == 0) {
                helper.setText(R.id.tx_name, item.hfrxm);
                helper.setText(R.id.tx_time, item.hfsj);
                helper.setText(R.id.tx_consult, item.hfnr);
                Glide.with(ReplyActivity.this)
                        .load(item.hfrtx)
                        .placeholder(R.mipmap.ic_loading)
                        .error(R.mipmap.ic_load_faile)
                        .centerCrop()
                        .dontAnimate()
                        .into((ImageView) helper.getView(R.id.img_head));
            }
        }
    }

    public void setData(List<Reply.HdataEntity> list) {
        if (list != null) {
            mListData.clear();
            mListData.addAll(list);
        } else {
            mListData.clear();
            mListData.add(new Reply.HdataEntity(1));
        }
        mReplyAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_reply)
    public void reply() {

        String mReply = mEditReply.getText().toString().trim();
        if (TextUtils.isEmpty(mReply)) {
            ToastUtils.show(this, mEditReply.getHint());
            return;
        }
        mEditReply.setEnabled(false);
        mBtnReply.setEnabled(false);
        mBtnReply.setProgress(1);
        getP().replyQuestion(String.valueOf(mDataEntity.ywid), mReply);
    }

    public void refresList(Response response) {
        mEditReply.setEnabled(true);
        mBtnReply.setEnabled(true);
        mBtnReply.setProgress(0);
        if (response.success) {
            mEditReply.setText("");
            getP().getReplyList(String.valueOf(mDataEntity.ywid));
        }
    }

    public void removeReulst(Response response) {
        if (response.success) {
            RxBus.getDefault().post(ConsultActivity.REFRESH_CODE, response.message);
            finish();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_undo) {
            new MaterialDialog.Builder(this).title("提示")
                    .content("你确定要删除这条问题吗?")
                    .positiveText("确定")
                    .positiveColor(getResources().getColor(R.color.colorPrimary))
                    .negativeText("取消")
                    .negativeColor(getResources().getColor(R.color.colorPrimary))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            getP().removeQuestion(String.valueOf(mDataEntity.ywid));
                        }
                    }).build().show();

        }
        return super.onMenuItemClick(item);
    }
}
