package com.company;

import com.company.homework.Manager;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager(Connector.setConnection("root", "12345"));
        Engine engine = new Engine(manager);
        engine.run();
    }
}
