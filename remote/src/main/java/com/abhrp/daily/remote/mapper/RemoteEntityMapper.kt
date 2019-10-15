package com.abhrp.daily.remote.mapper

interface RemoteEntityMapper<in M, out E> {
    fun mapToEntity(m: M): E
}