package wgcwgcwgc;

import java.util.Scanner;

public class Test2
{	
	public static void main(String[] args)
	{
		test22 t22 = new test22();
//		new Thread(t22).start();
//		lambda		//		new Thread(() -> System.out.println("Hello world !")).start();
		t22.start();
		Scanner cin = new Scanner(System.in);
		String str = cin.nextLine();
		if(str.toLowerCase().equals("q"))
		{
			t22.setIsrund(false);
			System.out.println("Q被点击了！！！");
			cin.close();
		}
	}
}

class test22 extends Thread
{
	public boolean isrund = true;
	
	public boolean isIsrund()
	{
		return isrund;
	}
	
	public void setIsrund(boolean isrund)
	{
		this.isrund = isrund;
	}
	
	public void run()
	{
		while(isrund)
		{
			System.out.println("运行中，，，"+ isrund);
		}
	}
}
