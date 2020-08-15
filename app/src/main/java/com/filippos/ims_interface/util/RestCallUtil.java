package com.filippos.ims_interface.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public abstract class RestCallUtil {

    public static final String makeHttpRequest(URL url, HttpMethod httpMethod) throws IOException {

        HttpURLConnection urlConnection;

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(httpMethod.getHttpMethod());

        return readInputStream(urlConnection);
    }

    private static final String readInputStream(HttpURLConnection urlConnection) throws IOException {

        StringBuilder result = new StringBuilder();

        try {

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();
            char current;

            while (data != -1) {

                current = (char) data;

                result.append(current);

                data = reader.read();

            }

        } catch(IOException e) {

            e.printStackTrace();

        }

        finally {

            urlConnection.disconnect();
        }


        return result.toString();
    }
}

