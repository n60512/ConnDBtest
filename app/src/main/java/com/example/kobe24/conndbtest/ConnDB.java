package com.example.kobe24.conndbtest;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.*;

public class ConnDB {

    public ConnDB(){
        //String link = "http://140.122.91.218/test/posttest.php";
        drawing("xbox","user1");
    }

    private void  jsonParse(String content){
        JSONObject obj;
        try {
            String tmp = content;//successful error
            obj = new JSONObject(tmp);
            Object jsonOb = obj.getJSONObject("drawing");
            Log.d("json",jsonOb.toString());
        }catch(Exception e){
        }
    }

    /// <summary>
    /// connect drawing db
    /// </summary>
    /// <param name="Name">pictureName</param>
    /// <param name="crtuser">userName</param>
    private void  drawing(String Name,String crtuser){
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://140.122.91.218/test/drawing.php");

        String webRequest = null;      //  web request;type:json
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("Name",Name));
        params.add(new BasicNameValuePair("crtuser",crtuser));

        try {
            request.setEntity(new UrlEncodedFormEntity(params));    //  set post params
            HttpResponse response = client.execute(request);        //  get web response
            HttpEntity resEntity = response.getEntity();
            webRequest = EntityUtils.toString(resEntity);

            Log.d("webRequest",webRequest);

            jsonParse(webRequest);  //  json parse
            //EntityUtils.consume(resEntity);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IOException", e.getMessage());
        }
}

    private void connWeb(String link){

        HttpClient client = new DefaultHttpClient();
        //HttpGet request = new HttpGet(link);
        HttpPost request = new HttpPost(link);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user","John"));
        params.add(new BasicNameValuePair("age","23"));

        try {
            request.setEntity(new UrlEncodedFormEntity(params));    //set params

            HttpResponse response = client.execute(request);
            HttpEntity resEntity = response.getEntity();
            String webRequest = EntityUtils.toString(resEntity);
            Log.d("webRequest",webRequest);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IOException", e.getMessage());
        }

    }
}
