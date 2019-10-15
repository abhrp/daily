package com.abhrp.daily.di.annotation

import androidx.lifecycle.ViewModel

import dagger.MapKey
import kotlin.reflect.KClass
import kotlin.annotation.MustBeDocumented
import kotlin.annotation.Retention

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
