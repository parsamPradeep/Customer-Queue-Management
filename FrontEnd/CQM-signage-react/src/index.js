import React from "react";
import ReactDOM from "react-dom";
import App from "./App";

window._env_ = {
  API_END_POINT: "http://localhost:4070/",
};

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById("root")
);
