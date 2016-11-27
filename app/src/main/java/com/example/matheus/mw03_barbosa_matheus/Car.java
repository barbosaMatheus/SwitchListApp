package com.example.matheus.mw03_barbosa_matheus;

import java.io.Serializable;

/**
 * Created by matheus on 11/26/16.
 */

public class Car implements Serializable {
    public String road_name; //road abbreviation
    public long car_number;  //railroad car number
    public String location;  //destination or origin
    public boolean selected; //true if the item is selected on the list

    public Car( String name, long number, String loc ) {
        this.road_name = name;
        this.car_number = number;
        this.location = loc;
        this.selected = false;
    }
}
