import authService from "../_services/auth.service";
import { messageActionConstants } from '../_constants';
import { actionConstants } from "../_constants";

export const register = (username, email, password) => (dispatch) => {
  return authService.register(username, email, password).then(
    (response) => {
      dispatch({
        type: actionConstants.REGISTER_SUCCESS,
      });

      dispatch({
        type: messageActionConstants.SET_MESSAGE,
        payload: response.data.message,
      });

      return Promise.resolve();
    },
    (error) => {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();

      dispatch({
        type: actionConstants.REGISTER_FAIL,
      });

      dispatch({
        type: messageActionConstants.SET_MESSAGE,
        payload: message,
      });

      return Promise.reject();
    }
  );
};

export const login = (username, password) => (dispatch) => {
  return authService.login(username, password).then(
    (data) => {
      dispatch({
        type: actionConstants.LOGIN_SUCCESS,
        payload: { user: data },
      });

      return Promise.resolve();
    },
    (error) => {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();

      dispatch({
        type: actionConstants.LOGIN_FAIL,
      });

      dispatch({
        type: messageActionConstants.SET_MESSAGE,
        payload: message,
      });

      return Promise.reject();
    }
  );
};

export const logout = () => (dispatch) => {
  authService.logout();

  dispatch({
    type: actionConstants.LOGOUT,
  });
};
