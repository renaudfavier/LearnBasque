package com.renaudfavier.learnbasque.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val learnBasqueDispatcher: LearnBasqueDispatchers)

enum class LearnBasqueDispatchers {
    IO, Default
}
