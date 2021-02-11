package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class UserImpl extends Conexion {

        public Usuario iniciarSesion(Usuario objeto) throws Exception {
            try {
                Usuario usuario = null;
                ResultSet rs;
                String sql = "SELECT IDUSU, EMLUSU, NICKUSU FROM USUARIO\n"
                        + " where NICKUSU = ? and PSSUSU = ?";
                PreparedStatement ps = this.conectar().prepareStatement(sql);
                ps.setString(1, objeto.getNICKUSU().toUpperCase());
                ps.setString(2, objeto.getPSSUSU());
                rs = ps.executeQuery();

                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setCODUSU(rs.getString(1));
                    usuario.setEMLUSU(rs.getString(2));
                    usuario.setNICKUSU(rs.getString(3));
                    return usuario;
                }else{
                    return null;
                }
            } catch (Exception e) {
                throw e;
            } finally {
                this.cerrarCnx();
            }
        }

        public String generarCode(){
            String twoFaCode = String.valueOf(new Random().nextInt(9999) + 1000);
            return  twoFaCode;
        }

        public String reenviarCode(String id) throws Exception{

                String sql = "UPDATE USUARIO SET CODE = ?, EXPIRETIME = ? "
                        + " WHERE IDUSU = ?";
                String nuevoCode = generarCode();
                PreparedStatement ps = this.conectar().prepareStatement(sql);
                ps.setString(1, nuevoCode);
                /* Obtenemos la hora actual(Milisegundos) y le sumamos más 30 seg. Es el tiempo que el user tendra para
                  ingresar el code  5: 20 : 15 ||  5: 20 : 45*/
                ps.setString(2, String.valueOf((System.currentTimeMillis()/1000)+30));
                ps.setString(3, id);
                ps.executeUpdate();
                ps.close();
                return nuevoCode;
        }

    /* Obtenemos el code y comparamos el tiempo de ingreso: si es igual o mayor de lo acordado
     el code expirá */
    public String verificarCode(String id, String code) throws Exception{
                String idUsuario = null;
                ResultSet rs;
                String sql = "SELECT IDUSU FROM USUARIO WHERE CODE = ? and IDUSU = ? AND EXPIRETIME >= ?";
                PreparedStatement ps = this.conectar().prepareStatement(sql);
                ps.setString(1, code);
                ps.setString(2, id);
                ps.setString(3, String.valueOf(System.currentTimeMillis()/1000));
                rs = ps.executeQuery();
                if (rs.next()){
                    idUsuario = rs.getString(1);

                }
        return idUsuario;

    }


}
