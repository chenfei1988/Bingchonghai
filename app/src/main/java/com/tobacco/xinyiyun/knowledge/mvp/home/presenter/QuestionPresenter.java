package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.home.QuestionActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.ConsultModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;

import java.io.File;
import java.util.List;

/**
 * Created by YangQiang on 2017/8/20.
 */

public class QuestionPresenter extends BasePresenter<ConsultModel, QuestionActivity> {

    public void commitQuestion(String question, String type, List<File> listFile) {
        getModel().commitQuestion(question, type, listFile)
                .compose(this.<Response>appTransformer())
                .subscribe(new ApiSubscribe<Response>() {
                    @Override
                    public void onNext(Response response) {
                        super.onNext(response);
                        getView().setData(response);
                    }
                });
    }
}
