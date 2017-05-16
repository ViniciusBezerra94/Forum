/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.model.Postagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author vinicius
 */
@Named(value = "mensageriaMB")
@ViewScoped
public class MensageriaMB implements Serializable{

    /**
     * Creates a new instance of MensageriaMB
     */
    public MensageriaMB() {
    }
    
    
     private String mensagem = "";
    private List<Postagem> postagens = new ArrayList<Postagem>();

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<Postagem> postagens) {
        this.postagens = postagens;
    }
    
    

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public void mostrarMensagem(){
        postagens.add(new Postagem("Vinicius", mensagem));
        mensagem = "";
        
    }
    
    
    
    
}
