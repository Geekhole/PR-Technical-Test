package uk.geekhole.popreach.ui.global

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter


@BindingAdapter("android:src")
fun setImageUri(view: ImageView, @DrawableRes resourceId: Int?) {
    if (resourceId != null && resourceId != -1) {
        view.setImageResource(resourceId)
    } else {
        view.setImageResource(-1)
    }
}