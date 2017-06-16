package ar.com.betianaminio.workingwithsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBBuilder extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "MYSQLITE";

    private static final String DATABASE_NAME = "my_exercises.db";
    private static final int DATABASE_VERSION = 1;


    private static final String QUERY_CREATE_TABLE_EXERCISES = "CREATE TABLE " + DBExercisesContract.ExerciseTable.TABLE_NAME + "( " + DBExercisesContract.ExerciseTable.COLUMN_ID + " INTEGER PRIMARY KEY, " + DBExercisesContract.ExerciseTable.COLUMN_NAME + " VARCHAR(30), " + DBExercisesContract.ExerciseTable.COLUMN_DESCRIPTION + " VARCHAR(128), " + DBExercisesContract.ExerciseTable.COLUMN_LEVEL + " TEXT  CHECK( level IN ('" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_LOWER + "','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM + "','"  + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_HIGHER + "') ) NOT NULL DEFAULT '" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_LOWER + "')" ;
    private static final String QUERY_DELETE_TABLE_EXERCISES = "DROP TABLE IF EXISTS " + DBExercisesContract.ExerciseTable.TABLE_NAME;
    private static final String QUERY_INSERT_DATA_INTO_TABLE_EXERCISES = "INSERT INTO " + DBExercisesContract.ExerciseTable.TABLE_NAME + "( " + DBExercisesContract.ExerciseTable.COLUMN_ID +","+ DBExercisesContract.ExerciseTable.COLUMN_NAME +","+ DBExercisesContract.ExerciseTable.COLUMN_DESCRIPTION +"," + DBExercisesContract.ExerciseTable.COLUMN_LEVEL +" )" +
            "VALUES(NULL,'Standard Crunch','Lying flat on your back on the floor. You place your arms behind your head with your fingers behind your ears and bring your knees to a bent position with your feet flat on the floor. Lift your shoulders and head to the ceiling with your chin pointing towards the ceiling and not tucking your chin to your chest.','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_LOWER + "')," +
            "(NULL,'Advanced crunch','Lying flat on your back and holding your legs straight up in air. Place your arms behind your head with your fingers behind your ears and lift your head towards the ceiling with your chin pointing towards the ceiling and not tucking your chin to your chest.','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM + "')," +
            "(NULL,'Plank','Get into pushup position on the floor. Now bend your elbows 90 degrees and rest your weight on your forearms. Your elbows should be directly beneath your shoulders, and your body should form a straight line from your head to your feet. Hold the position for as long as you can. Your goal should be to hold it for two minutes.','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM + "')," +
            "(NULL,'Knee Drive Plank','Start in an elbow plank, and bring your right knee into your nose; your pelvis will rise toward the ceiling. Place right foot back on the ground. Alternate sides, bringing your left knee in.','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM + "')," +
            "(NULL,'Knee Tuck Kickout Slider','Start in high plank position with both feet on sliders, socks, paper, glides etc. or whatever makes you slide. With both feet on sliders, bring knees in toward chest and then slide both feet evenly to the right, making sure to engage your obliques. Finish with both feet outside of your right hand. Push back into a high plank and repeat on the other side.','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_HIGHER + "')," +
            "(NULL,'Burpee', 'Bend over and squat down. Place hands on floor, slightly wider than shoulder width. While holding upper body in place, kick legs back. Land on forefeet with body in straight, plank position. Perform pushup by lowering body to floor and back up. Keeping upper body in place, pull legs forward under body returning feet in original position. Jump up and land to original standing posture or repeat.', '" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_HIGHER + "')," +
            "(NULL,'Jump Rope: Boxer Step', 'Swing rope in front of body, down to floor. Hop up as rope travels under foot and land over to left side, left foot lands then right foot touches. Swing rope around body and hop over rope again landing to right side, right foot lands then left foot touches. Continue jumps hopping side to side.', '" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_HIGHER + "'), " +
            "(NULL,'Jump Rope: Regular Bounce','Swing rope in front of body, down to floor. Hop as rope travels under feet, and repeat.','" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_LOWER + "'),"+
            "(NULL,'Jump Rope: Run in place', 'Swing rope in front of body, down to floor. Hop on one leg as rope travels under foot. Repeat by alternating legs.', '" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM + "'),"+
            "(NULL,'Mountain Climber', 'While holding upper body in place, alternate leg positions by pushing hips up while immediately extending forward leg back and pulling rear leg forward under body, landing on both forefeet simultaneously.', '" + DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM + "')";
    private static final String QUERY_SELECT_ALL_FROM_TABLE = "SELECT * FROM " + DBExercisesContract.ExerciseTable.TABLE_NAME;

    private static DBBuilder s_instance = null;

    public static synchronized DBBuilder getInstance(Context context){

        if ( s_instance == null)
            s_instance = new DBBuilder(context);

        return s_instance;
    }

    public void shutDown(){

        s_instance = null;
    }

    private DBBuilder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_TABLE_EXERCISES);

        //When this method is executed, the db is in writable mode,
        //so we don't need to call getWritableDatabase(). It will be recursively.

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

    public int deleteData( int id ){

        try{

            int result = getReadableDatabase().delete(DBExercisesContract.ExerciseTable.TABLE_NAME, DBExercisesContract.ExerciseTable.COLUMN_ID + "=" + id,null);
            this.close();

            return result;

        }catch (SQLiteException e){

            Log.d(DEBUG_TAG,"Raised an exception while deleting data: " + e.getLocalizedMessage());

            return 0;
        }

    }

    public Cursor readData(  ){

        try{

            return getReadableDatabase().rawQuery(QUERY_SELECT_ALL_FROM_TABLE,null);

        }catch (SQLiteException e){

            Log.d(DEBUG_TAG,"Raised an exception while reading data: " + e.getLocalizedMessage());

        }

        return null;
    }

    public long insert(String name, String description, String level){

        try{

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBExercisesContract.ExerciseTable.COLUMN_NAME,name);
            contentValues.put(DBExercisesContract.ExerciseTable.COLUMN_DESCRIPTION,description);
            contentValues.put(DBExercisesContract.ExerciseTable.COLUMN_LEVEL,level);

            long newRow = db.insertOrThrow(DBExercisesContract.ExerciseTable.TABLE_NAME,null,contentValues);

            db.close();

            return newRow;

        }catch (SQLiteException e){

            Log.d(DEBUG_TAG,"Raised an exception while inserting data: " + e.getLocalizedMessage());

            return -1;
        }


    }

    public long update(int id, String name, String description, String level ){

        try{

            SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBExercisesContract.ExerciseTable.COLUMN_NAME,name);
            contentValues.put(DBExercisesContract.ExerciseTable.COLUMN_DESCRIPTION,description);
            contentValues.put(DBExercisesContract.ExerciseTable.COLUMN_LEVEL,level);

            long rowUpdated = db.update(DBExercisesContract.ExerciseTable.TABLE_NAME,contentValues, DBExercisesContract.ExerciseTable.COLUMN_ID + "="+id,null);

            db.close();

            return  rowUpdated;

        }catch (SQLiteException e ){

            Log.d(DEBUG_TAG,"Raised an exception while updating data: " + e.getLocalizedMessage());
        }

        return -1;
    }

    public Cursor getItemById( int id ){

        try{

            return this.getReadableDatabase().query(DBExercisesContract.ExerciseTable.TABLE_NAME,null, DBExercisesContract.ExerciseTable.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null,null);

        }catch (SQLiteException e){

            Log.d(DEBUG_TAG,"Raised an exception while getting data: " + e.getLocalizedMessage());
        }

        return null;
    }
}
