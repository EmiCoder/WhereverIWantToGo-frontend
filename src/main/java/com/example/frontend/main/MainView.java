package com.example.frontend.main;
import com.example.frontend.database.DbManager;
import com.example.frontend.domain.RequestForm;
import com.example.frontend.domain.ResponseCity;
import com.example.frontend.service.CitiesService;
import com.example.frontend.domain.UserRequest;
import com.example.frontend.web.GoogleShowMethod;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;
import java.sql.Statement;


@Route
public class MainView extends VerticalLayout {

    private CitiesService citiesService = new CitiesService();
    private RequestForm requestForm = new RequestForm();
    private Button searchButton = new Button("Search");
    private UserRequest userRequest = new UserRequest();
    private HorizontalLayout mainContent;

    VerticalLayout verticalLayout;
    Grid<ResponseCity> grid;
    Button clearButton = new Button("Try again");
    boolean searchButtonClicked = false;
    boolean temperatureComboBox = false;
    boolean monthComboBox = false;
    boolean countryComboBox = false;
    boolean sorryImagePrinted = false;

    Image image =  new Image("img/wholeWorld.png", "Alternative text");
    Image sorryImage =  new Image("img/tryAgain.png", "Alternative text");

    public MainView() throws SQLException {
        image.setHeight("85%");
        sorryImage.setHeight("60%");

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
        searchButton.addClickListener(event -> {
            if (!searchButtonClicked && temperatureComboBox && monthComboBox && countryComboBox) {
                try {
                    if (citiesService.getCitiesToGo(userRequest).size() != 0) {
                        addToDatabase();
                        mainContent.remove(image);
                        grid = new Grid<>(ResponseCity.class);
                        grid.setSizeFull();
                        mainContent.add(grid);
                        showResponseCitiesList();
                        searchButtonClicked = true;
                    } else {
                        mainContent.remove(image);
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
                mainContent.add(image);
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
                mainContent.add(image);
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
                mainContent.add(image);
                requestForm.getCountry().setValue("");
                requestForm.getMonth().setValue("");
                requestForm.getTemperature().setValue("");
                temperatureComboBox = false;
                monthComboBox = false;
                countryComboBox = false;
            }
        });


        verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("40%");
        verticalLayout.add(requestForm, searchButton, clearButton);

        mainContent = new HorizontalLayout(verticalLayout, image);
        mainContent.setSizeFull();


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
