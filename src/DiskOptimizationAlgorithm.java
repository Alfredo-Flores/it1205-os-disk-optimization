import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * IT1205 - Disk Optimization Algorithms Assignment (Module Group IT1206)
 * 
 * @author Wong Kang Fei (124465R)
 * @author Loi Fuh Chang (120501J)
 * @version 1.0
 * 
 */
public class DiskOptimizationAlgorithm {

	private List<Integer> secuencia;
	private int actual;
	private int previo;

	/**
	 * 
	 * Initialize a DiskOptimizationAlgorithm instance.
	 * 
	 * @param previo
	 *            - The previous cylinder served.
	 * @param actual
	 *            - Current position of the cylinder.
	 * @param secuencia
	 *            - Given queued cylinder sequence, excluding current cylinder.
	 */
	public DiskOptimizationAlgorithm(int previo, int actual,
									 List<Integer> secuencia) {
		this.secuencia = new ArrayList<Integer>();
		this.actual = actual;
		this.previo = previo;

		// Concatenates current cylinder and given sequence into new list, for
		// ease of calculations later.
		this.secuencia.add(actual);
		this.secuencia.addAll(secuencia);
	}

	public List<Integer> fcfs() {
		return this.secuencia;
	}

	public List<Integer> sstf() {
		int tamanolistasec = secuencia.size();
		int indiceactual = actual;
		int sstf[] = new int[tamanolistasec];
		for (int i = 0; i < tamanolistasec; i++) {
			sstf[i] = secuencia.get(i);
		}

		int indice2 = -1;
		for (int i = 0; i < tamanolistasec; i++) {
			int minimo = Integer.MAX_VALUE;
			indice2 = i;
			for (int j = i; j < tamanolistasec; j++) {
				int distancia = Math.abs(indiceactual - sstf[j]);
				if (distancia < minimo) {
					indice2 = j;
					minimo = distancia;
				}
			}
			int temp = sstf[i];
			sstf[i] = sstf[indice2];
			sstf[indice2] = temp;
			indiceactual = sstf[i];
		}

		List<Integer> ListaSstf = new ArrayList<Integer>();
		for (int indice = 0; indice < sstf.length; indice++) {
			ListaSstf.add(sstf[indice]);
		}

		return ListaSstf;
	}

	public List<Integer> scan() {
		List<Integer> NuevaLista = new ArrayList<Integer>(secuencia);

		int Direccion = previo - actual;

		if (Direccion < 0) {

			if (!NuevaLista.contains(4999)) {
				NuevaLista.add(4999);
			}

			Collections.sort(NuevaLista);
		} else if (Direccion > 0) {
			if(!NuevaLista.contains(0)){
				NuevaLista.add(0);
			}

			Collections.sort(NuevaLista);
			Collections.reverse(NuevaLista);
		} else {
			throw new UnsupportedOperationException(
					"El cilindro anterior y el cilindro actual son iguales. ¡El brazo no se mueve en absoluto!");
		}

		int IndiceDeCilindrosActual = NuevaLista.indexOf(actual);

		List<Integer> scanParte1 = NuevaLista.subList(0, IndiceDeCilindrosActual);
		List<Integer> scanParte2 = NuevaLista.subList(IndiceDeCilindrosActual,NuevaLista.size());

		Collections.reverse(scanParte1);

		scanParte2.addAll(scanParte1);

		return scanParte2;
	}

	public List<Integer> cscan() {

		List<Integer> NuevaLista = new ArrayList<Integer>(secuencia);

		int direction = previo - actual;
		
		// añadir limites superior o inferior si no lo tiene
		if (!NuevaLista.contains(4999)) {
			NuevaLista.add(4999);
		}
		if (!NuevaLista.contains(0)) {
			NuevaLista.add(0);
		}

		if (direction < 0) { // aumentando
			Collections.sort(NuevaLista);
		} else if (direction > 0) { // disminuyendo
			Collections.sort(NuevaLista);
			Collections.reverse(NuevaLista);
		} else {
			// nada
		}

		int IndiceDeCilindrosActual = NuevaLista.indexOf(actual);

		// partimos la lista en dos a base del indice actual
		List<Integer> scanParte1 = NuevaLista.subList(0, IndiceDeCilindrosActual);
		List<Integer> scanParte2 = NuevaLista.subList(IndiceDeCilindrosActual,
				NuevaLista.size());

		scanParte2.addAll(scanParte1);

		return scanParte2;
	}

	// para visualizar
	
	public List<Integer> cscanPlot() {

		List<Integer> NuevaLista = new ArrayList<Integer>(secuencia);

		int direction = previo - actual;
		
		// Adding cylinder boundaries if the given sequence do not have them
		if (!NuevaLista.contains(4999)) {
			NuevaLista.add(4999);
		}
		if (!NuevaLista.contains(0)) {
			NuevaLista.add(0);
		}

		if (direction < 0) { // increasing
			Collections.sort(NuevaLista);
		} else if (direction > 0) { // decreasing
			Collections.sort(NuevaLista);
			Collections.reverse(NuevaLista);
		} else {
			// do nothing
		}
		
		// This is for plotting purpose, to simulate discontinuous line between the boundary seek
		// Should remove the null when displaying the sequence
		NuevaLista.add(null);

		int IndiceDeCilindrosActual = NuevaLista.indexOf(actual);

		List<Integer> scanSplicedArr1 = NuevaLista.subList(0, IndiceDeCilindrosActual);
		List<Integer> scanSplicedArr2 = NuevaLista.subList(IndiceDeCilindrosActual,
				NuevaLista.size());

		scanSplicedArr2.addAll(scanSplicedArr1);

		return scanSplicedArr2;
	}
}
