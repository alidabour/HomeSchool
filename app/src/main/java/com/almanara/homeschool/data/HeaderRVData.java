package com.almanara.homeschool.data;

import com.almanara.homeschool.data.firebase.Courses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 3/1/2017.
 */
public class HeaderRVData {
    String header;
    List<Courses> categoryInformations;

    public HeaderRVData(String header, List<Courses> categoryInformations) {
        this.header = header;
        this.categoryInformations = categoryInformations;
    }

    public List<Courses> getCategoryInformations() {
        return categoryInformations;
    }

    public void setCategoryInformations(ArrayList<Courses> categoryInformations) {
        this.categoryInformations = categoryInformations;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
