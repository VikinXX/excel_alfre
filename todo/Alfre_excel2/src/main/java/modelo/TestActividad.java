 package modelo;

import javax.swing.JOptionPane;

public class TestActividad {

	public static void main(String[] args) {
		
		int miembroS=Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos puntos tiene de miembro superior?"));
		int muneca=Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos puntos tiene de muñeca?"));
		int mano=Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos puntos tiene de mano?"));
		int coordi=Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos puntos tiene de coordinación/velocidad?"));
		String [] actividades= {"_","_","_","_"};
		String opcion="si";
		Actividad ac1=new Actividad(miembroS, muneca, mano, coordi);
		
		while(opcion.equals("si")) {
			
			while(ac1.comprobarRepetidos(actividades)) {
				for(int i=0 ; i<4 ; i++) {
					actividades[i]=ac1.setActividad();
				}
			}
			
			JOptionPane.showMessageDialog(null, actividades[0]+", "+actividades[1]+", "+actividades[2]+", "+actividades[3]);
			opcion=JOptionPane.showInputDialog("¿Quieres sacar otras 4 actividades?").toLowerCase();
			for(int i=0 ; i<4 ; i++) {
				actividades[i]=ac1.setActividad();
			}
		}
	}
	
}
