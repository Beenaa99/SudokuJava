import java.io.*;
import java.util.*;

class Main{
	
	int [][] arr=new int [9][9];
	int [][] check_array = new int [81][9];
	
	public static void main(String args[]){
	Main obj1 = new Main();
	
	obj1.input();
	obj1.checkArray();	
	obj1.display();

	}
	
	boolean checkRow(int r , int num){
		for(int k=0;k<9;k++){
			if(arr[r][k]==num) 
				return false;
		}
	return true;	
	}	

	boolean checkColumn(int c , int num){
		for(int k = 0; k<9;k++){
			if(arr[k][c]==num)
				return false;
					
		}
	return true;
	}
	
	boolean checkBox(int i, int j, int num) 
	{	
		int r = i-i % 3;
		int c = j-j % 3; 
		for (int p =r; p<r+3; p++)
		{
			for (int q =c; q<c+3; q++)
			{
				if (arr[p][q] == num) 
					return false;
			}
		} 
		return true;
	}
	
	void input()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the elements for the Sudoku to solve.\n Enter 0 for spaces to be filled");
		System.out.println("Enter the data of every row:");
		System.out.println();
		System.out.println("123456789");
		System.out.println();
		for (int m = 0; m<9; m++)
		{
		    System.out.println("Enter the input for row "+(m+1));
		    String str=sc.nextLine();
			for (int n = 0; n<9; n++)
			{
			    char ch = str.charAt(n);
			    arr[m][n]=Character.getNumericValue(ch);
			    /*
			    System.out.println("Enter element at  "+(m+1)+","+(n+1));
				arr[m][n]=sc.nextInt();
	    		*/
			}
		}
		int changes=0;
		display();
		do{
		    System.out.println("Do you wish to make any changes. Enter 1 for yes, 0 for no");
		    changes=sc.nextInt();
		    if(changes==1)
		    {
		        System.out.println("Enter row no:");
		        int i=sc.nextInt();
		        System.out.println("Enter column number:");
		        int j=sc.nextInt();
		        System.out.println("Enter data:");
		        arr[i-1][j-1] =sc.nextInt();
		        display();
		    }
		}while(changes==1);
	}
	
	void display()
	{
		System.out.println(" -------------------------");
		for (int m = 0; m<9; m++)
		{
			for (int n = 0; n<9; n++)
			{
				if(n%3==0)
					System.out.print(" | "+arr[m][n]);
				else
					System.out.print(" "+arr[m][n]);
			}
			System.out.println(" |");
			if((m+1)%3==0)				
				System.out.println(" -------------------------");
		}
	}
	
	void update()
	{
	    int pos=0;
			for(int i = 0; i<9 ; i++)
			{
				for (int j=0 ; j<9;j++)
				{
					
					if(arr[i][j]==0)
					{
						for(int k=0;k<9;k++ )
						{
							if(checkRow(i,k+1)==true&&checkColumn(j,k+1)==true&&checkBox(i,j,k+1)==true)
							{
								
								check_array[pos][k]=1;
							}
							else
							{
								check_array[pos][k]=0;
							}
						}
					}
					pos++;
				}
			}
			
	}
	
	void checkArray()
	{
		    update();
		    System.out.println("     1  2  3  4  5  6  7  8  9");
		    for(int p=0;p<81;p++)
		    {
		        System.out.print(((p/9)+1)+","+((p%9)+1)+":");
		        for(int q =0;q<9;q++)
		        System.out.print("  "+check_array[p][q]);
		        System.out.println();
		    }
		boolean p4;	
		boolean p3;	
		boolean p2;
		boolean p1;
		int counter=0;
		do
		{
		    counter++;
	         p1=onePossibleAtPos();
			 p2=onceInCols();
			 p3=onceInRows();
			 p4=onceInBox();
			 System.out.println(counter);
		}while(p1||p2||p3||p4);

	}
	
	boolean onePossibleAtPos()
	{
	    boolean print = false;
		int j=0;
		int store_i=0;
		int store_j=0;
		for(int i=0;i<81;i++)
		{
			int possiblity=0;
			for(j=0;j<9;j++)
			{
				if(check_array[i][j]==1)
				{
				    if(arr[i/9][i%9]==0)
				    {
				        store_j=j;
				        store_i=i;
					    possiblity++;
				    }
				}
				if(possiblity==2)
				{
					break;
			
				}    
			}
			if(possiblity==1)
			{
			    possiblity=0;
				arr[store_i/9][store_i%9]=(store_j+1);
				print =true;
				update();
				System.out.println("Printing "+(store_j+1)+" at "+((store_i/9)+1)+","+((store_i%9)+1)+" from onePossibleAtPos");
			}
		}
		return print;
	}

	boolean onceInCols()
	{
		int store_i=0;int store_j=0;
		boolean print=false;
		for(int k=0;k<9;k++)
		{
			for(int j=0;j<9;j++)
			{
				int number_in_col=0;
				int i=k;
				while(i<81)				
				{
					if(check_array[i][j]==1)
					{
				        if(arr[i/9][i%9]==0)
				        {
						    store_i=i;
						    store_j=j;
						    number_in_col++;
					     }
					}
					i=i+9;
				}
				if(number_in_col==1)
				{
					number_in_col=0;
					arr[store_i/9][store_i%9]=(store_j+1);
					print =true;
					update();
					System.out.println("Printing "+(store_j+1)+" at "+((store_i/9)+1)+","+((store_i%9)+1)+" from onceInCols");
				}
				
			}
		}
		return print;
	}

	boolean onceInRows()
	{
		boolean print = false;
		int store_i=0;int store_j=0;
		int i=0;int number_in_row=0;
		for(int j=0;j<9;j++)
		{
			for(i=0;i<81;i++)
			{				
				if(check_array[i][j]==1)
				{
					
				    if(arr[i/9][i%9]==0)
				    {
				        number_in_row++;
					    store_i=i;
					    store_j=j;
				    }
				}
				
				if((i+1)%9==0)
				{
					if(number_in_row==1)
					{
						arr[store_i/9][store_i%9]=store_j+1;
						print = true;
						update();
						System.out.println("Printing "+(store_j+1)+" at "+((store_i/9)+1)+","+((store_i%9)+1)+" from onceInRows");
					}
					number_in_row=0;
				}

			}
		}
		return print;
	}

	
	boolean onceInBox()
	{
		boolean print=false;
		int row_counter =0, column_counter=0;
		int store_i=0, store_j=0;
		int i=0,k=0;
		while(k<9)
		{
		    for(int j=0;j<9;j++)
		    {
	    		int num_in_box=0;
	    		i=k;
    			while(i<81)
			    {
				    if(check_array[i][j]==1)
			    	{
		    			
				        if(arr[i/9][i%9]==0)
				        {   
			    	        num_in_box++;
	        				store_i=i;
	        				store_j=j;
    				    }    
				    }
				    column_counter++;
				    i++;
			    	if(column_counter == 3)
		    		{
	    				column_counter =0;
    					row_counter++;
					    i=i+6;
				    }
			    	if(row_counter==3)
		    	    {
	    		        if(num_in_box==1)
    			        {
                            num_in_box=0;
                            row_counter=0;
		    		        arr[store_i/9][store_i%9]=store_j+1;
		    		        print = true;
		    		        update();
        		    		System.out.println("Printing "+(store_j+1)+" at "+((store_i/9)+1)+","+((store_i%9)+1)+" from onceInBox");
    	        		}
    	        		num_in_box=0;
		    	    }
	    		}
    		}
		    k=k+3;
		}
		return print;
	}
}