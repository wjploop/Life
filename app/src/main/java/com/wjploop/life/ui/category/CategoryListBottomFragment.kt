package com.wjploop.life.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.wjploop.life.R
import com.wjploop.life.data.db.entity.Category
import com.wjploop.life.databinding.FragmentCategoryBinding
import com.wjploop.life.ui.edit.EditTaskViewModel
import kotlinx.coroutines.launch

class CategoryListBottomFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentCategoryBinding

    private lateinit var viewModel: CategoryViewModel


    private val editTaskViewModel: EditTaskViewModel by navGraphViewModels(R.id.graph_edit_task)

    val categories = mutableListOf<Category>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        viewModel.categoryList.observe(viewLifecycleOwner) {
            it.filter { newCategory ->
                categories.firstOrNull { it.id == newCategory.id } == null
            }.map { category ->
                Chip(context).apply {
                    text = category.name
                    id = category.id
                    setOnClickListener {
                        if(editTaskViewModel.task.value?.category != category.name){
                            editTaskViewModel.editingTask?.category = category.name
                            lifecycleScope.launch {
                                editTaskViewModel.repo.updateTask(editTaskViewModel.task.value?.copy(category = category.name)!!)
                            }
                            findNavController().popBackStack()
                        }
                    }
                }.let {
                    binding.chipGroup.addView(it)
                }
            }
        }
        return binding.root

    }
}