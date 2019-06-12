package web;

import web.entity.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Request {
    String method;
    String url;
    String version;
    Hashtable<String, String> header = new Hashtable<>();

    Hashtable<String, String> postEntityBody = new Hashtable<>();

    public Request(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String str = reader.readLine();
            if (str == null) {
                throw new IOException("null request");
            }
            System.out.println(str);
            String[] split = str.split("\\s+");

            method = split[0];
            url = split[1];
            version = split[2];

            boolean contentUnknow = false;


            while (!str.equals("")) {
                str = reader.readLine();
                System.out.println(str);
                if (str.equals("")) {
                    break;
                }
                String[] strs = str.split(":", 2);
                header.put(strs[0].trim(), strs[1].trim());
            }

            // POST
            if (method.equals(Method.POST)) {
                if (!header.get("Content-Type").equals("application/json")) {
                    throw new Exception("post method now does only support application/json type");
                }
                if (header.get("Content-Length") != null) {
                    char[] content = new char[Integer.parseInt(header.get("Content-Length"))];
                    int length = reader.read(content);
                    String[] contentStr = String.valueOf(content).split("&");
                    for (String i : contentStr) {
                        String[] keyValue = i.split("=");
                        postEntityBody.put(keyValue[0], keyValue[1]);
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Parse Request Error");
        }
    }


}