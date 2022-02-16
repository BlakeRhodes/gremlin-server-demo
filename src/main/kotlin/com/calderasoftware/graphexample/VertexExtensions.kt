package com.calderasoftware.graphexample

import org.apache.tinkerpop.gremlin.structure.Vertex

// Just a litter helper, the Naomi Wildman of the app if you will.
fun Vertex.longId(): Long {
    return this.id() as Long
}