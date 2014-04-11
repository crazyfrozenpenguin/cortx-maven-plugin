cortx-maven-plugin
==================

cortx is a Maven Plugin that facilitates the testing of client side REST services.

Provides an embedded server that, for any given URI, can:

- Store request information
- Injects response on request
- Retrieve request information

Plugin Usage
------------

### cortx:run

Starts the cortx server in blocking mode.

	mvn cortx:run


### cortx:start, cortx:stop are to be used within maven phases

    <plugin>
      <groupId>org.cortx</groupId>
      <artifactId>cortx-maven-plugin</artifactId>
      <version>2.0.5-final</version>
      <configuration>
      	<log>true</log>
      	<port>7919</port>
      </configuration>
      <executions>
        <execution>
          <id>start-vertx</id>
          <phase>pre-integration-test</phase>
          <goals>
            <goal>start</goal>
          </goals>
        </execution>
        <execution>
          <id>stop-vertx</id>
          <phase>post-integration-test</phase>
          <goals>
            <goal>stop</goal>
          </goals>
        </execution>
      </executions>
    </plugin>


Cortx Client usage
------------------

## configuration:

   	<dependency>
      <groupId>org.cortx</groupId>
      <artifactId>cortx-maven-plugin</artifactId>
      <version>2.0.5-final</version>
      <scope>test</scope>
    </dependency>


## interface:

#### Create Cortx instance
    
    // Create Cortx client
    // Host - The Cortx Maven Plugin host (normally localhost)
    // Port - The port Cortx was set to listen to (defaults to 7919 if not passed)
    final Cortx cortx = CortxFactory.getCortx("localhost", 12201);

#### Response Registration

    // Register response for when a given REST end-point url is hit
    cortx.on().get("/my/app/url?with=params").returns(jsonString);
    
Available commands are: get(url), post(url) - other commands to be supported soon

Available actions are: returns(body) - other return such as desired HTTP status, headers, etc to come soon.
    
#### Retrieve request details

    // Retrieve submitted request information
    final String requestBody = cortx.retrieve().post("/my/app/url?with=params").body();
    
Available commands are get(url), post(url) - others to be supported soon

Available actions are body() - others such as request header(s) will come soon

#### Verification/Validation

    // Verify if REST end-point was hit
    cortx.verify().post(url).wasCalled();
    
Available commands are get(url), post(url) - more to come soon

Available actions are wasCalled() - more to come soon (times called, with body, with header(s), etc)
