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

    public boolean validateAccountInformation(Account account)
    {
        String accountPassword = account.getPassword();
        String accountUsername = account.getUsername();
        AccountDAO accountDAO = new AccountDAO();

        if (accountPassword.length() < passwordLengthRequirement)
        {
            return false;
        }
        if (accountUsername == "" || accountUsername == null)
        {
            return false;
        }

        return accountDAO.checkUniqueUsername(accountUsername);
    }
}