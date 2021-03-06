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

/**
 * Adapter for the feed on the first page. Has two viewholders, one for content, another to show a loading state in the footer.
 * Both ViewHolders extend BaseViewHolder
 */
class FeedAdapter @Inject constructor() : RecyclerView.Adapter<FeedAdapter.BaseViewHolder>() {


    companion object {
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_LOADING = 2
    }

    @Inject
    lateinit var pixelHelper: PixelHelper

    var feedItems = mutableListOf<FeedUIItem>()
    var feedItemClickListener: FeedItemClickListener? = null
    var isLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType) {
            VIEW_TYPE_NORMAL -> {
                 feedItemViewHolder(parent)
            }
            VIEW_TYPE_LOADING -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.layout_feed_progressbar, parent, false)
                 ProgressViewHolder(itemView)
            }
            else -> {
                 feedItemViewHolder(parent)
            }
        }
    }

    private fun feedItemViewHolder(parent: ViewGroup): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_feed_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = feedItems.count()

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val feedUIItem = feedItems[position]
            holder.bind(feedUIItem)
        }
    }

    fun addFeedItems(newFeedItems: List<FeedUIItem>) {
        doneLoadingItems()
        feedItems.addAll(newFeedItems)
        notifyDataSetChanged()
    }

    fun refreshFeedItems() {
        feedItems.clear()
        notifyDataSetChanged()
    }

    fun isLoadingItems() {
        isLoading = true
        feedItems.add(FeedUIItem("", "", "", "", ""))
        notifyItemInserted(feedItems.count() - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) {
            if (position == feedItems.count() - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    fun doneLoadingItems() {
        if (isLoading) {
            isLoading = false
            val position = feedItems.count() - 1
            val feedUIItem = feedItems[position]
            if (feedUIItem.id.isEmpty()) {
                feedItems.remove(feedUIItem)
                notifyItemRemoved(position)
            }
        }
    }

    open inner class BaseViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer

    inner class ViewHolder(override val containerView: View) : BaseViewHolder(containerView) {

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
                @Suppress("DEPRECATION")
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

    inner class ProgressViewHolder(override val containerView: View): BaseViewHolder(containerView)
}