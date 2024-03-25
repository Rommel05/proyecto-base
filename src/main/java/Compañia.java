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
                    case 1: currentScreen = 1; break;
                    case 2: currentScreen = 2; break;
                    case 3: currentScreen = 5; break;
                }
            } else if (currentScreen == 1) {
                switch (op) {
                    case 1: currentScreen = 0; break;
                    case 2: infoTienda(); break;
                    case 3: nuevaTienda(); break;
                }
            } else if (currentScreen == 2) {
                switch (op) {
                    case 1: currentScreen = 0; break;
                    case 2: currentScreen = 3; break;
                    case 3: currentScreen = 4; break;
                }
            } else if (currentScreen == 3) {
                switch (op) {
                    case 1: currentScreen = 2; break;
                    case 2: infoJefe(); break;
                    case 3: nuevoJefe(); break;
                }
            } else if (currentScreen == 4) {
                switch (op) {
                    case 1: currentScreen = 2; break;
                    case 2: infoEmpleado(); break;
                    case 3: nuevoEmpleado(); break;
                }
            } else if (currentScreen == 5) {
                switch (op) {
                    case 1: currentScreen = 0; break;
                    case 2: infofamiliarJefe(); break;
                    case 3: infofamiliarempleado(); break;
                    case 4: nuevoFamiliarJefe(); break;
                    case 5: nuevoFamiliarEmpleado(); break;
                }
            }
        }
    }
    private static int getOption() {
        Scanner sc = new Scanner(System.in);
        int op = -1;
        try {
            op = Integer.parseInt(sc.next());
            if ((currentScreen == 0 && op > 4) || (currentScreen == 1 && op > 3) || (currentScreen == 2 && op > 3) || (currentScreen == 3 && op > 3) || (currentScreen == 4 && op > 3) || (currentScreen == 5 && op > 5)) {
                System.out.println("Opción incorecta");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Opción incorrecta");
        }
        return op;
    }
    private static void printMenu() {
        if (currentScreen == 0) {
            System.out.println("0 Salir | 1 Tiendas | 2 Empleados | 3 Familiares");
        } else if (currentScreen == 1) {
            System.out.println("1 Volver | 2 Información tiendas | 3 Nueva Tienda");
        } else if (currentScreen == 2){
            System.out.println("1 Volver | 2 Jefe | 3 Empleado");
        } else if (currentScreen == 3) {
            System.out.println("1 Volver | 2 Información jefe | 3 Nuevo jefe");
        } else if (currentScreen == 4) {
            System.out.println("1 Volver | 2 Información empleado | 3 Nuevo Empleado");
        } else if (currentScreen == 5) {
            System.out.println("1 Volver | 2 Infromación Familares jefes | 3 Infromación Familares empleados | 4 Nuevo familiar jefe | 5 Nuevo familiar empleado");
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

    private static void infoEmpleado() throws SQLException{
        PreparedStatement st = null;
        String query= "SELECT e.nombre, e.apellido, e.salario, e.id FROM empleado e;";
        st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            System.out.println("Id: " + rs.getInt("id") + " Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido") + " Salario: " + rs.getInt("salario"));
        }
    }

    private static void infoJefe() throws SQLException{
        PreparedStatement st = null;
        String query= "SELECT j.id_jefe, j.nombre, j.apellido, j.salario FROM jefe j;";
        st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            System.out.println("Id: " + rs.getInt("id_jefe") + " Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido") + " Salario: " + rs.getInt("salario"));
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

    private static void nuevoJefe() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Apellido: ");
        String apellido = sc.nextLine();
        System.out.println("Salario. ");
        int salario = sc.nextInt();
        System.out.println("Tienda a la que pertenece: ");
        int id = sc.nextInt();
        String query = "insert into jefe(nombre, apellido, salario, id) values (?, ?, ?, ?)";
        st = con.prepareStatement(query);
        st.setString(1,nombre);
        st.setString(2,apellido);
        st.setInt(3,salario);
        st.setInt(4,id);
        st.executeUpdate();
    }

    private static void nuevoEmpleado() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Apellido: ");
        String apellido = sc.nextLine();
        System.out.println("Salario. ");
        int salario = sc.nextInt();
        System.out.println("Jefe:");
        int jefe = sc.nextInt();
        System.out.println("Tienda a la que pertenece: ");
        int id = sc.nextInt();
        String query = "INSERT into empleado (nombre, apellido, salario, id, id_jefe) values(?, ?, ?, ? , ?);\n";
        st = con.prepareStatement(query);
        st.setString(1,nombre);
        st.setString(2,apellido);
        st.setInt(3,salario);
        st.setInt(4,id);
        st.setInt(5,jefe);
        st.executeUpdate();
    }

    private static void infofamiliarJefe() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        String query= "SELECT f.nombre, f.apellido FROM familiar f WHERE id_jefe is not null;";
        st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            System.out.println("Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
        }
    }

    private static void infofamiliarempleado() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        String query= "SELECT f.nombre, f.apellido FROM familiar f WHERE id_emp is not null;";
        st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            System.out.println("Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
        }
    }

    private static void nuevoFamiliarJefe() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        System.out.println("Dni:");
        String dni = sc.nextLine();
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Apellido:");
        String apellido = sc.nextLine();
        System.out.println("Jefe:");
        int jefe = sc.nextInt();
        String query = "INSERT into familiar (dni, nombre, apellido, id_jefe) values(?, ?, ?, ?)";
        st = con.prepareStatement(query);
        st.setString(1,dni);
        st.setString(2,nombre);
        st.setString(2,apellido);
        st.setInt(4,jefe);
        st.executeUpdate();
    }

    private static void nuevoFamiliarEmpleado() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        System.out.println("Dni:");
        String dni = sc.nextLine();
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Apellido:");
        String apellido = sc.nextLine();
        System.out.println("Empleado:");
        int empleado = sc.nextInt();
        String query = "INSERT into familiar (dni, nombre, apellido, id_emp) values(?, ?, ?, ?)";
        st = con.prepareStatement(query);
        st.setString(1,dni);
        st.setString(2,nombre);
        st.setString(3,apellido);
        st.setInt(4,empleado);
        st.executeUpdate();
    }

}