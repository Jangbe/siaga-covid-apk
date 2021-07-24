package com.smkypc.siagacovid;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by jens on 25.03.17.
 */
public class MyHTTPD extends NanoHTTPD {
    public static final int PORT = 8765;
    private Context mContext;

    public MyHTTPD(Context context) throws IOException {
        super(PORT);
        mContext = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.i("TAG", "serve: "+session.getUri());
        String uri = session.getUri();
        String filename = uri.substring(1);

        if (uri.equals("/"))
            filename = "views/index.html";
        boolean is_ascii = true;
        String mimetype = "text/html";
        if (filename.contains(".html") || filename.contains(".htm")) {
            mimetype = "text/html";
            is_ascii = true;
        } else if (filename.contains(".js")) {
            mimetype = "text/javascript";
            is_ascii = false;
        } else if (filename.contains(".css")) {
            mimetype = "text/css";
            is_ascii = false;
        } else if (filename.contains(".gif")) {
            mimetype = "text/gif";
            is_ascii = false;
        } else if (filename.contains(".jpeg") || filename.contains(".jpg")) {
            mimetype = "text/jpeg";
            is_ascii = false;
        } else if (filename.contains(".png")) {
            mimetype = "image/png";
            is_ascii = false;
        } else if (filename.contains(".bin")) {
            mimetype = "application/octet-stream";
            is_ascii = false;
        }else if (filename.contains(".json")) {
            mimetype = "application/json";
            is_ascii = false;
        }else if (filename.contains(".mp3")) {
            mimetype = "audio/mpeg";
            is_ascii = false;
        }else if (filename.contains(".mp4")) {
            mimetype = "video/mp4";
            is_ascii = false;
        }else if (filename.contains(".otf")) {
            mimetype = "font/otf";
            is_ascii = false;
        }else if (filename.contains(".ico")) {
            mimetype = "image/vnd.microsoft.icon";
            is_ascii = false;
        }else {
            filename = "views/index.html";
            mimetype = "text/html";
        }

        if (is_ascii) {
            String response = "";
            String line = "";
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(filename)));

                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newFixedLengthResponse(Response.Status.OK, mimetype, response);
        } else {
            InputStream isr;
            try {
                isr = mContext.getAssets().open(filename);
                return newFixedLengthResponse(Response.Status.OK, mimetype, isr, isr.available());
            } catch (IOException e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.OK, mimetype, "");
            }
        }
    }

}