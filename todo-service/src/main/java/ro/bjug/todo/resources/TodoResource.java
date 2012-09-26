package ro.bjug.todo.resources;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.db.Database;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ro.bjug.todo.api.Item;
import ro.bjug.todo.api.Todo;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

  public static final Item ITEM = Item.newBuilder()
      .title("Finish demo")
      .created(new Date())
      .build();

  public static final Todo TODO = Todo.newBuilder()
      .id(UUID.randomUUID().toString())
      .created(new Date())
      .email("john@example.com")
      .item(ITEM)
      .build();

  private final Database database;

  public TodoResource(Database database) {
    this.database = checkNotNull(database, "database");
  }

  @GET
  public List<Todo> list() {
    return ImmutableList.of(TODO);
  }

  @GET
  @Path("{id}")
  public Todo get(@PathParam("id") String id) {
    return TODO;
  }

  @POST
  public Response create() {
    // TODO: generate ID and create
    return Response.created(URI.create("id")).build();
  }

  @PUT
  @Path("{id}")
  public Response update(@PathParam("id") String id, @Valid Todo todo) {
    return Response.ok().build();
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") String id) {

  }
}
