package ar.com.betianaminio.workingwithsqlite.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Betiana G. Miño on 09/06/2017.
 */

public class DBBuilder extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "MYSQLITE";

    private static final String DATABASE_NAME = "my_exercises";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE_EXERCISES = "exercises";
    private static final String QUERY_CREATE_TABLE_EXERCISES = "CREATE TABLE " + DATABASE_TABLE_EXERCISES + "( id INTEGER PRIMARY KEY, name VARCHAR(30), description VARCHAR(128), level TEXT CHECK( level IN ('Higher','Medium','Lower') ) NOT NULL DEFAULT 'Lower')" ;
    private static final String QUERY_DELETE_TABLE_EXERCISES = "DROP TABLE IF EXISTS " + DATABASE_TABLE_EXERCISES;
    private static final String QUERY_INSERT_DATA_INTO_TABLE_EXERCISES = "INSERT INTO " + DATABASE_TABLE_EXERCISES + "( id,name,description,level)" +
            "VALUES(NULL,'Standard Crunch','Lying flat on your back on the floor. You place your arms behind your head with your fingers behind your ears and bring your knees to a bent position with your feet flat on the floor. Lift your shoulders and head to the ceiling with your chin pointing towards the ceiling and not tucking your chin to your chest.','Lower')," +
            "(NULL,'Advanced crunch','Lying flat on your back and holding your legs straight up in air. Place your arms behind your head with your fingers behind your ears and lift your head towards the ceiling with your chin pointing towards the ceiling and not tucking your chin to your chest.','Medium')," +
            "(NULL,'Plank','Get into pushup position on the floor. Now bend your elbows 90 degrees and rest your weight on your forearms. Your elbows should be directly beneath your shoulders, and your body should form a straight line from your head to your feet. Hold the position for as long as you can. Your goal should be to hold it for two minutes.','Medium')," +
            "(NULL,'Knee Drive Plank','Start in an elbow plank, and bring your right knee into your nose; your pelvis will rise toward the ceiling. Place right foot back on the ground. Alternate sides, bringing your left knee in.','Medium')," +
            "(NULL,'Knee Tuck Kickout Slider','Start in high plank position with both feet on sliders, socks, paper, glides etc. or whatever makes you slide. With both feet on sliders, bring knees in toward chest and then slide both feet evenly to the right, making sure to engage your obliques. Finish with both feet outside of your right hand. Push back into a high plank and repeat on the other side.','Higher')," +
            "(NULL,'Burpee', 'Bend over and squat down. Place hands on floor, slightly wider than shoulder width. While holding upper body in place, kick legs back. Land on forefeet with body in straight, plank position. Perform pushup by lowering body to floor and back up. Keeping upper body in place, pull legs forward under body returning feet in original position. Jump up and land to original standing posture or repeat.', 'Higher')," +
            "(NULL,'Jump Rope: Boxer Step', 'Swing rope in front of body, down to floor. Hop up as rope travels under foot and land over to left side, left foot lands then right foot touches. Swing rope around body and hop over rope again landing to right side, right foot lands then left foot touches. Continue jumps hopping side to side.', 'Higher'), " +
            "(NULL,'Jump Rope: Regular Bounce','Swing rope in front of body, down to floor. Hop as rope travels under feet, and repeat.','Lower'),"+
            "(NULL,'Jump Rope: Run in place', 'Swing rope in front of body, down to floor. Hop on one leg as rope travels under foot. Repeat by alternating legs.', 'Medium'),"+
            "(NULL,'Mountain Climber', 'While holding upper body in place, alternate leg positions by pushing hips up while immediately extending forward leg back and pulling rear leg forward under body, landing on both forefeet simultaneously.', 'Medium')";


    public  final int TABLE_EXERCISE_COLUMN_ID          = 0;
    public  final int TABLE_EXERCISE_COLUMN_NAME        = 1;
    public  final int TABLE_EXERCISE_COLUMN_DESCRIPTION = 2;
    public  final int TABLE_EXERCISE_COLUMN_LEVEL       = 3;

    public DBBuilder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_TABLE_EXERCISES);

        insertData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(QUERY_DELETE_TABLE_EXERCISES);

        onCreate(db);
    }

    private void insertData( SQLiteDatabase db ){

        try {

            db.execSQL(QUERY_INSERT_DATA_INTO_TABLE_EXERCISES);

        }catch (SQLiteException e){

            Log.d(DEBUG_TAG,"Raised an exception while inserting data: " + e.getLocalizedMessage());
        }
    }

    public Cursor readData(  ){

        try{

            return getReadableDatabase().rawQuery("SELECT * FROM " + DATABASE_TABLE_EXERCISES,null);


        }catch (SQLiteException e){

            Log.d(DEBUG_TAG,"Raised an exception while reading data: " + e.getLocalizedMessage());

        }

        return null;
    }
}
