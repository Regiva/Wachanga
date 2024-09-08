package com.example.wachanga.feature.main.adapter

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.wachanga.R

class ItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val context = holder.itemView.context
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_in)
        holder.itemView.startAnimation(animation)
        dispatchAddStarting(holder)

        animation.setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    dispatchAddFinished(holder)
                }
                override fun onAnimationRepeat(animation: Animation) {}
            }
        )

        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        val context = holder.itemView.context
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide_out)
        holder.itemView.startAnimation(animation)
        dispatchRemoveStarting(holder)

        animation.setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    dispatchRemoveFinished(holder)
                    holder.itemView.alpha = 1f
                }
                override fun onAnimationRepeat(animation: Animation) {}
            }
        )

        return true
    }
}
