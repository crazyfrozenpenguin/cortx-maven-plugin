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
      <version>0.1.1</version>
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


Cortx DSL Client usage
------------------

## configuration:

   	<dependency>
      <groupId>org.cortx</groupId>
      <artifactId>cortx-maven-plugin</artifactId>
      <version>0.1.1</version>
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
    cortx.on().get("/my/app/url?with=params").returnBody(jsonString).returnHeader("Content-Type", "application/json;").response();
    
Available commands are: get, post, put, delete, head, options and trace

Available actions are: returnBody(body:String), returnHeader(key:String, value:String), returnHeaders(headers:Map<String,String>), returnStatusCode(code:int), returnStatusMessage(msg:String)
    
#### Retrieve request details

    // Retrieve submitted request information
    final String requestBody = cortx.retrieve().post("/my/app/url?with=params").body();
    
Available commands are: get, post, put, delete, head, options and trace

Available actions are body():String, header(key:String):String, headers():Map<String, String>

#### Verification/Validation

    // Verify if REST end-point was hit
	cortx.verify().post("/my/app/url?with=params").withHeader("Content-Type", "application/json").withBody("{}").wasCalled();
    
Available commands are get, post, put, delete, head, options and trace

#### Reset Cortx server state

	cortx.reset();
