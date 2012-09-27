package com.yammer.dropwizard.testing;

import com.google.common.base.Charsets;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.ServerFactory;
import com.yammer.dropwizard.logging.Log;
import com.yammer.dropwizard.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;

public class ServiceLiveTest<T extends Service> {

  private static Log LOG = Log.forThisClass();

  private final String configFilePath;

  private T service;

  private Server server;
  private int port;
  private int adminPort;
  private Configuration configuration;

  public ServiceLiveTest(String configFilePath) {
    this.configFilePath = checkNotNull(configFilePath, "configFilePath");
  }

  public T getService() {
    return service;
  }

  public int getPort() {
    return port;
  }

  public int getAdminPort() {
    return adminPort;
  }

  public <C extends Configuration> C getConfiguration() {
    return (C) configuration;
  }

  /**
   * @see com.yammer.dropwizard.cli.ServerCommand#run
   */
  @Before
  public void setUp() throws Exception {
    service = createServiceInstance();

    int[] openPorts = getPossiblyOpenPorts(2);
    port = openPorts[0];
    adminPort = openPorts[1];

    File temporaryConfigFile = createTemporaryConfigFile(port, adminPort);

    ConfigurationFactory<?> factory = ConfigurationFactory.forClass(
        service.getConfigurationClass(), new Validator(), service.getJacksonModules());
    configuration = (Configuration) factory.build(temporaryConfigFile);

    Environment environment = new Environment(service, configuration);
    service.initializeWithBundles(configuration, environment);

    server = new ServerFactory(configuration.getHttpConfiguration(),
        service.getName()).buildServer(environment);

    LOG.info("Starting embedded Jetty with configuration {}", configuration.toString());
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    LOG.info("Stopping embedded Jetty server");
    server.stop();
    server.join();
    server = null;
  }

  /**
   * This method tries to find a set of open ports by creating a bunch of
   * server sockets.
   * <p/>
   * Note: This procedure is susceptible to a race condition and some
   * of the returned ports may be in use
   */
  private int[] getPossiblyOpenPorts(int number) throws IOException {
    ServerSocket[] serverSockets = new ServerSocket[number];
    try {
      int[] ports = new int[number];
      for (int i = 0; i < number; i++) {
        serverSockets[i] = new ServerSocket(0 /* bind to any open port */);
        ports[i] = serverSockets[i].getLocalPort();
      }
      return ports;

    } finally {
      for (ServerSocket socket : serverSockets) {
        socket.close();
      }
    }
  }

  protected T createServiceInstance() throws Exception {
    return getServiceClass().newInstance();
  }

  protected String createConfigurationAsYaml() throws IOException {
    return Resources.toString(Resources.getResource(configFilePath), Charsets.UTF_8);
  }

  private File createTemporaryConfigFile(int port, int adminPort) throws IOException {
    File temporaryConfig = File.createTempFile("dropwizard", ".yml");
    temporaryConfig.deleteOnExit();

    String content = createConfigurationAsYaml() +
        "\n\n" +
        "http:\n" +
        "  port: {{port}}\n" +
        "  adminPort: {{adminPort}}\n";

    content = content
        .replace("{{port}}", "" + port)
        .replace("{{adminPort}}", "" + adminPort);

    Files.write(content, temporaryConfig, Charsets.UTF_8);

    return temporaryConfig;
  }

  /**
   * @see com.yammer.dropwizard.AbstractService#getConfigurationClass
   */
  @SuppressWarnings("unchecked")
  public final Class<T> getServiceClass() {
    Type t = getClass();
    while (t instanceof Class<?>) {
      t = ((Class<?>) t).getGenericSuperclass();
    }
    if (t instanceof ParameterizedType) {
      for (Type param : ((ParameterizedType) t).getActualTypeArguments()) {
        if (param instanceof Class<?>) {
          final Class<?> cls = (Class<?>) param;
          if (Service.class.isAssignableFrom(cls)) {
            return (Class<T>) cls;
          }
        }
      }
    }
    throw new IllegalStateException("Can not figure out Configuration type " +
        "parameterization for " + getClass().getName());
  }
}
