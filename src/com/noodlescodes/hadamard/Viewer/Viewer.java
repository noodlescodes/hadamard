package com.noodlescodes.hadamard.Viewer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.noodlescodes.hadamard.entity.User;
import com.noodlescodes.hadamard.graphics.Drawer;
import com.noodlescodes.hadamard.graphics.Node;
import com.noodlescodes.hadamard.graphics.Screen;
import com.noodlescodes.hadamard.graphics.Sprite;
import com.noodlescodes.hadamard.input.Keyboard;
import com.noodlescodes.hadamard.input.Mouse;
import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;
import com.noodlescodes.hadamard.structures.Tree;

public class Viewer extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width = 420;
	public static int height = width / 16 * 9;
	public static int scale = 4;

	private Thread thread;
	private JFrame frame;
	private static Keyboard key;
	private static Mouse mouse;
	private Drawer drawer;
	private User user;
	private Tree<EquationSystemMatrix> tree;
	private static boolean running = false;

	private String oldHoverString = "";

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Viewer() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		mouse = new Mouse();

		tree = new Tree<EquationSystemMatrix>(new TNode<EquationSystemMatrix>(new EquationSystemMatrix("test5.dat")));

		int length = ((EquationSystemMatrix) tree.getRoot().getData()).getGramOrder();
		int h = length * 150;
		int w = 500;

		int howWide = 128;
		drawer = new Drawer(w, h, new Node(-8, 0, 0, tree.getRoot(), Sprite.TYPE.SQUARE, null, -howWide, howWide));

		user = new User(0, 0, key);

		init();
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public void init() {
		System.out.println("Initialising.");
		user.init(drawer);
		System.out.println("User initialised.");
	}

	public void update() {
		key.update();
		user.update();
		drawer.update(user.x, user.y, key.enter);
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		int xScroll = user.x - screen.width / 2;
		int yScroll = user.y - screen.height / 2;
		drawer.render(xScroll, yScroll, screen);
		user.render(screen);
		String hoverString = drawer.hover(user.x, user.y);
		// Boxes to the right, need a black one as well so you can't see the tree between the white boxes
		// ----
		Sprite s = new Sprite(75, screen.height, 0, 0, 0xFFFFFF);
		Sprite t = new Sprite(10, screen.height, 0, 0, 0xFFFFFF);
		Sprite b = new Sprite(1, screen.height, 0, 0, 0x000000);
		screen.renderSprite(xScroll + (screen.width - 75), yScroll, s, false);
		screen.renderSprite(xScroll + (screen.width - 86), yScroll, t, false);
		screen.renderSprite(xScroll + (screen.width - 76), yScroll, b, false);
		// ----
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		// drawing ruler here
		// ----
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, 0, 25));
		for(int currentLevel = 1; currentLevel - 1 <= drawer.highestLevelGenerated; currentLevel++) {
			g.drawString(Integer.toString(currentLevel - 1), scale * (screen.width - 85), (20 - scale * screen.getYOffset() + 4 * drawer.levelHeight * (currentLevel - 1)));
		}
		// g.drawString("10", scale * (screen.width - 85), 20);
		// ----

		if(key.debug) {
			g.setColor(Color.WHITE);
			g.setFont(new Font(null, 0, 25));
			g.drawString("X: " + user.x, 1, 20);
			g.drawString("Y: " + user.y, 1, 40);
			g.drawString("MouseX: " + Mouse.getX(), 1, 85);
			g.drawString("MouseY: " + Mouse.getY(), 1, 105);
		}
		if(hoverString == null) {
			hoverString = oldHoverString;
		}
		else {
			oldHoverString = hoverString;
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Monospaced", 0, 20));
		int ypos = 0;
		for(String line : hoverString.split("\n")) {
			g.drawString(line, scale * (screen.width - 74), ypos += g.getFontMetrics().getHeight());
		}
		bs.show();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
				updates++;
				render();
				frames++;
			}

			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle("Viewer\t|\tfps: " + frames + "\tups: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Setting the window properties.");
		Viewer viewer = new Viewer();
		System.out.println("Started viewer.");
		System.out.println("Setting frame to not be resizable.");
		viewer.frame.setResizable(false);
		System.out.println("Setting title.");
		viewer.frame.setTitle("Viewer");
		viewer.frame.add(viewer);
		System.out.println("Packing everything.");
		viewer.frame.pack();
		viewer.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewer.frame.setLocationRelativeTo(null);
		viewer.frame.setVisible(true);

		System.out.println("Starting...");
		viewer.start();
	}
}
