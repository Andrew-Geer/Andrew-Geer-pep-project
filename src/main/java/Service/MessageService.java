package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import java.util.Optional;

public class MessageService 
{

    public boolean validateNonEmptyMessage(String messageText)
    {
        if (messageText == "" || messageText == null)
        {
            return false;
        }
        return true;
    }

    public boolean validateMessageLegnth(String messageText)
    {
        if (messageText.length() > 255)
        {
            return false;
        }
        return true;
    }

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
