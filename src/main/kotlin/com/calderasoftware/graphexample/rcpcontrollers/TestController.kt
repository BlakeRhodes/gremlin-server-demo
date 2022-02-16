package com.calderasoftware.graphexample.rcpcontrollers

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// IGNORE ME!!! Just for testing the basic connection not really very interesting.

@RestController
class TestController(@Autowired private val g: GraphTraversalSource) {
    @GetMapping("test/vertex:count")
    fun getVertexCount(): Int {
        return g.V()
            // Well, kinda interesting, here is how to count something
            .count()
            .next().toInt()
    }

    @GetMapping("test/edge:count")
    fun getEdgeCount(): Int {
        return g.E().count().next().toInt()
    }
}