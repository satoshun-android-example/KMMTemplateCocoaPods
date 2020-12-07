package com.example.kmmtemplate.androidApp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kmmtemplate.shared.GithubApi
import com.example.kmmtemplate.shared.Greeting
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

fun greet(): String {
  return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val tv: TextView = findViewById(R.id.text_view)
    tv.text = greet()

    Greeting().greeting()

    val okHttpClient = OkHttpClient.Builder()
      .addInterceptor(SampleInterceptor)
      .build()

    val gitHubApi = GithubApi(
      httpClient = HttpClient(OkHttp) {
        engine {
          preconfigured = okHttpClient
//          addInterceptor(SampleInterceptor)
        }

        install(JsonFeature) {
          serializer = KotlinxSerializer(
            json = Json {
              ignoreUnknownKeys = true
            }
          )
        }
      }
    )
    lifecycleScope.launchWhenStarted {
      val repos = gitHubApi.getRepos("satoshun")
      println(repos)
    }
  }
}
