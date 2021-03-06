/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.dao.UserDAO;
import br.com.forum.model.User;
import java.io.Serializable;
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
@Named(value = "loginMB")
@ViewScoped
public class LoginMB implements Serializable{

    private String email = "";
    private String senha = "";
    /**
     * Creates a new instance of LoginMB
     */
    public LoginMB() {
    }
    
    public String logar(){
        UserDAO uDAO = new UserDAO();
        User u = new User();
        u = uDAO.buscarPorEmailESenha( email, senha );

        if( u != null ){
            email = "";
            senha = "";
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = ( HttpServletRequest ) context.getExternalContext().getRequest();
            HttpSession session = request.getSession();
            session.setAttribute( "user", u );
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso","Login realizado"));
            
            return "index.xhtml";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro", "Por favor verifique sua senha e login"));
            return "";
        }
           
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
    
    
    
}
