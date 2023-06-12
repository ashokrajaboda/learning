import { applyMiddleware, configureStore } from '@reduxjs/toolkit';
import { createLogger } from 'redux-logger';
import thunkMiddleware from 'redux-thunk';
import rootReducer from '../_reducers';
import { authReducer } from './auth.slice';
import { usersReducer } from './users.slice';

export * from './auth.slice';
export * from './users.slice';
/*
export const store = configureStore({
    reducer: {
        auth: authReducer,
        users: usersReducer
    },
});
*/

const loggerMiddleware = createLogger();

export const store = configureStore({
    reducer: rootReducer,
    /*reducer: {
        auth: authReducer,
        users: usersReducer
    },*/
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(loggerMiddleware)
    /*middleware: applyMiddleware(
        thunkMiddleware,
        loggerMiddleware
    )*/
    }
);