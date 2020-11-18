package com.example.kmmtemplate.shared


class Greeting {
    fun greeting(): String {
        return "HelloD, ${Platform().platform}!"
    }
}
