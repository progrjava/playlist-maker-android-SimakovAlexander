package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}