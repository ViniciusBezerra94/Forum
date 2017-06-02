/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.dao.TopicoDAO;
import br.com.forum.model.Topico;
import br.com.forum.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SUPORTE116
 */
@Named(value = "indexMB")
@ViewScoped
public class indexMB implements Serializable{

    /**
     * Creates a new instance of indexMB
     */
    private User u ;
    private List<Topico> topicos = new ArrayList<>(); 
    
    public indexMB() {
    }
    
    @PostConstruct
    public void init(){
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession session = request.getSession();
            u = (User) session.getAttribute("user");
            
            
            
            
        TopicoDAO tDAO = new TopicoDAO();
        topicos = tDAO.listar();
        }catch(Exception e) {
            System.out.println("Erro");
        }
    }

    public List<Topico> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<Topico> topicos) {
        this.topicos = topicos;
    }
    
    
    public String visualizar(Topico t){
            System.out.println("passou por aqui");
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("topico", t);
            
            return "respostaTopico.xhtml";
    }
    
    public boolean isLogou(){
        return u != null && u.getId() != null;
    }
    
    
    public String logout() {
        u = null;
        
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("user");

        return "index";
    }
    
    
    public String getNomeUsuarioLogado(){
        if(u.getNome().length() > 20){
            return u.getNome().substring(0, 20) + "...";
        }
        return u.getNome();
        
    }
    
    public boolean isRemove(Topico t){
        if( u != null )
        {
            if( u.getEmail().equalsIgnoreCase(t.getAutor().getEmail()) ){
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    public void remover(Topico t){
        try
        {
            TopicoDAO tDAO = new TopicoDAO();
            if( tDAO.remove( t.getId().toString() ) )
            {
                topicos.remove(t);
                FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso","Tópico removido" ) );
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"Erro","Não foi possivel remover o tópico, por favor consulte o administrador do sistema" ) );
            }
        }catch(Exception e)
        {
                FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"Erro", e.getMessage() ) );
        }
        
    }
    
    
}
