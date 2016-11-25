package com.lh16808.app.lhds.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BannerUrl implements Parcelable {
    String titlepic;
    String title;

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int describeContents() {
        return 0;
    }

    public BannerUrl() {

    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(titlepic);
        out.writeString(title);
    }

    public static final Creator<BannerUrl> CREATOR
            = new Creator<BannerUrl>() {
        public BannerUrl createFromParcel(Parcel in) {
            return new BannerUrl(in);
        }

        public BannerUrl[] newArray(int size) {
            return new BannerUrl[size];
        }
    };

    private BannerUrl(Parcel in) {
        titlepic = in.readString();
        title = in.readString();
    }
}
