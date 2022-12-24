package com.assignment.assignmentlloyds.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.usecase.IHouseListUseCase
import com.assignment.assignmentlloyds.presentation.viewmodel.viewstate.ViewState
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

class HouseListListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    lateinit var houseListViewModel: HouseListViewModel

    @RelaxedMockK
    var useCase: IHouseListUseCase = mockk()

    @RelaxedMockK
    private lateinit var mockException: Exception

    private  var mockHouseList: List<HouseListInfo> = spyk()

    private val fakeSuccessFlow = flow {
        emit(BaseModelInfo.OnSuccess(mockHouseList))
    }


    private  val flowCollector: FlowCollector<ViewState<List<HouseListInfo>>> = spyk()



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
        houseListViewModel = HouseListViewModel(useCase)
    }

    @Test
    fun `get house name list from API Success Case`() {
        runBlockingTest {
            coEvery { useCase() } returns fakeSuccessFlow
            launch {
                houseListViewModel.getHouses().collect(flowCollector)
            }
            houseListViewModel.fetchHouseNames()

            verifyOrder {
                runBlockingTest {
                    flowCollector.emit(ViewState.Loading(true))
                    flowCollector.emit(ViewState.Success(mockHouseList))
                    flowCollector.emit(ViewState.Loading(false))
                }
            }
        }

    }

    @Test
    fun `get house name list from API Failure Case`() {
        runBlockingTest {
            coEvery { useCase() } returns fakeFailureFlow
            launch {
                houseListViewModel.getHouses().collect(flowCollector)
            }
            houseListViewModel.fetchHouseNames()

            verifyOrder {
                runBlockingTest {
                    flowCollector.emit(ViewState.Loading(true))
                    flowCollector.emit(ViewState.Success(mockHouseList))
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