package ar.com.betianaminio.workingwithsqlite.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.models.Exercise;
import ar.com.betianaminio.workingwithsqlite.views.interfaces.IExercisesItemListener;
import ar.com.betianaminio.workingwithsqlite.views.viewholders.ExerciseViewHolder;


public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {

    private List<Exercise> mExercisesList;
    private Context mContext;
    private IExercisesItemListener mListener;

    public ExerciseAdapter( Context context, IExercisesItemListener listener){

        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item_card_view,parent,false);

        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {

        if ( this.mExercisesList != null ) {

            holder.setName(this.mExercisesList.get(position).getName());
            holder.setDescription(this.mExercisesList.get(position).getDescription());
            holder.setLevel(this.mContext.getString(R.string.exercise_level) + ": " + this.mExercisesList.get(position).getLevel());
            holder.setListener(this.mListener);

        }
    }

    @Override
    public int getItemCount() {

        if ( this.mExercisesList != null)
         return this.mExercisesList.size();

        return 0;
    }

    public void setData(List<Exercise> exercises_list ){
        this.mExercisesList = exercises_list;
        notifyDataSetChanged();
    }

}
