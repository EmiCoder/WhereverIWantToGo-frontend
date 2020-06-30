package com.example.frontend.main;
import com.example.frontend.database.DbManager;
import com.example.frontend.database.STM;
import com.example.frontend.domain.*;
import com.example.frontend.domain.dto.LoginData;
import com.example.frontend.service.*;
import com.example.frontend.web.GoogleShowMethod;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Route
public class MainView extends VerticalLayout  {

    private static StringBuilder temperature;
    private static StringBuilder month;
    private static StringBuilder country;

    private HorizontalLayout mainContent;
    private VerticalLayout verticalLayout;
    private Grid<ResponseCity> grid;

    private CitiesService citiesService = new CitiesService();
    private ForgottenPasswordForm forgottenPasswordForm = new ForgottenPasswordForm();
    private RequestForm requestForm = new RequestForm();


    private Button searchButton = new Button("Search");
    private Button loginButton = new Button("Log in");
    private Button registerButton = new Button("Register");
    private Button clearButton = new Button("Try again");
    private Button logoutButton = new Button("Log out");
    private Button register = new Button("Register");
    private Button commitButton = new Button("Commit");
    private Button logInButton = new Button("Log in");
    private Button forgotButton = new Button("Forgot password");

    private Req req = new Req();
    private User user = new User();
    private User oldUser = new User();
    private ForgottenPasswordUser forgottenPasswordUser = new ForgottenPasswordUser();
    private static LoginData loginData = new LoginData();
    private int userId;

    boolean searchButtonClicked = false;
    boolean temperatureComboBox = false;
    boolean monthComboBox = false;
    boolean countryComboBox = false;
    boolean sorryImagePrinted = false;
    boolean forgottenPasswordFormNick = false;
    boolean forgottenPasswordFormFirstname = false;
    boolean forgottenPasswordFormLastname = false;
    boolean forgottenPasswordFormAge = false;
    boolean forgottenPasswordFormEmail = false;
    boolean gridAdded = false;


    Image mainImageWorld =  new Image("img/wholeWorld.png", "Alternative text");
    Image sorryImage =  new Image("img/tryAgain.png", "Alternative text");

    private Label appTitle = new Label("Wherever U want 2 go....");
    private Label loggedLabel = new Label();
    private Label emptyLabel1 = new Label();
    private Label emptyLabel2 = new Label();
    private Label emptyLabel3 = new Label();
    private Label emptyLabel4 = new Label();
    private Label logInTitle = new Label("LOG IN");
    private Label registerTitle = new Label("Register process");

    private TextField nick = new TextField("Nick");
    private TextField password = new TextField("Password");
    private TextField nickOfNewUser = new TextField("Nick");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField eMail = new TextField("e-mail");

    private ComboBox<Integer> age = new ComboBox<>("Age");


    public MainView() throws SQLException {
        age.setItems(generateAgeList());
        verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("40%");
        mainContent = new HorizontalLayout();
        mainContent.setSizeFull();

        mainImageWorld.setHeight("85%");
        sorryImage.setHeight("60%");

        forgottenPasswordForm.getNick().addValueChangeListener(event -> {
            forgottenPasswordUser.setNick(new StringBuilder(event.getValue()));
            forgottenPasswordFormNick = true;
        });
        forgottenPasswordForm.getFirstname().addValueChangeListener(event -> {
            forgottenPasswordUser.setFirstname(new StringBuilder(event.getValue()));
            forgottenPasswordFormFirstname = true;
        });
        forgottenPasswordForm.getLastname().addValueChangeListener(event -> {
            forgottenPasswordUser.setLastname(new StringBuilder(event.getValue()));
            forgottenPasswordFormLastname = true;
        });
        forgottenPasswordForm.getAge().addValueChangeListener(event -> {
            forgottenPasswordUser.setAge(event.getValue());
            forgottenPasswordFormAge = true;
        });
        forgottenPasswordForm.getEMail().addValueChangeListener(event -> {
            forgottenPasswordUser.setEMail(new StringBuilder(event.getValue()));
            forgottenPasswordFormEmail = true;
        });

        commitButton.setWidth("60%");
        commitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        commitButton.addClickListener(event -> {
            if (forgottenPasswordFormNick && forgottenPasswordFormFirstname && forgottenPasswordFormLastname
                && forgottenPasswordFormAge && forgottenPasswordFormEmail) {
                try {
                    user.setNick(forgottenPasswordUser.getNick());
                    user.setFirstname(forgottenPasswordUser.getFirstname());
                    user.setLastname(forgottenPasswordUser.getLastname());
                    user.setAge(forgottenPasswordUser.getAge());
                    user.setEMail(forgottenPasswordUser.getEMail());
                    if (UserExistingStatusChecking.isUserExisting(user)) {
                        try {
                            ForgotPasswordService.getPassword(forgottenPasswordUser);
                            verticalLayout.remove(forgottenPasswordForm, commitButton);
                            verticalLayout.add(loginButton, registerButton);
                            Notification.show("Password sent on your email");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Notification.show("User doesn't exist. Check fields are correct filled.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                Notification.show("Fill all fields");
            }
        });

        loginButton.setWidth("60%");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickListener(event -> {
            verticalLayout.remove(loginButton, registerButton);
            verticalLayout.add(logInTitle, nick, password, logInButton, forgotButton);
        });

        nickOfNewUser.setValueChangeMode(ValueChangeMode.EAGER);
        nickOfNewUser.setWidth("60%");
        nickOfNewUser.addValueChangeListener(e -> {
            user.setNick(new StringBuilder(e.getValue()));
        });
        firstname.setValueChangeMode(ValueChangeMode.EAGER);
        firstname.setWidth("60%");
        firstname.addValueChangeListener(e -> {
            user.setFirstname(new StringBuilder(e.getValue()));
        });
        lastname.setValueChangeMode(ValueChangeMode.EAGER);
        lastname.setWidth("60%");
        lastname.addValueChangeListener(e -> {
            user.setLastname(new StringBuilder(e.getValue()));
        });
        age.setWidth("60%");
        age.addValueChangeListener(e -> {
            user.setAge(e.getValue());
        });
        eMail.setValueChangeMode(ValueChangeMode.EAGER);
        eMail.setWidth("60%");
        eMail.addValueChangeListener(e -> {
            user.setEMail(new StringBuilder(e.getValue()));
        });
        register.setWidth("60%");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        register.addClickListener(event -> {

            try {
                if (!UserExistingStatusChecking.isUserExisting(user)) {
                    try {
                        UserSavingService.addUserToDatabase(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        RegisterSavingService.addRegisterToDatabase(user);
                        EmailSendingService.sendEmail(user);
                        Notification.show("Registration successful. You've got an uniqe password on your e-mail. Check it.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    verticalLayout.remove(registerTitle, nickOfNewUser, firstname, lastname, age, eMail, register);
                    verticalLayout.add(appTitle, loginButton, registerButton);
                } else {
                    Notification.show("This user is exist already");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        registerButton.setWidth("60%");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(event -> {
            verticalLayout.remove(loginButton, registerButton);
            verticalLayout.add(registerTitle, nickOfNewUser, firstname, lastname, age, eMail, register);
        });

        verticalLayout.add(appTitle, loginButton, registerButton);

        logoutButton.setWidth("60%");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logoutButton.addClickListener(event -> {
            verticalLayout.remove(requestForm, searchButton, clearButton, emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4, loggedLabel, logoutButton);
            verticalLayout.add(appTitle, loginButton, registerButton);
            if (gridAdded) {
                mainContent.remove(grid);
            }
            mainContent.add(mainImageWorld);
            gridAdded = false;
            try {
                LogoutService.saveLogout(userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        requestForm.getTemperature().addValueChangeListener(event -> {
            temperatureComboBox = true;
            req.setTemperature(new StringBuilder(event.getValue()));
        });
        requestForm.getMonth().addValueChangeListener(event -> {
            monthComboBox = true;
            req.setMonth(new StringBuilder(event.getValue()));
        });
        requestForm.getCountry().addValueChangeListener(event -> {
            countryComboBox = true;
            req.setCountry(new StringBuilder(event.getValue()));
        });

        searchButton.setWidth("60%");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickListener(event -> {
            if (!searchButtonClicked && temperatureComboBox && monthComboBox && countryComboBox) {
                try {

                    if (citiesService.getCitiesToGo(req).size() != 0) {
                        Request request = new Request();
                        request.setUser(getOldUser(userId));
                        request.setTemperature(Integer.parseInt(req.getTemperature().toString()));
                        request.setMonth(req.getMonth().toString());
                        request.setCountryName(req.getCountry().toString());
                        request.setRequestDate(DataSettings.setDate());

                        addToDatabase(request);
                        mainContent.remove(mainImageWorld);
                        grid = new Grid<>(ResponseCity.class);
                        grid.setSizeFull();
                        mainContent.add(grid);
                        gridAdded = true;
                        showResponseCitiesList();
                        searchButtonClicked = true;
                    } else {
                        mainContent.remove(mainImageWorld);
                        mainContent.add(sorryImage);
                        sorryImagePrinted = true;
                        searchButtonClicked = true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        clearButton.setWidth("60%");
        clearButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addClickListener(event -> {
            if (sorryImagePrinted) {
                mainContent.remove(sorryImage);
                mainContent.add(mainImageWorld);
                searchButtonClicked = false;
                requestForm.getCountry().setValue("");
                requestForm.getMonth().setValue("");
                requestForm.getTemperature().setValue("");
                temperatureComboBox = false;
                monthComboBox = false;
                countryComboBox = false;
                sorryImagePrinted = false;
            } else if (temperatureComboBox && monthComboBox && countryComboBox && searchButtonClicked) {
                mainContent.remove(grid);
                mainContent.add(mainImageWorld);
                requestForm.getCountry().setValue("");
                requestForm.getMonth().setValue("");
                requestForm.getTemperature().setValue("");
                temperatureComboBox = false;
                monthComboBox = false;
                countryComboBox = false;
                searchButtonClicked = false;
            }  else if ((!temperatureComboBox && monthComboBox && countryComboBox) ||
                    (!temperatureComboBox && !monthComboBox && countryComboBox) ||
                    (!temperatureComboBox && monthComboBox && !countryComboBox) ||
                    (!temperatureComboBox && !monthComboBox && !countryComboBox) ||
                    (temperatureComboBox && !monthComboBox && countryComboBox) ||
                    (temperatureComboBox && monthComboBox && !countryComboBox) ||
                    (temperatureComboBox && !monthComboBox && !countryComboBox)) {
                searchButtonClicked = false;
                requestForm.getCountry().setValue("");
                requestForm.getMonth().setValue("");
                requestForm.getTemperature().setValue("");
                temperatureComboBox = false;
                monthComboBox = false;
                countryComboBox = false;
            } else {
                searchButtonClicked = false;
                mainContent.remove(grid);
                mainContent.add(mainImageWorld);
                requestForm.getCountry().setValue("");
                requestForm.getMonth().setValue("");
                requestForm.getTemperature().setValue("");
                temperatureComboBox = false;
                monthComboBox = false;
                countryComboBox = false;
            }
        });

        nick.setValueChangeMode(ValueChangeMode.EAGER);
        nick.setWidth("60%");
        nick.addValueChangeListener(e -> {
            loginData.setNick(new StringBuilder(e.getValue()));
        });
        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.setWidth("60%");
        password.addValueChangeListener(e -> {
            loginData.setPassword(new StringBuilder(e.getValue()));
        });
        logInButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logInButton.setWidth("60%");
        logInButton.addClickListener(e -> {
                try {
                    DbManager dbManager = DbManager.getInstance();
                    Statement statement = dbManager.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + loginData.getNick() + "'" + " and EMAIL_PASSWORD = " + "'" + loginData.getPassword() + "'");
                    resultSet.next();

                        if (resultSet.isLast()) {
                            userId = Integer.parseInt(resultSet.getString(1));
                            oldUser = getOldUser(userId);
                            verticalLayout.remove(logInTitle, nick, password, logInButton, forgotButton);
                            loggedLabel.setText("Logged as " + loginData.getNick());
                            loggedLabel.setWidth("60%");
                            verticalLayout.add(requestForm, searchButton, clearButton, emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4, loggedLabel, logoutButton);
                            user.setId(userId);
                            LogSavingService.saveLog(userId);
                        } else {
                            Notification.show("Incorect Nick or Password. Try again.");
                        }

                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        });

        forgotButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        forgotButton.setWidth("60%");
        forgotButton.addClickListener(event -> {
            try {
                verticalLayout.remove(logInTitle, nick, password, logInButton, forgotButton);
                verticalLayout.add(forgottenPasswordForm,commitButton);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        mainContent.add(verticalLayout, mainImageWorld);


        add(mainContent);
        setSizeFull();
        setAlignSelf(Alignment.CENTER);
    }


    private void showResponseCitiesList() throws SQLException, InterruptedException {
        mainContent.add(grid);
        grid.setItems(citiesService.getCitiesToGo(req));
        grid.addComponentColumn(this::button).setHeader("GoogleMaps");
    }

    private Button button(ResponseCity responseCity) {
        Button button = new Button("Show on maps");
        button.addClickListener(e -> GoogleShowMethod.show(responseCity));
        return button;
    }

    private void addToDatabase(Request request) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Statement statement = dbManager.getConnection().createStatement();

        statement.execute("insert into requests (USER_ID, TEMPERATURE, MTH, COUNTRY, REQUEST_DATE) values " +
                                                                                        "(" + request.getUser().getId() + "," +
                                                                                         request.getTemperature() + ","  +
                                                                                        "'" + request.getMonth() + "'" + "," +
                                                                                        "'" + request.getCountryName() + "'" + "," +
                                                                                        "'" + request.getRequestDate() + "'" +")");
        statement.close();
    }

    private List<Integer> generateAgeList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        } return list;
    }

    private User getOldUser(int userId) throws SQLException {
        Statement statement = STM.getStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where ID = " +  userId);
        resultSet.next();
        if (resultSet.isLast()) {
            oldUser.setId(userId);
            oldUser.setNick(new StringBuilder(resultSet.getString(2)));
            oldUser.setFirstname(new StringBuilder(resultSet.getString(3)));
            oldUser.setLastname(new StringBuilder(resultSet.getString(4)));
            oldUser.setAge(Integer.parseInt(resultSet.getString(5)));
            oldUser.setEMail(new StringBuilder(resultSet.getString(6)));
            oldUser.setPassword(new StringBuilder(resultSet.getString(7)));
            return oldUser;
        }
        return null;
    }



}
