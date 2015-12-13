package com.whx.testprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by whx on 2015/12/13.
 */
public class ResultActivity extends Activity{

    ExpandableListView expandableListView;
    ExpandableListAdapter adapter;
    ArrayList<String> names;
    ArrayList<ArrayList<String>> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        names = (ArrayList<String>)bundle.getSerializable("name");
        details = (ArrayList<ArrayList<String>>)bundle.getSerializable("detail");

        expandableListView = (ExpandableListView)findViewById(R.id.list);
        adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return names.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return details.get(groupPosition).size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return names.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {

                return details.get(groupPosition).get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                View view = View.inflate(ResultActivity.this,R.layout.groupview,null);

                ImageView image = (ImageView)view.findViewById(R.id.ren_icon);
                image.setImageResource(R.mipmap.touxiang);

                TextView textView = (TextView)view.findViewById(R.id.group_name);
                textView.setText(names.get(groupPosition));

                return view;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                View view = View.inflate(ResultActivity.this,R.layout.child_view,null);

                String text = getChild(groupPosition,childPosition).toString();

                ImageView imageView = (ImageView)view.findViewById(R.id.image);
                if(text.substring(0,1).equals("电话")){
                    imageView.setImageResource(R.mipmap.phone);
                }else{
                    imageView.setImageResource(R.mipmap.email);
                }

                TextView textView = (TextView)view.findViewById(R.id.text);
                textView.setText(text);

                return view;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };
    }
}
