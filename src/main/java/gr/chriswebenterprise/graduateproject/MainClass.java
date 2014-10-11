/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.chriswebenterprise.graduateproject;

/**
 *
 * @author chris91
 */
public class MainClass {
    
    public static void main(String[] args) {
        /*String query = "select * from nodes";
        SQLClass testClass = new SQLClass();
        testClass.queryDB(query);
        testClass.printResults();*/
        
        NeoClass neoTest = new NeoClass();
        neoTest.insertNodesAndRelationships();
    }
}
