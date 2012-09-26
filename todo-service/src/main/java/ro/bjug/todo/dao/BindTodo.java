package ro.bjug.todo.dao;

import com.yammer.dropwizard.json.Json;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import ro.bjug.todo.api.Todo;

@BindingAnnotation(BindTodo.TodoBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindTodo {

  public static class TodoBinderFactory implements BinderFactory {

    private static final Json json = new Json();

    @Override
    public Binder build(Annotation annotation) {
      return new Binder<BindTodo, Todo>() {
        @Override
        public void bind(SQLStatement<?> q, BindTodo bind, Todo todo) {
          q.bind("id", todo.getId());
          q.bind("json", json.writeValueAsString(todo));
        }
      };
    }
  }
}
