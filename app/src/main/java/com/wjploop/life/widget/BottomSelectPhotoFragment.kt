package com.wjploop.life.widget

import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.ImageEntity
import com.wjploop.life.databinding.FragBottomSelectPhontoBinding
import com.wjploop.life.ui.edit.images_key
import java.io.File

class BottomSelectPhotoFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    lateinit var binding: FragBottomSelectPhontoBinding

    val photoLiveData = MutableLiveData<List<ImageEntity>>()

    val photoPickingAdapter = PhotoPickingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragBottomSelectPhontoBinding.inflate(inflater, container, false)
        binding.rvPhotos.apply {
            layoutManager = GridLayoutManager(context, 3)
            photoPickingAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
                val imageEntity = photoPickingAdapter.data[position]

//                findNavController().run {
//                    previousBackStackEntry?.savedStateHandle?.set(images_key, imageEntity)
//                    navigate(0, null, navOptions {
//                        launchSingleTop = true
//                        popUpTo = R.id.nav_edit_task
//                    }, FragmentNavigatorExtras(Pair(view, imageEntity.id)))
//                }
                findNavController().run {
                    navigate(
                        R.id.nav_photo_preview_x, bundleOf(Pair("image", imageEntity)), null,
                        null,
//                        FragmentNavigatorExtras(
//                            Pair(
//                                view, imageEntity.id
//                            )
//                        )
                    )
                }
            })
            adapter = photoPickingAdapter
        }
        photoLiveData.observe(viewLifecycleOwner) {
            photoPickingAdapter.setDiffNewData(it.toMutableList())
        }
        return binding.root
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(BaseColumns._ID, MediaStore.Images.Media.DATA),
            null, null, null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {

        val newdData = mutableListOf<ImageEntity>()
        data.moveToFirst()
        while (!data.isAfterLast) {
            val imageEntity =
                ImageEntity(data.getString(data.getColumnIndex(BaseColumns._ID)), data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA)))
            newdData.add(imageEntity)
            data.moveToNext()
        }
        photoLiveData.postValue(newdData)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    class PhotoPickingAdapter : BaseQuickAdapter<ImageEntity, BaseViewHolder>(R.layout.item_photo_picking, mutableListOf()) {


        override fun convert(holder: BaseViewHolder, item: ImageEntity) {
//            Log.d("wolf", item.path)
            val imageView = holder.getView<ImageView>(R.id.iv_image)
//            ViewCompat.setTransitionName(imageView, item.id)
            Glide.with(context).load(File(item.path))
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(imageView)
        }

        init {
            setDiffCallback(diffCallback = object : DiffUtil.ItemCallback<ImageEntity>() {
                override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
                    return oldItem.path == newItem.path
                }
            })

        }
    }


}