/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.controle;

import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

/**
 *
 * @author alsnogtix
 */
public class Opcoes_gerente extends Window{

    private Window winMenu;
    private Include tela;
    
    public Opcoes_gerente(){
        
    }
     
    public void onCreate(){
        this.setWinMenu((Window) getFellow("winMenu"));
        this.setTela((Include) getFellow("tela"));
        
    }

    public void cadastroGerente(){
        this.tela.setSrc("cad_gerente.zul");
    }

        
    public void cadastroAtendente(){
        this.tela.setSrc("cad_atendente.zul");
    }
            
    public void cadastroUsuario(){
        this.tela.setSrc("cad_usuario.zul");
    }
    
    public void cadastroBiblioteconomista(){
        this.tela.setSrc("cad_biblioteconomista.zul");
    }

    
    public void cadastroMaterialBibliografico(){
        this.tela.setSrc("cad_material_bibliografico.zul");
    }

     
    public void consultaPagamento(){
        this.tela.setSrc("con_pagamento.zul");
    }

    public void consultaInventario(){
        this.tela.setSrc("con_inventario.zul");
    }
    public void consultaOperacoesUsuario(){
        this.tela.setSrc("con_operacoes_usuario.zul");
    }
    
    public void movimentacaoEmprestimo(){
        this.tela.setSrc("mov_emprestimo.zul");
    }

    public void movimentacaoDevolucao(){
        this.tela.setSrc("mov_devolucao.zul");
    }
    
    public void solicitacoesEmprestimo(){
        this.tela.setSrc("mov_solicitacoes_emprestimo.zul");
    }
    /**
     * @return the winMenu
     */
    public Window getWinMenu() {
        return winMenu;
    }

    /**
     * @param winMenu the winMenu to set
     */
    public void setWinMenu(Window winMenu) {
        this.winMenu = winMenu;
    }

    /**
     * @return the tela
     */
    public Include getTela() {
        return tela;
    }

    /**
     * @param tela the tela to set
     */
    public void setTela(Include tela) {
        this.tela = tela;
    }
}
