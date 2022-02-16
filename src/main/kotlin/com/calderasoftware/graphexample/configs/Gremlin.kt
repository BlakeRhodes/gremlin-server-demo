package com.calderasoftware.graphexample.configs

import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.driver.ser.Serializers
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Gremlin {
    @Bean
    fun makeCluster(): Cluster {
        // Nice Builder, this part is EZ
        return Cluster.build()
            .addContactPoint("localhost")
            .port(8182)
            .serializer(Serializers.GRAPHSON_V3D0)
            .create()
    }

    @Bean
    fun makeG(cluster: Cluster): GraphTraversalSource{
        // This is what will be used to actually make queries, the mysterious "g" or graph
        return traversal().withRemote(DriverRemoteConnection.using(cluster))
    }
}