package com.abhrp.daily.data.mapper

interface EntityMapper<in E, out D> {

    /**
     * Maps an entity(data) model to domain model
     * @param e: Entity(data) model
     * @return D Domain model
     */
    fun mapToDomain(e: E): D
}