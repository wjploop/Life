package com.wjploop.life.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.ImageEntity
import com.wjploop.life.databinding.FragmentPhotoPreviewBinding
import com.wjploop.life.databinding.FragmentPhotoPreviewXBinding

class PhotoPreviewFragmentX : Fragment(R.layout.fragment_photo_preview_x) {


    lateinit var binding: FragmentPhotoPreviewXBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoPreviewXBinding.bind(view)
        val image = requireArguments().getParcelable<ImageEntity>("image")!!
//        ViewCompat.setTransitionName(binding.ivImage, arguments?.getString(image.id))
        ViewCompat.setTransitionName(binding.ivImage, "123")


        Glide.with(this).load(image.path).into(binding.ivImage)

        binding.ivImage.setOnClickListener {
            findNavController().run {
                navigate(
                    R.id.nav_photo_preview, bundleOf(Pair("image", image)), null, FragmentNavigatorExtras(
                        Pair(
                            binding.ivImage, "1"
                        )
                    )
                )
            }
        }
    }
}