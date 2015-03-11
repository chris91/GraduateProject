
package gr.chriswebenterprise.graduateproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

/**
 *
 * @author chris
 */
public class NeoClass {

    private final GraphDatabaseService graphDb;
    private Relationship relationships;
    private long startTime, endTime, elapsedTime;
    private Index<Node> nodeIndex;

    private static enum RelTypes implements RelationshipType {

        POINTS_TO
    }

    public NeoClass() {

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("C:\\Users\\chris91\\Desktop\\WebGoogleNeo4jDB26_11");
        this.registerShutdownHook(graphDb);
        try (Transaction tx = graphDb.beginTx()) {
            nodeIndex = graphDb.index().forNodes("WebSiteId");
        }
    }

    private Node createAndIndexNode(String myNodeID) {

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
                
                startTime = System.currentTimeMillis();

                FileReader fr = new FileReader(InputFile);
                BufferedReader br1 = new BufferedReader(fr);

                String RelType, line, array[];
                int j = 0;

               
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
                    insertedNodeNo1 = this.createAndIndexNode(firstNodeID);

                    secondNodeID = array[1];
                    insertedNodeNo2 = this.createAndIndexNode(secondNodeID);
                    if (insertedNodeNo2 != null) {
                        if (insertedNodeNo1 != null) {
                            relationships = insertedNodeNo1.createRelationshipTo(insertedNodeNo2, RelTypes.POINTS_TO);
                            
                        }
                       
                    } 
                    
                }

                endTime = System.currentTimeMillis();
                elapsedTime = endTime - startTime;
                System.out.println("Iteration:   " + i);
                System.out.println("Elapsed Time:   " + elapsedTime + " ms");

                tx.success();
            } catch (IOException e) {
                System.out.println("IO Error" + e);

            } finally {
                tx.close();
            }
            
        }
    }

    public void insertProperties() {

        FileReader fr = null;
        try {
            ExecutionEngine engine = new ExecutionEngine(graphDb);
            String InputFile = "C:\\Users\\chris91\\Desktop\\node_properties.csv";
            startTime = System.currentTimeMillis();
            fr = new FileReader(InputFile);
            BufferedReader br1 = new BufferedReader(fr);
            String line, array[], nodeId, category, views, query, resultString;
            
            while ((line = br1.readLine()) != null) {
                array = line.split("\t");

                nodeId = array[0];
                category = array[1];
                views = array[2];
                
                query = "start n=node:WebSiteId(nodeId='"+nodeId+"') set n.category = '"+category+"', n.views = '"+views+"' return n";

                resultString = engine.execute(query).dumpToString();
            
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NeoClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NeoClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(NeoClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String cypherQueriesInJava(String query) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        String resultString;
        startTime = System.currentTimeMillis();
        resultString = engine.execute(query).dumpToString();
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Elapsed Time:   " + elapsedTime + " ms");

        return resultString;
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
}
