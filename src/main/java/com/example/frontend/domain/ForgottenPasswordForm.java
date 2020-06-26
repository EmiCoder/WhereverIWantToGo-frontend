package com.example.frontend.domain;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ForgottenPasswordForm extends FormLayout {

    private VerticalLayout verticalLayout = new VerticalLayout();

    private TextField nick = new TextField("Nick");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private ComboBox<Integer> age = new ComboBox<>("Age");
    private TextField eMail = new TextField("e-mail");
    private Label title = new Label("Commit process");

    public ForgottenPasswordForm() {
        age.setItems(prepareAgeList());
        verticalLayout.setWidth("60%");
        verticalLayout.add(title, nick,firstname, lastname, age, eMail);
        add(verticalLayout);
        setMaxWidth("60%");
    }

    private List<Integer> prepareAgeList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        } return list;
    }
}
