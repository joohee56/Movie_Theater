import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getRegionsWithTheaterCount(movieId, date) {
  try {
    const response = await jsonApi.get("/screenings/region/theaterCount", {
      params: {
        movieId: movieId,
        date: date,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getRegionsWithTheaterCount };
