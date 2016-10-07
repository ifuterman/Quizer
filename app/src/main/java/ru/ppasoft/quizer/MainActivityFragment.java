package ru.ppasoft.quizer;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static ru.ppasoft.quizer.MainActivity.manager;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment
{
    private LinearLayout selectQuizLayout;
    private HashMap<Integer, Quiz> idQuizList;

    public MainActivityFragment()
    {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Quiz> lq = MainActivity.manager.asList();
        final QuizListAdApter ad = new QuizListAdApter(getActivity(), lq);
        setListAdapter(ad);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Создание вида фрагмента
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        selectQuizLayout = (LinearLayout)view.findViewById(R.id.quizSelectLinearLayout);
//        idQuizList = new HashMap<>();
//
//        QuizManager manager = MainActivity.manager;
//        Set<String> quizTitles = manager.getQuizTitleSet();
//        LinearLayout.LayoutParams paramsLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        int currentId = Integer.MAX_VALUE;
//        for (String title:quizTitles)
//        {
//            Quiz quiz = manager.getQuiz(title);
//            if (quiz == null)
//                continue;
//
//            LinearLayout layout = new LinearLayout(getContext());
//            layout.setLayoutParams(paramsLayout);
//            layout.setOrientation(LinearLayout.HORIZONTAL);
//            layout.setId(currentId);
//            currentId--;
//
//            ImageButton imageButton = new ImageButton(getContext());
//            imageButton.setId(currentId);
//            currentId--;
//            idQuizList.put(currentId, quiz);
//            imageButton.setImageDrawable(quiz.getQuizDrawable());
//            imageButton.setLayoutParams(paramsButton);
//            layout.addView(imageButton);
//
//            TextView textView = new TextView(getContext());
//            textView.setId(currentId);
//            currentId--;
//            textView.setText(quiz.getTitle());
//            textView.setLayoutParams(paramsButton);
//            layout.addView(textView);
//
//            selectQuizLayout.addView(layout);
//        }
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    /*@Override
    public void OnClick(View view)
    {
        this.
    }*/

    /**
     * A class that defines fields for each resource ID in the list item layout. This allows
     * ContactsAdapter.newView() to store the IDs once, when it inflates the layout, instead of
     * calling findViewById in each iteration of bindView.
     */
    class ViewHolder {
        ImageView icon;
        TextView text;
        TextView dt;
    }


}
