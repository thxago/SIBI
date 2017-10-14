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
import org.zkoss.zul.Doublebox;
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
import sibi.DAO.AtendenteDAO;
import sibi.persistencia.Atendente;
import sibi.utilitarios.ZkUtils;

/**
 *
 * @author alsnogtix
 */
public class Manter_atendente extends Window{
    
    private Window win;
    private Label cd_atendente;
    private Textbox nm_nome;
    private Intbox nu_cpf;
    private Datebox dt_data_nascimento;
    private Intbox nu_rg;
    private Textbox lt_sexo;
    private Datebox dt_inicio_trabalho;
    private Doublebox db_salario;
    private Textbox lt_endereco;
    private Textbox lt_bairro;
    private Intbox nu_cep;
    private Textbox lt_complemento;
    private Intbox nu_numero;
    private Textbox lt_email;
    private Textbox lt_senha;
    
    
    private Popup ppesquisar;
    private Listbox vl_campo;
    private Textbox vl_pesquisa;
    private Listbox resultados;
        
    private String resultadoBuscarBanco;
    private List resultadosPesquisa = new ArrayList();

    private AtendenteDAO DAO = new AtendenteDAO();
        
    private ZkUtils zut = new ZkUtils();
        
    private Atendente resultadoSelecionado = new Atendente();
    private Atendente ent = new Atendente();    
    
    public void onCreate(){
//        System.gc();
        setWin((Window) getFellow("win"));
        
        this.setCd_atendente((Label) getFellow("cd_atendente"));
        this.setNm_nome((Textbox) getFellow("nm_nome"));
        this.setNu_rg((Intbox) getFellow("nu_rg"));
        this.setDt_data_nascimento((Datebox) getFellow("dt_nascimento"));
        this.setNu_cpf((Intbox) getFellow("nu_cpf"));
        this.setNu_cep((Intbox) getFellow("nu_cep"));
        this.setLt_endereco((Textbox) getFellow("lt_endereco"));
        this.setLt_bairro((Textbox) getFellow("lt_bairro"));
        this.setNu_numero((Intbox) getFellow("nu_numero"));
        this.setLt_complemento((Textbox) getFellow("lt_complemento"));
        this.setLt_sexo((Textbox) getFellow("lt_sexo"));
        this.setLt_email((Textbox) getFellow("lt_email"));
        this.setLt_senha((Textbox) getFellow("lt_senha"));
        this.setDb_salario((Doublebox) getFellow("db_salario"));
        this.setDt_inicio_trabalho((Datebox) getFellow("dt_inicio_trabalho"));

        this.setVl_campo((Listbox) getFellow("vl_campo"));
        this.setVl_pesquisa((Textbox) getFellow("vl_pesquisa"));
        this.setResultados((Listbox) getFellow("resultados"));
        this.setPpesquisar((Popup) getFellow("pesquisar"));
        
    }
    
    public void novo() {
//        this.getBtExcluir().setVisible(true);
//        this.getBtGravar().setVisible(true);
        limpar();
        getCd_atendente().setValue("-1");
    }
    public void limpar() {
        this.getCd_atendente().setValue("-1");
        this.getNm_nome().setValue(null);
        this.getNu_cpf().setValue(null);
        this.getNu_rg().setValue(null);
        this.getDt_data_nascimento().setValue(null);
        this.getNu_cep().setValue(null);
        this.getLt_endereco().setValue(null);
        this.getLt_bairro().setValue(null);
        this.getNu_numero().setValue(null);
        this.getLt_complemento().setValue(null);
        this.getLt_sexo().setValue(null);
        this.getLt_email().setValue(null);
        this.getLt_senha().setValue(null);
        this.getDb_salario().setValue(null);
        this.getDt_inicio_trabalho().setValue(null);
    }     

    public boolean validarCampos(){
        
                
        if (getNm_nome().getValue() == null || getNm_nome().getValue().equals("")) {
            try {
                Messagebox.show("Campo nome não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getNm_nome().focus();
            return false;
        }
        
         
        //fazer uma boa validação de cpf
        if (getNu_cpf().getValue() == null) {
            try {
                Messagebox.show("Campo CPF não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getNu_cpf().focus();
            return false;
        }
        if (getNu_rg().getValue() == null) {
            try {
                Messagebox.show("Campo RG não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getNu_rg().focus();
            return false;
        }
         
        if (getDt_data_nascimento().getValue() == null) {
            try {
                Messagebox.show("Campo data de nascimento não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getDt_data_nascimento().focus();
            return false;
        }
        
         
        if (getNu_cep().getValue() == null || getNu_cep().getValue().equals("")) {
            try {
                Messagebox.show("Campo CEP não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getNu_cep().focus();
            return false;
        }
        
        if (getLt_endereco().getValue().equals("")) {
            try {
                Messagebox.show("Campo endereço não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLt_endereco().focus();
            return false;
        }        

        if (getLt_bairro().getValue() == null || getLt_bairro().getValue().equals("")) {
            try {
                Messagebox.show("Campo bairro não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLt_bairro().focus();
            return false;
        }        
        if (getNu_numero().getValue() == null || getNu_numero().getValue().equals("")) {
            try {
                Messagebox.show("Campo numero não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getNu_numero().focus();
            return false;
        }
        
        if (getLt_complemento().getValue() == null || getLt_complemento().getValue().equals("")) {
            try {
                Messagebox.show("Campo complemento não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLt_complemento().focus();
            return false;
        }
        
        
        if (getLt_sexo().getValue() == null || getLt_sexo().getValue().equals("")) {
            try {
                Messagebox.show("Campo sexo não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLt_sexo().focus();
            return false;
        }
        
        
        if (getLt_email().getValue() == null || getLt_email().getValue().equals("")) {
            try {
                Messagebox.show("Campo email não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLt_email().focus();
            return false;
        }
        
        
        if (getLt_senha().getValue() == null || getLt_senha().getValue().equals("")) {
            try {
                Messagebox.show("Campo senha não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLt_senha().focus();
            return false;
        }
        
        if(getDb_salario().getValue() == null || getDb_salario().getValue().equals("")){
           try {
                Messagebox.show("Campo salario não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getDb_salario().focus();
            return false;            
        }

        if(getDt_inicio_trabalho().getValue() == null || getDt_inicio_trabalho().getValue().equals("")){
           try {
                Messagebox.show("Campo data de inicio de trabalho não pode ser vazio", "Validar", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);

            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            getDb_salario().focus();
            return false;            
        } 
        
        return true;
    }

    
    
    
    public void gravar() {
        if (validarCampos()) {
//            ConcessaoRecurso ent = new ConcessaoRecurso();
            getEnt().setCd_atendente(Integer.parseInt(this.getCd_atendente().getValue()));
            getEnt().setNm_nome(this.getNm_nome().getValue());
            getEnt().setNu_cpf(this.getNu_cpf().getValue());
            getEnt().setNu_rg(this.getNu_rg().getValue());
            getEnt().setDt_data_nascimento(this.getDt_data_nascimento().getValue());
            getEnt().setNu_cep(this.getNu_cep().getValue());
            getEnt().setLt_endereco(this.getLt_endereco().getValue());
            getEnt().setLt_bairro((this.getLt_bairro().getValue()));
            getEnt().setNu_numero(this.getNu_numero().getValue());
            getEnt().setDb_salario(this.getDb_salario().getValue());
            getEnt().setDt_inicio_trabalho(this.getDt_inicio_trabalho().getValue());
            getEnt().setLt_complemento(this.getLt_complemento().getValue());
            getEnt().setLt_sexo(this.getLt_sexo().getValue());
            getEnt().setLt_email(this.getLt_email().getValue());
            getEnt().setLt_senha((this.getLt_senha().getValue()));
            
            if (this.getCd_atendente().getValue().equals("-1")) {
                getDAO().incluir(getEnt());
                this.getCd_atendente().setValue(String.valueOf(getEnt().getCd_atendente()));
                if (getEnt().getCd_atendente()> 0) {
                    Messagebox.show("Registro incluído com sucesso", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("Sistema encontrou um erro ao tentar inserir registro!", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.ERROR);
                }
            } else {
                getEnt().setCd_atendente(Integer.parseInt(this.getCd_atendente().getValue()));
                boolean r = getDAO().alterar(getEnt());
                if (r) {
                    Messagebox.show("Registro alterado com sucesso", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("Sistema encontrou um erro ao tentar alterar registro!", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.ERROR);
                }
            }
            

        }
    }

    
    public void excluir(){


        if(getCd_atendente().getValue() == null || getCd_atendente().getValue().equals("-1")){
            try{
                Messagebox.show("Selecione um Atendente para excluir", "Excluir Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            }catch (Exception ex){
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
             
            try {              
                if (Messagebox.show("Deseja excluir o registro atual?", "Excluir Registro", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                    getEnt().setCd_atendente(new Integer(this.getCd_atendente().getValue()));
                    boolean r = getDAO().excluir(Integer.parseInt(this.getCd_atendente().getValue()));
                    if (r) {
                        Messagebox.show("Atendente excluido com sucesso", "Excluir Registro", Messagebox.OK, Messagebox.INFORMATION);
                        novo();
                    } else {
                        Messagebox.show("Sistema encontrou um erro ao tentar excluir atendente!", "Gravar Registro", Messagebox.OK | Messagebox.CANCEL, Messagebox.ERROR);
                    }
                }
            } catch (Exception ex) {
                Messagebox.show("Sistema encontrou um problema ao tentar excluir o atendente!", "Excluir Registro", Messagebox.OK, Messagebox.ERROR);

            }
        }
    }
    

    
    
        //popular campos da tela principal, depois de sair do popup busca de concessao de recurso
    public void popularCampos(){
                
        if (this.getResultadoSelecionado().getCd_atendente()> 0) {
                  
            this.getCd_atendente().setValue(String.valueOf(this.getResultadoSelecionado().getCd_atendente()));
            this.getNm_nome().setValue(this.getResultadoSelecionado().getNm_nome());
            this.getNu_cpf().setValue(this.getResultadoSelecionado().getNu_cpf());
            this.getNu_rg().setValue(this.getResultadoSelecionado().getNu_rg());
            this.getDt_data_nascimento().setValue(this.getResultadoSelecionado().getDt_data_nascimento());
            this.getNu_cep().setValue(this.getResultadoSelecionado().getNu_cep());
            this.getLt_endereco().setValue(this.getResultadoSelecionado().getLt_endereco());
            this.getLt_bairro().setValue(this.getResultadoSelecionado().getLt_bairro());
            this.getDt_inicio_trabalho().setValue(this.getResultadoSelecionado().getDt_inicio_trabalho());
            this.getDb_salario().setValue(this.getResultadoSelecionado().getDb_salario());
            this.getNu_numero().setValue(this.getResultadoSelecionado().getNu_numero());
            this.getLt_complemento().setValue(this.getResultadoSelecionado().getLt_complemento());
            this.getLt_sexo().setValue(this.getResultadoSelecionado().getLt_sexo());
            this.getLt_email().setValue(this.getResultadoSelecionado().getLt_email());
            this.getLt_senha().setValue(this.getResultadoSelecionado().getLt_senha());
            
        }
    }
    
    
    
     public void executarPesquisa() {

        if (getVl_campo().getSelectedCount() != 0 && getVl_pesquisa().getValue() != null) {
            getResultados().getItems().clear();
            this.setResultadosPesquisa(getDAO().pesquisar(getVl_campo().getSelectedItem().getValue().toString(), getVl_pesquisa().getValue()));

            if (this.getResultadosPesquisa().isEmpty()) {
                try {
                    Messagebox.show("Não foram encontrados registros!", "Pesquisar Atendente", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                    this.getVl_campo().setSelectedIndex(0);
                    this.getVl_pesquisa().setValue(null);

                } catch (Exception ex) {
                    Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
                 }
            } else{
                for (Iterator i = this.getResultadosPesquisa().iterator(); i.hasNext();) {
                    Object[] obj = (Object[]) i.next();

                    Listitem item = new Listitem();
                    Listcell cellCd_atendente = new Listcell();
                    Listcell cellNm_atendente = new Listcell();

                    cellCd_atendente.setLabel(obj[0].toString());
                    cellNm_atendente.setLabel(obj[1].toString());

                    item.appendChild(cellCd_atendente);
                    item.appendChild(cellNm_atendente);

                    item.setValue(obj[0].toString());

                    item.setParent(getResultados());
                }
            }
        } else {
            try {
                Messagebox.show("Campo e/ou valor da pesquisa inválido", "Pesquisar Atendente", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
            } catch (Exception ex) {
                Logger.getLogger(Manter_atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
        
    //fecha o popup de pesquisa de um registro de concessao de recurso
    public void fecharPesquisa(int tipo) {
        try {
            this.getPpesquisar().close();
            //limpar();
            
            this.getResultadoSelecionado().setCd_atendente(Integer.parseInt(this.getResultados().getSelectedItem().getValue().toString()));
            Atendente atendenteSelecionado = this.getResultadoSelecionado();
            Atendente atendenteSelecionadoBanco = (Atendente) getDAO().pesquisarCID(atendenteSelecionado);

            this.setResultadoSelecionado(atendenteSelecionadoBanco);
            if (tipo == 2){
                popularCampos();
            }
            this.getVl_campo().setSelectedIndex(0);
            this.getVl_pesquisa().setValue(null);
            this.getResultados().getItems().clear();
            

        } catch (Exception e) {
            System.out.println(e);
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
     * @return the cd_atendente
     */
    public Label getCd_atendente() {
        return cd_atendente;
    }

    /**
     * @param cd_atendente the cd_atendente to set
     */
    public void setCd_atendente(Label cd_atendente) {
        this.cd_atendente = cd_atendente;
    }

    /**
     * @return the nm_nome
     */
    public Textbox getNm_nome() {
        return nm_nome;
    }

    /**
     * @param nm_nome the nm_nome to set
     */
    public void setNm_nome(Textbox nm_nome) {
        this.nm_nome = nm_nome;
    }

    /**
     * @return the nu_cpf
     */
    public Intbox getNu_cpf() {
        return nu_cpf;
    }

    /**
     * @param nu_cpf the nu_cpf to set
     */
    public void setNu_cpf(Intbox nu_cpf) {
        this.nu_cpf = nu_cpf;
    }

    /**
     * @return the dt_data_nascimento
     */
    public Datebox getDt_data_nascimento() {
        return dt_data_nascimento;
    }

    /**
     * @param dt_data_nascimento the dt_data_nascimento to set
     */
    public void setDt_data_nascimento(Datebox dt_data_nascimento) {
        this.dt_data_nascimento = dt_data_nascimento;
    }

    /**
     * @return the nu_rg
     */
    public Intbox getNu_rg() {
        return nu_rg;
    }

    /**
     * @param nu_rg the nu_rg to set
     */
    public void setNu_rg(Intbox nu_rg) {
        this.nu_rg = nu_rg;
    }

    /**
     * @return the lt_sexo
     */
    public Textbox getLt_sexo() {
        return lt_sexo;
    }

    /**
     * @param lt_sexo the lt_sexo to set
     */
    public void setLt_sexo(Textbox lt_sexo) {
        this.lt_sexo = lt_sexo;
    }

    /**
     * @return the dt_inicio_trabalho
     */
    public Datebox getDt_inicio_trabalho() {
        return dt_inicio_trabalho;
    }

    /**
     * @param dt_inicio_trabalho the dt_inicio_trabalho to set
     */
    public void setDt_inicio_trabalho(Datebox dt_inicio_trabalho) {
        this.dt_inicio_trabalho = dt_inicio_trabalho;
    }

    /**
     * @return the db_salario
     */
    public Doublebox getDb_salario() {
        return db_salario;
    }

    /**
     * @param db_salario the db_salario to set
     */
    public void setDb_salario(Doublebox db_salario) {
        this.db_salario = db_salario;
    }

    /**
     * @return the lt_endereco
     */
    public Textbox getLt_endereco() {
        return lt_endereco;
    }

    /**
     * @param lt_endereco the lt_endereco to set
     */
    public void setLt_endereco(Textbox lt_endereco) {
        this.lt_endereco = lt_endereco;
    }

    /**
     * @return the lt_bairro
     */
    public Textbox getLt_bairro() {
        return lt_bairro;
    }

    /**
     * @param lt_bairro the lt_bairro to set
     */
    public void setLt_bairro(Textbox lt_bairro) {
        this.lt_bairro = lt_bairro;
    }

    /**
     * @return the nu_cep
     */
    public Intbox getNu_cep() {
        return nu_cep;
    }

    /**
     * @param nu_cep the nu_cep to set
     */
    public void setNu_cep(Intbox nu_cep) {
        this.nu_cep = nu_cep;
    }

    /**
     * @return the lt_complemento
     */
    public Textbox getLt_complemento() {
        return lt_complemento;
    }

    /**
     * @param lt_complemento the lt_complemento to set
     */
    public void setLt_complemento(Textbox lt_complemento) {
        this.lt_complemento = lt_complemento;
    }

    /**
     * @return the nu_numero
     */
    public Intbox getNu_numero() {
        return nu_numero;
    }

    /**
     * @param nu_numero the nu_numero to set
     */
    public void setNu_numero(Intbox nu_numero) {
        this.nu_numero = nu_numero;
    }

    /**
     * @return the lt_email
     */
    public Textbox getLt_email() {
        return lt_email;
    }

    /**
     * @param lt_email the lt_email to set
     */
    public void setLt_email(Textbox lt_email) {
        this.lt_email = lt_email;
    }

    /**
     * @return the lt_senha
     */
    public Textbox getLt_senha() {
        return lt_senha;
    }

    /**
     * @param lt_senha the lt_senha to set
     */
    public void setLt_senha(Textbox lt_senha) {
        this.lt_senha = lt_senha;
    }

    /**
     * @return the ppesquisar
     */
    public Popup getPpesquisar() {
        return ppesquisar;
    }

    /**
     * @param ppesquisar the ppesquisar to set
     */
    public void setPpesquisar(Popup ppesquisar) {
        this.ppesquisar = ppesquisar;
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
     * @return the resultadoBuscarBanco
     */
    public String getResultadoBuscarBanco() {
        return resultadoBuscarBanco;
    }

    /**
     * @param resultadoBuscarBanco the resultadoBuscarBanco to set
     */
    public void setResultadoBuscarBanco(String resultadoBuscarBanco) {
        this.resultadoBuscarBanco = resultadoBuscarBanco;
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
     * @return the DAO
     */
    public AtendenteDAO getDAO() {
        return DAO;
    }

    /**
     * @param DAO the DAO to set
     */
    public void setDAO(AtendenteDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * @return the zut
     */
    public ZkUtils getZut() {
        return zut;
    }

    /**
     * @param zut the zut to set
     */
    public void setZut(ZkUtils zut) {
        this.zut = zut;
    }

    /**
     * @return the resultadoSelecionado
     */
    public Atendente getResultadoSelecionado() {
        return resultadoSelecionado;
    }

    /**
     * @param resultadoSelecionado the resultadoSelecionado to set
     */
    public void setResultadoSelecionado(Atendente resultadoSelecionado) {
        this.resultadoSelecionado = resultadoSelecionado;
    }

    /**
     * @return the ent
     */
    public Atendente getEnt() {
        return ent;
    }

    /**
     * @param ent the ent to set
     */
    public void setEnt(Atendente ent) {
        this.ent = ent;
    }





}
