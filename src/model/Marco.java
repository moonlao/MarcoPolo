package model;

import java.awt.Color;

import view.Main;

public class Marco extends Automata {

	private static long TIEMPO_DE_ESPERA = 4000l;
	private boolean atrapando;

	public Marco(float posX, float posY, float speed, float size, Color color, Modelo main, float angulo) {
		super(posX, posY, (float) Math.cos(Math.toRadians(angulo)), (float) Math.sin(Math.toRadians(angulo)), speed,
				size, color, main, () -> {

					while (true) {
						try {
							Thread.sleep(2000);
							main.buscarMasCercano();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
		atrapando = true;

	}

	public void cambiarDireccion(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void noEstaAtrapando() {
		this.atrapando = false;
		if (TIEMPO_DE_ESPERA <= 0) {
			atrapando = true;
		} else {

			new Thread(() -> {
				try {
					Thread.sleep(TIEMPO_DE_ESPERA);
					atrapando = true;

				} catch (Exception e) {
					// TODO: handle exception
				}
			}).start();
		}
	}

	public boolean estaAtrapando() {
		return atrapando;
	}

}
