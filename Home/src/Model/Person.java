package Model

public abstract class Person {
  protected String id;
  protected String username;
  protected String password;
  protected String name;

  public Person(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
      this.name = name;
  }
}
