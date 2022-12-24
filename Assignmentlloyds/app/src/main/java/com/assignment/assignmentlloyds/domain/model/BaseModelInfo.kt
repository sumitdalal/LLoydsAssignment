package com.assignment.assignmentlloyds.domain.model

sealed class BaseModelInfo<out T : Any> {
    data class OnSuccess<out T : Any>(val data: T) : BaseModelInfo<T>()
    data class OnFailure(val throwable: Throwable) : BaseModelInfo<Nothing>()
}