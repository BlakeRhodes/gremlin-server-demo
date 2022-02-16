docker container stop gremlin
docker container rm gremlin
docker run -p 8182:8182 --name gremlin tinkerpop/gremlin-server