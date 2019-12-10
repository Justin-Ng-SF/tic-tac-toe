import React from "react";
import client from "./clientServer"

class lossBanner extends React.Component {
  render() {
    // const wins = () => {
    //   "1";
    // };
    return <h5>{"User2: " + client.player2}</h5>;
  }
}

export default lossBanner;
