package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

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
        app.get("createNewUser", this::createNewUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //TODO Implement createNewUser Endpoint
    private void createNewUser(Context context) 
    {

    }

    //TODO Implement userLogin Endpoint
    private void userLogin(Context context)
    {

    }
    
    //TODO Implement createNewMessage Endpoint
    private void createNewMessage(Context context) 
    {

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