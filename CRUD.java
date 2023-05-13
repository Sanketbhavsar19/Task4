package task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CRUD {
    private static Connection connect = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "6761");
            System.out.println("Connected to database");
            while (true) {
                System.out.println("Enter 1 to insert data");
                System.out.println("Enter 2 to update data");
                System.out.println("Enter 3 to delete data");
                System.out.println("Enter 4 to view data");
                System.out.println("Enter 5 to exit");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        insertData(scanner);
                        break;
                    case 2:
                        updateData(scanner);
                        break;
                    case 3:
                        deleteData(scanner);
                        break;
                    case 4:
                        viewData();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.close();
            }
        }
    }

    private static void insertData(Scanner scanner) throws SQLException {
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        preparedStatement = connect.prepareStatement("insert into users(name, age) values (?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.executeUpdate();
        System.out.println("Data inserted successfully");
    }

    private static void updateData(Scanner scanner) throws SQLException {
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        preparedStatement = connect.prepareStatement("update users set name=?, age=? where id=?");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
        System.out.println("Data updated successfully");
    }

    private static void deleteData(Scanner scanner) throws SQLException {
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        preparedStatement = connect.prepareStatement("delete from users where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Data deleted successfully");
    }

    private static void viewData() throws SQLException {
        preparedStatement = connect.prepareStatement("select * from users");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.printf("%d %s %d\n", id, name, age);
        }
    }
}
