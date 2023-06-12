import React, { useState, useEffect } from "react";
import { validateResponse } from "../../services/common.service";
import { getApiExceptionContent, getBindingContent, getExceptionContent, getPublicContent } from "../../services/user.service";
import ResponseDTO from "../../types/model.type";


const Home: React.FC = () => {
  const [content, setContent] = useState<string>("");

  useEffect(() => {
    getPublicContent().then(validateResponse)
      .then((returnedResponse: ResponseDTO<any>) => {
        // Your response to manipulate
        //const data = returnedResponse && JSON.parse(returnedResponse);
        const data = returnedResponse.data;
        setContent(data?.zonedDateTimeWithUserZone);
      }).catch((error: any) => {
        // Your error is here!
        console.log(error)
      });
    /*
    getExceptionContent().then(validateResponse)
      .then((returnedResponse: ResponseDTO<any>) => {
        // Your response to manipulate
        //const data = returnedResponse && JSON.parse(returnedResponse);
        const data = returnedResponse.data;
        setContent(data?.zonedDateTimeWithUserZone);
      }).catch((error: any) => {
        // Your error is here!
        console.log(error)
      });

    getApiExceptionContent().then(validateResponse)
      .then((returnedResponse: ResponseDTO<any>) => {
        // Your response to manipulate
        //const data = returnedResponse && JSON.parse(returnedResponse);
        const data = returnedResponse.data;
        setContent(data?.zonedDateTimeWithUserZone);
      }).catch((error: any) => {
        // Your error is here!
        console.log(error)
      });

    getBindingContent().then(validateResponse)
      .then((returnedResponse: ResponseDTO<any>) => {
        // Your response to manipulate
        //const data = returnedResponse && JSON.parse(returnedResponse);
        const data = returnedResponse.data;
        setContent(data?.zonedDateTimeWithUserZone);
      }).catch((error: any) => {
        // Your error is here!
        console.log(error)
      });
    /*
    getPublicContent().then(
      (response: any) => {
        console.log('getPublicContent :', response);
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
    */
  }, []);

  return (
    <section className="section min-vh-100">
      <h3>{content}</h3>
    </section>
  );
};

export default Home;