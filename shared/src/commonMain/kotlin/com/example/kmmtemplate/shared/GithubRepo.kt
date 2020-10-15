package com.example.kmmtemplate.shared

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepo(
  @SerialName("name") val name: String
)

sealed class NetworkResponse<out T> {
  data class Success<T>(val value: T) : NetworkResponse<T>()
  data class Error(val exception: Throwable) : NetworkResponse<Nothing>()
}

class GithubApi(
  private val httpClient: HttpClient
) {
  private val apiEndpoint = "https://api.github.com"

  suspend fun getRepos(user: String): NetworkResponse<List<GithubRepo>> =
    get("$apiEndpoint/users/$user/repos")

  private suspend inline fun <reified T> get(url: String): NetworkResponse<T> =
    runCatching {
      httpClient.get<T> {
        url(url)
        accept(ContentType.Application.Json)
      }
    }.fold(
      onSuccess = { NetworkResponse.Success(it) },
      onFailure = { NetworkResponse.Error(it) },
    )
}
