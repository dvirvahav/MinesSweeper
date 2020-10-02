package application;

import java.util.Random;

public class Engine {
	int Mines, Flags, width, height, win;
	Random rand = new Random();
	Place[][] mines;

	public Engine(int width, int height, int Mines) {
		this.Mines = Mines;
		this.Flags = Mines;
		this.width = width;
		this.height = height;
		this.win = 0;
		mines = new Place[width][height];

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				mines[i][j] = new Place(i, j);

		placeMines();

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				updateMinesAround(i, j);
	}

	// FUNCTIONS
	public int getWin() {
		return win;
	}

	public void reset() {

		Flags = Mines;
		mines = new Place[width][height];

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				mines[i][j] = new Place(i, j);

		placeMines();

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				updateMinesAround(i, j);
	}

	public void placeMines() {
		int temp = Mines, x, y;
		while (temp != 0) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			if (mines[x][y].isMine() != 1) {
				mines[x][y].setMine();
				temp--;
			}

		}

	}

	public void updateMinesAround(int x, int y) {

		int count = 0;
		if (mines[x][y].isMine == 1)
			return;
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++)
				if (!(x + j < 0) && !(y + i < 0) && !(x + j > width - 1) && !(y + i > height - 1)
						&& !((x + j) == x && (y + i) == y))
					if (mines[x + j][y + i].isMine() == 1)
						count++;

		mines[x][y].setNumOfMines(count);

	}

	public void openAll() {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				mines[i][j].justOpen();
	}
//////////////

	///////////////////
	public class Place {
		int x, y, numOfMines = 0, isMine = 0; // isMine - 1 for true and 0 for false.
		boolean isClosed = true, hasFlag = false;

		public Place(int x, int y) {
			this.x = x;
			this.y = y;

		}

		public void setMine() {
			isMine = 1;

		}

		public boolean setFlag() {
			if (Flags == 0)
				return false;
			else if(!isClosed||hasFlag){
				return false;
			}
			
			else 	{	
				hasFlag=true;
				Flags--;
				return true;
			}			
		}

		public void justOpen() {
			isClosed = false;
		}

		public void open() {
			if (!isClosed)
				return;
			else if (hasFlag) {
				hasFlag = false;
				Flags++;
			} else if (isMine == 1) {
				openAll();
			} else if (numOfMines > 0) {
				win++;
				isClosed = false;
			} else {
				isClosed = false;
				win++;
				for (int i = -1; i < 2; i++)
					for (int j = -1; j < 2; j++)
						if (!(x + j < 0) && !(y + i < 0) && !(x + j > width - 1) && !(y + i > height - 1)
								&& !((x + j) == x && (y + i) == y))
							mines[x + j][y + i].open();

			}

		}

		public void setNumOfMines(int numOfMines) {
			this.numOfMines = numOfMines;
		}

		public int getFlags() {
			return Flags;
		}

		public int isMine() {
			return isMine;
		}

		public String toString() {
			if (isClosed && hasFlag)
				return "f"; // closed and flag
			if (!isClosed && hasFlag)
				return "s"; // closed and flag
			else if (isClosed)
				return "c"; // is closed
			else if (isMine == 1)
				return "m"; // is mine
			else if (numOfMines > 0)
				return String.format("%d", numOfMines); // return num of mines
			else
				return "e"; // for empty

		}
	}
/////////////////////

	

}
