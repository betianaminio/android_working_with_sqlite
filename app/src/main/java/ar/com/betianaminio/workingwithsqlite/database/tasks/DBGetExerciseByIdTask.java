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

public class DBGetExerciseByIdTask extends AsyncTask<Void,Void,Cursor> {

    private Context mContext;
    private int     mItem;
    private DBListener.OnGetResultListener mListener;

    public DBGetExerciseByIdTask(Context context, int item, DBListener.OnGetResultListener listener){

        this.mContext =  context;
        this.mItem    =  item;
        this.mListener = listener;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);

        if ( cursor != null && cursor.moveToFirst() )
            this.mListener.onGetResult(cursor);
        else
            this.mListener.onFailedToGetResult(this.mContext.getString(R.string.exercise_error_message));
    }

    @Override
    protected Cursor doInBackground(Void... params) {

        return DBBuilder.getInstance(this.mContext).getItemById(this.mItem);
    }
}
