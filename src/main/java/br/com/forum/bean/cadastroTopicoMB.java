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
import java.util.ArrayList;
import java.util.Date;
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
@Named(value = "cadastroTopicoMB")
@ViewScoped
public class cadastroTopicoMB implements Serializable {

    private Topico t;
    private User u;
    
    /**
     * Creates a new instance of cadastroTopicoMB
     */
    public cadastroTopicoMB() {
       
    }
    
    @PostConstruct
    public void init(){
        t = new Topico();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            u = (User) session.getAttribute("user");
        }
        
    }
    
    public String salvar(){

        if(u != null){
            
            Autor a = new Autor();
            a.setNome(u.getNome());
            a.setEmail(u.getEmail());
            t.setAutor(a);

            t.setData(new Date());
            List<Mensagem> mensagens = new ArrayList<>();
            t.setMensagens(mensagens);

            TopicoDAO tDAO = new TopicoDAO();
            if(tDAO.salvar(t))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso","Tópico Inserido"));
                t = new Topico();
                return "index.xhtml";
            }else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro","Não foi possivel inserir o tópico, por favor consulte o administrador do sistema"));
                return "";
            }
        }else
        {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro","Por favor faça o login para inserir um tópico"));
            return "";
        }

        
    }

    public Topico getT() {
        return t;
    }

    public void setT(Topico t) {
        this.t = t;
    }
    
}
