import React from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';
import Parent from './Parent';


const wsSession = new WebSocket(`ws://localhost:1234/ws`); // fix for multiple connections

function App() {
  const [text, setText] = React.useState(''); // creates state variable, retuns tuple
  const [responseText, setResponseText] = React.useState('');
  const [responseText2, setResponseText2] = React.useState();
  const game = new Parent();

  var sendData = {
    type: "Register",
    userName: null
  };

  var updateData = {
    type: "gameRoomUpdate",

  }
  
 
  const ws = React.useRef(wsSession);

  const handleClick = () => {
    var x = document.getElementById("test");
    x.style.display = "none";

    setResponseText2(game.render());

    
    sendData.userName = text;
    ws.current.send(JSON.stringify(sendData));

   /* ws.current.send(JSON.stringify({
      type: 'handshake',
      status: 'success'
    }));*/

    
   
    
  };

 

  ws.current.onopen = () => {
    console.log('Connection open!')

   
   
    
   // ws.current.send(responseText + 1);
  };

  ws.current.onclose = () =>{
    console.log('Connection close!')
    setResponseText(" Connection closed")
    
   // ws.current.send(responseText - 1);

  }

  

  ws.current.onmessage = (message) => {
    console.log(message.data)

    switch(JSON.parse(message.data).type){
      case "PlayerCountUpdate":
          setResponseText(JSON.parse(message.data).playerCount);
          break;

    }
   
  
     //console.log(JSON.parse(message.data).PlayerCount);
    
     
   };

  
 
  

  

  return (
    <div className="App">
      <header className="App-header">
        <h1>Tik Tok Toe</h1>
       
        <img src="thumbnail.png" className="App-logo" alt="logo" />
        <p>
          
         Online Player: {responseText}
         {responseText2}
         
        
       
        </p>

        <div id="test">
        <input value={text} onChange={e => setText(e.target.value)} />
        
        <button onClick={handleClick}>Submit</button>
        </div>
      
      </header>
    </div>
  );
}

export default App;
