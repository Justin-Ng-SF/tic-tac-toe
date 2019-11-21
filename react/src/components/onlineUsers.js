import React from "react";
import axios from "axios";

class onlineUsers extends React.Component {
  state = {
    Users: []
  };

  componentDidMount() {
    console.log("comp mounted");
    axios.get("/list").then(response => {
      this.setState({ Users: response.data.response });
    });
  }

  render() {
    return (
      <div className="scroller">
        <div className="container-fluid">
          <div className="row">
            <div className="col">
              {this.state.Users.map(User => {
                return (
                  <div className="card">
                    <div className="card-body">
                      <h5 className="card-title">{User._id}</h5>
                      <p className="card-text">{User.data}</p>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default onlineUsers;
