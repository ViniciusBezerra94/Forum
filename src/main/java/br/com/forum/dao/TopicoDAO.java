/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.dao;

import br.com.forum.model.Mensagem;
import br.com.forum.model.Topico;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author SUPORTE116
 */
public class TopicoDAO {
    private MongoClient mongoClient;
    private DB db;
    private DBCollection col;
    private Gson gson = new Gson();
    
    
    public boolean isConnectedToMongo(){
        Mongo mongo = new Mongo();
        DBObject ping = new BasicDBObject( "ping", "1" );
        try {
              mongo.getDB( "mydb" ).command( ping );
              mongo.close();
              return true;
        } catch (MongoException e) {
              return false;
        }
    }
    
    
    
    public boolean salvar( Topico t ){
        try{

            if( isConnectedToMongo() ){
            
            
            mongoClient = new MongoClient( "localhost" , 27017 );
          

            db = mongoClient.getDB( "mydb" );
      
            col = db.getCollection( "topicos" );
            String json = gson.toJson( t );
            DBObject dbObject = ( DBObject ) JSON.parse( json );
            return col.insert( dbObject ).wasAcknowledged();
            
            }else{
                System.out.println( "erro ao fazer conexao com o banco" );
                return false;
            }


            
        }catch( MongoException e ){
            System.out.println( "erro: " + e.getMessage() );
            return false;
        }finally{           
            if(mongoClient != null){
                mongoClient.close();
                
            }
        }
    }
        public boolean update(Topico t ,String id){
            try
            {
                if(isConnectedToMongo()){
                    mongoClient = new MongoClient( "localhost" , 27017 );      
                    db = mongoClient.getDB("mydb");
                    col = db.getCollection("topicos");
                    String js = gson.toJson(t);
                    DBObject dbObject = (DBObject)JSON.parse(js);
                    dbObject.removeField("_id");
                    dbObject.put("_id",new ObjectId(id));
                    BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(id));
                    return col.update(query, dbObject).wasAcknowledged();
                }else{
                    System.out.println("erro ao conectar ao banco");
                    return false;
                }
            }catch(MongoException e){
                System.out.println("erro ao relizar update");
                return false;
            }finally
            {
                if(mongoClient != null)
                {
                    mongoClient.close();
                
                }
            }    
                
                
                

        }
        
        public boolean remove(String id){
        try{
            if(isConnectedToMongo()){
                mongoClient = new MongoClient( "localhost" , 27017 );
                db = mongoClient.getDB("mydb");
                col = db.getCollection("topicos");
                BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(id));
                return col.remove(query).wasAcknowledged();
            }else{
                System.out.println("erro ao conectar no banco");
                return false;
            }
            
        }catch(Exception e ){
            System.out.println("erro ao remover do banco");
            return false;
            
        }finally{
            
            if(mongoClient != null){
                mongoClient.close();
                
            }
        }
        
        }
        
        public List<Topico> listar()
        {
            List<Topico> topicos = new ArrayList<>();
            try{
                if(isConnectedToMongo())
                {
                    mongoClient = new MongoClient( "localhost" , 27017 );
                    db = mongoClient.getDB("mydb");
                    col = db.getCollection("topicos");
                    BasicDBObject sort = new BasicDBObject();
                    sort.append("_id", -1);



                    DBCursor cursor = col.find().sort(sort);
                    while(cursor.hasNext()) 
                    {
                        DBObject dobject = cursor.next();
                        String json = dobject.toString();
                        String certoId = dobject.get("_id").toString();
                        Gson gson = new Gson();
                        Topico t = gson.fromJson(json, Topico.class);
                        t.setId(new ObjectId(certoId));

                        topicos.add(t);
                    }
                }
                else
                {
                    System.out.println("erro ao conectar no banco");
                    return null;
                }

            }
            catch(Exception e)
            {
                return null;
            }
            finally
            {
                if(mongoClient != null){
                    mongoClient.close();

                }
            }
            return topicos;
        }    
}
