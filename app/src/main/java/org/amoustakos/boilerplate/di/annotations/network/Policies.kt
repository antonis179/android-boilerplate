package org.amoustakos.boilerplate.di.annotations.network

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class IDefaultPrePolicyApplier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class IDefaultPostPolicyApplier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class IDefaultDecoratorPolicyApplier