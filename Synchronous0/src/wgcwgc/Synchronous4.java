package wgcwgc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Synchronous4
{

	public static void main(String[] args)
	{
		new deal();
		System.out.println(12345);
	}

}

class deal implements KeyListener
{
	public deal()
	{
		System.out.println("asdfasdf");
		
		
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() == 'q')
		{
			System.out.println("asdfasdf");
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}