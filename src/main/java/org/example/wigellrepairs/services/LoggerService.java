package org.example.wigellrepairs.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    private final Logger funcLog = LogManager.getLogger("functionality");
    private final Logger errLog = LogManager.getLogger("error");

    public void info(String txt){
        funcLog.info(txt);
    }

    public void error(String txt) {
        errLog.error(txt);
    }
}
