package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import java.util.Optional;


public class MessageService 
{

    /**
    * @info This is a validation method to verify a non empty message
    * @param messageText The messageText for a message that is to be checkecked to see if it has any invalid components
    * @return boolean value that shows if the password is valid. True = Valid, False = Invalid.
    */
    public boolean validateNonEmptyMessage(String messageText)
    {
        if (messageText == "" || messageText == null)
        {
            return false;
        }
        return true;
    }

    /**
    * @info This is a validation method to verify a message is not over the maxium character limit
    * @param messageText The messageText for a message that is to be checkecked to see if it has any invalid components
    * @return boolean value that shows if the password is valid. True = Valid, False = Invalid.
    */
    public boolean validateMessageLegnth(String messageText)
    {
        if (messageText.length() > 255)
        {
            return false;
        }
        return true;
    }

    /**
    * @info This is a validation method to verify a existing user in the database
    * @param userId The userId for an account that is to be checkecked to see if it exists in the database
    * @return boolean value that shows if the password is valid. True = Valid, False = Invalid.
    */
    public boolean validateRealUser(int userId)
    {
        AccountDAO accountDAO = new AccountDAO();
        Optional<Account> account = accountDAO.getUserById(userId);

        if (account.isPresent())
        {
            return true;
        }
        return false;
    }

    /**
    * @info This is a validation method to verify all components of a message
    * @param message The message object that is to be verified
    * @return boolean value that shows if the password is valid. True = Valid, False = Invalid.
    */
    public boolean ValidateEntireMessage(Message message)
    {
        if (!validateNonEmptyMessage(message.message_text))
        {
            return false;
        }
        if (!validateMessageLegnth(message.message_text))
        {
            return false;
        }
        if (!validateRealUser(message.posted_by))
        {
            return false;
        }
        return true;
    }
}
