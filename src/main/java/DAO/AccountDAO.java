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
     * A DAO that will create a new user in the database
     * @param account A Account object that is to be inserted into the database
     * @return The Account object that was created in the database
     */
    public Account createNewAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setAccount_id(generatedKeys.getInt(1));
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return account;
    }

    public boolean checkUniqueUsername(String username)
    {
        try 
        {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            return !rs.next();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Account> getLoginCredentials(String username)
    {
        try 
        {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
    
                return Optional.of(account);
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    public Optional<Account> getUserById(int ID)
    {
        try 
        {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
    
                return Optional.of(account);
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
}