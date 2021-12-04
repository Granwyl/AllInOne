package id.ac.istts;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;



public class InternetTask extends AsyncTask<String, Void, String> {
    public static String ip="http://proyekeo.xyz/";
    //public static String ip="http://192.168.1.8/isaac/";
    public ProgressDialog dialog;// = new ProgressDialog();
    public Context ctx;
    public String title;
    public String content;
    TResponse ar;
    boolean post=false;
    JSONObject param=null;
    public InternetTask(Context ctx, String title, String content, TResponse ar, boolean post, JSONObject param)
    {
        this.ctx=ctx;
        this.title=title;
        this.content=content;
        this.ar=ar;

        this.param=param;
        this.post=post;
        dialog=new ProgressDialog(ctx);

    }

    @Override
    protected void onPreExecute() {
        if (!title.equals(""))
        {
            dialog.setMessage(content);
            dialog.setTitle(title);
            dialog.show();
        }

    }

    @Override
    protected String doInBackground(String... url) {
        if (post)
        {
            String hasil="";
            try {
                hasil=sendPost(url[0],param);
            }
            catch (Exception ex)
            {

            }
            return hasil;
        }
        else {
            String readStream="";
            try
            {
                URL urlx = new URL(url[0]);
                HttpURLConnection con = (HttpURLConnection) urlx.openConnection();
                try
                {
                    readStream = readStream(con.getInputStream());
                }
                catch (Exception ex)
                {
                    readStream = readStream(con.getErrorStream());
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return readStream;
        }

    }
    private static String encodeParams(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    public static String sendPost(String r_url , JSONObject postDataParams) throws Exception {
        URL url = new URL(r_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(20000);
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
        writer.write(encodeParams(postDataParams));
        writer.flush();
        writer.close();
        os.close();

        int responseCode=conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            BufferedReader in=new BufferedReader( new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();
        }
        return null;
    }

    private String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    @Override
    protected void onPostExecute(String result) {
        try
        {
            if (!title.equals(""))
            {
                dialog.dismiss();
            }

            ar.execute(result);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
