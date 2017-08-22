package com.tobacco.xinyiyun.knowledge.mvp.home.presenter;

import com.tobacco.xinyiyun.knowledge.base.BasePresenter;
import com.tobacco.xinyiyun.knowledge.mvp.home.ReplyActivity;
import com.tobacco.xinyiyun.knowledge.mvp.home.entity.Reply;
import com.tobacco.xinyiyun.knowledge.mvp.home.model.ConsultModel;
import com.tobacco.xinyiyun.knowledge.net.ApiSubscribe;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;
import com.tobacco.xinyiyun.knowledge.transformer.LoadTransformer;

/**
 * Created by YangQiang on 2017/8/21.
 */

public class ReplyPresenter extends BasePresenter<ConsultModel, ReplyActivity> {

    public void getReplyList(String ywid) {
        getModel().getReplyList(ywid)
                .compose(this.<Reply>appTransformer(true,false))

                .compose(LoadTransformer.<Reply>build(getView()))
                .subscribe(new ApiSubscribe<Reply>() {
                    @Override
                    public void onNext(Reply reply) {
                        super.onNext(reply);
                        if (reply.success && reply.hdata != null && reply.hdata.size() > 0) {
                            getView().setData(reply.hdata);
                        } else {
                            getView().setData(null);
                        }
                    }
                });
    }

    public void replyQuestion(String ywid, String content) {
        getModel().replyQuestion(ywid, content)
                .compose(this.<Response>appTransformer(true, true))
                .subscribe(new ApiSubscribe<Response>() {
                    @Override
                    public void onNext(Response response) {
                        super.onNext(response);
                        getView().refresList(response);
                    }
                });
    }

    public void removeQuestion(String ywid) {
        getModel().removeQuestion(ywid)
                .compose(this.<Response>appTransformer(true, true))
                .compose(LoadTransformer.<Response>build(getView()))
                .subscribe(new ApiSubscribe<Response>() {
                    @Override
                    public void onNext(Response response) {
                        super.onNext(response);
                        getView().removeReulst(response);
                    }
                });
    }
}
