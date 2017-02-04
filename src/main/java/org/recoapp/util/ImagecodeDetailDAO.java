package org.recoapp.util;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by younghan on 2017. 2. 3..
 */

public class ImagecodeDetailDAO {

    private JSONArray jsonData = null;
    private ArrayList<ImagecodeDTO> imagecodeDTOList = null;
    private ArrayList<ImagecodeDTO> imagecodeDTOTagList = null;
    public JSONArray selectImageCodeDetail(Context context, ImagecodeDTO imagecodeDTO)
    {
        Log.d("imagecode", imagecodeDTO.toString());


        RequestParams params = new RequestParams();
        params.add("imagecode_code", imagecodeDTO.getImagecode_code());
        RecoHttpClient.getInstance().post(context, "file/search_imagecode", params, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                try {
                    String input = new String(response, 0, response.length, "UTF-8");
                    jsonData = new JSONArray(input);

                } catch (Exception e) {
                    Log.d("imagecode", e.getMessage());
                    e.printStackTrace();
                }
            }
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                Log.d("imagecode","failure get Image code detail");
            }
        });

        return jsonData;
    }

    public ArrayList<ImagecodeDTO> selectImageCodeAll(Context context)
    {

        RequestParams params2 = new RequestParams();
        RecoHttpClient.getInstance().post(context, "imagecode/search_all", params2, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                try {
                    String input = new String(response, 0, response.length, "UTF-8");
                    JSONArray arr = new JSONArray(input);
                    if(arr.length() > 0) {imagecodeDTOList = new ArrayList<ImagecodeDTO>();}
                    for (int i=0; i<arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        imagecodeDTOList.add(new ImagecodeDTO(obj.getString("imagecode_code"), obj.getString("member_code"),
                                obj.getString("imagecode_latest_modifydate"), obj.getString("imagecode_name"), obj.getString("imagecode_url")));
                    }
                    Log.d("imagecode", imagecodeDTOList.size()+"");


                } catch (Exception e) {
                    Log.d("imagecode", e.getMessage());
                    e.printStackTrace();
                }
            }
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                imagecodeDTOList = null;
            }
        });
        return imagecodeDTOList;
    }

    public ArrayList<ImagecodeDTO> selectImageCodeTag(Context context)
    {
        RequestParams params2 = new RequestParams();
        RecoHttpClient.getInstance().post(context, "imagecode/search_tag", params2, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                try {
                    String input = new String(response, 0, response.length, "UTF-8");
                    JSONArray arr = new JSONArray(input);
                    if(arr.length() > 0 ) {imagecodeDTOTagList = new ArrayList<ImagecodeDTO>();}
                    for (int i=0; i<arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        imagecodeDTOTagList.add(new ImagecodeDTO(obj.getString("imagecode_code"), obj.getString("member_code"),
                                obj.getString("imagecode_latest_modifydate"), obj.getString("imagecode_name"), obj.getString("imagecode_url")));
                    }
                    Log.d("imagecode", imagecodeDTOTagList.size()+"");
                } catch (Exception e) {
                    Log.d("imagecode", e.getMessage());
                    e.printStackTrace();
                }
            }
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                imagecodeDTOTagList = null;
            }
        });
        return imagecodeDTOTagList;
    }


}
