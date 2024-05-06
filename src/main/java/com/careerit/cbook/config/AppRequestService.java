package com.careerit.cbook.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
public class AppRequestService {

   // @Scheduled(cron = "0 */3 * ? * *")
    public void makeRestApiCall(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://cbook-server-app-usci.onrender.com/", String.class);
        log.info("Response : {}",response);
    }
}
