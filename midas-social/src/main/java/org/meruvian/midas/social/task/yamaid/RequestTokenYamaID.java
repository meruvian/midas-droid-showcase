package org.meruvian.midas.social.task.yamaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONException;
import org.json.JSONObject;
import org.meruvian.midas.core.service.TaskService;
import org.meruvian.midas.core.util.ConnectionUtil;
import org.meruvian.midas.social.R;
import org.meruvian.midas.social.SocialVariable;

/**
 * Created by meruvian on 26/06/15.
 */
public class RequestTokenYamaID extends AsyncTask<String, Void, JSONObject> {
    private TaskService service;
    private Context context;

    public RequestTokenYamaID(TaskService service, Context context) {
        this.service = service;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        service.onExecute(SocialVariable.YAMAID_REQUEST_TOKEN_TASK);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(SocialVariable.YAMA_REQUEST_TOKEN)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(SocialVariable.YAMA_APP_ID)
                    .setClientSecret(SocialVariable.YAMA_API_SECRET)
                    .setRedirectURI(SocialVariable.YAMA_CALLBACK)
                    .setCode(params[0])
                    .buildQueryMessage();

            Log.d(getClass().getSimpleName(), "Code: " + params[0]);
            Log.d(getClass().getSimpleName(), "URI Request Token Yama: " + request.getLocationUri());

            String authorization = SocialVariable.YAMA_APP_ID + ":" + SocialVariable.YAMA_API_SECRET;

            return  ConnectionUtil.postHttp(request.getLocationUri(), authorization);

        } catch (OAuthSystemException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                Log.i(getClass().getSimpleName(), "json : " + jsonObject.toString());

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putBoolean("yamaid", true);
                editor.putString("yamaid_token", jsonObject.getString("access_token"));
                editor.putString("yamaid_refresh_token", jsonObject.getString("refresh_token"));
                editor.putString("yamaid_token_type", jsonObject.getString("token_type"));
                editor.putLong("yamaid_expires_in", jsonObject.getLong("expires_in"));
                editor.putString("yamaid_scope", jsonObject.getString("scope"));
                editor.putString("yamaid_jti", jsonObject.getString("jti"));
                editor.commit();

                service.onSuccess(SocialVariable.YAMAID_REQUEST_TOKEN_TASK, jsonObject.getString("access_token"));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.getMessage(), e);
                service.onError(SocialVariable.YAMAID_REQUEST_TOKEN_TASK, context.getString(R.string.failed_recieve));
            }
        } else {
            service.onError(SocialVariable.YAMAID_REQUEST_TOKEN_TASK, context.getString(R.string.failed_recieve));
        }
    }
}
