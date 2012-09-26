package ro.bjug.todo.dao;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import ro.bjug.todo.api.Todo;

@RegisterMapper(TodoMapper.class)
public interface TodoDao {

  @SqlUpdate("create table if not exists todos(id varchar(36) primary key, json text)")
  void createTableIfNotExists();

  @SqlQuery("select json from todos")
  List<Todo> findAll();

  @SqlQuery("select json from todos where id = :id")
  Todo findById(@Bind("id") String id);

  @SqlQuery("select exists(select 1 from todos where id = :id)")
  boolean exists(@Bind("id") String id);

  @SqlUpdate("insert into todos(id, json) values (:id, :json)")
  void insert(@BindTodo Todo todo);

  @SqlUpdate("update todos set json = :json where id= :id")
  void update(@BindTodo Todo todo);

  @SqlUpdate("delete from todos where id = :id")
  void delete(@Bind("id") String id);

  @SqlUpdate("delete from todos")
  void deleteAll();

  void close();
}
