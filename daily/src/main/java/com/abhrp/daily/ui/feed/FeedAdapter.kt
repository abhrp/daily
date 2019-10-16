package com.abhrp.daily.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhrp.daily.R
import com.abhrp.daily.core.util.PixelHelper
import com.abhrp.daily.model.feed.FeedUIItem
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.ColorFilterTransformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_feed_item.*
import javax.inject.Inject

class FeedAdapter @Inject constructor(): RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    @Inject
    lateinit var pixelHelper: PixelHelper

    var feedItems = mutableListOf<FeedUIItem>()
    var feedItemClickListener: FeedItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_feed_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = feedItems.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedUIItem = feedItems[position]
        holder.bind(feedUIItem)
    }

    fun addFeedItems(newFeedItems: List<FeedUIItem>) {
        feedItems.addAll(newFeedItems)
        notifyDataSetChanged()
    }

    fun refreshFeedItems() {
        feedItems.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

        /**
         * Function to bind the data to the view elements
         * @param feedItem FeedItem model to bind to the ui
         */
        fun bind(feedItem: FeedUIItem) {

            addImageToFeedItem(feedItem.thumbnail)

            thumbnail.contentDescription = feedItem.headline
            sectionName.text = feedItem.sectionName
            elapsedTime.text = feedItem.date
            headline.text = feedItem.headline

            feedItemContainer.setOnClickListener {
                feedItemClickListener?.feedItemClicked(feedItem)
            }
        }

        private fun addImageToFeedItem(imageUrl: String) {
            if (imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .transform(RoundedCornersTransformation(pixelHelper.pixelFromDp(8), 0))
                    .transform(ColorFilterTransformation(thumbnail.context.resources.getColor(R.color.colorImage)))
                    .into(thumbnail)
            }
        }
    }
}