package com.company;

import com.company.homework.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Engine implements Runnable{

    private final Manager manager;
    private final BufferedReader reader;

    public Engine(Manager manager) {
        this.manager = manager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            int num;
            System.out.println("Please enter exercise number (2-9) you want to test");
            System.out.println("If you want to exit, press 0");
            try{
                num = Integer.parseInt(this.reader.readLine());
                if (num == 0) {
                    break;
                }
                switch (num) {
                    case 2 -> this.manager.getVillainsNamesEx2();
                    case 3 -> {
                        System.out.println("Please enter villain id.");
                        num = Integer.parseInt(this.reader.readLine());
                        this.manager.getMinionNamesEx3(num);
                    }
                    case 4 -> {
                        System.out.println("Please enter minions info: name, age, town name:");
                        String[] minionsInfo = this.reader.readLine().split("\\s+");
                        String name = minionsInfo[0];
                        int age = Integer.parseInt(minionsInfo[1]);
                        String townName = minionsInfo[2];
                        System.out.println("Please enter villain name:");
                        String villain = this.reader.readLine();
                        manager.addMinionEx4(name, age, townName, villain);
                    }
                    case 5 -> {
                        System.out.println("Please enter country name:");
                        String country = this.reader.readLine();
                        this.manager.changeTownNameCasingEx5(country);
                    }
                    case 6 -> {
                        System.out.println("Please enter villain ID:");
                        int id = Integer.parseInt(this.reader.readLine());
                        this.manager.removeVillainEx6(id);
                    }
                    case 7 -> {
                        this.manager.printAllMinionNamesEx07();
                    }
                    case 8 ->{
                        System.out.println("Please enter IDs");
                        String[] ids = this.reader.readLine().split("\\s+");
                        this.manager.increaseMinionsAgeEx8(ids);
                    }
                    case 9 -> {
                        System.out.println("Please enter minion ID");
                        int minionId = Integer.parseInt(this.reader.readLine());
                        this.manager.increaseAgeWithProcedureEx9(minionId);
                    }
                    default -> System.out.println("There is no such exercise.");
                }
            }catch (IOException | SQLException e){
                e.printStackTrace();
            }
        }
    }
}
