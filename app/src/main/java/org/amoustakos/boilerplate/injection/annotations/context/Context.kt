package org.amoustakos.boilerplate.injection.annotations.context

import javax.inject.Qualifier


@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext