package UFPS.Json;

import java.util.List;

import UFPS.model.Usuario;
import co.edu.ufps.tictactoe.util.Utils;

public class Ranking {
	private List<Usuario> listaUsuarios;
	private Utils util;
	public final int tipo = 2;

	public Ranking () {
		listaUsuarios = util.getListaUsuario();
	}

	public List<Usuario> getListaUsuarios() {
		return this.listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
}
