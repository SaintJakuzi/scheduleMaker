package com.hammak;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PreView extends GridPane {

    private String DAY_TITLE_COLOR_CODE = "#ffd966";
    @FXML
    TabPane preViewTable;
    @FXML
    GridPane root;

    Semester current;


    public void setColor(String colorCode){
        DAY_TITLE_COLOR_CODE = colorCode;
    }

    public PreView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void fillWeek(VBox weekBox, Week week) {
        for (int i = 0; i < week.daysAmount(); i++) {
            if (!week.getDay(i).isEmpty()) {
                weekBox.getChildren().add(fillDay(week.getDay(i)));
            }
        }
    }

     GridPane fillDay(Day day) {
        GridPane dayContainer = new GridPane();
        ColumnConstraints numberColumn = new ColumnConstraints();
        ColumnConstraints pairColumn = new ColumnConstraints();
        ColumnConstraints subjectColumn = new ColumnConstraints();
        ColumnConstraints typeColumn = new ColumnConstraints();
        ColumnConstraints teacherColumn = new ColumnConstraints();
        ColumnConstraints pairHallNumberColumn = new ColumnConstraints();

        numberColumn.setPercentWidth(4.5);
        pairColumn.setPercentWidth(12);
        subjectColumn.setPercentWidth(48);
        typeColumn.setPercentWidth(4.5);
        teacherColumn.setPercentWidth(24);
        pairHallNumberColumn.setPercentWidth(7);

        dayContainer.getColumnConstraints().setAll(numberColumn, pairColumn, subjectColumn, typeColumn, teacherColumn, pairHallNumberColumn);

        Label titleLabel = new Label(ExcelPrinter.getdayTitle(day));
        titleLabel.getStyleClass().add("informationLabel");
        StackPane title = new StackPane(titleLabel);
        title.setPrefHeight(20);
        title.getStyleClass().add("informationBox");
        title.setStyle("-fx-background-color: " + DAY_TITLE_COLOR_CODE);
        dayContainer.add(title, 0, 0, 6, 1);


        for (int i = 0; i < day.pairsAmount(); i++) {
            if (!day.getPair(i).isEmpty()) {
                fillPair(day.getPair(i), dayContainer, i + 1);
            }
        }
        return dayContainer;
    }

    private void fillPair(Pair pair, GridPane pairContainer, int rowIndex) {
        Label pairNumber = new Label(pair.getNumber() + "");
        StackPane numberBox = new StackPane(pairNumber);

        Label pairTime = new Label(String.format("%02d:%02d-%02d:%02d", pair.getStartTime().getHour(),
                pair.getStartTime().getMinute(),
                pair.getStartTime().plusMinutes(80).getHour(),
                pair.getStartTime().plusMinutes(80).getMinute()));
        StackPane pairTimeBox = new StackPane(pairTime);

        Label pairSubject = new Label(pair.getSubject());
        StackPane subjectBox = new StackPane(pairSubject);

        Label pairType = new Label(pair.getType() + "");
        StackPane typeBox = new StackPane(pairType);

        Label pairTeacher = new Label(pair.getTeacher());
        StackPane teacherBox = new StackPane(pairTeacher);

        Label pairHallNumber = new Label(pair.getLectureHallNumber() + "");
        StackPane hallNumberBox = new StackPane(pairHallNumber);

        pairNumber.getStyleClass().add("informationLabel");
        pairTime.getStyleClass().add("informationLabel");
        pairSubject.getStyleClass().add("informationLabel");
        pairType.getStyleClass().add("informationLabel");
        pairTeacher.getStyleClass().add("informationLabel");
        pairHallNumber.getStyleClass().add("informationLabel");

        numberBox.getStyleClass().add("informationBox");
        numberBox.setStyle("-fx-background-color: " + DAY_TITLE_COLOR_CODE);
        pairTimeBox.getStyleClass().add("informationBox");
        pairTimeBox.setStyle("-fx-background-color: " + DAY_TITLE_COLOR_CODE);
        subjectBox.getStyleClass().add("informationBox");
        typeBox.getStyleClass().add("informationBox");
        teacherBox.getStyleClass().add("informationBox");
        hallNumberBox.getStyleClass().add("informationBox");

        pairContainer.add(numberBox, 0, rowIndex);
        pairContainer.add(pairTimeBox, 1, rowIndex);
        pairContainer.add(subjectBox, 2, rowIndex);
        pairContainer.add(typeBox, 3, rowIndex);
        pairContainer.add(teacherBox, 4, rowIndex);
        pairContainer.add(hallNumberBox, 5, rowIndex);
    }
    public void repaint(){
        if(current != null){
            fill(current);
        }
    }

    public void fill(Semester semester) {
        current = semester;
        preViewTable.getTabs().clear();
        for (int i = 0; i < semester.getWeeksAmount(); i++) {
            if (!semester.getWeek(i).isEmpty()) {
                VBox weekBox = new VBox();

                fillWeek(weekBox, semester.getWeek(i));

                ScrollPane scrolledContainer = new ScrollPane(weekBox);
                scrolledContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrolledContainer.setFitToWidth(true);
                Tab weekTab = new Tab();
                weekTab.setText(ExcelPrinter.getWeekTitleString(semester.getWeek(i)));
                weekTab.setContent(scrolledContainer);
                preViewTable.getTabs().add(weekTab);
            }
        }
    }
}
