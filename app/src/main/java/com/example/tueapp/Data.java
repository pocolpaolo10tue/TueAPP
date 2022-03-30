package com.example.tueapp;

import java.util.ArrayList;
import java.util.List;

public class Data {

    // It was much cleaner to create the list in a different file and then send the data to the MapFragment
    public static List getMyListData(){

        List<String> myList = new ArrayList<>();

        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");

        return myList;
    }
}
