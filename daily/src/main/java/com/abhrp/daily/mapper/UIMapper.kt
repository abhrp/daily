package com.abhrp.daily.mapper

interface UIMapper<in P, out V> {
    fun mapToUIView(p: P): V
}