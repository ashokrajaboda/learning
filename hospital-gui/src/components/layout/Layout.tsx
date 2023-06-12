import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import eventBus from "../../common/EventBus";
import { getCurrentUser, logout } from "../../services/auth.service";
import IUser from "../../types/user.type";
import AppRoutes from "./AppRoutes";
import Footer from "./footer/Footer";
import Header from "./header/Header";
import Main from "./main/Main";
import Sidebar from "./sidebar/Sidebar";

const Layout: React.FC = (props) => {
    const appName: string = (process.env.REACT_APP_APPLICATION_NAME as string);
    return (
        <>
            <Header appName={appName}/>
            <Sidebar />
            <Main />
            <Footer />
        </>
    );
};

export default Layout;