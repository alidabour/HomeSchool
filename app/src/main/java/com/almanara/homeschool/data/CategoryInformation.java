package com.almanara.homeschool.data;

/**
 * Created by Ali on 1/22/2017.
 * This is the class that i can create an object from that will have the
 * image and the text of the course
 */

public class CategoryInformation {
    private String categoryName;
    private int categoryImage ;

    public CategoryInformation(String categoryName, int categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
