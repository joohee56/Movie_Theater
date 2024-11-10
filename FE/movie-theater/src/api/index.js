import axios from "axios";
axios.defaults.withCredentials = true;

const config = {
  baseUrl: "http://api.b7n5k2q8v9r.com:8081/api/v1",
};

function jsonApiInstance() {
  const instance = axios.create({
    baseURL: config.baseUrl,
    headers: {
      "content-type": "application/json",
    },
  });
  return instance;
}

export { jsonApiInstance };
