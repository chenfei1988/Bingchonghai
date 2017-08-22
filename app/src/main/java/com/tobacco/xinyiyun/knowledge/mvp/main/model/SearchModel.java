package com.tobacco.xinyiyun.knowledge.mvp.main.model;

import com.tobacco.xinyiyun.knowledge.entity.Search;
import com.tobacco.xinyiyun.knowledge.net.ApiService;

import rx.Observable;

/**
 * Created by YangQiang on 2017/8/21.
 */

public class SearchModel {

    public Observable<Search> search(String context) {
        return ApiService.get().searchKnowledge(context);
    }
}
