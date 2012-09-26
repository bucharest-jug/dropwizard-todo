package ro.bjug.todo.api;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Item {

  public static ItemBuilder newBuilder() {
    return new ItemBuilder();
  }

  @JsonProperty
  private final String title;

  @JsonProperty
  private final Date created;

  @JsonProperty
  private final Date completed;

  @JsonCreator
  public Item(
      @JsonProperty("title") String title,
      @JsonProperty("created") Date created,
      @JsonProperty("completed") Date completed
  ) {
    this.title = checkNotNull(title, "title is null");
    this.created = checkNotNull(created, "created is null");
    this.completed = completed;
  }

  public String getTitle() {
    return title;
  }

  public Date getCreated() {
    return created;
  }

  public Date getCompleted() {
    return completed;
  }

  @JsonIgnore
  public boolean isDone() {
    return completed != null;
  }

  public ItemBuilder toBuilder() {
    return new ItemBuilder(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Item)) return false;

    Item item = (Item) o;

    if (created != null ? !created.equals(item.created) : item.created != null) return false;
    if (completed != null ? !completed.equals(item.completed) : item.completed != null) return false;
    if (title != null ? !title.equals(item.title) : item.title != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (created != null ? created.hashCode() : 0);
    result = 31 * result + (completed != null ? completed.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Item{" +
        "title='" + title + '\'' +
        ", created=" + created +
        ", finished=" + completed +
        '}';
  }
}
