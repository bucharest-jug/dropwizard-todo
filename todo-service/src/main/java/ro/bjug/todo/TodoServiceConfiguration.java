package ro.bjug.todo;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonProperty;

public class TodoServiceConfiguration extends Configuration {

  @Valid
  @NotNull
  @JsonProperty
  private DatabaseConfiguration database = new DatabaseConfiguration();

  public DatabaseConfiguration getDatabase() {
    return database;
  }
}
