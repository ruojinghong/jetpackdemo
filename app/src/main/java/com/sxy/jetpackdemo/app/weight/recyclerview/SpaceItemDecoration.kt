package com.sxy.jetpackdemo.app.weight.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val leftRight : Int ,private val topBottom : Int ,private val firstNeedTop:Boolean = true):RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
            val linearLayoutManager = parent.layoutManager as LinearLayoutManager?
        //数值方向
        if(linearLayoutManager!!.orientation == LinearLayoutManager.VERTICAL){
                //最后一项需要bottom
            if(parent.getChildAdapterPosition(view) == linearLayoutManager.itemCount -1 ){
                    outRect.bottom = topBottom
            }
            if(!firstNeedTop&&parent.getChildAdapterPosition(view)==0){
                outRect.top = 0
            }else{
                outRect.top = topBottom
            }
            outRect.left = leftRight
            outRect.right  = leftRight
        }else {
            //最后一项需要right
            if(parent.getChildAdapterPosition(view) == linearLayoutManager.itemCount -1 ){
                outRect.right = leftRight
            }
            if(!firstNeedTop&&parent.getChildAdapterPosition(view)==0){
                outRect.left = 0
            }else{
                outRect.left = leftRight
            }
            outRect.top = topBottom
            outRect.bottom = topBottom
        }
    }

}