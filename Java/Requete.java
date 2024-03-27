import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Requete {
  Connection connection = null;
  public Requete (Connection connection){
    this.connection = connection;
  }

  public String[] getQuestion(int id) {
    String requete = "SELECT * FROM question WHERE id_question = " + id + ";";
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(requete);
      if (rs.next()) {
        return new String[] {rs.getString("pays"), rs.getString("capital")};
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

}
