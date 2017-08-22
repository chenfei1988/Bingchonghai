package com.tobacco.xinyiyun.knowledge.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.tobacco.xinyiyun.knowledge.util.ViewUtils;
import com.tobacco.xinyiyun.knowledge.view.MultiStateView;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import butterknife.ButterKnife;


/**
 * User: yq
 * Date: 2016-06-06
 * Time: 11:44
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements Toolbar.OnMenuItemClickListener, OnRefreshListener, OnLoadmoreListener {

    public static final String EXTRA_STRING_TITLE = "extra_string_title";
    public static final String EXTRA_STRING_ARRAY_ARGS = "extra_string_array_args";
    protected MultiStateView mMultiStateView;
    private SmartRefreshLayout mSmartRefreshLayout;
    protected Toolbar mToolBar;
    protected TextView mTitle;
    private View mRootView;
    private LinearLayout mLinearLayout;
    private P mPresenter;

    private boolean isFragmentVisible;
    private boolean isPrepared;
    private boolean isFirstLoad = true;
    private boolean isLazyLoad;
    private LayoutInject mLayoutInject;
    public String title;


    public void setBundle(Bundle arguments) {
        title = arguments.getString(EXTRA_STRING_TITLE, "");
        setArguments(arguments);
    }

    public static void start(Context context, Class target, String... args) {
        Intent mIntent = new Intent(context, target);
        if (args != null) {
            mIntent.putExtra(BaseActivity.EXTRA_STRING_ARRAY_ARGS, args);
        }
        context.startActivity(mIntent);
    }

    public String getArgValue(String key) {
        if (getArguments() != null) {
            return getArguments().getString(key);
        }
        return "";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ReflectionUtils.getSuperClassGenricType(this, 0);

        if (mPresenter != null) {
            mPresenter.setLifecycleTransformer(bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            mPresenter.setView(this);
            mPresenter.setModel(ReflectionUtils.getSuperClassGenricType(mPresenter, 0));
            mPresenter.setContext(getActivity());
        }
        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle);
        }
    }

    public P getP() {
        return mPresenter;
    }


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;

        mLayoutInject = getAnnotation(LayoutInject.class, getClass());
        if (mLayoutInject == null) {
            return null;
        }
        if (mLayoutInject.rxbus()) {
            RxBus.getDefault().register(this);
        }
        inflateStateView(mLayoutInject == null ? null : inflater.inflate(mLayoutInject.value(), container, false), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, mRootView);
        isPrepared = true;
        isLazyLoad = mLayoutInject.lazyLoad();
        if (isLazyLoad) {
            lazyLoad();
        } else {
            init(mRootView);
        }
        return mRootView;
    }


    private void inflateStateView(View view, ViewGroup.LayoutParams params) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.view_common_state_view, null);
        mLinearLayout = (LinearLayout) ((ViewGroup) mRootView).getChildAt(0);
        mToolBar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mTitle = (TextView) mRootView.findViewById(R.id.toolbar_title);
        mLinearLayout.addView(view, params);
        initMultiState();
        initToolBar();
        initSmartRefresh();
    }


    private void initToolBar() {
        ToolbarInject mBarInject = getAnnotation(ToolbarInject.class, getClass());
        if (mBarInject == null) {
            mLinearLayout.removeView(mToolBar);
            mToolBar = null;
        } else {
            setTitle(mBarInject.value());
            isShowBack(mBarInject.showBack());
            LayoutInject mActivityInject = getAnnotation(LayoutInject.class, getActivity().getClass());

            //状态栏透明
            if (mActivityInject != null && mActivityInject.translucentStatus()) {
                ViewUtils.steepStatusBar(getActivity(), mToolBar);
            }

            //toolbar浮动
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
                mMultiStateView = new MultiStateView(getActivity());
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
                mMultiStateView.setLayoutParams(mView.getLayoutParams());
                mMultiStateView.setViewForState(R.layout.layout_common_loading, MultiStateView.VIEW_STATE_LOADING);
                mMultiStateView.setViewForState(R.layout.layout_common_empty, MultiStateView.VIEW_STATE_EMPTY);
                mMultiStateView.setViewForState(R.layout.layout_common_error, MultiStateView.VIEW_STATE_ERROR);
                mMultiStateView.setViewState(mMultiStateInject.state());
                mParent.addView(mMultiStateView, mIndex);
            }
        }
    }

    private void initSmartRefresh() {
        RefreshInject mRefreshInject = getAnnotation(RefreshInject.class, getClass());
        if (mRefreshInject != null) {
            View mView = mRootView.findViewById(mRefreshInject.value());
            if (mView != null) {
                mSmartRefreshLayout = new SmartRefreshLayout(getActivity());
                mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
                mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
                mSmartRefreshLayout.setLayoutParams(mView.getLayoutParams());
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

    public void setTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
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
                    getActivity().onBackPressed();
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


    public MultiStateView getMutilState() {
        return mMultiStateView;
    }


    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isFragmentVisible = true;
            lazyLoad();
        } else {
            isFragmentVisible = false;
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isFragmentVisible = true;
            lazyLoad();
        } else {
            isFragmentVisible = false;
        }
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (isFirstLoad) {
                isFirstLoad = false;
                if (isLazyLoad) {
                    init(mRootView);
                }
            }
        }
    }


    public void initVariables(Bundle bundle) {
        title = bundle.getString(EXTRA_STRING_TITLE, "");
    }

    public abstract void init(View view);

    @Override
    public void onDestroyView() {
        if (mLayoutInject.rxbus()) {
            RxBus.getDefault().unRegister(this);
        }
        super.onDestroyView();
        isPrepared = false;
        ButterKnife.unbind(this);
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

}
