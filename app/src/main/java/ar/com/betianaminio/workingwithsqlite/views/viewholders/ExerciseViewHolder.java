package ar.com.betianaminio.workingwithsqlite.views.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ar.com.betianaminio.workingwithsqlite.R;
import ar.com.betianaminio.workingwithsqlite.views.interfaces.IExercisesItemListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.text_view_exercise_name)
    TextView mName;

    @BindView(R.id.text_view_exercise_description)
    TextView mDescription;

    @BindView(R.id.text_view_exercise_level)
    TextView mLevel;

    private IExercisesItemListener mListener;

    public ExerciseViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

        itemView.setOnClickListener(this);
    }

    public void setName( CharSequence name ){

        this.mName.setText( name );
    }

    public void setDescription( CharSequence description ){

        this.mDescription.setText( description );
    }

    public void setLevel( CharSequence level ){

        this.mLevel.setText( level );
    }

    public void setListener(IExercisesItemListener listener ){

        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {

        if ( this.mListener != null )
            this.mListener.onClick( getAdapterPosition() );
    }
}
