import React, { Component } from "react";
import Button from "../App.css";
class leaveGameButton extends Component {
  render() {
    const exit = () => {};
    return (
      <button className="button" style={Button} onClick={exit}>
        Leave Game
      </button>
    );
  }
}

export default leaveGameButton;
