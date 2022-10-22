package service.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static List<String> urlList = new ArrayList<>();
    public static void main(String[] args) {
        String host = "localhost";
        int port = 0;

        for (int i=0; i < args.length; i++) {

            if(args[i].trim().equals("-h")){
                host = args[++i];
                i++;
            }

            if(args[i].trim().equals("-p")){
                port = Integer.parseInt(args[++i]);
            }

            urlList.add("http://"+host+":"+port+"/quotations");
        }

        SpringApplication.run(Application.class, args);
    }
}