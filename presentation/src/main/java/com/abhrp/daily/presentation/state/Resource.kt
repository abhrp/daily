package com.abhrp.daily.presentation.state

class Resource<out T> constructor(val status: ResourceState, val data: T?, val error: String?)