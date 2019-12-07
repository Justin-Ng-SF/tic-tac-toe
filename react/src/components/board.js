import React from "react";
import Square from "./square";
import calculateWinner from "./calculateWinner";
import client from "./clientServer"


 class board extends React.Component{

  
  



  constructor(props) {
    super(props);

    
    

    this.state = {
      squares: client.game,
      xIsNext: true,
     
    };

  



  }

  

  

  
  

  

  handleClick(i) {
   
    const squares = this.state.squares.slice();

    
   

    if(client.myTurn === true){
      
    
    if (calculateWinner(squares) || squares[i]) {
      return;
    }
    //squares[i] = this.state.xIsNext ? "X" : "O";

    squares[i] = client.XO; 


    client.thingToSend.gameBoard = squares;
    client.thingToSend.RoomID    = client.roomId;

    console.log(client.thingToSend.RoomID)

    client.x.current.send(JSON.stringify(client.thingToSend));
    
   
    
    
    
    
    this.setState({
      squares: squares,
      xIsNext: !this.state.xIsNext
      
    });

    client.myTurn = false;
    client.nextPlayer = "Opponent"
  }
  }
  
   
 

  renderSquare(i) {
    return (
      <Square
        value={this.state.squares[i]}
        
        onClick={() => this.handleClick(i)}
      />
      
    );
    
  }

  render() {
    const winner = calculateWinner(this.state.squares);
    let status;
    if (winner) {
      status = "Winner: " + winner;
    } else {
      status = "Next player: " + (client.nextPlayer);
    }

   

    return (
      <div class ="center2">
        <div className="status">{status}</div>
        <div className="board-row">
          {this.renderSquare(0)}
          {this.renderSquare(1)}
          {this.renderSquare(2)}
        </div>
        <div className="board-row">
          {this.renderSquare(3)}
          {this.renderSquare(4)}
          {this.renderSquare(5)}
        </div>
        <div className="board-row">
          {this.renderSquare(6)}
          {this.renderSquare(7)}
          {this.renderSquare(8)}
        </div>
      </div>
    );
  }
}




export default board;

