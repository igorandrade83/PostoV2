package blockly;

import cronapi.*;
import cronapi.rest.security.CronappSecurity;
import java.util.concurrent.Callable;

@CronapiMetaData(type = "blockly")
@CronappSecurity
public class Campeoes {

	public static final int TIMEOUT = 300;

	/**
	 *
	 * @return Var
	 */
	// Campeoes
	public static Var carros() throws Exception {
		return new Callable<Var>() {

			private Var retorno = Var.VAR_NULL;
			private Var consulta = Var.VAR_NULL;
			private Var valor_parcial = Var.VAR_NULL;

			public Var call() throws Exception {
				retorno = cronapi.list.Operations.newList();
				consulta = cronapi.database.Operations.query(Var.valueOf("app.entity.Abastecimento"),
						Var.valueOf(
								"select a.carro.marca.fipe_name, a.carro.modelo.fipe_name, SUM(a.valor)/SUM(a.km) from Abastecimento a  group by a.carro.marca.fipe_name, a.carro.modelo.fipe_name  order by SUM(a.valor)/SUM(a.km) asc"),
						Var.VAR_NULL);
				while (cronapi.database.Operations.hasElement(consulta).getObjectAsBoolean()) {
					valor_parcial = cronapi.map.Operations.createObjectMapWith(
							Var.valueOf("custo_medio",
									cronapi.database.Operations.getField(consulta, Var.valueOf("this[2]"))),
							Var.valueOf("marca",
									cronapi.database.Operations.getField(consulta, Var.valueOf("this[0]"))),
							Var.valueOf("modelo",
									cronapi.database.Operations.getField(consulta, Var.valueOf("this[1]"))));
					cronapi.list.Operations.addLast(retorno, valor_parcial);
					cronapi.database.Operations.next(consulta);
				} // end while
				return retorno;
			}
		}.call();
	}

}
