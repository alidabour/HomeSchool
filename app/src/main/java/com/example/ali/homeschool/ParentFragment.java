package com.example.ali.homeschool;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link ParentFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ParentFragment#newInstance} factory method to
// * create an instance of this fragment.
// */

public class ParentFragment extends Fragment {
    View view;

    public ParentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_parent, container, false);

        return view;
    }
}
