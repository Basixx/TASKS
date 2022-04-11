package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    private static String HTML_FILE = "mail/trello-card-mail";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message){

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        setContext(context, message, false, false, true);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process(HTML_FILE, context);
    }

    public String buildTrelloCardsQuantityEmail(String message){
        Context mailContext = new Context();
        setContext(mailContext, message, true, false, false);
        return templateEngine.process(HTML_FILE, mailContext);
    }

    private void setContext(Context context, String message, boolean showButton, boolean friend, boolean showFunctionality){
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", showButton);
        context.setVariable("show_functionality", showFunctionality);
        context.setVariable("is_friend", friend);
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("owner_name", companyConfig.getCompanyOwnerFirstName() + " " + companyConfig.getCompanyOwnerLastName());
        context.setVariable("company_email", companyConfig.getCompanyEmail());
        context.setVariable("admin_config", adminConfig);
    }
}
