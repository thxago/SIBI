/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.utilitarios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import java.sql.Clob;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
//import utilitarios.Utils;

/**
 *
 * @author root
 */
public class ZkUtils {

//    private static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
//    
//    private Pattern p = Pattern.compile(EMAIL_REGEX);
//
//    public String ano() {
//        int iano = 0;
//        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
//        Sessao Ss = new Sessao();
//        Ss.setRequest(request);
//        String anoOrc = Ss.retornarAtributoSessao("ano");
//
//        try {
//            iano = Integer.parseInt(anoOrc);
//        } catch (Exception e) {
//            Utils ut = new Utils();
//            anoOrc = ut.getDateTime("yyyy");
//        }
//
//
//        return anoOrc;
//    }

    public byte[] gerarHash(String frase, String algoritmo) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String gerarMD5(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            String crypto = hash.toString(16);
            if (crypto.length() % 2 != 0) {
                crypto = "0" + crypto;
            }
            return crypto;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public void selecionarLista(Listbox lb, long valor) {

        for (int i = 0; i < lb.getItemCount(); i++) {
            Listitem li = lb.getItemAtIndex(i);
            try {
                if (li.getValue().toString().equals(Long.toString(valor))) {
                    lb.selectItem(li);
                    return;
                }

            } catch (Exception ex) {
            }
        }

    }

    public void selecionarLista(Listbox lb, String valor) {

        for (int i = 0; i < lb.getItemCount(); i++) {
            Listitem li = lb.getItemAtIndex(i);
            try {
                if (li.getValue().equals(valor)) {
                    lb.selectItem(li);
                    return;
                }

            } catch (Exception ex) {
            }
        }

    }

    public void selecionarListaLabel(Listbox lb, String valor) {

        for (int i = 0; i < lb.getItemCount(); i++) {
            Listitem li = lb.getItemAtIndex(i);
            Listcell cell = (Listcell) li.getChildren().get(0);
            String scell = cell.getLabel().substring(0, 10);
            try {
                if (scell.equals(valor)) {
                    lb.selectItem(li);
                    return;
                }

            } catch (Exception ex) {
            }
        }

    }

    public void limparListbox(Listbox lb) {

        lb.getItems().clear();

    }

    public void carregarListbox(Listbox lb, List resultadosPesquisa) {
        int s = 0;
        for (Iterator i = resultadosPesquisa.iterator(); i.hasNext();) {
            Object[] obj = (Object[]) i.next();

            Listitem item = new Listitem();
            Listcell cellCodigo = new Listcell();
            Listcell cellDescricao = new Listcell();
            cellDescricao.setStyle("line-height: 100%;");

            cellCodigo.setLabel(obj[0].toString());
            cellDescricao.setLabel(obj[1].toString());

            item.appendChild(cellCodigo);
            item.appendChild(cellDescricao);
            item.setValue(obj[0].toString());
            item.setLabel(obj[1].toString());

            if (s == 0) {
                item.setSelected(true);
                s = 1;
            }

            item.setParent(lb);

            //   lbResultados.getItems().add(item);
        }

    }
//
//    public void incluirAtributoSessao(String atributo, String valor) {
//
//        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
//        Sessao Ss = new Sessao();
//        Ss.setRequest(request);
//        Ss.setSessao(request.getSession());
//        //String ssmsg = Ss.verificaSessao();
//        HttpSession sess = Ss.getSessao();
//        sess.setAttribute(atributo, valor);
//
//    }
//
//    public String getAtributoSessao(String atributo) {
//
//        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
//        Sessao Ss = new Sessao();
//        Ss.setRequest(request);
//        Ss.setSessao(request.getSession());
//        //String ssmsg = Ss.verificaSessao();
//        return (String) Ss.getSessao().getAttribute(atributo);
//
//    }

    public String formatarDecimal(String valor) {

        if (valor.equals("0")) {
            return "0,00";
        }
        DecimalFormat myformat = new DecimalFormat("#,###,###.00");
        //float fvalor = Float.parseFloat(valor);
        double dvalor = Double.parseDouble(valor);
        String retorno = myformat.format(dvalor);
//        NumberFormat z = NumberFormat.getCurrencyInstance();
//        String retorno = z.format(Double.parseDouble(valor));
        return retorno;

    }

    public String formatarData(Date dt, String mascara) {
        Format formatter;
        formatter = new SimpleDateFormat(mascara);
        return (formatter.format(dt));

    }

    public String diferencaDias(String dataNormalInicio, String dataNormalFim) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date di = null;
        Date df = null;
        try {
            di = sdf.parse(dataNormalInicio);
            df = sdf.parse(dataNormalFim);
        } catch (java.text.ParseException e) {
            return "";
        }

        long diferencaMS = df.getTime() - di.getTime();
        long diferencaSegundos = diferencaMS / 1000;
        long diferencaMinutos = diferencaSegundos / 60;
        long diferencaHoras = diferencaMinutos / 60;
        long diferencaDias = diferencaHoras / 24;

        String retorno = Long.toString(diferencaDias);
        /*
         System.out.println(diferencaMS);
         System.out.println(diferencaSegundos);
         System.out.println(diferencaMinutos);
         System.out.println(diferencaHoras);
         System.out.println(diferencaDias);
         */

        return retorno;

    }

    public String formatarClob(java.sql.Clob s) throws SQLException, IOException {

        char[] buffer = new char[1024];
        Reader instream = s.getCharacterStream();
        StringBuffer sb = new StringBuffer();
        int length;
        while ((length = instream.read(buffer)) != -1) {
            sb.append(buffer, 0, length);
        }
        instream.close();
        String strFinal = sb.toString();

        return strFinal;
    }

    public String clobStringConversion(Clob clb) throws IOException, SQLException {
        if (clb == null) {
            return "";
        }

        StringBuffer str = new StringBuffer();
        String strng;


        BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

        while ((strng = bufferRead.readLine()) != null) {
            str.append(strng);
        }

        return str.toString();
    }

    public String convertClobToString(Clob clob) {
        String toRet = "";
        if (clob != null) {
            try {
                long length = clob.length();
                toRet = clob.getSubString(1, (int) length);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return toRet;
    }

//    public boolean validaEmail(String email) {
//        return p.matcher(email).matches();
//    }

    public void lerArquivo(org.zkoss.util.media.Media media, String nomeDoArquivo, Listbox lb) throws Exception, FileNotFoundException, IOException {


        File arquivo;

        arquivo = new File(nomeDoArquivo);
        FileOutputStream fos = new FileOutputStream(arquivo);
        String texto = media.getStringData();
        fos.write(texto.getBytes());
        fos.close();

        File arq = new File(nomeDoArquivo); //Criamos um nome para o arquivo  
        try {
            //Indicamos o arquivo que será lido
            FileReader fileReader = new FileReader(arq);

            //Criamos o objeto bufferReader que nos
            // oferece o método de leitura readLine()
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //String que irá receber cada linha do arquivo
            String linha = "";

            //Fazemos um loop linha a linha no arquivo,
            // enquanto ele seja diferente de null.
            //O método readLine() devolve a linha na
            // posicao do loop para a variavel linha.
            while ((linha = bufferedReader.readLine()) != null) {
                //Aqui imprimimos a linha
                lb.appendItem(linha, linha);
            }

            //liberamos o fluxo dos objetos ou fechamos o arquivo
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void gravarArquivo(org.zkoss.util.media.Media media, String nomeDoArquivo) throws Exception, FileNotFoundException, IOException {

        File file = new File(nomeDoArquivo); //Criamos um nome para o arquivo  

        InputStream inputStream = media.getStreamData();
        OutputStream out = new FileOutputStream(file);
        byte buf[] = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        inputStream.close();

    }

    public String valorPorExtenso(double vlr) {
        if (vlr == 0) {
            return ("zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (s.length() != 0) {
            if (umReal) {
                s = s + " real";
            } else if (tem) {
                s = s + " reais";
            } else {
                s = s + " de reais";
            }
        }

// definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
            {
                s = s + " e ";
            }
            if (centavos.equals("1")) {
                s = s + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " e " + unidade[unid];
                    }
                }
                s = s + " centavos";
            }
        }
        return (s);
    }

    public void gravarArquivo(String origem, String destino) throws Exception, FileNotFoundException, IOException {

        File file = new File(destino); //Criamos um nome para o arquivo  

        if (!file.exists()) {

            InputStream inputStream = new FileInputStream(origem);

            OutputStream out = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();

        }
    }

    public void apagarArquivo(String caminhoDoArquivo) {
        File file = new File(caminhoDoArquivo);
        file.delete();
    }

    public String caracteresEspeciais(String str) {

        /**
         * Troca os caracteres especiais da string por "" *
         */
        String[] caracteresEspeciais = {"\\ ", ",", "-", ":", "\\(", "\\)", "ª", "\\|", "\\\\", "°", "° ", "ç", "á"};

        for (int i = 0; i < caracteresEspeciais.length; i++) {
            str = str.replaceAll(caracteresEspeciais[i], "");
        }

        /**
         * Troca os espaços no início por "" *
         */
        str = str.replaceAll("^\\s+", "");
        /**
         * Troca os espaços no início por "" *
         */
        str = str.replaceAll("\\s+$", "");
        /**
         * Troca os espaços duplicados, tabulações e etc por " " *
         */
        str = str.replaceAll("\\s+", " ");
        return trata(str);
    }

    public String trata(String passa) {
        passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
        passa = passa.replaceAll("[âãàáä]", "a");
        passa = passa.replaceAll("[ÊÈÉË]", "E");
        passa = passa.replaceAll("[êèéë]", "e");
        passa = passa.replaceAll("ÎÍÌÏ", "I");
        passa = passa.replaceAll("îíìï", "i");
        passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
        passa = passa.replaceAll("[ôõòóö]", "o");
        passa = passa.replaceAll("[ÛÙÚÜ]", "U");
        passa = passa.replaceAll("[ûúùü]", "u");
        passa = passa.replaceAll("Ç", "C");
        passa = passa.replaceAll("ç", "c");
        passa = passa.replaceAll("[ýÿ]", "y");
        passa = passa.replaceAll("Ý", "Y");
        passa = passa.replaceAll("ñ", "n");
        passa = passa.replaceAll("Ñ", "N");
        passa = passa.replaceAll("['<>\\|/]", "");
        return passa;
    }

    public double getMenorValor(double[] vetor) {
        double menorValor = vetor[0];
        for (double valor : vetor) {
            if (menorValor > valor && valor > 0) {
                menorValor = valor;
            }
        }
        return menorValor;
    }

    public double getMaiorValor(double[] vetor) {
        double maiorValor = vetor[0];
        for (double valor : vetor) {
            if (maiorValor < valor && valor > 0) {
                maiorValor = valor;
            }
        }
        return maiorValor;
    }

    //Parâmetros:  
    /**
     * 1 - Valor a arredondar. 2 - Quantidade de casas depois da vírgula. 3 -
     * Arredondar para cima ou para baixo? Para cima = 0 (ceil) Para baixo = 1
     * ou qualquer outro inteiro (floor)
     *
     */
    public double arredondar(double valor, int casas, int ceilOrFloor) {
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (ceilOrFloor == 0) {
            arredondado = Math.ceil(arredondado);
        } else {
            arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }

    public java.util.Date getClassDateCompilation(Class clazz) throws IOException {
        String className = clazz.getName();
        className = className.replaceAll("\\.", "/");
        className = "/" + className + ".class";
        URL url = Class.class.getResource(className);
        URLConnection urlConnection = url.openConnection();
        java.util.Date lastModified = new java.util.Date(urlConnection.getLastModified());
        return lastModified;
    }

    public boolean verificaPIS(String pisOrPasep) {
        int digit_count = 11;
        if (pisOrPasep == null) {
            return false;
        }
        String n = pisOrPasep.replaceAll("[^0-9]*", "");
        if (n.length() != digit_count) {
            return false;
        }
        int i;          // just count 
        int digit;      // A number digit
        int coeficient; // A coeficient  
        int sum;        // The sum of (Digit * Coeficient)
        int foundDv;    // The found Dv (Chek Digit)
        int dv = Integer.parseInt(String.valueOf(n.charAt(n.length() - 1)));
        sum = 0;
        coeficient = 2;
        for (i = n.length() - 2; i >= 0; i--) {
            digit = Integer.parseInt(String.valueOf(n.charAt(i)));
            sum += digit * coeficient;
            coeficient++;
            if (coeficient > 9) {
                coeficient = 2;
            }
        }
        foundDv = 11 - sum % 11;
        if (foundDv >= 10) {
            foundDv = 0;
        }
        return dv == foundDv;
    }

    public FileOutputStream abrirArquivoTexto(String nomeArq) throws FileNotFoundException {
        File arquivo;
        arquivo = new File(nomeArq);
        return new FileOutputStream(arquivo);
    }

    public void fecharArquivoTexto(FileOutputStream arquivo) throws IOException {
        arquivo.close();
    }
    
        public boolean validarCampo(Component c) {
        boolean r = true;
        String classe = c.getWidgetClass();

        if (classe.contains("Textbox")) {
            Textbox tb = (Textbox) c;
            if (tb.getValue() == null || tb.getValue().equals("")) {
                Messagebox.show("Campo obrigatório!", "Gescomp", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                tb.focus();
                return false;
            }
        }

        if (classe.contains("Longbox")) {
            Longbox tb = (Longbox) c;
            if (tb.getValue() == null) {
                Messagebox.show("Campo obrigatório!", "Gescomp", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                tb.focus();
                return false;
            }
        }

        if (classe.contains("Decimalbox")) {
            Decimalbox tb = (Decimalbox) c;
            if (tb.getValue() == null) {
                Messagebox.show("Campo obrigatório!", "Gescomp", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                tb.focus();
                return false;
            }
        }

        if (classe.contains("Datebox")) {
            Datebox tb = (Datebox) c;
            if (tb.getText() == null || tb.getText().equals("")) {
                Messagebox.show("Campo obrigatório!", "Gescomp", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                tb.focus();
                return false;
            }
        }

        if (classe.contains("Timebox")) {
            Timebox tb = (Timebox) c;
            if (tb.getText() == null || tb.getText().equals("")) {
                Messagebox.show("Campo obrigatório!", "Gescomp", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION);
                tb.focus();
                return false;
            }
        }

        return r;
    }
//
//    public void popularCampo(Component c, Object vlr) {
//        Utils ut = new Utils();
//
//        try {
//            String valor = (String) vlr.toString();
//
//            if (valor != null && !valor.equals("")) {
//
//                String classe = c.getWidgetClass();
//
//                if (classe.contains("Textbox")) {
//                    Textbox tb = (Textbox) c;
//                    tb.setValue(valor);
//                }
//
//                if (classe.contains("Longbox")) {
//                    Longbox tb = (Longbox) c;
//                    tb.setValue(Long.parseLong(valor));
//                }
//                
//                if (classe.contains("Intbox")) {
//                    Intbox tb = (Intbox) c;
//                    tb.setValue(Integer.parseInt(valor));
//                }
//
//                if (classe.contains("Decimalbox")) {
//                    Decimalbox tb = (Decimalbox) c;
//                    tb.setValue(BigDecimal.valueOf(Double.parseDouble(valor)));
//                }
//
//                if (classe.contains("Datebox")) {
//                    Datebox tb = (Datebox) c;
//                    tb.setValue(ut.strToDate(ut.dataNormal(valor)));
//                }
//
//                if (classe.contains("Timebox")) {
//                    Timebox tb = (Timebox) c;
//                    tb.setText(valor);
//                }
//                if (classe.contains("Label")) {
//                    Label tb = (Label) c;
//                    tb.setValue(valor);
//                }
//                
//
//            }
//        } catch (Exception e) {
//        }
//    }
//

    
}