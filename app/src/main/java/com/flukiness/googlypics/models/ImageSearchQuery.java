package com.flukiness.googlypics.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/21/14.
 */
public class ImageSearchQuery implements Parcelable {
    private static final String SEARCH_PREFIX = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private static final String NUM_PER_PAGE_PARAM = "&rsz=";
    private static final String START_PARAM = "&start=";
    private static final String SIZE_PARAM = "&imgsz=";
    private static final String COLOR_PARAM = "&imgcolor=";
    private static final String TYPE_PARAM = "&imgtype=";
    private static final String SITE_PARAM = "&as_sitesearch=";

    public static final int MAX_NUM_PER_PAGE = 8;
    public static final int MAX_PAGES = 8; // Maximum # of pages the API returns

    public enum SIZE {
        icon, medium, xxlarge, huge
    }
    public enum COLOR {
        black, blue, brown, gray, green, orange, pink, purple, red, teal, white, yellow
    }
    public enum TYPE {
        face, photo, clipart, lineart
    }

    public int numPerPage = MAX_NUM_PER_PAGE;
    public int page = 0;
    public String query;

    // Options, any can be null
    public SIZE size;
    public COLOR color;
    public TYPE type;
    public String site;

    public ImageSearchQuery(ImageSearchQuery q) {
        this.numPerPage = q.numPerPage;
        this.page = q.page;
        this.query = q.query;
        this.size = q.size;
        this.color = q.color;
        this.type = q.type;
        this.site = q.site;
    }

    public String toString() {
        String str = SEARCH_PREFIX + query + NUM_PER_PAGE_PARAM + numPerPage;

        if (page > 0) {
            str += START_PARAM + (numPerPage * page);
        }
        if (size != null) {
            str += SIZE_PARAM + size.name();
        }
        if (color != null) {
            str += COLOR_PARAM + color.name();
        }
        if (type != null) {
            str += TYPE_PARAM + type.name();
        }
        if (site != null && !site.isEmpty()) {
            str += SITE_PARAM + site;
        }

        return str;
    }

    public void resetQuery() {
        query = null;
        page = 0;
    }

    public void resetSettings() {
        numPerPage = MAX_NUM_PER_PAGE;
    }

    public boolean validate() {
        if (query == null || query.isEmpty())
            return false;
        if (numPerPage > MAX_NUM_PER_PAGE)
            return false;
        if (page >= MAX_PAGES)
            return false;

        return true;
    }

    public boolean isEqual(ImageSearchQuery q) {
        // Note that we don't compare the current page since that is dynamic even for the same search.
        if (numPerPage == q.numPerPage && query == q.query && size == q.size && color == q.color &&
                type == q.type && site == q.site) {
            return true;
        }

        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numPerPage);
        dest.writeInt(this.page);
        dest.writeString(this.query);
        dest.writeInt(this.size == null ? -1 : this.size.ordinal());
        dest.writeInt(this.color == null ? -1 : this.color.ordinal());
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.site);
    }

    public ImageSearchQuery() {
    }

    private ImageSearchQuery(Parcel in) {
        this.numPerPage = in.readInt();
        this.page = in.readInt();
        this.query = in.readString();
        int tmpSize = in.readInt();
        this.size = tmpSize == -1 ? null : SIZE.values()[tmpSize];
        int tmpColor = in.readInt();
        this.color = tmpColor == -1 ? null : COLOR.values()[tmpColor];
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : TYPE.values()[tmpType];
        this.site = in.readString();
    }

    public static final Creator<ImageSearchQuery> CREATOR = new Creator<ImageSearchQuery>() {
        public ImageSearchQuery createFromParcel(Parcel source) {
            return new ImageSearchQuery(source);
        }

        public ImageSearchQuery[] newArray(int size) {
            return new ImageSearchQuery[size];
        }
    };
}
