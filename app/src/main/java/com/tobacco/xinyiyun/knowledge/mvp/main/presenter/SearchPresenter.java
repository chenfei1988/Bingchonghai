package com.tobacco.xinyiyun.knowledge.mvp.main.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.entity.Search;
import com.tobacco.xinyiyun.knowledge.mvp.main.SearchActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.model.SearchModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;

/**
 * Created by YangQiang on 2017/8/21.
 */

public class SearchPresenter extends BasePresenter<SearchModel, SearchActivity> {

    public void search(String context) {
        getModel().search(context)
                .compose(this.<Search>appTransformer())
                .compose(LoadTransformer.<Search>build(getView()))
                .subscribe(new ApiSubscribe<Search>() {
                    @Override
                    public void onNext(Search search) {
                        if (search.success && search.data != null && search.data.size() > 0) {
                            getView().setData(search.data);
                        } else {
                            getView().setData(null);
                        }
                    }
                });
    }
}
