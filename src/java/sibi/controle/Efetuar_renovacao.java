/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sibi.DAO.EmprestimoDAO;
import sibi.persistencia.Emprestimo;

/**
 *
 * @author admin
 */
public class Efetuar_renovacao extends Window{


    private Window win;
    private Intbox cd_material_bibliografico;
    private Textbox lt_nome;
    private Textbox lt_autor;
    private Textbox lt_genero;
    private Datebox dt_devolucao;
    private Textbox lt_situacao;
    private Intbox nu_renovacao;
    private Popup ppPesquisar;
    private Intbox cd_usuario;
    private Listbox resultados;
    
    private EmprestimoDAO DAO = new EmprestimoDAO();
    private List resultadosPesquisa = new ArrayList();
    
    private Emprestimo resultadoSelecionado = new Emprestimo();
    private Emprestimo ent = new Emprestimo();
    
    private List resultadosPesquisaMaterial = new ArrayList();

    

    public void onCreate(){
        this.setCd_material_bibliografico((Intbox) getFellow("cd_material_bibliografico"));
        this.setCd_usuario((Intbox) getFellow("cd_usuario"));
        this.setDt_devolucao((Datebox) getFellow("dt_devolucao"));
        this.setLt_autor((Textbox) getFellow("lt_autor"));
        this.setLt_genero((Textbox) getFellow("lt_genero"));
        this.setLt_nome((Textbox) getFellow("lt_nome"));
        this.setLt_situacao((Textbox) getFellow("lt_situacao"));
        this.setNu_renovacao((Intbox) getFellow("nu_renovacao"));
        this.setPpPesquisar((Popup) getFellow("pesquisar"));
        this.setResultados((Listbox) getFellow("resultados"));
        
    }
    
    public void novo(){
        this.getCd_material_bibliografico().setValue(null);
        this.getLt_nome().setValue("");
        this.getLt_autor().setValue("");
        this.getLt_genero().setValue("");
        
        this.getDt_devolucao().setValue(null);
        this.getLt_situacao().setValue("");
        this.getNu_renovacao().setValue(null);
    }

    
    public void executarPesquisa(){
        if(this.getCd_usuario().getValue() == null){
            Messagebox.show("Entre com o seu código de usuário", "Pesquisa Emprestimos",
                    Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
        }else{
            getResultados().getItems().clear();
            this.setResultadosPesquisa(getDAO().pesquisarEmprestimos(getCd_usuario().getValue()));
            if(getResultadosPesquisa().isEmpty()){
                try {
                    Messagebox.show("Não foram encontrados Emprestimos pendentes!", "Pesquisar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                    this.getCd_usuario().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Efetuar_renovacao.class
                            .getName()).log(Level.SEVERE, null, ex);
                 }
            } else{
                for (Iterator i = this.getResultadosPesquisa().iterator(); i.hasNext();) {
                    Object[] obj = (Object[]) i.next();

                    Listitem item = new Listitem();
                    Listcell cellCd_emprestimo = new Listcell();
                    Listcell cellDt_devolucao = new Listcell();
                    Listcell cellLt_nome = new Listcell();

                    cellCd_emprestimo.setLabel(obj[0].toString());
                    cellDt_devolucao.setLabel(obj[1].toString());
                    cellLt_nome.setLabel(obj[2].toString());

                    item.appendChild(cellCd_emprestimo);
                    item.appendChild(cellDt_devolucao);
                    item.appendChild(cellLt_nome);

                    item.setValue(obj[0].toString());

                    item.setParent(getResultados());
        
                }
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
            }
            this.getCd_usuario().setValue(null);
            this.getResultados().getItems().clear();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     
    public void popularCampos(){
                
        if (this.getResultadoSelecionado().getCd_emprestimo()> 0) {
            this.getCd_material_bibliografico().setValue(this.getResultadoSelecionado().getCd_material_bibliografico());
            this.getDt_devolucao().setValue(this.getResultadoSelecionado().getDt_devolucao());
            this.getLt_situacao().setValue(this.getResultadoSelecionado().getLt_situacao());
            this.getNu_renovacao().setValue(this.getResultadoSelecionado().getNu_renovacao());
            
            pesquisarMaterial();
        }
    }
    
 
    public void pesquisarMaterial() {
        
        this.setResultadosPesquisaMaterial(getDAO().buscarMaterialCodigo(this.getCd_material_bibliografico().getValue()));

        Object[] obj = (Object[]) this.getResultadosPesquisaMaterial().get(0);

        this.getLt_nome().setValue(obj[0].toString());
        this.getLt_autor().setValue(obj[1].toString());
        this.getLt_genero().setValue(obj[2].toString());

    }
    
    public void renovar(){
        Date hoje = new Date();
        Date dt_devol = this.getDt_devolucao().getValue();
        if(dt_devol.before(hoje)){
            Messagebox.show("Emprestimo encontra-se vencido, apresente o livro na biblioteca",
                    "Renovação Emprestimo", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
        } else if(getNu_renovacao().getValue() > 3){
            Messagebox.show("Numero de renovações máximo alcançado, apresente o livro na biblioteca",
                    "Renovação Emprestimo", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
        } else{
            getNu_renovacao().setValue(getNu_renovacao().getValue() + 1);
            
            getLt_situacao().setValue("Emprestimo renovado.");
            
            String date = getDt_devolucao().getText();
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
            try {
                calendar.setTime(sd.parse(date));
            } catch (ParseException e) {
            }
            calendar.add(Calendar.DAY_OF_MONTH , 10);
            getDt_devolucao().setValue(calendar.getTime());
            
            getEnt().setCd_emprestimo(getResultadoSelecionado().getCd_emprestimo());
            getEnt().setDt_devolucao(getDt_devolucao().getValue());
            getEnt().setLt_situacao(getLt_situacao().getValue());
            getEnt().setNu_renovacao(getNu_renovacao().getValue());
            boolean r = getDAO().renovacao(getEnt());
            
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
    public EmprestimoDAO getDAO() {
        return DAO;
    }

    /**
     * @param DAO the DAO to set
     */
    public void setDAO(EmprestimoDAO DAO) {
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

}
