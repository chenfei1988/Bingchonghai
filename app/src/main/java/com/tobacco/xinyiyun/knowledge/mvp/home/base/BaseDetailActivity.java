package com.tobacco.xinyiyun.knowledge.mvp.home.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.orhanobut.logger.Logger;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.entity.Collect;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import sam.android.utils.viewhelper.CommonExpandableListAdapter;

@LayoutInject(R.layout.activity_base_detail)
@ToolbarInject(value = "详情描述", showBack = true)
@MultiStateInject(R.id.ll_content)
public abstract class BaseDetailActivity<P extends BasePresenter, T extends Object> extends BaseActivity<P> {
    protected static final String EXTRA_OBJ_ENTITY = "extra_obj_entity";
    @Bind(R.id.expl_contents)
    protected ExpandableListView mExplContents;
    @Bind(R.id.ll_share)
    protected LinearLayout mLlShare;
    @Bind(R.id.ll_collect)
    protected LinearLayout mLlCollect;
    @Bind(R.id.ll_good)
    protected LinearLayout mLlGood;
    @Bind(R.id.img_collect)
    protected ImageView mImgCollect;
    protected CommonExpandableListAdapter mCommonExpandableListAdapter;
    protected TextView mTxTitle;
    protected TextView mTxTime;
    protected TextView mTxRead;
    protected RollPagerView mRpvGallery;
    protected UMShareListener mShareListener;
    protected ShareAction mShareAction;
    protected ContentEntity mEntity;
    protected ShareBoardConfig mConfig;

    @Override
    public void init() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        config.setIndicatorVisibility(false);
        mEntity = (ContentEntity) getIntent().getSerializableExtra(EXTRA_OBJ_ENTITY);
        View mView = LayoutInflater.from(this).inflate(R.layout.view_pest_detail_header, null);
        mTxTitle = (TextView) mView.findViewById(R.id.tx_title);
        mTxTime = (TextView) mView.findViewById(R.id.tx_time);
        mTxRead = (TextView) mView.findViewById(R.id.tx_read);
        mRpvGallery = (RollPagerView) mView.findViewById(R.id.rpv_gallery);
        mRpvGallery.setHintView(new ColorPointHintView(this, Color.WHITE, getResources().getColor(R.color.c_50ffffff)));
        mExplContents.addHeaderView(mView);
        mCommonExpandableListAdapter = new CommonExpandableListAdapter<String, String>(this, R.layout.view_pest_detail_child_item, R.layout.view_pest_detail_group_item) {

            @Override
            protected void getChildView(ViewHolder viewHolder, int i, int i1, boolean b, String s) {
                ((TextView) viewHolder.getView(R.id.tx_title)).setText(s);
            }

            @Override
            protected void getGroupView(ViewHolder viewHolder, int i, boolean b, String s) {
                TextView mTxTitle = viewHolder.getView(R.id.tx_title);
                mTxTitle.setText(s);
                mTxTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, b ? R.mipmap.ic_arrow_up : R.mipmap.ic_arrow_down, 0);

            }
        };

        String[] mGourps = getGroups();
        for (String mGourp : mGourps) {
            mCommonExpandableListAdapter.getGroupData().add(mGourp);
        }
        mExplContents.setAdapter(mCommonExpandableListAdapter);
        mExplContents.setGroupIndicator(null);
        mShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {

                if (platform.name().equals("WEIXIN_FAVORITE")) {
                    Toast.makeText(BaseDetailActivity.this, "收藏成功啦", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BaseDetailActivity.this, " 分享成功啦", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(BaseDetailActivity.this, "分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Logger.d("throw", "throw:" + t.getMessage());
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {

                Toast.makeText(BaseDetailActivity.this, "分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        mShareAction = new ShareAction(this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        )

                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        new ShareAction(BaseDetailActivity.this)
                                .withTitle(mEntity.title)
                                .withText("来自【金叶在线】烟农的财富专家")
                                .withMedia(new UMImage(BaseDetailActivity.this, mEntity.imgs[0]))
                                .withTargetUrl(mEntity.url)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share();
                    }
                });
        loadData();
    }

    public abstract String[] getGroups();

    public abstract String[] getChilds(T data);

    public void setData(T data) {
        if (data == null) {
            setViewState(MultiStateView.VIEW_STATE_EMPTY);
            return;
        }
        String[] mChilds = getChilds(data);
        for (final String mChild : mChilds) {
            mCommonExpandableListAdapter.getChildrenData().add(new ArrayList<String>() {{
                add(mChild);
            }});
        }
        mCommonExpandableListAdapter.notifyDataSetChanged();
    }

    public abstract void loadData();


    protected class GralleryAdapter extends LoopPagerAdapter {

        private String[] mUrls;

        public GralleryAdapter(RollPagerView viewPager, String[] urls) {
            super(viewPager);
            mUrls = urls;
        }

        @Override
        public View getView(ViewGroup container, int position) {

            ImageView mImage = new ImageView(container.getContext());
            mImage.setScaleType(ImageView.ScaleType.CENTER);
            mImage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(BaseDetailActivity.this)
                    .load(mUrls[position])
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_load_faile)
                    .dontAnimate()
                    .centerCrop()
                    .into(mImage);
            return mImage;
        }

        @Override
        public int getRealCount() {
            return mUrls == null ? 0 : mUrls.length;
        }
    }

    @OnClick(R.id.ll_collect)
    public void collect() {
        Collect mCollect = getCollect();
        if (mCollect != null) {
            User mUser = BaseApplication.getUser();
            if (mUser == null) {
                ToastUtils.show(this, "请登录后再操作!");
                return;
            }
            mCollect.userId = mUser.user.id;
            RuntimeExceptionDao<Collect, Integer> mCollectDao = BaseApplication.getDBHelper().getRuntimeExceptionDao(Collect.class);
            try {
                if (mCollectDao.queryBuilder()
                        .where().eq("ywlx", mCollect.ywlx)
                        .and().eq("ywid", mCollect.ywid)
                        .and().eq("userId", mCollect.userId).query().size() == 0) {
                    mCollectDao.create(mCollect);
                    ToastUtils.show(this, "收藏成功!");
                    mImgCollect.setImageResource(R.mipmap.ic_collected);
                } else {
                    ToastUtils.show(this, "该文章已收藏");
                }

            } catch (SQLException e) {
                Logger.e(e, "");
            }

        }
    }

    public abstract Collect getCollect();

    public void checkCollect(String ywid, int ywlx) {
        User mUser = BaseApplication.getUser();
        if (mUser == null) {
            return;
        }
        RuntimeExceptionDao<Collect, Integer> mCollectDao = BaseApplication.getDBHelper().getRuntimeExceptionDao(Collect.class);
        try {
            if (mCollectDao.queryBuilder()
                    .where().eq("ywlx", ywlx)
                    .and().eq("ywid", ywid)
                    .and().eq("userId", mUser.user.id).query().size() > 0) {
                mImgCollect.setImageResource(R.mipmap.ic_collected);
            } else {
                mImgCollect.setImageResource(R.mipmap.ic_collect);
            }
        } catch (SQLException e) {
            Logger.e(e, "");
        }
    }

    @OnClick(R.id.ll_good)
    public void good() {
        ToastUtils.show(this, "点赞成功!");
    }

    @OnClick(R.id.ll_share)
    public void share() {

        mShareAction.open(mConfig);
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.GET_ACCOUNTS};
//            new RxPermissions(this).request(mPermissionList).subscribe(new ApiSubscribe<Boolean>() {
//                @Override
//                public void onNext(Boolean aBoolean) {
//                    super.onNext(aBoolean);
//                    if (!aBoolean) {
//                        ToastUtils.show(BaseDetailActivity.this, "请打开相应的权限开关");
//                    }
//                }
//            });
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    public static void start(Context context, Class claz, ContentEntity entity) {
        Intent mIntent = new Intent(context, claz);
        mIntent.putExtra(EXTRA_OBJ_ENTITY, entity);
        context.startActivity(mIntent);
    }


}
