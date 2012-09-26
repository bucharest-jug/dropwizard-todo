package ro.bjug.todo.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.yammer.dropwizard.testing.ResourceTest;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import ro.bjug.todo.api.Item;
import ro.bjug.todo.api.Todo;
import ro.bjug.todo.dao.TodoDao;

public class TodoResourceTest extends ResourceTest {

  private TodoDao dao;

  @Override
  protected void setUpResources() throws Exception {
    DBI dbi = new DBI("jdbc:h2:mem:test");

    dao = dbi.open(TodoDao.class);
    dao.createTableIfNotExists();

    addResource(new TodoResource(dao));
  }

  @After
  public void tearDown() throws Exception {
    dao.close();
    super.tearDownJersey();
  }

  @Test
  public void testCreateNewTodoWithoutEmail() {
    ClientResponse response = client().resource("/todos").post(ClientResponse.class);
    assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

    List<Todo> all = dao.findAll();
    assertThat(all).hasSize(1);

    Todo todo = all.get(0);
    assertThat(response.getLocation().toASCIIString()).endsWith(todo.getId());
    assertThat(todo.getEmail()).isNullOrEmpty();
  }

  @Test
  public void testCreateNewTodoWithEmail() {
    final String EMAIL = "john@example.com";

    MultivaluedMap<String, String> arguments = new MultivaluedMapImpl();
    arguments.add("email", EMAIL);

    ClientResponse response = client().resource("/todos")
        .type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, arguments);
    assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

    Todo todo = dao.findAll().get(0);
    assertThat(todo.getEmail()).isEqualTo(EMAIL);
  }

  @Test
  public void testRetrieveExistingTodoList() {
    final String id = UUID.randomUUID().toString();
    final Todo todo = Todo.newBuilder().id(id).created(new Date()).build();
    dao.insert(todo);

    Todo stored = client().resource("/todos/" + id).get(Todo.class);
    assertThat(stored).isEqualTo(todo);
  }

  @Test
  public void testAddItemToExistingTodo() {
    final String id = UUID.randomUUID().toString();
    final Todo todo = Todo.newBuilder().id(id).created(new Date()).build();
    dao.insert(todo);

    final Item item = Item.newBuilder().title("Something").created(new Date()).build();
    Todo updated = todo.toBuilder().item(item).build();

    Todo response = client().resource("/todos/" + id)
        .type(MediaType.APPLICATION_JSON).put(Todo.class, updated);
    assertThat(response).isEqualTo(updated);
  }

  @Test
  public void testDeleteTodo() {
    final String id = UUID.randomUUID().toString();
    final Todo todo = Todo.newBuilder().id(id).created(new Date()).build();
    dao.insert(todo);

    ClientResponse response = client().resource("/todos/" + id).delete(ClientResponse.class);
    assertThat(response.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());

    assertThat(dao.findById(id)).isNull();
  }

  @Test
  public void testDeleteAll() {
    final String id = UUID.randomUUID().toString();
    final Todo todo = Todo.newBuilder().id(id).created(new Date()).build();

    dao.insert(todo);
    dao.insert(todo.toBuilder().id("dummy").build());
    assertThat(dao.findAll()).hasSize(2);

    ClientResponse response = client().resource("/todos").delete(ClientResponse.class);
    assertThat(response.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    assertThat(dao.findAll()).isEmpty();
  }
}
