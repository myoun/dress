package app.myoun.dress.api.command.node.internal

import app.myoun.dress.api.command.node.AbstractCommandNode
import app.myoun.dress.api.command.node.RootNode

class RootNodeImpl: AbstractCommandNode(), RootNode {

    internal fun initialize(
        name: String
    ) {
        super.initialize0(name)
    }
}