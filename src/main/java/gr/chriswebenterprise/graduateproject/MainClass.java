/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.chriswebenterprise.graduateproject;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author chris91
 */
public class MainClass {
    
    public static void main(String[] args) {
        
        ArrayList<String> listOfCategories= new ArrayList();
        listOfCategories.add("News");
        listOfCategories.add("Social Media");
        listOfCategories.add("Sports");
        listOfCategories.add("Music");
        listOfCategories.add("Media Streaming");
        listOfCategories.add("Movie & Film");
        listOfCategories.add("Games");
        listOfCategories.add("Shopping");
        listOfCategories.add("Education");
        listOfCategories.add("Employment");
        listOfCategories.add("Food & Drink");
        listOfCategories.add("Science");
        listOfCategories.add("Consumer Electronics");
        listOfCategories.add("Guides/Ratings/Reviews");
        listOfCategories.add("Radio & Podcasts");
        listOfCategories.add("Portfolio");
        listOfCategories.add("Travel");
        listOfCategories.add("Email Newsletters");
        listOfCategories.add("Gonvernment");
        listOfCategories.add("University");
        listOfCategories.add("Personal Blog/Website");
        
        Random randomGenerator = new Random();
        SQLClass testClass = new SQLClass();
        //String query = "select count(*) from nodes";
        //String updtQuery = "update nodes set views = (ceil(rand() * 100000))";
        String catChoise;
        int randNum;
        //String updtQuery = "update nodes set category = 'catChoise' where category = 'null'";
        //testClass.insertRandomValuesIntoNodes(updtQuery);
        for(int i=0; i<875713; i++){
            catChoise = listOfCategories.get(randomGenerator.nextInt(listOfCategories.size()));
            //System.out.println(catChoise);
            //String updtQuery = "update nodes set category = concat(category,'" + listOfCategories.get(randomGenerator.nextInt(listOfCategories.size())).toString() + "')";
            randNum = randomGenerator.nextInt(916427 + 1);
            String updtQuery = "update nodes set category =\"" + catChoise + "\" where id =" +  randNum + " and category = \"catChoise \" ";
            testClass.insertRandomValuesIntoNodes(updtQuery);
        }
    
        //testClass.queryDB(query);
        //testClass.printResults();
        
        //NeoClass neoTest = new NeoClass();
        ///neoTest.insertNodesAndRelationships();
    }
}
