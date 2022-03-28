import com.demo.grpc.User;
import com.demo.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.logging.Logger;

public class Grpc_client {
    private static final Logger logger = Logger.getLogger(Grpc_client.class.getName());
    public static void main(String[] args) {
        ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",9090).usePlaintext().build();
        userGrpc.userBlockingStub userStub=userGrpc.newBlockingStub(channel);
        while(true)
        {
            Scanner input=new Scanner(System.in);
            System.out.println("1.Register\n2.Login");
            int op=input.nextInt();
            if(op==1)
            {

                   Scanner input1 = new Scanner(System.in);
                   System.out.println("Enter Your username:\n");
                   String usName = input1.nextLine();
                   System.out.println("Enter Your password:\n");
                   String usPass = input1.nextLine();
                   User.regReq reg = User.regReq.newBuilder().setUsername(usName).setPassword(usPass).build();
                   User.APIresponse apiRes = userStub.register(reg);
                   logger.info(apiRes.getResponseCode() + "\n" + apiRes.getResponseMessage());



            }
            else if(op==2)
            {

                    Scanner input1 = new Scanner(System.in);
                    System.out.println("Enter Your Username:");
                    String usName = input1.nextLine();
                    System.out.println("Enter Your password:");
                    String usPass = input1.nextLine();
                    User.loginReq logReq = User.loginReq.newBuilder().setUsername(usName).setPassword(usPass).build();
                    User.APIresponse apiRes = userStub.login(logReq);
                    logger.info(apiRes.getResponseCode() + "\n" + apiRes.getResponseMessage());


            }
            else
            {
                break;
            }

        }

    }
}
