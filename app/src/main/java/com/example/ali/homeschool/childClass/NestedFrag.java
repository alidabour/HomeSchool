package com.example.ali.homeschool.childClass;

/**
 * Created by Ali on 6/24/2017.
 */


//import android.app.Fragment;

import android.widget.ImageButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ali.homeschool.R;

public class NestedFrag extends Fragment {
    public NestedFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.nested_frag, container, false);
        ImageButton doNestingButton = (ImageButton) root.findViewById(R.id.image_button);
        return root;
    }

}