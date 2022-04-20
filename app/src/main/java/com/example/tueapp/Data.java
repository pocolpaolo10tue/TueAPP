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

    //The Latitudes
    public static double[] getLat() {

        double[] Lat = new double[100];

        //Athene
        Lat[0]= 51.446033745316804;
        //Atlas
        Lat[1]= 51.44739466117641;
        //Auditorium
        Lat[2]= 51.447954608386354;
        //Aurora
        Lat[3]= 51.450075918238625;
        //BBC Reststoffencentrum
        Lat[4]= 51.44700733628367;
        //Cascade
        Lat[5]= 51.44686270761628;
        //Catalyst
        Lat[6]= 51.44969881477664;
        //Ceres
        Lat[7]= 51.44716859939337;
        //Connector
        Lat[8]= 51.45063836990312;
        //Cyclotron
        Lat[9]= 51.44628236492782;
        //Differ
        Lat[10]= 51.44878225944901;
        //Echo
        Lat[11]= 51.446425497530015;
        //Fenix
        Lat[12]= 51.44768645517391;
        //Flux
        Lat[13]= 51.44703832371395;
        //Fontys ER
        Lat[14]= 51.44657218752646;
        //Fontys S1
        Lat[15]= 51.44928630254935;
        //Fontys S2
        Lat[16]= 51.44937464052409;
        //Fontys S3
        Lat[17]= 51.44992586346372;
        //Gaslab
        Lat[18]= 51.44605727944994;
        //Gemini
        Lat[19]= 51.44743830890194;
        //Helix
        Lat[20]= 51.44624312576041;
        //Impuls
        Lat[21]= 51.44906172014148;
        //IPO
        Lat[22]= 51.449802429370564;
        //Kennispoort
        Lat[23]= 51.44597877408315;
        //Koepel
        Lat[24]= 51.44857162479822;
        //Laplace
        Lat[25]= 51.448886976522985;
        //Luna
        Lat[26]= 51.4493225824409;
        //Matrix
        Lat[27]= 51.44643922991537;
        //Metaforum
        Lat[28]= 51.447649922146816;
        //Momentum
        Lat[29]= 51.447943629117034;
        //Multimedia paviljoen
        Lat[30]= 51.44968831650282;
        //Paviljoen
        Lat[31]= 51.450648703668996;
        //Spectrum
        Lat[32]= 51.44775685957061;
        //Studenten sportcentum"
        Lat[33]= 51.45212991602611;
        //Traverse
        Lat[34]= 51.44930751705899;
        //Twinning center
        Lat[35]= 51.449930686796854;
        //Ventur
        Lat[36]= 51.446849906788394;
        //Vertigo
        Lat[37]= 51.446328406270865;
        //Zwarte Doos
        Lat[38]= 51.44637163051971;

        return Lat;
    }

    //The Longitudes
    public static double[] getLong() {

        double[] Long = new double[100];

        //Athene
        Long[0]= 5.488759271408982;
        //Atlas
        Long[1]= 5.486122099846816;
        //Auditorium
        Long[2]= 5.484687710373851;
        //Aurora
        Long[3]= 5.489511246681518;
        //BBC Reststoffencentrum
        Long[4]= 5.494733935257754;
        //Cascade
        Long[5]= 5.49320905531146;
        //Catalyst
        Long[6]= 5.496452069276067;
        //Ceres
        Long[7]= 5.48867344037513;
        //Connector
        Long[8]= 5.490853223555441;
        //Cyclotron
        Long[9]= 5.492247796212145;
        //Differ
        Long[10]= 5.494763074372235;
        //Echo
        Long[11]= 5.493719996294055;
        //Fenix
        Long[12]= 5.4972076565311045;
        //Flux
        Long[13]= 5.492240261758868;
        //Fontys ER
        Long[14]= 5.489826760791369;
        //Fontys S1
        Long[15]= 5.491578968513589;
        //Fontys S2
        Long[16]= 5.49255484758124;
        //Fontys S3
        Long[17]= 5.493262325410845;
        //Gaslab
        Long[18]= 5.486583960767254;
        //Gemini
        Long[19]= 5.489571487215056;
        //Helix
        Long[20]= 5.4876281408046195;
        //Impuls
        Long[21]= 5.486545494241401;
        //IPO
        Long[22]= 5.491445143144659;
        //Kennispoort
        Long[23]= 5.482070686024018;
        //Koepel
        Long[24]= 5.497650247726285;
        //Laplace
        Long[25]= 5.48900299813972;
        //Luna
        Long[26]= 5.487778288826113;
        //Matrix
        Long[27]= 5.486622712908059;
        //Metaforum
        Long[28]= 5.487464028932002;
        //Momentum
        Long[29]= 5.496116287667789;
        //Multimedia paviljoen
        Long[30]= 5.49476766723666;
        //Paviljoen
        Long[31]= 5.495089967559111;
        //Spectrum
        Long[32]= 5.493146532003661;
        //Studenten sportcentum"
        Long[33]= 5.489451347420352;
        //Traverse
        Long[34]= 5.489776273414294;
        //Twinning center
        Long[35]= 5.497164891648972;
        //Ventur
        Long[36]= 5.495808514307252;
        //Vertigo
        Long[37]= 5.48506532067811;
        //Zwarte Doos
        Long[38]= 5.48433211918479;

        return Long;
    }

}
