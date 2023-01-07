package com.assignment.assignmentlloyds.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.assignmentlloyds.domain.mapper.ListMapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.data.response.HouseListResponse
import com.assignment.assignmentlloyds.domain.mapper.DetailMapper
import com.assignment.assignmentlloyds.domain.repository.IRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class HouseListRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var iRepository:  RepositoryImpl


    private  var dataService: IApiService = mockk()

    private  var listMapper: ListMapper = mockk()
    private  var detailMapper: DetailMapper = mockk()



    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        iRepository = RepositoryImpl(dataService, listMapper,detailMapper)
    }

    @Test
    fun testAPIHouseListSuccess() {
        runTest {
            val response = mockk<Response<List<HouseListResponse>>>()
            coEvery { response.isSuccessful } returns true
            val houseListResponse = mockk<List<HouseListResponse>>()
            coEvery { response.body() } returns houseListResponse
            coEvery { listMapper.map(houseListResponse) } returns listOf(
                mockk()
            )
            coEvery { dataService.getHouseList() } returns response.body()!!
            TestCase.assertEquals(1, iRepository.getHouseList().first())
        }
    }

    @Test
    fun testAPIHouseListFail() {
        runTest {
            val response = mockk<Response<List<HouseListResponse>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getHouseList() } returns response.body()!!
            TestCase.assertEquals("error", iRepository.getHouseList().first())
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}