package ru.nemelianov.githubrepositories.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.nemelianov.githubrepositories.R

object BindingUtils {
    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageViewFromUrl(view: AppCompatImageView, url: String) {
        val resources = view.resources
        Glide.with(view)
            .load(url)
            .override(
                resources.getDimensionPixelOffset(R.dimen.avatar_width),
                resources.getDimensionPixelOffset(R.dimen.avatar_width)
            )
            .transform(
                CenterCrop(),
                RoundedCorners(resources.getDimensionPixelOffset(R.dimen.avatar_radius))
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}