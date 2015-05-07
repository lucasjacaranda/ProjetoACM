package ACM;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Conexao {
    Connection con;
    Statement sql;
    ResultSet rs;
    private String dbDriver  = "org.firebirdsql.jdbc.FBDriver";
    private String dbUrl     = "jdbc:firebirdsql:localhost/3050:C:/Program Files/Firebird/SGF.FDB"; 
    private String dbUsuario = "SYSDBA"; 
    private String dbSenha   = "masterkey";
    
    public void ConectarBD() {
        try {
            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbUrl, dbUsuario, dbSenha);
            sql = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,
                                      rs.CONCUR_UPDATABLE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Conexão Negada.\nVerifique com o suporte do Banco de Dados.",
                    "Erro",
                 JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void salvar_lancamento(String DATA, String DESC, Float VALOR, String FORMA, String TIPO) throws SQLException
    {
        PreparedStatement sql = null;
        
        sql = con.prepareStatement("INSERT INTO TBLLAN (ID, DATA, DESC, VALOR, FORMA, TIPO, DEL)" +
                                   "VALUES(NULL, '" + DATA + "', '" + DESC + "', " + VALOR + ", '" +
                                   FORMA + "', '" + TIPO + "', 'N')");
        sql.executeUpdate(); //Executa insert no banco
        sql.close(); //Fecha a execução de insert no banco
    }

    public void apagar_lancamento(Integer ID) throws SQLException
    {
        PreparedStatement sql = null;                
        
        sql = con.prepareStatement("DELETE FROM TBLLAN WHERE ID = " + ID);
        sql.executeUpdate(); //Executa delete no banco
        sql.close(); //Fecha a execução de delete no banco
    }
    
    public void listar_lancamentos() throws SQLException
    {
        rs = sql.executeQuery("SELECT * FROM TBLLAN WHERE DEL = 'N' ORDER BY ID"); //Executa select no banco
    }

    void transferir_lancamento(Integer ID, String DATA, String DESC, float VALOR, String FORMA, String TIPO) throws SQLException {
        PreparedStatement sql = null;                
        
        sql = con.prepareStatement("UPDATE TBLLAN SET DATA = '" + DATA + "', DESC = '" + DESC +
                                   "', VALOR = " + VALOR + ", FORMA = '" + FORMA + "', TIPO = '" + TIPO +
                                   "', DEL = 'N' WHERE ID = " + ID);
        sql.executeUpdate(); //Executa delete no banco
        sql.close(); //Fecha a execução de delete no banco
    }
}