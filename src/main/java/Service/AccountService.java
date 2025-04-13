package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.Optional;

public class AccountService
{
    //Value to detiemine the minimum legnth for a valid password
    private int passwordLengthRequirement;

    public AccountService()
    {
        passwordLengthRequirement = 4;
    }

    /**
     * This is a validation method to verify a correct password
     * @param password The password for an account that is to be checkecked to see if it has any invalid components
     * @return boolean value that shows if the password is valid. True = Valid, False = Invalid.
     */
    public boolean validateAccountPassword(String password)
    {
        if (password.length() < passwordLengthRequirement)
        {
            return false;
        }
        return true;
    }

    /**
     * This is a validation method to verify a non-empty username.
     * @param username The username for an account that is to be checkecked to see if it is "" or null.
     * @return boolean value that shows if the username is valid. True = Valid, False = Invalid.
     */
    public boolean validateNonEmptyUsername(String username)
    {
        if (username == "" || username == null)
        {
            return false;
        }
        return true;
    }

     /**
     * This is a validation method to verify a non-empty username.
     * @param account The username for an account that is to be checkecked to see if it is "" or null.
     * @return The integer ID of the account that has been loged into.
     * @return 0 if the account login information is incorrect
     */
    public int userLogin (Account loginAttempt)
    {
        Account validAccount;
        AccountDAO accountDAO = new AccountDAO();
        Optional<Account> optionalAccount = accountDAO.getLoginCredentials(loginAttempt.username);

        //Checks if the optional is empty and exits if it is
        if (optionalAccount.isEmpty())
        {
            return 0;
        }

        //Retrives the account out of the optional object
        validAccount = optionalAccount.get();

        //Checks to see if the login is correct
        if (validAccount.password.equals(loginAttempt.password))
        {
            return validAccount.account_id;
        }

        return 0;
    }
}