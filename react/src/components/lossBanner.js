import React from "react";
class lossBanner extends React.Component {
  render() {
    // const wins = () => {
    //   "1";
    // };
    return <h5>{"Losses: " + this.props.lose}</h5>;
  }
}

export default lossBanner;
