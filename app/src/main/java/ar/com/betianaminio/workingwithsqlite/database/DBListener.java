package ar.com.betianaminio.workingwithsqlite.database;

import android.database.Cursor;

public class DBListener {

    public interface OnGetResultListener{
        void onGetResult( Cursor cursor);
        void onFailedToGetResult( String error );
    }

    public interface OnFinishTaskListener{
        void onFinishTask();
        void onFailedToFinishTask( String error );
    }
}
