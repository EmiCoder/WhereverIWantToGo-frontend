package com.example.frontend.domain;

import com.example.frontend.database.DbManager;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RequestForm extends FormLayout {

    private static final int MONTH = 12;
    private static final int MIN_TEMP = -20;
    private static final int MAX_TEMP = 40;

    private Label label = new Label("Wherever U want to go....");

    private ComboBox<String> temperature = new ComboBox<>("Favourite temperature");
    private ComboBox<String> month = new ComboBox<>("Favourite month");
    private ComboBox<String> country = new ComboBox<>("Favourite country");

    public RequestForm() throws SQLException {
        label.setHeight("500%");
        temperature.setItems(prepareTemperatures());
        month.setItems(prepareMonths());
        country.setItems(prepareCountry());
        add(label, temperature, month, country);
        setMaxWidth("60%");
    }


    private List<String> prepareMonths() {
        String[] months = new String[] {
                "JANUARY", "FEBRUARY", "MARCH", "APRIL",
                "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
                "OCTOBER", "NOVEMBER", "DECEMBER"};
        List<String> list = new ArrayList<>();
        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
        }
        return list;
    }

    private List<String> prepareTemperatures() {
        List<String> list = new ArrayList<>();
        for (int i = MIN_TEMP; i <= MAX_TEMP; i++) {
            list.add(String.valueOf(i));
        } return list;
    }

    private List<String> prepareCountry() throws SQLException {
        Statement statement = getStatement();
        ResultSet rs = statement.executeQuery("select COUNTRY_NAME from countries");
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        statement.close();
        return list;
    }

    private Statement getStatement() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        return dbManager.getConnection().createStatement();
    }
}
