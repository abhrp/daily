package com.abhrp.daily.cache.mapper

interface CacheEntityMapper<E, C> {
    fun mapToCache(e: E): C
    fun mapToEntity(c: C): E
}