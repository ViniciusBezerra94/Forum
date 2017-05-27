/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.dao;

import br.com.forum.model.User;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author SUPORTE116
 */
public class UserDAO 
{
    private MongoClient mongoClient;
    private DB db;
    private DBCollection col;
    private Gson gson = new Gson();
    
    
    public boolean isConnectedToMongo()
    {
        Mongo mongo = new Mongo();
        DBObject ping = new BasicDBObject( "ping", "1" );
        try 
        {
              mongo.getDB( "mydb" ).command( ping );
              mongo.close();
              return true;
        }
        catch( MongoException e ) 
        {
              return false;
        }
       
    }
    
    
    
    public boolean salvar( User u )
    {
        
        
        try
        {
            
           
            if( isConnectedToMongo() )
            {
            
            
            mongoClient = new MongoClient( "localhost" , 27017 );
          

            db = mongoClient.getDB( "mydb" );
      
            col = db.getCollection( "users" );
            String json = gson.toJson( u );
            DBObject dbObject = ( DBObject ) JSON.parse( json );
                
               
            return col.insert( dbObject ).wasAcknowledged();

            
            }
            else
            {
                
                
                System.out.println( "erro ao fazer conexao com o banco" );
                return false;
                
                
            }


            
        }
        catch( MongoException e )
        {
            
            
            System.out.println( "erro: " + e.getMessage() );
            return false;
            
            
        }
        finally
        {

            if( mongoClient != null )
            {
                
                mongoClient.close();
                
            }
        }
        
    }
    public boolean update(User u, String id)
    {
            try
            {
                if( isConnectedToMongo() )
                {
                    mongoClient = new MongoClient( "localhost" , 27017 );      
                    db = mongoClient.getDB( "mydb" );
                    col = db.getCollection( "users" );
                    String js = gson.toJson( u );
                    DBObject dbObject = (DBObject)JSON.parse( js );
                    dbObject.removeField( "_id" );
                    dbObject.put( "_id", new ObjectId( id ) );
                    BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
                    return col.update(query, dbObject).wasAcknowledged();
                }
                else
                {
                    System.out.println( "erro ao conectar ao banco" );
                    return false;
                }
            }
            catch( MongoException e )
            {
                System.out.println( "erro ao relizar update" );
                return false;
            }
            finally{
                if( mongoClient != null )
                {
                    mongoClient.close();

                }                
            }
                
                
                

        }
        
        public boolean remove(  String id )
        {
        try
        {
            if( isConnectedToMongo() )
            {
                mongoClient = new MongoClient( "localhost" , 27017 );
                db = mongoClient.getDB( "mydb" );
                DBCollection col = db.getCollection( "users" );
                BasicDBObject query = new BasicDBObject().append( "_id", new ObjectId( id ) );
                return col.remove(query).wasAcknowledged();
            }
            else
            {
                System.out.println( "erro ao conectar no banco" );
                return false;
            }
            
        }
        catch( Exception e )
        {
            System.out.println( "erro ao remover do banco" );
            return false;
            
        }
        finally
        {
            
            if( mongoClient != null )
            {
                mongoClient.close();
                
            }
        }
        
        }
        
        public List<User> listar()
        {
            List<User> users = new ArrayList<>();
            try{
                if( isConnectedToMongo() )
                {
                    mongoClient = new MongoClient( "localhost" , 27017 );
                    db = mongoClient.getDB( "mydb" );
                    col = db.getCollection( "users" );



                    DBCursor cursor = col.find();
                    while( cursor.hasNext() ) 
                    {
                        DBObject dobject = cursor.next();
                        String json = dobject.toString();
                        Gson gson = new Gson();
                        User u = gson.fromJson( json, User.class );
                        users.add( u );
                    }
                }
                else
                {
                    System.out.println("erro ao conectar no banco");
                }

            }
            catch( Exception e )
            {
                System.out.println("Erro ao listar");
            }
            finally
            {
                if(mongoClient != null)
                {
                    mongoClient.close();

                }
            }
            return users;
        }
        
        public User buscarPorEmailESenha( String email,String senha ){
             try{
                    if(isConnectedToMongo())
                    {
                        mongoClient = new MongoClient( "localhost" , 27017 );
                        db = mongoClient.getDB( "mydb" );
                        col = db.getCollection( "users" );
                        BasicDBObject query = new BasicDBObject();
                        query.append( "email", email );
                        query.append( "senha", senha );
                        DBObject obj = col.findOne( query );
                        User u = new User();
                        u = gson.fromJson( obj.toString(), User.class );
                        return u;



                    }
                    else
                    {
                        System.out.println( "erro ao conectar no banco" );
                        return null;
                    }

            }
            catch( Exception e )
            {
                return null;
            }
            finally
            {
                if(mongoClient != null)
                {
                    mongoClient.close();

                }
                
            }
        }
        
        
        
}