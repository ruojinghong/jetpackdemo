package com.sxy.jetpackdemo.app.ui.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sxy.jetpackdemo.R
import com.sxy.jetpackdemo.app.data.model.bean.AriticleResponse
import com.sxy.jetpackdemo.app.ext.setAdapterAnimation
import com.sxy.jetpackdemo.app.util.SettingUtil
import com.sxy.jetpackdemo.app.weight.customview.CollectView
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author: sxy
 * @date: 2020/12/21
 * @description:
 */
class AriticleAdapter(data:MutableList<AriticleResponse>?) :
        BaseDelegateMultiAdapter<AriticleResponse,BaseViewHolder>(data){
        private val Ariticle = 1//文章类型
        private val Project = 2//项目类型
        private var showTag = false//是否显示标签
        private var collectAction : (item : AriticleResponse,v: CollectView,position : Int) -> Unit =
                {_: AriticleResponse,_:CollectView,_:Int ->}

        constructor(data:MutableList<AriticleResponse>?,showTag: Boolean ): this(data){
                this.showTag = showTag
        }
        init {
               setAdapterAnimation(SettingUtil.getListMode())
                // 第一步，设置代理
                setMultiTypeDelegate(object : BaseMultiTypeDelegate<AriticleResponse>(){
                        override fun getItemType(data: List<AriticleResponse>, position: Int) =
                                //根据是否有图片 判断为文章还是项目，好像有点low的感觉。。。我看实体类好像没有相关的字段，就用了这个，也有可能是我没发现
                                if(TextUtils.isEmpty(data[position].envelopePic)) Ariticle else Project
                })
                // 第二步，绑定 item 类型
                getMultiTypeDelegate()?.let {
                        it.addItemType(Ariticle, R.layout.item_ariticle)
                        it.addItemType(Project, R.layout.item_project)
                }
        }


        override fun convert(holder: BaseViewHolder, item: AriticleResponse) {
              when(holder.itemViewType){
                      Ariticle -> {
                              //文章
                              item.run {
                                      holder.setText(R.id.item_home_author ,if (author.isNotEmpty()) author else shareUser)
                                      holder.setText(R.id.item_home_content, title.toHtml())
                                      holder.setText(R.id.item_home_type2, "$superChapterName·$chapterName".toHtml())
                                      holder.setText(R.id.item_home_date, niceDate)
                                      holder.getView<CollectView>(R.id.item_home_collect).isChecked = collect
                                      if (showTag) {
                                              //展示标签
                                              holder.setGone(R.id.item_home_new, !fresh)
                                              holder.setGone(R.id.item_home_top, type != 1)
                                              if (tags.isNotEmpty()) {
                                                      holder.setGone(R.id.item_home_type1, false)
                                                      holder.setText(R.id.item_home_type1, tags[0].name)
                                              } else {
                                                      holder.setGone(R.id.item_home_type1, true)
                                              }
                                      } else {
                                              //隐藏所有标签
                                              holder.setGone(R.id.item_home_top, true)
                                              holder.setGone(R.id.item_home_type1, true)
                                              holder.setGone(R.id.item_home_new, true)
                                      }


                                      holder.getView<CollectView>(R.id.item_home_collect).setOnCollectViewClickListener(
                                              object : CollectView.OnCollectViewClickListener{
                                                      override fun onClick(v: CollectView) {
                                                              collectAction.invoke(item, v, holder.adapterPosition)
                                                      }

                                                }
                                      )
                                      


                      }
                              
                              
              }
                      Project -> {
                              //项目布局的赋值
                              item.run {
                                      holder.setText(
                                              R.id.item_project_author,
                                              if (author.isNotEmpty()) author else shareUser
                                      )
                                      holder.setText(R.id.item_project_title, title.toHtml())
                                      holder.setText(R.id.item_project_content, desc.toHtml())
                                      holder.setText(
                                              R.id.item_project_type,
                                              "$superChapterName·$chapterName".toHtml()
                                      )
                                      holder.setText(R.id.item_project_date, niceDate)
                                      if (showTag) {
                                              //展示标签
                                              holder.setGone(R.id.item_project_new, !fresh)
                                              holder.setGone(R.id.item_project_top, type != 1)
                                              if (tags.isNotEmpty()) {
                                                      holder.setGone(R.id.item_project_type1, false)
                                                      holder.setText(R.id.item_project_type1, tags[0].name)
                                              } else {
                                                      holder.setGone(R.id.item_project_type1, true)
                                              }
                                      } else {
                                              //隐藏所有标签
                                              holder.setGone(R.id.item_project_top, true)
                                              holder.setGone(R.id.item_project_type1, true)
                                              holder.setGone(R.id.item_project_new, true)
                                      }
                                      holder.getView<CollectView>(R.id.item_project_collect).isChecked = collect
                                      Glide.with(context).load(envelopePic)
                                              .transition(DrawableTransitionOptions.withCrossFade(500))
                                              .into(holder.getView(R.id.item_project_imageview))
                              }
                              holder.getView<CollectView>(R.id.item_project_collect)
                                      .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                                              override fun onClick(v: CollectView) {
                                                      collectAction.invoke(item, v, holder.adapterPosition)
                                              }
                                      })
                      }
              }
                      
        }
        fun setCollectClick(inputCollectAction: (item: AriticleResponse, v: CollectView, position: Int) -> Unit) {
                this.collectAction = inputCollectAction
        }
}