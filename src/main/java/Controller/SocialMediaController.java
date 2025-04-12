package Controller;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::createNewUser);
        app.post("login", this::userLogin);
        app.post("messages", this::createNewMessage);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createNewUser(Context context)
    {
        AccountDAO accountDAO = new AccountDAO();
        AccountService accountService = new AccountService();
        Account account = context.bodyAsClass(Account.class);

        //Invalid Usernames return 400 status code
        if (!accountService.validateNonEmptyUsername(account.username))
        {
            context.status(400);
            return;
        }
        //Invalid Passwords return 400 status code
        if(!accountService.validateAccountPassword(account.password))
        {
            context.status(400);
            return;
        }
        //Duplicate Usernames return 
        if(!accountDAO.checkUniqueUsername(account.username))
        {
            context.status(400);
            return;
        }
        
        account = accountDAO.createNewAccount(account);
        context.json(account);
        context.status(200);
    }

    private void userLogin(Context context)
    {
        AccountService accountService = new AccountService();
        Account account = context.bodyAsClass(Account.class);

        int loginCanidateID = accountService.userLogin(account);

        if (loginCanidateID == 0)
        {
            context.status(401);
            return;
        }

        account.setAccount_id(loginCanidateID);
        context.json(account);
        context.status(200);
    }
    
    //TODO Implement createNewMessage Endpoint
    private void createNewMessage(Context context) 
    {
        //Variable decleration and creation
        Message message = context.bodyAsClass(Message.class);
        MessageDAO messageDAO = new MessageDAO();
        MessageService messageService = new MessageService();

        //Validates the entire message in one method rather then calling each individual validation method
        if (!messageService.ValidateEntireMessage(message))
        {
            context.status(400);
            return;
        }

        //Creating the message
        message = messageDAO.createNewMessage(message);

        //Setting up the return context
        context.json(message);
        context.status(200);
    }
    //TODO Implement retriveAllMessages Endpoint
    private void retriveAllMessages(Context context) 
    {

    }

    //TODO Implement retriveAllMessages Endpoint
    private void retriveAllMessagesByUserId(Context context) 
    {

    }

    //TODO Implement retriveMessageById Endpoint
    private void retriveMessageById(Context context) 
    {

    }
    //TODO Implement deleteMessageById Endpoint
    private void deleteMessageById(Context context)
    {

    }
    //TODO Implement updateMessage Endpoint
    private void updateMessage(Context context)
    {

    }
}