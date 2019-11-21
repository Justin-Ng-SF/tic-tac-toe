import React from "react";
import LeaveGameButton from "./components/leaveGameButton";
import NewOpponentButton from "./components/newOpponentButton";
import WinsBanner from "./components/winsBanner";
import LossBanner from "./components/lossBanner";
import Board from "./components/board";
class Parent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userList: this.props,
      wins: this.props
    };
  }
  updateWins = () => this.setState({ score: this.props.wins + 1 });
  render() {
    return (
      <div>
        <LeaveGameButton />
        <NewOpponentButton />
        <WinsBanner wins={1} />
        <LossBanner lose={0} />
        <Board />
      </div>
    );
  }
}

export default Parent;
