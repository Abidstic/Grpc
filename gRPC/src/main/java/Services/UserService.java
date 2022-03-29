package Services;

import com.demo.grpc.User;
import com.demo.grpc.userGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.*;
import java.util.logging.Logger;

public class UserService extends userGrpc.userImplBase {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    String url="jdbc:mysql://localhost:3306/grpc";
    String uname="root";
    String dbpassword="********";

    @Override
    public void register(User.regReq request, StreamObserver<User.APIresponse> responseObserver) {
        System.out.println("Registering...");
        String username=request.getUsername();
        String password=request.getPassword();


        User.APIresponse.Builder regResponse=User.APIresponse.newBuilder();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,uname,dbpassword);
            Statement statement=con.createStatement();
            String query="INSERT INTO UserInfo (Username,Password) VALUES('"+username+"','"+password+"')";
            int i=statement.executeUpdate(query);
            if(i==1)
            {
                regResponse.setResponseCode(101).setResponseMessage("Registration Success");
                logger.info("Registration Successful user:"+username);

            }
            else
            {
                regResponse.setResponseCode(400).setResponseMessage("Registration Failed");
                logger.info("REgistration failed user:"+username);

            }


        }catch(Exception e)
        {
            System.out.println(e);

        }




    }

    @Override
    public void login(User.loginReq request, StreamObserver<User.APIresponse> responseObserver) {
        System.out.println("Inside Login");
        String username= request.getUsername();
        String password= request.getPassword();
        User.APIresponse.Builder response= User.APIresponse.newBuilder();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,uname,dbpassword);
            Statement statement=con.createStatement();
            String query="SELECT Password FROM UserInfo WHERE Username='"+username+"'";
            ResultSet resultSet=statement.executeQuery(query);
            resultSet.next();
            String uspass=resultSet.getString(1);
            if(password.equals(uspass))
            {
                //SUCCESS
                response.setResponseCode(0).setResponseMessage("Success");
                logger.info("Login successful for user : " +username);
            }
            else
            {
                //failure
                response.setResponseCode(404).setResponseMessage("Not Found");
                logger.info("Login failed for user : " + username);
            }
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        }catch (Exception e)
        {
            System.out.println(e);
        }



    }

    @Override
    public void logout(User.Empty request, StreamObserver<User.APIresponse> responseObserver) {
        super.logout(request,responseObserver);

    }
}
