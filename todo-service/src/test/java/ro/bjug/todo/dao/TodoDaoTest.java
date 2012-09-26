package ro.bjug.todo.dao;

import java.util.Date;
import java.util.UUID;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import ro.bjug.todo.api.Item;
import ro.bjug.todo.api.Todo;

public class TodoDaoTest {

  private TodoDao dao;

  @Before
  public void setUp() {
    DBI dbi = new DBI("jdbc:h2:mem:test");
    dao = dbi.open(TodoDao.class);
  }

  @After
  public void tearDown() {
    dao.close();
  }

  @Test
  public void testCreateAnEmptyTable() {
    dao.createTableIfNotExists();
    assertThat(dao.findAll()).isEmpty();
  }

  @Test
  public void testCRUD() {
    final Todo todo = Todo.newBuilder()
        .id(UUID.randomUUID().toString())
        .email("john@example.com")
        .build();

    dao.createTableIfNotExists();
    dao.insert(todo);

    assertThat(dao.exists(todo.getId())).isTrue();
    assertThat(dao.exists("dummy-missing-id")).isFalse();

    assertThat(dao.findAll()).contains(todo);
    assertThat(dao.findById(todo.getId())).isEqualTo(todo);

    final Item item = Item.newBuilder()
        .title("Something")
        .created(new Date())
        .build();

    Todo updated = todo.toBuilder().item(item).build();
    dao.update(updated);

    assertThat(dao.findById(updated.getId())).isEqualTo(updated);

    dao.delete(updated.getId());
    assertThat(dao.findAll()).isEmpty();
  }

  @Test
  public void testTryToRetrieveMissingTodo() {
    dao.createTableIfNotExists();

    assertThat(dao.findById("dummy")).isNull();
  }

  @Test
  public void testInsertSameElementTwice() {
    final Todo todo = Todo.newBuilder()
        .id(UUID.randomUUID().toString())
        .email("john@example.com")
        .build();
    dao.createTableIfNotExists();

    dao.insert(todo);
    try {
      dao.insert(todo);
      fail("Same todo list was added twice");

    } catch (UnableToExecuteStatementException e) {
      assertThat(e.getMessage()).contains("Unique index or primary key violation");
    }
  }
}
