package com.calderasoftware.graphexample.edgecontrollers

import com.calderasoftware.graphexample.vertices.Person
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.T
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CrewMateController(
    @Autowired private val g: GraphTraversalSource
) {
    // A more interesting controller returns everyone that has served on the same ship as the person
    @GetMapping("people/{personId}/crew-mates")
    fun get(@PathVariable personId: Long): List<Person> {
        val crewMates = g.V(personId)
            .outE("assignment").inV().inE("assignment").outV()
            .dedup() // remove duplicates
            .elementMap<Any>().toList()
            // This is a logic step removing the original person from the list.
            // Technically they are part of the traversal, but for our purposes we don't want them.
            // I am sure this can be done directly in gremlin, but not sure how.
            .filter { it[T.id] != personId } // T.id is special object that is the id key of an elementMap

        return crewMates.map { Person(it) }
    }
}