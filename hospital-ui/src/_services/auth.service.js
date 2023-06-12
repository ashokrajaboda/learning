//import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL;

const register = (user) => {
  const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(user)
  };

  return fetch(API_URL+`/users/register`, requestOptions).then(handleResponse);
};
/*
const register1 = (username, email, password) => {
  return axios.post(API_URL + "signup", {
    username,
    email,
    password,
  });
};
*/

const login = (username, password) => {
  const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
  };
  return fetch(API_URL+`/users/authenticate`, requestOptions)
  //return fetch(`${config.apiUrl}/users/authenticate`, requestOptions)
      .then(handleResponse)
      .then(user => {
          console.log(user);
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('user', JSON.stringify(user));

          return user;
      });
}

/*
const login1 = (username, password) => {
  return axios
    .post(API_URL + "signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};
*/

const logout = () => {
  localStorage.removeItem("user");
};

const handleResponse = (response) => {
  console.log('Response :', response);
  return response.text().then(text => {
      const data = text && JSON.parse(text);
      if (!response.ok) {
          if (response.status === 401) {
              // auto logout if 401 response returned from api
              logout();
              //location.reload(true);
          }

          const error = (data && data.message) || response.statusText;
          return Promise.reject(error);
      }

      return data;
  });
};

export default {
  register,
  login,
  logout,
};
