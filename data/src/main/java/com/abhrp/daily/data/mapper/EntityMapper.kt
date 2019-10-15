package com.abhrp.daily.data.mapper

interface EntityMapper<E, D> {

    /**
     * Maps an entity(data) model to domain model
     * @param e: Entity(data) model
     * @return D Domain model
     */
    fun mapToDomain(e: E): D

    /**
     * Maps a domain model to entity(data) model
     * @param d: Domain model
     * @return E Entity(data) model
     */
    fun mapToEntity(d: D): E
}