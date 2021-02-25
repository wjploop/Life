package com.wjploop.life.ui.edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.wjploop.life.R
import com.wjploop.life.databinding.FragmentEditTaskBinding
import kotlinx.coroutines.launch

class EditTaskFragment : Fragment() {


    private lateinit var binding: FragmentEditTaskBinding


    private val viewModel: EditTaskViewModel by navGraphViewModels(R.id.graph_edit_task) {
        EditTaskViewModel.Companion.Factory(arguments?.getLong("taskId") ?: 0L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                }
            }
            change.observe(viewLifecycleOwner) {
                activity?.invalidateOptionsMenu()
            }
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
        if (item.itemId == R.id.action_done) {
                         lifecycleScope.launch {
                viewModel.repo.updateTask(viewModel.editingTask!!)
            }.invokeOnCompletion {
                checkTaskChange()
            }
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}