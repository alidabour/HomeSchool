package com.example.ali.homeschool.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 3/1/2017.
 */
public class HeaderRVData {
    String header;
    List<CategoryInformation> categoryInformations;

    public HeaderRVData(String header, List<CategoryInformation> categoryInformations) {
        this.header = header;
        this.categoryInformations = categoryInformations;
    }

    public List<CategoryInformation> getCategoryInformations() {
        return categoryInformations;
    }

    public void setCategoryInformations(ArrayList<CategoryInformation> categoryInformations) {
        this.categoryInformations = categoryInformations;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
