package com.calderasoftware.graphexample.vertexcontrollers

import com.calderasoftware.graphexample.longId
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

// This bad boy makes creating RESTful endpoints easier.
// These actions don't care about what the entites, and getting them is mostly the same.
abstract class AbstractVertexController<T>(
    //This holds the object specific transformation logic
    private val graphCalculator: GraphCalculator<T>,
) {
    @Autowired
    private lateinit var g: GraphTraversalSource

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): T {
        val map = g.V(id)
            .elementMap<Any>()
            .next()
        // calculator contains creation logic
        return graphCalculator.build(map)
    }

    @GetMapping
    fun getAll(): List<T> {
        val values = g.V()
            // This will look for vertices that are of a "type" label.
            // This is how wer are retaining the type information.
            // The calculator contains this detail because it should change for each controller
            .hasLabel(graphCalculator.label)
            .elementMap<Any>().toList()
        return values.map { graphCalculator.build(it) }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody value: T): T {

        // Add function to hold the creation logic
        val vertex = graphCalculator.add(g, value).next()
        val id = vertex.longId()

        return get(id)
    }

    @PutMapping("/{id}")
    fun put(@PathVariable id: Long, @RequestBody updates: T): T {
        // Update logic is kept in the calculator since it is class specific.
        // I have not tried to handle any errors in this example, so this will catch fire with a bad request.
        graphCalculator.update(g, updates)
        return get(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        // Since all vertices are the same in the db removing them is very simple.
        g.V(id).drop().iterate()
    }
}