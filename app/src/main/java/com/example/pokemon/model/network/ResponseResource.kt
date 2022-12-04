package com.example.pokemon.model.network

import com.example.pokemon.R

data class ResponseResource<out T>(val status: Status, val data: T? = null, val message: Int = R.string.error_msg, val loadMoreItem:T? = null) {
    companion object {
        fun <T> success(data: T): ResponseResource<T> = ResponseResource(status = Status.SUCCESS, data = data)

        fun <T> error(data: T?, message: Int): ResponseResource<T> =
            ResponseResource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(): ResponseResource<T> = ResponseResource(status = Status.INITIAL_LOADING)

        fun <T> loadingMoreItems(data: T): ResponseResource<T> = ResponseResource(data = data, status = Status.MORE_ITEMS_LOADING)

    }
}
