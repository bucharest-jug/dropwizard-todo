package ro.bjug.todo.resources;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.db.Database;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ro.bjug.todo.api.Item;
import ro.bjug.todo.api.Todo;
import ro.bjug.todo.api.TodoBuilder;
import ro.bjug.todo.dao.TodoDao;

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

  private final TodoDao dao;

  public TodoResource(TodoDao dao) {
    this.dao = checkNotNull(dao, "dao is null");
  }

  @GET
  public List<Todo> list() {
    return dao.findAll();
  }

  @GET
  @Path("{id}")
  public Todo get(@PathParam("id") String id) {
    Todo todo = dao.findById(id);
    if (todo == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    return todo;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response create(@FormParam("email") String email) {
    String id = UUID.randomUUID().toString();
    while (dao.exists(id)) {
      id = UUID.randomUUID().toString();
    }

    final TodoBuilder builder = Todo.newBuilder().id(id).created(new Date());
    if (email != null) {
      builder.email(email);
    }
    dao.insert(builder.build());

    return Response.created(URI.create(id)).build();
  }

  @PUT
  @Path("{id}")
  public Todo update(@PathParam("id") String id, @Valid Todo todo) {
    checkArgument(id.equals(todo.getId()));
    dao.update(todo);
    return todo;
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") String id) {
    dao.delete(id);
  }

  @DELETE
  public void deleteAll() {
    dao.deleteAll();
  }
}
