package com.assignment.assignmentlloyds.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.assignmentlloyds.R
import com.assignment.assignmentlloyds.databinding.FragmentHouselistBinding
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.presentation.adapter.HouseListAdapter
import com.assignment.assignmentlloyds.presentation.viewmodel.viewstate.ViewState
import com.assignment.assignmentlloyds.utility.Constant
import com.llyods.assignment.presentation.viewmodel.HouseListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HouseListFragment : Fragment() {

    private var _binding: FragmentHouselistBinding? = null
    private val houseListViewModel:HouseListViewModel by viewModels()
    private lateinit var houseListAdapter:HouseListAdapter;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHouselistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        registerFlow()
        fetchHouseNames()
    }

    private fun initViews() {
        with(binding.rvQuotes) {
            layoutManager = LinearLayoutManager(context)
            houseListAdapter =  HouseListAdapter(::onClickItem)
            adapter =houseListAdapter
        }

    }

    private fun fetchHouseNames() {
        houseListViewModel.fetchHouseNames()
    }

    private fun onClickItem(houseItem: HouseListInfo){
        findNavController().navigate(
            R.id.action_houseListFragment_to_detailFragment,
            Bundle().apply {
                putString(Constant.HOUSE_SLUG,houseItem.slug )
            })
    }

    private fun registerFlow() {
        lifecycleScope.launch {
            houseListViewModel.getHouses.collect { viewState ->
                when (viewState) {
                    is ViewState.Failure -> handleErrorState(viewState.throwable)
                    is ViewState.Loading -> handleLoadingState()
                    is ViewState.Success -> handleSuccessState(viewState.output)
                }
            }
        }
    }

    private fun handleSuccessState(result: List<HouseListInfo>) {
         houseListAdapter?.updateItemList(result)
        _binding?.progressBar?.visibility= View.GONE
    }

    private fun handleLoadingState() {
        _binding?.progressBar?.visibility= View.VISIBLE
    }

    private fun handleErrorState(throwable: Throwable) {
        _binding?.progressBar?.visibility= View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}