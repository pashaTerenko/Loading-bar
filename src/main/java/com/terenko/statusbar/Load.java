package com.terenko.statusbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class Load implements Runnable {
    private static final int BUFFER_SIZE = 4096;

    private URL url;
    private int size;
    private int complect = 0;
    private String filename;
    private String uuid;
    private boolean isStop=false;
    private boolean isDelete=false;
    private Loader loader;

    public Load(Loader l, String urlp) {
        loader = l;
        uuid = UUID.randomUUID().toString();
        try {

            url = new URL(urlp);
            URLConnection conn = url.openConnection();
            size = conn.getContentLength();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String fileURL = url.toString();
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                try {
                    if (disposition != null) {
                        // extracts file name from header field
                        int index = disposition.indexOf("filename=");
                        if (index > 0) {
                            fileName = disposition.substring(index + 10);
                            String fl = fileName.substring(fileName.lastIndexOf("."));
                            int fll = fl.length();
                            if (fll > 3) {
                                String fl1 = fileName.substring(0, fileName.lastIndexOf(".") + 4);
                                fileName = fl1;
                            }


                        }
                    } else {
                        // extracts file name from URL
                        fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);
                System.out.println("fileName = " + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = "D:\\" + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(new File(saveFilePath));

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1||!isDelete) {
                    if (isStop)
                        Thread.yield();
                    else {
                        outputStream.write(buffer, 0, bytesRead);
                        complect += bytesRead;
                    }
                }
                if(!isDelete){
                loader.editMap(this, 100);
                    System.out.println("File downloaded");
                }

                outputStream.close();
                inputStream.close();


            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Thread getThread() {
        return Thread.currentThread();
    }
    public synchronized void interrupt() {
       isDelete=true;
    }
    public synchronized void Stop() {
        isStop=true;
    }
    public synchronized void resume() {
        isStop=false;
    }
    public synchronized URL getUrl() {
        return url;
    }

    public synchronized int getSize() {
        return size;
    }

    public synchronized int getComplect() {
        return complect;
    }

    public synchronized String getUuid() {
        return uuid;
    }

    public synchronized String getFilename() {
        return filename;
    }
}
