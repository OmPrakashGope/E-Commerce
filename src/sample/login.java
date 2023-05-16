package sample;

import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class login {

//    public static byte[] getSha(String input)
//    {
//        try{
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            return md.digest(input.getBytes(StandardCharsets.UTF_8));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return new byte[16];
//    }
//    public static String getEncrytedPassword(String password)
//    {
//            BigInteger num = new BigInteger(getSha(password));
//            StringBuilder hexString = new StringBuilder(num.toString(16));
//            return hexString.toString();
//    }
    public static Customer customerLogin(String userId,String password) {
//        String password1 = getEncrytedPassword(password);
        if(!userId.isEmpty() && !password.isEmpty()) {
            String query = "Select * from customer where email = '" + userId + "' and password = '" + password + "'";
//        System.out.println(password1);
            DatabaseConnection dbConn = new DatabaseConnection();
            ResultSet rs = null;
            try {
                rs = dbConn.getQueryTable(query);
//            System.out.println(rs);
                if (rs != null && rs.next()) {
                    return new Customer(rs.getInt("cid"), rs.getString("name"), rs.getString("email"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        System.out.println(rs.getInt("cid"));
        return null;
    }
    public static void main(String[] args)
    {
        System.out.println(customerLogin("Hello@gmail.com","abc"));
    }
}
