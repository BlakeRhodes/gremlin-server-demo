package com.calderasoftware.graphexample.edgecontrollers

import com.calderasoftware.graphexample.edges.Assignment
import com.calderasoftware.graphexample.vertices.Person
import com.calderasoftware.graphexample.vertices.StarShip
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("assignments")
class AssignmentController(
    @Autowired private val g: GraphTraversalSource,
) {

    // Creates a new relationship as an edge
    @PostMapping("/{personId}/{shipId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(
        @RequestBody assignment: Assignment,
        @PathVariable personId: Long,
        @PathVariable shipId: Long
    ) {
        // Go find entities for later
        // Next runs the query and gets the result
        val person = g.V(personId).next()
        val ship = g.V(shipId).next()

        // Add edge to graph with some properties
        g.addE("assignment")
            .property("department", assignment.department)
            .property("start", assignment.start)
            .property("end", assignment.end)
            .property("current", assignment.current)

            // Set the direction of the relationship, ie a person is assigned a ship (person->ship)
            .from(person).to(ship)
            .next()
    }

    // Gets all ships a person has been assigned to
    @GetMapping("/{personId}:person")
    fun getPerson(@PathVariable personId: Long): List<StarShip> {
        val assignments = g.V(personId).out("assignment")
            // elementMap is new, lots of docs point to valueMap which is deprecated
            .elementMap<Any>()
            .toList() // traverse to list

        // The types get mangled as they are coming out of the database
        // If you look at the constructor you will see what it takes to get them back.
        // Not pretty, open to ideas here.
        // ORMs usually hide this, but tinkerpop is far too raw to be expected to do this

        return assignments.map {
            StarShip(it)
        }
    }

    // Gets all the people who have been assigned to a ship
    @GetMapping("/{starShipId}:star-ship")
    fun getStarShip(@PathVariable starShipId: Long): List<Person> {
        val assignments = g.V(starShipId)
            // This is `` wrapped because in is a keyword in kotlin
            .`in`("assignment")
            .elementMap<Any>().toList()

        return assignments.map { Person(it) }
    }
}