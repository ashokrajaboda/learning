import { alertActionConstants } from "../_constants";

function success(message) {
    return { type: alertActionConstants.SUCCESS, message };
}

function error(message) {
    return { type: alertActionConstants.ERROR, message };
}

function clear() {
    return { type: alertActionConstants.CLEAR };
}