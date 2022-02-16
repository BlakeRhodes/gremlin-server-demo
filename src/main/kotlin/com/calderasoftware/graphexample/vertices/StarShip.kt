package com.calderasoftware.graphexample.vertices

import com.calderasoftware.graphexample.vertexcontrollers.GraphCalculator
import org.apache.tinkerpop.gremlin.structure.T

// This is basically a person with more fields (deflector and warp to be exact)
data class StarShip(
    val id: Long?,
    val name: String,
    val registry: String
) {
    companion object {
        private const val label = "star-ship"
        private const val nameKey = "name"
        private const val registryKey = "registry"
        val calculator = GraphCalculator(
            label = label,
            build = { map -> StarShip(map) },
            add = { graph, value ->
                graph.addV(label).property(nameKey, value.name).property(registryKey, value.registry)
            },
            update = { graph, value ->
                graph.V(value.id).property(nameKey, value.name).property(registryKey, value.registry).next()
            },
        )
    }

    constructor(map: MutableMap<Any, Any>): this(
        id = map[T.id] as Long,
        name = map[nameKey].toString(),
        registry = map[registryKey].toString()
    )
}
