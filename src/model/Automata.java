package model;

import java.awt.Color;

import processing.core.PVector;
import view.Main;

public class Automata {

	private float posX;
	private float posY;
	public float speed;
	private Color color;
	private Modelo main;
	private Thread validacion;
	public float dx, dy;
	public float size;

	public Automata(float posX, float posY, float dx, float dy, float speed, float size, Color color, Modelo main,
			Runnable validacion) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.size = size;
		this.dx = dx;
		this.dy = dy;
		this.color = color;
		this.main = main;
		if (validacion != null) {
			this.validacion = new Thread(validacion);
		}

	}

	public void mover() {
		Automata chocado = main.chocaObjeto(this);
		if (chocado != null) {
			if (this instanceof Polo) {
				dx = -dx;
				dy = -dy;

			}

		} else if (main.chocaX(this)) {
			dx = -dx;
		}

		else if (main.chocaY(this)) {
			dy = -dy;
		}

		float nextX = (getPosX() + dx * speed);
		float nextY = (getPosY() + dy * speed);

		setPosX(nextX);
		setPosY(nextY);

	}

	public void init() {
		validacion.start();
	}

	public Thread getValidacion() {
		return validacion;
	}

	public void setValidacion(Thread validacion) {
		this.validacion = validacion;
	}

	public Modelo getPrincipal() {
		return main;
	}

	public void setPrincipal(Modelo main) {
		this.main = main;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
