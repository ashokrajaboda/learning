import IResponseDTO from "../types/model.type";
import { logout } from "./auth.service";

export const authHeader = () => {
    const userStr = localStorage.getItem("user");
    let user = null;
    if (userStr)
        user = JSON.parse(userStr);

    if (user && user.accessToken) {
        return { Authorization: 'Bearer ' + user.accessToken }; // for Spring Boot back-end
        // return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
    } else {
        return { Authorization: '' }; // for Spring Boot back-end
        // return { 'x-access-token': null }; // for Node Express back-end
    }
};

export const validateResponse = (response: Response) => {
    console.log('Response :', response);
    return response.json().then((json: IResponseDTO<any>) => {
        console.log('json Response :', json);
        //const data = text && JSON.parse(text);
        //console.log('Response data:', data);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                //location.reload(true);
            }
  
            const error = (json && json.errors) || response.statusText;
            return Promise.reject(error);
        }
        return json;
    });
  };

export default authHeader;