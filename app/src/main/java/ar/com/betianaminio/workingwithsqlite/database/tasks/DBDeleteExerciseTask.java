package ar.com.betianaminio.workingwithsqlite.database.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.database.DBBuilder;
import ar.com.betianaminio.workingwithsqlite.database.DBListener;

/**
 * Created by Betiana G. Miño on 16/06/2017.
 */

public class DBDeleteExerciseTask extends AsyncTask<Void,Void,Integer> {

    private Context mContext;
    private List<Integer> mItems;
    private DBListener.OnFinishTaskListener mListener;

    public DBDeleteExerciseTask(Context context, List<Integer> items, DBListener.OnFinishTaskListener listener){

        this.mContext  = context;
        this.mItems     = items;
        this.mListener = listener;
    }

    @Override
    protected void onPostExecute(Integer aInteger ) {
        super.onPostExecute(aInteger);

        if ( aInteger > 0 )
            this.mListener.onFinishTask();
        else
            this.mListener.onFailedToFinishTask(this.mContext.getString(R.string.exercise_error_message));
    }

    @Override
    protected Integer doInBackground(Void... params) {

        int result = 0;

        for (Integer id: this.mItems
             ) {

            result = DBBuilder.getInstance(this.mContext).deleteData(id);

            if ( result <= 0)
                break;
        }

        return result;
    }
}
