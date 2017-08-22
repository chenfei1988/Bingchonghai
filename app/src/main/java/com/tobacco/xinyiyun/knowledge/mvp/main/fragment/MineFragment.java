package com.tobacco.xinyiyun.knowledge.mvp.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.base.BaseFragment;
import com.tobacco.xinyiyun.knowledge.mvp.login.LoginActivity;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.main.CollectActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.MainActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.presenter.MinePresenter;
import com.tobacco.xinyiyun.knowledge.util.RxUtils;
import com.tobacco.xinyiyun.knowledge.util.SPreferencesUtils;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by YangQiang on 2017/8/15.
 */

@LayoutInject(R.layout.fragment_mine)
@ToolbarInject("我的")
public class MineFragment extends BaseFragment<MinePresenter> {
    @Bind(R.id.img_head)
    CircleImageView mImgHead;
    @Bind(R.id.stx_head)
    SuperTextView mStxHead;
    @Bind(R.id.stx_exit)
    SuperTextView mStxExit;
    @Bind(R.id.stx_about)
    SuperTextView mStxAbout;

    @Override
    public void init(View view) {
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            if (mBundle.getBoolean(MainActivity.EXTRA_BOOLEAN_AUTO_LOGIN)) {
                getP().refreshUser();
            }
        }
        mStxAbout.setRightString(com.tobacco.xinyiyun.knowledge.BuildConfig.VERSION_NAME);
        setUser(BaseApplication.getUser());
    }


    public void setUser(User user) {
        if (user != null) {
            mStxHead.setLeftTopString(user.name);
            Glide.with(this)
                    .load(user.user.tx)
                    .placeholder(R.mipmap.ic_default_head)
                    .error(R.mipmap.ic_default_head)
                    .centerCrop()
                    .dontAnimate()
                    .into(mImgHead);
        }
    }

    @OnClick(R.id.stx_clean)
    public void clean() {
        new MaterialDialog.Builder(getActivity()).title("提示")
                .content("你确定要清除缓存吗?")
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
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {

                        Observable.create(new Observable.OnSubscribe<Object>() {
                            @Override
                            public void call(Subscriber<? super Object> subscriber) {
                                Glide.get(BaseApplication.getInstance()).clearDiskCache();
                                Utils.clearAppCache(getContext());
                                subscriber.onNext(true);
                            }
                        }).compose(RxUtils.androidTransformer()).subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                ToastUtils.show(getActivity(), "清理成功!");
                                dialog.dismiss();
                            }
                        });


                    }
                }).build().show();

    }

    @OnClick(R.id.stx_collect)
    public void collect() {
        startActivity(new Intent(getActivity(), CollectActivity.class));
    }

    @OnClick(R.id.stx_exit)
    public void exit() {
        new MaterialDialog.Builder(getActivity()).title("提示")
                .content("你确定要退出登录吗?")
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
                        SPreferencesUtils.putBoolean(getActivity(), getString(R.string.pre_key_boolean_remember_the_password), false);
                        start(getActivity(), LoginActivity.class);
                        getActivity().finish();
                        dialog.dismiss();
                    }
                }).build().show();
    }

}
