import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import './App.scss';
import Footer from './components/layout/footer/Footer';
import Header2 from './components/layout/header/Header2';
import Layout from './components/layout/Layout';
import Main2 from './components/layout/main/Main2';
import Sidebar from './components/layout/sidebar/Sidebar';

import { FaAngleUp } from "react-icons/fa";

const App: React.FC = () => {
  //toggle-sidebar side-bar
  const [isNavCollapsed, setIsNavCollapsed] = useState(true);
  const toggleSidebar = () => setIsNavCollapsed(!isNavCollapsed);
  const toggleStyle = isNavCollapsed ? "" : " toggle-sidebar";
  useEffect(() => {
    //console.log('toggleStyle :', toggleStyle);
  }, [isNavCollapsed]);

  const [showTopBtn, setShowTopBtn] = useState(false);
  useEffect(() => {
    window.addEventListener('scroll', () => {
      if (window.scrollY > 150) {
        setShowTopBtn(true);
      } else {
        setShowTopBtn(false);
      }
    });
  }, []);

  const showTopBtnStyle = showTopBtn ? " active" : "";

  const goToTop = (e: any) => {
    e.preventDefault();
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  return (
    <div className={'container-fluid' + toggleStyle}>
      <Header2 appName='Gagans Api' toggleSidebar={toggleSidebar} />
      <Sidebar />
      <Main2 />
      <Footer />
      <a href="#" onClick={goToTop} className={"back-to-top d-flex align-items-center justify-content-center" + showTopBtnStyle}><i className="bi bi-arrow-up-short"></i></a>
    </div>
  );
};

export default App;
