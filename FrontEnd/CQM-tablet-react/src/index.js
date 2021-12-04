import React from "react";
import ReactDOM from "react-dom";
import TokenForm from "./component/TokenGenerator/index";

window._env_ = {
  API_END_POINT: "http://localhost:4070/",
};

ReactDOM.render(
  <React.StrictMode>
    <TokenForm />
  </React.StrictMode>,
  document.getElementById("root")
);
