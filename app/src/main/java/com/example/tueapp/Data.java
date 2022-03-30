package com.example.tueapp;

import java.util.ArrayList;
import java.util.List;

public class Data {

    // It was much cleaner to create the list in a different file and then send the data to the MapFragment
    public static List getMyListData(){

        List<String> myList = new ArrayList<>();

        myList.add("Athene");
        myList.add("Atlas");
        myList.add("Auditorium");
        myList.add("Aurora");
        myList.add("BBC Reststoffencentrum");
        myList.add("Cascade");
        myList.add("Catalyst");
        myList.add("Ceres");
        myList.add("Connector");
        myList.add("Cyclotron");
        myList.add("Differ");
        myList.add("Echo");
        myList.add("Fenix");
        myList.add("Flux");
        myList.add("Fontys ER");
        myList.add("Fontys S1");
        myList.add("Fontys S2");
        myList.add("Fontys S3");
        myList.add("Gaslab");
        myList.add("Gemini");
        myList.add("Helix");
        myList.add("Impuls");
        myList.add("IPO");
        myList.add("Kennispoort");
        myList.add("Koepel");
        myList.add("Laplace");
        myList.add("Luna");
        myList.add("Matrix");
        myList.add("Metaforum");
        myList.add("Momentum");
        myList.add("Multimedia paviljoen");
        myList.add("Paviljoen");
        myList.add("Spectrum");
        myList.add("Studenten sportcentum");
        myList.add("Traverse");
        myList.add("Twinning center");
        myList.add("Ventur");
        myList.add("Vertigo");
        myList.add("Zwarte Doos");

        return myList;
    }
    public static double[] getLat() {

        double Lat[] = new double[100];

        Lat[0]= 51.44790482743093;
        Lat[1]= 51.44924448149617;
        Lat[2]= 51.44788039894837;

        return Lat;
    }
    public static double[] getLong() {

        double Long[] = new double[100];

        Long[0]= 5.486032776614522;
        Long[1]= 5.487699141970546;
        Long[2]= 5.495841144320372;

        return Long;
    }

}
