package ljusas.com.termin32.db.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Movie.TABLE_NAME_USERS)
public class Movie {

    public static final String TABLE_NAME_USERS = "movie";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_NAME   = "name";
    public static final String FIELD_NAME_TYPE  = "type";
    public static final String FIELD_NAME_YEAR  = "year";
    public static final String FIELD_NAME_ACTOR = "actor";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int Id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String Name;

    @DatabaseField(columnName = FIELD_NAME_TYPE)
    private String type;

    @DatabaseField(columnName = FIELD_NAME_YEAR)
    private String year;

    @DatabaseField(columnName = FIELD_NAME_ACTOR, foreign = true, foreignAutoRefresh = true)
    private Actor mActor;

    public Movie() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Actor getmActor() {
        return mActor;
    }

    public void setmActor(Actor mActor) {
        this.mActor = mActor;
    }

    @Override
    public String toString() {
        String movie = Name + " " + type + " " + year;
        return movie;
    }
}
