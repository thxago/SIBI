/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.controle;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


/**
 *
 * @author admin
 */
public class Login_usuario extends Window{


    private Window win;
    private Textbox lb_login;
    private Textbox lb_senha;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private GerenteDAO gerenteDAO = new GerenteDAO();
    private BiblioteconomistaDAO biblioteconomistaDAO = new BiblioteconomistaDAO();
    private AtendenteDAO atendenteDAO = new AtendenteDAO();
    private MaterialBibliograficoDAO materialBibliograficoDAO = new MaterialBibliograficoDAO();

    
    
    public void onCreate(){
        this.setLb_login((Textbox) getFellow("lb_login"));
        this.setLb_senha((Textbox) getFellow("lb_senha"));
//        Executions.sendRedirect("cad_usuario.zul");
    }
    


    public void efetuar_login(){
        
        if(getLb_login().getValue().isEmpty() || getLb_senha().getValue().isEmpty()){
            try{
                Messagebox.show("Campo Login e Senha estão vazios", "Login", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            } catch(Exception ex){
                Logger.getLogger(Login_usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        }else if(!getUsuarioDAO().buscarUsuario(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            boolean r = getUsuarioDAO().buscaPendencia(this.getLb_login().getValue());
            if(r){
                Messagebox.show("Você possui pendencias no sistema, procure a biblioteca assim que possível", "Controle de Usuários", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                
            }else{
                Executions.sendRedirect("menuUsuario.zul");
            }
            
        
        }else if(!getGerenteDAO().buscarGerente(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            Executions.sendRedirect("menuGerente.zul");
            
        }else if(!getAtendenteDAO().buscarAtendente(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            Executions.sendRedirect("menuAtendente.zul");
            
        }else if(!getBiblioteconomistaDAO().buscarBiblioteconomista(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            Executions.sendRedirect("menuBiblioteconomista.zul");

        }else{
            try{
                Messagebox.show("Usuario não encontrado", "Login", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            } catch(Exception ex){
                Logger.getLogger(Login_usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
//            Executions.sendRedirect("gerente.zul");
        }
    }

    public void esqueci_minha_senha(){
        Messagebox.show("Não podemos ajudar no momento", "Login", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
    }


    /**
     * @return the win
     */
    public Window getWin() {
        return win;
    }

    /**
     * @param win the win to set
     */
    public void setWin(Window win) {
        this.win = win;
    }

    /**
     * @return the lb_login
     */
    public Textbox getLb_login() {
        return lb_login;
    }

    /**
     * @param lb_login the lb_login to set
     */
    public void setLb_login(Textbox lb_login) {
        this.lb_login = lb_login;
    }

    /**
     * @return the lb_senha
     */
    public Textbox getLb_senha() {
        return lb_senha;
    }

    /**
     * @param lb_senha the lb_senha to set
     */
    public void setLb_senha(Textbox lb_senha) {
        this.lb_senha = lb_senha;
    }
    
    
    /**
     * @return the usuarioDAO
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    /**
     * @param usuarioDAO the usuarioDAO to set
     */
    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    
    /**
     * @return the gerenteDAO
     */
    public GerenteDAO getGerenteDAO() {
        return gerenteDAO;
    }

    /**
     * @param gerenteDAO the gerenteDAO to set
     */
    public void setGerenteDAO(GerenteDAO gerenteDAO) {
        this.gerenteDAO = gerenteDAO;
    }

    /**
     * @return the biblioteconomistaDAO
     */
    public BiblioteconomistaDAO getBiblioteconomistaDAO() {
        return biblioteconomistaDAO;
    }

    /**
     * @param biblioteconomistaDAO the biblioteconomistaDAO to set
     */
    public void setBiblioteconomistaDAO(BiblioteconomistaDAO biblioteconomistaDAO) {
        this.biblioteconomistaDAO = biblioteconomistaDAO;
    }

    /**
     * @return the atendenteDAO
     */
    public AtendenteDAO getAtendenteDAO() {
        return atendenteDAO;
    }

    /**
     * @param atendenteDAO the atendenteDAO to set
     */
    public void setAtendenteDAO(AtendenteDAO atendenteDAO) {
        this.atendenteDAO = atendenteDAO;
    }

    /**
     * @return the materialBibliograficoDAO
     */
    public MaterialBibliograficoDAO getMaterialBibliograficoDAO() {
        return materialBibliograficoDAO;
    }

    /**
     * @param materialBibliograficoDAO the materialBibliograficoDAO to set
     */
    public void setMaterialBibliograficoDAO(MaterialBibliograficoDAO materialBibliograficoDAO) {
        this.materialBibliograficoDAO = materialBibliograficoDAO;
    }
    
}
