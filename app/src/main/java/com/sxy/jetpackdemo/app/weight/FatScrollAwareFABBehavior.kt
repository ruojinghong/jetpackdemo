package com.sxy.jetpackdemo.app.weight

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * @author: sxy
 * @date: 2020/12/21
 * @description: 自定义behavior fab上划显示 下滑隐藏
 */
class FatScrollAwareFABBehavior : FloatingActionButton.Behavior{

    constructor(context: Context,attributeSet: AttributeSet): super(context,attributeSet)


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )


        if(dyConsumed > 0 && child.visibility != View.VISIBLE){
            child.visibility  = View.INVISIBLE
        }else if (dyConsumed < 0 && child.visibility != View.VISIBLE){
            child.show()
        }
    }

}