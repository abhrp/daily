package com.abhrp.daily.cache.mapper

interface CacheEntityMapper<E, C> {
    
    /**
     * Maps models from type entity to cache
     * @param e: Model of type entity (data layer)
     * @return C: Model of type cache
     */
    fun mapToCache(e: E): C

    /**
     * Maps models from cache to entity, i.e. from cache layer models to data layer models
     * @param c Model of type cache
     * @return E Model of type entity
     */
    fun mapToEntity(c: C): E
}