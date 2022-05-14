package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

final public class GameField extends JPanel implements ActionListener {
	private final static int SIZE = 320;
	private final static int DOT_SIZE = 16;
	private final static int ALL_DOTS = 400;
	private static Image dot;
	private static Image apple;
	private JButton buttonRestart = null;
	private static int getArrPic = 0;
	private final static String[] arrayPicEat = { "img/apple.png", "img/berry.png", "img/cherry.png",
			"img/coconut.png", "img/orange.png", "img/peach.png", "img/tomato.png",
			"img/watermelon.png" };

	private HashMap<Integer, Integer> mapNumArrPicAndScore = new HashMap<Integer, Integer>(); // number array
																								// picture/score
	private JButton buttonExitGame = null;

	private Date startTimeData = null;
	private Date endTimeData = null;

	private static int appleX;
	private static int appleY;
	private static int[] x = new int[ALL_DOTS];
	private static int[] y = new int[ALL_DOTS];
	
	public static int getVarScore() {
		return varScore;
	}

	public static int getHours() {
		return hours;
	}

	public static int getMinutes() {
		return minutes;
	}

	public static int getSeconds() {
		return seconds;
	}

	private static int dots;

	private static Timer timer;
	private static boolean inGame = true;

	private static String varDirection = "right";

	private static int varScore = 0;

	private static int hours = 0;
	private static int minutes = 0;
	private static int seconds = 0;

	public GameField() {
		startGame();
	}

	private void startGame() {
		setBackground(Color.BLACK);
		addInMapPic();
		loadImages();
		initGame();
	}

	private void drawBackground(Graphics gc) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((i + j) % 2 == 0) {
					gc.setXORMode(Color.yellow);
				} else {
					gc.setXORMode(Color.orange);
				}
				gc.fillRect(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
			}
		}
	}

	private void addInMapPic() {
		Integer integer = new Integer(10);
		for (int i = 0; i < arrayPicEat.length; i++) {
			mapNumArrPicAndScore.put(i, integer += 10);
		}
//		mapNumArrPicAndScore.put(0, 30);
	}

	private static void loadImages() {
		getArrPic = (int) (Math.random() * arrayPicEat.length);
		ImageIcon imageIconApple = new ImageIcon(arrayPicEat[getArrPic]);
		apple = imageIconApple.getImage();

		ImageIcon imageIconDot = new ImageIcon("img/body.png");
		dot = imageIconDot.getImage();
	}

	private void createSnake() {
		startTimeData = timeGame();
		dots = 3;
		for (int i = 0; i < dots; i++) {
			GameField.x[i] = 48 - i * GameField.DOT_SIZE;
			GameField.y[i] = 48;
		}
	}

	private void initGame() {
		createSnake();
		GameField.timer = new Timer(250, this);
		System.out.println(timer.getDelay());
		GameField.timer.start();
		createApple();
	}

	private static void createApple() {
		GameField.appleX = new Random().nextInt(20) * GameField.DOT_SIZE;
		GameField.appleY = new Random().nextInt(20) * GameField.DOT_SIZE;
	}

	private static void move() {

		for (int i = dots; i > 0; i--) {
			GameField.x[i] = GameField.x[i - 1];
			GameField.y[i] = GameField.y[i - 1];
		}

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {
			switch (ke.getKeyCode()) {

			case KeyEvent.VK_LEFT: {
				System.out.println("left - " + ke.paramString());
				varDirection = "left";
				break;
			}
			case KeyEvent.VK_RIGHT: {
				System.out.println("right - " + ke.paramString());
				varDirection = "right";
				break;
			}
			case KeyEvent.VK_UP: {
				System.out.println("up - " + ke.paramString());
				varDirection = "up";
				break;
			}
			case KeyEvent.VK_DOWN: {
				System.out.println("down - " + ke.paramString());
				varDirection = "down";
				break;
			}
			}
			return true;
		});
		if (varDirection.equals("left"))
			GameField.x[0] -= GameField.DOT_SIZE;
		if (varDirection.equals("right"))
			GameField.x[0] += GameField.DOT_SIZE;
		if (varDirection.equals("up"))
			GameField.y[0] -= GameField.DOT_SIZE;
		if (varDirection.equals("down"))
			GameField.y[0] += GameField.DOT_SIZE;
	}

	private void checkTime() {
		endTimeData = timeGame();
		long result = endTimeData.getTime() - startTimeData.getTime();
		hours = (int) result / (1000 * 60 * 60);
		minutes = (int) result / (1000 * 60);
		seconds = (int) result / (1000);
		if (seconds > 60)
			seconds -= 60;
		if (minutes > 60)
			minutes -= 60;
		if (hours > 60 && minutes > 60 && seconds > 60) {
			hours -= 60;
			seconds -= 60;
			minutes -= 60;
		}
		System.out.println("h:" + hours + "m:" + minutes + "s:" + seconds);
	}

	private void checkGameOver() {
		for (int i = dots; i > 0; i--) {
			if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
				inGame = false;
			}
			if (x[0] > SIZE)
				inGame = false;
			if (x[0] < 0)
				inGame = false;
			if (y[0] > SIZE)
				inGame = false;
			if (y[0] < 0)
				inGame = false;

		}
		if (!inGame) {
			restart();
			exitGame();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (inGame) {
			drawBackground(g);
			g.drawImage(apple, appleX, appleY, this);
			for (int i = 0; i < dots; i++) {
				g.drawImage(dot, x[i], y[i], this);
			}
		} else {
			g.setColor(Color.red);
			g.setFont(new Font("Digital-7", 16, 40));
			g.drawString("Game Over!", 70, 155);
		}
	}

	private void restart() {
		buttonRestart = new JButton("Restart");
		buttonRestart.setBackground(SystemColor.yellow);
		buttonRestart.setBounds(90, 180, 90, 30);
		buttonRestart.addActionListener(this);
		add(buttonRestart);
	}

	private void exitGame() {
		buttonExitGame = new JButton("Exit");
		buttonExitGame.setBackground(SystemColor.red);
		buttonExitGame.setBounds(200, 180, 70, 30);
		buttonExitGame.addActionListener(this);
		add(buttonExitGame);
	}

	private Date timeGame() {
		Date varTimeGame = new Date();
		SimpleDateFormat simpleDateFormatStart = new SimpleDateFormat("hh:mm:ss");
		System.out.println(simpleDateFormatStart.format(varTimeGame));
		return varTimeGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if (GameField.inGame) {
			if (GameField.x[0] == GameField.appleX && GameField.y[0] == GameField.appleY) {
				System.out.println(mapNumArrPicAndScore.get(getArrPic));
				varScore += mapNumArrPicAndScore.get(getArrPic);
				dots++;
				loadImages();
				createApple();
			}
			checkGameOver();
			move();
			checkTime();
		} else {
			if (e.getSource() == buttonRestart) {
				varDirection = "right";
				varScore = 0;
				inGame = true;
				loadImages();
				createSnake();
				timer.restart();
				createApple();
				buttonRestart.setVisible(false);
				buttonExitGame.setVisible(false);
			}
			if (e.getSource() == buttonExitGame) {
				System.exit(0);
			}
		}
	}
}
