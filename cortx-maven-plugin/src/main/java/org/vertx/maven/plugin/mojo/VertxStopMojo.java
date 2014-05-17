package org.vertx.maven.plugin.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;
import static org.vertx.maven.plugin.mojo.VertxStartMojo.pm;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

/**
 * @description Stops the cortx embedded verticle or module.
 */
@Mojo(name = "stop", requiresProject = true, threadSafe = false, requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class VertxStopMojo extends AbstractMojo {

	/**
	 * The Maven project.
	 * 
	 * @parameter property="${project}"
	 * @required
	 */
	@Component
	protected MavenProject mavenProject;

	@Override
	public void execute() throws MojoExecutionException {
		if (pm != null && !pm.listInstances().isEmpty()) {
			pm.stop();
			getLog().info("cortx successfully stopped!");
		}
	}

}
