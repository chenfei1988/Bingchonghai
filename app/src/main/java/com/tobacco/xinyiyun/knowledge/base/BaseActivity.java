package com.tobacco.xinyiyun.knowledge.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.MultiStateInject;
import com.tobacco.xinyiyun.knowledge.annocation.RefreshInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.util.ReflectionUtils;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import butterknife.ButterKnife;


/**
 * User: yq
 * Date: 2016-06-02
 * Time: 16:09
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements Toolbar.OnMenuItemClickListener, OnRefreshListener, OnLoadmoreListener {
    public static final String EXTRA_STRING_ARRAY_ARGS = "args";
    protected P mPresenter;
    protected MultiStateView mMultiStateView;
    protected Toolbar mToolBar;
    protected TextView mTitle;
    private View mRootView;
    private LinearLayout mLinearLayout;
    private long mLastTime;
    private LayoutInject mLayoutInject;
    private SmartRefreshLayout mSmartRefreshLayout;

    public static void start(Context context, Class target, String... args) {
        Intent mIntent = new Intent(context, target);
        if (args != null) {
            mIntent.putExtra(EXTRA_STRING_ARRAY_ARGS, args);
        }
        context.startActivity(mIntent);
    }

    public String[] getArgs() {
        return getIntent().getStringArrayExtra(EXTRA_STRING_ARRAY_ARGS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ReflectionUtils.getSuperClassGenricType(this, 0);
        if (mPresenter != null) {
            mPresenter.setLifecycleTransformer(bindUntilEvent(ActivityEvent.DESTROY));
            mPresenter.setView(this);
            mPresenter.setModel(ReflectionUtils.getSuperClassGenricType(mPresenter, 0));
            mPresenter.setContext(this);
        }

        mLayoutInject = getAnnotation(LayoutInject.class, getClass());

        if (mLayoutInject != null) {
            //状态栏透明
            if (mLayoutInject.translucentStatus()) {
                ViewUtils.translucentStatus(this);
            }
            if (mLayoutInject.rxbus()) {
                RxBus.getDefault().register(this);
            }
            setContentView(mLayoutInject.value());
        }


    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        inflateStateView(view, params);
        super.setContentView(mRootView, params);
        ButterKnife.bind(this);
        init();
    }


    @Override
    public void setTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    /**
     * 初始化话状态界面
     *
     * @param view
     * @param params
     */
    private void inflateStateView(View view, ViewGroup.LayoutParams params) {
        mRootView = LayoutInflater.from(this).inflate(R.layout.view_common_state_view, null);
        mLinearLayout = (LinearLayout) ((ViewGroup) mRootView).getChildAt(0);
        mToolBar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mTitle = (TextView) mRootView.findViewById(R.id.toolbar_title);
        mLinearLayout.addView(view, params);
        initMultiState();
        initToolBar();
        initSmartRefresh();
    }


    /**
     * 初始化工具条
     */
    private void initToolBar() {
        ToolbarInject mBarInject = getAnnotation(ToolbarInject.class, getClass());
        if (mBarInject == null) {
            mLinearLayout.removeView(mToolBar);
            mToolBar = null;
        } else {
            setTitle(mBarInject.value());
            isShowBack(mBarInject.showBack());
            if (mLayoutInject != null && mLayoutInject.translucentStatus()) {
                ViewUtils.steepStatusBar(this, mToolBar);
            }

            if (mBarInject.flotage()) {
                mLinearLayout.removeView(mToolBar);
                ((ViewGroup) mRootView).addView(mToolBar);
            }

            int mBackgroundColorRes = mBarInject.colorId();
            if (mBackgroundColorRes == 0) {
                mBackgroundColorRes = R.color.colorPrimary;
            }
            mToolBar.setBackgroundColor(getResources().getColor(mBackgroundColorRes));
            mTitle.setBackgroundColor(getResources().getColor(mBackgroundColorRes));

            if (mBarInject.menuId() != 0) {
                setMenu(mBarInject.menuId());
            }
        }
    }

    /**
     * 初始化多状态界面
     */
    private void initMultiState() {
        MultiStateInject mMultiStateInject = getAnnotation(MultiStateInject.class, getClass());
        if (mMultiStateInject != null && mMultiStateInject.value() != 0) {
            View mView = mRootView.findViewById(mMultiStateInject.value());
            if (mView != null) {
                mMultiStateView = new MultiStateView(this);
                mMultiStateView.setLayoutParams(mView.getLayoutParams());

                ViewGroup mParent = (ViewGroup) mView.getParent();
                int mIndex = 0;
                for (int i = 0; i < mParent.getChildCount(); i++) {
                    if (mParent.getChildAt(i) == mView) {
                        mIndex = i;
                        break;
                    }
                }
                mParent.removeView(mView);
                mMultiStateView.addView(mView);
                mMultiStateView.setViewForState(R.layout.layout_common_loading, MultiStateView.VIEW_STATE_LOADING);
                mMultiStateView.setViewForState(R.layout.layout_common_empty, MultiStateView.VIEW_STATE_EMPTY);
                mMultiStateView.setViewForState(R.layout.layout_common_error, MultiStateView.VIEW_STATE_ERROR);
                mMultiStateView.setViewState(mMultiStateInject.state());
                mParent.addView(mMultiStateView, mIndex);
            }
        }
    }

    /**
     * 初始化刷新控件
     */
    private void initSmartRefresh() {
        RefreshInject mRefreshInject = getAnnotation(RefreshInject.class, getClass());
        if (mRefreshInject != null && mRefreshInject.value() != 0) {
            View mView = mRootView.findViewById(mRefreshInject.value());
            if (mView != null) {
                mSmartRefreshLayout = new SmartRefreshLayout(this);
                mSmartRefreshLayout.setLayoutParams(mView.getLayoutParams());
                mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
                mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
                mSmartRefreshLayout.setEnableOverScrollBounce(false);
                mSmartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
                mSmartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
                mSmartRefreshLayout.setHeaderHeight(80);
                mSmartRefreshLayout.setOnRefreshListener(this);
                mSmartRefreshLayout.setOnLoadmoreListener(this);
                mSmartRefreshLayout.setEnableLoadmore(mRefreshInject.loadMore());
                ViewGroup mParent = (ViewGroup) mView.getParent();
                int mIndex = 0;
                for (int i = 0; i < mParent.getChildCount(); i++) {
                    if (mParent.getChildAt(i) == mView) {
                        mIndex = i;
                        break;
                    }
                }
                mParent.removeView(mView);
                mSmartRefreshLayout.addView(mView);
                mParent.addView(mSmartRefreshLayout, mIndex);
            }
        }
    }

    public SmartRefreshLayout getRefresh() {
        return mSmartRefreshLayout;
    }


    public MultiStateView getMultiState() {
        return mMultiStateView;
    }

    public void setViewState(int state) {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(state);
        }
    }

    /**
     * 显示返回按钮
     *
     * @param isShow
     */
    public void isShowBack(boolean isShow) {
        if (mToolBar != null && isShow) {
            mToolBar.setNavigationIcon(R.mipmap.ic_back);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * 设置菜单
     *
     * @param menuId
     */
    public void setMenu(int menuId) {
        if (mToolBar != null) {
            mToolBar.inflateMenu(menuId);
            Menu mMenu = mToolBar.getMenu();
            if (mMenu != null) {
                if (mMenu.getClass().getSimpleName().equals("MenuBuilder")) {
                    try {
                        Method m = mMenu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                        m.setAccessible(true);
                        m.invoke(mMenu, true);
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                    }
                }
            }
            mToolBar.setOnMenuItemClickListener(this);
        }
    }

    public P getP() {
        return mPresenter;
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            long mCurrentTime = System.currentTimeMillis();
            if ((mCurrentTime - mLastTime) / 1000 > 2) {
                ToastUtils.show(this, R.string.click_again_exit);
                mLastTime = mCurrentTime;
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public abstract void init();


    private <A extends Annotation> A getAnnotation(Class<A> annotationClass, Class claz) {
        A mAnno = null;
        if (claz != null) {
            mAnno = (A) claz.getAnnotation(annotationClass);
            if (mAnno == null && claz.getSuperclass() != null) {
                mAnno = getAnnotation(annotationClass, claz.getSuperclass());
            }
        }
        return mAnno;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
    }

    @Override
    protected void onDestroy() {
        if (mLayoutInject != null && mLayoutInject.rxbus()) {
            RxBus.getDefault().unRegister(this);
        }
        super.onDestroy();
    }
}


