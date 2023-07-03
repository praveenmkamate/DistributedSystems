package service.broker;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.Quotation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
public class Broker {

    static int id = 1001;
    static final int getId = 1001;

    Map<Integer, ClientApplication> resultMap = new HashMap<>();

    @RequestMapping(value="/applications",method= RequestMethod.GET)
    public List<ClientApplication> getClientApplications() {

        List<ClientApplication> resultList = new ArrayList<>();

        for(int i=getId;i<getId+resultMap.size();i++){
            resultList.add(resultMap.get(i));
        }

        return resultList;
    }

    @RequestMapping(value="/applications/{application-number}",method=RequestMethod.GET)
    public ClientApplication getClientApplication(@PathVariable("application-number") int applicationNumber) {
        return resultMap.get(applicationNumber);
    }

    @RequestMapping(value = "/applications", method = RequestMethod.POST)
    public ResponseEntity<ClientApplication> createApplication(@RequestBody ClientInfo info) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ClientInfo> request = new HttpEntity<>(info);
        List<Quotation> data = new ArrayList<>();

        for(String url: Application.urlList) {
            Quotation quotation =
                    restTemplate.postForObject(url,
                            request, Quotation.class);

            data.add(quotation);
        }

        HttpHeaders headers = new HttpHeaders();

        ClientApplication clientApplication = new ClientApplication(id,info,data);

        resultMap.put(id++,clientApplication);

        return new ResponseEntity<>(clientApplication, headers, HttpStatus.CREATED);

    }
}
