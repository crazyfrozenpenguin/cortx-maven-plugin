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

