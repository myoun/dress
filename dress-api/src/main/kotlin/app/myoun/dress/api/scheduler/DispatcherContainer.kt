package app.myoun.dress.api.scheduler

import app.myoun.dress.api.loader.DressLoader
import app.myoun.dress.api.plugin.Plugin
import app.myoun.dress.api.scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface DispatcherContainer {
    companion object : DispatcherContainer by DressLoader.loadImplement(DispatcherContainer::class.java)

    val minecraft: CoroutineContext
}

val Dispatchers.minecraft: CoroutineContext by DispatcherContainer.Companion::minecraft


fun Plugin.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return scope.launch(Dispatchers.minecraft, block=block)
}

val Plugin.scope: CoroutineScope by ::scope