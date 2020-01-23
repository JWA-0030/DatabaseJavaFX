package databasejavafx;

import dataModel.FilmDAO;
import inputOutput.ConnectionData;
import inputOutput.PostgreSQLConnect;
import inputOutput.XmlParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author jesse
 */
public class DatabaseJavaFx extends Application 
{
    //instatiating member variables
    private static Logger logger = Logger.getLogger(DatabaseJavaFx.class.getName());
    private ObservableList<FilmDAO> data = FXCollections.observableArrayList();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        
//creates new Table display 
        TableView tableView = new TableView();
        tableView.setEditable(false);
        final Label label = new Label("Films");
        label.setFont(new Font("Arial", 20));
//creates new Column Title for overhead
        TableColumn title = new TableColumn("Title");
        title.setMinWidth(200);
        title.setCellValueFactory(
                new PropertyValueFactory<FilmDAO, String>("filmName"));
//sets name for the columns                
        TableColumn description = new TableColumn("Description");
        description.setMinWidth(400);
        description.setCellValueFactory(
                new PropertyValueFactory<FilmDAO, String>("filmDescription")); 
            
        TableColumn rental_rate = new TableColumn("Rental Rate");
        title.setMinWidth(100);
        title.setCellValueFactory(
                new PropertyValueFactory<FilmDAO, Double>("filmPrice"));
        
        TableColumn rating = new TableColumn("Rating");
        title.setMinWidth(100);
        title.setCellValueFactory(
                new PropertyValueFactory<FilmDAO, String>("filmRating"));
//adds all the create columns to the table in view 
        tableView.getColumns().addAll(title, description, rental_rate, rating);
 //adds button to fetch the films from the database       
        final Button fetchData = new Button("Fetch films from database");
        
//creates the event of the button click       
        fetchData.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) 
            {
                fetchData(tableView);
            }
        });
//creates  the background for the graph
        Scene scene = new Scene(new Group());
  
        final VBox vbox = new VBox();
        vbox.setPrefHeight(500);
        vbox.setPrefWidth(900);
//sets background dimensions        
        vbox.setStyle("-fx-background-color: grey; -fx-padding: 50;");
        vbox.getChildren().addAll(label, tableView);
//adds the database info to the graph
        ((Group) scene.getRoot()).getChildren().addAll(vbox, fetchData);
        
        stage.setTitle("Films for Rent");
        stage.setScene(scene);
        stage.show();
}
    
//fetches the data from the database    
    private void fetchData(TableView tableView) {
//try block to get the connection
        try (Connection con = getConnection())
        {
            tableView.setItems(fetchFilms(con));
        }
        //if fails it shows the error information
        catch (SQLException | ClassNotFoundException ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
 // trys to connect to the Database   
    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
//outputs info to screen to show progress    
        logger.info("Getting a database connection");
//creates new instance of the connection        
        XmlParser xml = new XmlParser("inputOutput/properties.xml");
        ConnectionData data = xml.getConnectionData();
        
        PostgreSQLConnect connect = new PostgreSQLConnect(data);
        Connection dbConnect = connect.getConnection();
        
        return dbConnect;
    }
// fetches the films from the database    
    private ObservableList<FilmDAO> fetchFilms(Connection con) throws SQLException
    {
        logger.info("Fetching films from database");
        ObservableList<FilmDAO> films = FXCollections.observableArrayList();
 //outputs information to select a certain node     
        String select = "select title, description, rental_rate, rating " +
                        "from film " +
                        "order by title;";
        
        logger.info("Select statement " + select);
//records statement        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(select);
//gos through the array till the film asked for is found        
        while (rs.next())
        {
            
            FilmDAO film = new FilmDAO(); 
            film.setFilmName(rs.getString("title"));
            film.setFilmRating(rs.getString("rating"));
            film.setFilmDescription(rs.getString("description"));
            film.setFilmPrice(rs.getDouble("rental_rate"));
            films.add(film);            
        }
 //returns info to user       
        logger.info("Found " + films.size() + "film ");
        
        return films; 
    }
        

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }
    /**
     * @param aLogger the logger to set
     */
    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

    /**
     * @return the data
     */
    public ObservableList<FilmDAO> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(ObservableList<FilmDAO> data) {
        this.data = data;
    }
}