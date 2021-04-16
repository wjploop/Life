package com.wjploop.life.ui.edit

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.collection.ArraySet
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.ImageEntity
import com.wjploop.life.data.db.entity.Task
import com.wjploop.life.databinding.FragmentEditTaskBinding
import com.wjploop.life.ui.navigation.PickPhotoNavOption
import kotlinx.coroutines.launch
import java.io.File

const val images_key = "images"
class EditTaskFragment : Fragment() {


    private lateinit var binding: FragmentEditTaskBinding

    val photoPreviewAdapter = PhotoPreviewAdapter()

    private val viewModel: EditTaskViewModel by navGraphViewModels(R.id.graph_edit_task) {
        EditTaskViewModel.Companion.Factory(arguments?.getLong("taskId") ?: 0L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)

        viewModel.run {
            task.observe(viewLifecycleOwner) {
                Log.d("wolf", "observer task $it")
                binding.run {
                    tvTitle.setText(it.title)
                    tvCategory.text = it.category
                    tvContent.setText(it.content)
//                    photoPreviewAdapter.setList(it.imageUrls)

                    photoPreviewAdapter.setDiffNewData(it.imageUrls.toMutableList().also {
                        Log.d("wolf","current count ${it.count()}")
                    })
                }
            }
            change.observe(viewLifecycleOwner) {
                activity?.invalidateOptionsMenu()
            }

        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ImageEntity>(images_key)?.observe(viewLifecycleOwner){image->
            updateTask(viewModel.task.value?.apply {
                imageUrls.add(0, image)
            })
//            photoPreviewAdapter.addData(0,it)
//            Log.d("wolf","add ${it.path}")
//            Log.d("wolf", "task ${viewModel.editingTask}")
//            checkTaskChange()
        }

        binding.run {

            tvTitle.addTextChangedListener(onTextChanged = { text, start, before, count ->
                viewModel.editingTask?.title = text?.trim().toString()
                checkTaskChange()
            })
            tvContent.addTextChangedListener(onTextChanged = { text, start, before, count ->
                viewModel.editingTask?.content = text?.trim().toString()
                checkTaskChange()
            })

            tvCategory.setOnClickListener {
                findNavController().navigate(R.id.nav_select_category)
            }
            layoutImagePick.setOnClickListener {
                findNavController().navigate(R.id.nav_select_photo, null,  navOptions { launchSingleTop=true
                                                                                      }, FragmentNavigatorExtras(
                ))
            }

            rvImage.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
                adapter = photoPreviewAdapter
            }
        }

        return binding.root
    }


    fun checkTaskChange() {
        val hasChange = viewModel.task.value != viewModel.editingTask
        viewModel.change.postValue(hasChange)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_task, menu)
        menu.findItem(R.id.action_done).isVisible = viewModel.change.value ?: false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId ==  R.id.action_done) {
                         lifecycleScope.launch {
                viewModel.repo.updateTask(viewModel.editingTask!!)
            }.invokeOnCompletion {
                checkTaskChange()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateTask(task: Task?) {
        if (task == null) {
            return
        }
        lifecycleScope.launch {
            viewModel.repo.updateTask(task)
        }
    }

    class PhotoPreviewAdapter : BaseQuickAdapter<ImageEntity, BaseViewHolder>(R.layout.item_photo_preview) {
        override fun convert(holder: BaseViewHolder, item: ImageEntity) {
            val imageView = holder.getView<ImageView>(R.id.iv_image)

            ViewCompat.setTransitionName(imageView,item.id)

            Glide.with(context).load(File(item.path))
                    // 等加载好后再开始共享元素动画？
                    // 还不够，等RecyclerView所有的元素放置好后再执行
//                .listener(object : RequestListener<Drawable?> {
//                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
//                        return false
//                    }
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: Target<Drawable?>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        (context as Fragment).startPostponedEnterTransition()
//                        return false
//                    }
//                })
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