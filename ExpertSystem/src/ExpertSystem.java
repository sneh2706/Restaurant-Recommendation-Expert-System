
import java.io.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.clipsrules.jni.*;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;





public class ExpertSystem extends Application {

    AnchorPane systemPane, root, main;
    //Input array
    static ArrayList<String> options = new ArrayList<>();;
    //Clips Environment where clips rule evalautes.
    static Environment clips;
    //array list of restaurant object(Restuarent,Place, Rating).
    static ArrayList<restaurant> answers = new ArrayList<>();
    String Ans1 = "", Ans2 = "", Ans3 = "";

    public static void getdata(){
        try {
            String get4 = options.get(4).substring(0, 1);
            int pp = Integer.parseInt(get4);
            get4 = Integer.toString(pp);
            String temp = "(assert (Restaurant (city " + options.get(0) + ") (category " + options.get(1) + ") (cuisine " + options.get(2) + ") (price " + options.get(3) + ") (rating " + pp + ")))";
            //.eval take a string which has already been formed by assert and is evaluated in the environment of clips.
            clips.eval(temp);
            //.run() fires the fact in the environment.
            clips.run();
            // get all the respective facts which has been fired.
            List<FactAddressValue> people = clips.findAllFacts("Person");


            // get the comma seprated string to make a restuarent object. and add to the list of restaurant.
            for(FactAddressValue fd : people){
                String ans = fd.getSlotValue("restaurantname").toString();
                ans = ans.substring(1,ans.length()-1);
                String temp1[] = ans.split(",");
                answers.add(new restaurant(temp1[0],temp1[1],temp1[2]));
            }
            //clear all input data because you have to use it again.
            people.clear();
            //reset the fact which has been called just before.
            clips.reset();
        } catch (CLIPSException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args){
        try {
            clips=new Environment();
            clips.load("rules.CLP");
        } catch (CLIPSLoadException ex) {
            Logger.getLogger(ExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        launch(args);
    }


    /* for reading csv and making rules */

    private static void loadCLP() {
        PrintStream fileStream = null;
        try {
            fileStream = new PrintStream("rules.CLP");
            System.setOut(fileStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("(deftemplate Person\n" +
                "    (multislot restaurantname)\n" +
                ")\n" +
                "\n" +
                "(deftemplate Restaurant\n" +
                "    (slot city)\n" +
                "    (slot category)\n" +
                "    (slot cuisine)\n" +
                "    (slot price)\n" +
                "    (slot rating)\n" +
                ")");


        ArrayList<input> rules = new ArrayList<>();
        File file = new File("restaurantDATA.csv");
        try{
            Scanner scfile = new Scanner(file);
            int i = 0;
            while(scfile.hasNextLine()){
                if (i == 0){
                    i++;
                } else {
                    String record = scfile.nextLine();
                    String[] value = record.split(",");
                    if (value[0].equals("RESTAURANT NAME")){
                        continue;
                    } else {
                        rules.add(new input(value[0], value[1], value[2], value[3], value[4], value[5], value[6]));
                    }
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        for (int i=0;i<rules.size();i++){
            System.out.println();
            System.out.println("(defrule " + rules.get(i).getName() + "\n" +
                    "    (Restaurant (city ?cit&" + rules.get(i).getCity() + ") (category ?cat&"+ rules.get(i).getType() + ") (cuisine ?cui&"+ rules.get(i).getCuisine() + ") (price ?pri&" + rules.get(i).getPrice()+ ") (rating ?rat))\n" +
                    "    (test (>= (str-compare \"" + rules.get(i).getRating() + "\" (str-cat ?rat)) 0))\n" +
                    "    =>\n" +
                    "    (assert (Person (restaurantname " + rules.get(i).getName() + "," + rules.get(i).getAddress() + "," + rules.get(i).getRating() + ")))\n" +
                    ")");
        }
        fileStream.close();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new AnchorPane();
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    primaryStage.close();
                }
                if (key == KeyCode.ENTER){
                    main.setVisible(false);
                    systemPane.setVisible(true);
                }
            }
        });

        for (int i=0;i<5;i++){
            options.add("");
        }
        addMainPane(primaryStage);
        addSystemPane(primaryStage);

        main.setVisible(true);
        systemPane.setVisible(true);

        root.getChildren().addAll(systemPane, main);

        primaryStage.setTitle("Recommendation System");
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void addMainPane(Stage primaryStage) {
        main = new AnchorPane();
        String style = "-fx-background-color: #a6b4c2;";
        String headStyle = "-fx-text-fill: #00B1D2FF;-fx-stroke: black; -fx-stroke-width: 1;";

        main.setStyle(style);
        main.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        main.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        main.setTranslateX(0);
        main.setTranslateY(0);

        FileInputStream inputstream1 = null;
        Image image3, image4, image5;
        ImageView imageView3 = null, imageView4 = null, imageView5 = null;
        try {
            image3 = new Image(new FileInputStream("./res.jpg"));
            imageView3 = new ImageView(image3);
            imageView3.setX(0);
            imageView3.setY(0);
            imageView3.setFitHeight(Screen.getPrimary().getBounds().getHeight());
            imageView3.setFitWidth(Screen.getPrimary().getBounds().getWidth());

            image4 = new Image(new FileInputStream("./iiita.png"));
            imageView4 = new ImageView(image4);
            imageView4.setX(80);
            imageView4.setY(10);
            imageView4.setFitHeight(200);
            imageView4.setFitWidth(200);

            image5 = new Image(new FileInputStream("./20y.jpg"));
            imageView5 = new ImageView(image5);
            imageView5.setX(Screen.getPrimary().getBounds().getWidth()-417);
            imageView5.setY(15);
            imageView5.setFitHeight(220);
            imageView5.setFitWidth(392);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Group root3 = new Group(imageView3);
        Group root4 = new Group(imageView4);
        Group root5 = new Group(imageView5);



        Label l1 = new Label("Restaurant Recommendation System");
        l1.setTranslateX((Screen.getPrimary().getBounds().getWidth())/2 - 520);
        l1.setTranslateY(60);
        l1.setFont(Font.font("Areal", FontWeight.BOLD, 50));
        l1.setStyle(headStyle);

        FileInputStream inputstream = null;
        Image image, image1;
        ImageView imageView = null, imageView1 = null;
        try {
            image = new Image(new FileInputStream("./anupam.jpg"));
            imageView = new ImageView(image);
            imageView.setX(50);
            imageView.setY(400);
            imageView.setFitHeight(350);
            imageView.setFitWidth(350);

            image1 = new Image(new FileInputStream("./jana.png"));
            imageView1 = new ImageView(image1);
            imageView1.setX(550);
            imageView1.setY(400);
            imageView1.setFitHeight(350);
            imageView1.setFitWidth(350);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Group root = new Group(imageView);
        Group root1 = new Group(imageView1);


        Label l6 = new Label("Project under supervision of:");
        l6.setTranslateX(55);
        l6.setTranslateY(300);
        l6.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        l6.setStyle(headStyle);


        Label l2 = new Label("Dr. Anupam Agrawal");
        l2.setTranslateX(55);
        l2.setTranslateY(770);
        l2.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        l2.setStyle(headStyle);

        Label l3 = new Label("Mr. Gopal Chandra Jana");
        l3.setTranslateX(555);
        l3.setTranslateY(770);
        l3.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        l3.setStyle(headStyle);

        Label l4 = new Label("Project by:\n1. Aditya Raj - IIT2017005\n2. Sameer Kathal - IIT2017010\n3. Aman Saxena - IIT2017021\n4. Nishant Kumar - iit2017023\n5. Sneh Sameer - IIT2017028\n6.Aditi Agrawal - IIT2017030\n7. Divyanshu Abhishek - IIT2017036\n8. Manish Kumar Jagnani - IIT2017503");
        l4.setTranslateX(1250);
        l4.setTranslateY(450);
        l4.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        l4.setStyle(headStyle);

        Label l5 = new Label("Press Enter to Continue...\nPress Escape to Exit...");
        l5.setTranslateX(50);
        l5.setTranslateY(Screen.getPrimary().getBounds().getHeight() - 100);
        l5.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        l5.setStyle(headStyle);

        Button load = new Button("Load CSV");
        load.setTranslateX(Screen.getPrimary().getBounds().getWidth()-250);
        load.setTranslateY(Screen.getPrimary().getBounds().getHeight() - 100);
        load.setPrefWidth(200);
        load.setPrefHeight(50);
        load.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadCLP();
                try {
                    clips.clear();
                    clips.reset();
                    clips.load("rules.CLP");
                } catch (CLIPSLoadException e) {
                    e.printStackTrace();
                } catch (CLIPSException e) {
                    e.printStackTrace();
                }
            }
        });



        main.getChildren().addAll(root3, root, root1, root4);
        main.getChildren().addAll(load);
        main.getChildren().addAll(l1, l2, l3, l4, l5, l6);
        main.setVisible(false);
    }

    private void addSystemPane(Stage primaryStage) {
        systemPane = new AnchorPane();
        String style = "-fx-background-color: #a6b4c2;";
        String headStyle = "-fx-text-fill: #000000; -fx-stroke: black; -fx-stroke-width: 1;";

        systemPane.setStyle(style);
        systemPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        systemPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        systemPane.setTranslateX(0);
        systemPane.setTranslateY(0);

        FileInputStream inputstream1 = null;
        Image image3;
        ImageView imageView3 = null;
        try {
            image3 = new Image(new FileInputStream("./res.jpg"));
            imageView3 = new ImageView(image3);
            imageView3.setX(0);
            imageView3.setY(0);
            imageView3.setFitHeight(Screen.getPrimary().getBounds().getHeight());
            imageView3.setFitWidth(Screen.getPrimary().getBounds().getWidth());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Group root3 = new Group(imageView3);



        Label l1 = new Label("Restaurant Recommendation System");
        l1.setTranslateX((Screen.getPrimary().getBounds().getWidth())/2 - 520);
        l1.setTranslateY(60);
        l1.setFont(Font.font("Areal", FontWeight.BOLD, 50));
        l1.setStyle(headStyle);






        Label o1, o2, o3, o4, o5;

        Label tab = new Label();
        tab.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2);
        tab.setTranslateY(220);
        tab.setFont(Font.font("Areal", FontWeight.BOLD, 25));
//        tab.setStyle("-fx-text-fill: #00B1D2FF");

        Label tab1 = new Label();
        tab1.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2 + 350);
        tab1.setTranslateY(220);
        tab1.setFont(Font.font("Areal", FontWeight.BOLD, 25));
//        tab1.setStyle("-fx-text-fill: #00B1D2FF");

        Label tab2 = new Label();
        tab2.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2 + 700);
        tab2.setTranslateY(220);
        tab2.setFont(Font.font("Areal", FontWeight.BOLD, 25));
//        tab2.setStyle("-fx-text-fill: #00B1D2FF");


        Label table = new Label();
        table.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2);
        table.setTranslateY(270);
        table.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        table.setStyle("-fx-text-fill: #00B1D2FF");

        Label table1 = new Label();
        table1.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2 + 350);
        table1.setTranslateY(270);
        table1.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        table1.setStyle("-fx-text-fill: #00B1D2FF");

        Label table2 = new Label();
        table2.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2 + 700);
        table2.setTranslateY(270);
        table2.setFont(Font.font("Areal", FontWeight.BOLD, 25));
        table2.setStyle("-fx-text-fill: #00B1D2FF");

        Button done = new Button("Done");
        done.setTranslateX(Screen.getPrimary().getBounds().getWidth()/2 - 200);
        done.setTranslateY(830);
        done.setPrefWidth(200);
        done.setPrefHeight(50);
        done.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean flag = true;
                for (int i=0;i<5;i++){
                    if (options.get(i).equals("")){
                        flag = false;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Wrong Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select all categories");
                        alert.showAndWait();
                        break;
                    }
                }
                if (flag) {
                    for (int i = 0; i < 5; i++) {
                        String temp[] = options.get(i).split("\\'");
                        if (temp.length > 1){
                            options.set(i, temp[1].replaceAll(" ", ""));
                        }
//                        answers.add(new restaurant(options.get(i), ""));
                    }
                    answers.removeAll(answers);

                    //function for restro
                    getdata();

                    tab.setText("Restaurant Name");
                    tab1.setText("Address");
                    tab2.setText("Rating");
                    Ans1 = "";
                    Ans2 = "";
                    Ans3 = "";
                    for (int i=0;i<answers.size();i++){
                        Ans1 += answers.get(i).getName().trim() + "\n";
                        Ans2 += answers.get(i).getAddress().trim() + "\n";
                        Ans3 += "   " + answers.get(i).getRating() + "\n";
                    }
                    table.setText(Ans1);
                    table1.setText(Ans2);
                    table2.setText(Ans3);

                }
            }
        });


        o1 = new Label("City :");
        o1.setTranslateX(150);
        o1.setTranslateY(200);
        o1.setFont(Font.font("Areal", FontWeight.BOLD, 25));

        ToggleGroup group1 = new ToggleGroup();
        RadioButton a1 = new RadioButton("Allahabad");
        a1.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        a1.setTranslateX(150);
        a1.setTranslateY(250);
        a1.setToggleGroup(group1);
        RadioButton a2 = new RadioButton("Lucknow");
        a2.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        a2.setTranslateX(350);
        a2.setTranslateY(250);
        a2.setToggleGroup(group1);
        final String[] optionSelected1 = {""};
        group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group1.getSelectedToggle() != null) {
                    options.set(0, group1.getSelectedToggle().toString());
                    optionSelected1[0] = group1.getSelectedToggle().toString();
                }
            }
        });


        o2 = new Label("Food Type :");
        o2.setTranslateX(150);
        o2.setTranslateY(350);
        o2.setFont(Font.font("Areal", FontWeight.BOLD, 25));

        ToggleGroup group2 = new ToggleGroup();
        RadioButton b1 = new RadioButton("Veg");
        b1.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        b1.setTranslateX(150);
        b1.setTranslateY(400);
        b1.setToggleGroup(group2);
        RadioButton b2 = new RadioButton("Non-Veg");
        b2.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        b2.setTranslateX(350);
        b2.setTranslateY(400);
        b2.setToggleGroup(group2);
        final String[] optionSelected2 = {""};
        group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group2.getSelectedToggle() != null) {
                    options.set(1, group2.getSelectedToggle().toString());
                    optionSelected2[0] = group2.getSelectedToggle().toString();
                }
            }
        });


        o3 = new Label("Cuisine :");
        o3.setTranslateX(150);
        o3.setTranslateY(500);
        o3.setFont(Font.font("Areal", FontWeight.BOLD, 25));

        ToggleGroup group3 = new ToggleGroup();
        RadioButton c1 = new RadioButton("Indian");
        c1.setTranslateX(150);
        c1.setTranslateY(550);
        c1.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        c1.setToggleGroup(group3);
        RadioButton c2 = new RadioButton("Chinese");
        c2.setTranslateX(350);
        c2.setTranslateY(550);
        c2.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        c2.setToggleGroup(group3);
        RadioButton c3 = new RadioButton("Italian");
        c3.setTranslateX(550);
        c3.setTranslateY(550);
        c3.setToggleGroup(group3);
        c3.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        final String[] optionSelected3 = {""};
        group3.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group3.getSelectedToggle() != null) {
                    options.set(2, group3.getSelectedToggle().toString());
                    optionSelected3[0] = group3.getSelectedToggle().toString();
                }
            }
        });



        o4 = new Label("Price :");
        o4.setTranslateX(150);
        o4.setTranslateY(650);
        o4.setFont(Font.font("Areal", FontWeight.BOLD, 25));

        ToggleGroup group4 = new ToggleGroup();
        RadioButton d1 = new RadioButton("Under 150");
        d1.setTranslateX(150);
        d1.setTranslateY(700);
        d1.setToggleGroup(group4);
        d1.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        RadioButton d2 = new RadioButton("150-300");
        d2.setTranslateX(350);
        d2.setTranslateY(700);
        d2.setToggleGroup(group4);
        d2.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        RadioButton d3 = new RadioButton("Above 300");
        d3.setTranslateX(550);
        d3.setTranslateY(700);
        d3.setToggleGroup(group4);
        d3.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        final String[] optionSelected4 = {""};
        group4.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group4.getSelectedToggle() != null) {
                    options.set(3, group4.getSelectedToggle().toString());
                    optionSelected4[0] = group4.getSelectedToggle().toString();
                }
            }
        });



        o5 = new Label("Rating :");
        o5.setTranslateX(150);
        o5.setTranslateY(800);
        o5.setFont(Font.font("Areal", FontWeight.BOLD, 25));

        ToggleGroup group5 = new ToggleGroup();
        RadioButton e1 = new RadioButton("1 Star");
        e1.setTranslateX(150);
        e1.setTranslateY(850);
        e1.setToggleGroup(group5);
        e1.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        RadioButton e2 = new RadioButton("2 Star");
        e2.setTranslateX(350);
        e2.setTranslateY(850);
        e2.setToggleGroup(group5);
        e2.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        RadioButton e3 = new RadioButton("3 Star");
        e3.setTranslateX(550);
        e3.setTranslateY(850);
        e3.setToggleGroup(group5);
        e3.setStyle("-fx-text-fill: black; -fx-font-family:\"Arial\"; -fx-font-size:20px;");
        final String[] optionSelected5 = {""};
        group5.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group5.getSelectedToggle() != null) {
                    options.set(4, group5.getSelectedToggle().toString());
                    optionSelected5[0] = group5.getSelectedToggle().toString();
                }
            }
        });




        systemPane.getChildren().addAll(root3);
        systemPane.getChildren().addAll(table, table1, table2);
        systemPane.getChildren().addAll(tab, tab1, tab2);
        systemPane.getChildren().addAll(o1, o2, o3, o4, o5, l1);
        systemPane.getChildren().addAll(a1, a2);
        systemPane.getChildren().addAll(b1, b2);
        systemPane.getChildren().addAll(c1, c2, c3);
        systemPane.getChildren().addAll(d1, d2, d3);
        systemPane.getChildren().addAll(e1, e2, e3);
        systemPane.getChildren().addAll(done);

        systemPane.setVisible(false);
    }
}
