package com.almanara.homeschool.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.almanara.ali.homeschool.R;

/**
 * Created by hossam on 05/06/17.
 */

public class InternetConnectionChecker {
    Context context;

    public InternetConnectionChecker(Context context) {
        this.context = context;
    }

    public boolean isInternetOn() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            // connected to wifi
            Toast.makeText(context, context.getString(R.string.connect_from)+activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            return true;

        } else {
            // not connected to the internet
            return false;
        }
    }

}
