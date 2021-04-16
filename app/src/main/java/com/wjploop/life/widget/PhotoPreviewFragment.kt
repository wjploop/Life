package com.wjploop.life.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.ImageEntity
import com.wjploop.life.databinding.FragmentPhotoPreviewBinding
import com.wjploop.life.databinding.FragmentPhotoPreviewXBinding

class PhotoPreviewFragment : Fragment(R.layout.fragment_photo_preview) {


    lateinit var binding: FragmentPhotoPreviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoPreviewBinding.bind(view)
        val image = requireArguments().getParcelable<ImageEntity>("image")!!
        ViewCompat.setTransitionName(binding.ivImage, "1")

        Glide.with(this).load(image.path).into(binding.ivImage)
    }
}