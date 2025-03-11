package com.henry.fakestore.modules.detail.ui

import android.annotation.SuppressLint
import android.os.Build
import com.henry.fakestore.data.local.model.Product
import com.henry.fakestore.databinding.FragmentDetailBinding
import com.henry.fakestore.modules.contants.Constants
import com.henry.fakestore.modules.baseFragment.BaseFragment
import com.henry.fakestore.modules.utils.GlideExtension
import com.henry.fakestore.modules.utils.isVisibility

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private lateinit var product: Product

    @SuppressLint("NewApi")
    override fun setupViews()  {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            product = arguments?.getSerializable(Constants.PRODUCT, Product::class.java) as Product
        } else {
            product = arguments?.getSerializable(Constants.PRODUCT) as Product
        }

        with(binding) {
            product?.apply {
                nameProduct.text = title
                descriptionProductTextView.text = description
                priceProductTextView.text = price
                GlideExtension().setImage(imageProductImageView, image ?: "")
                favoriteImageButton.isVisibility(isFavorite)
                ratingConstraintLayout.ratingTextView.text = rating
                ratingConstraintLayout.reviewsTextView.text = count
            }
        }
    }
}