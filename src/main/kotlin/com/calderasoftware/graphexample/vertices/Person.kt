package com.calderasoftware.graphexample.vertices

import com.calderasoftware.graphexample.vertexcontrollers.GraphCalculator
import org.apache.tinkerpop.gremlin.structure.T

data class Person(
    val id: Long?, // For the pre-save vs post-save, this saves us have two different types. For this example it is fine, but choose wisely for larger projects.
    val name: String,
) {
    companion object {
        private const val label = "person"
        private const val nameKey = "name"

        // Since this is a companion object you can easily pass it around when you need it. I think this might come in handy.
        val calculator = GraphCalculator(
            label = label,
            build = { map -> Person(map) },
            add = { graph, value -> graph.addV(label).property(nameKey, value.name) },
            update = { graph, value -> graph.V(value.id).property(nameKey, value.name).next() },
        )
    }

    // This is the scary part, figuring out the types after pulling it from the db.
    // I think a nicer solution should be found, this will explode with larger objects.
    constructor(map: MutableMap<Any, Any>) : this(
        map[T.id] as Long?, // T.id is the special object key for the id of the entity.
        map[nameKey].toString()
    )
}
