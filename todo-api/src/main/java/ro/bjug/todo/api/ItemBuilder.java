package ro.bjug.todo.api;

import java.util.Date;

public class ItemBuilder {

  private String title;
  private Date created;
  private Date completed;

  public ItemBuilder() {
  }

  public ItemBuilder(Item item) {
    this.title = item.getTitle();
    this.created = item.getCreated();
    this.completed = item.getCompleted();
  }

  public ItemBuilder title(String title) {
    this.title = title;
    return this;
  }

  public ItemBuilder created(Date created) {
    this.created = created;
    return this;
  }

  public ItemBuilder completed(Date completed) {
    this.completed = completed;
    return this;
  }

  public Item build() {
    return new Item(title, created, completed);
  }
}