package ro.bjug.todo;

import com.yammer.dropwizard.testing.ServiceLiveTest;
import org.junit.Test;

public class TodoServiceLiveTest extends ServiceLiveTest<TodoService> {

  public TodoServiceLiveTest() {
    super("config/todo.test.yml");
  }

  @Test
  public void testListAll() {

  }
}
