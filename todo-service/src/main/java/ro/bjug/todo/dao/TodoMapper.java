package ro.bjug.todo.dao;

import static com.google.common.base.Throwables.propagate;
import com.yammer.dropwizard.json.Json;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import ro.bjug.todo.api.Todo;

public class TodoMapper implements ResultSetMapper<Todo> {

  @Override
  public Todo map(int i, ResultSet resultSet, StatementContext statementContext)
      throws SQLException {
    Json json = new Json();
    try {
      return json.readValue(resultSet.getString("json"), Todo.class);

    } catch (IOException e) {
      throw propagate(e);
    }
  }
}
