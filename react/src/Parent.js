import React from "react";
import WinsBanner from "./components/winsBanner";
import LossBanner from "./components/lossBanner";
import Board from "./components/board";
import Navbar from "./components/navbar";



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
       
        <WinsBanner wins={0} />
        <LossBanner lose={0} />
        <div class="center">
        <Board />
        </div>
      </div>
    );
  }
}

export default Parent;
