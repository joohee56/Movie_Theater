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
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

async function getRecentMovies() {
  try {
    const response = await jsonApi.get("/movies/recent");
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

async function getMoviesWithWatchable(date, theaterId) {
  try {
    const response = await jsonApi.get("/movies/watchable", {
      params: {
        date: date,
        theaterId: theaterId,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

async function createMovie(formData) {
  try {
    const response = await multipartApi.post("/movies/new", formData);
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

export { getMovies, getRecentMovies, getMoviesWithWatchable, createMovie };
