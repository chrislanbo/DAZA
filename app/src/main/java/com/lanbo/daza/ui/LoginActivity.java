package com.lanbo.daza.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lanbo.daza.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * 跟踪登录任务，以确保我们可以取消它
     */
    private UserLoginTask mAuthTask = null;

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mMsgCode;
    private EditText mPasswordComfirm;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mFinishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mEmailView = (EditText) findViewById(R.id.phone);
        mMsgCode = (EditText) findViewById(R.id.msg_code);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordComfirm = (EditText) findViewById(R.id.password_confirm);
        mFinishBtn = (TextView) findViewById(R.id.tv_sign_in_button);
        mPasswordComfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    Log.i("sadad", "点击键盘完成按钮");
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mFinishBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * 登录逻辑
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mMsgCode.setError(null);
        mPasswordView.setError(null);
        mPasswordComfirm.setError(null);

        String phone = mEmailView.getText().toString();
        String code = mMsgCode.getText().toString();
        String password = mPasswordView.getText().toString();
        String password_confirm = mPasswordComfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("密码不能为空");
            focusView = mPasswordView;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !checkPwd(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password_confirm)) {
            mPasswordComfirm.setError("请确认密码");
            focusView = mPasswordComfirm;
            cancel = true;
        } else if (!TextUtils.isEmpty(password_confirm) && !checkPwd(password, password_confirm)) {
            mPasswordComfirm.setError("密码输入不一致");
            focusView = mPasswordComfirm;
            cancel = true;
        }
        if (TextUtils.isEmpty(code)) {
            mMsgCode.setError("验证码不能为空");
            focusView = mMsgCode;
            cancel = true;
        } else if (!TextUtils.isEmpty(code) && code.length() < 6) {
            mMsgCode.setError("验证码输入有误");
            focusView = mMsgCode;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!checkPhone(phone)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mLoginFormView.getWindowToken(), 0);
            showProgress(true);
            mAuthTask = new UserLoginTask(phone, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean checkPhone(String str) {
        // TODO: 2017/7/19 检查手机号码
        if (str.length() == 11 && str.startsWith("1")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPwd(String password) {
        return (password != null && password.length() > 3);
    }

    private boolean checkPwd(String password, String confirm) {
        return (password != null && password.length() > 3 && password.equals(confirm));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            Log.i("异步登录任务", "登录成功");
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                finish();
            } else {
                showProgress(false);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

