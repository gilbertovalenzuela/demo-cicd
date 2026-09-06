package com.cicd.facil;

import org.springframework.stereotype.Repository;

@Repository
public class DemoRepository {

    public String getUserName() {
        return "DemoName";
    }

}
