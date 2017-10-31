/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.controle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sibi.DAO.DevolucaoDAO;
import sibi.persistencia.Emprestimo;

/**
 *
 * @author admin
 */
public class Efetuar_devolucao extends Window{

    private Window win;
    private Label cd_emprestimo;
    private Textbox lt_situacao;
    private Intbox cd_usuario;
    private Textbox nm_usuario;
    private Textbox lt_status;
    private Intbox cd_material_bibliografico;
    private Textbox lt_nome;
    private Datebox dt_emprestimo;
    private Datebox dt_devolucao;
    private Decimalbox db_multa;
        
    private Popup ppPesquisar;
    private Listbox vl_campo;
    private Textbox vl_pesquisa;
    private Listbox resultados;   
    
    private Button btDevolucao;
        
    private List resultadosPesquisa = new ArrayList();
    private Emprestimo resultadoSelecionado = new Emprestimo();

    private Emprestimo ent = new Emprestimo();

    
    private DevolucaoDAO DAO = new DevolucaoDAO();
    
    public void onCreate(){
        this.setWin((Window) getFellow("win"));
        this.setCd_emprestimo((Label) getFellow("cd_emprestimo"));
        this.setLt_situacao((Textbox) getFellow("lt_situacao"));
        this.setCd_usuario((Intbox) getFellow("cd_usuario"));
        this.setNm_usuario((Textbox) getFellow("nm_usuario"));
        this.setLt_status((Textbox) getFellow("lt_status"));
        this.setCd_material_bibliografico((Intbox) getFellow("cd_material_bibliografico"));
        this.setLt_nome((Textbox) getFellow("lt_nome"));
        this.setDt_emprestimo((Datebox) getFellow("dt_emprestimo"));
        this.setDt_devolucao((Datebox) getFellow("dt_devolucao"));
        this.setDb_multa((Decimalbox) getFellow("db_multa"));
        this.setResultados((Listbox) getFellow("resultados"));
        this.setVl_campo((Listbox) getFellow("vl_campo"));
        this.setVl_pesquisa((Textbox) getFellow("vl_pesquisa"));
        this.setPpPesquisar((Popup) getFellow("pesquisar"));
        this.setBtDevolucao((Button) getFellow("btDevolucao"));
        
    }
    
    public void novo(){
        this.getCd_emprestimo().setValue("-1");
        this.getLt_situacao().setValue("");
        this.getCd_usuario().setValue(null);
        this.getNm_usuario().setValue("");
        this.getLt_status().setValue("");
        this.getCd_material_bibliografico().setValue(null);
        this.getLt_nome().setValue("");
        this.getDt_emprestimo().setValue(null);
        this.getDt_devolucao().setValue(null);
        this.getDb_multa().setValue(BigDecimal.ZERO);
        this.getBtDevolucao().setVisible(true);
    }
    
    public void executarPesquisa() {

        if (getVl_campo().getSelectedCount() != 0 && getVl_pesquisa().getValue() != null) {
            getResultados().getItems().clear();
            this.setResultadosPesquisa(getDAO().pesquisar(getVl_campo().getSelectedItem().getValue().toString(), getVl_pesquisa().getValue()));

            if (this.getResultadosPesquisa().isEmpty()) {
                try {
                    Messagebox.show("Não foram encontrados registros!", "Pesquisar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                    this.getVl_campo().setSelectedIndex(0);
                    this.getVl_pesquisa().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Efetuar_devolucao.class
                            .getName()).log(Level.SEVERE, null, ex);
                 }
            } else{
                for (Iterator i = this.getResultadosPesquisa().iterator(); i.hasNext();) {
                    Object[] obj = (Object[]) i.next();

                    Listitem item = new Listitem();
                    Listcell cellCd_emprestimo = new Listcell();
                    Listcell cellNm_usuario = new Listcell();
                    Listcell cellLt_nome = new Listcell();

                    cellCd_emprestimo.setLabel(obj[0].toString());
                    cellNm_usuario.setLabel(obj[1].toString());
                    cellLt_nome.setLabel(obj[2].toString());

                    item.appendChild(cellCd_emprestimo);
                    item.appendChild(cellNm_usuario);
                    item.appendChild(cellLt_nome);

                    item.setValue(obj[0].toString());

                    item.setParent(getResultados());

                }
            }

        } else {
            try {
                Messagebox.show("Campo e/ou valor da pesquisa inválido", "Pesquisar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            } catch (Exception ex) {
                Logger.getLogger(Efetuar_devolucao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void fecharPesquisa(int tipo) {
        try {
            this.getPpPesquisar().close();
            //limpar();
            
            this.getResultadoSelecionado().setCd_emprestimo(Integer.parseInt(this.getResultados().getSelectedItem().getValue().toString()));
            Emprestimo emprestimoSelecionado = this.getResultadoSelecionado();
            Emprestimo emprestimoSelecionadoBanco = (Emprestimo) getDAO().pesquisarCID(emprestimoSelecionado);

            this.setResultadoSelecionado(emprestimoSelecionadoBanco);
            if (tipo == 2){
                popularCampos();
                verificaVencimento();
            }
            this.getVl_campo().setSelectedIndex(0);
            this.getVl_pesquisa().setValue(null);
            this.getResultados().getItems().clear();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    //popular campos da tela principal, depois de sair do popup busca de concessao de recurso
    public void popularCampos(){
        novo();
                
        if (this.getResultadoSelecionado().getCd_emprestimo()> 0) {
                  
            this.getCd_emprestimo().setValue(String.valueOf(this.getResultadoSelecionado().getCd_emprestimo()));
            this.getDt_emprestimo().setValue(this.getResultadoSelecionado().getDt_emprestimo());
            this.getDt_devolucao().setValue(this.getResultadoSelecionado().getDt_devolucao());
            this.getLt_situacao().setValue(this.getResultadoSelecionado().getLt_situacao());
            
            this.getCd_usuario().setValue(this.getResultadoSelecionado().getCd_usuario());

            this.getCd_material_bibliografico().setValue(this.getResultadoSelecionado().getCd_material_bibliografico());
            
//            pesquisarUsuario();
//            pesquisarMaterial();
        }
    }
    
    
    public void verificaVencimento(){
        Date hoje = new Date();
        Date dt_devol = this.getDt_devolucao().getValue();
        if(dt_devol.before(hoje) && !getLt_situacao().getValue().equals("Divida paga.")){
            this.getLt_situacao().setValue("Devolução Vencida, cobrar multa.");
            this.getDb_multa().setValue(BigDecimal.TEN);
            this.getBtDevolucao().setVisible(false);
            this.getLt_status().setValue("Inadimplente");
//            mudarStatusUsuario("Inadimplente");
        }
    }

    
    public void pagamento(){
        if(!this.getCd_emprestimo().getValue().equals("-1")){
            this.getLt_situacao().setValue("Divida paga.");
            this.getLt_status().setValue("Sem pendencias");
            this.getDb_multa().setValue(BigDecimal.ZERO);
            this.getBtDevolucao().setVisible(true);    
        }
        
    }
    
    public void terminar(){
        getEnt().setCd_emprestimo(Integer.parseInt(this.getCd_emprestimo().getValue()));
        getEnt().setCd_material_bibliografico(this.getCd_material_bibliografico().getValue());
        getEnt().setCd_usuario(this.getCd_usuario().getValue());
        getEnt().setDt_devolucao(this.getDt_devolucao().getValue());
        getEnt().setDt_emprestimo(this.getDt_emprestimo().getValue());
        getEnt().setLt_situacao("Devolução Efetuada");
        if(!this.getCd_emprestimo().getValue().equals("-1")){
            boolean r = getDAO().alterar(getEnt());
                if (r) {
                    this.getLt_situacao().setValue(getEnt().getLt_situacao());
                    Messagebox.show("Devolução realizada com sucesso", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("Sistema encontrou um erro ao efetuar devolução!", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.ERROR);
                }
        }
//        Session session = HibernateUtil.open();
//        Emprestimo emp = (Emprestimo) session.get(Emprestimo.class, this.getCd_emprestimo().getValue());
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
     * @return the db_multa
     */
    public Decimalbox getDb_multa() {
        return db_multa;
    }

    /**
     * @param db_multa the db_multa to set
     */
    public void setDb_multa(Decimalbox db_multa) {
        this.db_multa = db_multa;
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
     * @return the vl_campo
     */
    public Listbox getVl_campo() {
        return vl_campo;
    }

    /**
     * @param vl_campo the vl_campo to set
     */
    public void setVl_campo(Listbox vl_campo) {
        this.vl_campo = vl_campo;
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
     * @return the DAO
     */
    public DevolucaoDAO getDAO() {
        return DAO;
    }

    /**
     * @param DAO the DAO to set
     */
    public void setDAO(DevolucaoDAO DAO) {
        this.DAO = DAO;
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
     * @return the resultadoSelecionado
     */
    public Emprestimo getResultadoSelecionado() {
        return resultadoSelecionado;
    }

    /**
     * @param resultadoSelecionado the resultadoSelecionado to set
     */
    public void setResultadoSelecionado(Emprestimo resultadoSelecionado) {
        this.resultadoSelecionado = resultadoSelecionado;
    }
    
    
    
    /**
     * @return the btDevolucao
     */
    public Button getBtDevolucao() {
        return btDevolucao;
    }

    /**
     * @param btDevolucao the btDevolucao to set
     */
    public void setBtDevolucao(Button btDevolucao) {
        this.btDevolucao = btDevolucao;
    }
    /**
     * @return the ent
     */
    public Emprestimo getEnt() {
        return ent;
    }

    /**
     * @param ent the ent to set
     */
    public void setEnt(Emprestimo ent) {
        this.ent = ent;
    }


}
