/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.model;

import org.bson.types.ObjectId;

/**
 *
 * @author SUPORTE116
 */
public class User {
    private ObjectId _id;
    private String nome;
    private String email;
    private String senha;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    




    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "User{" + "_id=" + _id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + '}';
    }
    
    
}
