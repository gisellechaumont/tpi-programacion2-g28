package main;

import models.Usuario;
import models.CredencialAcceso;
import service.UsuarioService;

public class TestUsuarioCredencial {
    public static void main(String[] args) throws Exception {

        UsuarioService service = new UsuarioService();

        CredencialAcceso cred = new CredencialAcceso();
        cred.setHashPassword("123456");

        Usuario u = new Usuario();
        u.setUsername("agusFinal");
        u.setEmail("agusfinal@gmail.com");
        u.setActivo(true);

        service.insertarUsuarioConCredencial(u, cred);

        System.out.println("Usuario + credencial creados correctamente");
    }
}
