package org.recoapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by younghan on 2017. 2. 3..
 */

public class FileDownloadDAO {

    private Bitmap imageFile = null;
    public Bitmap imageDownload(Context context, FileDTO fileDTO)
    {
        RequestParams params = new RequestParams();
        params.add("file_code", fileDTO.getFile_code());
        RecoHttpClient.getInstance().post(context, "file/download", params, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                try {
                    imageFile = BitmapFactory.decodeByteArray(response, 0, response.length);

                } catch (Exception e) {
                    Log.d("imagecode", e.getMessage());
                    e.printStackTrace();
                }
            }
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                imageFile = null;
            }
        });

        return imageFile;
    }



}
