package ar.com.betianaminio.workingwithsqlite.database.tasks;

import android.content.Context;
import android.os.AsyncTask;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.database.DBBuilder;
import ar.com.betianaminio.workingwithsqlite.database.DBListener;
import ar.com.betianaminio.workingwithsqlite.models.Exercise;

/**
 * Created by Betiana G. Miño on 15/06/2017.
 */

public class DBInsertUpdateExerciseTask extends AsyncTask<Void,Void,Long> {

    private Context mContext;
    private boolean mUpdateMode;
    private DBListener.OnFinishTaskListener mListener;
    private Exercise mExercise;

    public DBInsertUpdateExerciseTask(Context context, Exercise exercise, boolean update_mode, DBListener.OnFinishTaskListener listener){

        this.mContext = context;
        this.mExercise = exercise;
        this.mUpdateMode = update_mode;
        this.mListener = listener;
    }

    @Override
    protected Long doInBackground(Void... params) {

        if ( this.mUpdateMode){

            return DBBuilder.getInstance(this.mContext).update(this.mExercise.getId(),this.mExercise.getName(),this.mExercise.getDescription(),this.mExercise.getLevel());
        }

        return DBBuilder.getInstance(this.mContext).insert(this.mExercise.getName(),this.mExercise.getDescription(),this.mExercise.getLevel());
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);

        if ( aLong > 0 )
            this.mListener.onFinishTask();
        else
            this.mListener.onFailedToFinishTask(this.mContext.getString(R.string.exercise_error_message));
    }
}
