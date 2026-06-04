package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.dto.BaseResponse
import com.practicum.playlistmaker.data.dto.TracksSearchRequest
import com.practicum.playlistmaker.domain.api.NetworkClient
import retrofit2.HttpException
import java.io.IOException

class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {
    override suspend fun doRequest(dto: Any): BaseResponse {
        return try {
            when(dto) {
                is TracksSearchRequest -> api.searchTracks(
                    query = dto.expression,
                    media = "music",
                    entity = "song"
                )
                else -> BaseResponse().apply {
                    resultCode = 400
                    errorMessage = "Invalid request type: expected TracksSearchRequest"
                }
            }
        } catch (_: IOException) {
            BaseResponse().apply { resultCode = -1 }
        } catch (e: HttpException) {
            BaseResponse().apply { resultCode = e.code() }
        } catch (_: Exception) {
            BaseResponse().apply { resultCode = 500 }
        }
    }
}
