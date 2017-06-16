package ar.com.betianaminio.workingwithsqlite.views.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.database.DBExercisesContract;
import ar.com.betianaminio.workingwithsqlite.database.DBListener;
import ar.com.betianaminio.workingwithsqlite.database.tasks.DBGetExerciseByIdTask;
import ar.com.betianaminio.workingwithsqlite.database.tasks.DBInsertUpdateExerciseTask;
import ar.com.betianaminio.workingwithsqlite.models.Exercise;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends AppCompatActivity {

    public static final String INTENT_KEY_UPDATE_MODE = "exercise_update";

    @BindView(R.id.exercise_text_input_name)
    TextInputLayout mTextInputName;
    @BindView(R.id.exercise_edit_name)
    EditText mEditTextName;

    @BindView(R.id.exercise_text_input_description)
    TextInputLayout mTextInputDescription;
    @BindView(R.id.exercise_edit_description)
    EditText mEditTextDescription;

    @BindView(R.id.exercise_radio_group_levels)
    RadioGroup mRadioGroupLevels;

    private String mLevel;
    private boolean mEditMode;
    private int mIdExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        ButterKnife.bind(this);

        this.mLevel      = DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_LOWER;
        this.mEditMode   = false;
        this.mIdExercise = -1;

        if ( getIntent().hasExtra(INTENT_KEY_UPDATE_MODE)){

            this.mEditMode = true;
            getExerciseToEdit(getIntent().getIntExtra(INTENT_KEY_UPDATE_MODE,-1));

            setTitle(getString(R.string.edit_exercise_title));
        }
        else
            setTitle(getString(R.string.new_exercise_title));



    }

    @Override
    public void onBackPressed(){

        alertDialogGoOut();
    }

    public void onSelectLevelClicked( View view ){

    }

    public void onDoneButtonClicked( View view ){

        if ( isFormCompleted()) {

            String name = this.mEditTextName.getText().toString();
            String description = this.mEditTextDescription.getText().toString();
            this.mLevel = getSelectedRadioButtonValue();


            Exercise exercise = null;
            if (this.mEditMode)
                exercise = new Exercise(this.mIdExercise, name, description, this.mLevel);
            else {
                exercise = new Exercise();
                exercise.setName( name );
                exercise.setDescription( description );
                exercise.setLevel( this.mLevel );
            }


            new DBInsertUpdateExerciseTask(this.getApplicationContext(), exercise, this.mEditMode, new DBListener.OnFinishTaskListener() {
                @Override
                public void onFinishTask() {

                    if ( !ExerciseActivity.this.mEditMode )
                        setResult(Activity.RESULT_OK);

                    finish();
                }

                @Override
                public void onFailedToFinishTask(String error) {


                }
            }).execute();
        }


    }

    public void onCancelButtonClicked( View view ){

        alertDialogGoOut();

    }

    private String getSelectedRadioButtonValue(){

        int item_selected = this.mRadioGroupLevels.getCheckedRadioButtonId();

        RadioButton radio_button_view = (RadioButton)this.mRadioGroupLevels.findViewById(item_selected);

        switch ( radio_button_view.getId()){

            case R.id.exercise_radio_button_medium: return DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM;

            case R.id.exercise_radio_button_higher: return DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_HIGHER;

            default: return DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_LOWER;
        }


    }

    private void getExerciseToEdit(int item){

        this.mIdExercise = item;

        new DBGetExerciseByIdTask(this.getApplicationContext(), item, new DBListener.OnGetResultListener() {
            @Override
            public void onGetResult(Cursor cursor) {

                ExerciseActivity.this.mEditTextName.setText(cursor.getString(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_NAME)));
                ExerciseActivity.this.mEditTextDescription.setText(cursor.getString(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_DESCRIPTION)));

                String level = cursor.getString(cursor.getColumnIndex(DBExercisesContract.ExerciseTable.COLUMN_LEVEL));
                RadioButton radio_button_level;
                if ( level.compareToIgnoreCase(DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_HIGHER) == 0){

                    radio_button_level = (RadioButton)findViewById(R.id.exercise_radio_button_higher);
                    radio_button_level.setChecked(true);

                }else if ( level.compareToIgnoreCase(DBExercisesContract.ExerciseTable.COLUMN_LEVEL_VALUE_MEDIUM) == 0){

                    radio_button_level = (RadioButton)findViewById(R.id.exercise_radio_button_medium);
                    radio_button_level.setChecked(true);

                }

                cursor.close();

            }

            @Override
            public void onFailedToGetResult(String error) {

            }
        }).execute();


    }

    private boolean isNameFieldCompleted(){

        if ( this.mEditTextName.length() == 0){
            this.mTextInputName.setError(getString(R.string.exercise_error_complete_name));
            return false;
        }
        else
            this.mTextInputName.setError(null);

        return true;
    }

    private boolean isDescriptionFieldCompleted(){

        if ( this.mEditTextDescription.length() == 0){
            this.mTextInputDescription.setError(getString(R.string.exercise_error_complete_description));
            return false;
        }
        else
            this.mTextInputDescription.setError(null);

        return true;
    }

    private boolean isFormCompleted(){

        if ( !isNameFieldCompleted())
            return false;

        if ( !isDescriptionFieldCompleted())
            return false;

        return true;
    }

    private void alertDialogGoOut(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.go_out_message));

        alertDialogBuilder.setPositiveButton(getString(R.string.action_button_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.action_button_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

}
