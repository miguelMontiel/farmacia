import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;

public class USAMAIN
{
        static String bd = "nombreBD";
        static String login = "usuario";
        static String password = "contraseÃ±a";
        static String url = "jdbc:mysql://localhost/"+bd;
        
    public static void main(String args[]) throws Exception
    {
        MAIN m = new MAIN();
        m.setTitle("Farmaciashon");
        m.setSize(600, 250);
        m.setVisible(true);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url,login,password);
            if (conn != null)
            {
            System.out.println("Conexion a base de datos " + url + " ... Ok");
            conn.close();
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url);
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
    }
}