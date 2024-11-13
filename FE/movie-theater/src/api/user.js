import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function login(loginId, password) {
  try {
    const response = await jsonApi.post("/users/user/login", {
      loginId: loginId,
      password: password,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function logout() {
  try {
    const response = await jsonApi.get("/users/user/logout");
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { login, logout };