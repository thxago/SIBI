/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.controle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sibi.DAO.SolicitarEmprestimoDAO;
import sibi.persistencia.SolicitarEmprestimo;

/**
 *
 * @author admin
 */
public class Solicitar_emprestimo extends Window {


    private Window win;
    private Label cd_emprestimo;
    private Label cd_solicitacao;
    private Datebox dt_emprestimo;
    private Datebox dt_devolucao;
    private Intbox nu_renovacao;
    private Textbox lt_situacao;
    
    private Intbox cd_material_bibliografico;
    private Textbox lt_nome;
    private Textbox lt_autor;
    private Textbox lt_genero;
    private Intbox nu_quantidade_estoque;
    
    private Intbox cd_usuario;
    private Textbox nm_usuario;
    private Textbox lt_status;
    
    private Textbox vl_pesquisa;
    private Listbox resultados;
    private Popup ppPesquisar;
    private Listbox resultados_usuario;
    private Listbox resultados_material;
    
    private Popup ppPesquisarUsuario;
    private Popup ppPesquisarMaterial;
    private Textbox pnm_material;
    private Textbox pnm_usuario;
    
    private List resultadosPesquisa = new ArrayList();
    private List resultadosPesquisaUsuario = new ArrayList();
    private List resultadosPesquisaMaterial = new ArrayList();
    
    private SolicitarEmprestimo resultadoSelecionado = new SolicitarEmprestimo();
    
    private SolicitarEmprestimoDAO DAO = new SolicitarEmprestimoDAO();
    private SolicitarEmprestimo ent = new SolicitarEmprestimo();
    
    
    public void onCreate(){
        setWin((Window) getFellow("win"));
     
        this.setCd_solicitacao((Label) getFellow("cd_solicitacao"));
        this.setDt_emprestimo((Datebox) getFellow("dt_emprestimo"));
        this.setDt_devolucao((Datebox) getFellow("dt_devolucao"));
        this.setLt_situacao((Textbox) getFellow("lt_situacao"));
        this.setNu_renovacao((Intbox) getFellow("nu_renovacao"));
        this.setCd_material_bibliografico((Intbox) getFellow("cd_material_bibliografico"));
        this.setLt_nome((Textbox) getFellow("lt_nome"));
        this.setLt_autor((Textbox) getFellow("lt_autor"));
        this.setLt_genero((Textbox) getFellow("lt_genero"));
        this.setNu_quantidade_estoque((Intbox) getFellow("nu_quantidade_estoque"));
        this.setCd_usuario((Intbox) getFellow("cd_usuario"));
        this.setNm_usuario((Textbox) getFellow("nm_usuario"));
        this.setLt_status((Textbox) getFellow("lt_status"));
        this.setVl_pesquisa((Textbox) getFellow("vl_pesquisa"));
        this.setResultados((Listbox) getFellow("resultados"));
        this.setPpPesquisar((Popup) getFellow("pesquisar"));
        this.setResultados_usuario((Listbox) getFellow("resultados_usuario"));
        this.setResultados_material((Listbox) getFellow("resultados_material"));
        this.setPpPesquisarMaterial((Popup) getFellow("ppPesquisarMaterial"));
        this.setPpPesquisarUsuario((Popup) getFellow("ppPesquisarUsuario"));
        this.setPnm_usuario((Textbox) getFellow("pnm_usuario"));
        this.setPnm_material((Textbox) getFellow("pnm_material"));
    
        novo();
    }
    
    public void novo(){
        this.getCd_usuario().setValue(null);
        this.getNm_usuario().setValue("");
        this.getLt_status().setValue("");
        
        this.getCd_material_bibliografico().setValue(null);
        this.getLt_nome().setValue("");
        this.getLt_autor().setValue("");
        this.getLt_genero().setValue("");
        this.getNu_quantidade_estoque().setValue(null);
        
        this.getCd_solicitacao().setValue("0");
        this.getDt_emprestimo().setValue(null);
        this.getDt_devolucao().setValue(null);
        this.getLt_situacao().setValue("");
        this.getNu_renovacao().setValue(null);
    }

    public boolean validarCampos(){
    
        return true;

    }
    
    public void terminar() {
        if (validarCampos()) {
            getEnt().setCd_solicitacao(Integer.parseInt(this.getCd_solicitacao().getValue()));
            getEnt().setDt_emprestimo(this.getDt_emprestimo().getValue());
            getEnt().setDt_devolucao(this.getDt_devolucao().getValue());
            getEnt().setNu_renovacao(this.getNu_renovacao().getValue());
            getEnt().setLt_situacao(this.getLt_situacao().getValue());
            getEnt().setCd_usuario(this.getCd_usuario().getValue());
            getEnt().setCd_material_bibliografico(this.getCd_material_bibliografico().getValue());
            
            if (this.getCd_solicitacao().getValue().equals("0")) {
                getDAO().incluir(getEnt());
                this.getCd_solicitacao().setValue(String.valueOf(getEnt().getCd_solicitacao()));
                if (getEnt().getCd_solicitacao()> 0) {
                    Messagebox.show("Solicitação de Empréstimo realizado com sucesso", "Gravar Registro", Messagebox.OK , Messagebox.INFORMATION);
                } else {
                    Messagebox.show("Sistema encontrou um erro ao tentar efetuar a solicitação de emprestimo!", "Gravar Registro", Messagebox.OK , Messagebox.ERROR);
                }
            }else{
                getDAO().atualizar(getEnt());
                this.getCd_solicitacao().setValue(String.valueOf(getEnt().getCd_solicitacao()));
                if (getEnt().getCd_solicitacao()> 0) {
                    Messagebox.show("Solicitação de Empréstimo atualizado com sucesso", "Gravar Registro", Messagebox.OK , Messagebox.INFORMATION);
                } else {
                    Messagebox.show("Sistema encontrou um erro ao tentar efetuar a solicitação de emprestimo!", "Gravar Registro", Messagebox.OK , Messagebox.ERROR);
                }
            }
        }
    }
    
     //executa pesquisa de um registro de concessao de recurso
    public void executarPesquisa() {

        if (getVl_pesquisa().getValue() != null) {
            getResultados().getItems().clear();
            this.setResultadosPesquisa(getDAO().pesquisar(getVl_pesquisa().getValue()));

            if (this.getResultadosPesquisa().isEmpty()) {
                try {
                    Messagebox.show("Não foram encontrados registros!", "Pesquisar Registro", Messagebox.OK , Messagebox.INFORMATION);
                    
                    this.getVl_pesquisa().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Solicitar_emprestimo.class
                            .getName()).log(Level.SEVERE, null, ex);
                 }
            } else{
                for (Iterator i = this.getResultadosPesquisa().iterator(); i.hasNext();) {
                    Object[] obj = (Object[]) i.next();

                    Listitem item = new Listitem();
                    Listcell cellCd_solicitacao = new Listcell();
                    Listcell cellCd_usuario = new Listcell();
                    Listcell cellLt_nome = new Listcell();

                    cellCd_solicitacao.setLabel(obj[0].toString());
                    cellCd_usuario.setLabel(obj[1].toString());
                    cellLt_nome.setLabel(obj[2].toString());

                    item.appendChild(cellCd_solicitacao);
                    item.appendChild(cellCd_usuario);
                    item.appendChild(cellLt_nome);

                    item.setValue(obj[0].toString());

                    item.setParent(getResultados());

                }
            }
        } else {
            try {
                Messagebox.show("Valor da pesquisa inválido", "Pesquisar Registro", Messagebox.OK , Messagebox.INFORMATION);
            } catch (Exception ex) {
                Logger.getLogger(Solicitar_emprestimo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
        
    //fecha o popup de pesquisa de um registro
    public void fecharPesquisa(int tipo) {
        try {
            this.getPpPesquisar().close();
            //limpar();
            
            this.getResultadoSelecionado().setCd_solicitacao(Integer.parseInt(this.getResultados().getSelectedItem().getValue().toString()));
            SolicitarEmprestimo emprestimoSelecionado = this.getResultadoSelecionado();
            SolicitarEmprestimo emprestimoSelecionadoBanco = (SolicitarEmprestimo) getDAO().pesquisarCID(emprestimoSelecionado);

            this.setResultadoSelecionado(emprestimoSelecionadoBanco);
            if (tipo == 2){
                popularCampos();
            }
            this.getVl_pesquisa().setValue(null);
            this.getResultados().getItems().clear();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //popular campos da tela principal, depois de sair do popup busca de concessao de recurso
    public void popularCampos(){
                
        if (this.getResultadoSelecionado().getCd_solicitacao()> 0) {
                  
            this.getCd_solicitacao().setValue(String.valueOf(this.getResultadoSelecionado().getCd_solicitacao()));
            this.getDt_emprestimo().setValue(this.getResultadoSelecionado().getDt_emprestimo());
            this.getDt_devolucao().setValue(this.getResultadoSelecionado().getDt_devolucao());
            this.getNu_renovacao().setValue(this.getResultadoSelecionado().getNu_renovacao());
            this.getLt_situacao().setValue(this.getResultadoSelecionado().getLt_situacao());
            
            this.getCd_usuario().setValue(this.getResultadoSelecionado().getCd_usuario());
            //pesquisa automatica

            this.getCd_material_bibliografico().setValue(this.getResultadoSelecionado().getCd_material_bibliografico());
            //pesquisa automatica
            
            pesquisarUsuario();
            pesquisarMaterial();
        }
    }
    
        //Método que busca um usuario pelo seu codigo
    public void pesquisarUsuario() {
        
        if (getCd_usuario().getValue() == null || getCd_usuario().getValue() == 0) {
            try {
                Messagebox.show("Entre com o código do usuário", "Validar", Messagebox.OK , Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Solicitar_emprestimo.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            getCd_usuario().focus();
            return;
        }
        try {
            this.setResultadosPesquisaUsuario(getDAO().buscarUsuarioCodigo(this.getCd_usuario().getValue().toString()));

            Object[] obj = (Object[]) this.getResultadosPesquisaUsuario().get(0);

            this.getNm_usuario().setValue(obj[0].toString());
            this.getLt_status().setValue(obj[1].toString());

            if (getNm_usuario().getValue() == null || getNm_usuario().getValue().equals("")) {
                try {
                    Messagebox.show("Código Inválido!", "Validar", Messagebox.OK , Messagebox.INFORMATION);
                    getNm_usuario().setValue(null);
                    getLt_status().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Solicitar_emprestimo.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                getCd_usuario().focus();
                return;
            }

        } catch (Exception ex) {
            try {
                Messagebox.show("Código Inválido!", "Validar", Messagebox.OK , Messagebox.INFORMATION);
                getNm_usuario().setValue(null);
                getLt_status().setValue(null);
            } catch (Exception ex1) {
                Logger.getLogger(Solicitar_emprestimo.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void popularCamposUsuario(){
                    
        Object[] resultado = (Object[]) this.getResultadosPesquisaUsuario().get(getResultados_usuario().getSelectedIndex());
            
        this.getCd_usuario().setValue(Integer.parseInt(resultado[0].toString()));
        this.getNm_usuario().setValue(resultado[1].toString());
        this.getLt_status().setValue(resultado[2].toString());
    }
    
    public void popularCamposMaterial(){
        
        Object[] resultado = (Object[]) this.getResultadosPesquisaMaterial().get(getResultados_material().getSelectedIndex());
            
        this.getCd_material_bibliografico().setValue(Integer.parseInt(resultado[0].toString()));
        this.getLt_nome().setValue(resultado[1].toString());
        this.getLt_autor().setValue(resultado[2].toString()); 
        this.getLt_genero().setValue(resultado[3].toString());
        this.getNu_quantidade_estoque().setValue(Integer.parseInt(resultado[4].toString()));
        
    }
    
    public void fecharPesquisaUsuario(int tipo){
        try {
            this.getPpPesquisarUsuario().close();
            
            if (tipo == 2){
                popularCamposUsuario();
            }
            this.getPnm_usuario().setValue(null);
            this.getResultados_usuario().getItems().clear();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fecharPesquisaMaterial(int tipo){
        try {
            this.getPpPesquisarMaterial().close();
            
            if (tipo == 2){
                popularCamposMaterial();
            }
            this.getPnm_material().setValue(null);
            this.getResultados_material().getItems().clear();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void pesquisarMaterial() {
        
        if (getCd_material_bibliografico().getValue() == null || getCd_material_bibliografico().getValue() == 0) {
            try {
                Messagebox.show("Entre com o código do material", "Validar", Messagebox.OK , Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Solicitar_emprestimo.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            getCd_material_bibliografico().focus();
            return;
        }
        try {
            this.setResultadosPesquisaMaterial(getDAO().buscarMaterialCodigo(this.getCd_material_bibliografico().getValue()));

            Object[] obj = (Object[]) this.getResultadosPesquisaMaterial().get(0);

            this.getLt_nome().setValue(obj[0].toString());
            this.getLt_autor().setValue(obj[1].toString());
            this.getLt_genero().setValue(obj[2].toString());
            this.getNu_quantidade_estoque().setValue(Integer.parseInt(obj[3].toString()));

            if (getLt_nome().getValue() == null || getLt_nome().getValue().equals("")) {
                try {
                    Messagebox.show("Código Inválido!", "Validar", Messagebox.OK , Messagebox.INFORMATION);
                    getLt_nome().setValue(null);
                    getLt_autor().setValue(null);
                    getLt_genero().setValue(null);
                    getNu_quantidade_estoque().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Solicitar_emprestimo.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                getCd_material_bibliografico().focus();
                return;
            }

        } catch (Exception ex) {
            try {
                Messagebox.show("Código Inválido!", "Validar", Messagebox.OK , Messagebox.INFORMATION);
                getLt_nome().setValue(null);
                getLt_autor().setValue(null);
                getLt_genero().setValue(null);
                getNu_quantidade_estoque().setValue(null);
            } catch (Exception ex1) {
                Logger.getLogger(Solicitar_emprestimo.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }

        }
}



    public void executarPesquisaMaterial(){

    if (getPnm_material().getValue() != null) {
            getResultados_material().getItems().clear();
            this.setResultadosPesquisaMaterial(getDAO().pesquisarMaterial(getPnm_material().getValue()));

            if (this.getResultadosPesquisaMaterial().isEmpty()) {
                try {
                    Messagebox.show("Não foram encontrados registros!", "Pesquisar Registro", Messagebox.OK , Messagebox.INFORMATION);
                    this.getPnm_material().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Solicitar_emprestimo.class
                            .getName()).log(Level.SEVERE, null, ex);
                 }
            } else{
                for (Iterator i = this.getResultadosPesquisaMaterial().iterator(); i.hasNext();) {
                    Object[] obj = (Object[]) i.next();

                    Listitem item = new Listitem();
                    Listcell cellCd_material_bibliografico = new Listcell();
                    Listcell cellLt_nome = new Listcell();
                    Listcell cellLt_autor = new Listcell();

                    cellCd_material_bibliografico.setLabel(obj[0].toString());
                    cellLt_nome.setLabel(obj[1].toString());
                    cellLt_autor.setLabel(obj[1].toString());

                    item.appendChild(cellCd_material_bibliografico);
                    item.appendChild(cellLt_nome);
                    item.appendChild(cellLt_autor);

                    item.setValue(obj[0].toString());

                    item.setParent(getResultados_material());

                }
            }
            

        } else {
            try {
                Messagebox.show("Valor da pesquisa inválido", "Pesquisar Registro", Messagebox.OK , Messagebox.INFORMATION);
            } catch (Exception ex) {
                Logger.getLogger(Solicitar_emprestimo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   
}


    public void executarPesquisaUsuario(){
        if (getPnm_usuario().getValue() != null) {
            
            getResultados_usuario().getItems().clear();
            this.setResultadosPesquisaUsuario(getDAO().pesquisarUsuario(getPnm_usuario().getValue()));

            if (this.getResultadosPesquisaUsuario().isEmpty()) {
                try {
                    Messagebox.show("Não foram encontrados registros!", "Pesquisar Registro", Messagebox.OK , Messagebox.INFORMATION);
                    this.getPnm_usuario().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Solicitar_emprestimo.class
                            .getName()).log(Level.SEVERE, null, ex);
                 }
            } else{
                for (Iterator i = this.getResultadosPesquisaUsuario().iterator(); i.hasNext();) {
                    Object[] obj = (Object[]) i.next();

                    Listitem item = new Listitem();
                    Listcell cellCd_usuario = new Listcell();
                    Listcell cellNm_usuario = new Listcell();
                    Listcell cellLt_status = new Listcell();

                    cellCd_usuario.setLabel(obj[0].toString());
                    cellNm_usuario.setLabel(obj[1].toString());
                    cellLt_status.setLabel(obj[1].toString());

                    item.appendChild(cellCd_usuario);
                    item.appendChild(cellNm_usuario);
                    item.appendChild(cellLt_status);

                    item.setValue(obj[0].toString());

                    item.setParent(getResultados_usuario());

                }
            }
            

        } else {
            try {
                Messagebox.show("Valor da pesquisa inválido", "Pesquisar Registro", Messagebox.OK , Messagebox.INFORMATION);
            } catch (Exception ex) {
                Logger.getLogger(Solicitar_emprestimo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    public Label getCd_solicitacao() {
        return cd_solicitacao;
    }

    public void setCd_solicitacao(Label cd_solicitacao) {
        this.cd_solicitacao = cd_solicitacao;
    }
    
    /**
     * @return the cd_emprestimo
     */
    public Label getCd_emprestimo() {
        return cd_emprestimo;
    }

    /**
     * @param cd_emprestimo the cd_emprestimo to set
     */
    public void setCd_emprestimo(Label cd_emprestimo) {
        this.cd_emprestimo = cd_emprestimo;
    }

    /**
     * @return the dt_emprestimo
     */
    public Datebox getDt_emprestimo() {
        return dt_emprestimo;
    }

    /**
     * @param dt_emprestimo the dt_emprestimo to set
     */
    public void setDt_emprestimo(Datebox dt_emprestimo) {
        this.dt_emprestimo = dt_emprestimo;
    }

    /**
     * @return the dt_devolucao
     */
    public Datebox getDt_devolucao() {
        return dt_devolucao;
    }

    /**
     * @param dt_devolucao the dt_devolucao to set
     */
    public void setDt_devolucao(Datebox dt_devolucao) {
        this.dt_devolucao = dt_devolucao;
    }

    /**
     * @return the nu_renovacao
     */
    public Intbox getNu_renovacao() {
        return nu_renovacao;
    }

    /**
     * @param nu_renovacao the nu_renovacao to set
     */
    public void setNu_renovacao(Intbox nu_renovacao) {
        this.nu_renovacao = nu_renovacao;
    }

    /**
     * @return the lt_situacao
     */
    public Textbox getLt_situacao() {
        return lt_situacao;
    }

    /**
     * @param lt_situacao the lt_situacao to set
     */
    public void setLt_situacao(Textbox lt_situacao) {
        this.lt_situacao = lt_situacao;
    }

    /**
     * @return the cd_material_bibliografico
     */
    public Intbox getCd_material_bibliografico() {
        return cd_material_bibliografico;
    }

    /**
     * @param cd_material_bibliografico the cd_material_bibliografico to set
     */
    public void setCd_material_bibliografico(Intbox cd_material_bibliografico) {
        this.cd_material_bibliografico = cd_material_bibliografico;
    }

    /**
     * @return the lt_nome
     */
    public Textbox getLt_nome() {
        return lt_nome;
    }

    /**
     * @param lt_nome the lt_nome to set
     */
    public void setLt_nome(Textbox lt_nome) {
        this.lt_nome = lt_nome;
    }

    /**
     * @return the lt_autor
     */
    public Textbox getLt_autor() {
        return lt_autor;
    }

    /**
     * @param lt_autor the lt_autor to set
     */
    public void setLt_autor(Textbox lt_autor) {
        this.lt_autor = lt_autor;
    }

    /**
     * @return the lt_genero
     */
    public Textbox getLt_genero() {
        return lt_genero;
    }

    /**
     * @param lt_genero the lt_genero to set
     */
    public void setLt_genero(Textbox lt_genero) {
        this.lt_genero = lt_genero;
    }

    /**
     * @return the nu_quantidade_estoque
     */
    public Intbox getNu_quantidade_estoque() {
        return nu_quantidade_estoque;
    }

    /**
     * @param nu_quantidade_estoque the nu_quantidade_estoque to set
     */
    public void setNu_quantidade_estoque(Intbox nu_quantidade_estoque) {
        this.nu_quantidade_estoque = nu_quantidade_estoque;
    }

    /**
     * @return the cd_usuario
     */
    public Intbox getCd_usuario() {
        return cd_usuario;
    }

    /**
     * @param cd_usuario the cd_usuario to set
     */
    public void setCd_usuario(Intbox cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    /**
     * @return the nm_usuario
     */
    public Textbox getNm_usuario() {
        return nm_usuario;
    }

    /**
     * @param nm_usuario the nm_usuario to set
     */
    public void setNm_usuario(Textbox nm_usuario) {
        this.nm_usuario = nm_usuario;
    }

    /**
     * @return the lt_status
     */
    public Textbox getLt_status() {
        return lt_status;
    }

    /**
     * @param lt_status the lt_status to set
     */
    public void setLt_status(Textbox lt_status) {
        this.lt_status = lt_status;
    }

    /**
     * @return the vl_pesquisa
     */
    public Textbox getVl_pesquisa() {
        return vl_pesquisa;
    }

    /**
     * @param vl_pesquisa the vl_pesquisa to set
     */
    public void setVl_pesquisa(Textbox vl_pesquisa) {
        this.vl_pesquisa = vl_pesquisa;
    }

    /**
     * @return the resultados
     */
    public Listbox getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(Listbox resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the ppPesquisar
     */
    public Popup getPpPesquisar() {
        return ppPesquisar;
    }

    /**
     * @param ppPesquisar the ppPesquisar to set
     */
    public void setPpPesquisar(Popup ppPesquisar) {
        this.ppPesquisar = ppPesquisar;
    }

    /**
     * @return the resultados_usuario
     */
    public Listbox getResultados_usuario() {
        return resultados_usuario;
    }

    /**
     * @param resultados_usuario the resultados_usuario to set
     */
    public void setResultados_usuario(Listbox resultados_usuario) {
        this.resultados_usuario = resultados_usuario;
    }

    /**
     * @return the resultados_material
     */
    public Listbox getResultados_material() {
        return resultados_material;
    }

    /**
     * @param resultados_material the resultados_material to set
     */
    public void setResultados_material(Listbox resultados_material) {
        this.resultados_material = resultados_material;
    }

    /**
     * @return the ppPesquisarUsuario
     */
    public Popup getPpPesquisarUsuario() {
        return ppPesquisarUsuario;
    }

    /**
     * @param ppPesquisarUsuario the ppPesquisarUsuario to set
     */
    public void setPpPesquisarUsuario(Popup ppPesquisarUsuario) {
        this.ppPesquisarUsuario = ppPesquisarUsuario;
    }

    /**
     * @return the ppPesquisarMaterial
     */
    public Popup getPpPesquisarMaterial() {
        return ppPesquisarMaterial;
    }

    /**
     * @param ppPesquisarMaterial the ppPesquisarMaterial to set
     */
    public void setPpPesquisarMaterial(Popup ppPesquisarMaterial) {
        this.ppPesquisarMaterial = ppPesquisarMaterial;
    }

    /**
     * @return the resultadosPesquisa
     */
    public List getResultadosPesquisa() {
        return resultadosPesquisa;
    }

    /**
     * @param resultadosPesquisa the resultadosPesquisa to set
     */
    public void setResultadosPesquisa(List resultadosPesquisa) {
        this.resultadosPesquisa = resultadosPesquisa;
    }

    /**
     * @return the resultadosPesquisaUsuario
     */
    public List getResultadosPesquisaUsuario() {
        return resultadosPesquisaUsuario;
    }

    /**
     * @param resultadosPesquisaUsuario the resultadosPesquisaUsuario to set
     */
    public void setResultadosPesquisaUsuario(List resultadosPesquisaUsuario) {
        this.resultadosPesquisaUsuario = resultadosPesquisaUsuario;
    }

    /**
     * @return the resultadosPesquisaMaterial
     */
    public List getResultadosPesquisaMaterial() {
        return resultadosPesquisaMaterial;
    }

    /**
     * @param resultadosPesquisaMaterial the resultadosPesquisaMaterial to set
     */
    public void setResultadosPesquisaMaterial(List resultadosPesquisaMaterial) {
        this.resultadosPesquisaMaterial = resultadosPesquisaMaterial;
    }

    /**
     * @return the resultadoSelecionado
     */
    public SolicitarEmprestimo getResultadoSelecionado() {
        return resultadoSelecionado;
    }

    /**
     * @param resultadoSelecionado the resultadoSelecionado to set
     */
    public void setResultadoSelecionado(SolicitarEmprestimo resultadoSelecionado) {
        this.resultadoSelecionado = resultadoSelecionado;
    }

    /**
     * @return the DAO
     */
    public SolicitarEmprestimoDAO getDAO() {
        return DAO;
    }

    /**
     * @param DAO the DAO to set
     */
    public void setDAO(SolicitarEmprestimoDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * @return the ent
     */
    public SolicitarEmprestimo getEnt() {
        return ent;
    }

    /**
     * @param ent the ent to set
     */
    public void setEnt(SolicitarEmprestimo ent) {
        this.ent = ent;
    }

    
    
    /**
     * @return the pnm_material
     */
    public Textbox getPnm_material() {
        return pnm_material;
    }

    /**
     * @param pnm_material the pnm_material to set
     */
    public void setPnm_material(Textbox pnm_material) {
        this.pnm_material = pnm_material;
    }

    /**
     * @return the pnm_usuario
     */
    public Textbox getPnm_usuario() {
        return pnm_usuario;
    }

    /**
     * @param pnm_usuario the pnm_usuario to set
     */
    public void setPnm_usuario(Textbox pnm_usuario) {
        this.pnm_usuario = pnm_usuario;
    }

}
