package ar.com.betianaminio.workingwithsqlite.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.database.DBBuilder;
import ar.com.betianaminio.workingwithsqlite.database.DBExercisesContract;
import ar.com.betianaminio.workingwithsqlite.database.DBListener;
import ar.com.betianaminio.workingwithsqlite.database.tasks.DBDeleteExerciseTask;
import ar.com.betianaminio.workingwithsqlite.database.tasks.DBGetExercisesTask;
import ar.com.betianaminio.workingwithsqlite.models.Exercise;
import ar.com.betianaminio.workingwithsqlite.views.adapters.ExerciseAdapter;
import ar.com.betianaminio.workingwithsqlite.views.interfaces.IExercisesItemListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int ACTIVITY_RESULT_UPDATE_EXERCISE = 1;
    private final int ACTIVITY_RESULT_NEW_EXERCISE = 2;

    @BindView(R.id.recycler_view_exercises_list)
    RecyclerView mRecyclerExercisesList;
    @BindView(R.id.new_item_floating_button)
    FloatingActionButton mNewExerciseButton;

    private ArrayList<Exercise> mExercisesList;
    private ArrayList<Integer> mExercisesToRemove;
    private ExerciseAdapter mExercisesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        prepareUI();

        getExercisesList();

        prepareRecycleViewToRemoveItem();

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){
        super.onActivityResult(request_code,result_code,data);

        switch ( request_code ){

            case ACTIVITY_RESULT_UPDATE_EXERCISE:

                getExercisesList();

                break;

            case ACTIVITY_RESULT_NEW_EXERCISE:

                getExercisesList();

                if (Activity.RESULT_OK == result_code)
                    showSnackBarMessage(getString(R.string.new_exercise_created_message));

                break;
        }

    }

    @Override
    protected void onPause(){
        super.onPause();

        if ( this.mExercisesToRemove.size() > 0 )
            new DBDeleteExerciseTask(this.getApplicationContext(), this.mExercisesToRemove, new DBListener.OnFinishTaskListener() {
                @Override
                public void onFinishTask() {

                    MainActivity.this.mExercisesToRemove.clear();

                }

                @Override
                public void onFailedToFinishTask(String error) {

                    Toast.makeText(MainActivity.this.getApplicationContext(),error,Toast.LENGTH_LONG).show();
                }
            }).execute();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        DBBuilder.getInstance(this.getApplicationContext()).shutDown();
    }

    private void prepareUI(){

        this.mNewExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MainActivity.this,ExerciseActivity.class),ACTIVITY_RESULT_NEW_EXERCISE);
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.mRecyclerExercisesList.setLayoutManager(layoutManager);
        this.mExercisesAdapter = new ExerciseAdapter(this.getApplicationContext(), new IExercisesItemListener() {
            @Override
            public void onClick(int item) {

                Intent intent = new Intent(MainActivity.this,ExerciseActivity.class);
                intent.putExtra(ExerciseActivity.INTENT_KEY_UPDATE_MODE,MainActivity.this.mExercisesList.get(item).getId());
                startActivityForResult(intent,ACTIVITY_RESULT_UPDATE_EXERCISE);

            }
        });
        this.mRecyclerExercisesList.setAdapter(this.mExercisesAdapter);

    }

    private void showSnackBarMessage( String message ){

        Snackbar snackbar = Snackbar.make(this.mRecyclerExercisesList, message, Snackbar.LENGTH_LONG);
        snackbar.show();

        View snackBarView = snackbar.getView();
        TextView snackBarText = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            snackBarText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            snackBarText.setGravity(Gravity.CENTER_HORIZONTAL);

    }

    private void getExercisesList(){

        new DBGetExercisesTask(this.getApplicationContext(), new DBListener.OnGetResultListener() {
            @Override
            public void onGetResult(Cursor cursor) {

                if ( MainActivity.this.mExercisesList == null)
                    MainActivity.this.mExercisesList = new ArrayList<>();
                else
                    MainActivity.this.mExercisesList.clear();

                do{

                    Exercise exercise = new Exercise(cursor.getInt(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_LEVEL)));

                    MainActivity.this.mExercisesList.add(exercise);


                }while ( cursor.moveToNext());

                MainActivity.this.mExercisesAdapter.setData(MainActivity.this.mExercisesList);

                cursor.close();


            }

            @Override
            public void onFailedToGetResult(String error) {


            }
        }).execute();

    }

    private void prepareRecycleViewToRemoveItem(){

        this.mExercisesToRemove = new ArrayList<>();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final int adapterPosition = viewHolder.getAdapterPosition();

                MainActivity.this.mExercisesToRemove.add(MainActivity.this.mExercisesList.get(adapterPosition).getId());
                MainActivity.this.mExercisesList.remove(adapterPosition);
                MainActivity.this.mExercisesAdapter.notifyItemRemoved(adapterPosition);


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(this.mRecyclerExercisesList);

    }

}
