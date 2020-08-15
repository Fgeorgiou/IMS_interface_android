package com.filippos.ims_interface.data.remote;

import android.os.AsyncTask;

import com.filippos.ims_interface.util.HttpMethod;
import com.filippos.ims_interface.util.RestCallUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PostAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        URL url;

        try {

            url = new URL(urls[0]);

            return RestCallUtil.makeHttpRequest(url, HttpMethod.POST);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e){

            e.printStackTrace();

        }

        return null;

    }
}
