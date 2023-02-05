package app.myoun.dress.api.command.node

import app.myoun.dress.api.command.DressCommandContext
import app.myoun.dress.api.command.DressCommandSource
import app.myoun.dress.api.command.node.internal.ArgumentNodeImpl
import app.myoun.dress.api.command.node.internal.LiteralNodeImpl
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class AbstractCommandNode : DressCommandNode {

    protected fun <T> dressCommandField(initialValue: T): ReadWriteProperty<Any?, T> =
        object : ObservableProperty<T>(initialValue) {
            private var initialized = false

            override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean {
                require(!initialized) { "Cannot redefine ${property.name} after registration" }

                return true;
            }
        }

    lateinit var name: String

    var parent: AbstractCommandNode? = null

    override var permission: String? by dressCommandField(null)

    var requires: (DressCommandSource.() -> Boolean)? = null
        private set

    var executes: (DressCommandSource.(DressCommandContext) -> Unit)? = null
        private set

    protected fun initialize0(name: String) {
        this.name = name
    }

    val nodes = arrayListOf<AbstractCommandNode>()

    override fun requires(requires: DressCommandSource.() -> Boolean) {
        require(this.requires == null) { "Cannot redefine requires" }
        this.requires = requires
    }

    override fun executes(executes: DressCommandSource.(DressCommandContext) -> Unit) {
        require(this.executes == null) { "Cannot redefine executes" }
        this.executes = executes
    }

    override fun then(
        name: String,
        vararg arguments: Pair<String, ArgumentType<*>>,
        init: DressCommandNode.() -> Unit
    ) {
        then(LiteralNodeImpl().apply {
            parent = this@AbstractCommandNode
            initialize(name)
        }.also {
            nodes += it
        }, arguments, init)
    }

    override fun then(
        argument: Pair<String, ArgumentType<*>>,
        vararg arguments: Pair<String, ArgumentType<*>>,
        init: DressCommandNode.() -> Unit
    ) {
        then(ArgumentNodeImpl().apply {
            parent = this@AbstractCommandNode
            initialize(argument.first, argument.second)
        }.also {
            nodes += it
        }, arguments, init)
    }

    private fun then(
        node: AbstractCommandNode,
        arguments: Array<out Pair<String, ArgumentType<*>>>,
        init: DressCommandNode.() -> Unit
    ) {
        var tail = node

        for ((subName, subArgument) in arguments) {
            val child = ArgumentNodeImpl().apply {
                parent = tail
                initialize(subName, subArgument)
            }.also { tail.nodes += it }

            tail = child
        }

        tail.init()
    }


}