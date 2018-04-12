package hello;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
Problems:
https://dev.mysql.com/doc/refman/5.7/en/sql-mode.html

W MySQL workbench to tez nie dziala:
-- INSERT INTO `db_example`.`users` (`enabled`, `password`, `username`) VALUES (b'1', 'a', 'a');
-- ALTER TABLE`db_example`.`users` MODIFY id int NOT NULL AUTO_INCREMENT;
SHOW VARIABLES LIKE 'sql_mode'; -- to jest odpowiedzialne
https://stackoverflow.com/questions/25865104/field-id-doesnt-have-a-default-value

*/

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {
  // TODO: Try to get other generation type
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String username;

  private String password;

  private Boolean enabled;

	public Integer getId() {
		return id;
	}

  // Probably should not be possible to set id
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

  public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
