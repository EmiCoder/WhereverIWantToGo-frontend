package com.example.frontend.service;

import com.example.frontend.database.DbManager;
import com.example.frontend.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterSavingService {

    private static int userId;

    public static void addRegisterToDatabase(User user) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Statement statement = dbManager.getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + user.getNick() + "'" + " and EMAIL = " + "'" + user.getEMail() + "'");
        resultSet.next();
        if (resultSet.isLast()) {
            userId = Integer.parseInt(resultSet.getString(1));
        }

        statement.execute("insert into registers (USER_ID, REGISTER_DATE, REGISTER_TIME) values " +
                "(" + userId +","  +
                "'" + setDate() + "'" + ", " +
                "'" + setTime() + "'" +")");
        statement.close();
    }

    private static String setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }

    private static String setTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(calendar.getTime());
    }
}
