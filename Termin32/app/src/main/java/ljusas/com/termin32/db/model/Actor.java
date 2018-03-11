package ljusas.com.termin32.db.model;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Actor.TABLE_NAME_USERS)
public class Actor {

    public static final String TABLE_NAME_USERS = "actor";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_NAME   = "name";
    public static final String FIELD_NAME_SERNAME   = "sername";
    public static final String FIELD_NAME_CV   = "cv";
    public static final String FIELD_NAME_RATING   = "rating";
    public static final String FIELD_NAME_YEAR  = "year";
    public static final String FIELD_NAME_MOVIE = "movie";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String mName;

    @DatabaseField(columnName = FIELD_NAME_SERNAME)
    private String mSername;

    @DatabaseField(columnName = FIELD_NAME_CV)
    private String cv;

    @DatabaseField(columnName = FIELD_NAME_RATING)
    private float rating;

    @DatabaseField(columnName = FIELD_NAME_YEAR)
    private String year;

    @ForeignCollectionField(columnName = Actor.FIELD_NAME_MOVIE, eager = true)
    private ForeignCollection<Movie> movies;

    public Actor() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSername() {
        return mSername;
    }

    public void setmSername(String mSername) {
        this.mSername = mSername;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ForeignCollection<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ForeignCollection<Movie> movies) {
        this.movies = movies;
    }

    @Override
   public String toString() {
        String actor = mName + " " + mSername + mId;
        return actor;
    }
}
