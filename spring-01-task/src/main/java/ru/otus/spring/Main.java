package ru.otus.spring;

import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuizService;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizService service = context.getBean(QuizService.class);

        List<Question> questions = service.findAll();
        if (questions != null) {
            for (Question question : questions) {
                System.out.format("\n%s \n", question.getQuestion());

                List<String> answers = question.getAnswers();
                for (int i = 0; i < answers.size(); i++) {
                    String x = answers.get(i);
                    System.out.format("%d) %s \n", i + 1, x);
                }
            }
        }

        context.close();
    }
}
