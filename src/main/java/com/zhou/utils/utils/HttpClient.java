package com.zhou.utils.utils;

import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;


/**
 * @author Wenyu Zhou
 */
public class HttpClient {

    public static String sendHttp(String url, String param, Map<String, String> headers, String method, Integer timeOut) throws Exception {
        method = method == null ? "GET" : method.toUpperCase();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        if (!StringUtils.isEmpty(param) && "GET".equalsIgnoreCase(method)) {
            url = url + "?" + param;
        }
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
        if (timeOut != null && timeOut > 0) {
            httpURLConnection.setReadTimeout(timeOut * 1000);
        }
        httpURLConnection.setRequestMethod(method);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
        }
        httpURLConnection.setDoInput(true);
        boolean bool = !StringUtils.isEmpty(param) &&
                ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method));
        if (bool) {
            httpURLConnection.setDoOutput(true);
            out = new PrintWriter(httpURLConnection.getOutputStream());
            out.print(param);
            out.flush();
        }
        httpURLConnection.connect();
        /*
         * 判断getResponseCode，
         * 当返回不是HttpURLConnection.HTTP_OK， HttpURLConnection.HTTP_CREATED， HttpURLConnection.HTTP_ACCEPTED 时，
         * 不能用getInputStream()，而是应该用getErrorStream()
         */
        int responseCode = httpURLConnection.getResponseCode();
        try {
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        System.out.println("http 请求 url = " + url + " ; result = " + result);
        return result;
    }

    public static String[] sendHttps(String url, String param, Map<String, String> headers, String method, Integer timeOut) throws Exception {
        method = method == null ? "GET" : method.toUpperCase();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        if (!StringUtils.isEmpty(param) && "GET".equalsIgnoreCase(method)) {
            url = url + "?" + param;
        }
        HttpsURLConnection.setDefaultHostnameVerifier(new HttpClient().new NullHostNameVerifier());
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        URL realUrl = new URL(null, url, new sun.net.www.protocol.https.Handler());
        URLConnection conn = realUrl.openConnection();
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) conn;
        if (timeOut != null && timeOut > 0) {
            httpsURLConnection.setReadTimeout(timeOut * 1000);
        }
        httpsURLConnection.setRequestMethod(method);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpsURLConnection.setRequestProperty(header.getKey(), header.getValue());
        }

        httpsURLConnection.setDoInput(true);
        boolean bool = !StringUtils.isEmpty(param) && ("POST".equalsIgnoreCase(method)
                || "PUT".equalsIgnoreCase(method));
        if (bool) {
            httpsURLConnection.setDoOutput(true);
            out = new PrintWriter(httpsURLConnection.getOutputStream());
            out.print(param);
            out.flush();
        }
        httpsURLConnection.connect();
        /*
         * 判断getResponseCode，
         * 当返回不是HttpURLConnection.HTTP_OK， HttpURLConnection.HTTP_CREATED， HttpURLConnection.HTTP_ACCEPTED 时，
         * 不能用getInputStream()，而是应该用getErrorStream()
         */
        int responseCode = httpsURLConnection.getResponseCode();
        try {
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(httpsURLConnection.getErrorStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        System.out.println("http_S 请求 url = " + url + " ; result = " + result);
        return new String[]{String.valueOf(responseCode), result};
    }

    public class NullHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }

    static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    }};


}
