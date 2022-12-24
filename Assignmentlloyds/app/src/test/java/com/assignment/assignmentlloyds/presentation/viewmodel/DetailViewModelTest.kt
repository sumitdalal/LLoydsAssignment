package com.assignment.assignmentlloyds.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.usecase.IDetailUseCase
import com.assignment.assignmentlloyds.domain.usecase.IHouseListUseCase
import com.assignment.assignmentlloyds.presentation.viewmodel.viewstate.ViewState
import com.llyods.assignment.presentation.viewmodel.DetailViewModel
import com.llyods.assignment.presentation.viewmodel.HouseListViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

class DetailViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    lateinit var detailViewModel: DetailViewModel

    @RelaxedMockK
    var useCase: IDetailUseCase = mockk()

    @RelaxedMockK
    private lateinit var mockException: Exception

    private  var mockDeatailInfo: DetailInfo = spyk()

    private val fakeSuccessFlow = flow {
        emit(BaseModelInfo.OnSuccess(mockDeatailInfo))
    }


    private  val flowCollector: FlowCollector<ViewState<DetailInfo>> = spyk()



    private val fakeFailureFlow = flow {
        emit(BaseModelInfo.OnFailure(mockException))
    }

    @RelaxedMockK
    private lateinit var viewStateSuccess: ViewState.Success<List<HouseListInfo>>

    @RelaxedMockK
    private lateinit var viewStateLoading: ViewState.Loading

    @RelaxedMockK

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        detailViewModel = DetailViewModel(useCase)
    }

    @Test
    fun `get Detail of house from API with Success Case`() {
        runBlockingTest {
            coEvery { useCase("slug") } returns fakeSuccessFlow
            launch {
                detailViewModel.getDetail().collect(flowCollector)
            }
            detailViewModel.fetchHouseDetail("slug")

            verifyOrder {
                runBlockingTest {
                    flowCollector.emit(ViewState.Loading(true))
                    flowCollector.emit(ViewState.Success(mockDeatailInfo))
                    flowCollector.emit(ViewState.Loading(false))
                }
            }
        }

    }

    @Test
    fun `get detail of house from API with Failure Case`() {
        runBlockingTest {
            coEvery { useCase("slug") } returns fakeFailureFlow
            launch {
                detailViewModel.getDetail().collect(flowCollector)
            }
            detailViewModel.getDetail()

            verifyOrder {
                runBlockingTest {
                    flowCollector.emit(ViewState.Loading(true))
                    flowCollector.emit(ViewState.Success(mockDeatailInfo))
                    flowCollector.emit(ViewState.Loading(false))
                }
            }
        }

    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}