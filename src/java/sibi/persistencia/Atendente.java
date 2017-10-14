/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author alsnogtix
 */

@Entity
@Table(name = "ATENDENTE")
public class Atendente implements Serializable {

    @Id
    @Column(name = "CD_ATENDENTE")
    private Integer cd_atendente;

    @Column(name = "NM_NOME")
    private String nm_nome;

    @Column(name = "NU_CPF")
    private Integer nu_cpf;

    @Column(name = "NU_RG")
    private Integer nu_rg;

    @Column(name = "DT_DATA_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date dt_data_nascimento;

    @Column(name = "NU_CEP")
    private Integer nu_cep;

    @Column(name = "LT_ENDERECO")
    private String lt_endereco;

    @Column(name = "LT_BAIRRO")
    private String lt_bairro;

    @Column(name = "NU_NUMERO")
    private Integer nu_numero;

    @Column(name = "LT_COMPLEMENTO")
    private String lt_complemento;

    @Column(name = "LT_SEXO")
    private String lt_sexo;

    @Column(name = "LT_EMAIL")
    private String lt_email;

    @Column(name = "LT_SENHA")
    private String lt_senha;

    @Column(name = "DB_SALARIO")
    private Double db_salario;

    @Column(name = "DT_INICIO_TRABALHO")
    @Temporal(TemporalType.DATE)
    private Date dt_inicio_trabalho;

    
    
    
    public Atendente() {
    }

    /**
     * @return the cd_atendente
     */
    public Integer getCd_atendente() {
        return cd_atendente;
    }

    /**
     * @param cd_atendente the cd_atendente to set
     */
    public void setCd_atendente(Integer cd_atendente) {
        this.cd_atendente = cd_atendente;
    }

    /**
     * @return the nm_nome
     */
    public String getNm_nome() {
        return nm_nome;
    }

    /**
     * @param nm_nome the nm_nome to set
     */
    public void setNm_nome(String nm_nome) {
        this.nm_nome = nm_nome;
    }

    /**
     * @return the nu_cpf
     */
    public Integer getNu_cpf() {
        return nu_cpf;
    }

    /**
     * @param nu_cpf the nu_cpf to set
     */
    public void setNu_cpf(Integer nu_cpf) {
        this.nu_cpf = nu_cpf;
    }

    /**
     * @return the nu_rg
     */
    public Integer getNu_rg() {
        return nu_rg;
    }

    /**
     * @param nu_rg the nu_rg to set
     */
    public void setNu_rg(Integer nu_rg) {
        this.nu_rg = nu_rg;
    }

    /**
     * @return the dt_data_nascimento
     */
    public Date getDt_data_nascimento() {
        return dt_data_nascimento;
    }

    /**
     * @param dt_data_nascimento the dt_data_nascimento to set
     */
    public void setDt_data_nascimento(Date dt_data_nascimento) {
        this.dt_data_nascimento = dt_data_nascimento;
    }

    /**
     * @return the nu_cep
     */
    public Integer getNu_cep() {
        return nu_cep;
    }

    /**
     * @param nu_cep the nu_cep to set
     */
    public void setNu_cep(Integer nu_cep) {
        this.nu_cep = nu_cep;
    }

    /**
     * @return the lt_endereco
     */
    public String getLt_endereco() {
        return lt_endereco;
    }

    /**
     * @param lt_endereco the lt_endereco to set
     */
    public void setLt_endereco(String lt_endereco) {
        this.lt_endereco = lt_endereco;
    }

    /**
     * @return the lt_bairro
     */
    public String getLt_bairro() {
        return lt_bairro;
    }

    /**
     * @param lt_bairro the lt_bairro to set
     */
    public void setLt_bairro(String lt_bairro) {
        this.lt_bairro = lt_bairro;
    }

    /**
     * @return the nu_numero
     */
    public Integer getNu_numero() {
        return nu_numero;
    }

    /**
     * @param nu_numero the nu_numero to set
     */
    public void setNu_numero(Integer nu_numero) {
        this.nu_numero = nu_numero;
    }

    /**
     * @return the lt_complemento
     */
    public String getLt_complemento() {
        return lt_complemento;
    }

    /**
     * @param lt_complemento the lt_complemento to set
     */
    public void setLt_complemento(String lt_complemento) {
        this.lt_complemento = lt_complemento;
    }

    /**
     * @return the lt_sexo
     */
    public String getLt_sexo() {
        return lt_sexo;
    }

    /**
     * @param lt_sexo the lt_sexo to set
     */
    public void setLt_sexo(String lt_sexo) {
        this.lt_sexo = lt_sexo;
    }

    /**
     * @return the lt_email
     */
    public String getLt_email() {
        return lt_email;
    }

    /**
     * @param lt_email the lt_email to set
     */
    public void setLt_email(String lt_email) {
        this.lt_email = lt_email;
    }

    /**
     * @return the lt_senha
     */
    public String getLt_senha() {
        return lt_senha;
    }

    /**
     * @param lt_senha the lt_senha to set
     */
    public void setLt_senha(String lt_senha) {
        this.lt_senha = lt_senha;
    }

    /**
     * @return the db_salario
     */
    public Double getDb_salario() {
        return db_salario;
    }

    /**
     * @param db_salario the db_salario to set
     */
    public void setDb_salario(Double db_salario) {
        this.db_salario = db_salario;
    }

    /**
     * @return the dt_inicio_trabalho
     */
    public Date getDt_inicio_trabalho() {
        return dt_inicio_trabalho;
    }

    /**
     * @param dt_inicio_trabalho the dt_inicio_trabalho to set
     */
    public void setDt_inicio_trabalho(Date dt_inicio_trabalho) {
        this.dt_inicio_trabalho = dt_inicio_trabalho;
    }
    
}
