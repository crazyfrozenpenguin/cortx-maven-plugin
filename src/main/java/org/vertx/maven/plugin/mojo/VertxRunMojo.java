package org.vertx.maven.plugin.mojo;

import static java.lang.Long.MAX_VALUE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;

import java.net.URLClassLoader;
import java.util.concurrent.CountDownLatch;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.PlatformManager;

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

/**
 * @description Runs (blocking) the cortx embedded server or given vert.x module
 *              in maven space. Blocks until user presses CTRL-C.
 */
@Mojo(name = "run", requiresProject = true, threadSafe = false, requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class VertxRunMojo extends BaseVertxMojo {

	@Override
	public void execute() throws MojoExecutionException {

		final ClassLoader oldTCCL = Thread.currentThread().getContextClassLoader();
		try {
			setVertxMods();

			Thread.currentThread().setContextClassLoader(createClassLoader());

			final CountDownLatch latch = new CountDownLatch(1);

			final PlatformManager pm = createPlatformManager();

			pm.createModuleLink(moduleName, new Handler<AsyncResult<Void>>() {
				@Override
				public void handle(final AsyncResult<Void> asyncResult) {
					if (moduleName.equals("org.cortx~cortx-maven-plugin")) {
						// Deploy cortx handler
						getLog().info("Initializing cortx embedded server...");
						try {
							final JsonObject config = new JsonObject();
							config.putBoolean("log", log);
							config.putNumber("port", port);
							pm.deployVerticle("org/cortx/maven/plugin/module/cortx.js", config, ((URLClassLoader) Thread
									.currentThread().getContextClassLoader()).getURLs(), 1, null, new Handler<AsyncResult<String>>() {
								@Override
								public void handle(final AsyncResult<String> event) {
									if (event.succeeded()) {
										getLog().info("cortx server is now running.");
									} else {
										if (!event.succeeded()) {
											getLog().error(event.cause());
										}
										latch.countDown();
									}
								}
							});
						} catch (final Exception e) {
							getLog().info("cortx server failed to start.", e);
						}
					} else {
						pm.deployModule(moduleName, getConf(), instances, new Handler<AsyncResult<String>>() {
							@Override
							public void handle(final AsyncResult<String> event) {
								if (event.succeeded()) {
									getLog().info("cortx server succeeded to deploy user module.");
								} else {
									if (!event.succeeded()) {
										getLog().error(event.cause());
									}
									latch.countDown();
								}
							}
						});
					}
				}
			});

			latch.await(MAX_VALUE, MILLISECONDS);
		} catch (final Exception e) {
			throw new MojoExecutionException(e.getMessage());
		} finally {
			Thread.currentThread().setContextClassLoader(oldTCCL);
		}
	}

}
