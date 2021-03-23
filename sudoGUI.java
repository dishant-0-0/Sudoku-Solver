import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//  GUI

public class sudoGUI implements ActionListener{

    static int[][] matrix=new int[9][9];

    JFrame f = new JFrame();
    JButton solve = new JButton("Solve");
    JButton reset = new JButton("Reset");

    int fields_x,fields_y;
    int x = 35;
    int y= 35;
    JTextField[][] fields = new JTextField[9][9]; {
        for (fields_y=0; fields_y < 9; fields_y++) {
            x=35;
            for (fields_x =0; fields_x < 9; fields_x++) {

                fields[fields_x][fields_y] = new JTextField();
                fields[fields_x][fields_y].setBounds(x, y, 32, 32);
                f.getContentPane().add(fields[fields_x][fields_y]);
                fields[fields_x][fields_y].setHorizontalAlignment(JTextField.CENTER);
                x = x+35;
            }
            y = y+35;
        }
    }

    sudoGUI(){

        solve.setBounds(110,370,70, 30);
        reset.setBounds(210,370,70, 30);
        f.add(solve);
        f.add(reset);

        solve.addActionListener(this);
        reset.addActionListener(this);
        f.setSize(400,480);
        f.setTitle("Sudoku Solver!");
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
		for (fields_y =0; fields_y < 9; fields_y++) {
            for (fields_x =0; fields_x < 9; fields_x++) {
				fields[fields_x][fields_y].setText("0");
			}
		}
	}
	

    @Override
    public void actionPerformed(ActionEvent e) {

        String s;
        for (fields_y =0; fields_y < 9; fields_y++) {
            for (fields_x =0; fields_x < 9; fields_x++) {
                s = fields[fields_x][fields_y].getText();
                matrix[fields_x][fields_y]=Integer.parseInt(s);
            }
        }

        if(e.getSource()== solve){
            if(solve(matrix, 9)){
                for (fields_y =0; fields_y < 9; fields_y++) {
                    for (fields_x =0; fields_x < 9; fields_x++) {
                        fields[fields_x][fields_y].setText(""+matrix[fields_x][fields_y]);
                    }
                }
            }
        }

        if(e.getSource()==reset){
            if(reset()){
                for (fields_y =0; fields_y < 9; fields_y++) {
                    for (fields_x =0; fields_x < 9; fields_x++) {
                        fields[fields_x][fields_y].setText("0");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new sudoGUI();
    }


//  Logic

    public boolean solve(int [][]board, int n) {

        int i,j=0,value;
        int rowInd = -1 , colInd = -1;
        for(i=0; i<n; i++){
            for(j=0; j<n; j++){
                if( board[i][j]==0 ){
                    rowInd=i;
                    colInd=j;
                    break;
                }
            }
            if(rowInd != -1){
                break;
            }
        }
        if( i==n && j==n ){
            return true;
        }
        else{
            for( value=1; value<10; value++ ){
                if(trueVal(board,value,rowInd,colInd)){
                    board[rowInd][colInd] = value;
                    if(!solve(board, n)){
                        board[rowInd][colInd]=0;
                    }
                    else{
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean reset(){
        int i,j;
        for(i=0; i<9; i++){
            for(j=0; j<9; j++){
                matrix[i][j]=0;
            }
        }
        return true;
    }


    public boolean trueVal(int [][]board, int value, int rowInd, int colInd) {

        for(int i=0; i<9; i++){
            if(board[rowInd][i] == value){
                return false;
            }
        }

        for(int j=0; j<9; j++){
            if(board[j][colInd] == value){
                return false;
            }
        }

        int baseRow = rowInd - ( rowInd % 3 );
        int baseCol = colInd - ( colInd % 3 );
        for(int i=baseRow; i<baseRow+3; i++){
            for(int j=baseCol; j<baseCol+3; j++){
                if(board[i][j] == value){
                    return false;
                }
            }
        }
        return true;
    }
}
