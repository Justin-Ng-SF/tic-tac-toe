import React from "react";
class winsBanner extends React.Component {
  render() {
    // const wins = () => {
    //   "1";
    // };
    return <h5>{"Wins: " + this.props.wins}</h5>;
  }
}

export default winsBanner;
