package com.tobacco.xinyiyun.knowledge.mvp.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.annocation.LayoutInject;
import com.tobacco.xinyiyun.knowledge.base.BaseActivity;
import com.tobacco.xinyiyun.knowledge.base.BaseApplication;
import com.tobacco.xinyiyun.knowledge.mvp.login.entity.User;
import com.tobacco.xinyiyun.knowledge.mvp.login.presenter.LoginPresenter;
import com.tobacco.xinyiyun.knowledge.mvp.main.MainActivity;
import com.tobacco.xinyiyun.knowledge.mvp.register.RegisterActivity;
import com.tobacco.xinyiyun.knowledge.util.SPreferencesUtils;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@LayoutInject(value = R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginPresenter> {


    @Bind(R.id.edit_name)
    EditText mEditName;
    @Bind(R.id.edit_pwd)
    EditText mEditPwd;
    @Bind(R.id.btn_login)
    ActionProcessButton mBtnLogin;
    @Bind(R.id.chk_pwd)
    CheckBox mChkPwd;
    @Bind(R.id.tx_register)
    TextView mTxRegister;

    @Override
    public void init() {
        mBtnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        mChkPwd.setChecked(SPreferencesUtils.getBoolean(this, getString(R.string.pre_key_boolean_remember_the_password), false));
        User mUser = BaseApplication.getUser();
        if (mUser != null) {
            mEditName.setText(mUser.name);
            if (mChkPwd.isChecked()) {
                mEditPwd.setText(mUser.password);
            }
        }
    }

    @OnCheckedChanged(R.id.chk_pwd)
    public void OnCheckedChanged(android.widget.CompoundButton button, boolean isChecked) {
        SPreferencesUtils.putBoolean(this, getString(R.string.pre_key_boolean_remember_the_password), isChecked);
    }

    @OnClick(R.id.btn_login)
    public void login(View view) {
        String mName = mEditName.getText().toString().trim();
        String mPwd = mEditPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mName)) {
            ToastUtils.show(this, mEditName.getHint());
            return;
        }
        if (TextUtils.isEmpty(mPwd)) {
            ToastUtils.show(this, mEditPwd.getHint());
            return;
        }

        getP().login(mName, mPwd);
        mBtnLogin.setEnabled(false);
        mEditName.setEnabled(false);
        mEditPwd.setEnabled(false);
        mBtnLogin.setProgress(1);
        mChkPwd.setEnabled(false);
        mTxRegister.setEnabled(false);
    }

    public void refreshViewState() {
        mBtnLogin.setEnabled(true);
        mEditName.setEnabled(true);
        mEditPwd.setEnabled(true);
        mBtnLogin.setProgress(100);
        mChkPwd.setEnabled(true);
        mTxRegister.setEnabled(true);
    }

    public void goHomeActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @OnClick(R.id.tx_register)
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
