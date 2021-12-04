import axios from "axios";
import SockJs from "sockjs-client";
import Stomp from "stompjs";

const headers = {
  Accept: "application/json",
  "Content-Type": "application/json",
};

export const connect = () => {
  const socket = new SockJs("http://localhost:4070/queue");

  const stompClient = Stomp.over(socket);
  return stompClient;
};

export const getServiceTypesForBranch = async (branchCode) => {
  let request = {
    applicationDate: new Date(),
    branchCode: branchCode,
    data: {
      branchCode: branchCode,
    },
  };
  let res;
  await axios
    .post(
      window._env_.API_END_POINT + "getServiceTypesForBranch",
      request,
      headers
    )
    .then((response) => {
      res = response;
    })
    .catch((error) => {
      console.log(error);
    });
  return res;
};
