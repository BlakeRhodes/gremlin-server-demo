package com.calderasoftware.graphexample.vertexcontrollers

import com.calderasoftware.graphexample.vertices.StarShip
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/star-ships")
class StarShipController: AbstractVertexController<StarShip>(
    StarShip.calculator // This makes new controllers really simple. Very clever work, good job!
)