import React from "react";
import LeaveGameButton from "./leaveGameButton";
import NewOpponentButton from "./newOpponentButton";
class navbar extends React.Component {
  render() {
    return (
      <nav className="App-header">
        <LeaveGameButton />
        <NewOpponentButton />
      </nav>
    );
  }
}

export default navbar;
