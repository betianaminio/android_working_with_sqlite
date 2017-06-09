package ar.com.betianaminio.workingwithsqlite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Betiana G. Miño on 09/06/2017.
 */

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_exercise_name)
    TextView mName;

    @BindView(R.id.text_view_exercise_description)
    TextView mDescription;

    @BindView(R.id.text_view_exercise_level)
    TextView mLevel;

    public ExerciseViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);

    }

    void setName( CharSequence name ){

        this.mName.setText( name );
    }

    void setDescription( CharSequence description ){

        this.mDescription.setText( description );
    }

    void setLevel( CharSequence level ){

        this.mLevel.setText( level );
    }

}
