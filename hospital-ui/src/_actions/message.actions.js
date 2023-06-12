import { messageActionConstants } from '../_constants';

export const setMessage = (message) => ({
  type: messageActionConstants.SET_MESSAGE,
  payload: message,
});

export const clearMessage = () => ({
  type: messageActionConstants.CLEAR_MESSAGE,
});