package app.myoun.dress.api.command.node.internal

import app.myoun.dress.api.command.node.AbstractCommandNode
import app.myoun.dress.api.command.node.LiteralNode

class LiteralNodeImpl : AbstractCommandNode(), LiteralNode {

    internal fun initialize(name: String) {
        initialize0(name)
    }
}