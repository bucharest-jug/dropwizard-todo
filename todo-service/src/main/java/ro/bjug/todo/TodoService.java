package ro.bjug.todo;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.DBIExceptionsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.views.ViewBundle;
import ro.bjug.todo.dao.TodoDao;
import ro.bjug.todo.resources.TodoResource;

public class TodoService extends Service<TodoServiceConfiguration> {

  public static void main(String[] arguments) throws Exception {
    new TodoService().run(arguments);
  }

  public TodoService() {
    addBundle(new ViewBundle());
    addBundle(new DBIExceptionsBundle());
  }

  @Override
  protected void initialize(TodoServiceConfiguration configuration,
                            Environment environment) throws Exception {

    final DatabaseFactory factory = new DatabaseFactory(environment);
    Database database = factory.build(configuration.getDatabase(), "todo");

    final TodoDao dao = database.onDemand(TodoDao.class);
    dao.createTableIfNotExists();

    environment.addResource(new TodoResource(dao));
  }
}
