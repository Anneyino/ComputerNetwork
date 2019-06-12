package web;


import java.io.*;
import java.util.*;

import web.entity.MIME;
import web.entity.Method;
import web.entity.Status;

public class Response {
    private String version = "HTTP/1.1";
    private String status;
    private ArrayList<String> header = new ArrayList<>();
    private byte[] entity;
    private String mime;

    private final String resPath = "webroot";

    public Response(Request req) {
        if (req.method.equals(Method.GET)) {
            get(req);
        } else if (req.method.equals(Method.POST)) {
            post(req);
        }
        setHeaders();
    }

    private void get(Request req) {
        try {
            //读取HTML文件，保存与RES
            File res = new File(resPath + req.url);
            if (res.exists()) {
                //改变服务器状态
                status = Status.OK;
                mime = MIME.toString(MIME.HTML);
                setResponse(getBytes(res));
            } else {
                status = Status.NOT_FOUND;
                mime = MIME.toString(MIME.TXT);
                setResponse(Status.NOT_FOUND);
            }
        } catch (Exception e) {

            //异常处理机制
            e.printStackTrace();
            status = Status.BAD_REQUEST;
            mime = MIME.toString(MIME.TXT);
            setResponse(Status.BAD_REQUEST);
        }
    }


    private void post(Request req) {
        try {
            if (!req.postEntityBody.isEmpty()) {
                 //改变服务器状态
                status = Status.OK;
                mime = MIME.toString(MIME.JSON);
                StringBuilder res = new StringBuilder("get:\n");
                for (String key : req.postEntityBody.keySet()) {
                    res.append(key).append(":").append(req.postEntityBody.get(key)).append("\n");
                }
                setResponse(res.toString());
            } else {
                status = Status.OK;
                mime = MIME.toString(MIME.JSON);
                setResponse("None Json Data");
            }

        } catch (Exception e) {
            //异常处理机制
            e.printStackTrace();
            status = Status.BAD_REQUEST;
            mime = MIME.toString(MIME.TXT);
            setResponse(Status.BAD_REQUEST);
        }
    }

    private byte[] getBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }


    private void setHeaders() {
        header.clear();
        header.add(version + " " + status);
        header.add("Connection: close");
        header.add("Server: WebServer");
        if (mime != null) {
            header.add(mime);
        }
    }

    private void setResponse(String response) {
        setResponse(response.getBytes());
    }

    private void setResponse(byte[] response) {
        entity = response;
    }

    public void write(OutputStream os) throws IOException {
        DataOutputStream output = new DataOutputStream(os);
        for (String header : header) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");
        if (entity != null) {
            output.write(entity);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

}