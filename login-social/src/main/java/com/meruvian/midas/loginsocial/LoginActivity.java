package com.meruvian.midas.loginsocial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.meruvian.midas.core.defaults.DefaultActivity;
import org.meruvian.midas.core.service.TaskService;
import org.meruvian.midas.social.SocialVariable;
import org.meruvian.midas.social.activity.WebViewActivity;
import org.meruvian.midas.social.task.facebook.RequestAccessFacebook;
import org.meruvian.midas.social.task.facebook.RequestTokenFacebook;
import org.meruvian.midas.social.task.google.RequestAccessGoogle;
import org.meruvian.midas.social.task.google.RequestTokenGoogle;
import org.meruvian.midas.social.task.mervid.RequestAccessMervID;
import org.meruvian.midas.social.task.mervid.RequestTokenMervID;
import org.meruvian.midas.social.task.yamaid.RequestAccessYamaID;
import org.meruvian.midas.social.task.yamaid.RequestTokenYamaID;
import com.meruvian.midas.loginsocial.R;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by merv on 6/8/15.
 */
public class LoginActivity extends DefaultActivity implements TaskService {

    @InjectView(R.id.button_facebook_login)
    Button facebookLogin;
    @InjectView(R.id.button_google_login)
    Button googleLogin;
    @InjectView(R.id.button_mervid_login)
    Button mervidLogin;
    @InjectView(R.id.button_yamaid_login)
    Button yamaidLogin;

    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    @Override
    protected int layout() {
        return R.layout.activity_login;
    }

    @Override
    public void onViewCreated() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        updateViewFacebook();
        updateViewGoogle();
        updateViewMervID();
        updateViewYamaID();
    }

    private void updateViewGoogle() {
        if (preferences.getBoolean("google", false)) {
            googleLogin.setText(getString(R.string.refresh_google));
        } else {
            googleLogin.setText(getString(R.string.login_google));
        }
    }

    private void updateViewMervID() {
        if (preferences.getBoolean("mervid", false)) {
            mervidLogin.setText(getString(R.string.refresh_merv_id));
        } else {
            mervidLogin.setText(getString(R.string.login_merv_id));
        }
    }

    private void updateViewYamaID() {
        if (preferences.getBoolean("yamaid", false)) {
            yamaidLogin.setText(getString(R.string.refresh_yama_id));
        } else {
            yamaidLogin.setText(getString(R.string.login_yama_id));
        }
    }

    private void updateViewFacebook() {
        if (preferences.getBoolean("facebook", false)) {
            facebookLogin.setText(getString(R.string.refresh_facebook));
        } else {
            facebookLogin.setText(getString(R.string.login_facebook));
        }
    }


    @Override
    public void onExecute(int code) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }

        if (code == SocialVariable.MERVID_REQUEST_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.signing_in_merv_id));
        } else if (code == SocialVariable.YAMAID_REQUEST_TOKEN_TASK)  {
            progressDialog.setMessage(getString(R.string.signing_in_yama_id));
        } else if (code == SocialVariable.FACEBOOK_REQUEST_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.signing_in_facebook));
        } else if (code == SocialVariable.GOOGLE_REQUEST_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.signing_in_google));
        } else if (code == SocialVariable.MERVID_REQUEST_ACCESS) {
            progressDialog.setMessage(getString(R.string.access_merv_id));
        } else if (code == SocialVariable.YAMAID_REQUEST_ACCESS) {
            progressDialog.setMessage(getString(R.string.access_yama_id));
        } else if (code == SocialVariable.FACEBOOK_REQUEST_ACCESS) {
            progressDialog.setMessage(getString(R.string.access_facebook));
        } else if (code == SocialVariable.GOOGLE_REQUEST_ACCESS) {
            progressDialog.setMessage(getString(R.string.access_google));
        } else if (code == SocialVariable.MERVID_REFRESH_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.update_merv_id));
        } else if (code == SocialVariable.YAMAID_REFRESH_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.update_yama_id));
        } else if (code == SocialVariable.FACEBOOK_REFRESH_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.update_facebook));
        } else if (code == SocialVariable.GOOGLE_REFRESH_TOKEN_TASK) {
            progressDialog.setMessage(getString(R.string.update_google));
        }
    }


    @Override
    public void onSuccess(int code, Object object) {
        progressDialog.dismiss();

        if (object != null) {
            if (code == SocialVariable.MERVID_REQUEST_TOKEN_TASK) {
                String result = (String) object;
                if (result != null && !"".equalsIgnoreCase(result)) {
                    mervidLogin.setText(getString(R.string.logout_merv_id));
                    startActivity(new Intent(this, MainActivity.class));
                }

            } else if (code == SocialVariable.YAMAID_REQUEST_TOKEN_TASK) {
                String result = (String) object;
                if (result != null && !"".equalsIgnoreCase(result)) {
                    yamaidLogin.setText(getString(R.string.logout_yama_id));
                    startActivity(new Intent(this, MainActivity.class));
                }
                Log.d(getClass().getSimpleName(), "URL YAMA : " + object.toString());

            } else if (code == SocialVariable.FACEBOOK_REQUEST_TOKEN_TASK) {
                String result = (String) object;
                if (result != null && !"".equalsIgnoreCase(result)) {
                    facebookLogin.setText(getString(R.string.logout_facebook));
                    startActivity(new Intent(this, MainActivity.class));
                }
            } else if (code == SocialVariable.GOOGLE_REQUEST_TOKEN_TASK) {
                String result = (String) object;
                if (result != null && !"".equalsIgnoreCase(result)) {
                    googleLogin.setText(getString(R.string.logout_google));
                    startActivity(new Intent(this, MainActivity.class));
                }
            } else if (code == SocialVariable.MERVID_REQUEST_ACCESS) {

                Log.d(getClass().getSimpleName(), "URL MERVid : " + object.toString());
                Log.d(getClass().getSimpleName(), "Data MervId : " + Uri.parse(object.toString()));

                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", object.toString());
                intent.setData(Uri.parse(object.toString()));
                startActivityForResult(intent, SocialVariable.MERVID_REQUEST_ACCESS);

            } else if (code == SocialVariable.YAMAID_REQUEST_ACCESS) {

                Log.d(getClass().getSimpleName(), "URL YAMA : " + object.toString());
                Log.d(getClass().getSimpleName(), "Data YAMA : " + Uri.parse(object.toString()));

                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", object.toString());
                intent.setData(Uri.parse(object.toString()));
                startActivityForResult(intent, SocialVariable.YAMAID_REQUEST_ACCESS);

            } else if (code == SocialVariable.FACEBOOK_REQUEST_ACCESS) {
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", object.toString());
                intent.setData(Uri.parse(object.toString()));
                startActivityForResult(intent, SocialVariable.FACEBOOK_REQUEST_ACCESS);
            } else if (code == SocialVariable.GOOGLE_REQUEST_ACCESS) {
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", object.toString());
                intent.setData(Uri.parse(object.toString()));
                startActivityForResult(intent, SocialVariable.GOOGLE_REQUEST_ACCESS);
            } else if (code == SocialVariable.MERVID_REFRESH_TOKEN_TASK) {
                Toast.makeText(this, getString(R.string.finish_update_merv_id), Toast.LENGTH_LONG).show();
            } else if (code == SocialVariable.FACEBOOK_REFRESH_TOKEN_TASK) {
                Toast.makeText(this, getString(R.string.finish_update_facebook), Toast.LENGTH_LONG).show();
            } else if (code == SocialVariable.GOOGLE_REFRESH_TOKEN_TASK) {
                Toast.makeText(this, getString(R.string.finish_update_google), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.failed_recieve), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel(int code, String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onError(int code, String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void onClickFacebook() {
        if (preferences.getBoolean("facebook", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("facebook");
            editor.remove("facebook_token");
            editor.remove("facebook_token_type");
            editor.remove("facebook_expires_in");
            editor.remove("facebook_scope");
            editor.remove("facebook_jti");
            editor.commit();

            facebookLogin.setText(getString(R.string.login_facebook));
        } else {
            new RequestAccessFacebook(this, this).execute();
        }
    }

    private void onClickGoogle() {
        if (preferences.getBoolean("google", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("google");
            editor.remove("google_token");
            editor.remove("google_token_type");
            editor.remove("google_expires_in");
            editor.remove("google_scope");
            editor.remove("google_jti");
            editor.commit();

            googleLogin.setText(getString(R.string.login_google));
        } else {
            new RequestAccessGoogle(this, this).execute();
        }
    }

    private void onClickMervID() {
        if (preferences.getBoolean("mervid", false)) {
            Log.d(getClass().getSimpleName(), "OnClick Merv ID 1");
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("mervid");
            editor.remove("mervid_token");
            editor.remove("mervid_token_type");
            editor.remove("mervid_expires_in");
            editor.remove("mervid_scope");
            editor.remove("mervid_jti");
            editor.commit();

            mervidLogin.setText(getString(R.string.login_merv_id));
        } else {
            Log.d(getClass().getSimpleName(), "OnClick Merv ID 2");
            new RequestAccessMervID(this, this).execute();
        }
    }

    private void onClickYamaID() {
        if (preferences.getBoolean("yamaid", false)) {
            Log.d(getClass().getSimpleName(), "OnClick YAMA ID 1");
            SharedPreferences.Editor editor = preferences.edit();

            editor.remove("yamaid");
            editor.remove("yamaid_token");
            editor.remove("yamaid_token_type");
            editor.remove("yamaid_expires_in");
            editor.remove("yamaid_scope");
            editor.remove("yamaid_jti");
            editor.commit();

            yamaidLogin.setText(getString(R.string.login_yama_id));
        } else {
            Log.d(getClass().getSimpleName(), "OnClick YAMA ID 2");
            new RequestAccessYamaID(this, this).execute();
        }
    }

    @OnClick({R.id.button_facebook_login, R.id.button_google_login, R.id.button_mervid_login, R.id.button_yamaid_login})
    public void setOnClickListener(Button button) {
        if (button.getId() == R.id.button_facebook_login) {
            onClickFacebook();
        } else if (button.getId() == R.id.button_google_login) {
            onClickGoogle();
        } else if (button.getId() == R.id.button_mervid_login) {
            onClickMervID();
        } else if (button.getId() == R.id.button_yamaid_login) {
            onClickYamaID();
        }
    }


    private String parseCode(Intent data) {
        Uri uri = data.getData();
        Log.d(getClass().getSimpleName(), "Uri parse Code: " + uri.toString());

        if (uri != null && uri.toString().startsWith(SocialVariable.MERVID_CALLBACK)) {
            String code = uri.getQueryParameter("code");

            if (code != null && !"".equalsIgnoreCase(code)) {
                return code;
            }
        }
        return "null";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SocialVariable.MERVID_REQUEST_ACCESS) {
                Log.d(getClass().getSimpleName(), "Intent Data MervId : " + parseCode(data));
                Log.d(getClass().getSimpleName(), "Request Code MervId : " + requestCode);
                Log.d(getClass().getSimpleName(), "Result Code MervId : " + resultCode);

                new RequestTokenMervID(this, this).execute(parseCode(data));

            } else if (requestCode == SocialVariable.FACEBOOK_REQUEST_ACCESS) {
                new RequestTokenFacebook(this, this).execute(parseCode(data));
            } else if (requestCode == SocialVariable.GOOGLE_REQUEST_ACCESS) {
                new RequestTokenGoogle(this, this).execute(parseCode(data));
            } else if (requestCode == SocialVariable.YAMAID_REQUEST_ACCESS) {
                Log.d(getClass().getSimpleName(), "Intent Data Yama : " + parseCode(data));
                Log.d(getClass().getSimpleName(), "Request Code Yama : " + requestCode);
                Log.d(getClass().getSimpleName(), "Result Code Yama : " + resultCode);

                new RequestTokenYamaID(this, this).execute(parseCode(data));
            }
        }
    }

}
