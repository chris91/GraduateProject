
package gr.chriswebenterprise.graduateproject;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author chris
 */
public class MainClass {
    
    public static void main(String[] args) {
        
        
        // MySQL Queries
        SQLClass sqlClass = new SQLClass();
        
        
        
        
        //FOF 1
        String query1 = "select n1.id, n2.id from nodes as n1, nodes as n2, edges "
                + "where n1.id=edges.from_node and n2.id=edges.to_node and n1.id=102581";
        //FOF 2
        String query2 = "select n1.id, n2.id, n3.id from nodes as n1, nodes as n2, nodes as n3, edges as e1, edges as e2 "
                + "where n1.id=e1.from_node and e1.to_node=n2.id and n2.id=e2.from_node and e2.to_node=n3.id and n1.id=102581";
        //FOF 3
        String query3 = "select n1.id, n2.id, n3.id, n4.id from nodes as n1, nodes as n2, nodes as n3, nodes as n4,"
                + " edges as e1, edges as e2, edges as e3 "
                + "where n1.id=e1.from_node and e1.to_node=n2.id and n2.id=e2.from_node and e2.to_node=n3.id"
                + " and n3.id=e3.from_node and e3.to_node=n4.id and n1.id=890254";
        //FOF 4
        String query4= "select n1.id, n2.id, n3.id, n4.id, n5.id from nodes as n1, nodes as n2, nodes as n3, nodes as n4,"
                + " nodes as n5, edges as e1, edges as e2, edges as e3, edges as e4 "
                + "where n1.id=e1.from_node and e1.to_node=n2.id and n2.id=e2.from_node and e2.to_node=n3.id"
                + " and n3.id=e3.from_node and e3.to_node=n4.id and n4.id=e4.from_node and e4.to_node=n5.id and n1.id=927";
        //FOF 5
        String query5= "select n1.id, n2.id, n3.id, n4.id, n5.id, n6.id from nodes as n1, nodes as n2, nodes as n3, nodes as n4,"
                + " nodes as n5, nodes as n6, edges as e1, edges as e2, edges as e3, edges as e4, edges as e5 "
                + "where n1.id=e1.from_node and e1.to_node=n2.id and n2.id=e2.from_node and e2.to_node=n3.id"
                + " and n3.id=e3.from_node and e3.to_node=n4.id and n4.id=e4.from_node and e4.to_node=n5.id"
                + " and n5.id=e5.from_node and e5.to_node=n6.id and n1.id=350459";
        
        //mysql> select e1.from_node, e6.to_node from edges as e1, edges as e2, edges as e3, edges as e4, edges as e5, edges as e6
        //where e1.from_node=350459 and e1.to_node=e2.from_node and e2.to_node=e3.from_node and e3.to_node=e4.from_node and e4.to
       //_node=e5.from_node and e5.to_node=e6.from_node;

        // attribute-based generic queries
        String query6 = "select id, views from nodes where id=3456";
        String query7 = "select id, views from nodes group by category";
        String query8 = "select id, views from nodes order by views";
        String query9 = "select count(*), views from nodes";
        
        
        sqlClass.queryDB(query1);
        sqlClass.printResults();
        
        // Neo4j Queries
        NeoClass neoTest = new NeoClass();
        neoTest.insertNodesAndRelationships();
        neoTest.insertProperties();
        
        
        
        //FOF 1
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='102581') match (n)-->(m) return n,m"));
        //FOF 2
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='102581') match (n)-[*2..2]->(m) return n,m"));
        //FOF 3
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='890254') match (n)-[*3..3]->(m) return n,m"));
        //FOF 4
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='927') match (n)-[*4..4]->(m) return n,m"));
        //FOF 5
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='736') match (n)-[*5..5]->(m) return n.nodeId,m.nodeId"));
        
        // single source shortest path
        
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='736'), m=node:WebSiteId('nodeId:*') "
                + "match p = shortestPath((n)-[:POINTS_TO*..6]->(m)) return p"));
        
        // attribute-based generic queries
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId(nodeId='3456') match (n) return n"));
        
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId('nodeId:*') return count(distinct n.category), n.category"));
        
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId('nodeId:*') return n order by n.views desc limit 1000"));
        
        System.out.println(neoTest.cypherQueriesInJava("start n=node:WebSiteId('nodeId:*') return count(distinct n.category)"));
        
        
        
        
    }
}
