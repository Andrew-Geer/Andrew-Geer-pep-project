package Controller;

import Model.Account;
import Model.Message;
import java.util.Optional;
import io.javalin.Javalin;
import io.javalin.http.Context;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Service.AccountService;
import Service.MessageService;


public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("messages", this::retriveAllMessages);
        app.post("register", this::createNewUser);
        app.post("login", this::userLogin);
        app.post("messages", this::createNewMessage);
        app.get("messages/{message_id}", this::retriveMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::retriveAllMessagesByUserId);

        //app.get("accounts/{account_id}/messages", this::retriveAllMessagesByUserId);

        return app;
    }

    //Endpoint method that will create a new user with the information provided
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

    //Endpoint method that will "login" a user
    private void userLogin(Context context)
    {
        AccountService accountService = new AccountService();
        Account account = context.bodyAsClass(Account.class);

        int loginCanidateID = accountService.userLogin(account);

        //Checks if the method returned a valid canidate
        //0 means that the username was not found in the database
        if (loginCanidateID == 0)
        {
            context.status(401);
            return;
        }

        account.setAccount_id(loginCanidateID);
        context.json(account);
        context.status(200);
    }
    
    //Endpoint method that will create a new message
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

    //Endpoint method that will retrive all methods found in the database
    private void retriveAllMessages(Context context) 
    {
        MessageDAO messageDAO = new MessageDAO();
        context.json(messageDAO.retriveAllMessages());
        context.status(200);
    }

    //Endpoint method that will retive a message provided its message id as a path variable
    private void retriveMessageById(Context context) 
    {
        MessageDAO messageDAO = new MessageDAO();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Optional<Message> OptionalMessage = messageDAO.retriveMessageById(messageId);

        if (OptionalMessage.isPresent())
        {
            context.json(OptionalMessage.get());
        }
        context.status(200);
    }

    //Endpoint method that will remove a message provided its message id as a path variable
    //This will also return the deleated message as a confirmation
    private void deleteMessageById(Context context)
    {
        MessageDAO messageDAO = new MessageDAO();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Optional<Message> optionalDeleatedMessage = messageDAO.retriveMessageById(messageId);

        if (optionalDeleatedMessage.isPresent())
        {
            messageDAO.deleteMessageById(messageId);
            context.json(optionalDeleatedMessage.get());
        }
        context.status(200);
    }

    //Endpoint method that will update a messages text provided its message id as a path variable
    private void updateMessage(Context context)
    {
        //Variable decleration and creation
        MessageDAO messageDAO = new MessageDAO();
        MessageService messageService = new MessageService();
        Message messageCanidate = context.bodyAsClass(Message.class);
        
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String messageText = messageCanidate.message_text;

        //Validates seperate aspects of the message becuse the validate entire message method was designed to check posted_by which may not be provided
        if (!messageService.validateMessageLegnth(messageText))
        {
            context.status(400);
            return;
        }
        if (!messageService.validateNonEmptyMessage(messageText))
        {
            context.status(400);
            return;
        }

        //Gets the message opject
        Optional<Message>updatedMessage = messageDAO.updateMessageText(messageId, messageCanidate);

        //Checks if a message was found. If no message found it did not find a message with the same id
        if (updatedMessage.isEmpty()) 
        {
            context.status(400);
            return;
        }
        //Setting up the return context
        context.json(updatedMessage.get());
        context.status(200);
    }
    
    //Endpoint method that will retrive every message a user has posted provided their id
    private void retriveAllMessagesByUserId(Context context) 
    {
        MessageDAO messageDAO = new MessageDAO();
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageDAO.retriveMessageByUserId(accountId));
        context.status(200);
    }
}