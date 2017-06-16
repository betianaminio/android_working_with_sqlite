package ar.com.betianaminio.workingwithsqlite.database;

import android.provider.BaseColumns;


public class DBExercisesContract {

    public static abstract class ExerciseTable implements BaseColumns{

        static final String TABLE_NAME = "exercises";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LEVEL = "level";

        public static final String COLUMN_LEVEL_VALUE_LOWER = "Lower";
        public static final String COLUMN_LEVEL_VALUE_MEDIUM = "Medium";
        public static final String COLUMN_LEVEL_VALUE_HIGHER = "Higher";

    }

}
