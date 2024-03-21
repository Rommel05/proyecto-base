import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Compañia {
    private static int currentScreen = 0;
    private static  java.sql.Connection con;
    public static void main(String[] args) throws SQLException {
        String host = "jdbc:sqlite:src/main/resources/tienda";
        con = java.sql.DriverManager.getConnection( host );
        Scanner sc = new Scanner(System.in);
        int op;
        while (true) {
            printMenu();
            op = getOption();
            if (op == 0) {
                break;
            }
            if (currentScreen == 0) {
                switch (op) {
                    case 1: infoTienda();
                    case 2: nuevaTienda();
                }
            }
        }
    }
    private static int getOption() {
        Scanner sc = new Scanner(System.in);
        int op = -1;
        try {
            op = Integer.parseInt(sc.next());
            if ((currentScreen == 0 && op > 5) || (currentScreen == 1 && op > 3)) {
                System.out.println("Opción incorecta");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Opción incorrecta");
        }
        return op;
    }
    private static void printMenu() {
        if (currentScreen == 0) {
            System.out.println("0 Salir | 1 Información tienda | 2 Nueva tienda | 3 Información empleados | 4 Alta empleado | 5 Familiares empleados");
        } else {
            System.out.println("0 Volver | 1 Empleado | 2 Jefe");
        }
    }
    private static void infoTienda() throws SQLException{
        PreparedStatement st = null;
        String query= "SELECT t.id, t.dirección, t.nombre FROM tienda t;";
        st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            System.out.println("Id: " + rs.getInt("id") + " Dirección: " + rs.getString("dirección") + " Nombre: " + rs.getString("nombre"));
        }
    }

    private static void nuevaTienda() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Dirección: ");
        String dirección = sc.nextLine();
        String query = "insert into tienda(dirección, nombre) values (?, ?)";
        st = con.prepareStatement(query);
        st.setString(1,dirección);
        st.setString(2,nombre);
        st.executeUpdate();
    }
}
