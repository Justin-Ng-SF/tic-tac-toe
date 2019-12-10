import React from "react";
import WinsBanner from "./components/winsBanner";
import LossBanner from "./components/lossBanner";
import Board from "./components/board";
import Navbar from "./components/navbar";
import client from "./components/clientServer"





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
       
        <WinsBanner  />
        <LossBanner  />
        <div>
        <Board />
        </div>
      </div>
    );
  }
}

export default Parent;