package ru.ppasoft.quizer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by valerius on 10/7/16.
 */

class QuizListAdApter extends BaseAdapter {

    private Context mCtx;
    private List<Quiz> mQuizList;
    private static final DateFormat DF = SimpleDateFormat.getTimeInstance();

    QuizListAdApter(Context ctx, List<Quiz> quizList) {
        this.mCtx = ctx;
        this.mQuizList = quizList;
    }

    @Override
    public int getCount() {
        return mQuizList.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mQuizList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mCtx
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.quiz_list_item, null);
        }

        final ImageButton imgButton = (ImageButton) convertView.findViewById(R.id.imageButtonnQuizImage);
        final TextView txtTitle = (TextView) convertView.findViewById(R.id.itemTextQuizTitle);

        final Quiz row_pos = mQuizList.get(position);
        // setting the image resource and title
        imgButton.setImageDrawable(row_pos.getQuizDrawable());
        txtTitle.setText(row_pos.getTitle());

        // Turn screen, scroll list - see, that only visible rows are retrieving
        Log.i("FAFA", row_pos.getTitle());

        return convertView;
    }

}
