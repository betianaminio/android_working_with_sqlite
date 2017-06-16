package ar.com.betianaminio.workingwithsqlite.database.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.database.DBBuilder;
import ar.com.betianaminio.workingwithsqlite.database.DBListener;

/**
 * Created by Betiana G. Miño on 15/06/2017.
 */

public class DBGetExercisesTask extends AsyncTask<Void,Void,Cursor> {

    private Context mContext;
    private DBListener.OnGetResultListener mListener;

    public DBGetExercisesTask( Context context, DBListener.OnGetResultListener listener ){

        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected Cursor doInBackground(Void... params) {

        return DBBuilder.getInstance(this.mContext).readData();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);

        if ( cursor != null && cursor.moveToFirst() )
            this.mListener.onGetResult(cursor);
        else
            this.mListener.onFailedToGetResult( this.mContext.getString(R.string.exercise_error_message));
    }
}
