package com.assignment.assignmentlloyds.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.assignmentlloyds.domain.mapper.ListMapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.data.response.DetailResponse
import com.assignment.assignmentlloyds.domain.mapper.DetailMapper
import com.assignment.assignmentlloyds.domain.repository.IRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class DetailRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var iRepository: RepositoryImpl


    private var dataService: IApiService = mockk()

    private var listMapper: ListMapper = mockk()
    private var detailMapper: DetailMapper = mockk()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        iRepository = RepositoryImpl(dataService, listMapper,detailMapper)
    }

    @Test
    fun testAPIDetailSuccess() {
        runTest {
            val response = mockk<Response<List<DetailResponse>>>()
            coEvery { response.isSuccessful } returns true
            val detailResponse = mockk<List<DetailResponse>>()
            coEvery { response.body() } returns detailResponse
            coEvery { detailMapper.map(detailResponse) } returns
                    mockk()

            coEvery { dataService.getDetail("slug") } returns response.body()!!
            TestCase.assertEquals(1, iRepository.getHouseDetail("slug").first())
        }
    }

    @Test
    fun testAPIDetailFail() {
        runTest {
            val response = mockk<Response<List<DetailResponse>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getDetail("slug") } returns response.body()!!
            TestCase.assertEquals("error", iRepository.getHouseDetail("slug").first())
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}