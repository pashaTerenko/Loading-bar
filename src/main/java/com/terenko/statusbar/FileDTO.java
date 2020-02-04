package com.terenko.statusbar;

public class FileDTO {
    private String urlF;
    private String procent;
    private String uuid;

    FileDTO(String URL, String procent, String u) {
        this.urlF = URL;
        this.procent = procent;
        this.uuid = u;
    }

    public String getUrlF() {
        return urlF;
    }

    public void setUrlF(String urlF) {
        this.urlF = urlF;
    }

    public String getProcent() {
        return procent;
    }

    public void setProcent(String procent) {
        this.procent = procent;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
