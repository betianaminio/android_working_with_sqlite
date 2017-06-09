package ar.com.betianaminio.workingwithsqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ar.com.betianaminio.workingwithsqlite.database.DBBuilder;
import ar.com.betianaminio.workingwithsqlite.models.Exercise;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_exercises_list)
    RecyclerView mRecyclerExercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DBBuilder myLocalDB = new DBBuilder( this.getApplicationContext());

        Cursor cursor_exercises_data = myLocalDB.readData();

        if( cursor_exercises_data != null && cursor_exercises_data.moveToFirst()) {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            this.mRecyclerExercisesList.setLayoutManager(layoutManager);

            ArrayList<Exercise> exercises = new ArrayList<>();

            do {

                exercises.add(new Exercise(cursor_exercises_data.getInt(myLocalDB.TABLE_EXERCISE_COLUMN_ID),
                        cursor_exercises_data.getString(myLocalDB.TABLE_EXERCISE_COLUMN_NAME),
                        cursor_exercises_data.getString(myLocalDB.TABLE_EXERCISE_COLUMN_DESCRIPTION),
                        cursor_exercises_data.getString(myLocalDB.TABLE_EXERCISE_COLUMN_LEVEL)));


            } while (cursor_exercises_data.moveToNext());

            cursor_exercises_data.close();

            ExerciseAdapter exercisesAdapter = new ExerciseAdapter(this.getApplicationContext(), exercises);

            this.mRecyclerExercisesList.setAdapter(exercisesAdapter);

        }
    }
}
