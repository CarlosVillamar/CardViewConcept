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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    //Member variables
    private ArrayList<Topic> mTopicData;
    //Topic is made into an array list
    private Context mContext;
    private GradientDrawable mGradientDrawable;


    /**
     * Constructor that passes in the sports data and the context
     *
     * @param tData   ArrayList containing the sports data
     * @param context Context of the application
     */
    TopicAdapter(Context context, ArrayList<Topic> tData) {
        this.mTopicData = tData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.fcbarcelona);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }


    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ViewHolder.
     */
    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false), mGradientDrawable);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Get current sport
        Topic currentTopic = mTopicData.get(position);
        //Populate the textviews with data
        holder.bindTo(currentTopic);

    }


    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mTopicData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Member Variables for the ViewHolderClass
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView imageView;
        private Context mContext;
        private Topic mcurrentTopic;
        private GradientDrawable mGradientDrawable;


        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView         The rootview of the list_item.xml layout file
         * @param gradientDrawable
         */
        ViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views and variables from the layout and class above
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            imageView = itemView.findViewById(R.id.TopicImages);
            mContext = context;
            mGradientDrawable = gradientDrawable;


            //sets up the onClickLsitener to itemView
            itemView.setOnClickListener(this);
        }

        void bindTo(Topic currentTopic) {
            //Populate the textviews with data
            mTitleText.setText(currentTopic.getTitle());
            mInfoText.setText(currentTopic.getInfo());

            //get currentTopic
            mcurrentTopic = currentTopic;

            Glide.with(mContext).load(currentTopic.getImageRes()).placeholder(mGradientDrawable).into(imageView);


        }

        @Override
        public void onClick(View v) {
            //add in intent
            Intent detailIntent = Topic.Starter(mContext, mcurrentTopic.getTitle(), mcurrentTopic.getImageRes());
            //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("www.google.com"));
            //start the activity
            mContext.startActivity(detailIntent);
            // mContext.startActivity(intent);

            //Get object for each item clicked using getAdapterPosition
            mcurrentTopic = mTopicData.get(getAdapterPosition());

        }
    }
}
