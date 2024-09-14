package com.arakel.superherocrush.uiltel

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeListener(context: Context?) : View.OnTouchListener
{
    var gestureDelector: GestureDetector

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return gestureDelector.onTouchEvent(motionEvent)
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener(){

        val SWIPE_THRESOLD = 100
        val SWIPE_VELOCITY_THRESOLD = 100
        override fun onDown(event: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent, p2: Float, p3: Float): Boolean {
            var result = false

            val yDiff = e2.y - e1!!.y
            val xDiff = e2.x - e1.x
            // it means that we are either going
            // to left or right direction &
            // Top to bottom direction

            if (Math.abs(xDiff) > Math.abs(yDiff)){
                if (Math.abs(xDiff) > SWIPE_THRESOLD
                    && Math.abs(p2) > SWIPE_VELOCITY_THRESOLD) {
                    if (xDiff > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLift()
                    }
                    result = true
                }
            }
            else if (Math.abs(yDiff) > SWIPE_THRESOLD
                && Math.abs(p3) > SWIPE_VELOCITY_THRESOLD){
                if (yDiff > 0){
                    onSwipeBottom()
                }
                else{
                    onSwipeTop()
                }
                result = true
            }
            return result
        }
    }

    open fun onSwipeBottom() {

    }

    open fun onSwipeTop() {

    }

    open fun onSwipeLift() {

    }

    open fun onSwipeRight() {

    }

    init{
        gestureDelector = GestureDetector(context,GestureListener())
    }

}