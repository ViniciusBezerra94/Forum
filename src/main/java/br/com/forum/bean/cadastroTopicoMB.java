/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.dao.TopicoDAO;
import br.com.forum.model.Mensagem;
import br.com.forum.model.Topico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author SUPORTE116
 */
@Named(value = "cadastroTopicoMB")
@ViewScoped
public class cadastroTopicoMB implements Serializable {

    private Topico t;
    
    /**
     * Creates a new instance of cadastroTopicoMB
     */
    public cadastroTopicoMB() {
       
    }
    
    @PostConstruct
    public void init(){
        t = new Topico();
    }
    
    public void salvar(){
        t.setAutor("anderson");
        t.setData(new Date());
        List<Mensagem> mensagens = new ArrayList<>();
        t.setMensagens(mensagens);
        
        TopicoDAO tDAO = new TopicoDAO();
        tDAO.salvar(t);
        t = new Topico();
        
    }

    public Topico getT() {
        return t;
    }

    public void setT(Topico t) {
        this.t = t;
    }
    
}
