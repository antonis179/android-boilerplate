package org.amoustakos.boilerplate.injection.annotations.network

import javax.inject.Qualifier


@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class DefaultOkHttpClient

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class DefaultOkHttpOptions