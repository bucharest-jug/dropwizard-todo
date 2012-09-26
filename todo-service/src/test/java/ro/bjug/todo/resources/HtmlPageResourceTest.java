package ro.bjug.todo.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.yammer.dropwizard.testing.ResourceTest;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class HtmlPageResourceTest extends ResourceTest {

  @Override
  protected void setUpResources() throws Exception {
    addResource(new HtmlPageResource());
  }

  @Test
  public void testLoadsIndexPage() throws Exception {
    ClientResponse response = client().resource("/").get(ClientResponse.class);
    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

    assertThat(response.getEntity(String.class)).contains("html").containsIgnoringCase("Dropwizard Todo");
  }
}
