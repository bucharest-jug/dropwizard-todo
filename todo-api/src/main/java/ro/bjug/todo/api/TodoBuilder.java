package ro.bjug.todo.api;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;

public class TodoBuilder {

  private String id;
  private String email;
  private Date created;
  private List<Item> items = Lists.newArrayList();

  public TodoBuilder() {
  }

  public TodoBuilder(Todo todo) {
    this.id = todo.getId();
    this.email = todo.getEmail();
    this.created = todo.getCreated();
    this.items = Lists.newArrayList(todo.getItems());
  }

  public TodoBuilder id(String id) {
    this.id = checkNotNull(id, "id is null");
    return this;
  }

  public TodoBuilder email(String email) {
    this.email = checkNotNull(email, "email is null");
    return this;
  }

  public TodoBuilder created(Date created) {
    this.created = checkNotNull(created, "created is null");
    return this;
  }

  public TodoBuilder items(List<Item> items) {
    this.items = Lists.newArrayList(checkNotNull(items, "items is null"));
    return this;
  }

  public TodoBuilder item(Item item) {
    this.items.add(item);
    return this;
  }

  public Todo build() {
    return new Todo(id, email, created, items);
  }
}