package com.amineaytac.biblictora.ui.discover

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amineaytac.biblictora.R
import com.amineaytac.biblictora.core.data.model.Book
import com.amineaytac.biblictora.databinding.ItemDiscoverBookBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class DiscoverBookAdapter(

    private val books: List<Book>,
    private val resources: Resources,
    private val onBookClickListener: (position: Int) -> Unit

) : RecyclerView.Adapter<DiscoverBookAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDiscoverBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book, position: Int) = with(binding) {

            tvName.text = item.title

            val imageTarget = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    pictureView.setBitmap(bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    pictureView.setBitmap(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_biblictora
                        )
                    )
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            }

            Picasso.get().load(item.image).into(imageTarget)

            bookItemLayout.setOnClickListener {
                onBookClickListener.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDiscoverBookBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = books[position]
        item.let {
            holder.bind(item, position)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}