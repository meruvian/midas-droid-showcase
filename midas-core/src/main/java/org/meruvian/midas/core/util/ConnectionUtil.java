/*
 * Copyright 2012 Meruvian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.meruvian.midas.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Base64;
import android.util.Log;

/**
 * @author Dias Nurul Arifin
 * 
 */
public class ConnectionUtil {
	
	public static final int TIMEOUT = 15000;

	public static boolean isInternetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null)
			return cm.getActiveNetworkInfo().isConnectedOrConnecting();
		else
			return false;
	}
	
	public static HttpParams getHttpParams(int connectionTimeout,
			int socketTimeout) {
		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, socketTimeout);

		return params;
	}
	
	public static JSONObject get(String url) {
		JSONObject json = null;
		
		try {
			HttpClient httpClient = new DefaultHttpClient(getHttpParams(TIMEOUT, TIMEOUT));
			HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(httpGet);
			
			json = new JSONObject(convertEntityToString(response.getEntity()));
		} catch (IOException e) {
			json = null;
			e.printStackTrace();
		} catch (JSONException e) {
			json = null;
			e.printStackTrace();
		} catch (Exception e) {
			json = null;
			e.printStackTrace();
		}
		return json;
	}

    public static JSONObject postHttp(String url, String authorization){
        JSONObject json = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String authBase64 = Base64.encodeToString(authorization.getBytes(), Base64.DEFAULT);

        httpPost.addHeader("Authorization", "Basic " + authBase64);
        httpPost.setHeader("Host", httpPost.getURI().getHost() + ":" + httpPost.getURI().getPort());

        Log.d("ConnectionUtil", "HttpPost: " + httpPost.getURI().toString());
        for (Header header : httpPost.getAllHeaders()) {
            Log.d("ConnectionUtil Headers: ", header.toString());
        }

        try {
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            Log.d("ConnectionUtil", "Response Code: " + response2.getStatusLine().getStatusCode()
                    + " " +response2.getStatusLine().getReasonPhrase());

            Log.d("ConnectionUtil", "Response " +convertEntityToString(response2.getEntity()));

            json = new JSONObject(convertEntityToString(response2.getEntity()));

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ConnectionUtil", e.getMessage(), e);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ConnectionUtil", e.getMessage(), e);
        }

        return json;
    }

    public static JSONObject getWithAuthorizationHeader(String url, String authorization) {
        JSONObject json = null;

        try {
            HttpClient httpClient = new DefaultHttpClient(getHttpParams(TIMEOUT, TIMEOUT));
//            HttpGet httpGet = new HttpGet(url);
            HttpPost httpPost = new HttpPost(url);

            String authBase64 = Base64.encodeToString(authorization.getBytes(), Base64.DEFAULT);

            httpPost.setHeader("Authorization", "Basic " + authBase64);

            Log.d("ConnectionUtil", "HttpGet: " + httpPost.getURI().toString());
            for (Header header : httpPost.getAllHeaders()) {
                Log.d("ConnectionUtil Headers: ", header.toString());
            }

            HttpResponse response = httpClient.execute(httpPost);

            Log.d("ConnectionUtil", "Response Code: " + response.getStatusLine().getStatusCode()
                    + " " +response.getStatusLine().getReasonPhrase());

            Log.d("ConnectionUtil", "Response " +convertEntityToString(response.getEntity()));

            json = new JSONObject(convertEntityToString(response.getEntity()));
        } catch (IOException e) {
            json = null;
            e.printStackTrace();
        } catch (JSONException e) {
            json = null;
            e.printStackTrace();
        } catch (Exception e) {
            json = null;
            e.printStackTrace();
        }
        return json;
    }
	
	public static JSONObject post(String url, List<NameValuePair> nameValuePairs) {
		JSONObject json = null;
		try {
			HttpClient httpClient = new DefaultHttpClient(getHttpParams(TIMEOUT, TIMEOUT));
			HttpPost httpPost = new HttpPost(url);
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpClient.execute(httpPost);
			json = new JSONObject(convertEntityToString(response.getEntity()));
		} catch (IOException e) {
			json = null;
			e.printStackTrace();
		} catch (JSONException e) {
			json = null;
			e.printStackTrace();
		} catch (Exception e) {
			json = null;
			e.printStackTrace();
		}
		return json;
	}

	public static String convertEntityToString(HttpEntity entity) {
		InputStream instream;
		StringBuilder total = null;
		try {
			instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream));
			total = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				total.append(line);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total.toString();
	}
}