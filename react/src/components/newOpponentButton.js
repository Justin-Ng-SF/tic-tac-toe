import React from "react";
import client from "./clientServer"
class newOpponentButton extends React.Component {
  render() {
    const newGame = () => {
      var x = document.getElementById("test");
      x.style.display = "none";

      client.thingToSend3.RoomID    = client.roomId;

      client.x.current.send(JSON.stringify(client.thingToSend3));

  
    };
    return (
      <button className="button" onClick={newGame}>
        new opponent
      </button>
    );
  }
}

export default newOpponentButton;
