package DAO;

import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import Model.Message;
 
public class MessageDAO
{
    /**
     * A DAO that will insert a new message into the database
     * @param message A message object that is to be inserted into the database
     * @return The Message Object that was created in the database
     */
    public Message createNewMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.time_posted_epoch);

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setMessage_id(generatedKeys.getInt(1));
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return message;
    }

    /**
     *  A DAO Object that will update a message given a differnt message object with an existing ID.
     * @param messageID The id of a message that is to be changed
     * @return The Message Object that was changed in the database
     */
    public Message updateMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE Message (posted_by = ?, message_text = ?, time_posted_epoch = ?) WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.time_posted_epoch);
            ps.setInt(4, message.getMessage_id());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setMessage_id(generatedKeys.getInt(1));
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return message;
    }
    
}