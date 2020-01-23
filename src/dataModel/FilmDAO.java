package dataModel;

import java.util.logging.Logger;

/**
 *
 * @author jesse
 */
public class FilmDAO {
//member variables
    private String filmName;
    private String filmDescription;
    private String filmRating;
    private Double filmPrice;
// no argument constructor for logging information to user   
    private static final Logger logger = Logger.getLogger(FilmDAO.class.getName());
//no argument construct
    public FilmDAO()
    {
        
    }
 // constructor that recieve the previous member variables  
    public FilmDAO(String name, String rating, String description, Double rental_rate)
    {
        //creates new instances of the member variables.
        this.filmName = name;
        this.filmRating = rating;
        this.filmDescription = description;
        this.filmPrice = rental_rate;
    }

    /**
     * @return the filmName
     */
    public String getFilmName() {
        return filmName;
    }

    /**
     * @param filmName the filmName to set
     */
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    /**
     * @return the filmDescription
     */
    public String getFilmDescription() {
        return filmDescription;
    }

    /**
     * @param filmDescription the filmDescription to set
     */
    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    /**
     * @return the filmRating
     */
    public String getFilmRating() {
        return filmRating;
    }

    /**
     * @param filmRating the filmRating to set
     */
    public void setFilmRating(String filmRating) {
        this.filmRating = filmRating;
    }

    /**
     * @return the filmPrice
     */
    public Double getFilmPrice() {
        return filmPrice;
    }

    /**
     * @param filmPrice the filmPrice to set
     */
    public void setFilmPrice(Double filmPrice) {
        this.filmPrice = filmPrice;
    }
    
}