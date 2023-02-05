package app.myoun.dress.fabric.scheduler

import app.myoun.dress.fabric.FabricDressMod
import app.myoun.dress.fabric.server
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

class DressDispatcher : CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (FabricDressMod.primaryThread == Thread.currentThread()) {
            block.run()
        } else {
            server.executeSync(block)
        }
    }

}