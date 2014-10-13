/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.chriswebenterprise.graduateproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

/**
 *
 * @author chris91
 */
public class NeoClass {

    private GraphDatabaseService graphDb;
    private Node node;
    private ExecutionEngine engine;
    //private Node firstNodes;
    //private Node secondNodes;
    private Relationship relationships;
    //private IndexDefinition indexDefinition;
    private long startTime, endTime, elapsedTime;
    Index<Node> nodeIndex;

    private static enum RelTypes implements RelationshipType {

        POINTS_TO
    }

    public NeoClass() {
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("C:\\Users\\chris91\\Desktop\\WebGoogleNeo4jDB");
        registerShutdownHook(graphDb);

        nodeIndex = graphDb.index().forNodes("WebSiteId");

    }

    private Node CreateAndIndexNode(String myNodeID) {

        Node tmpNode = graphDb.createNode();
        tmpNode.setProperty("nodeId", myNodeID);
        Node newNode;
        newNode = nodeIndex.putIfAbsent(tmpNode, "nodeId", myNodeID);
        if (newNode != null) {
            return newNode;
        } else {
            return tmpNode;
        }
    }

    public void insertNodesAndRelationships() {

        for (int i = 0; i <= 510; i++) {
            Transaction tx = graphDb.beginTx();
            try {
                String InputFile = "C:\\Users\\chris91\\Desktop\\Snap Datasets\\good sets\\webGOOGLE\\edges_file" + i + ".txt";
                //String InputFile="data/edges.txt";
                startTime = System.currentTimeMillis();

                FileReader fr = new FileReader(InputFile);
                BufferedReader br1 = new BufferedReader(fr);

                String RelType, line, array[];
                int j = 0;

                //Label label = DynamicLabel.label("WebSiteId");
                while ((line = br1.readLine()) != null) {

                    Node insertedNodeNo1 = null;
                    Node insertedNodeNo2 = null;
                    String firstNodeID, secondNodeID;

                    array = line.split("\t");
                    if (array.length != 2) {
                        continue;
                    }
                    j++;
                    firstNodeID = array[0];
                    insertedNodeNo1 = this.CreateAndIndexNode(firstNodeID);

                    secondNodeID = array[1];
                    insertedNodeNo2 = this.CreateAndIndexNode(secondNodeID);
                    if (insertedNodeNo2 != null) {
                        if (insertedNodeNo1 != null) {
                            relationships = insertedNodeNo1.createRelationshipTo(insertedNodeNo2, RelTypes.POINTS_TO);
                            //relationships.setProperty( "RelationshipType", RelType );
                        }
                        // System.out.println("2: Successfull Node Insertion to Index");
                    } else {
                        //System.out.println("2: FAILURE, Node allready exists");

                    }
                    //relationship = insertedNodeNo1.createRelationshipTo(insertedNodeNo2, RelTypes.POINTS_TO);
                    //System.out.println(j+" :      "+ relationship.toString());
                }

                endTime = System.currentTimeMillis();
                elapsedTime = endTime - startTime;
                System.out.println("Iteration:   " + i);
                System.out.println("Elapsed Time:   " + elapsedTime + " ms");

                tx.success();
            } catch (IOException e) {
                System.out.println("IO Error" + e);
                System.exit(1);
            } finally {
                tx.close();
            }
            //System.exit(1);
        }
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
}
