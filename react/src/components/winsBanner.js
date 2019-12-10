import React from "react";
import client from "./clientServer"

class winsBanner extends React.Component {
  render() {
    // const wins = () => {
    //   "1";
    // };
    return <h5>{"User1: " + client.player1}</h5>;
  }
}

export default winsBanner;
