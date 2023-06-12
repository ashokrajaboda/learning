/*import { applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import { createLogger } from 'redux-logger';
import rootReducer from '../_reducers';
import { configureStore } from '@reduxjs/toolkit';

const loggerMiddleware = createLogger();

export const store = configureStore({
    rootReducer: rootReducer,
    middleware: applyMiddleware(
        thunkMiddleware,
        loggerMiddleware
    )
    }
);
*/