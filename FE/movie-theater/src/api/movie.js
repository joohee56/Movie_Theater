import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getAllMovies() {
  try {
    const response = await jsonApi.get("/movies");
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getAllMovies };
