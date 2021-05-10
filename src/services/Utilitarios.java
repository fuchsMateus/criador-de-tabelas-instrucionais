package services;

public class Utilitarios {
	public static Object[][] processarInstrucao(String[] instrucoes){
		String[] coluna1 = new String[instrucoes.length];
		String[] coluna2 = new String[instrucoes.length];
		
		
		for (int i = 0; i < instrucoes.length; i++) {
			coluna1[i] = instrucoes[i].split(":")[0];
			if(coluna1[i].contains("ou")) {
				coluna1[i] = coluna1[i].substring(0, coluna1[i].indexOf("ou"));
			}
			coluna2[i] = instrucoes[i].split(":")[1].substring(1);
		}
	
		Object[][] data = new Object[instrucoes.length][2];
		
		for (int i = 0; i < instrucoes.length; i++) {
			data[i][0] = coluna1[i];
			data[i][1] = coluna2[i];
		}
		
		return data;
	}
}
