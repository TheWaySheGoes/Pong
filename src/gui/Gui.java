package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Gui extends JPanel implements KeyListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8002985731068363787L;
	int lX = 0;
	int lY = 0;
	int lDX = 10;
	int lDY = 10;
	int lHeight = 100;
	int lWidth = 20;
	int lTop = lX;
	int lBottom = lX + lHeight;
	int lFront = lX + lWidth;
	int lCenter;
	boolean moveLeftPadle=true;
	String lMoveDirection=null;
	int Rx = 550;
	int Ry = 0;
	int bX = 50;
	int bY = 50;
	int bDX = 1;
	int bDY = 1;
	int bWidth = 30;
	int bHeight = 30;
	int bCenter;

	boolean bThread = true;
	int refreshRate = 5;

	/**
	 * Frame Image <- frame.createImage.. Graphics <- Image.getGraphics..
	 */
	public Gui() {
		this.setLayout(null);
		this.setBounds(5, 5, 590, 580);
		this.addKeyListener(this);
		this.addMouseListener(this);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGame();
			}
		});
	}

	private void moveBall() {
		bX += bDX;
		bY += bDY;
		bCenter  = bY + (bHeight / 2);
		lCenter = lY + (lHeight / 2);
	}

	private void bounceBall() {
		// top bounce
		if (bY <= 0) {
			bDY = bDY * (-1);
			bY+=5;
		}
		// bottom bounce
		if (bY >= Gui.this.getHeight() - bHeight) {
			bDY = bDY * (-1);
		}
		// right side bounce
		if (bX >= this.getWidth() - this.bWidth) {
			bDX = bDX * (-1);
		}
		// left pad bounce
		if (this.bY >= this.lY && this.bY <= this.lY+this.lHeight && this.bX <= this.lFront) {
			bDX *= (-1);
			bX+=5;
			bY+= Math.abs(this.lCenter - this.bCenter) / (this.lWidth / 4);
			if(this.bCenter<this.lCenter) {
				bDY*=(-1);
				
			}
			System.out.println("Ball c "+bCenter+" lCenter "+lCenter);
		}

	}


	public void startGame() {
		Thread ballT = new Thread(new Runnable() {
			public void run() {
				while (bThread) {
					
					moveBall();
					bounceBall();

					Gui.this.repaint();
					try {
						Thread.sleep(refreshRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ballT.start();
	}

	
	
	public void paint(Graphics g) {
		super.paint(g);
		super.setBackground(Color.BLACK);

		g.setColor(Color.WHITE);
		g.fill3DRect(this.lX, this.lY, this.lWidth, this.lHeight, false);
		g.fill3DRect(this.Rx, this.Ry, 20, 100, false);
		g.fillOval(bX, bY, this.bWidth, this.bHeight);

	}

	public void update(Graphics g) {

		paint(g);
	}

	public static void main(String[] args) {
		Gui g = new Gui();

		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.setLayout(null);
		frame.setBounds(0, 0, 610, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Pong by Lukas Kurasinski");
		frame.add(g);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(g);
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// down 40 upp 38
		if (ke.getKeyCode() == 38) {
			this.lMoveDirection="UP";
			if (lY > 0) {
				lY-=lDY;
			}
			
		}
		if (ke.getKeyCode() == 40) {
			this.lMoveDirection="DOWN";
			if (lY < Gui.this.getHeight() - lHeight) {
				lY+=lDY;
			}
		}
		// reset on enter press
		if (ke.getKeyCode() == 10) {
			this.lMoveDirection=null;
			lX = 0;
			lY = 0;
			lHeight = 100;
			lWidth = 20;
			lTop = lX;
			lBottom = lX + lHeight;
			lFront = lX + lWidth;
			lCenter = lY + (lHeight / 2);
			Rx = 550;
			Ry = 0;
			bX = 50;
			bY = 50;
			bDX = 3;
			bDY = 3;
			bWidth = 30;
			bHeight = 30;
			bCenter = bY + (bHeight / 2);
		}

		System.out.println(ke.getKeyChar());
		System.out.println(ke.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent ke) {
		this.lMoveDirection=null;
		System.out.println(ke.getKeyCode());
		System.out.println("dsadsadsa");
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		System.out.println(ke.getKeyCode());
		System.out.println("dsadsadsa");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("dsadsadsa");

	}

	@Override
	public void mouseEntered(MouseEvent m) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent m) {
		System.out.println(m.getY());

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
