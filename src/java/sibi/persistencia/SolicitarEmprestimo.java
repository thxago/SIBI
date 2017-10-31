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
 * @author admin
 */
@Entity
@Table(name = "SOLICITAR_EMPRESTIMO")

public class SolicitarEmprestimo implements Serializable {

    @Id
    @Column(name = "CD_SOLICITACAO")
    private Integer cd_solicitacao;

    @Column(name = "CD_MATERIAL_BIBLIOGRAFICO")
    private Integer cd_material_bibliografico;

    @Column(name = "CD_USUARIO")
    private Integer cd_usuario;

    @Column(name = "LT_SITUACAO")
    private String lt_situacao;

    @Column(name = "DT_EMPRESTIMO")
    @Temporal(TemporalType.DATE)
    private Date dt_emprestimo;

    @Column(name = "DT_DEVOLUCAO")
    @Temporal(TemporalType.DATE)
    private Date dt_devolucao;
        
    @Column(name = "NU_RENOVACAO")
    private Integer nu_renovacao;

    public SolicitarEmprestimo() {
    }

    /**
     * @return the cd_solicitacao
     */
    public Integer getCd_solicitacao() {
        return cd_solicitacao;
    }

    /**
     * @param cd_solicitacao the cd_solicitacao to set
     */
    public void setCd_solicitacao(Integer cd_solicitacao) {
        this.cd_solicitacao = cd_solicitacao;
    }

    /**
     * @return the cd_material_bibliografico
     */
    public Integer getCd_material_bibliografico() {
        return cd_material_bibliografico;
    }

    /**
     * @param cd_material_bibliografico the cd_material_bibliografico to set
     */
    public void setCd_material_bibliografico(Integer cd_material_bibliografico) {
        this.cd_material_bibliografico = cd_material_bibliografico;
    }

    /**
     * @return the cd_usuario
     */
    public Integer getCd_usuario() {
        return cd_usuario;
    }

    /**
     * @param cd_usuario the cd_usuario to set
     */
    public void setCd_usuario(Integer cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    /**
     * @return the lt_situacao
     */
    public String getLt_situacao() {
        return lt_situacao;
    }

    /**
     * @param lt_situacao the lt_situacao to set
     */
    public void setLt_situacao(String lt_situacao) {
        this.lt_situacao = lt_situacao;
    }

    /**
     * @return the dt_emprestimo
     */
    public Date getDt_emprestimo() {
        return dt_emprestimo;
    }

    /**
     * @param dt_emprestimo the dt_emprestimo to set
     */
    public void setDt_emprestimo(Date dt_emprestimo) {
        this.dt_emprestimo = dt_emprestimo;
    }

    /**
     * @return the dt_devolucao
     */
    public Date getDt_devolucao() {
        return dt_devolucao;
    }

    /**
     * @param dt_devolucao the dt_devolucao to set
     */
    public void setDt_devolucao(Date dt_devolucao) {
        this.dt_devolucao = dt_devolucao;
    }


    /**
     * @return the nu_renovacao
     */
    public Integer getNu_renovacao() {
        return nu_renovacao;
    }

    /**
     * @param nu_renovacao the nu_renovacao to set
     */
    public void setNu_renovacao(Integer nu_renovacao) {
        this.nu_renovacao = nu_renovacao;
    }

}
