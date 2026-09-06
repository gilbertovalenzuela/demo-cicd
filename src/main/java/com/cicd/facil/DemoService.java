package com.cicd.facil;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final DemoRepository repository;

    public DemoService(DemoRepository repository) {
        this.repository = repository;
    }

    public String getUserName() {
        String userName = repository.getUserName();
        return userName;
    }

}
