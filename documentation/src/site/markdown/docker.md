#Docker



##Issues

###Issue getting docker to start connect from the command line after starting with Kitematic.

When trying to connect to docker after installing with docker machine, I was getting : 

    pebbles:sprint0 rickyw$ docker ps
    Get http:///var/run/docker.sock/v1.20/containers/json: dial unix /var/run/docker.sock: no such file or directory.
    * Are you trying to connect to a TLS-enabled daemon without TLS?
    * Is your docker daemon up and running?


To fix the problem, execute this on the command line `eval "$(docker-machine env default)"`

Then this happens:

    pebbles:sprint0 rickyw$ docker ps
    CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES

Resolution was from `https://github.com/docker/machine/issues/1760`

More information on Docker Machine to be found here : `https://docs.docker.com/machine/get-started/`

**ISSUE RESOLVED**