package com.example.frontend.main;
import com.example.frontend.database.DbManager;
import com.example.frontend.domain.RequestForm;
import com.example.frontend.domain.ResponseCity;
import com.example.frontend.domain.dto.LoginData;
import com.example.frontend.service.CitiesService;
import com.example.frontend.domain.UserRequest;
import com.example.frontend.service.LogSavingService;
import com.example.frontend.web.GoogleShowMethod;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import sun.rmi.runtime.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Route
public class MainView extends VerticalLayout {

    private CitiesService citiesService = new CitiesService();
    private RequestForm requestForm = new RequestForm();


    private Button searchButton = new Button("Search");
    private Button loginButton = new Button("Log in");
    private Button registerButton = new Button("Register");
    private Button clearButton = new Button("Try again");
    private Button logoutButton = new Button("Log out");

    private UserRequest userRequest = new UserRequest();
    private LoginData loginData = new LoginData();
    private int userId;


    private HorizontalLayout mainContent;
    private VerticalLayout loginLayout;

    VerticalLayout verticalLayout;
    Grid<ResponseCity> grid;

    boolean searchButtonClicked = false;
    boolean temperatureComboBox = false;
    boolean monthComboBox = false;
    boolean countryComboBox = false;
    boolean sorryImagePrinted = false;
    boolean loginButtonPressed = false;
    boolean registerButtonPressed = false;
    boolean logged = false;
    boolean registered = false;
    boolean nickFilled = false;
    boolean passwordFilled = false;

    Image mainImageWorld =  new Image("img/wholeWorld.png", "Alternative text");
    Image sorryImage =  new Image("img/tryAgain.png", "Alternative text");

    private Label appTitle = new Label("Wherever U want 2 go....");
    private Label loggedLabel = new Label();
    private Label emptyLabel1 = new Label();
    private Label emptyLabel2 = new Label();
    private Label emptyLabel3 = new Label();
    private Label emptyLabel4 = new Label();

    private TextField nick;
    private TextField password;
    private Button logInButton = new Button("Log in");
    private Button forgotButton = new Button("Forgot password");
    private Label title = new Label("LOG IN");

    public MainView() throws SQLException {
        verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("40%");
        mainContent = new HorizontalLayout();
        mainContent.setSizeFull();

        mainImageWorld.setHeight("85%");
        sorryImage.setHeight("60%");

//        if (!logged && !registered) {
            loginButton.setWidth("60%");
            loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            loginButton.addClickListener(event -> {
                verticalLayout.remove(loginButton, registerButton);
                verticalLayout.add(title, nick, password, logInButton, forgotButton);
                loginButtonPressed = true;
            });
            registerButton.setWidth("60%");
            registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            registerButton.addClickListener(event -> {
                registerButtonPressed = true;
            });

            verticalLayout.add(appTitle, loginButton, registerButton);
//        }

        logoutButton.setWidth("60%");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logoutButton.addClickListener(event -> {
            verticalLayout.remove(requestForm, searchButton, clearButton, emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4, loggedLabel, logoutButton);
            verticalLayout.add(appTitle, loginButton, registerButton);
            loginData.setNick(new StringBuilder(""));
            loginData.setPassword(new StringBuilder(""));
        });
        requestForm.getTemperature().addValueChangeListener(event -> {
            temperatureComboBox = true;
            userRequest.setTemperature(event.getValue());
        });
        requestForm.getMonth().addValueChangeListener(event -> {
            monthComboBox = true;
            userRequest.setMonth(event.getValue());
        });
        requestForm.getCountry().addValueChangeListener(event -> {
            countryComboBox = true;
            userRequest.setCountryName(event.getValue());
        });

        searchButton.setWidth("60%");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        clearButton.setWidth("60%");
        clearButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        nick = new TextField("Nick");
//        nick.setValueChangeMode(ValueChangeMode.EAGER);
        nick.setWidth("60%");
        nick.addValueChangeListener(e -> {
            nickFilled = true;
            loginData.setNick(new StringBuilder(e.getValue()));
        });
        password = new TextField("Password");
//        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.setWidth("60%");
        password.addValueChangeListener(e -> {
            passwordFilled = true;
            loginData.setPassword(new StringBuilder(e.getValue()));
        });
        logInButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logInButton.setWidth("60%");
        logInButton.addClickListener(e -> {
            System.out.println("klikam");
//            if (nickFilled && passwordFilled) {
//                System.out.println("jestem za ifem");
                try {
                    System.out.println("jestem za try");
                    System.out.println(loginData.getNick());
                    System.out.println(loginData.getPassword());
                    System.out.println("");
                    DbManager dbManager = DbManager.getInstance();
                    Statement statement = dbManager.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + loginData.getNick() + "'" + " and EMAIL_PASSWORD = " + "'" + loginData.getPassword() + "'");
                    resultSet.next();

                        if (resultSet.isLast()) {
                            userId = Integer.parseInt(resultSet.getString(1));
                            verticalLayout.remove(title, nick, password, logInButton, forgotButton);
                            loggedLabel.setText("Logged as " + loginData.getNick());
                            loggedLabel.setWidth("60%");
                            verticalLayout.add(requestForm, searchButton, clearButton, emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4, loggedLabel, logoutButton);
                            LogSavingService.saveLog(userId);
                            logged = true;
                        }

                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                nickFilled = false;
                passwordFilled = false;
//            }
        });

        forgotButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        forgotButton.setWidth("60%");


        mainContent.add(verticalLayout, mainImageWorld);


        add(mainContent);
        setSizeFull();
        setAlignSelf(Alignment.CENTER);
    }


    private void showResponseCitiesList() throws SQLException, InterruptedException {
        mainContent.add(grid);
        grid.setItems(citiesService.getCitiesToGo(userRequest));
        grid.addComponentColumn(this::button).setHeader("GoogleMaps");
    }

    private Button button(ResponseCity responseCity) {
        Button button = new Button("Show on maps");
        button.addClickListener(e -> GoogleShowMethod.show(responseCity));
        return button;
    }

    private void addToDatabase() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Statement statement = dbManager.getConnection().createStatement();
        statement.execute("insert into user_requests (TEMPERATURE, MTH, COUNTRY) values " +
                "(" + "'" + userRequest.getTemperature() + "'" + ","  +
                "'" + userRequest.getMonth() + "'" + "," +
                "'" + userRequest.getCountryName() + "'" + ")");
        statement.close();
    }


}
