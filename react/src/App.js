import React from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';
import Parent from './Parent';
import board from './components/board';
import client from "./components/clientServer"


const wsSession = new WebSocket(`ws://localhost:1234/ws`); // fix for multiple connections

function App() {

  const [text, setText] = React.useState(''); // creates state variable, retuns tuple
  const [responseText, setResponseText] = React.useState('');
  const [responseText2, setResponseText2] = React.useState();
  const [responseText3, setResponseText3] = React.useState('');
  const [responseText4, setResponseText4] = React.useState('');

  const [leaderboard, setLeaderboard] = React.useState('');

  var test = ["user1", "user2", "user3"];

  const toString = () =>{
    var size = test.length;
    for(let i=0; i< size; i++){
      test[i] = `\n user: ${test[i]}`
    }
    setLeaderboard(test);
  }

  React.useEffect(() => {
    toString();
   }, [])

  var game = new Parent();
  
  
  var gameRoomID = null;

  var sendData = {
    type: "Register",
    userName: null
  }

  var updateData = {
    type: "gameRoomUpdate",

  }

  client.thingToSend  = {
    type: "gameRoomUpdate",
    RoomID: 0,
    gameBoard: null



};

  var quit ={
    type: "quitGame",
    gameRoomId: null
  }
  
 
  const ws = React.useRef(wsSession);

  const handleClick = () => {
    var x = document.getElementById("test");
    x.style.display = "none";

    setResponseText3("\nMatchmaking");

    
    sendData.userName = text;
    ws.current.send(JSON.stringify(sendData));

    
   
    
  };

  ws.current.onopen = (message) => {
    console.log('Connection open!')
    client.x = ws;
  //  client.game = Array(9).fill("X")
   // setResponseText2(game.render());
    
   
   
  //  gamebox.state.ws = "ok"
   // console.log(gamebox.state.ws)
  //  console.log(gamebox.state.squares[8])
    


    
  
  };

  ws.current.onclose = () =>{
    console.log('Connection close!')
    
    setResponseText(" Connection closed")
    
  

  };

  ws.current.onmessage = (message) => {
    console.log(message.data)

    switch(JSON.parse(message.data).type){
      case "Leaderboard":


      case "PlayerCountUpdate":
          setResponseText(JSON.parse(message.data).playerCount);
        
          break;

      case "Matched":
        console.log(JSON.parse(message.data).roomId)

        client.thingToSend.RoomID = JSON.parse(message.data).roomId;

        client.roomId = JSON.parse(message.data).roomId;

        console.log(client.thingToSend.RoomID)




        client.game = JSON.parse(message.data).board
      
      
        setResponseText2(game.render());
        
        setResponseText3("");
        break;

      case "Turn":
        client.myTurn = JSON.parse(message.data).turn;
        client.XO     = JSON.parse(message.data).XO;
        client.game   = JSON.parse(message.data).board
        
        
      
        //client.game = JSON.parse(message.data).board;
        setResponseText2();
        setResponseText2(game.render());
        break;

    }
   
     //console.log(JSON.parse(message.data).PlayerCount);
     
   };




  return (
    <div className="App">
      <header className="App-header">
        <h1>Tik Tac Toe</h1>
       
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
          <font color="White">Leaderboard</font>
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
