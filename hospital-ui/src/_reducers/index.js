import { combineReducers } from 'redux';

import { users } from './users.reducer';
import { alert } from './alert.reducer';
import { authReducer } from '../_store/auth.slice';
import { message } from './message.reducer';

const rootReducer = combineReducers({
    //authentication: authentication,
    //registration: registration,
    //users: users,
    alert: alert,
    //auth: authReducer,
    auth: users,
    message: message
});

export default rootReducer;