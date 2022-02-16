package com.calderasoftware.graphexample.vertexcontrollers

import com.calderasoftware.graphexample.vertices.Person
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("people")
class PersonController: AbstractVertexController<Person>(
    Person.calculator // Oh, we can just pass a companion object, very nice! Whoever wrote this was classy.
)