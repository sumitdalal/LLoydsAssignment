package com.assignment.assignmentlloyds.presentation.viewmodel.viewstate


sealed class ViewState<out T : Any> {

    /**
     * State of UI where a loader should be showing to represents a loading UX to the user
     * @param isLoading will be true when the loading UX needs to display, false when not
     */
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()

    /**
     * State of UI where the operation requested has been completed successfully and the result
     * of type [T] has been provided to UI
     * @param output result object of [T] type representing the successful operation
     */
    data class Success<out T : Any>(val output: T) : ViewState<T>()

    /**
     * Represents the UI state where the operation requested by the UI has failed to complete
     * either due to a IO issue or a service exception and the same is conveyed back to the UI
     * to be shown the user
     * @param throwable [Throwable] instance containing the root cause of the failure in a [String]
     */
    data class Failure(val throwable: Throwable) : ViewState<Nothing>()

}