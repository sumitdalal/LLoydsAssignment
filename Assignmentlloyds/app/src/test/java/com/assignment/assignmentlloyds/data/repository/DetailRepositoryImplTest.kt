package com.assignment.assignmentlloyds.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.assignmentlloyds.data.mapper.Mapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.data.response.DetailResponse
import com.assignment.assignmentlloyds.data.response.HouseListResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class DetailRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var detailRepositoryImpl: DetailRepositoryImpl


    private var dataService: IApiService = mockk()

    private var mapper: Mapper = mockk()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        detailRepositoryImpl = DetailRepositoryImpl(dataService, mapper)
    }

    @Test
    fun testAPIDetailSuccess() {
        runBlockingTest {
            val response = mockk<Response<List<DetailResponse>>>()
            coEvery { response.isSuccessful } returns true
            val detailResponse = mockk<List<DetailResponse>>()
            coEvery { response.body() } returns detailResponse
            coEvery { mapper.mapDetail(detailResponse) } returns
                    mockk()

            coEvery { dataService.getDetail("slug") } returns response.body()!!
            TestCase.assertEquals(1, detailRepositoryImpl("slug").first())
        }
    }

    @Test
    fun testAPIDetailFail() {
        runBlockingTest {
            val response = mockk<Response<List<DetailResponse>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getDetail("slug") } returns response.body()!!
            TestCase.assertEquals("error", detailRepositoryImpl("slug").first())
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}