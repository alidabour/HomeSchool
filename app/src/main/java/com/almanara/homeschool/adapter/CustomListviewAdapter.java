package com.almanara.homeschool.adapter;

/**
 * Created by P on 2/9/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.almanara.ali.homeschool.R;

public class CustomListviewAdapter extends BaseAdapter{
    String [] result;
    Context context;

    private static LayoutInflater inflater=null;
    public CustomListviewAdapter( Context con, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=con;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ProgressBar ProgressBar;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.ProgressBar=(ProgressBar) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.ProgressBar.setProgress(50);
        holder.ProgressBar.setMinimumHeight(20);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}