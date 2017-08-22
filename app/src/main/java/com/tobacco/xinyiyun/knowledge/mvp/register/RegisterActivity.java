package com.tobacco.xinyiyun.knowledge.mvp.register;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.annocation.ToolbarInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.mvp.common.WebBrowerActivity;
import com.tobacco.xinyiyun.knowledge.mvp.main.MainActivity;
import com.tobacco.xinyiyun.knowledge.mvp.register.presenter.RegisterPresenter;
import com.tobacco.xinyiyun.knowledge.net.entity.Response;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;
import com.tobacco.xinyiyun.knowledge.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;


@LayoutInject(R.layout.activity_register)
@ToolbarInject(value = "注册", showBack = true)
public class RegisterActivity extends BaseActivity<RegisterPresenter> {

    @Bind(R.id.edit_name)
    EditText mEditName;
    @Bind(R.id.edit_pwd)
    EditText mEditPwd;
    @Bind(R.id.edit_pwd_agin)
    EditText mEditPwdAgin;
    @Bind(R.id.btn_register)
    ActionProcessButton mBtnRegister;
    @Bind(R.id.chk_rules)
    CheckBox mChkRules;
    @Bind(R.id.tx_rules)
    TextView mTxRules;

    @Override
    public void init() {

    }

    @OnClick(R.id.btn_register)
    public void register() {
        String mName = mEditName.getText().toString().trim();
        if (TextUtils.isEmpty(mName)) {
            ToastUtils.show(this, mEditName.getHint());
            return;
        }

        String mPwd = mEditPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mPwd)) {
            ToastUtils.show(this, mEditPwd.getHint());
            return;
        }

        String mPwdAgin = mEditPwdAgin.getText().toString().trim();
        if (TextUtils.isEmpty(mPwdAgin)) {
            ToastUtils.show(this, mEditPwdAgin.getHint());
            return;
        }

        if (!Utils.isChinaPhoneLegal(mName)) {
            ToastUtils.show(this, "请输入正确的手机号码");
            return;
        }

        if (!mPwd.equals(mPwdAgin)) {
            ToastUtils.show(this, "两次密码输入不一致");
            return;
        }

        if (!mChkRules.isChecked()) {
            ToastUtils.show(this, "请选择同意协议");
            return;
        }

        mEditName.setEnabled(false);
        mEditPwd.setEnabled(false);
        mEditPwdAgin.setEnabled(false);
        mBtnRegister.setProgress(1);
        getP().regiseter(mName, mPwd);
    }

    @OnClick(R.id.tx_rules)
    public void rules() {
        WebBrowerActivity.start(this, "file:///android_asset/deal.html", "用户使用条款");
    }

    public void setData(Response response) {
        if (response.success) {
            getP().autoLogin(mEditName.getText().toString().trim(), mEditPwd.getText().toString().trim());
            mBtnRegister.setLoadingText("登录中");
        } else {
            mEditName.setEnabled(true);
            mEditPwd.setEnabled(true);
            mEditPwdAgin.setEnabled(true);
            mBtnRegister.setProgress(100);
        }
    }

    public void loginData(Response response) {
        if (response.success) {
            MainActivity.start(this, true);
            finish();
        } else {
            finish();
        }
    }
}
