import dao.QuestionDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.QuestionService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionDAO questionDAO = context.getBean(QuestionDAO.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.printQuestions();
    }

}
