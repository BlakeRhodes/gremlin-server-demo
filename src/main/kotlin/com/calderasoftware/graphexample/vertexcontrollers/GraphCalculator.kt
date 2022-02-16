package com.calderasoftware.graphexample.vertexcontrollers

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Vertex

// Each class should have one of these for to handle actual storage logic
data class GraphCalculator<T>(
    val label: String,
    val build: (MutableMap<Any, Any>) -> T,
    val add: (GraphTraversalSource, T) -> GraphTraversal<Vertex, Vertex>,
    val update: (GraphTraversalSource, T) -> Unit,
)
