package pe.edu.unfv.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constantes {

	public static final int UNO=1;
	public static final String EL_EMAIL="El email: ";
	public static final String NO_EXISTE=" no existe en el sistema!";
	public static final String NO_ROL=" no tiene roles asignados en el sistema!";
	public static final String PARAM_1="elio@net";
	public static final String PARAM_2="content e-mail";
	public static final String PNG="png";
	public static final Path TEMPLATE_PATH = Paths.get("src/main/resources/templates/email/template/email_template.html");
	public static final Integer CANTIDAD_POR_PAGINA=3;
	public static final String FIRMA="12345678";
}
