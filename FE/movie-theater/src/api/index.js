import axios from "axios";

const config = {
  baseUrl: "http://localhost:8081/api/v1",
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
