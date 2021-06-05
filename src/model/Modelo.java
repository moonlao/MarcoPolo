package model;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PVector;
import view.Main;


public class Modelo {
	
	public static final float POLO_SPEED = 1;
	public static final float MARCO_SPEED = 2;
	private ArrayList<Polo> arregloPolo = new ArrayList<>();
	public int width;
	public int height;
	private Marco marco;
	
	public void init() {

		width = 800;
		height = 800;

		marco = new Marco(400, 100, MARCO_SPEED, 50, Color.GREEN, this, 60);
		Polo polo1 = new Polo(20, 50, POLO_SPEED, 30, Color.BLACK, this, -45);
		Polo polo2 = new Polo(20, 200, POLO_SPEED, 40, Color.BLACK, this, 60);
		Polo polo3 = new Polo(300, 500, POLO_SPEED, 40, Color.BLACK, this, -140);
		arregloPolo.add(polo1);
		arregloPolo.add(polo2);
		arregloPolo.add(polo3);

		marco.init();
		for (int i = 0; i < arregloPolo.size(); i++) {
			arregloPolo.get(i).init();

		}

	}
	
	public void buscarMasCercano() {
		double min = Integer.MAX_VALUE;
		double yMin = Integer.MAX_VALUE;
		double xMin = Integer.MAX_VALUE;
		for (int i = 0; i < arregloPolo.size(); i++) {
			double x1 = marco.getPosX();
			double x2 = arregloPolo.get(i).getPosX();

			double x3 = x2 - x1;

			double y1 = marco.getPosY();
			double y2 = arregloPolo.get(i).getPosY();
			double y3 = y2 - y1;

			double dist = Math.sqrt((x3 * x3) + (y3 * y3));
			if (dist < min) {
				min = dist;
				xMin = x3;
				yMin = y3;
			}

		}
		PVector p = new PVector((float) xMin, (float) yMin);
		p = p.normalize();

		if (marco.estaAtrapando()) {
			marco.cambiarDireccion(p.x, p.y);
		}

	}

	public boolean chocaY(Automata automata) {
		float y = automata.getPosY();

		return y > height || y <= 0;
	}

	public boolean chocaX(Automata automata) {
		float x = automata.getPosX();
		return x > width || x <= 0;
	}

	public Automata chocaObjeto(Automata automata) {
		int index = -1;
		for (int i = 0; i < arregloPolo.size(); i++) {
			if (automata != arregloPolo.get(i)) {
				double x1 = automata.getPosX();
				double x2 = arregloPolo.get(i).getPosX();

				double x3 = x2 - x1;

				double y1 = automata.getPosY();
				double y2 = arregloPolo.get(i).getPosY();
				double y3 = y2 - y1;

				double dist = Math.sqrt((x3 * x3) + (y3 * y3));

				if (dist <= automata.size) {
					index = i;
				}
			}

		}
		if (index != -1 && automata instanceof Marco) {
			if (((Marco) automata).estaAtrapando()) {
				Automata polo = arregloPolo.remove(index);
				PVector vector = new PVector(polo.getPosX(), polo.getPosY());
				PVector vector2 = new PVector(marco.getPosX(), marco.getPosY());
				float angle = PVector.angleBetween(vector, vector2);
				Polo newPolo = new Polo(marco.getPosX(), marco.getPosY(), POLO_SPEED, marco.size, Color.PINK,
						this, (float) Math.toDegrees(angle));
				marco = new Marco(polo.getPosX(), polo.getPosY(), MARCO_SPEED + 1, polo.size, Color.GREEN, this,(float) Math.toDegrees(angle) + 180);
				marco.noEstaAtrapando();
				arregloPolo.add(newPolo);
			}

		}

		return index != -1 ? arregloPolo.get(index) : null;
	}

	public void cambiarDireccionPerseguido() {
		double min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < arregloPolo.size(); i++) {
			double x1 = marco.getPosX();
			double x2 = arregloPolo.get(i).getPosX();

			double x3 = x2 - x1;

			double y1 = marco.getPosY();
			double y2 = arregloPolo.get(i).getPosY();
			double y3 = y2 - y1;

			double dist = Math.sqrt((x3 * x3) + (y3 * y3));
			if (dist < min) {
				min = dist;
				index = i;
			}
		}
		if (index != -1 && marco.estaAtrapando()) {
			Polo polo = arregloPolo.get(index);
			PVector v1 = new PVector(marco.getPosX(), marco.getPosY());
			PVector v2 = new PVector(polo.getPosX(), polo.getPosY());
			PVector v3 = v2.sub(v1);
			v3 = v3.normalize();
			polo.dx = v3.x;
			polo.dy = v3.y;

		}
	}

	public ArrayList<Polo> getArregloPolo() {
		return arregloPolo;
	}

	public void setArregloPolo(ArrayList<Polo> arregloPolo) {
		this.arregloPolo = arregloPolo;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Marco getMarco() {
		return marco;
	}

	public void setMarco(Marco marco) {
		this.marco = marco;
	}
	
	

}
