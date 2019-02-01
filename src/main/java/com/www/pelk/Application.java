package com.www.pelk;

import java.io.IOException;
import java.sql.*;

public class Application {

    public static void main(String[] args) throws IOException {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/<Ваша БД>", "<Ваша БД>", "<Ваша БД>");
            Statement st = connection.createStatement();
            String query = ("SELECT * FROM `wp_posts` WHERE `post_type` = 'houses'");
            ResultSet rs = st.executeQuery(query);


            while (rs.next()) {
                System.out.println(rs.getString("post_title"));
                String i = rs.getString("ID");
                String[] cmd = {"/bin/bash", "-c", "echo <пароль пользователя с правами sudo> | sudo -S xvfb-run -a wkhtmltopdf -L 0 -R 0 -B 0 https://<site>/print/?flat_id=" + i + " ./pdf/download/" + i + ".pdf"};
                Process pb = Runtime.getRuntime().exec(cmd);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
