import axios from "axios";

const headers = {
  Accept: "application/json",
  "Content-Type": "application/json",
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

export const requestNewToken = async (branchCode, serviceTypeId) => {
  let request = {
    applicationDate: new Date(),
    branchCode: branchCode,
    data: {
      queueDate: new Date(),
      branchCode: branchCode,
      serviceTypeId: serviceTypeId,
    },
  };
  let res;
  await axios
    .post(window._env_.API_END_POINT + "requestNewToken", request, headers)
    .then((response) => {
      res = response;
    })
    .catch((error) => {
      console.log(error);
    });
  return res;
};
