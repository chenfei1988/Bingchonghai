package com.tobacco.xinyiyun.knowledge.mvp.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.dd.processbutton.iml.ActionProcessButton;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.presenter.QuestionPresenter;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.view.HorizontalDivider;
import com.tobacco.xinyiyun.knowledge.view.PhotoBottomSheetDialog;
import com.tobacco.xinyiyun.knowledge.view.VerticalDivider;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

@LayoutInject(R.layout.activity_question)
@ToolbarInject(value = "提问", showBack = true)
public class QuestionActivity extends BaseActivity<QuestionPresenter> {
    private static final int MAX_COUNT = 4;
    @Bind(R.id.edit_question)
    EditText mEditQuestion;
    @Bind(R.id.rcl_pictures)
    RecyclerView mRclPictures;
    @Bind(R.id.btn_commit)
    ActionProcessButton mBtnCommit;
    List<Picture> mListPicture = new ArrayList<>();
    PictureAdapter mPictureAdapter;
    @Bind(R.id.rl_content)
    RelativeLayout mRlContent;
    PhotoBottomSheetDialog mPhotoBottomSheetDialog;
    Picture mAddPicture = new Picture(true, null);
    @Bind(R.id.sp_types)
    Spinner mSpTypes;

    @Override
    public void init() {
        mSpTypes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.question_types)));
        mPictureAdapter = new PictureAdapter(mListPicture);
        mRclPictures.setLayoutManager(new GridLayoutManager(this, 4));
        mRclPictures.addItemDecoration(new HorizontalDivider.Builder(this)
                .sizeResId(R.dimen.dp_2)
                .colorResId(R.color.transparent)
                .build());
        mRclPictures.addItemDecoration(new VerticalDivider.Builder(this)
                .sizeResId(R.dimen.dp_05)
                .colorResId(R.color.transparent)
                .build());
        mPictureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (mListPicture.get(position).getItemType() == 0) {
                    mPhotoBottomSheetDialog.show();
                } else {
                    if (view.getId() == R.id.img_remove) {
                        ToastUtils.show(QuestionActivity.this, "xxxxx" + position);
                        mListPicture.remove(position);
                        if (!mListPicture.contains(mAddPicture)) {
                            mListPicture.add(mAddPicture);
                        }
                        mPictureAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mRclPictures.setAdapter(mPictureAdapter);
        mPhotoBottomSheetDialog = new PhotoBottomSheetDialog(this);
        mPhotoBottomSheetDialog.dataCallback(new PhotoBottomSheetDialog.OnDataCallback() {
            @Override
            public void onCallBack(String filePath) {
                mListPicture.add(mListPicture.size() - 1, new Picture(false, filePath));
                if (mListPicture.size() > MAX_COUNT) {
                    mListPicture.remove(mAddPicture);
                }
                mPictureAdapter.notifyDataSetChanged();
            }
        });

    }


    @OnClick(R.id.btn_commit)
    public void commit() {
        String mQuestion = mEditQuestion.getText().toString();
        if (TextUtils.isEmpty(mQuestion)) {
            ToastUtils.show(QuestionActivity.this, mEditQuestion.getHint());
            return;
        }

        if (mSpTypes.getSelectedItemPosition() == 0) {
            ToastUtils.show(QuestionActivity.this, mSpTypes.getSelectedItem().toString());
            return;
        }
        mEditQuestion.setEnabled(false);
        mSpTypes.setEnabled(false);
        mBtnCommit.setProgress(1);
        getP().commitQuestion(mQuestion, mSpTypes.getSelectedItem().toString(), mPictureAdapter.getFiles());
    }

    public void setData(Response response) {
        mEditQuestion.setEnabled(false);
        mSpTypes.setEnabled(false);
        mBtnCommit.setProgress(0);
        if (response.success) {
            ToastUtils.show(this, response.message);
            RxBus.getDefault().post(ConsultActivity.REFRESH_CODE, response.message);
            finish();
        }
    }

    private class PictureAdapter extends BaseMultiItemQuickAdapter<Picture, BaseViewHolder> {

        public PictureAdapter(@Nullable List<Picture> data) {
            super(data);
            addData(mAddPicture);
            addItemType(0, R.layout.view_question_add_list_item);
            addItemType(1, R.layout.view_question_list_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Picture item) {
            if (item.getItemType() == 1) {
                helper.addOnClickListener(R.id.img_remove);
                helper.setVisible(R.id.img_remove, true);
                Glide.with(mContext)
                        .load(new File(item.imgUrl))
                        .placeholder(R.mipmap.ic_loading)
                        .error(R.mipmap.ic_load_faile)
                        .centerCrop()
                        .dontAnimate()
                        .into((ImageView) helper.getView(R.id.img_picture));
            } else {

                helper.addOnClickListener(R.id.tx_add);
            }
        }

        public List<File> getFiles() {
            List<File> mListFile = new ArrayList<>();

            for (Picture picture : mListPicture) {
                if (!picture.isAdd) {
                    mListFile.add(new File(picture.imgUrl));
                }
            }
            return mListFile;
        }
    }

    private class Picture implements MultiItemEntity {

        boolean isAdd;
        public String imgUrl;

        public Picture(boolean isAdd, String img) {
            this.isAdd = isAdd;
            this.imgUrl = img;
        }

        @Override
        public int getItemType() {
            return isAdd ? 0 : 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoBottomSheetDialog.activityResult(requestCode, resultCode, data);
    }
}
