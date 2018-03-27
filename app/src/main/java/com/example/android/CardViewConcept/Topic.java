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
import android.support.annotation.DrawableRes;

/**
 * Data model for each row of the RecyclerView.
 */
class Topic {

    //Member variables representing the title and information about the sport
    static final String TITLE_KEY = "Title";
    static final String IMAGE_KEY = "Image Resource";
    static final String KEY = "meh";
    //without the image keys Detail Activity would have nothing to reference to causing an error

    private final String title;
    private final String info;
    private final int imageRes;

    public Topic(String title, String info, int imageRes) {

        this.title = title;
        this.info = info;
        this.imageRes = imageRes;


    }

    static Intent Starter(Context context, String title, @DrawableRes int getImageRes) {
        Intent intent = new Intent(context, googleSearch2.class);
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        //intent.putExtra("title", title);
        //intent.putExtra("imageResource", getImageRes);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(IMAGE_KEY, getImageRes);

        return intent;
    }

    /**
     * Gets the title of the sport
     *
     * @return The title of the sport.
     */
    String getTitle() {
        return title;
    }

    /**
     * Gets the info about the sport
     *
     * @return The info about the sport.
     */
    String getInfo() {
        return info;
    }

    int getImageRes() {
        return imageRes;
    }


}
