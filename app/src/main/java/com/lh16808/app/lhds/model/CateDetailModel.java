package com.lh16808.app.lhds.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CateDetailModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String title;
    /**
     * sid
     */
    String url;
    /**
     * newstime
     */
    String desTitle;

    public CateDetailModel(String title, String url, String desTitle) {
        this.title = title;
        this.url = url;
        this.desTitle = desTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesTitle() {
        return desTitle;
    }

    public void setDesTitle(String desTitle) {
        this.desTitle = desTitle;
    }
}
