import React from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';

const wsSession = new WebSocket(`ws://localhost:1234/ws`); // fix for multiple connections

function App() {
  const [text, setText] = React.useState(''); // creates state variable, retuns tuple
  const [responseText, setResponseText] = React.useState('');
  
 
  const ws = React.useRef(wsSession);

  const handleClick = () => {
    console.log("checkpoint");
    axios.post(`/player`, text) // enter a player tag save it to database
      .then(
        console.log("checkpoint2")

      )
      .catch(console.log);
  };

  const testPost = () => {
 
   

    //ws.current.send(responseText + 1);
    

  
  };

  ws.current.onopen = () => {
    console.log('Connection open!')

   
   
    
   // ws.current.send(responseText + 1);
  };

  ws.current.onclose = () =>{
    console.log('Connection close!')
    
   // ws.current.send(responseText - 1);

  }

  

  ws.current.onmessage = (message) => {
    setResponseText(message.data);
   // count = Number(message.data);
     console.log(message.data);
     
   };

  
 
  

  

  return (
    <div className="App">
      <header className="App-header">
        <h1>Tik Tok Toe</h1>
       
        <img src="thumbnail.png" className="App-logo" alt="logo" />
        <p>
          
         Online Player: {responseText}
        </p>
        <input value={text} onChange={e => setText(e.target.value)} />
        <hr />
        <button onClick={handleClick}>Submit</button>
        <button onClick={testPost}>Test Post</button>
      </header>
    </div>
  );
}

export default App;
