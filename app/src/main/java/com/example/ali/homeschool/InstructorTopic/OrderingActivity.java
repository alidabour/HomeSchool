package com.example.ali.homeschool.InstructorTopic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Button;

import com.example.ali.homeschool.Constants;
import com.example.ali.homeschool.InstructorTopic.helper.DoneOrderInterface;
import com.example.ali.homeschool.InstructorTopic.helper.OnStartDragListener;
import com.example.ali.homeschool.InstructorTopic.helper.SimpleItemTouchHelperCallback;
import com.example.ali.homeschool.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderingActivity extends AppCompatActivity implements OnStartDragListener {
    RecyclerView recyclerView ;
    private ItemTouchHelper mItemTouchHelper;
    Button doneBtn;
    ArrayList<String> layoutsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);
        doneBtn = (Button) findViewById(R.id.done);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        if(intent.hasExtra("Layout")){
            String layouts = intent.getStringExtra("Layout");
            layoutsList= new ArrayList<String>(Arrays.asList(layouts.split(Constants.ARRANGE)));
            Log.v("Test","OrderingActivity :" + layoutsList.toString());
        }
        ArrayList<String> layouts  = new ArrayList<>();
        layouts.add(Constants.mTextView(1,"Test1",-121212,-1223));
        layouts.add(Constants.mTextView(2,"Test2",-121212,-1223));
        layouts.add(Constants.mTextView(3,"Test3",-121212,-1223));
        layouts.add(Constants.mTextView(4,"Test4",-121212,-1223));
        layouts.add(Constants.mTextView(5,"Test5",-121212,-1223));

        RecyclerListAdapter adapter = new RecyclerListAdapter(getApplicationContext(), this,
                layoutsList, this, new DoneOrderInterface() {
            @Override
            public void onReorder(List<String> layouts) {
                String layoutss = "";
                for (String x:layouts){
                    layoutss+=x + Constants.ARRANGE;
                }
                Log.v("Orderings","OnReorder :"+ layoutss);
                Intent intent = new Intent();
                intent.setData(Uri.parse(layoutss));
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
//        adapter.notifyItemChanged(layouts.size() -1);
        recyclerView.setAdapter(adapter);


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
