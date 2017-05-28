/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.dao.TopicoDAO;
import br.com.forum.model.Topico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
    
    private List<Topico> topicos = new ArrayList<>(); 
    
    public indexMB() {
    }
    
    @PostConstruct
    public void init(){
        try{
        
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
    
    
    
}
