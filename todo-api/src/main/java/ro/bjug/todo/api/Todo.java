package ro.bjug.todo.api;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Todo {

  public static TodoBuilder newBuilder() {
    return new TodoBuilder();
  }

  @JsonProperty
  private final String id;

  @JsonProperty
  private final String email;

  @JsonProperty
  private final Date created;

  @JsonProperty
  private final List<Item> items;

  @JsonCreator
  public Todo(
      @JsonProperty("id") String id,
      @JsonProperty("email") String email,
      @JsonProperty("created") Date created,
      @JsonProperty("items") List<Item> items
  ) {
    this.id = checkNotNull(id, "id is null");
    this.email = email;
    this.created = created;
    this.items = (items == null) ? ImmutableList.<Item>of() : ImmutableList.copyOf(items);
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public Date getCreated() {
    return created;
  }

  public List<Item> getItems() {
    return items;
  }

  public TodoBuilder toBuilder() {
    return new TodoBuilder(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Todo)) return false;

    Todo todo = (Todo) o;

    if (created != null ? !created.equals(todo.created) : todo.created != null) return false;
    if (email != null ? !email.equals(todo.email) : todo.email != null) return false;
    if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
    if (items != null ? !items.equals(todo.items) : todo.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (created != null ? created.hashCode() : 0);
    result = 31 * result + (items != null ? items.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id='" + id + '\'' +
        ", email='" + email + '\'' +
        ", created=" + created +
        ", items=" + items +
        '}';
  }
}
