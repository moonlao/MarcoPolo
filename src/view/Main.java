package view;

import java.awt.Color;
import java.util.ArrayList;

import model.*;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

	private Modelo main;

	public static void main(String[] args) {
		System.out.println("GGG");
		PApplet.main(Main.class.getName());
	}

	@Override
	public void settings() {

		main = new Modelo();
		main.init();
		size(main.getWidth(), main.getHeight());

	}

	@Override
	public void draw() {
		background(255);

		for (int i = 0; i < main.getArregloPolo().size(); i++) {
			Automata polo = main.getArregloPolo().get(i);
			fill(polo.getColor().getRed(), polo.getColor().getGreen(), polo.getColor().getBlue());
			circle((int) polo.getPosX(), (int) polo.getPosY(), polo.size);
			polo.mover();
		}
		fill(main.getMarco().getColor().getRed(), main.getMarco().getColor().getGreen(), main.getMarco().getColor().getBlue());
		circle((int) main.getMarco().getPosX(), (int) main.getMarco().getPosY(), main.getMarco().size);
		float xVal = (float) (main.getMarco().getPosX());
		float yVal = (float) (main.getMarco().getPosY());
		float lineXVal = (float) (xVal + main.getMarco().dx * main.getMarco().size * 0.7);
		float lineYVal = (float) (yVal + main.getMarco().dy * main.getMarco().size * 0.7);
		if (main.getMarco().estaAtrapando()) {
			fill(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
			line(xVal, yVal, lineXVal, lineYVal);
		}
		main.getMarco().mover();

	}

	public void buscarMasCercano() {
		double min = Integer.MAX_VALUE;
		double yMin = Integer.MAX_VALUE;
		double xMin = Integer.MAX_VALUE;
		for (int i = 0; i < main.getArregloPolo().size(); i++) {
			double x1 = main.getMarco().getPosX();
			double x2 = main.getArregloPolo().get(i).getPosX();

			double x3 = x2 - x1;

			double y1 = main.getMarco().getPosY();
			double y2 = main.getArregloPolo().get(i).getPosY();
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

		if (main.getMarco().estaAtrapando()) {
			main.getMarco().cambiarDireccion(p.x, p.y);
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
		for (int i = 0; i < main.getArregloPolo().size(); i++) {
			if (automata != main.getArregloPolo().get(i)) {
				double x1 = automata.getPosX();
				double x2 = main.getArregloPolo().get(i).getPosX();

				double x3 = x2 - x1;

				double y1 = automata.getPosY();
				double y2 = main.getArregloPolo().get(i).getPosY();
				double y3 = y2 - y1;

				double dist = Math.sqrt((x3 * x3) + (y3 * y3));

				if (dist <= automata.size) {
					index = i;
				}
			}

		}
		if (index != -1 && automata instanceof Marco) {
			if (((Marco) automata).estaAtrapando()) {
				Automata polo = main.getArregloPolo().remove(index);
				PVector vector = new PVector(polo.getPosX(), polo.getPosY());
				PVector vector2 = new PVector(main.getMarco().getPosX(), main.getMarco().getPosY());
				float angle = PVector.angleBetween(vector, vector2);
				Polo newPolo = new Polo(main.getMarco().getPosX(), main.getMarco().getPosY(), Modelo.POLO_SPEED, main.getMarco().size, main.getMarco().getColor(),
						main, (float) Math.toDegrees(angle));
				main.setMarco(new Marco(polo.getPosX(), polo.getPosY(), Modelo.MARCO_SPEED, polo.size, Color.GREEN, main,
						(float) Math.toDegrees(angle) + 180));
				main.getMarco().noEstaAtrapando();
				main.getArregloPolo().add(newPolo);
			}

		}

		return index != -1 ? main.getArregloPolo().get(index) : null;
	}

	public void cambiarDireccionPerseguido() {
		double min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < main.getArregloPolo().size(); i++) {
			double x1 = main.getMarco().getPosX();
			double x2 = main.getArregloPolo().get(i).getPosX();

			double x3 = x2 - x1;

			double y1 = main.getMarco().getPosY();
			double y2 = main.getArregloPolo().get(i).getPosY();
			double y3 = y2 - y1;

			double dist = Math.sqrt((x3 * x3) + (y3 * y3));
			if (dist < min) {
				min = dist;
				index = i;
			}
		}
		if (index != -1 && main.getMarco().estaAtrapando()) {
			Polo polo = main.getArregloPolo().get(index);
			PVector v1 = new PVector(main.getMarco().getPosX(), main.getMarco().getPosY());
			PVector v2 = new PVector(polo.getPosX(), polo.getPosY());
			PVector v3 = v2.sub(v1);
			v3 = v3.normalize();
			polo.dx = v3.x;
			polo.dy = v3.y;

		}
	}
}
