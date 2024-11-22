import { jsonApiInstance, multipartApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();
const multipartApi = multipartApiInstance();

async function getMovies(date, theaterId) {
  try {
    const response = await jsonApi.get("/movies", {
      params: {
        date: date,
        theaterId: theaterId,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function createMovie(formData) {
  try {
    const response = await multipartApi.post("/movies/new", formData);
    return response;
  } catch (error) {
    return error.response;
  }
}

export { getMovies, createMovie };
