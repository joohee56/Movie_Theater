import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function createScreening(request) {
  try {
    const response = await jsonApi.post("/screenings/new", request);
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getRegionsWithScreeningCount(movieId, date) {
  try {
    const response = await jsonApi.get("/screenings/region/screeningCount", {
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

async function getScreenings(movieId, theaterId, date) {
  try {
    const response = await jsonApi.get("/screenings", {
      params: {
        movieId: movieId,
        theaterId: theaterId,
        date: date,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getTheaterAndScreeningCounts(date, movieId, region) {
  try {
    const response = await jsonApi.get("/screenings/theater/screeningCount", {
      params: {
        date: date,
        movieId: movieId,
        region: region,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getScreeningAndTotalPrice(screeningId) {
  try {
    const response = await jsonApi.get(`/screenings/screening/${screeningId}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export {
  createScreening,
  getRegionsWithScreeningCount,
  getScreenings,
  getTheaterAndScreeningCounts,
  getScreeningAndTotalPrice,
};
