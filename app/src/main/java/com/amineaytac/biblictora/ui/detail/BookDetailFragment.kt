package com.amineaytac.biblictora.ui.detail

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.amineaytac.biblictora.R
import com.amineaytac.biblictora.databinding.FragmentBookDetailBinding
import com.amineaytac.biblictora.util.gone
import com.amineaytc.biblictora.util.viewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {

    private val binding by viewBinding(FragmentBookDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun createTransparentColor(color: Int): Int {
        val alpha = Color.alpha(color)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        val transparentAlpha = (alpha * 0.5f).toInt()

        return Color.argb(transparentAlpha, red, green, blue)
    }

    private fun bindUI() = with(binding) {
        if (arguments != null) {
            val book = BookDetailFragmentArgs.fromBundle(requireArguments()).book

            if (book.image.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val bitmap = Picasso.get().load(book.image).get()
                        withContext(Dispatchers.Main) {
                            bindBackground(bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            tvAuthor.text = book.authors
            tvLanguages.text = book.languages
            tvTitle.text = book.title
            if (book.bookshelves.isEmpty()) {
                cvBookshelves.gone()
            } else {
                tvBookshelves.text = book.bookshelves.joinToString("\n")
            }

            /* ivWillRead.setOnClickListener {
                 if(it.background == resources.getDrawable(R.drawable.ic_dark_will_read)){
                     it.setBackgroundResource(R.drawable.ic_light_will_read)
                     ivReading.setBackgroundResource(R.drawable.ic_dark_reading)
                     ivHaveRead.setBackgroundResource(R.drawable.ic_dark_have_read)
                 }else{
                     it.setBackgroundResource(R.drawable.ic_dark_will_read)
                     ivReading.setBackgroundResource(R.drawable.ic_light_reading)
                     ivHaveRead.setBackgroundResource(R.drawable.ic_light_have_read)
                 }
             }
             ivReading.setOnClickListener {
                 if(it.background == resources.getDrawable(R.drawable.ic_dark_reading)){
                     it.setBackgroundResource(R.drawable.ic_light_reading)
                     ivWillRead.setBackgroundResource(R.drawable.ic_dark_will_read)
                     ivHaveRead.setBackgroundResource(R.drawable.ic_dark_have_read)
                 }else{
                     it.setBackgroundResource(R.drawable.ic_dark_reading)
                     ivWillRead.setBackgroundResource(R.drawable.ic_light_will_read)
                     ivHaveRead.setBackgroundResource(R.drawable.ic_light_have_read)
                 }
             }
             ivHaveRead.setOnClickListener {
                 if(it.background == resources.getDrawable(R.drawable.ic_dark_have_read)){
                     it.setBackgroundResource(R.drawable.ic_light_have_read)
                     ivReading.setBackgroundResource(R.drawable.ic_dark_reading)
                     ivWillRead.setBackgroundResource(R.drawable.ic_dark_will_read)
                 }else{
                     it.setBackgroundResource(R.drawable.ic_dark_have_read)
                     ivReading.setBackgroundResource(R.drawable.ic_light_reading)
                     ivWillRead.setBackgroundResource(R.drawable.ic_light_will_read)
                 }
             }*/
        }
    }

    private fun bindBackground(bitmap: Bitmap?) = with(binding) {
        if (bitmap != null) {

            ivBookPicture.setImageBitmap(bitmap)
            ivBookBackground.setImageBitmap(bitmap)

            Palette.from(bitmap).generate { palette ->

                val dominantColor = palette?.dominantSwatch?.rgb ?: 0
                if (dominantColor != 0) {

                    val transparentDominantColor = createTransparentColor(dominantColor)
                    val vibrantColor = palette?.vibrantSwatch?.rgb ?: 0

                    llLanguages.setBackgroundColor(transparentDominantColor)
                    llBookshelves.setBackgroundColor(transparentDominantColor)
                    llReadInfo.setBackgroundColor(transparentDominantColor)

                    if (vibrantColor != 0) {
                        if (calculateColorDistanceToWhite(vibrantColor) < 200) {
                            tvTitle.setTextColor(requireContext().getColor(R.color.black))
                            btnReadingNow.setTextColor(requireContext().getColor(R.color.black))
                        }
                        cvBgBookPicture.setCardBackgroundColor(vibrantColor)
                        btnReadingNow.backgroundTintList = ColorStateList.valueOf(vibrantColor)
                    } else {
                        if (calculateColorDistanceToWhite(dominantColor) < 200) {
                            tvTitle.setTextColor(requireContext().getColor(R.color.black))
                            btnReadingNow.setTextColor(requireContext().getColor(R.color.black))
                        }
                        cvBgBookPicture.setCardBackgroundColor(dominantColor)
                        btnReadingNow.backgroundTintList = ColorStateList.valueOf(dominantColor)
                    }
                }
            }
        }
    }

    private fun calculateColorDistanceToWhite(colorInt: Int): Double {
        val colorHex = String.format("#%06X", 0xFFFFFF and colorInt)
        val color = Color.parseColor(colorHex)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        val whiteRed = 255
        val whiteGreen = 255
        val whiteBlue = 255

        return sqrt(
            ((whiteRed - red) * (whiteRed - red) + (whiteGreen - green) * (whiteGreen - green) + (whiteBlue - blue) * (whiteBlue - blue)).toDouble()
        )
    }
}