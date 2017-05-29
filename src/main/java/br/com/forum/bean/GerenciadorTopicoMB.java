/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.dao.TopicoDAO;
import br.com.forum.model.Autor;
import br.com.forum.model.Mensagem;
import br.com.forum.model.Topico;
import br.com.forum.model.User;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vinicius
 */
@Named(value = "gerenciadorTopicoMB")
@ViewScoped
public class GerenciadorTopicoMB implements Serializable {

    
    private Topico t;
    private Mensagem m ;
    private User u;
   
    
    /**
     * Creates a new instance of GerenciadorTopicoMB
     */
    public GerenciadorTopicoMB() {
    }
    
    @PostConstruct
    public void init(){
        m = new Mensagem();

        t = new Topico();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute("topico") != null ){
            t = (Topico) session.getAttribute("topico");
        }
        if( session.getAttribute("user") != null ){
            u = (User) session.getAttribute("user");
        }
        
    }

    public Topico getT() {
        return t;
    }

    public void setT(Topico t) {
        this.t = t;
    }

    public Mensagem getM() {
        return m;
    }

    public void setM(Mensagem m) {
        this.m = m;
    }
    
    
    
    public void salvarNovaMensagem(){
        if(u != null){
            Autor a = new Autor();
            a.setEmail(u.getEmail());   
            a.setNome(u.getNome());
            m.setAutor(a);
            m.setData(new Date());
            System.out.println("" + t.getId());
            t.getMensagens().add(0, m);

            TopicoDAO tDAO = new TopicoDAO();
            tDAO.update(t, t.getId().toString());
            System.out.println("salvo com sucesso");
            m = new Mensagem();
            
        }else{
            System.out.println("Por favor faça o login para inserir uma nova mensagem");
        }
        

    }
    
    
    
    
    
    
    
}
