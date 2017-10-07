package com.mysticwater.myfilms.util

import android.widget.ImageView
import com.squareup.picasso.Picasso


fun ImageView.loadUrl(url: String) {
    Picasso.with(this.context).load(url).into(this)
}
