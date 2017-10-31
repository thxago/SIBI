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
 * @author admin
 */
public class Opcoes_usuario extends Window{

    private Window winMenu;
    private Include tela;
    
    public Opcoes_usuario(){
        
    }
     
    public void onCreate(){
        this.setWinMenu((Window) getFellow("winMenu"));
        this.setTela((Include) getFellow("tela"));
    }

    
    public void cadastroUsuario(){
        this.tela.setSrc("cad_usuario.zul");
    }
    
    public void movimentacaoReserva(){
        this.tela.setSrc("mov_solicita_reserva.zul");
    }
    
    public void movimentacaoRenovacao(){
        this.tela.setSrc("mov_renovacao.zul");
    }
    
    public void solicitarEmprestimo(){
        this.tela.setSrc("mov_solicitar_emprestimo.zul");
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