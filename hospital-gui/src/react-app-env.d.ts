/// <reference types="react-scripts" />
declare namespace NodeJS {
    interface ProcessEnv {
       //types of envs
        NODE_ENV: 'development' | 'production' | 'test';
        PUBLIC_URL: string;
        REACT_APP_API_URL: string = 'http://localhost:8082/api';
        REACT_APP_APPLICATION_NAME: string = 'GAGANA Hospital UI';
        PORT: number = 9095;
    }
}