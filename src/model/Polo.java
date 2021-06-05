package model;

import java.awt.Color;

import view.Main;

public class Polo extends Automata {

	public Polo(float posX, float posY, float speed, float size, Color color, Modelo main, float angulo) {
		super(posX, posY, (float) Math.cos(Math.toRadians(angulo)), (float) Math.sin(Math.toRadians(angulo)), speed,
				size, color, main, () -> {

					while (true) {
						try {
							Thread.sleep(3000);
							main.cambiarDireccionPerseguido();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}

				});

	}

}
