package ru.ppasoft.quizer;

import java.util.ArrayList;

/**
 * Created by ifuterman on 11.09.2016.
 */
//Класс викторины
public class Quiz
{
    private String quizTitle;//Название
    private String quizTitleImagePath;//Путь к картинке заголовка
    private ArrayList<Question> quizQuestions;//Вопросы
    public String getTitle()
    {
        return quizTitle;
    }

}
