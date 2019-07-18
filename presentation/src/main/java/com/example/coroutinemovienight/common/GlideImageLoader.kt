package com.example.coroutinemovienight.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.coroutinemovienight.di.ActivityContext
import javax.inject.Inject

class GlideImageLoader @Inject constructor(
    @ActivityContext private val context: Context
) : ImageLoader {

    private val glide = Glide.with(context)

    override fun load(url: String, imageView: ImageView, callback: (Boolean) -> Unit) {
        glide.load(url)
            .listener(GlideListener(callback))
            .into(imageView)
    }

    override fun load(url: String, imageView: ImageView, fadeEffect: Boolean) {
        if (fadeEffect) {
            glide.load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        } else {
            glide.load(url).into(imageView)
        }
    }

    private class GlideListener(val delegate: (Boolean) -> Unit): RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            delegate(false)
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            delegate(true)
            return false
        }
    }
}