/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.CardViewConcept;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Main Activity for the Material Me app, a mock sports news application with poor design choices
 */
public class MainActivity extends AppCompatActivity {

    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<Topic> mTopicData;
    private TopicAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialize the RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);

        //Set the Layout Manager
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //set the gridlayout manager
        int gridColumnCount = getResources().getInteger(R.integer.grid_Column_Count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        //Initialize the ArrayLIst that will contain the data
        mTopicData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new TopicAdapter(this, mTopicData);
        mRecyclerView.setAdapter(mAdapter);

        //Get the data
        initializeData();

        // If there is more than one column, disable swipe to dismiss
        int swipeDirs;
        if (gridColumnCount > 1) {
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        //Helper class for creating swipe to dismiss and drag and drop functionality
       /* ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, ItemTouchHelper.LEFT |
                        temTouchHelper.RIGHT) {*/

        //update itemTouchHelper for gridLayout
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, swipeDirs) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //Get the from and to position
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                //Swap the items and notify the adapter
                Collections.swap(mTopicData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //allows to remove the card view when it is swiped
                mTopicData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());


            }
        });

        //attaches helper to RecycleView
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Method for initializing the sports data from resources.
     */
    private void initializeData() {
        //Get the resources from the XML file
        String[] topicList = getResources().getStringArray(R.array.topic_titles);
        String[] topicInfo = getResources().getStringArray(R.array.topic_info);
        TypedArray imageRes = getResources().obtainTypedArray(R.array.Card_Images);

        //Clear the existing data (to avoid duplication)
        mTopicData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (int i = 0; i < topicList.length; i++) {
            mTopicData.add(new Topic(topicList[i], topicInfo[i], imageRes.getResourceId(i, 0)));
        }

        //make sure the typed array gets recycled
        imageRes.recycle();

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }

    public void reset(View view) {
        initializeData();
    }

}
