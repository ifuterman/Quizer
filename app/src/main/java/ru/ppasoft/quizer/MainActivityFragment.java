package ru.ppasoft.quizer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

import static ru.ppasoft.quizer.MainActivity.manager;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
{
    private LinearLayout selectQuizLayout;
    private HashMap<Integer, Quiz> idQuizList;

    public MainActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Создание вида фрагмента
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        selectQuizLayout = (LinearLayout)view.findViewById(R.id.quizSelectLinearLayout);
        idQuizList = new HashMap<>();

        QuizManager manager = MainActivity.manager;
        Set<String> quizTitles = manager.getQuizTitleSet();
        LinearLayout.LayoutParams paramsLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int currentId = Integer.MAX_VALUE;
        for (String title:quizTitles)
        {
            Quiz quiz = manager.getQuiz(title);
            if (quiz == null)
                continue;

            LinearLayout layout = new LinearLayout(getContext());
            layout.setLayoutParams(paramsLayout);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setId(currentId);
            currentId--;

            ImageButton imageButton = new ImageButton(getContext());
            imageButton.setId(currentId);
            currentId--;
            idQuizList.put(currentId, quiz);
            imageButton.setImageDrawable(quiz.getQuizDrawable());
            imageButton.setLayoutParams(paramsButton);

            layout.addView(imageButton);

            TextView textView = new TextView(getContext());
            textView.setId(currentId);
            currentId--;
            textView.setText(quiz.getTitle());
            textView.setLayoutParams(paramsButton);
            layout.addView(textView);

            selectQuizLayout.addView(layout);
        }
        return view;
    }
    /*@Override
    public void OnClick(View view)
    {
        this.
    }*/

}
