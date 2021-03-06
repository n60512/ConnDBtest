package com.example.kobe24.conndbtest;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.*;

public class ConnDB {

    private String database = null;
    private String webRequest = null;      //  web request;type:json

    /**
     * 處理遊戲 drawing,drawingmult 建構子
     *
     * @param database
     * @param content
     * @param crtuser
     */
    public ConnDB(String database, String content, String crtuser, Bitmap image) {
        this.database = database;
        switch (this.database) {
            case "drawing":
                draw(content, crtuser, toBase64(image));
                break;
            case "drawingmult":
                draw(content, crtuser, toBase64(image));
                break;
            default:
                ;
        }
    }

    /**
     * oneimage 建構子
     *
     * @param database
     * @param content
     * @param crtuser
     * @param imageID
     */
    public ConnDB(String database, String content, String crtuser, String imageID) {
        this.database = database;

        if (this.database.equals("oneimage")) {
            oneimage(content, crtuser, imageID);
        } else {
        }

    }

    /**
     * 處理遊戲 association 建構子
     *
     * @param database
     * @param description
     * @param chosenList
     * @param crtuser
     */
    public ConnDB(String database, String description, String[] chosenList, String crtuser) {
        this.database = database;

        int index = 0;
        String tmpText = "";
        for (String option : chosenList)
            tmpText = tmpText + "'chosen" + Integer.toString(index = index + 1) + "','" + option + "',";

        String chosenContent = "column_create(" + tmpText.substring(0, tmpText.length() - 1) + ")";     // column_create('chosen1','0','chosen2','0' ...)
        //showMessage("test", tmpText); for test

        if (this.database.equals("association")) {
            association(description, chosenContent, crtuser);
        } else {
        }
    }

    /**
     * @param Name    pictureName
     * @param crtuser userName
     */
    private void draw(String Name, String crtuser, String base64img) {

        JSONObject obj;
        //Object jsonOb = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://140.122.91.218/thinkingapp/connDB/drawing.php");

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("database", this.database));
        params.add(new BasicNameValuePair("Name", Name));
        params.add(new BasicNameValuePair("base64img", base64img));
        params.add(new BasicNameValuePair("crtuser", crtuser));


        try {

            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));    //  set post params
            HttpResponse response = client.execute(request);        //  get web response
            HttpEntity resEntity = response.getEntity();
            webRequest = EntityUtils.toString(resEntity);             //  取得網頁 REQUEST

            showMessage("webRequest", webRequest);

            obj = new JSONObject(this.webRequest);  // parse web request
            String webResponse = obj.getJSONObject(this.database).getString("response");
            showMessage(this.database, webResponse);

        } catch (java.io.IOException e) {
            showMessage("IOException", e.getMessage());
        } catch (org.json.JSONException e) {
            showMessage("JSON Error", e.getMessage());
        }
    }

    /**
     * oneimage 建構子
     *
     * @param Data
     * @param crtuser
     * @param imageID
     */
    private void oneimage(String Data, String crtuser, String imageID) {

        JSONObject obj;
        //Object jsonOb = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://140.122.91.218/thinkingapp/connDB/oneimage.php");


        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("database", this.database));
        params.add(new BasicNameValuePair("Data", Data));
        params.add(new BasicNameValuePair("imageID", imageID));
        params.add(new BasicNameValuePair("crtuser", crtuser));

        try {

            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));    //  set post params
            HttpResponse response = client.execute(request);        //  get web response

            HttpEntity resEntity = response.getEntity();
            webRequest = EntityUtils.toString(resEntity);             //  取得網頁 REQUEST

            //showMessage("webRequest", webRequest);

            obj = new JSONObject(this.webRequest);  // parse web request
            String webResponse = obj.getJSONObject(this.database).getString("response");
            showMessage(this.database, webResponse);

        } catch (java.io.IOException e) {
            showMessage("IOException", e.getMessage());
        } catch (org.json.JSONException e) {
            showMessage("JSON Error", e.getMessage());
        }
    }

    /**
     * Game association connection
     *
     * @param description
     * @param chosenContent
     * @param crtuser
     */
    private void association(String description, String chosenContent, String crtuser) {

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://140.122.91.218/thinkingapp/connDB/association.php");

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("Description", description));
        params.add(new BasicNameValuePair("ChosenContent", chosenContent));
        params.add(new BasicNameValuePair("crtuser", crtuser));

        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));    //  set post params
            HttpResponse response = client.execute(request);        //  get web response
            HttpEntity resEntity = response.getEntity();
            webRequest = EntityUtils.toString(resEntity);             //  取得網頁 REQUEST

            showMessage("association webRequest", webRequest);

        } catch (java.io.IOException e) {
            showMessage("IOException", e.getMessage());
        }
    }

    /**
     * convert bitmap to base64
     *
     * @param bitmap
     * @return
     */
    public String toBase64(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        bitmap.recycle();   //  recycle resource

        return android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void showMessage(String title, String content) {
        Log.d(title, content);
    }

}
