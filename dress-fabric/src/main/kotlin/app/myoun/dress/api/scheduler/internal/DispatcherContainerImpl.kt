package app.myoun.dress.api.scheduler.internal

import app.myoun.dress.api.scheduler.DispatcherContainer
import app.myoun.dress.fabric.scheduler.DressDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DispatcherContainerImpl : DispatcherContainer {

    private var fabricCoroutine: CoroutineContext? = null

    override val minecraft: CoroutineContext
        get() {
            if (fabricCoroutine == null) {
                fabricCoroutine = DressDispatcher()
            }
            return fabricCoroutine!!
        }
}