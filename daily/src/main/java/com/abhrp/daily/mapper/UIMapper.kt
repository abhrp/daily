package com.abhrp.daily.mapper

interface UIMapper<in P, out V> {

    /**
     * Map presentation layer model to ui model
     * @param p Presentation model
     */
    fun mapToUIView(p: P): V
}