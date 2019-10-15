package com.abhrp.daily.presentation.mapper

interface ViewItemMapper<in D, out V> {
    fun mapToView(d: D): V
}