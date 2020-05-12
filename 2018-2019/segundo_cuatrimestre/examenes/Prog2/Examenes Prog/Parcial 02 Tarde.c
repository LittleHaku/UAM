/******************************************************************/
/*	 					EXAMEN PARCIAL 02 TARDE 				  */
/******************************************************************/


/* Ejercicio 1 */
Status listaCircular_imprimir_vuelta(const listaCircular *pl, int pos, FILE *f){
	if (!pl || pos < 0 || pos >= pl->last->info || !f){
		return ERROR;
	}

	
}

Status elemento_imprimir(Elemento *pe, FILE *f);

/* Ejercicio 2 */

/* a) */
BOOL ab_v_invertida(ArbolBinario T){
	si ab_vacio(T) = FALSE{
		si ab_vacio(izq(T)) = FALSE{
			si ab_v_invertida_rec_izq(izq(T)) = FALSE
				devolver FALSE
		}
		si ab_vacio(der(T))	= FALSE{
			si ab_v_invertida_rec_der(der(T)) = FALSE
				devolver FALSE
		}
		devolver TRUE
	}
	devolver FALSE
}

BOOL ab_v_invertida_rec_izq(ArbolBinario T){
	si ab_vacio(der(T)) = FALSE
		devolver FALSE

	si ab_vacio(izq(T)) = FALSE
		ab_v_invertida_rec_izq(izq(T))

	devolver TRUE
}

BOOL ab_v_invertida_rec_der(ArbolBinario T){
	si ab_vacio(izq(T)) = FALSE
		devolver FALSE

	si ab_vacio(der(T)) = FALSE
		ab_v_invertida_rec_izq(der(T))

	devolver TRUE
}

/* b) */
BOOL ab_v_invertida(ArbolBinario *pa){
	if (ab_vacio(pa) == FALSE){
		if (ab_vacio(izq(pa)) == FALSE){
			if (ab_v_invertida_rec_izq(izq(pa)) == FALSE){
				return FALSE;
			}
		}
		if (ab_vacio(der(pa)) == FALSE){
			if (ab_v_invertida_rec_der(der(pa)) == FALSE){
				return FALSE;
			}	
		}
		return TRUE;
	}
	return FALSE;
}

BOOL ab_v_invertida_rec_izq(ArbolBinario *pa){
	if (ab_vacio(der(pa)) == FALSE){
		return FALSE;
	}

	if (ab_vacio(izq(pa)) == FALSE){
		ab_v_invertida_rec_izq(izq(pa));
	}

	return TRUE;
}

BOOL ab_v_invertida_rec_der(ArbolBinario *pa){
	if (ab_vacio(izq(pa)) == FALSE){
		return FALSE;
	}

	if (ab_vacio(der(pa)) == FALSE){
		ab_v_invertida_rec_der(der(pa));
	}

	return TRUE;
}