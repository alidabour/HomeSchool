package com.example.ali.homeschool.InstructorTopic;

/**
 * Created by Ali on 6/21/2017.
 */

public interface XMLEditClick extends XMLClickParent {
    void onEditImageView(int id,String src,String layout);
     void onEditSound(int id, String audioUrl,String audioText, String layout);

}
