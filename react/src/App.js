import React from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';
import Parent from './Parent';
import board from './components/board';
import client from "./components/clientServer"


const wsSession = new WebSocket(`ws://localhost:1234/ws`); // fix for multiple connections

function App() {

  const [text, setText] = React.useState(''); // Number of Player
  const [responseText, setResponseText] = React.useState(''); //Connection status
  const [responseText2, setResponseText2] = React.useState(); //Render Game Board
  const [responseText3, setResponseText3] = React.useState('');//Matchmaking status
  const [responseText4, setResponseText4] = React.useState('');

  const [leaderboard, setLeaderboard] = React.useState('');


  const set = (list, size) =>{
    //var size = test.length;
    var entry = new Array(size);
    for(let i=0; i< size; i++){
      var user = list[i]["username"];
      var wins = list[i]["wins"];
      entry[i] = `\n ${i+1}. ${user} has ${wins} wins!`

    }
    setLeaderboard(entry);
  }

  React.useEffect(() => {
    set();
   }, [])

  var game = new Parent();
  
  
  var gameRoomID = null;

  var sendData = {
    type: "Register",
    userName: null
  }

  

  client.thingToSend  = {
    type: "gameRoomUpdate",
    RoomID: 0,
    gameBoard: null



};

  client.winner ={
    type: "winner",
    RoomID: 0,
  }
  
 
  const ws = React.useRef(wsSession);

  const handleClick = () => {
    var x = document.getElementById("test");
    x.style.display = "none";

    setResponseText3(" "+"Matchmaking");

    
    sendData.userName = text;
    ws.current.send(JSON.stringify(sendData));

    
   
    
  };

  ws.current.onopen = () => {
    console.log('Connection open!')
    client.x = ws;
 
  };

  ws.current.onclose = () =>{
    console.log('Connection close!')
    
    setResponseText(" "+"Connection closed")

    setResponseText2();
    setResponseText3(" ");
    
  

  };

  ws.current.onmessage = (message) => {
    console.log(message.data)

    switch(JSON.parse(message.data).type){
      case "Leaderboard":
          set(JSON.parse(message.data).leaderboard, JSON.parse(message.data).leaderboard.length);
      
      case "PlayerCountUpdate":
          setResponseText(JSON.parse(message.data).playerCount);
        
          break;

      case "Matched":
        client.nextPlayer = "Opponent"
        console.log(JSON.parse(message.data).roomId)

        client.thingToSend.RoomID = JSON.parse(message.data).roomId;

        client.roomId = JSON.parse(message.data).roomId;

        client.winner.RoomID = JSON.parse(message.data).roomId;



        console.log(client.thingToSend.RoomID)
        console.log("Roomid" + client.winner.RoomID)




        client.game = JSON.parse(message.data).board
      
      
        setResponseText2(game.render());
        
        setResponseText3("");
        break;

      case "Turn":
        client.myTurn = JSON.parse(message.data).turn;
        client.XO     = JSON.parse(message.data).XO;
        client.game   = JSON.parse(message.data).board;
        client.nextPlayer = JSON.parse(message.data).nextPlayer;
        
        
      
       
        setResponseText2();
        setResponseText2(game.render());
        break;

      case "gameOver":
        setResponseText2();
        setResponseText3(" " + "Opponent left, Matchmaking");


    }
   
   
     
   };




  return (
    <div className="App">
      <header className="App-header">
        <h1>Tic Tac Toe</h1>
       
        <img src="thumbnail.png" className="App-logo" alt="logo" />
        <p>
          
         Online Player: {responseText}
         {responseText3}
         {responseText2}
        
       
        </p> 
       
        <div class="LeaderBoard">
          <img src="top3.png" className="LeaderBoard">
          </img>
        </div>



        <div class="Top10">
          <font color="White" size="1vh">Top 5 Leaderboard</font>
          <pre color="White">{leaderboard}</pre>

        </div>


  <body><p>Welcome {text}</p></body>


        <div id="test">
        <input value={text} onChange={e => setText(e.target.value)} />
        
        <button onClick={handleClick}>Submit</button>
        </div>
      
      </header>
    </div>
  );
}

export default App;