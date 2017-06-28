package com.almanara.homeschool.instructor.create.xmlinterface;

/**
 * Created by Ali on 6/21/2017.
 */

public interface XMLEditClick extends XMLClickParent {
    void onEditImageView(int id,String src,String layout);
     void onEditSound(int id, String audioUrl,String audioText, String layout);
    void onEditColorQuestion(int id,String questionTitle,String layout);
    void onEditTextView(int id,String text,String layout);

}
