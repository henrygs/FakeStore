package com.henry.fakestore.modules.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.henry.fakestore.R

class GlideExtension {
    fun setImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.logo_fakestoreapi)
            .fitCenter()
            .transition(
                DrawableTransitionOptions
                .withCrossFade())
            .into(view)
    }
}