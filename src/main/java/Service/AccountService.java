package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService
{
    private int passwordLengthRequirement;

    public AccountService()
    {
        passwordLengthRequirement = 4;
    }

    public boolean validateAccountPassword(String password)
    {
        if (password.length() < passwordLengthRequirement)
        {
            return false;
        }
        return true;
    }

    public boolean validateNonEmptyUsername(String username)
    {
        if (username == "" || username == null)
        {
            return false;
        }
        return true;
    }
}