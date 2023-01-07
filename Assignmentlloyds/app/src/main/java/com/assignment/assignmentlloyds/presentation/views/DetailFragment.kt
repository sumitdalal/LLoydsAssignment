package com.assignment.assignmentlloyds.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.assignment.assignmentlloyds.databinding.FragmentDetailBinding
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.presentation.viewmodel.viewstate.ViewState
import com.assignment.assignmentlloyds.utility.Constant
import com.llyods.assignment.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerFlow()
        fetchHouseDetail()

    }


    private fun fetchHouseDetail() {
        arguments?.getString(Constant.HOUSE_SLUG)?.let { detailViewModel.fetchHouseDetail(it) }
    }


    private fun registerFlow() {
        lifecycleScope.launch {
            detailViewModel.getDetail.collect { viewState ->
                when (viewState) {
                    is ViewState.Failure -> handleErrorState(viewState.throwable)
                    is ViewState.Loading -> handleLoadingState()
                    is ViewState.Success -> handleSuccessState(viewState.output)
                }
            }
        }
    }

    private fun handleSuccessState(result: DetailInfo) {
        with(_binding){
            this?.tvHouse?.text = result.houseName
            this?.tvSlug?.text = result.slug
            this?.progressBar?.visibility = View.GONE
        }

    }

    private fun handleLoadingState() {
        _binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun handleErrorState(throwable: Throwable) {
        _binding?.progressBar?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}