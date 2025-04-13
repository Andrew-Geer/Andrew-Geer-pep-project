package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDAO
{
    /**
     * @info A DAO that will create a new user in the database
     * @param account A Account object that is to be inserted into the database
     * @return The Account object that was created in the database
     */
    public Account createNewAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        //Try catch block to handle any sql erros that occur
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            //Gets the generated id from the sql table and sets the account ID to the generated key
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setAccount_id(generatedKeys.getInt(1));
                }
            }
            
        }
        //Catch block to print the stack trace
        catch(SQLException e){
            e.printStackTrace();
        }
        return account;
    }

    /**
     * @infoA DAO that will check to see if a username already exists in the database
     * @param username A username string that is to be checked
     * @return True if the username is unique. False if the username is already in use.
     */
    public boolean checkUniqueUsername(String username)
    {
        //Try catch block to handle any sql erros that occur
        try 
        {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            //Returns ture if there is a next entry
            //Returns false is there is no content
            return !rs.next();
        }
        //Catch block to print the stack trace
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @info A DAO that will check to retrive the login credentials for an account. This is horrible practice but I assumed this is what the project wanted me to do.
     * @param username A username string that is to be checked
     * @return An optional object that will be null if the account is not found
     */
    public Optional<Account> getLoginCredentials(String username)
    {
        //Try catch block to handle any sql erros that occur
        try 
        {

            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            //Gets information from the result set
            //Returns the account if one is found
            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
    
                return Optional.of(account);
            }

        }
        //Catch block to print the stack trace
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    /**
     * @info A DAO that will check to retrive user information based on the user ID.
     * @param ID The id of an account that will be returned
     * @return An optional object that will be null if the account is not found
     */
    public Optional<Account> getUserById(int ID)
    {
        //Try catch block to handle any sql erros that occur
        try 
        {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            //Gets information from the result set
            //Returns the account if one is found
            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
    
                return Optional.of(account);
            }

        }
        //Catch block to print the stack trace
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
}